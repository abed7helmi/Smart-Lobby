package episen.si.ing1.pds.backend.server;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerConfig {
	
	private final static Logger logger = LoggerFactory.getLogger(ServerConfig.class);
	private static final String episenServerConfigEnvVar = "EPISEN_SRV_CONFIG";
	private final String episenServerConfigFileLocation;
	private ServerCoreConfig config;
	
	public ServerConfig() throws IOException {
		episenServerConfigFileLocation = System.getenv(episenServerConfigEnvVar);
		logger.debug("Config file = {}", episenServerConfigFileLocation);
		final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		config = mapper.readValue(new File(episenServerConfigFileLocation), ServerCoreConfig.class);
		logger.debug("Config = {}", config.toString());
	}
	public ServerCoreConfig getConfig() {
		return config;
	}
}
