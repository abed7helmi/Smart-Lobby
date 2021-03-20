package episen.si.ing1.pds.backend.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ClientRequestManager {

	private final static Logger logger = LoggerFactory.getLogger(ClientRequestManager.class);
	private final PrintWriter output;
	private final BufferedReader input;
	private Connection c;
	private String name = "client-thread-manager";
	private Thread self;

	public ClientRequestManager(Socket socket, Connection connection) throws IOException, SQLException {
		c = connection;
		output = new PrintWriter(socket.getOutputStream(),true);
		input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		self = new Thread(name) {
			@Override
			public void run() {
				try {
					String value = input.readLine();
					ObjectMapper mapper = new ObjectMapper(new JsonFactory());
					Map<String, Object> map = mapper.readValue(value, new TypeReference<Map<String,Object>>(){});
					output.println(map.toString());
				} catch (IOException e) {
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
