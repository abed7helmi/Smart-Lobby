package episen.si.ing1.pds.client;


import javax.swing.*;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;


public class ViewWithPlan {
    private String page = "device";
    private final JFrame frame;
    private Map<String , String> input = new HashMap<>();
    private JPanel pageBody;
    private String order;
    private String floorNumber;
    private JPanel configButton = new JPanel();
    private Map<JButton, String> listButton = new HashMap<>();
    private Map<String ,Map<String,String>> proposalSelected = new HashMap<>();
    private Map<String,Map<String, String>> configRoom = new HashMap<>();


    public ViewWithPlan(JFrame frame, Map<String, String> input){
        this.frame = frame;
        this.input = input;
    }
    public ViewWithPlan(JFrame frame, Map<String, String> input, String o,  Map<String ,Map<String,String>>  ps) {
        this.frame = frame;
        this.input = input;
        this.order = o;
        proposalSelected = ps;
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
        configButton.setBounds(520,100,380,490);

        int x = 10;
        int y = 10;

        for(Map<String , String> map : proposalSelected.values()){
            configRoom(map.get("room_wording").split("salle")[0]+" etage "+ map.get("floor_number"), x,y,175,50, configButton, view, map.get("building_name"), map.get("room_id"));
            if(y >= 480) {
                y = 10;
                x = 195;
            } else y = y + 60;
        }
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
                changePage(view, room, listButton, room_id);
            }
        });
        listButton.put(room, "unvalidated");
        configButton.add(room);
    }
    public void changePage(JPanel view, JButton room, Map<JButton, String> listButton, String room_id){
        ChoiceDevice device = new ChoiceDevice(frame, input,room_id, configRoom, proposalSelected);
        device.choice(pageBody, view, room, listButton);
    }
    public void back(JPanel oldView, JPanel pb, JButton room, Map<JButton, String> list, Map<String, Map<String, String>> configurationRoom , Map<String, Map<String, String>> ps){
        configRoom = configurationRoom;
        proposalSelected = ps;
        this.pageBody = pb;
        boolean verifContinue = true;
        for(Map.Entry map : list.entrySet()){
            if( (map.getValue()).equals("unvalidated")) verifContinue = false;
        }
        if(verifContinue){
            JButton buttonContinue = new JButton("> Continuer");
            buttonContinue.setBackground(new Color(255,255,255));
            buttonContinue.setForeground(Color.black);
            buttonContinue.setBorder(BorderFactory.createLineBorder(Color.black));
            buttonContinue.setEnabled(true);
            buttonContinue.setBounds(780, 10, 100, 50);
            oldView.add(buttonContinue);
            buttonContinue.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    oldView.setVisible(false);
                    Bill bill = new Bill(input, frame, input, proposalSelected, configRoom);
                    bill.confirmation(pageBody);
                }
            });
        }
        oldView.setVisible(true);
        room.repaint();
        pageBody.repaint();
    }
}
