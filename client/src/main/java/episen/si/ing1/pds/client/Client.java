package episen.si.ing1.pds.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.cli.*;

import java.io.InputStream;
import java.util.Properties;

public class Client {

	private final static Logger logger = LoggerFactory.getLogger(Client.class.getName());

	public static void main(String[] args) {
		try {
			final Options options = new Options();

			final Option testmode = Option.builder().longOpt("testmode").build();
			final Option numberConnection = Option.builder().longOpt("max_connection").hasArg()
					.argName("max_connection").build();

			options.addOption(testmode);
			options.addOption(numberConnection);

			final CommandLineParser clp = new DefaultParser();
			final CommandLine commandLine = clp.parse(options, args);

			boolean testmodeV = false;

			InputStream inStream = Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("application.properties");
			Properties props = new Properties();

			props.load(inStream);
			if (commandLine.hasOption("testmode")) {
				testmodeV = true;
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
	}
}
