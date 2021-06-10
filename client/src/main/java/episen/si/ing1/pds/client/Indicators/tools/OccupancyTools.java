package episen.si.ing1.pds.client.Indicators.tools;

import java.util.List;
import java.util.Map;

public class OccupancyTools {

    public static List<Map> getOccupancyByCompany() {
        Request request = new Request();
        request.setRequestEvent("occupancy_rate_by_company");

        Response response = Tools.sendToSocket(request);
        return (List<Map>) response.getResponseBody();
    }
    public static List<Map> getOccupancyByBuilding() {
        Request request = new Request();
        request.setRequestEvent("occupancy_rate_by_building");

        Response response = Tools.sendToSocket(request);
        return (List<Map>) response.getResponseBody();
    }

    public static String getGlobalOccupancyRate() {
        Request request = new Request();
        request.setRequestEvent("global_occupancy_rate");

        Response response = Tools.sendToSocket(request);
        Map data = (Map) response.getResponseBody();
        return (String) data.get("rate");
    }
}
