package episen.si.ing1.pds.backend.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
					logger.debug(clientInput);
					String requestType = clientInput.split("#")[0];
					String values = clientInput.split("#")[1];

					returnChoice(values);

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

	public void returnChoice(String values){
		try {
			ObjectMapper mapper = new ObjectMapper(new JsonFactory());
			Map<String, Map<String, String>> map = mapper.readValue(values,
					new TypeReference<Map<String, Map<String, String>>>() {
					});
			String request = "select room_wording, floor_number, building_name, room_price as prix, room_id " +
					"from room r " +
					"inner join floor f on f.floor_id = r.floor_id " +
					"inner join building b on b.building_id = f.building_id " +
					"where room_id in "+
					"(select room_id " +
						"from room r " +
						"inner join floor f on f.floor_id = r.floor_id " +
						"inner join building b on b.building_id = f.building_id " +
						"where status = 'free' and room_type_id = 1 Limit " + Integer.parseInt(map.get("requestLocation1").get("numberOpenSpace")) + ") " +
						"or room_id in "+
					" (select room_id "+
						"from room r " +
						"inner join floor f on f.floor_id = r.floor_id " +
						"inner join building b on b.building_id = f.building_id " +
						"where status = 'free' and room_type_id = 3 Limit " + Integer.parseInt(map.get("requestLocation1").get("numberClosedOffice")) + ") "+
						"or room_id in "+
					"(select room_id " +
						"from room r " +
						"inner join floor f on f.floor_id = r.floor_id " +
						"inner join building b on b.building_id = f.building_id " +
						"where status = 'free' and room_type_id = 3 Limit " + Integer.parseInt(map.get("requestLocation1").get("numberClosedOffice")) + ") "+
						"or room_id in " +
					"(select room_id " +
						"from room r " +
						"inner join floor f on f.floor_id = r.floor_id " +
						"inner join building b on b.building_id = f.building_id " +
						"where status = 'free' and room_type_id = 2 Limit " + Integer.parseInt(map.get("requestLocation1").get("numberMeetingRoom")) + ") ;";
			ResultSet result = c.createStatement().executeQuery(request);
			StringBuilder data = new StringBuilder();
			while(result.next()){
					data.append("Room wording : " + result.getString(1) + " numero etage : " + result.getInt(2) +
							"nom bâtiment : " +result.getString(3)+ " prix : "+result.getString(4) + " room id : " +
							result.getInt(5));
			}
			output.println(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*public void firstPage(String requestType, String values) {
		logger.debug(requestType+"//" + values);
		try {
			if(requestType.equals("select")) {
				logger.debug("toto");
				ResultSet result = c.createStatement().executeQuery("select company_name from company where company_name = '"+ values +"';");
				if(result.next()) output.println(true);
				else
					output.println(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
}
