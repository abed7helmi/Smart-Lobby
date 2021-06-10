package episen.si.ing1.pds.client.Indicators;

import episen.si.ing1.pds.client.HomePage;
import episen.si.ing1.pds.client.Indicators.views.Badges;
import episen.si.ing1.pds.client.Indicators.views.OccupancyRate;
import episen.si.ing1.pds.client.Indicators.views.Sensors;
import episen.si.ing1.pds.client.Indicators.views.Windows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Indicators {
    private JPanel pane = new JPanel();
    private final JFrame frame;
    private final JDialog dialog;

    public Indicators(JFrame frame) {
        this.frame = frame;
        this.dialog = new JDialog(frame);
    }

    public JPanel getIndicator() {
        pane = new JPanel(new BorderLayout());

        JPanel header = new JPanel(new BorderLayout());
        JButton returnBtn = new JButton("Retour");
        returnBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(frame != null)
                    frame.dispose();
                new HomePage();
            }
        });
        header.add(returnBtn, BorderLayout.WEST);
        header.add(new JLabel("Indicateurs"), BorderLayout.EAST);

        pane.add(header, BorderLayout.PAGE_START);

        JPanel content = new JPanel(new GridLayout(2, 2, 140, 140));

        JButton occRate = new JButton("Taux d'occupation");
        occRate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DialogCommand oRCommand = new OccupancyRate();
                dialogToOpen(oRCommand);
            }
        });
        content.add(occRate);

        JButton sensors = new JButton("Capteurs-Equipements");
        sensors.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DialogCommand sensors = new Sensors();
                dialogToOpen(sensors);
            }
        });

        content.add(sensors);


        JButton windows = new JButton("Fenetres Electrochromatiques");
        windows.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialogToOpen(new Windows());
            }
        });

        content.add(windows);

        JButton badges = new JButton("Cartes d'acces");
        badges.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialogToOpen(new Badges());
            }
        });

        content.add(badges);

        pane.add(content, BorderLayout.CENTER);
        return pane;
    }

    private void dialogToOpen(DialogCommand command) {
        JPanel dialogPanel = new JPanel();
        if(dialog.isVisible()){
            dialog.remove(dialogPanel);
            dialog.dispose();
        }

        command.execute(dialogPanel);

        dialog.setSize(900, 500);
        dialog.setVisible(true);
        dialog.setPreferredSize(dialog.getSize());
        dialog.setContentPane(dialogPanel);
        dialog.invalidate();
        dialog.repaint();
        dialog.pack();
        dialog.setLocationRelativeTo(pane);
    }

}
