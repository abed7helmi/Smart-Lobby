package episen.si.ing1.pds.client;

import java.sql.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectionDB {
    private String DRIVER_NAME = "";
    private String DATABASE_URL =  "";
    private String user = "";
    private String password = "";
    private Connection connect = null;
    private Statement stmt = null;

    private final static Logger logger = LoggerFactory.getLogger(ConnectionDB.class.getName());

    public ConnectionDB(String driver, String url, String user, String psw){
        this.DRIVER_NAME = driver;
        this.DATABASE_URL = url;
        this.user = user;
        this.password = psw;
    }

    public boolean connect() {
        try{
            Class.forName(DRIVER_NAME);
            this.connect = DriverManager.getConnection(DATABASE_URL, user ,password);
            this.stmt = connect.createStatement();
            logger.info("connected");
            return true;
        }catch (Exception e){
            logger.info(e.getMessage());
        }
        return false;
    }

    public ResultSet execute(String sql) {
        try{
            ResultSet rs = this.stmt.executeQuery(sql);
            return rs;
        } catch (SQLException e){
            logger.info(e.getMessage());
        }
        return null;
    }

    public void insert(String sql){
        try{
            this.stmt.executeQuery(sql);
        } catch (SQLException e){
            logger.info(e.getMessage());
        }
    }


    public void close() {
        try{
            if(connect!= null) connect.close();
            logger.info("disconnected");
        } catch(Exception e) {
            logger.info(e.getMessage());
        }
    }
}
