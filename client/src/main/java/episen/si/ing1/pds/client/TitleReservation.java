package episen.si.ing1.pds.client;

import javax.swing.*;
import java.awt.*;

public class TitleReservation {
    public JPanel titleReservation(){
        JPanel titlePage = new JPanel();
        titlePage.setLayout(null);
        Dimension dimTitle = new Dimension(950, 100);
        sizeComposant(dimTitle, titlePage);
        titlePage.setBackground(Color.WHITE);

        JTextField title = new JTextField("Realisation d'une location : ", 50);
        title.setFont(new Font("Serif", Font.BOLD, 35));
        title.setHorizontalAlignment(JTextField.RIGHT);
        title.setBackground(Color.WHITE);
        title.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        title.setBorder(BorderFactory.createMatteBorder(0,1, 1, 0, Color.RED));
        title.setEditable(false);
        title.setBounds(500, 0, 420, 75);

        //titlePage.add(flecheBack);
        titlePage.add(title);

        return titlePage;
    }

    public void sizeComposant(Dimension dim, Component c){
        c.setPreferredSize(dim);
        c.setMaximumSize(dim);
        c.setMinimumSize(dim);
    }
}
