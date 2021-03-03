package episen.si.ing1.pds.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.cli.*;
import java.sql.*;
import java.util.Scanner;

public class Client {

	private final static Logger logger = LoggerFactory.getLogger(Client.class.getName());

	public void test(DataSource d) throws SQLException {
		Connection c = d.send();
		c.setAutoCommit(true);
		Statement s = c.createStatement();
		Scanner sc = new Scanner(System.in);
		int input=0;
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
				update(oldName,newName, s);
				break;
			case 4:
				logger.info("Type the name to delete:");
				String nameDeleted = sc.nextLine();
				delete(nameDeleted, s);
				break;
			}
		}
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
