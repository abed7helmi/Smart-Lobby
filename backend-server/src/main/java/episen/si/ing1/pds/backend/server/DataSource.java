package episen.si.ing1.pds.backend.server;

import java.sql.Connection;

public class DataSource {

	private static JDBCConnectionPool connectionPool;

	public DataSource(int nbConnection) {
		connectionPool = JDBCConnectionPool.getInstance(nbConnection);
	}
	
	public Connection send() {
		synchronized (connectionPool) {
			while (true) {
				if (connectionPool.Pool.size() == 0) {
					try {
						connectionPool.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} else {
					return connectionPool.sendConnection();
				}
			}
		}
	}

	public void receive(Connection c) {
		synchronized (connectionPool) {
			connectionPool.receiveConnection(c);
			connectionPool.notifyAll();
		}
	}

	public void closeConnection() {
		connectionPool.closeAllConnection();
	}

	public JDBCConnectionPool test() {
		return connectionPool;
	}
}