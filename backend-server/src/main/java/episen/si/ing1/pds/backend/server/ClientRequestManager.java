package episen.si.ing1.pds.backend.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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
		output = new PrintWriter(socket.getOutputStream(),true);
		input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		self = new Thread(name) {
			@Override
			public void run() {
				try {
					String clientInput = input.readLine();

					String requestType = clientInput.split("=")[0];
					String values = clientInput.split("=")[1];
					ObjectMapper mapper = new ObjectMapper(new JsonFactory());

					switch(requestType) {
						case "select":
							StringBuilder sb = new StringBuilder();
							ResultSet result = c.createStatement().executeQuery("select * from test");
							while (result.next()) {
								sb.append("id=" + result.getInt(1) + ",name=" + result.getString(2) +
										",age=" + result.getString(3) + "|");
							}
							output.println(sb.toString());
	                    break;
						case "insert":
							Map<String, Map<String,String>> map = mapper.readValue(values, new TypeReference<Map<String,Map<String,String>>>(){});
							StringBuilder request = new StringBuilder();
							request.append("insert into test (name, age) values");
							for(Map<String,String> m: map.values()) request.append("('"+m.get("name")+"','"+m.get("age")+"'),");
							request.deleteCharAt(request.length()-1);
							request.append(";");

							output.println("Successfully inserted "+c.createStatement().executeUpdate(request.toString())+" rows.");
	                    break;
						case "delete":
							output.println("Successfully deleted "+c.createStatement().executeUpdate("delete from test")+" rows.");
	                    break;
						case "update":
							StringBuilder requestUpdate = new StringBuilder();
							requestUpdate.append("update test set ");
							Map<String, String> mapUpdate = mapper.readValue(values, new TypeReference<Map<String, String>>(){});
							requestUpdate.append(mapUpdate.get("set") + " = '" + mapUpdate.get(mapUpdate.get("set"))+ "' where id = " + mapUpdate.get("id") + ";");
							output.println("Successfully inserted "+c.createStatement().executeUpdate(requestUpdate.toString())+" rows.");
						break;
					}
				} catch (IOException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		};
		self.start();
	}
	
	public Thread getSelf() {
		return self;
	}
}
