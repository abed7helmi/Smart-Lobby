package episen.si.ing1.pds.client.reservation;

import episen.si.ing1.pds.client.Ihm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import java.util.List;

public class ViewWithPlan {
    private final String page = "device";
    private JPanel pageBody;
    private JPanel view = new JPanel();
    private String order;
    private String floorNumber;
    private final JPanel configButton = new JPanel();
    private JTextField consign = new JTextField("**Veuillez configurer les salles qui sont rouges. " +
            "Les salles vertes sont configurees.");
    public static Map<JButton, String> listButton = new HashMap<>();
    public static Map<String,Map<String, String>> configRoom = new HashMap<>();
    public static List listDeviceId = new ArrayList();
    public static Map<String, String> listDeviceIdRoom = new HashMap<>();
    private JPanel advancement = new JPanel();
    private JTextField orderSelected = new JTextField();
    private JFrame frame;
    private JButton confirm = new JButton("Continuer");
    public static boolean verifConfiguration = false;

    public ViewWithPlan(JFrame frame, String o) {
        this.frame = frame;
        this.order = o;
    }
    public void viewWithPlan(JPanel pb){
        this.pageBody = pb;
        pageBody.setBackground(Color.WHITE);
        view = view();
        RentalAdvancement rentalAdvancement = new RentalAdvancement(page);
        advancement = rentalAdvancement.rentalAdvancement();
        advancement.setVisible(true);
        pageBody.add(advancement, BorderLayout.CENTER);
        pageBody.add(view, BorderLayout.SOUTH);
        pageBody.repaint();
        frame.repaint();
    }
    public JPanel view(){
        view = new JPanel();
        view.setBackground(Color.WHITE);
        Ihm.sizeComposant(new Dimension(950,600), view);
        view.setLayout(null);

        confirm = Ihm.navJButton(confirm, 780,10,100,50);
        view.add(confirm);
        confirm.setEnabled(false);
        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.setVisible(false);
                advancement.setVisible(false);
                pageBody.repaint();
                Bill bill = new Bill(frame);
                bill.confirmation(pageBody);
            }
        });

        orderSelected = new JTextField("Vous avez choisi l'offre " + order);
        orderSelected = styleJTextFieldReservation(orderSelected, 20, 20, 320, 50, Color.WHITE, Color.WHITE);
        orderSelected.setFont(new Font("Serif", Font.BOLD, 20));
        view.add(orderSelected);

        JPanel planPanel = new JPanel();
        planPanel.setBounds(10,100,500,420);
        ImageIcon planImage = new ImageIcon(Ihm.path+"plan.png");
        planImage = new ImageIcon(planImage.getImage().getScaledInstance(planPanel.getWidth(), planPanel.getHeight(), Image.SCALE_DEFAULT));
        JLabel planLabel = new JLabel(planImage, JLabel.CENTER);
        planPanel.add(planLabel);
        view.add(planPanel);

        createButton(view);

        consign = styleJTextFieldReservation(consign, 20, 550, 500, 50, Color.WHITE, Color.WHITE);
        view.add(consign);
        return view;
    }
    public void createButton(JPanel view){
        configButton.setLayout(null);
        configButton.setBounds(520,100,380,490);

        int x = 10;
        int y = 10;
        for(Map<String , String> map : Choice.proposalSelected.values()){
            if(map.get("room_wording").contains("reunion")){
                configRoom(map.get("room_wording").split("reunion")[0]+" reunion etage "+ map.get("floor_number"),
                        x,y,175,50, configButton,  "batiment : " + map.get("building_name") +", etage : "+
                                map.get("floor_number")  +" numero de salle "+ map.get("room_wording").split("reunion")[1], map.get("room_id"));
            } else configRoom(map.get("room_wording").split("salle")[0]+" etage "+ map.get("floor_number"),
                    x,y,175,50, configButton,  "batiment : " + map.get("building_name") +", etage : "+
                            map.get("floor_number")  +" numero de salle "+ map.get("room_wording").split("salle")[1], map.get("room_id"));
            if(y >= 480) {
                y = 10;
                x = 195;
            } else y = y + 60;
        }
        view.add(configButton);
        view.repaint();
    }
    public JTextField styleJTextFieldReservation(JTextField t, int x, int y, int w, int h, Color c1 , Color c2) {
        t.setEditable(false);
        t.setBackground(c1);
        t.setBorder(BorderFactory.createLineBorder(c2));
        t.setBounds(x, y, w, h);
        return t;
    }
    public void configRoom( String message, int x, int y, int w, int h,JPanel configButton,String information, String room_id){
        JButton room = new JButton(message);
        room.setBackground(new Color(255,102,102));
        room.setBounds(x,y,w,h);
        room.setVisible(true);
        room.setFont(new Font("Arial", Font.PLAIN, 10));
        room.setToolTipText(information);
        room.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changePage(room, room_id, confirm);
            }
        });
        listButton.put(room, "unvalidated");
        configButton.add(room);
    }
    public void changePage(JButton room, String room_id, JButton confirm){
        ChoiceDevice device = new ChoiceDevice(frame, room_id);
        device.choice(room,confirm);
    }
    public static void reload(JButton c){
        if(verifConfiguration){
            c.setEnabled(true);
        }
    }
}

