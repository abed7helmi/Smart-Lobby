package episen.si.ing1.pds.client;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Properties;


public class Client {

	private final static Logger logger = LoggerFactory.getLogger(Client.class.getName());

	private static final String episenClientJson = "CLIENT_JSON";
	private static String episenClientFileLocation;
	private static final String configClient = "CONFIG_CLIENT";
	private static String episenClientConfig;
	private static ClientConfig config;
	public static Map<String, Map<String, String>> map;

	public static void main(String[] args) {
		try {
			final Options options = new Options();
			final Option requesttype = Option.builder().longOpt("requesttype").hasArg().argName("requesttype").build();
			options.addOption(requesttype);

			final CommandLineParser clp = new DefaultParser();
			final CommandLine commandLine = clp.parse(options, args);

			Properties props = new Properties();
			props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("connection.properties"));

			//Ihm ihm = new Ihm("Smart Lobby");
			HomePage homePage = new HomePage();

			//String requestType = commandLine.getOptionValue("requesttype");

			episenClientFileLocation = System.getenv(episenClientJson);
			String values = Files.readString(Path.of(episenClientFileLocation));

			ObjectMapper mapper = new ObjectMapper(new JsonFactory());
			map = mapper.readValue(values,
					new TypeReference<Map<String, Map<String, String>>>() {});


		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	public static String sendBd(String request){
		try{
			ObjectMapper objectMapper = new ObjectMapper();
			String data = objectMapper.writeValueAsString(map);

			//System.out.println(request+"#"+data);

			episenClientConfig = System.getenv(configClient);
			final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
			config = mapper.readValue(new File(episenClientConfig), ClientConfig.class);

			Socket socket = new Socket(config.getIpAddress(), config.getPort());

			PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			output.println(request+"#"+data);
			return input.readLine();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return "";
	}
}