package episen.si.ing1.pds.backend.server;

public class DataBaseCoreConfig {
	 	private String driverName;
	    private String dataBaseUrl;
	    private String userName;
	    private String password;
	    private int nbConnection;

	    public String getDriverName() {
	        return driverName;
	    }

	    public void setDriverName(String driverName) {
	        this.driverName = driverName;
	    }

	    public String getDataBaseUrl() {
	        return dataBaseUrl;
	    }

	    public void setDataBaseUrl(String dataBaseUrl) {
	        this.dataBaseUrl = dataBaseUrl;
	    }

	    public String getUserName() {
	        return userName;
	    }

	    public void setUserName(String userName) {
	        this.userName = userName;
	    }

	    public String getPassword() {
	        return password;
	    }

	    public void setPassword(String password) {
	        this.password = password;
	    }

	    public int getNbConnection() {
	        return nbConnection;
	    }

	    public void setNbConnection(int nbConnection) {
	        this.nbConnection = nbConnection;
	    }



	    @Override
	    public String toString() {
	        return "DataBaseCoreConfig{" +
	                "driverName='" + driverName + "'" +
	                ", dataBaseUrl='" + dataBaseUrl + "'" +
	                ", userName='" + userName + "'" +
	                ", password='" + password + "'" +
	                ", nbConnection=" + nbConnection +
	                '}';
	    }
}
