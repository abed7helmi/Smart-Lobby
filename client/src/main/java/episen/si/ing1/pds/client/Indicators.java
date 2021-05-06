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
                { "Dernière location ajoutée", "2444" },
        };
        JTable table2 = new JTable(data2, cols);
        table2.setRowHeight(30);

        indicateurGeneral.add(table);
        indicateurGeneral.add(table2);
        context.add(indicateurGeneral);

        JPanel list = new JPanel(new GridLayout(1, 3));
        Border listBorder = BorderFactory.createTitledBorder("Liste des locations");
        list.setBorder(listBorder);

        JLabel l = new JLabel("Batiment 1");
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(l.getText());
        root.setUserObject(l);

        l.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Batiment 1 has been Clicked");
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        //create the child nodes
        DefaultMutableTreeNode vegetableNode = new DefaultMutableTreeNode("Floor 1");
        DefaultMutableTreeNode fruitNode = new DefaultMutableTreeNode("Floor 2");
        //add the child nodes to the root node
        root.add(vegetableNode);
        root.add(fruitNode);

        JTree tree = new JTree(root);

        DefaultMutableTreeNode root2 = new DefaultMutableTreeNode("Batiment 2");
        //create the child nodes
        DefaultMutableTreeNode floor2 = new DefaultMutableTreeNode("Floor 1");
        DefaultMutableTreeNode floor3 = new DefaultMutableTreeNode("Floor 2");
        //add the child nodes to the root node
        root2.add(floor2);
        root2.add(floor3);

        JTree tree2 = new JTree(root2);

        DefaultMutableTreeNode root3 = new DefaultMutableTreeNode("Batiment 3");
        //create the child nodes
        DefaultMutableTreeNode flor2 = new DefaultMutableTreeNode("Floor 1");
        DefaultMutableTreeNode flor3 = new DefaultMutableTreeNode("Floor 2");
        //add the child nodes to the root node
        root3.add(flor2);
        root3.add(flor3);

        JTree tree3 = new JTree(root3);
        list.add(tree);
        list.add(tree2);
        list.add(tree3);

        context.add(list);

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
