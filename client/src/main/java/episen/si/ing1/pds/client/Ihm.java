package episen.si.ing1.pds.client;

import javax.smartcardio.Card;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class Ihm extends JFrame{
    private JFrame frame = new JFrame();
    private JPanel pageBody = new JPanel();
    private JPanel pageBody1 ;
    private JPanel pageBody2 ;
    private JPanel pageBody3 ;
    private JPanel pageBody4 ;

    public Ihm(String name) {
        //HomePage home = new HomePage(this);
        //home.firstPage();
        frame = this;
        CardLayout pages = new CardLayout();
        pageBody.setLayout(pages);


        ChoiceCriteria reservation = new ChoiceCriteria(frame);
        pageBody1 = reservation.realizeReservation();

        Mapping m = new Mapping();
        pageBody2 = m.mappingPanel();


        pageBody.add(pageBody1,"realize");
        pageBody.add(pageBody2,"consult");
        //pageBody.add(pageBody1,"staff");
        //pageBody.add(pageBody1,"page1");

        frame.add(pageBody, BorderLayout.CENTER);

        JPanel menu = new JPanel();
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.add(Box.createVerticalStrut(100));

        JButton realize = new JButton("Realiser une location");
        realize.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pages.show(pageBody,"realize");
            }
        });

        sizeComposant(new Dimension(Integer.MAX_VALUE, 75), realize);
        realize.setBackground(Color.CYAN);

        JButton consult = new JButton("Consulter une location");
        consult.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pages.show(pageBody,"consult");

            }
        });
        sizeComposant(new Dimension(Integer.MAX_VALUE, 75), consult);
        consult.setBackground(Color.CYAN);

        JButton staff = new JButton("Personnel");
        staff.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pages.show(pageBody, "staff");
            }
        });
        sizeComposant(new Dimension(Integer.MAX_VALUE, 75), staff);
        staff.setBackground(Color.CYAN);

        JButton configWindow = new JButton("Configurer fenêtre");
        staff.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pages.show(pageBody,"window");
            }
        });
        sizeComposant(new Dimension(Integer.MAX_VALUE, 75), staff);
        configWindow.setBackground(Color.CYAN);

        JPanel underMenu = new JPanel(new FlowLayout(FlowLayout.LEFT));
        underMenu.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        underMenu.setBackground(Color.CYAN);

        JButton disconnect = new JButton("Deconnecter");
        disconnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        disconnect.setMaximumSize(new Dimension(100, 100));
        disconnect.setBackground(Color.CYAN);

        ImageIcon iconHome = new ImageIcon(new ImageIcon("C:\\Users\\cedri\\Bureau\\pds\\image\\maison.png").getImage().getScaledInstance(18,18,Image.SCALE_DEFAULT));
        JButton home = new JButton(iconHome);
        home.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {}
        });
        home.setBackground(Color.CYAN);

        ImageIcon iconRefresh = new ImageIcon(new ImageIcon("C:\\Users\\cedri\\Bureau\\pds\\image\\actualiser.png").getImage().getScaledInstance(18,18,Image.SCALE_DEFAULT));
        JButton refresh = new JButton(iconRefresh);
        refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {}
        });
        refresh.setBackground(Color.CYAN);

        underMenu.add(disconnect);
        underMenu.add(home);
        underMenu.add(refresh);

        menu.add(realize);
        menu.add(consult);
        menu.add(staff);
        menu.add(Box.createGlue());
        menu.add(underMenu);

        menu.setBackground(Color.CYAN);
        menu.setPreferredSize(new Dimension(250, 800));

        frame.add(menu , BorderLayout.WEST);
        frame.setSize(1200, 800);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /*public JPanel menu(){
        JPanel menu = new JPanel();
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.add(Box.createVerticalStrut(100));

        JButton realize = new JButton("Realiser une location");
        realize.addActionListener(test);

        sizeComposant(new Dimension(Integer.MAX_VALUE, 75), realize);
        realize.setBackground(Color.CYAN);

        JButton consult = new JButton("Consulter une location");
        consult.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {}
        });
        sizeComposant(new Dimension(Integer.MAX_VALUE, 75), consult);
        consult.setBackground(Color.CYAN);

        JButton staff = new JButton("Personnel");
        staff.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {}
        });
        sizeComposant(new Dimension(Integer.MAX_VALUE, 75), staff);
        staff.setBackground(Color.CYAN);

        JButton configWindow = new JButton("Configurer fenêtre");
        staff.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {}
        });
        sizeComposant(new Dimension(Integer.MAX_VALUE, 75), staff);
        configWindow.setBackground(Color.CYAN);



        JPanel underMenu = new JPanel(new FlowLayout(FlowLayout.LEFT));
        underMenu.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        underMenu.setBackground(Color.CYAN);

        JButton disconnect = new JButton("Deconnecter");
        disconnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {}
        });
        disconnect.setMaximumSize(new Dimension(100, 100));
        disconnect.setBackground(Color.CYAN);

        ImageIcon iconHome = new ImageIcon(new ImageIcon("C:\\Users\\cedri\\Bureau\\pds\\image\\maison.png").getImage().getScaledInstance(18,18,Image.SCALE_DEFAULT));
        JButton home = new JButton(iconHome);
        home.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {}
        });
        home.setBackground(Color.CYAN);

        ImageIcon iconRefresh = new ImageIcon(new ImageIcon("C:\\Users\\cedri\\Bureau\\pds\\image\\actualiser.png").getImage().getScaledInstance(18,18,Image.SCALE_DEFAULT));
        JButton refresh = new JButton(iconRefresh);
        refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {}
        });
        refresh.setBackground(Color.CYAN);

        underMenu.add(disconnect);
        underMenu.add(home);
        underMenu.add(refresh);

        menu.add(realize);
        menu.add(consult);
        menu.add(staff);
        menu.add(Box.createGlue());
        menu.add(underMenu);

        menu.setBackground(Color.CYAN);
        menu.setPreferredSize(new Dimension(250, 800));

        return menu;
    }*/

    public void sizeComposant(Dimension dim, Component c){
        c.setPreferredSize(dim);
        c.setMaximumSize(dim);
        c.setMinimumSize(dim);
    }
}
