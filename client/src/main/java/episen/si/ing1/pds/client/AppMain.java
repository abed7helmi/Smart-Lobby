package episen.si.ing1.pds.client;



import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Properties;

public class AppMain {

    private final static Logger logger = LoggerFactory.getLogger(AppMain.class.getName());


    private static DataSource d;
    static int NBCONNECTION;

    public static void main(String[] args) throws Exception {
        final Options options = new Options();

        final Option numberConnection = Option.builder().longOpt("max_connection").hasArg().argName("max_connection").build();
        options.addOption(numberConnection);

        final CommandLineParser clp = new DefaultParser();
        final CommandLine commandLine = clp.parse(options, args);

        InputStream inStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("connection.properties");
        Properties props = new Properties();
        try {
            props.load(inStream);
            NBCONNECTION = Integer.parseInt(props.getProperty("NBCONNECTION"));
        } catch (Exception e) {
            logger.info(e.getMessage());
        }

        if (commandLine.hasOption("max_connection")) {
            NBCONNECTION = Integer.parseInt(commandLine.getOptionValue("max_connection"));
        }
        d = new DataSource(NBCONNECTION);

        Client a = new Client();
        Client b = new Client();
        Client z = new Client();
        Client l = new Client();
        Client e = new Client();
        Client f = new Client();

        a.test(d);
        b.test(d);
        z.test(d);
        l.test(d);
        e.test(d);

    }
}
