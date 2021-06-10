package episen.si.ing1.pds.client.Indicators.tools;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.List;
import java.util.Map;

public class SensorsTools {
    public static List<Map> getSensorsSortedByMostUsedType() {
        Request request = new Request();
        request.setRequestEvent("sensors_sorted_by_most_used");

        Response response = Tools.sendToSocket(request);
        return (List<Map>) response.getResponseBody();
    }

    public static List<Map> getCompaniesList() {
        Request request = new Request();
        request.setRequestEvent("company_list");

        Response response = Tools.sendToSocket(request);
        return (List<Map>) response.getResponseBody();
    }

    public static List<Map> getMappingRate(int companyId) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("company_id", companyId);

        Request request = new Request();
        request.setRequestEvent("mapping_rate");
        request.setRequestBody(node);

        Response response = Tools.sendToSocket(request);
        return (List<Map>) response.getResponseBody();

    }
}
