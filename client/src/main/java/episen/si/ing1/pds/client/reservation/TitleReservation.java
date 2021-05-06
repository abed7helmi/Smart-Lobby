package episen.si.ing1.pds.client.reservation;

import episen.si.ing1.pds.client.Ihm;
import javax.swing.*;
import java.awt.*;

public class TitleReservation {
    public JPanel titleReservation(){
        JPanel titlePage = new JPanel();
        titlePage.setLayout(null);
        Dimension dimTitle = new Dimension(950, 100);
        Ihm.sizeComposant(dimTitle, titlePage);
        titlePage.setBackground(Color.WHITE);

        JTextField title = new JTextField("Realisation d'une location : ", 50);
        title.setFont(new Font("Serif", Font.BOLD, 35));
        title.setHorizontalAlignment(JTextField.RIGHT);
        title.setBackground(Color.WHITE);
        title.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        title.setBorder(BorderFactory.createMatteBorder(0,1, 1, 0, Color.RED));
        title.setEditable(false);
        title.setBounds(500, 0, 420, 75);

        titlePage.add(title);

        return titlePage;
    }
}
