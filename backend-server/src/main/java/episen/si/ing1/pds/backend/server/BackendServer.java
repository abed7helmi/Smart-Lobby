package episen.si.ing1.pds.backend.server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.cli.*;

public class BackendServer {

    private final static Logger logger = LoggerFactory.getLogger(BackendServer.class.getName());

    public static void main(String[] args) throws Exception {
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
        logger.info("BackendServer is running with testmode = {}",testmodeV);
    }
}