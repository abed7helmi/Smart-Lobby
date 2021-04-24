package episen.si.ing1.pds.client;


import javax.swing.*;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ViewWithPlan {
    private String page = "device";
    private final JFrame frame;
    private Map<String , String> input = new HashMap<>();
    private final JButton buttonReturn = new JButton("Retour");
    private JPanel pageBody;
    private String order;
    private String floorNumber;
    private JPanel configButton = new JPanel();
    private Map<JButton, String> listButton = new HashMap<>();


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
        JPanel view = view();
        view.add(buttonReturn);
        JPanel advancement;
        RentalAdvancement rentalAdvancement = new RentalAdvancement(page);
        advancement = rentalAdvancement.rentalAdvancement();
        pageBody.add(advancement, BorderLayout.CENTER);
        buttonReturn.setEnabled(true);
        buttonReturn.setBounds(670, 10, 100, 50);
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

        JPanel config = new JPanel();
        createButton(view);
        view.add(config);
        return view;
    }
    public void createButton(JPanel view){

        configButton.setLayout(null);
        configButton.setBounds(520,100,380,480);

        int x  = 20;
        int y = 20;

        JButton room1 = configRoom("Salle n 1" + " etage n 1" , x,y,150,50, configButton, view);
        room1.setToolTipText("batiment Pavillon ");
        y = y + 60;
        JButton room2 = configRoom("Salle n 2"  + " etage n 1", x,y,150,50, configButton, view);
        room2.setToolTipText("batiment Pavillon ");
        y = y + 60;
        JButton room3 = configRoom("Salle n 1"  + " etage n 2", x,y,150,50, configButton, view);
        room3.setToolTipText("batiment Pavillon ");
        y = y + 60;
        JButton room4 = configRoom("Salle n 5" + " etage n 3", x,y,150,50, configButton, view);
        room4.setToolTipText("batiment Pavillon ");
        y = y + 60;
        JButton room5 = configRoom("Salle n 5" + " etage n 4", x,y,150,50, configButton, view);
        room5.setToolTipText("batiment Pavillon ");
        y = y + 60;
        JButton room6 = configRoom("Salle n 1" + " etage n 5", x,y,150,50, configButton, view);
        room6.setToolTipText("batiment Pavillon ");
        y = y + 60;

        view.add(configButton);
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

    public JButton configRoom(String message, int x, int y, int w, int h,JPanel configButton, JPanel view){
        JButton room = new JButton(message);
        room.setBackground(Color.red);
        room.setBounds(x,y,w,h);
        room.setVisible(true);
        room.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.setVisible(false);
                changePage(view, room, listButton);
            }
        });
        listButton.put(room, "unvalidated");
        configButton.add(room);
        return room;
    }
    public void changePage(JPanel view, JButton room, Map<JButton, String> listButton){
        ChoiceDevice device = new ChoiceDevice(frame, input);
        device.choice(pageBody, view, room, listButton);
    }
    public void back(JPanel oldView, JPanel pb, JButton room, Map<JButton, String> list){
        this.pageBody = pb;
        boolean verifContinue = true;
        for(Map.Entry map : list.entrySet()){
            if( (map.getValue()).equals("unvalidated")) verifContinue = false;
        }
        if(verifContinue){
            JButton buttonContinue = new JButton("Continuer");
            buttonContinue.setEnabled(true);
            buttonContinue.setBounds(780, 10, 100, 50);
            oldView.add(buttonContinue);
            buttonContinue.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    oldView.setVisible(false);
                    Bill bill = new Bill(input, frame);
                    bill.confirmation(pageBody);
                }
            });
        }
        oldView.setVisible(true);
        room.repaint();
        pageBody.repaint();
    }
}
