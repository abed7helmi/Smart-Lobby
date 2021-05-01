package episen.si.ing1.pds.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JFrame{
    private JFrame frame;
    public static String company_id = "";

    public Menu(String name, String id){
        company_id = id;
        setTitle(name);
        frame = this;
        setSize(500, 500);
        frame.setLayout(null);

        JTextField title = new JTextField("Menu");
        title.setBounds(50, 50,  50, 50);
        title.setEditable(false);
        title.setBorder(BorderFactory.createLineBorder(Color.white));

        JButton realizeLocation = new JButton("Realiser une location");
        menuJButton(realizeLocation,150,160,200,50, "realize");

        JButton consultLocation = new JButton("Consulter une location");
        menuJButton(consultLocation,150,210,200,50, "consult");

        JButton staff = new JButton("Personnel");
        menuJButton(staff,150,260,200,50, "staff");

        JButton indicator = new JButton("Indicateurs - locations");
        menuJButton(indicator,150,310,200,50, "indicator");

        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void menuJButton(JButton button,int x, int y, int w,int h, String page){
        button.setBounds(x,y,w,h);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ihm ihm = new Ihm("Smart Lobby",page, company_id);
                frame.dispose();
            }
        });
        frame.add(button);
    }
}
