package episen.si.ing1.pds.client;


import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.chrono.JapaneseChronology;
import java.util.HashMap;
import java.util.Map;

public class ViewWithPlan {
    private final JFrame frame;
    private Map<String , String> input = new HashMap<>();
    private final JButton buttonContinue = new JButton("Continuer");
    private final JButton buttonReturn = new JButton("Retour");
    private JPanel pageBody;
    private String order;
    private JButton[] listeButton;
    //private JTextField information;
    private String numberFloor;

    public ViewWithPlan(JFrame frame, Map<String, String> input){
        this.frame = frame;
        this.input = input;
    }


    public ViewWithPlan(JFrame frame, Map<String, String> input, String o) {
        this.frame = frame;
        this.input = input;
        this.order = o;
    }

    public void viewWithPlan(JPanel pb){
        this.pageBody = pb;
        pageBody.setBackground(Color.WHITE);
        buttonContinue.setEnabled(false);
        buttonContinue.setBounds(780, 10, 100, 50);
        buttonReturn.setEnabled(true);
        buttonReturn.setBounds(670, 10, 100, 50);
        JPanel view = view();
        view.add(buttonContinue);
        view.add(buttonReturn);
        pageBody.add(view, BorderLayout.SOUTH);
        pageBody.repaint();
        frame.repaint();
    }

    public JPanel view(){
        JPanel view = new JPanel();
        view.setBackground(Color.WHITE);
        sizeComposant(new Dimension(950,600), view);
        view.setLayout(null);

        JTextField orderSelected = new JTextField("Vous avez choisi l'offre " + order);
        orderSelected = styleJTextFieldReservation(orderSelected, 10, 10, 320, 50, Color.WHITE, Color.WHITE);
        orderSelected.setFont(new Font("Serif", Font.BOLD, 20));
        view.add(orderSelected);

        JTextField floorTextField = new JTextField("Vous etes sur l'etage : ");
        floorTextField = styleJTextFieldReservation(floorTextField ,340,20 ,150 ,20,Color.white, Color.white);
        view.add(floorTextField);
        String[] floor = {"1","2","3"};
        JComboBox liste = new JComboBox(floor);

        liste.setEditable(true);
        liste.setBounds(490,20, 30, 20);
        view.add(liste);

        JPanel plan = new JPanel();
        plan.setLayout(new BorderLayout());
        plan.setBackground(Color.white);
        sizeComposant(new Dimension(500, 400), plan);
        plan.setBounds(10,100,500,400);
        ImageIcon planImage = new ImageIcon("C:\\Users\\cedri\\Bureau\\pds\\image\\plan.png");
        planImage = new ImageIcon(planImage.getImage().getScaledInstance(plan.getWidth(), plan.getHeight(), Image.SCALE_DEFAULT));
        JLabel planLabel = new JLabel(planImage, JLabel.CENTER);
        plan.add(planLabel, BorderLayout.CENTER);
        view.add(plan);

        /*JTextField legend = new JTextField();
        legend.setEditable(false);
        legend.setBackground(Color.GREEN);
        legend.setBounds(520, 100, 50 ,50);
        legend.setBorder(BorderFactory.createMatteBorder(1,1, 1, 1, Color.BLACK));
        numberFloor = (String)liste.getSelectedItem();
        information = new JTextField("Zone proposee dans le batiment XXX et l'etage "+ numberFloor);
        information = styleJTextFieldReservation(information, 580, 100, 330,50,Color.white, Color.white);
        view.add(legend);
        view.add(information);*/

        JPanel config = new JPanel();
        listeButton = createButton(view);
        view.add(config);

        // modif with data
        liste.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                System.out.println("etage "+ liste.getSelectedItem());
                if(liste.getSelectedItem().equals("2") ) {
                    numberFloor = "2";
                    configRoomRepaint(view, 2, listeButton);
                } else if(liste.getSelectedItem().equals("3")){
                    numberFloor = "3";
                    configRoomRepaint(view,3, listeButton);
                } else {
                    numberFloor = "1";
                    configRoomRepaint(view,1, listeButton);
                }
                //information.repaint();
            }
        });
        return view;
    }
    public JButton[] createButton(JPanel view){
        JPanel configButton = new JPanel();
        configButton.setLayout(null);
        configButton.setBounds(520,100,380,380);

        JButton room1 = configRoom("Salle n°1 : bureau ", 20, 20, 250, 50);
        room1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.setVisible(false);
                changePage(view);
            }
        });
        JButton room2 = configRoom("Salle n°2 :  ferme", 20, room1.getY() + 60, 250, 50);
        room2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.setVisible(false);
                changePage(view);
            }
        });
        JButton room3 = configRoom("Salle n°3 : ", 20, room2.getY() + 60, 250, 50);
        room3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.setVisible(false);
                changePage(view);
            }
        });
        JButton[] listeButton = {room1, room2, room3};
        for(int i = 0; i < listeButton.length; i++){
            configButton.add(listeButton[i]);
        }
        view.add(configButton);
        return listeButton;
    }
    public void configRoomRepaint(JPanel view, int nb, JButton[] liste){
        for(int i = 0; i < liste.length; i++){
            listeButton[i].setVisible(false);
        }
        for(int i = 0; i < nb; i++){
            listeButton[i].setVisible(true);
        }
        view.repaint();
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

    public JButton configRoom(String message, int x, int y, int w, int h){
        JButton room = new JButton(message);
        room.setBackground(Color.white);
        room.setBounds(x,y,w,h);
        return room;
    }
    public void changePage(JPanel view){
        ChoiceDevice device = new ChoiceDevice(frame, input);
        device.choice(pageBody, view);
    }
    public void back(JPanel oldView, JPanel pb){
        oldView.setVisible(true);
        pb.repaint();
    }
}
