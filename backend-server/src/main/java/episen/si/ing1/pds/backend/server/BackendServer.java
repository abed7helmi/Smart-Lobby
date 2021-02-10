
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
        final Option numberConnection = Option.builder().longOpt("numberConnection").hasArg().argName("numberConnection").build();

        options.addOption(testmode);
        options.addOption(numberConnection);


        final CommandLineParser clp = new DefaultParser();
        final CommandLine commandLine = clp.parse(options, args);

        boolean testmodeV = false;

        InputStream inStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties");
        Properties props = new Properties();
        try {
            props.load(inStream);
            int numberConnectionV = Integer.parseInt(props.getProperty("server.numberConnectionV"));
            if (commandLine.hasOption("testmode")) {
                testmodeV = true;
            }

            if (commandLine.hasOption("numberConnection")) {
                numberConnectionV = Integer.parseInt(commandLine.getOptionValue("numberConnection"));
            }

            logger.info("BackendServer is running with (testmode = {}), numberConnection = {}.",
                    testmodeV, numberConnectionV);

        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }
}