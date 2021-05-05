package episen.si.ing1.pds.backend.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerCore {

	private ServerSocket serverSocket;
	private final static Logger logger = LoggerFactory.getLogger(ServerCore.class);

	public ServerCore(final ServerConfig config) throws IOException {
		serverSocket = new ServerSocket(config.getConfig().getListenPort());
		serverSocket.setSoTimeout(config.getConfig().getTimeOut());
	}

	public void serve(DataSource d) throws IOException, SQLException {
		while (true) {
			try {
				final Socket socket = serverSocket.accept();
				logger.debug("Request received");

				try {
					Connection c = d.send();
					final ClientRequestManager clientRequestManager = new ClientRequestManager(socket, c);
					clientRequestManager.getSelf().join();
					d.receive(c);
				}catch (InterruptedException e) {
					e.printStackTrace();
				}catch(Exception e) {
					PrintWriter output = new PrintWriter(socket.getOutputStream(),true);
					output.println(e.getMessage());
				}
			} catch (SocketTimeoutException e) {
				logger.debug("Request timeout");
				Thread.currentThread().interrupt();
				break;
			}
		}
	}
}
