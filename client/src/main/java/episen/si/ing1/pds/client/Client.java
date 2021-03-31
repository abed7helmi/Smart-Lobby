package episen.si.ing1.pds.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public class Client {

	private final static Logger logger = LoggerFactory.getLogger(Client.class.getName());

	private static final String episenClientJson = "CLIENT_JSON";
	private static String episenClientFileLocation;
	private static final String configClient = "CONFIG_CLIENT";
    private static String episenClientConfig;
    private static ClientConfig config;
	
	public static void main(String[] args) {
		try {
			final Options options = new Options();
			final Option requesttype = Option.builder().longOpt("requesttype").hasArg().argName("requesttype").build();
			options.addOption(requesttype);
			
			final CommandLineParser clp = new DefaultParser();
			final CommandLine commandLine = clp.parse(options, args);
			
			Properties props = new Properties();
			props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("connection.properties"));
			
			episenClientConfig = System.getenv(configClient);
            final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            config = mapper.readValue(new File(episenClientConfig), ClientConfig.class);

            Socket socket = new Socket(config.getIpAddress(), config.getPort());
			
			PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			String requestType = commandLine.getOptionValue("requesttype");
			
			
			episenClientFileLocation = System.getenv(episenClientJson);
			String data = Files.readString(Path.of(episenClientFileLocation));
			output.println(requestType+"="+data);
			logger.info(input.readLine());
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
