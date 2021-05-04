package episen.si.ing1.pds.client;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class Choice{
    private final String page = "choice";
    private final JFrame frame;
    private JTextField selected = new JTextField("Vous avez choisi : ");
    private JPanel pageBody;
    protected static Map<String , Map<String, Map<String ,String>>> mapProposals = new HashMap<>();
    protected static Map<String ,Map<String,String>> proposalSelected = new HashMap<>();

    //keep for link with the previous page
    public Choice(JFrame f) {
        this.frame = f;
    }

    public void choice(JPanel pb, String proposals){
        this.pageBody = pb;
        pageBody.setBackground(Color.WHITE);
        JPanel view = view(proposals);
        RentalAdvancement rentalAdvancement = new RentalAdvancement(page);
        JPanel advancement = rentalAdvancement.rentalAdvancement();

        Ihm.buttonContinue.setEnabled(false);
        Ihm.buttonContinue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String order = (selected.getText().split(":")[1]).trim();
                view.setVisible(false);
                advancement.setVisible(false);
                changePage(order);
            }
        });

        Ihm.buttonVoid.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Menu Menu = new Menu("Smart Lobby", ChoiceCriteria.input.get("company_id"));
                ChoiceCriteria.input.clear();
                frame.dispose();
            }
        });
        view.add(Ihm.buttonVoid);
        view.add(Ihm.buttonContinue);

        pageBody.add(advancement, BorderLayout.CENTER);
        pageBody.add(view, BorderLayout.SOUTH);
        pageBody.repaint();
        frame.repaint();
    }

    public JPanel view(String proposals){
        try{
            ObjectMapper mapper = new ObjectMapper(new JsonFactory());
            mapProposals = mapper.readValue(proposals,
                    new TypeReference<Map<String , Map<String, Map<String ,String>>>>() {
                    });

        }catch(Exception e) {e.printStackTrace();}

        JPanel view = new JPanel();
        view.setBackground(Color.WHITE);
        Ihm.sizeComposant(new Dimension(950,600), view);
        view.setLayout(null);

        JTextField order = new JTextField("Choisissez une offre : ");
        order = Ihm.styleJTextFieldReservation(order, 20, 10, 320, 50, Color.WHITE, Color.white);
        order.setFont(new Font("Serif", Font.BOLD, 20));
        order.setForeground(Color.black);
        view.add(order);

        selected = Ihm.styleJTextFieldReservation(selected, 360, 10, 150, 50, Color.WHITE, Color.white);
        selected.setForeground(Color.black);
        view.add(selected);

        int nbRoom = Integer.parseInt(ChoiceCriteria.input.get("numberMeetingRoom")) + Integer.parseInt(ChoiceCriteria.input.get("numberSingleOffice")) +
                Integer.parseInt(ChoiceCriteria.input.get("numberOpenSpace")) + Integer.parseInt(ChoiceCriteria.input.get("numberClosedOffice"));

        if(nbRoom == mapProposals.get("proposal1").size()){
            JPanel display1 = proposal(mapProposals.get("proposal1"));
            JButton buttonDisplay1 = new JButton("Choisir");
            selectProposal(buttonDisplay1, 157,170,100,40,1, display1,mapProposals.get("proposal1"));
            display1.setBounds(10, 80, 415,235);
            view.add(display1);
        }

        if(nbRoom == mapProposals.get("proposal2").size()){
            JPanel display2 = proposal(mapProposals.get("proposal2"));
            display2.setBounds(10, 325, 415, 235);
            JButton buttonDisplay2 = new JButton("Choisir");
            selectProposal(buttonDisplay2, 157,170,100,40,2, display2,mapProposals.get("proposal2"));
            view.add(display2);
        }

        if(nbRoom == mapProposals.get("proposal3").size()){
            JPanel display3 = proposal(mapProposals.get("proposal3"));
            display3.setBounds(450, 80, 415, 235);
            JButton buttonDisplay3 = new JButton("Choisir");
            selectProposal(buttonDisplay3, 157,170,100,40,3, display3,mapProposals.get("proposal3"));
            view.add(display3);
        }

        if(nbRoom == mapProposals.get("proposal4").size()) {
            JPanel display4 = proposal(mapProposals.get("proposal4"));
            display4.setBounds(450, 325, 415, 235);
            JButton buttonDisplay4 = new JButton("Choisir");
            selectProposal(buttonDisplay4, 157, 170, 100, 40, 4, display4, mapProposals.get("proposal4"));
            view.add(display4);
        }

        return view;
    }

    public JPanel proposal(Map<String, Map<String ,String>> rooms){
        //avoid doublons
        LinkedHashSet<String> nameBuildings = new LinkedHashSet<>();
        LinkedHashSet<String> numberFloor = new LinkedHashSet<>();
        int priceProposal = 0;
        for(Map<String, String> m : rooms.values()){
            nameBuildings.add(m.get("building_name"));
            numberFloor.add(m.get("floor_number"));
            priceProposal = priceProposal + Integer.parseInt(m.get("price"));
        }

        JPanel proposal = new JPanel();
        proposal.setBorder(new BorderPanel());
        proposal.setBackground(Color.white);
        proposal.setLayout(null);
        if( nameBuildings.size() != 0){
            Iterator<String> itrName = nameBuildings.iterator();
            StringBuilder listName = new StringBuilder();
            while(itrName.hasNext()){
                listName.append(itrName.next()+ ",");
            }
            listName.deleteCharAt(listName.length()- 1);
            JTextField building = new JTextField("Batiment : ");
            building = Ihm.styleJTextFieldReservation(building, 20, 20, 350, 20, Color.WHITE, Color.white);
            building.setText(building.getText() +listName);

            proposal.add(building);
        }

        if(numberFloor.size() != 0){
            Iterator<String> itrFloor = numberFloor.iterator();
            StringBuilder listFloor = new StringBuilder();
            while(itrFloor.hasNext()){
                listFloor.append(itrFloor.next()+ ",");
            }
            listFloor.deleteCharAt(listFloor.length() - 1);

            JTextField floor = new JTextField("Etage(s) : ");
            floor = Ihm.styleJTextFieldReservation(floor, 20, 80, 350, 20, Color.WHITE, Color.white);
            floor.setText(floor.getText() +listFloor);

            proposal.add(floor);

            JTextField price = new JTextField("Prix sans euipement : ");
            price = Ihm.styleJTextFieldReservation(price, 20, 140, 350, 20, Color.WHITE, Color.white);
            price.setText(price.getText() + priceProposal);

            proposal.add(price);
        }

        return proposal;
    }

    public void changePage(String order){
        ViewWithPlan viewPlan = new ViewWithPlan(frame, order);
        viewPlan.viewWithPlan(pageBody);
    }

    public void selectProposal(JButton button ,int x, int y, int w, int h, int numberProposal, JPanel display, Map<String ,Map<String,String>> proposal){
        button.setBounds(x, y, w,h);
        button.setBackground(new Color(255, 255,255));
        button.setForeground(Color.BLACK);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selected.setText("Vous avez choisi : " + numberProposal);
                proposalSelected = proposal;
                Ihm.buttonContinue.setEnabled(true);
            }
        });
        display.add(button);
    }
}
