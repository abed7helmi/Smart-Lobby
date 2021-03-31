package episen.si.ing1.pds.backend.server;

import java.sql.Connection;

public class DataSource {

	private static JDBCConnectionPool connectionPool;

	public DataSource(int nbConnection) {
		connectionPool = JDBCConnectionPool.getInstance(nbConnection);
	}
	
	//send a connection to the user and remove it from the pool
	public Connection send() {
		synchronized (connectionPool) {
			while (true) {
				if (connectionPool.Pool.size() == 0) {
					throw new Error("No more connection available, waiting.");
				} 
				return connectionPool.sendConnection();
			}
		}
	}

	//receive a connection from the user and add it to the pool
	public void receive(Connection c) {
		synchronized (connectionPool) {
			connectionPool.receiveConnection(c);
		}
	}

	//close all connection, when all users are done using them
	public void closeConnection() {
		connectionPool.closeAllConnection();
	}
}