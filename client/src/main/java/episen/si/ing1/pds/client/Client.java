package episen.si.ing1.pds.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.cli.*;
import java.sql.*;
import javax.swing.*;



public class Client {

	private final static Logger logger = LoggerFactory.getLogger(Client.class.getName());

    public static void main(String[] args) throws Exception {
        ConnectionDB connection = new ConnectionDB("org.postgresql.Driver", "jdbc:postgresql://localhost:5432/pds", "postgres", "0000");
        connection.connect();
        Frame f1 = new Frame(connection);

        final Options options = new Options();
        final CommandLineParser clp = new DefaultParser();
        final Option testmode = Option.builder().longOpt("testmode").build();
        options.addOption(testmode);
        final CommandLine commandLine = clp.parse(options, args) ;
        boolean testmodeV = false;
        if (
                commandLine.hasOption("testmode")
        )
            testmodeV = true;
        logger.info("Client is running with testmode = {}",testmodeV);



        /* test requete */
        ResultSet rs = connection.execute("select * from entreprise");
        String result = "";
        while(rs.next()) {
            result = "voici le nom de l'entreprise : " + rs.getString("libelle_entreprise");
            System.out.println("voici le nom de l'entreprise : " + rs.getString("libelle_entreprise"));
        }

        f1.getContentPane().add(new JLabel(result));
        f1.setVisible(true);

    }
}


