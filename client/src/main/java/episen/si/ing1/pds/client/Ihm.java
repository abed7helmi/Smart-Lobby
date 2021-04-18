package episen.si.ing1.pds.client;

import javax.swing.*;
import java.net.Socket;


public class Ihm extends JFrame {
    private Socket socket;

    public Ihm(String name) {
        /*socket = s;
        try {
            output = new PrintWriter(socket.getOutputStream(), true);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        //HomePage home = new HomePage(this);
        //home.firstPage();


        ChoiceCriteria reservation = new ChoiceCriteria(this);
        reservation.realizeReservation();


        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
