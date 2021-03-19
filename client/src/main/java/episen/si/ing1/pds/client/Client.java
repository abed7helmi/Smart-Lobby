package episen.si.ing1.pds.client;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.cli.*;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

import java.util.Map;
import java.util.Properties;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.simple.JSONObject;


public class Client {

	private final static Logger logger = LoggerFactory.getLogger(Client.class.getName());
	private static final String episenClientJson = "CLIENT_JSON";
	private static String episenClientFileLocation;

	public static void main(String[] args) {

		try {
			final Options options = new Options();
			final Option crudMode = Option.builder().longOpt("crud").hasArg().argName("crud").build();

			options.addOption(crudMode);

			final CommandLineParser clp = new DefaultParser();
			final CommandLine commandLine = clp.parse(options, args);

			Properties props = new Properties();

			props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("connection.properties"));
			InetAddress ipAddress = InetAddress.getByName(props.getProperty("IPADDRESS"));
			Socket socket = new Socket(ipAddress,Integer.valueOf(props.getProperty("PORT")));
			PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));


			episenClientFileLocation = System.getenv(episenClientJson);
			ObjectMapper mapper = new ObjectMapper(new JsonFactory());
			Map<String, String> map = mapper.readValue(new File(episenClientFileLocation),new TypeReference<Map<String,String>>(){});

			JSONObject jo = new JSONObject(map);
			System.out.println(jo+"");
			output.println(jo);


		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
	}
}
