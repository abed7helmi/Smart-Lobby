package episen.si.ing1.pds.client;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

public class Mapping {
    private JPanel pageBody = new JPanel();

    public JPanel mappingPanel() {

        pageBody.setBackground(Color.RED);

        pageBody.setBackground(Color.white);
        pageBody.setLayout(new GridLayout(3, 1));

        JLabel title = new JLabel("Sélectionnez un emplacement à configurer:");
        pageBody.add(title);

        return pageBody;
    }
}