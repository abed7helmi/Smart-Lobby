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
    private Map<String, String> input = new HashMap<>();
    private final JFrame frame;
    private final JButton buttonContinue = new JButton("> Continuer");
    private JTextField selected = new JTextField("Vous avez choisi : ");
    private JPanel pageBody;
    private Map<String , Map<String, Map<String ,String>>> mapProposals = new HashMap<>();
    private Map<String ,Map<String,String>> proposalSelected = new HashMap<>();

    //keep for link with the previous page
    public Choice(Map<String, String> input,JFrame f) {
        this.input = input;
        this.frame = f;
    }

    public void choice(JPanel pb, String proposals){
        this.pageBody = pb;
        pageBody.setBackground(Color.WHITE);
        JPanel view = view(proposals);
        RentalAdvancement rentalAdvancement = new RentalAdvancement(page);
        JPanel advancement = rentalAdvancement.rentalAdvancement();

        buttonContinue.setBounds(780, 10, 100, 50);
        buttonContinue.setBackground(new Color(255,255,255));
        buttonContinue.setForeground(Color.black);
        buttonContinue.setBorder(BorderFactory.createLineBorder(Color.black));

        buttonContinue.setEnabled(false);
        buttonContinue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String order = (selected.getText().split(":")[1]).trim();
                view.setVisible(false);
                advancement.setVisible(false);
                changePage(order,proposalSelected);
            }
        });
        view.add(buttonContinue);
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
        sizeComposant(new Dimension(950,600), view);
        view.setLayout(null);

        JTextField order = new JTextField("Choisissez une offre : ");
        order = styleJTextFieldReservation(order, 20, 10, 320, 50, Color.white, Color.white);
        order.setFont(new Font("Serif", Font.BOLD, 20));
        order.setForeground(Color.black);
        view.add(order);

        selected = styleJTextFieldReservation(selected, 360, 10, 150, 50, Color.WHITE, Color.WHITE);
        selected.setForeground(Color.black);
        view.add(selected);

        JPanel display1 = proposal(mapProposals.get("proposal1"));
        JButton buttonDisplay1 = new JButton("Choisir");
        selectProposal(buttonDisplay1, 157,170,100,40,1, display1,mapProposals.get("proposal1"));
        display1.setBounds(10, 80, 415,235);

        JPanel display2 = proposal(mapProposals.get("proposal2"));
        display2.setBounds(10, 325, 415, 235);
        JButton buttonDisplay2 = new JButton("Choisir");
        selectProposal(buttonDisplay2, 157,170,100,40,2, display2,mapProposals.get("proposal2"));

        JPanel display3 = proposal(mapProposals.get("proposal3"));
        display3.setBounds(450, 80, 415, 235);
        JButton buttonDisplay3 = new JButton("Choisir");
        selectProposal(buttonDisplay3, 157,170,100,40,3, display3,mapProposals.get("proposal3"));

        JPanel display4 = proposal(mapProposals.get("proposal4"));
        display4.setBounds(450, 325, 415, 235);
        JButton buttonDisplay4 = new JButton("Choisir");
        selectProposal(buttonDisplay4, 157,170,100,40,4, display4,mapProposals.get("proposal4"));

        view.add(display4);
        view.add(display3);
        view.add(display2);
        view.add(display1);
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
        Iterator<String> itrName = nameBuildings.iterator();
        StringBuilder listName = new StringBuilder();
        while(itrName.hasNext()){
            listName.append(itrName.next()+ ",");
        }
        listName.deleteCharAt(listName.length()- 1);

        Iterator<String> itrFloor = numberFloor.iterator();
        StringBuilder listFloor = new StringBuilder();
        while(itrFloor.hasNext()){
            listFloor.append(itrFloor.next()+ ",");
        }
        listFloor.deleteCharAt(listFloor.length() - 1);
        JPanel proposal = new JPanel();
        proposal.setLayout(null);
        proposal.setBackground(new Color(150, 75,0));

        JTextField building = new JTextField("Batiment : ");
        building = styleJTextFieldReservation(building, 20, 20, 350, 20, new Color(150, 75,0), new Color(150, 75,0));
        building.setText(building.getText() +listName);

        JTextField floor = new JTextField("Etage(s) : ");
        floor = styleJTextFieldReservation(floor, 20, 80, 350, 20, new Color(150, 75,0), new Color(150, 75,0));
        floor.setText(floor.getText() +listFloor);

        JTextField price = new JTextField("Prix sans euipement : ");
        price = styleJTextFieldReservation(price, 20, 140, 350, 20, new Color(150, 75,0), new Color(150, 75,0));
        price.setText(price.getText() + priceProposal);

        proposal.add(building);
        proposal.add(floor);
        proposal.add(price);

        return proposal;
    }
    public void sizeComposant(Dimension dim, Component c){
        c.setPreferredSize(dim);
        c.setMaximumSize(dim);
        c.setMinimumSize(dim);
    }
    public JTextField styleJTextFieldReservation(JTextField t, int x, int y, int w, int h, Color c1 , Color c2) {
        t.setForeground(Color.white);
        t.setEditable(false);
        t.setBackground(c1);
        t.setBorder(BorderFactory.createLineBorder(c2));
        t.setBounds(x, y, w, h);
        return t;
    }

    public void changePage(String order, Map<String ,Map<String ,String>> proposalSelected){
        ViewWithPlan viewPlan = new ViewWithPlan(frame, input , order, proposalSelected);
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
                buttonContinue.setEnabled(true);
            }
        });
        display.add(button);
    }
}
