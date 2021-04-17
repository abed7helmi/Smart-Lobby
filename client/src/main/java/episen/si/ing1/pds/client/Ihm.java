package episen.si.ing1.pds.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
        Reservation reservation = new Reservation();
        reservation.realizeReservation();


    }
}
