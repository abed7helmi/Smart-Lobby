package episen.si.ing1.pds.client;

import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

public class JDBCConnectionPool {


    final String DRIVER_NAME;
    final String DATABASE_URL;
    final String USERNAME;
    final String PASSWORD;

    protected ArrayList<Connection> Pool = new ArrayList<Connection>();


    public JDBCConnectionPool(){
        Properties props = new Properties();
        try {
            props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("connection.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }


        DRIVER_NAME = props.getProperty("DRIVER_NAME");
        DATABASE_URL = props.getProperty("DATABASE_URL");
        USERNAME = props.getProperty("USERNAME");
        PASSWORD = props.getProperty("PASSWORD");
    }

    public void initPool(int NBCONNECTION){
        System.out.println(NBCONNECTION+"lala");
        try {
            Class.forName(DRIVER_NAME);
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }

        for(int i=0;i<NBCONNECTION;i++) {
            try {
                Connection c = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
                c.setAutoCommit(false);
                Pool.add(c);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Pool.size());
    }

    public Connection sendConnection() {
        if(Pool.size()<1) {
            try {
                throw new Exception("Plus de connexion dispo");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Connection c = Pool.get(Pool.size()-1);
        Pool.remove(Pool.size()-1);
        return c;
    }

    public void receiveConnection(Connection c) {
        Pool.add(c);
    }

    public void closeAllConnection(){
        for(Connection c : Pool)
            try {
                c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }
}

