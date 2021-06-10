package episen.si.ing1.pds.backend.server.indicators;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.sql.*;
import java.util.*;

public class RequestManager {
    private final Connection connection;
    private final Request request;

    private final ObjectMapper mapper = new ObjectMapper(new JsonFactory());
    private final Logger logger = LoggerFactory.getLogger(RequestManager.class.getName());

    public RequestManager(Connection connection, Request request) {
        this.connection = connection;
        this.request = request;
    }

    public void respond(PrintWriter output) throws Exception {
        String type = request.getRequestEvent();
        switch (type) {
            case "occupancy_rate_by_company":
                occupancyRateByCompany(output);
                break;
            case "occupancy_rate_by_building":
                occupancyRateByBuilding(output);
                break;
            case "sensors_sorted_by_most_used":
                sensorsSortedByMostUsedType(output);
                break;
            case "company_list":
                companiesList(output);
                break;
            case "mapping_rate":
                mappingRate(output);
                break;
            case "windows_by_company":
                windowsByCompany(output);
                break;
            case "badges_by_company":
                badgesByCompany(output);
                break;
            case "global_occupancy_rate":
                globalOccupancyRate(output);
                break;
        }
    }

    private void globalOccupancyRate(PrintWriter output) throws Exception {
        Map<String, String> lhm = new LinkedHashMap<>();
        String query = "SELECT ROUND((SELECT COUNT(*) FROM room WHERE status LIKE 'booked')::numeric / (SELECT COUNT(*) FROM room)::numeric *100, 2) ";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            lhm.put("rate", rs.getDouble(1) + " %");
        }

        sendReponse(output, lhm);

    }

    private void badgesByCompany(PrintWriter output) throws Exception {
        List<Map<String, String>> list = new LinkedList<>();
        String query = "SELECT c.company_name, COUNT(b.badge_id) qte " +
                "FROM company c " +
                "LEFT JOIN employee e on c.company_id = e.company_id " +
                "LEFT JOIN badge b on b.employee_id = e.employee_id " +
                "GROUP BY c.company_id " +
                "ORDER BY qte DESC ";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            Map<String, String> lhm = new LinkedHashMap<>();
            lhm.put("Entreprise", rs.getString("company_name"));
            lhm.put("Nombre de badges actifs", rs.getInt("qte") + " ");
            list.add(lhm);
        }

        sendReponse(output, list);

    }
    private void windowsByCompany(PrintWriter output) throws Exception {
        List<Map<String, String>> list = new LinkedList<>();
        String query = "SELECT c.company_name, COUNT(d.device_id) as qte " +
                "FROM company c " +
                "LEFT JOIN employee e on e.company_id = c.company_id " +
                "LEFT JOIN reservation rt on e.employee_id = rt.gs_manager_id " +
                "LEFT JOIN device d on (rt.reservation_id = d.reservation_id AND d.device_type_id = 5) " +
                "GROUP BY c.company_id " +
                "ORDER BY qte DESC";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            Map<String, String> lhm = new LinkedHashMap<>();
            lhm.put("Entreprise", rs.getString("company_name"));
            lhm.put("Quantite de fenetres electrochromatiques", rs.getInt("qte") + " ");
            list.add(lhm);
        }

        sendReponse(output, list);
    }
private void mappingRate(PrintWriter output) throws Exception {
        List<Map<String, String>> list = new LinkedList<>();
        JsonNode node = request.getRequestBody();
        int companyId = node.get("company_id").asInt();
        String query = "SELECT " +
                "concat('No', rt.reservation_id ,' /', rt.start_date,'/', rt.end_date) as reservation," +
                "ROUND(((SELECT count(*) from device d2 WHERE d2.reservation_id = rt.reservation_id AND d2.device_placed is TRUE)::NUMERIC / (SELECT count(*) from device d2 WHERE d2.reservation_id = rt.reservation_id)::NUMERIC), 2) as rate " +
                "FROM device d " +
                "JOIN reservation rt on rt.reservation_id = d.reservation_id " +
                "JOIN employee e on e.employee_id = rt.gs_manager_id " +
                "JOIN company c on c.company_id = e.company_id " +
                "WHERE c.company_id = ? " +
                "GROUP BY rt.reservation_id ORDER BY rate DESC";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, companyId);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            Map<String, String> lhm = new LinkedHashMap<>();
            lhm.put("Reservation", rs.getString("reservation"));
            lhm.put("Taux de remplissage", (rs.getDouble("rate") * 100) + " %");
            list.add(lhm);
        }

        sendReponse(output, list);
    }

    private void companiesList(PrintWriter output) throws Exception {
        List<Map<String, Object>> list = new LinkedList<>();
        String query = "SELECT * FROM company";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            Map<String, Object> lhm = new LinkedHashMap<>();
            lhm.put("id", rs.getInt("company_id"));
            lhm.put("name", rs.getString("company_name"));
            list.add(lhm);
        }

        sendReponse(output, list);
    }


    private void sensorsSortedByMostUsedType(PrintWriter output) throws Exception {
        List<Map<String, String>> list = new LinkedList<>();
        String query = "SELECT dt.device_type_wording, count(dt.device_type_id) as nb " +
                "FROM device d " +
                "LEFT JOIN device_type dt on d.device_type_id = dt.device_type_id " +
                "WHERE d.device_status LIKE 'booked' " +
                "GROUP by (dt.device_type_id) " +
                "ORDER BY nb DESC";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            Map<String, String> lhm = new LinkedHashMap<>();
            lhm.put("Type", rs.getString(1));
            lhm.put("Nombre", rs.getInt(2) + "");
            list.add(lhm);
        }

        sendReponse(output, list);
    }

    private void occupancyRateByBuilding(PrintWriter output) throws Exception {
        List<Map<String, String>> list = new LinkedList<>();
        String query = "SELECT b.building_name, ROUND(SUM((SELECT count(*) FROM room NATURAL JOIN floor NATURAL JOIN building bl where bl.building_id = b.building_id AND status LIKE 'booked')::numeric) / SUM((SELECT count(*) FROM room NATURAL JOIN floor NATURAL JOIN building bl where bl.building_id = b.building_id)) * 100, 2) as rate FROM building b GROUP BY (b.building_id) ORDER BY rate DESC";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            Map<String, String> lhm = new LinkedHashMap<>();
            lhm.put("Batiment", rs.getString(1));
            lhm.put("Taux d'occupation", rs.getDouble(2) + " %");
            list.add(lhm);
        }

        sendReponse(output, list);

    }

    private void occupancyRateByCompany(PrintWriter output) throws Exception {
        List<OccupancyByCompany> list = new LinkedList<>();
        String query = "SELECT c.company_name, ROUND(((count(r.room_id)) / (SELECT count(*) FROM room)::numeric) * 100, 2) as rate FROM company c LEFT JOIN employee e on e.company_id = c.company_id LEFT JOIN reservation rt on rt.gs_manager_id = e.employee_id LEFT JOIN room r on rt.reservation_id = r.reservation_id GROUP by (c.company_id) ORDER BY rate DESC";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            OccupancyByCompany obc = new OccupancyByCompany(rs.getString(1), rs.getDouble(2) + " %");
            list.add(obc);
        }

        sendReponse(output, list);
    }

    private Response responseFactory(Object data) {
        Response response = new Response();
        response.setResponseEvent(request.getRequestEvent());
        response.setResponseBody(data);
        return response;
    }

    private void sendReponse(PrintWriter output, Object data) throws JsonProcessingException {
        Response response = responseFactory(data);
        String serializedResponse = mapper.writeValueAsString(response);
        logger.info("Serialized response sent to client {}", serializedResponse);
        output.println(serializedResponse);
    }
}
