package episen.si.ing1.pds.client.Indicators.tools;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import episen.si.ing1.pds.client.Client;
import episen.si.ing1.pds.client.ClientConfig;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.net.Socket;

public class Tools {
    public static Response sendToSocket(Request request) {
        ObjectMapper mapper = new ObjectMapper(new JsonFactory());
        try {
            Field configField = Client.class.getDeclaredField("config");
            configField.setAccessible(true);
            ClientConfig config = (ClientConfig) configField.get(Client.class.newInstance());
            String requestSerialized = mapper.writeValueAsString(request);
            Socket socket = new Socket(config.getIpAddress(), config.getPort());
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            configField.setAccessible(false);

            output.println(requestSerialized);
            while (true) {
                String responseMsg = input.readLine();
                Response response = mapper.readValue(responseMsg, Response.class);
                if(response.getResponseEvent().equalsIgnoreCase(request.getRequestEvent()))
                    return response;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
