package episen.si.ing1.pds.backend.server;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

public class JDBCConnectionPool {

	static Properties props = new Properties();

	//the pool containing all the connection to the database
	protected ArrayList<Connection> Pool = new ArrayList<Connection>();
	//singleton instance of the class
	private static JDBCConnectionPool connectionPool = new JDBCConnectionPool();

	private JDBCConnectionPool() {
		try {
			props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties"));
		} catch (IOException e) {e.printStackTrace();}
	}

	public static JDBCConnectionPool getInstance(int nbConnection) {
		connectionPool.initPool(nbConnection);
		return connectionPool;
	}

	//Initializing the connections and adding them to the pool
	public void initPool(int nbConnection) {
		try {
			Class.forName(props.getProperty("DRIVER_NAME"));
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		for (int i = 0; i < nbConnection; i++) {
			try {
				Connection c = DriverManager.getConnection(props.getProperty("DATABASE_URL"), props.getProperty("USERNAME"), props.getProperty("PASSWORD"));
				c.setAutoCommit(false);
				Pool.add(c);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public Connection sendConnection() {
		if (Pool.size() < 1) {
			try {
				throw new Exception("Plus de connexion dispo");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Connection c = Pool.get(Pool.size() - 1);
		Pool.remove(Pool.size() - 1);
		return c;
	}

	public void receiveConnection(Connection c) {
		Pool.add(c);
	}

	public void closeAllConnection() {
		for (Connection c : Pool)
			try {
				c.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
}