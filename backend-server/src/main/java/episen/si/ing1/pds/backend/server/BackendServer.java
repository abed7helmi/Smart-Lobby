
package episen.si.ing1.pds.backend.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.cli.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

public class BackendServer {

	static DataSource d;
	static int NBCONNECTION;

	private final static Logger logger = LoggerFactory.getLogger(BackendServer.class.getName());

	public static void main(String[] args) {
		try {
			final Options options = new Options();

			final Option testmode = Option.builder().longOpt("testmode").build();
			final Option numberConnection = Option.builder().longOpt("max_connection").hasArg().argName("max_connection").build();

			options.addOption(testmode);
			options.addOption(numberConnection);

			final CommandLineParser clp = new DefaultParser();
			final CommandLine commandLine = clp.parse(options, args);

			Properties props = new Properties();
			props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties"));

			if (commandLine.hasOption("max_connection"))
				NBCONNECTION = Integer.parseInt(commandLine.getOptionValue("max_connection"));
			else
				NBCONNECTION = Integer.valueOf(props.getProperty("NBCONNECTION"));

			logger.info("BackendServer is running with (testmode = {}), max_connection = {}.", commandLine.hasOption("testmode"),NBCONNECTION);

			d = new DataSource(NBCONNECTION);
			//BackendServer bs = new BackendServer();
			//if(commandLine.hasOption("testmode")) bs.testConnection(d);
			//else bs.crud(d);
			
			ServerCore sc = new ServerCore(new ServerConfig());
			sc.serve(d);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//testing when there is more connection requested than available in the pool
	public void testConnection(DataSource d){
		ArrayList<Connection> activeConnections = new ArrayList<Connection>();
		Thread t1 = new Thread() {
			@Override
            public void run() {
				while(true) {
					logger.info("Number of active connections:"+activeConnections.size());
					activeConnections.add(d.send());
					logger.info("One user is connecting");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {}
				}
            }
		};
		Thread t2 = new Thread() {
			@Override
            public void run() {
				while(true) {
					try {
						Thread.sleep(10000);
					} catch (InterruptedException e) {}
					if(activeConnections.size()>0) {
					logger.info("One user is done using his connection, putting it back into the pool.");
					d.receive(activeConnections.get(0));
					activeConnections.remove(0);
					logger.info("Number of active connections:"+activeConnections.size());
					
					}
				}
            }
		};
		t1.start();
		t2.start();
	}

	//testing the crud with basic request on the database
	public void crud(DataSource d) throws SQLException {
		Connection c = d.send();
		c.setAutoCommit(true);
		Statement s = c.createStatement();
		Scanner sc = new Scanner(System.in);
		int input = 0;
		while (input != 5) {
			logger.info("\n1.select\n2.insert\n3.update\n4.delete\n5.exit");
			input = Integer.parseInt(sc.nextLine());
			switch (input) {
			case 1:
				logger.info(select(s));
				break;
			case 2:
				logger.info("Type the name to insert:");
				String name = sc.nextLine();
				insert(name, s);
				break;
			case 3:
				logger.info("Type the name to update:");
				String oldName = sc.nextLine();
				logger.info("Type the new name:");
				String newName = sc.nextLine();
				update(oldName, newName, s);
				break;
			case 4:
				logger.info("Type the name to delete:");
				String nameDeleted = sc.nextLine();
				delete(nameDeleted, s);
				break;
			}
		}
		sc.close();
		d.receive(c);
	}

	public int insert(String name, Statement s) throws SQLException {
		return s.executeUpdate("insert into test(name) values('" + name + "')");
	}

	public int update(String oldName, String newName, Statement s) throws SQLException {
		return s.executeUpdate("update test set name='" + newName + "' where name='" + oldName + "'");
	}

	public int delete(String name, Statement s) throws SQLException {
		return s.executeUpdate("delete from test where name='" + name + "'");
	}

	public String select(Statement s) throws SQLException {
		StringBuilder sb = new StringBuilder();
		ResultSet result = s.executeQuery("select * from test");
		while (result.next()) {
			sb.append("id=" + result.getInt(1) + "|name=" + result.getString(2) + "\n");
		}
		return sb.toString();
	}
}