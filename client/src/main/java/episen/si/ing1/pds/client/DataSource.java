package episen.si.ing1.pds.client;

import java.sql.Connection;

public class DataSource {

    private static JDBCConnectionPool connectionPool = new JDBCConnectionPool();

    public DataSource() {
        connectionPool.initPool();
    }
    public static Connection send(){
        synchronized (connectionPool) {
            return connectionPool.sendConnection();
        }
    }

    public static void receive(Connection c) {
        synchronized (connectionPool){
            connectionPool.receiveConnection(c);
        }
    }

    public static void close(){
        synchronized (connectionPool){
            connectionPool.closeAllConnection();
        }
    }
}
