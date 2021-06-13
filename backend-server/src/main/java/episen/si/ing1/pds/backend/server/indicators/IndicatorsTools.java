package episen.si.ing1.pds.backend.server.indicators;

import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class IndicatorsTools {
    private final Connection connection;

    public IndicatorsTools(Connection connection) {
        this.connection = connection;
    }

    public Map<String, Integer> randomRoomId() throws SQLException {
        String query = "SELECT room_id, room_price FROM room WHERE status like 'free' OFFSET random() * (SELECT count(*) from room WHERE status LIKE 'free') LIMIT 1";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(query);
        Map<String, Integer> hm = new LinkedHashMap<>();
        if (rs.next()) {
            hm.put("room_id", rs.getInt(1));
            hm.put("room_price", rs.getInt(2));
        }
        return hm;
    }


    public int createReservation(int price, int empId) throws SQLException {
        String query = "INSERT INTO reservation(start_date, end_date, price, gs_manager_id) VALUES (CURRENT_DATE,date('now') + interval '1 year', ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, price);
        statement.setInt(2, empId);
        statement.executeUpdate();
        ResultSet rs = statement.getGeneratedKeys();
        if (rs.next())
            return rs.getInt(1);
        else
            return 0;
    }

    public void injectRoomReservation(int roomID, int reservationID) throws SQLException {
        String query = "UPDATE room SET status = 'booked', reservation_id = ? WHERE room_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, reservationID);
        statement.setInt(2, roomID);

        statement.executeUpdate();
    }

    public int getRandomDeviceByType(int type) throws SQLException {
        String query = "SELECT device_id FROM device WHERE device_status LIKE 'free' AND device_type_id = ? OFFSET random() * (SELECT count(*) from device WHERE device_status LIKE 'free' AND device_type_id = ? ) LIMIT 1";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, type);
        statement.setInt(2, type);
        ResultSet rs = statement.executeQuery();
        if (rs.next())
            return rs.getInt(1);
        else
            return 0;
    }

    public void bookDeviceForRoom(int reservationID, int roomID, int deviceID) throws SQLException {
        String query = "UPDATE device SET device_status = 'booked', room_id = ?, reservation_id = ? WHERE device_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);

        statement.setInt(3, deviceID);
        statement.setInt(2, reservationID);
        statement.setInt(1, roomID);

        statement.executeUpdate();
    }

    public int findAndBookLocationByRoom(int roomID) throws SQLException {
        String query = "UPDATE location SET occupied_location = TRUE WHERE location_id = (SELECT location_id FROM location WHERE occupied_location is FALSE AND room_id = ? LIMIT 1)";
        PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, roomID);
        statement.executeUpdate();
        ResultSet rs = statement.getGeneratedKeys();
        if (rs.next())
            return rs.getInt(1);
        else
            return 0;
    }

    public void mapDeviceByDeviceID(int deviceID, int locationID) throws SQLException {
        String query = "UPDATE device SET device_placed = TRUE, location_id = ? WHERE device_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, locationID);
        statement.setInt(2, deviceID);
        statement.executeUpdate();
    }

    public void computeFPriceReservation(int reservationID) throws SQLException {
        String query = "UPDATE reservation SET price = price + (SELECT sum(device_price) FROM device WHERE reservation_id = ?) WHERE reservation_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, reservationID);
        statement.setInt(2, reservationID);
        statement.executeUpdate();
    }

}
