package episen.si.ing1.pds.backend.server;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientRequestManager {

	private final static Logger logger = LoggerFactory.getLogger(ClientRequestManager.class);
	private final PrintWriter output;
	private final BufferedReader input;
	private Connection c;
	private String name = "client-thread";
	private Thread self;
	private ObjectMapper mapper = new ObjectMapper(new JsonFactory());

	public ClientRequestManager(Socket socket, Connection connection) throws SQLException, IOException {
		c = connection;
		c.setAutoCommit(true);
		output = new PrintWriter(socket.getOutputStream(), true);
		input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		self = new Thread(name) {
			@Override
			public void run() {
				try {
					String clientInput = input.readLine();
					String requestType = clientInput.split("#")[0];
					String values = clientInput.split("#")[1];

					Map<String, String> map = mapper.readValue(values,new TypeReference<Map<String, String>>(){});

					switch (requestType) {
						case "companyReservation":
							companyReservation(map);
							break;
						case "reservationFloor":
							reservationFloor(map);
							break;
						case "floorRoom":
							floorRoom(map);
							break;
						case "roomLocation":
							roomLocation(map);
							break;
						case "locationEquipment":
							locationEquipment(map);
							break;
						case "reservationEquipment":
							reservationEquipment(map);
							break;
						case "setEquipment":
							setEquipment(map);
							break;
						case "homePage1":
							firstPage(map);
							break;
						case "requestLocation1":
							getChoice(map);
							break;
						case "requestLocation2":
							getDevice(map);
							break;
						case "requestLocation3":
							getDisponibility(map);
							break;
						case "requestLocation5":
							getManagerId(map);
							break;
						case "requestLocation4":
							insertReservation(map);
							break;
					}

					/*switch (requestType) {
					case "insert":
						StringBuilder request = new StringBuilder();
						request.append("insert into test(name,age) values");
						for (Map<String, String> m : map.values())
							request.append("('" + m.get("name") + "','" + m.get("age") + "'),");
						request.deleteCharAt(request.length() - 1);
						output.println("Successfully inserted " + c.createStatement().executeUpdate(request.toString())
								+ " rows.");
						break;
					case "select":
						StringBuilder sb = new StringBuilder();
						ResultSet result = c.createStatement().executeQuery("select * from test");
						while (result.next()) {
							sb.append("id=" + result.getInt(1) + ",name=" + result.getString(2) + ",age=" + result.getInt(3) +"|");
						}
						output.println(sb.toString());
						break;
					case "update":
						int newAge = Integer.valueOf(map.get("toto").get("age"))+1;
						output.println(
								"Successfully updated "
										+ c.createStatement()
												.executeUpdate("update test set age=" + newAge
														+ " where name='" + map.get("toto").get("name") + "'")
										+ " rows.");
						break;
					case "delete":
						output.println("Successfully deleted " + c.createStatement().executeUpdate("delete from test")
								+ " rows.");
						break;
					default:
						output.println("Invalid request type.");
						break;
					}*/
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		self.start();
	}

	public Thread getSelf() {
		return self;
	}

	public void companyReservation(Map<String,String> map) {
		try {
			String request = "Select reservation_id,start_date,end_date,price,employee_last_name,employee_first_name from reservation inner join employee on (reservation.gs_manager_id = employee.employee_id) where company_id="+map.get("company_id");
			ResultSet rs = c.createStatement().executeQuery(request);
			Map<String,Map<String, String>> result = new HashMap<String,Map<String, String>>();
			int i=0;
			while(rs.next()) {
				Map<String, String> resultmap = new HashMap<String, String>();
				resultmap.put("reservation_id", rs.getString(1));
				resultmap.put("start_date", rs.getString(2));
				resultmap.put("end_date", rs.getString(3));
				resultmap.put("price", rs.getString(4));
				resultmap.put("employee_last_name", rs.getString(5));
				resultmap.put("employee_first_name", rs.getString(6));
				result.put(""+i++, resultmap);
			}
			output.println(mapper.writeValueAsString(result));
		} catch (JsonMappingException e) {} catch (JsonProcessingException e) {} catch (SQLException e) {}

	}

	public void reservationFloor(Map<String,String> map) {
		try {
			String request = "select distinct(floor_id),floor_number,building_name from (room natural join floor) natural join building where reservation_id="+map.get("reservation_id");
			ResultSet rs = c.createStatement().executeQuery(request);
			Map<String,Map<String, String>> result = new HashMap<String,Map<String, String>>();
			int i=0;
			while(rs.next()) {
				Map<String, String> resultmap = new HashMap<String, String>();
				resultmap.put("floor_id", rs.getString(1));
				resultmap.put("floor_number", rs.getString(2));
				resultmap.put("building_name", rs.getString(3));
				result.put(""+i++, resultmap);
			}
			output.println(mapper.writeValueAsString(result));
		} catch (JsonMappingException e) {} catch (JsonProcessingException e) {} catch (SQLException e) {}

	}

	public void floorRoom(Map<String,String> map) {
		try {
			String request = "select room_id,room_wording,room_type_id from room where reservation_id="+map.get("reservation_id")+" and floor_id="+map.get("floor_id");
			ResultSet rs = c.createStatement().executeQuery(request);
			Map<String,Map<String, String>> result = new HashMap<String,Map<String, String>>();
			int i=0;
			while(rs.next()) {
				Map<String, String> resultmap = new HashMap<String, String>();
				resultmap.put("room_id", rs.getString(1));
				resultmap.put("room_wording", rs.getString(2));
				resultmap.put("room_type_id", rs.getString(3));
				result.put(""+i++, resultmap);
			}
			output.println(mapper.writeValueAsString(result));
		} catch (JsonMappingException e) {} catch (JsonProcessingException e) {} catch (SQLException e) {}

	}

	public void roomLocation(Map<String,String> map) {
		try {
			String request = "select location_id,occupied_location,is_sensor,x_position,y_position from location where room_id="+map.get("room_id");
			ResultSet rs = c.createStatement().executeQuery(request);
			Map<String,Map<String, String>> result = new HashMap<String,Map<String, String>>();
			int i=0;
			while(rs.next()) {
				Map<String, String> resultmap = new HashMap<String, String>();
				resultmap.put("location_id", rs.getString(1));
				resultmap.put("occupied_location", rs.getString(2));
				resultmap.put("is_sensor", rs.getString(3));
				resultmap.put("x_position", rs.getString(4));
				resultmap.put("y_position", rs.getString(5));
				result.put(""+i++, resultmap);
			}
			output.println(mapper.writeValueAsString(result));
		} catch (JsonMappingException e) {} catch (JsonProcessingException e) {} catch (SQLException e) {}

	}
	public void getWindow(String values) throws SQLException {

		try {
			ObjectMapper mapper = new ObjectMapper(new JsonFactory());
			String request="select device.device_id from device inner join windows on(device.device_id=windows.device_id)";
			ResultSet rs = c.createStatement().executeQuery(request);
			Map<String,Map<String, String>> result = new HashMap<String,Map<String, String>>();
			int i=0;
			while(rs.next()) {
				Map<String, String> tab = new HashMap<String, String>();
				tab.put("device_id", rs.getString(1));
				result.put(""+i++, tab);
			}
			output.println(mapper.writeValueAsString(result));
		} catch (JsonMappingException e) {} catch (JsonProcessingException e) {}
	}

	public void locationEquipment(Map<String,String> map) {
		try {
			String request = "select device_id,device_wording,device_active,device_price,reservation_id,is_sensor from device natural join location where location_id="+map.get("location_id");
			ResultSet rs = c.createStatement().executeQuery(request);
			Map<String, String> result = new HashMap<String, String>();
			while(rs.next()) {
				result.put("device_id", rs.getString(1));
				result.put("device_wording", rs.getString(2));
				result.put("device_active", rs.getString(3));
				result.put("device_price", rs.getString(4));
				result.put("reservation_id", rs.getString(5));
				result.put("is_sensor", rs.getString(6));
			}
			output.println(mapper.writeValueAsString(result));
		} catch (JsonMappingException e) {} catch (JsonProcessingException e) {} catch (SQLException e) {}

	}


	public void reservationEquipment(Map<String,String> map) {
		try {
			String request = "";
			if(map.get("is_sensor").equals("f")) {
				request = "select device_id,device_wording,device_price from device where device_type_id<11 and location_id is null and reservation_id="+map.get("reservation_id");
			}else {
				request = "select device_id,device_wording,device_price from device where device_type_id>10 and location_id is null and reservation_id="+map.get("reservation_id");
			}
			ResultSet rs = c.createStatement().executeQuery(request);
			Map<String,Map<String, String>> result = new HashMap<String,Map<String, String>>();
			int i=0;
			while(rs.next()) {
				Map<String, String> resultmap = new HashMap<String, String>();
				resultmap.put("device_id", rs.getString(1));
				resultmap.put("device_wording", rs.getString(2));
				resultmap.put("device_price", rs.getString(3));
				result.put(""+i++, resultmap);
			}
			output.println(mapper.writeValueAsString(result));
		} catch (JsonMappingException e) {} catch (JsonProcessingException e) {} catch (SQLException e) {}

	}

	public void setEquipment(Map<String,String> map) {
		try {

			String location = map.get("location_id");
			String newDevice = map.get("new_device_id");
			String oldDevice = map.get("old_device_id");

			Statement s = c.createStatement();
			if(oldDevice.isEmpty()) {
				s.executeUpdate("update device set device_placed='t', location_id="+location+" where device_id="+newDevice);
				s.executeUpdate("update location set occupied_location='t' where location_id="+location);
			}else if(newDevice.isEmpty()) {
				s.executeUpdate("update device set device_placed='f', location_id=null where device_id="+oldDevice);
				s.executeUpdate("update location set occupied_location='f' where location_id="+location);
			}else {
				s.executeUpdate("update device set device_placed='t', location_id="+location+" where device_id="+newDevice);
				s.executeUpdate("update device set device_placed='f', location_id=null where device_id="+oldDevice);
			}

			output.println("Done");
		} catch (SQLException e) {}
	}


	public void getChoice(Map<String,String> map){
		try {
			int numberOpenSpace = Integer.parseInt(map.get("numberOpenSpace")) * 4;
			int numberClosedOffice = Integer.parseInt(map.get("numberClosedOffice")) *4;
			int numberSingleOffice = Integer.parseInt(map.get("numberSingleOffice")) * 4;
			int numberMeetingRoom = Integer.parseInt(map.get("numberMeetingRoom")) * 4;

			String request = "select room_wording, floor_number, building_name, room_price as prix, room_id, room_type_id " +
					"from room r " +
					"inner join floor f on f.floor_id = r.floor_id " +
					"inner join building b on b.building_id = f.building_id " +
					"where room_id in "+
					"(select room_id " +
					"from room r " +
					"inner join floor f on f.floor_id = r.floor_id " +
					"inner join building b on b.building_id = f.building_id " +
					"where status = 'free' and room_type_id = 1 ";
			if( map.keySet().contains("location") && !(map.get("location").equals("")) )	request	= request + " and position = '" + map.get("location")+"' ";
			request = request + "Limit " + numberOpenSpace + ")"+
					"or room_id in "+
					" (select room_id "+
					"from room r " +
					"inner join floor f on f.floor_id = r.floor_id " +
					"inner join building b on b.building_id = f.building_id " +
					"where status = 'free' and room_type_id = 3 ";
			if( map.keySet().contains("location")  && !(map.get("location").equals("")) )	request	= request + " and position = '" + map.get("location")+"' ";
			request = request + "Limit " + numberClosedOffice + ")"+
					"or room_id in "+
					"(select room_id " +
					"from room r " +
					"inner join floor f on f.floor_id = r.floor_id " +
					"inner join building b on b.building_id = f.building_id " +
					"where status = 'free' and room_type_id = 4 " ;
			if( map.keySet().contains("location") && !(map.get("location").equals(""))  )	request	= request + " and position = '" + map.get("location")+"' ";
			request = request + "Limit " + numberSingleOffice + ")"+
					"or room_id in " +
					"(select room_id " +
					"from room r " +
					"inner join floor f on f.floor_id = r.floor_id " +
					"inner join building b on b.building_id = f.building_id " +
					"where status = 'free' and room_type_id = 2 ";
			if( map.keySet().contains("location")  && !(map.get("location").equals(""))  )	request	= request + " and position = '" + map.get("location")+"' ";
			request = request +"Limit " + numberMeetingRoom +") " +
					" order by room_price;";


			System.out.println(request);
			ResultSet result = c.createStatement().executeQuery(request);
			Map<String, Map<String, String>> roomProposal1 = new HashMap<>();
			Map<String, Map<String, String>> roomProposal2 = new HashMap<>();
			Map<String, Map<String, String>> roomProposal3 = new HashMap<>();
			Map<String, Map<String, String>> roomProposal4 = new HashMap<>();
			Map<String , Map<String, Map<String ,String>>> proposal = new HashMap<>();

			int numberRoom = Integer.parseInt(map.get("numberClosedOffice"))
					+ Integer.parseInt(map.get("numberSingleOffice"))
					+ Integer.parseInt(map.get("numberOpenSpace"))
					+ Integer.parseInt(map.get("numberMeetingRoom"));

			int countOpenSpaceProposal1 = 0, countMeetingRoomProposal1 = 0, countClosedOfficeProposal1 = 0, countSingleOfficeProposal1 = 0;
			int countOpenSpaceProposal2 = 0,countMeetingRoomProposal2 = 0,countClosedOfficeProposal2 = 0,countSingleOfficeProposal2 = 0;
			int countOpenSpaceProposal3 = 0,countMeetingRoomProposal3 = 0,countClosedOfficeProposal3 = 0,countSingleOfficeProposal3 = 0;
			int countOpenSpaceProposal4 = 0,countMeetingRoomProposal4 = 0,countClosedOfficeProposal4 = 0,countSingleOfficeProposal4 = 0;

			int countRoomProposal1 =1,countRoomProposal2 =1,countRoomProposal3 =1,countRoomProposal4 =1;

			while(result.next()){
				Map<String, String> underMap = new HashMap<>();
				underMap.put("room_wording",result.getString(1));
				underMap.put("floor_number",result.getInt(2)+"");
				underMap.put("building_name",result.getString(3));
				underMap.put("price",result.getString(4));
				underMap.put("room_id",result.getString(5));
				underMap.put("room_type_id",result.getString(6));

				if( result.getInt(6) == 4 ){
					if(countSingleOfficeProposal1 < Integer.parseInt(map.get("numberSingleOffice") )) {
						roomProposal1.put("roomSingleOffice"+countRoomProposal1,underMap);
						countSingleOfficeProposal1++;
					} else if (countSingleOfficeProposal2 < Integer.parseInt(map.get("numberSingleOffice") )) {
						roomProposal2.put("roomSingleOffice"+countRoomProposal2,underMap);
						countSingleOfficeProposal2++;
					} else if(countSingleOfficeProposal3 < Integer.parseInt(map.get("numberSingleOffice") )) {
						roomProposal3.put("roomSingleOffice"+countRoomProposal3,underMap);
						countSingleOfficeProposal3++;
					} else if(countSingleOfficeProposal4 < Integer.parseInt(map.get("numberSingleOffice") )) {
						roomProposal4.put("roomSingleOffice"+countRoomProposal4,underMap);
						countSingleOfficeProposal4++;
					}
				}
				if( result.getInt(6) == 3 ){
					if(countClosedOfficeProposal1 < Integer.parseInt(map.get("numberClosedOffice") )) {
						roomProposal1.put("roomClosedOffice"+countRoomProposal1,underMap);
						countClosedOfficeProposal1++;
					}else if (countClosedOfficeProposal2 < Integer.parseInt(map.get("numberClosedOffice") )) {
						roomProposal2.put("roomClosedOffice"+countRoomProposal2,underMap);
						countClosedOfficeProposal2++;
					}else if(countClosedOfficeProposal3 < Integer.parseInt(map.get("numberClosedOffice") )) {
						roomProposal3.put("roomClosedOffice"+countRoomProposal3,underMap);
						countClosedOfficeProposal3++;
					}else if(countClosedOfficeProposal4 < Integer.parseInt(map.get("numberClosedOffice") )) {
						roomProposal4.put("roomClosedOffice"+countRoomProposal4,underMap);
						countClosedOfficeProposal4++;
					}
				}
				if( result.getInt(6) == 2 ){
					if(countMeetingRoomProposal1 < Integer.parseInt(map.get("numberMeetingRoom") )) {
						roomProposal1.put("roomMeetingRoom"+countRoomProposal1,underMap);
						countMeetingRoomProposal1++;
					}else if (countMeetingRoomProposal2 < Integer.parseInt(map.get("numberMeetingRoom") )) {
						roomProposal2.put("roomMeetingRoom"+countRoomProposal2,underMap);
						countMeetingRoomProposal2++;
					}else if(countMeetingRoomProposal3 < Integer.parseInt(map.get("numberMeetingRoom") )) {
						roomProposal3.put("roomMeetingRoom"+countRoomProposal3,underMap);
						countMeetingRoomProposal3++;
					}else if(countMeetingRoomProposal4 < Integer.parseInt(map.get("numberMeetingRoom") )) {
						roomProposal4.put("roomMeetingRoom"+countRoomProposal4,underMap);
						countMeetingRoomProposal4++;
					}
				}
				if( result.getInt(6) == 1 ){
					if(countOpenSpaceProposal1 < Integer.parseInt(map.get("numberOpenSpace") )) {
						roomProposal1.put("roomOpenSpace"+countRoomProposal1,underMap);
						countOpenSpaceProposal1++;
					}else if (countOpenSpaceProposal2 < Integer.parseInt(map.get("numberOpenSpace") )) {
						roomProposal2.put("roomOpenSpace"+countRoomProposal2,underMap);
						countOpenSpaceProposal2++;
					}else if(countOpenSpaceProposal3 < Integer.parseInt(map.get("numberOpenSpace") )) {
						roomProposal3.put("roomOpenSpace"+countRoomProposal3,underMap);
						countOpenSpaceProposal3++;
					}else if(countOpenSpaceProposal4 < Integer.parseInt(map.get("numberOpenSpace") )) {
						roomProposal4.put("roomOpenSpace"+countRoomProposal4,underMap);
						countOpenSpaceProposal4++;
					}
				}
				if(countRoomProposal1 == numberRoom) countRoomProposal1 = 1;
				else countRoomProposal1++;
				if(countRoomProposal2 == numberRoom) countRoomProposal2 = 1;
				else countRoomProposal2++;
				if(countRoomProposal3 == numberRoom) countRoomProposal3 = 1;
				else countRoomProposal3++;
				if(countRoomProposal4 == numberRoom) countRoomProposal4 = 1;
				else countRoomProposal4++;
			}
			proposal.put("proposal1", roomProposal1);
			proposal.put("proposal2", roomProposal2);
			proposal.put("proposal3", roomProposal3);
			proposal.put("proposal4", roomProposal4);

			ObjectMapper objectMapper = new ObjectMapper();
			String proposals = objectMapper.writeValueAsString(proposal);

			output.println(proposals);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void firstPage(Map<String,String> map) {
		try {
			ResultSet result = c.createStatement().executeQuery("select company_name,company_id from company " +
					"where company_name = '"+ map.get("company_name") +"';");

			if(result.next()) {
				String data = result.getString(1)+ ","+result.getString(2);
				output.println(data);
			} else output.println("false,");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getDevice(Map<String,String> map){
		try {
			int room_id = Integer.parseInt(map.get("room_id"));
			String request = "select distinct device_wording, device_type_wording, device_price " +
					"from device d " +
					"inner join device_type dt on dt.device_type_id = d.device_type_id "+
					"where d.device_type_id in(" +
					"select dt.device_type_id " +
					"from device_type dt " +
					"inner join could_configure cc on cc.device_type_id = dt.device_type_id " +
					"inner join room_type rt on rt.room_type_id = cc.room_type_id " +
					"inner join room r on r.room_type_id = rt.room_type_id " +
					"where room_id = "+ room_id +");";

			ResultSet result = c.createStatement().executeQuery(request);
			StringBuilder sb = new StringBuilder();
			while(result.next()) sb.append(result.getString(1)+ " -- "+ result.getString(3) + "euros /      "+ result.getString(2)+ ",");

			output.println(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getDisponibility(Map<String,String> map){
		try {
			int quantity = Integer.parseInt(map.get("quantity"));
			String device = map.get("device_wording");

			String exceptId = " ";
			if(map.size() > 2){
				for(Map.Entry m : map.entrySet()){
					if( !((m.getKey()+"").equals("device_wording")) && !((m.getKey()+"").equals("quantity")) ){
						exceptId = exceptId + " and device_id <> " + m.getValue() + " ";
					}
				}
			}

			String request = "  select device_id " +
					"  from device d " +
					"  where device_wording ='"+ device +"' and device_status = 'free' ";
			if( !(exceptId.equals(" ")) ) request = request + exceptId;

			request = request + "  limit "+ quantity +"  ;";

			ResultSet result = c.createStatement().executeQuery(request);

			StringBuilder strB = new StringBuilder();
			while(result.next()){
				strB.append(result.getInt(1)+ ",");
			}
			output.println(strB.toString());
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getManagerId(Map<String,String> map){
		try {
			String request = "select gs_manager_id " +
					"from general_services_manager g " +
					"inner join employee e on g.gs_manager_id = e.employee_id " +
					"where company_id = "+ map.get("company_id") +";";
			ResultSet result = c.createStatement().executeQuery(request);

			String companyId ="";
			while(result.next()){
				companyId = result.getString(1);
			}
			output.println(companyId.toString());
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void insertReservation(Map<String,String> map){
		try {
			c.setAutoCommit(true);

			String requestInsert = " insert into reservation (end_date, start_date, price, gs_manager_id) "+
					" values ('" + map.get("end_date") + "', '"+ map.get("start_date")+
					"', '"+ map.get("price") + "', '"+ map.get("gs_manager_id")+ "'); ";

			int i = 0;
			String verifyDataRoom =" select status from room where ";
			String verifyRequestUpdateDevice = "select device_status from device where ";
			List verifyDeviceId = new ArrayList<>();

			String whereRequestUpdateRoom = "where ";
			String requestUpdateDevice = "";
			StringBuffer verifyDevice;
			for(Map.Entry m : map.entrySet()) {
				if((m.getKey()+"").contains("room")){
					whereRequestUpdateRoom = whereRequestUpdateRoom + " room_id = " + m.getValue() + " or ";
					verifyDataRoom = verifyDataRoom + " room_id = " + m.getValue() + " or ";
				}

				if( !((m.getKey()+"").equals("")) ){
					String key = (m.getKey()+"").trim();
					if( key.matches("\\d+")  ){

						String deviceId = m.getValue()+"";
						deviceId = deviceId.replace("[", "");
						deviceId = deviceId.replace("]","");

						String[] listDeviceId = deviceId.split(",");
						if( listDeviceId.length != 1 ){
							String whereRequestUpdateDevice =" where";
							System.out.println(listDeviceId.length);
							for(int y = 0; y < listDeviceId.length; y++){
								System.out.println(listDeviceId[y]);
								verifyDeviceId.add(listDeviceId[y]);
								whereRequestUpdateDevice = whereRequestUpdateDevice + " device_id = " + listDeviceId[y]+ " or ";
							}
							verifyDevice = new StringBuffer(whereRequestUpdateDevice);
							System.out.println(verifyDevice+"/////////////////////////");
							if(whereRequestUpdateDevice.contains("or")){
								verifyDevice.delete(verifyDevice.length() - 4, verifyDevice.length());
								verifyDevice.append(";");
							}
							requestUpdateDevice = requestUpdateDevice + " update device "+
									"set device_status = 'booked', "+
									"reservation_id = (select max(reservation_id) from reservation), "+
									" room_id = " + m.getKey()+ " "+ verifyDevice;
						}
					}
				}
			}
			StringBuffer verifyRoom = new StringBuffer(verifyDataRoom);
			verifyRoom.delete(verifyRoom.length() - 4, verifyRoom.length());
			verifyRoom.append(";");

			System.out.println("/////////////");
			System.out.println(verifyRoom);

			ResultSet resultRoom = c.createStatement().executeQuery(verifyRoom+"");

			boolean verifyDataDB = true;
			while(resultRoom.next()){
				if(resultRoom.getString(1).equals("booked"))	verifyDataDB = false;
			}

			for(int k = 0; k < verifyDeviceId.size(); k++){
				if( !(verifyDeviceId.get(k).equals("")) ) verifyRequestUpdateDevice = verifyRequestUpdateDevice + " device_id = " + verifyDeviceId.get(k)+ " or ";
			}

			if(verifyRequestUpdateDevice.contains("or")){
				System.out.println("test");
				StringBuffer verifyRoomUpdateDevice = new StringBuffer(verifyRequestUpdateDevice);
				verifyRoomUpdateDevice.delete(verifyRoomUpdateDevice.length() - 4, verifyRoomUpdateDevice.length());
				verifyRoomUpdateDevice.append(";");
				ResultSet resultDevice = c.createStatement().executeQuery(verifyRoomUpdateDevice+"");

				while(resultDevice.next()){
					if(resultDevice.getString(1).equals("booked")) verifyDataDB = false;
				}
			}

			if(verifyDataDB){
				StringBuffer correctWhere = new StringBuffer(whereRequestUpdateRoom);
				correctWhere.delete(correctWhere.length() - 4, correctWhere.length());
				correctWhere.append(";");

				String requestUpdateRoom = "update room "+
						"set status = 'booked', "+
						"    reservation_id = (select max(reservation_id) from reservation)  "+ correctWhere;

				String requestOrder = requestInsert + requestUpdateRoom + requestUpdateDevice;
				System.out.println("/////////////");
				System.out.println(requestOrder);
				output.println("Successfully updated "+ c.createStatement().executeUpdate(requestOrder));
			} else output.println("Not done");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}