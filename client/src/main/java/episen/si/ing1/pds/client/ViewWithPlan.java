package episen.si.ing1.pds.client;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class ViewWithPlan {
    private final String page = "device";
    private final JFrame frame;
    private JPanel pageBody;
    private String order;
    private String floorNumber;
    private final JPanel configButton = new JPanel();

    protected static Map<JButton, String> listButton = new HashMap<>();
    protected static Map<String,Map<String, String>> configRoom = new HashMap<>();
    protected static List listDeviceId = new ArrayList();
    protected static Map<String, String> listDeviceIdRoom = new HashMap<>();

    public ViewWithPlan(JFrame frame){
        this.frame = frame;
    }
    public ViewWithPlan(JFrame frame, String o) {
        this.frame = frame;
        this.order = o;
    }
    public void viewWithPlan(JPanel pb){
        this.pageBody = pb;
        pageBody.setBackground(Color.WHITE);
        JPanel view = view();
        JPanel advancement;
        RentalAdvancement rentalAdvancement = new RentalAdvancement(page);
        advancement = rentalAdvancement.rentalAdvancement();
        pageBody.add(advancement, BorderLayout.CENTER);
        pageBody.add(view, BorderLayout.SOUTH);
        pageBody.repaint();
        frame.repaint();
    }
    public JPanel view(){
        JPanel view = new JPanel();
        view.setBackground(Color.WHITE);
        Ihm.sizeComposant(new Dimension(950,600), view);
        view.setLayout(null);

        JTextField orderSelected = new JTextField("Vous avez choisi l'offre " + order);
        orderSelected = styleJTextFieldReservation(orderSelected, 20, 20, 320, 50, Color.WHITE, Color.WHITE);
        orderSelected.setFont(new Font("Serif", Font.BOLD, 20));
        view.add(orderSelected);

        JPanel plan = new JPanel();
        plan.setLayout(new BorderLayout());
        plan.setBackground(Color.white);
        Ihm.sizeComposant(new Dimension(500, 400), plan);
        plan.setBounds(10,100,500,400);
        try{
            ImageIcon planImage = new ImageIcon(ImageIO.read(new File(Ihm.path+"plan.png")));
            planImage = new ImageIcon(planImage.getImage().getScaledInstance(plan.getWidth(), plan.getHeight(), Image.SCALE_DEFAULT));
            JLabel planLabel = new JLabel(planImage, JLabel.CENTER);
            plan.add(planLabel, BorderLayout.CENTER);
            view.add(plan);
        } catch(Exception e){}


        JPanel config = new JPanel();

        createButton(view);
        view.add(config);
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
                        x,y,175,50, configButton, view, "batiment : " + map.get("building_name") +", etage : "+
                                map.get("floor_number")  +" numero de salle "+ map.get("room_wording").split("reunion")[1], map.get("room_id"));
            } else configRoom(map.get("room_wording").split("salle")[0]+" etage "+ map.get("floor_number"),
                    x,y,175,50, configButton, view, "batiment : " + map.get("building_name") +", etage : "+
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

    public void configRoom( String message, int x, int y, int w, int h,JPanel configButton, JPanel view,String information, String room_id){
        JButton room = new JButton(message);
        room.setBackground(Color.red);
        room.setBounds(x,y,w,h);
        room.setVisible(true);
        room.setFont(new Font("Arial", Font.PLAIN, 10));
        room.setToolTipText(information);
        room.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.setVisible(false);
                changePage(view, room, room_id);
            }
        });
        listButton.put(room, "unvalidated");
        configButton.add(room);
    }
    public void changePage(JPanel view, JButton room, String room_id){
        ChoiceDevice device = new ChoiceDevice(frame,room_id);
        device.choice(pageBody, view, room);
    }
    public void back(JPanel oldView, JPanel pb, JButton room){
        this.pageBody = pb;
        boolean verifContinue = true;
        for(Map.Entry map : listButton.entrySet()){
            if( (map.getValue()).equals("unvalidated")) verifContinue = false;
        }
        oldView.setVisible(true);
        if(verifContinue){
            Ihm.buttonContinue.setEnabled(true);
            oldView.add(Ihm.buttonContinue);
            Ihm.buttonContinue.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    oldView.setVisible(false);
                    Bill bill = new Bill(frame);
                    bill.confirmation(pageBody);
                }
            });
        }
        room.repaint();
        pageBody.repaint();
    }
}
