package episen.si.ing1.pds.backend.server;

import java.io.IOException;
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
			Connection c = d.send();
			try {
				final Socket socket = serverSocket.accept();
				logger.debug("Request received");
				final ClientRequestManager clientRequestManager = new ClientRequestManager(socket, c);
				clientRequestManager.getSelf().join();;
			} catch (SocketTimeoutException | InterruptedException e) {
				logger.debug("Request timeout");
			} finally {
				d.receive(c);
			}
		}
	}
}
