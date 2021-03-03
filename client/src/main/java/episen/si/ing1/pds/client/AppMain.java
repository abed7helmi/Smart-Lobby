package episen.si.ing1.pds.client;

import org.apache.commons.cli.*;

public class AppMain {

    static DataSource d;
    static int NBCONNECTION=-1;

    public static void main(String[] args) throws Exception {
        final Options options = new Options();

        final Option numberConnection = Option.builder().longOpt("max_connection").hasArg().argName("max_connection").build();
        options.addOption(numberConnection);

        final CommandLineParser clp = new DefaultParser();
        final CommandLine commandLine = clp.parse(options, args);

        if (commandLine.hasOption("max_connection")) NBCONNECTION = Integer.parseInt(commandLine.getOptionValue("max_connection"));
        
        d = new DataSource(NBCONNECTION);
        Client a = new Client();
        a.test(d);
    }
}