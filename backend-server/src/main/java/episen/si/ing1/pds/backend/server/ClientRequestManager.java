package episen.si.ing1.pds.backend.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
					logger.info(input.readLine());
					ResultSet rs = c.createStatement().executeQuery("select * from test");
					StringBuilder sb = new StringBuilder();
					while (rs.next()) {
						sb.append("id=" + rs.getInt(1) + "|name=" + rs.getString(2) + "\n");
					}
					logger.info(sb.toString());
					output.write("done");
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
