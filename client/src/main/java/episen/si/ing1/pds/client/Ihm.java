package episen.si.ing1.pds.client;

import javax.swing.*;
import java.net.Socket;


public class Ihm extends JFrame {


    public Ihm(String name) {



        AcceuilPersonnel personnel = new AcceuilPersonnel(this);
        personnel.acceuil();


        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
