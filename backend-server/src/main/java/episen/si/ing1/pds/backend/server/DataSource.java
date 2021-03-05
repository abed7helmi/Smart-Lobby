package episen.si.ing1.pds.backend.server;

import java.sql.Connection;
import java.sql.SQLException;

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
					try {
						System.out.println("No more connection available, waiting.");
						connectionPool.wait(10000);
						return connectionPool.additionnalConnection();
					} catch (InterruptedException e) {e.printStackTrace();
					} catch (SQLException e) {e.printStackTrace();}
				} else {
					return connectionPool.sendConnection();
				}
			}
		}
	}

	//receive a connection from the user and add it to the pool
	public void receive(Connection c) {
		synchronized (connectionPool) {
			connectionPool.receiveConnection(c);
			connectionPool.notifyAll();
		}
	}

	//close all connection, when all users are done using them
	public void closeConnection() {
		connectionPool.closeAllConnection();
	}
}