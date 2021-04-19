package episen.si.ing1.pds.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class Choice{
    private final String page = "choice";
    private Map<String, String> input = new HashMap<>();
    private JFrame frame;
    private JButton buttonContinue = new JButton("Continuer");
    private JButton buttonReturn = new JButton("Retour");
    private JTextField selected = new JTextField("Vous avez choisi : ");
    private JPanel pageBody;

    //keep for link with the previous page
    public Choice(Map<String, String> input,JFrame f) {
        this.input = input;
        this.frame = f;
    }

    public void choice(JPanel pb){
        this.pageBody = pb;
        pageBody.setBackground(Color.WHITE);
        JPanel view = view();
        buttonContinue.setEnabled(false);
        buttonContinue.setBounds(780, 10, 100, 50);
        buttonContinue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("vous avez choisi  ca"+ (selected.getText().split(":")[1]).trim());
                String order = (selected.getText().split(":")[1]).trim();
                view.setVisible(false);
                changePage(order);
            }
        });
        buttonReturn.setEnabled(true);
        buttonReturn.setBounds(670, 10, 100, 50);
        view.add(buttonContinue);
        view.add(buttonReturn);
        RentalAdvancement rentalAdvancement = new RentalAdvancement(page);
        JPanel advancement = rentalAdvancement.rentalAdvancement();
        pageBody.add(advancement, BorderLayout.CENTER);
        pageBody.add(view, BorderLayout.SOUTH);
        pageBody.repaint();
        frame.repaint();
    }

    public JPanel view(){
        JPanel view = new JPanel();
        view.setBackground(Color.WHITE);
        sizeComposant(new Dimension(950,600), view);
        view.setLayout(null);

        JTextField order = new JTextField("Choisissez une offre : ");
        order = styleJTextFieldReservation(order, 20, 10, 320, 50, Color.WHITE, Color.WHITE);
        order.setFont(new Font("Serif", Font.BOLD, 20));
        view.add(order);

        selected = styleJTextFieldReservation(selected, 360, 10, 150, 50, Color.WHITE, Color.WHITE);
        view.add(selected);

        JPanel display1 = proposal();
        JButton buttonDisplay1 = new JButton("Choisir");
        buttonDisplay1.setBounds(157, 170, 100,40);
        buttonDisplay1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selected.setText("Vous avez choisi : 1");
                buttonContinue.setEnabled(true);
            }
        });
        display1.setBounds(10, 80, 415,235);

        JPanel display2 = proposal();
        display2.setBounds(10, 325, 415, 235);
        JButton buttonDisplay2 = new JButton("Choisir");
        buttonDisplay2.setBounds(157, 170, 100,40);
        buttonDisplay2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selected.setText("Vous avez choisi : 2");
                buttonContinue.setEnabled(true);
            }
        });

        JPanel display3 = proposal();
        display3.setBounds(450, 80, 415, 235);
        JButton buttonDisplay3 = new JButton("Choisir");
        buttonDisplay3.setBounds(157, 170, 100,40);
        buttonDisplay3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selected.setText("Vous avez choisi : 3");
                buttonContinue.setEnabled(true);
            }
        });

        JPanel display4 = proposal();
        display4.setBounds(450, 325, 415, 235);
        JButton buttonDisplay4 = new JButton("Choisir");
        buttonDisplay4.setBounds(157, 170, 100,40);
        buttonDisplay4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selected.setText("Vous avez choisi : 4");
                buttonContinue.setEnabled(true);
            }
        });

        display1.add(buttonDisplay1);
        display2.add(buttonDisplay2);
        display3.add(buttonDisplay3);
        display4.add(buttonDisplay4);
        view.add(display4);
        view.add(display3);
        view.add(display2);
        view.add(display1);
        return view;
    }

    public JPanel proposal(){
        JPanel proposal = new JPanel();
        proposal.setLayout(null);
        proposal.setBackground(new Color(150, 75,0));

        JTextField building = new JTextField("Batiment : ");
        building = styleJTextFieldReservation(building, 20, 20, 350, 20, new Color(150, 75,0), new Color(150, 75,0));
        building.setForeground(Color.white);
        JTextField address = new JTextField("Adresse : ");
        address.setForeground(Color.white);
        address = styleJTextFieldReservation(address, 20, 50, 350, 20, new Color(150, 75,0), new Color(150, 75,0));
        JTextField floor = new JTextField("Etage(s) : ");
        floor.setForeground(Color.white);
        floor = styleJTextFieldReservation(floor, 20, 80, 350, 20, new Color(150, 75,0), new Color(150, 75,0));
        JTextField location = new JTextField("Situation geographique : ");
        location.setForeground(Color.white);
        location = styleJTextFieldReservation(location, 20, 110, 350, 20, new Color(150, 75,0), new Color(150, 75,0));
        JTextField price = new JTextField("Prix sans euipement : ");
        price = styleJTextFieldReservation(price, 20, 140, 350, 20, new Color(150, 75,0), new Color(150, 75,0));
        price.setForeground(Color.white);

        proposal.add(building);
        proposal.add(address);
        proposal.add(floor);
        proposal.add(building);
        proposal.add(location);
        proposal.add(price);

        return proposal;
    }
    public void sizeComposant(Dimension dim, Component c){
        c.setPreferredSize(dim);
        c.setMaximumSize(dim);
        c.setMinimumSize(dim);
    }
    public JTextField styleJTextFieldReservation(JTextField t, int x, int y, int w, int h, Color c1 , Color c2) {
        t.setEditable(false);
        t.setBackground(c1);
        t.setBorder(BorderFactory.createLineBorder(c2));
        t.setBounds(x, y, w, h);
        return t;
    }

    public void changePage(String order){
        ViewWithPlan viewPlan = new ViewWithPlan(frame, input , order);
        viewPlan.viewWithPlan(pageBody);
    }
}
