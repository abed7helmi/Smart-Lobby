
package episen.si.ing1.pds.backend.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.cli.*;

import java.io.InputStream;
import java.util.Properties;

public class BackendServer {

    private final static Logger logger = LoggerFactory.getLogger(BackendServer.class.getName());

    public static void main(String[] args) throws Exception {
        final Options options = new Options();

        final Option testmode = Option.builder().longOpt("testmode").build();
        final Option numberConnection = Option.builder().longOpt("max_connection").hasArg().argName("max_connection").build();

        options.addOption(testmode);
        options.addOption(numberConnection);


        final CommandLineParser clp = new DefaultParser();
        final CommandLine commandLine = clp.parse(options, args);

        boolean testmodeV = false;

        InputStream inStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties");
        Properties props = new Properties();
        try {
            props.load(inStream);
            int maxConnection = Integer.parseInt(props.getProperty("server.maxConnection"));
            if (commandLine.hasOption("testmode")) {
                testmodeV = true;
            }

            if (commandLine.hasOption("max_connection")) {
                maxConnection = Integer.parseInt(commandLine.getOptionValue("max_connection"));
            }

            logger.info("BackendServer is running with (testmode = {}), max_connection = {}.",
                    testmodeV, maxConnection);

        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }
}