package episen.si.ing1.pds.backend.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ClientRequestManager {

	private final static Logger logger = LoggerFactory.getLogger(ClientRequestManager.class);
	private final PrintWriter output;
	private final BufferedReader input;
	private Connection c;
	private String name = "client-thread";
	private Thread self;

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

					if(requestType.equals("homePage1")) firstPage(values);


					if(requestType.equals("requestNewBadge")) SaveBadge((values));
					if(requestType.equals("getpermissions")) getPermissions((values));
					if(requestType.equals("testpermissions")) testpermissions((values));
					if(requestType.equals("getdevices")) getdevices((values));
					if(requestType.equals("requestDetailBadge")) getDetailPermission((values));

					if(requestType.equals("requestadddevice")) adddevicepermission((values));
					if(requestType.equals("requestallbadges")) getallemployees((values));
					if(requestType.equals("requestManyNewBadge")) saveallemployees((values));





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


	public void SaveBadge(String values){
		logger.debug("bravo : test save4");
		try {
			ObjectMapper mapper = new ObjectMapper(new JsonFactory());
			Map<String, Map<String, String>> map = mapper.readValue(values,
					new TypeReference<Map<String, Map<String, String>>>() {
					});
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String name1 =map.get("requestNewBadge").get("nomemploye");
			String name2=map.get("requestNewBadge").get("prenomemploye");
			String idbadge=map.get("requestNewBadge").get("puceemploye");
			Date today = dateFormat.parse(dateFormat.format(new Date()));
			int agent=Integer.parseInt(map.get("requestNewBadge").get("idagent"));
			Date dateendbadge = dateFormat.parse(map.get("requestNewBadge").get("badge_date"));
			Date datecontract = dateFormat.parse(map.get("requestNewBadge").get("contract_date"));
			Date datepermission = dateFormat.parse(map.get("requestNewBadge").get("permission_date"));
			String emailagent=map.get("requestNewBadge").get("emailagent");
			String permission=map.get("requestNewBadge").get("permission");
			int id_company=Integer.parseInt(map.get("requestNewBadge").get("company_id"));
			String state="En Fonction";
			int id=0;






            String requestInsert = "insert into employee (employee_last_name,employee_first_name,company_id,contract_duration) values ('"+name1+"','"+name2+"','"+id_company+"','"+datecontract +"') ;";

			c.createStatement().executeUpdate(requestInsert);
			ResultSet lastemployee = c.createStatement().executeQuery("select employee.employee_id from employee order by employee.employee_id DESC LIMIT 1; ");

			while (lastemployee.next()){
				id=lastemployee.getInt(1);
			}

			ResultSet agentid = c.createStatement().executeQuery("select general_services_manager.gs_manager_id from general_services_manager where gs_manager_id = '"+ agent+"';");

			int id2;
			if (agentid.next())
				while (agentid.next()){
					id2=lastemployee.getInt(1);
					String requestupdate ="update general_services_manager set manager_email ='"+ emailagent +"'where gs_manager_id='"+id2   +" ';";
					c.createStatement().executeQuery(requestupdate);

				}
			else{output.println("notgood id agent");}


			String requestInsert2 = "insert into badge (badge_id,badge_start_date,badge_end_date,badge_state,employee_id,gs_manager_id) " +
					"values ('"+idbadge+"','"+today+"','"+dateendbadge+"','"+state +"','"+id+"','"+agent+"') ;";

			try{
				c.createStatement().executeUpdate(requestInsert2);
			}catch (Exception e) {
				output.println("notgood badge");
			}



			String per[]=permission.split(",");
			int idpermission=Integer.parseInt(getNbr(per[0]));
			String insertrequest = "insert into permission_access (badge_id,permission_id,permission_validity_period) VALUES ('"+idbadge+"','"+idpermission+"','"+datepermission+"') ;";
			try {
				c.createStatement().executeUpdate(insertrequest);
			}catch (Exception e){
				output.println("notgood permision");
			}




			output.println("good");
		}catch (Exception e){
			output.println("notgood");
			e.printStackTrace();
		}

	}

	static String getNbr(String str)
	{

		str = str.replaceAll("[^\\d]", " ");
		str = str.trim();
		str = str.replaceAll(" +", " ");
		return str;
	}





	public void getDetailPermission(String values){
		logger.debug("testdetail");
		try {
			ObjectMapper mapper = new ObjectMapper(new JsonFactory());
			Map<String, Map<String, String>> map = mapper.readValue(values,
					new TypeReference<Map<String, Map<String, String>>>() {
					});

			int id_company=Integer.parseInt(map.get("requestDetailBadge").get("company_id"));
			String permission=map.get("requestDetailBadge").get("permission");
			String per[]=permission.split(",");
			int idpermission=Integer.parseInt(getNbr(per[0]));
			String namepermission=per[1].split(":")[1];



			ResultSet result = c.createStatement().executeQuery("select device.device_id,device_wording,device_active,device.room_id,permission_device.number_validity_use,equipement_validity_period " +
					"from permission_device inner join device on device.device_id=permission_device.device_id where permission_device.permission_id='"+ idpermission +"';");

			logger.debug("c bon");

			StringBuilder sb = new StringBuilder();

			sb.append(idpermission);

			sb.append("//");
			sb.append(namepermission);
			sb.append("//");



			while (result.next()) {

				///sb.append("device_id=" + result.getInt(1) + ",device_wording=" + result.getString(2) + ",device_active=" + result.getBoolean(3) + ",room_id=" + result.getInt(4) +",number_validity_use=" + result.getInt(5) +",equipement_validity_period="+ result.getDate(6) +"#");
				sb.append(result.getInt(1) + "," + result.getString(2) + "," + result.getBoolean(3) + "," + result.getInt(4) +"," + result.getInt(5) +","+ result.getDate(6) +","+result.getInt(1)+"#");
			}



            ResultSet result2 = c.createStatement().executeQuery("select device.device_id,device.device_wording,device.room_id from device inner join reservation on reservation.reservation_id=device.reservation_id inner join general_services_manager on general_services_manager.gs_manager_id=reservation.gs_manager_id inner join employee on employee.employee_id=general_services_manager.gs_manager_id where employee.company_id='" + id_company+"';");

            sb.append("//");

            while (result2.next()) {

                sb.append("device_id=" + result2.getInt(1) + ",device_wording=" + result2.getString(2) + ",room_id=" + result2.getInt(3) +"#");
            }

            output.println(sb.toString());



		}catch (Exception e){
			output.println("notgood");
			e.printStackTrace();
		}

	}



	public void getdevices(String values){
		logger.debug("bravo2");
		try {
			ObjectMapper mapper = new ObjectMapper(new JsonFactory());
			Map<String, Map<String, String>> map = mapper.readValue(values,
					new TypeReference<Map<String, Map<String, String>>>() {
					});




			ResultSet result = c.createStatement().executeQuery("select device.device_id,device.device_wording,device.room_id from device inner join reservation on reservation.reservation_id=device.reservation_id\n" +
					"inner join general_services_manager on general_services_manager.gs_manager_id=reservation.gs_manager_id\n" +
					"inner join employee on employee.employee_id=general_services_manager.gs_manager_id where employee.company_id='" +
					Integer.parseInt(map.get("getdevices").get("company_id"))+"';");



			StringBuilder sb = new StringBuilder();
			while (result.next()) {

				sb.append("device_id=" + result.getInt(1) + ",device_wording=" + result.getString(2) + ",room_id=" + result.getInt(3) +"#");
			}

			output.println(sb.toString());



		}catch (Exception e){
			output.println("notgood");
			e.printStackTrace();
		}

	}




	public void adddevicepermission(String values){
		logger.debug("test add3");


		try {
			ObjectMapper mapper = new ObjectMapper(new JsonFactory());
			Map<String, Map<String, String>> map = mapper.readValue(values,
					new TypeReference<Map<String, Map<String, String>>>() {
					});

			//int id_company=Integer.parseInt(map.get("requestadddevice").get("idcompany"));
			String namepermission=map.get("requestadddevice").get("permission");
			//logger.debug(namepermission);
			String device=map.get("requestadddevice").get("device");
			//logger.debug(device);

			String dev[]=device.split(",");
			int idpermission=Integer.parseInt(map.get("requestadddevice").get("idpermission"));
			//logger.debug(" waw "+idpermission);
			int iddevice=Integer.parseInt(getNbr(dev[0]));


			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date today = dateFormat.parse(dateFormat.format(new Date()));

			int x=3;

			String requestInsert = "insert into permission_device (device_id,permission_id,equipement_validity_period,number_validity_use) values ('"+    iddevice     +"','"+  idpermission   +    " ','"+     today     +"','"+  x    +"'                                   ) ;";


			 c.createStatement().executeUpdate(requestInsert);





			output.println("bravo");



		}catch (Exception e){
			output.println("notgood");
			e.printStackTrace();
		}

	}





	public void getPermissions(String values){
		logger.debug("bravo2");
		try {
			ObjectMapper mapper = new ObjectMapper(new JsonFactory());
			Map<String, Map<String, String>> map = mapper.readValue(values,
					new TypeReference<Map<String, Map<String, String>>>() {
					});
			//int company = Integer.parseInt(map.get("getpermissions").get("company_id"));
			ResultSet result = c.createStatement().executeQuery("select permission_badge.permission_id,permission_badge.name_permission from permission_badge "+
					"where company_id='" +
					Integer.parseInt(map.get("getpermissions").get("company_id"))+"';");
			StringBuilder sb = new StringBuilder();
			while (result.next()) {

				sb.append("ID Permission:" + result.getInt(1) + ",Nom Permission:" + result.getString(2) +"#");
			}

			output.println(sb.toString());



		}catch (Exception e){
			output.println("notgood");
			e.printStackTrace();
		}

	}




	public void getallemployees(String values){
		logger.debug("requestallbadges test");
		try {
			ObjectMapper mapper = new ObjectMapper(new JsonFactory());
			Map<String, Map<String, String>> map = mapper.readValue(values,
					new TypeReference<Map<String, Map<String, String>>>() {
					});
			//int company = Integer.parseInt(map.get("requestallbadges").get("company_id"));
			ResultSet result = c.createStatement().executeQuery("select  badge.badge_id,employee.employee_last_name,employee.employee_last_name from employee inner join badge on employee.employee_id=badge.employee_id where company_id='" + Integer.parseInt(map.get("requestallbadges").get("company_id"))+"';");
			StringBuilder sb = new StringBuilder();
			while (result.next()) {

				sb.append("ID Badge:" + result.getInt(1) + ",Nom:" + result.getString(2) +",Prenom:" + result.getString(2) +"#");
			}

			output.println(sb.toString());



		}catch (Exception e){
			output.println("notgood");
			e.printStackTrace();
		}

	}



	public void firstPage( String values) {
		try {
			ObjectMapper mapper = new ObjectMapper(new JsonFactory());
			Map<String, Map<String, String>> map = mapper.readValue(values,
					new TypeReference<Map<String, Map<String, String>>>() {
					});
			ResultSet result = c.createStatement().executeQuery("select company_name,company_id from company " +
					"where company_name = '"+ map.get("homePage1").get("company_name") +"';");


			if(result.next()) {
				String data = result.getString(1)+ ","+result.getString(2);
				output.println(data);
			} else output.println("false,");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void saveallemployees( String values) {
		logger.debug("test save all employees2");
		try {
			ObjectMapper mapper = new ObjectMapper(new JsonFactory());
			Map<String, Map<String, String>> map = mapper.readValue(values,
					new TypeReference<Map<String, Map<String, String>>>() {
					});


			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

			int idcompany =Integer.parseInt(map.get("requestManyNewBadge").get("company_id"));
			logger.debug("waaw"+idcompany);
			String permission=map.get("requestManyNewBadge").get("permission");

			String per[]=permission.split(",");
			int idpermission=Integer.parseInt(getNbr(per[0]));
			//String employees=map.get("requestManyNewBadge").get("permission");
			logger.debug("waaw"+idpermission);
			String empls =map.get("requestManyNewBadge").get("employees");
			String [] employees = empls.split("&");

			Date datepermission = dateFormat.parse(map.get("requestManyNewBadge").get("date"));

			logger.debug("waaw"+empls);
			logger.debug("waaw2"+employees[0]);

			logger.debug("llee"+employees.length);



			for (int i = 0; i < employees.length; i++) {
				logger.debug(employees[i]);
				String emp[]=employees[i].split(",");
				//int idbadge=Integer.parseInt(getNbr(emp[0]));
				String idbadge=emp[0].substring(9,15);
				logger.debug("sdss"+idbadge);
				String insertrequest = "update permission_access set permission_id='" + idpermission + " ', permission_validity_period='" + datepermission +" 'where badge_id like'"+ idbadge +  "'                    ;";

				try {
					c.createStatement().executeUpdate(insertrequest);
				}catch (Exception e){
					output.println("notgood save");
				}

			}







		} catch (Exception e) {
			e.printStackTrace();
		}
	}






	public void testpermissions( String values) {
		logger.debug("tesssssttttt");
		try {
			ObjectMapper mapper = new ObjectMapper(new JsonFactory());
			Map<String, Map<String, String>> map = mapper.readValue(values,
					new TypeReference<Map<String, Map<String, String>>>() {
					});

			String name1 =map.get("testpermissions").get("employee_last_name");
			String name2=map.get("testpermissions").get("employee_first_name");
			String device=map.get("testpermissions").get("device_id");

			String per[]=device.split(",");
			int device_id=Integer.parseInt(getNbr(per[0]));

			System.out.println(device_id);



			ResultSet result = c.createStatement().executeQuery("select employee.employee_id from employee inner join badge on badge.employee_id=employee.employee_id inner join permission_access on\n" +
					"permission_access.badge_id=badge.badge_id inner join permission_badge on permission_badge.permission_id=permission_access.permission_id inner join\n" +
					"permission_device on permission_device.permission_id=permission_badge.permission_id where (employee_last_name='"+ name1  +"' and employee_first_name='"+name2+  "' and device_id='"+device_id+ "'                 );");

			System.out.println("bravo");

			if(result.next()) {
				//String data = result.getString(1);
				output.println("Good");
			} else output.println("Notgood");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
