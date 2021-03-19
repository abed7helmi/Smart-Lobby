package episen.si.ing1.pds.backend.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.sql.Connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerCore {

	private ServerSocket serverSocket;
	private final static Logger logger = LoggerFactory.getLogger(ServerCore.class);
	
	
	public ServerCore(final ServerConfig config) throws IOException {
		serverSocket = new ServerSocket(config.getConfig().getListenPort());
		serverSocket.setSoTimeout(config.getConfig().getTimeOut());
	}
	
	public void serve(DataSource d) throws IOException {
		try {
			final Socket socket = serverSocket.accept();
			logger.debug("Request received");
			Connection connection = d.send();
			logger.debug("Connection established");
			final ClientRequestManager clientRequestManager = new ClientRequestManager(socket, connection);
		}catch (SocketTimeoutException e) {
			logger.debug("Request timeout");
		}
	}
}
