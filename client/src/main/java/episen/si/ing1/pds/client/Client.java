package episen.si.ing1.pds.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.cli.*;
import java.sql.*;



public class Client {

	private final static Logger logger = LoggerFactory.getLogger(Client.class.getName());

    private static Connection c = null;





    public static void main(String[] args) throws Exception {

        /*final Options options = new Options();
        final CommandLineParser clp = new DefaultParser();
        final Option testmode = Option.builder().longOpt("testmode").build();
        options.addOption(testmode);
        final CommandLine commandLine = clp.parse(options, args) ;
        boolean testmodeV = false;
        if (
                commandLine.hasOption("testmode")
        )
            testmodeV = true;
        logger.info("Client is running with testmode = {}",testmodeV);*/

    }

    public void test(DataSource d) throws SQLException{
        this.c =  d.send();
        this.c.setAutoCommit(true);
        Statement stmt = c.createStatement();

        //int testInsert = stmt.executeUpdate("Insert into test ()values ('lol')");



        ResultSet testSelect = stmt.executeQuery("select * from test2");
        while(testSelect.next()) {
            System.out.println(testSelect.getString(2));
        }

        //int testDelete = stmt.executeUpdate("Delete from testPds");
    }



    public void Ajout (String ch,DataSource d) throws SQLException {
        this.c = d.send();
        this.c.setAutoCommit(true);
        //logger.info(ch);

        String requete = "INSERT INTO test2(nom) VALUES (?)";
        try {


            PreparedStatement PreparedStmt = c.prepareStatement(requete);

            PreparedStmt.setString(1, ch);
            PreparedStmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


