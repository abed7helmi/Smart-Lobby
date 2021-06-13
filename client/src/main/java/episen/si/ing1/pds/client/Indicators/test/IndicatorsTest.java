package episen.si.ing1.pds.client.Indicators.test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import episen.si.ing1.pds.client.ClientConfig;
import episen.si.ing1.pds.client.Indicators.tools.Request;
import episen.si.ing1.pds.client.Indicators.tools.Response;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Map;

public class IndicatorsTest {
    final static Logger logger = LoggerFactory.getLogger(IndicatorsTest.class.getName());
    final static ObjectMapper mapper = new ObjectMapper();
    private static final String configClient = "CONFIG_CLIENT";
    private static String episenClientConfig;
    private static ClientConfig config;

    public static void main(String[] args) throws IOException {
        logger.info("Test started");

        episenClientConfig = System.getenv(configClient);
        final ObjectMapper ymapper = new ObjectMapper(new YAMLFactory());
        config = ymapper.readValue(new File(episenClientConfig), ClientConfig.class);

        Options options = new Options();

        Option testType = Option.builder()
                .longOpt("test-type")
                .argName("testType")
                .desc("Indicate the test type")
                .required()
                .hasArg()
                .build();
        options.addOption(testType);

        Option empIdOpt = Option.builder().longOpt("employee-id").hasArg().build();
        options.addOption(empIdOpt);

        Option paramsOpt = Option.builder()
                .longOpt("devices")
                .hasArg()
                .build();
        options.addOption(paramsOpt);

        Option reservationOpt = Option.builder().longOpt("reservation-id").hasArg().build();
        options.addOption(reservationOpt);


        Option deviceNOpt = Option.builder().longOpt("to-be-mapped").hasArg().build();
        options.addOption(deviceNOpt);

        DefaultParser parser = new DefaultParser();
        try {
            CommandLine cli = parser.parse(options, args);
            String tstType = cli.getOptionValue(testType.getLongOpt());
            if(tstType.equalsIgnoreCase("booking-mapping")){
                int empID = Integer.parseInt(cli.getOptionValue(empIdOpt.getLongOpt()));
                String paramsJson = cli.getOptionValue(paramsOpt.getLongOpt());

                logger.info("Params are : {}", paramsJson);

                ObjectNode node = mapper.createObjectNode();
                node.put("emp_id", empID);
                node.put("devices", mapper.readTree(paramsJson));

                Request request = new Request();
                request.setRequestEvent("test_reservation_mapping");
                request.setRequestBody(node);

                Response response = send(request);
                logger.info(mapper.writeValueAsString(response));
            } else if(tstType.equalsIgnoreCase("mapping")) {
                int reservationID = Integer.parseInt(cli.getOptionValue(reservationOpt.getLongOpt()));
                int qte = Integer.parseInt(cli.getOptionValue(deviceNOpt.getLongOpt()));

                ObjectNode node = mapper.createObjectNode();
                node.put("reservation_id", reservationID);
                node.put("qte", qte);

                Request request = new Request();
                request.setRequestEvent("test_mapping");
                request.setRequestBody(node);

                Response response = send(request);
                logger.info(mapper.writeValueAsString(response));

            } else {
                logger.warn("Test isn't found");
            }
        } catch (ParseException e) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("CLI Helper", options);
            logger.error("Error: " + e.getLocalizedMessage(), e);

        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
        }
    }

    private static Response send(Request request) throws Exception {
        String requestSerialized = mapper.writeValueAsString(request);
        Socket socket = new Socket(config.getIpAddress(), config.getPort());
        PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        output.println(requestSerialized);
        while (true) {
            String responseMsg = input.readLine();
            Response response = mapper.readValue(responseMsg, Response.class);
            if (response.getResponseEvent().equalsIgnoreCase(request.getRequestEvent()))
                return response;
        }
    }
}
