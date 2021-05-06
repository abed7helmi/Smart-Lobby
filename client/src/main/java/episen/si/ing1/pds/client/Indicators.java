package episen.si.ing1.pds.client;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map;

public class Indicators {
    private JPanel pane = new JPanel();

    public JPanel getIndicator() {
        pane = new JPanel(new BorderLayout());

        JPanel header = new JPanel(new BorderLayout());

        header.add(new JButton("Retour"), BorderLayout.WEST);
        header.add(new JLabel("Indicateurs et locations"), BorderLayout.EAST);

        pane.add(header, BorderLayout.PAGE_START);

        JPanel context = new JPanel(new GridLayout(2,1, 40,40));

        JPanel indicateurGeneral = new JPanel(new GridLayout(1, 2, 40, 40));

        Border blackline = BorderFactory.createTitledBorder("Indicateurs generaux");
        indicateurGeneral.setBorder(blackline);

        Client.map = Map.of("test", Map.of("test", "tes"));
        String response = Client.sendBd("requestBuildList");
        System.out.println(response);

        String[] resData = response.split(",");
        //"WC-"+wc+",OC-"+nb+"EC-"+ec+"BG-"+bg+"W-"+w+"FR-"+fr+"BO-"+bo
        String wc = resData[0].split("-")[1];
        String oc = resData[1].split("-")[1];
        String ec = resData[2].split("-")[1];
        String bg = resData[3].split("-")[1];
        String w = resData[4].split("-")[1];
        String fr = resData[5].split("-")[1];
        String bo = resData[6].split("-")[1];
   //     String la = resData[7].split("-")[1];

        String[] cols = { "A", "B" };
        String[][] data = {
                { "Taux d'occupation", oc},
                { "Nombre de capteurs non installés", fr },
                { "Nombre de capteurs installés", bo },
                { "Nombre de fenêtres électrochromatiques ", w },

        };
        JTable table = new JTable(data, cols);
        table.setRowHeight(30);


        String[][] data2 = {
                { "Consommation d’eau global", wc },
                { "Consommation d’électricité globale ", ec },
                { "Nombre de badges en fonction", bg },
           //     { "Dernière location ajoutée", la },
        };
        JTable table2 = new JTable(data2, cols);
        table2.setRowHeight(30);

        indicateurGeneral.add(table);
        indicateurGeneral.add(table2);
        context.add(indicateurGeneral);


        pane.add(context, BorderLayout.CENTER);
        return pane;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Indicators");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 800);

        frame.setPreferredSize(frame.getSize());

        Indicators indi = new Indicators();
        frame.setContentPane(indi.getIndicator());
        frame.setVisible(true);
        frame.pack();
    }

}
