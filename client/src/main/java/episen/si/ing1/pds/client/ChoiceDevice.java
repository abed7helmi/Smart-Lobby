package episen.si.ing1.pds.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.Map;

public class ChoiceDevice {
    public ChoiceDevice(JFrame frame, Map<String, String> input) {
        this.frame = frame;
        this.input = input;
    }

    private JFrame frame;
    private Map<String , String> input = new HashMap<>();
    private JButton buttonValidate = new JButton("Valider");
    private JPanel pageBody;
    private final String page = "device";
    private JTextField sentenceEquipment = new JTextField("- Veuillez selectionner les equipements.");
    private String[] listeEquipment = {"ordinateur fixe","fenetre", "television connecte"};
    private JTextField sentenceSensor = new JTextField("- Veuillez selectionner les capteurs.");
    private String[] listeSensor = {"capteur de temperature","capteur de luminosite", "capteur de mouvement"};


    public void choice(JPanel pb, JPanel oldView, JButton room){
        this.pageBody = pb;
        pageBody.setBackground(Color.CYAN);
        JPanel view = view();
        view.setBackground(Color.white);
        buttonValidate.setBounds(780, 10, 100, 50);
        buttonValidate.setEnabled(false);
        buttonValidate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.setVisible(false);
                room.setBackground(Color.green);
                ViewWithPlan backViewPlan = new ViewWithPlan(frame, input);
                backViewPlan.back(oldView,pb,room);
            }
        });
        view.add(buttonValidate);
        view.setVisible(true);
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

        JTextField titleEquipment = new JTextField("Choisissez les equipements :");
        titleEquipment = styleJTextFieldReservation(titleEquipment, 50,100,350, 50, Color.white, Color.white);
        titleEquipment.setFont(new Font("Serif", Font.BOLD, 20));
        view.add(titleEquipment);

        JTextField choiceEquipment = new JTextField("- Souhaitez-vous des equipements ? ");
        choiceEquipment = styleJTextFieldReservation(choiceEquipment, 50, 160, 195, 20,Color.white, Color.white);
        view.add(choiceEquipment);

        ButtonGroup groupEquipment = new ButtonGroup();
        JRadioButton rYesEquipment = new JRadioButton("Oui ");
        rYesEquipment.setVisible(true);
        rYesEquipment.setBackground(Color.white);
        rYesEquipment.setBounds(275, 160, 80,20);
        sentenceEquipment = styleJTextFieldReservation(sentenceEquipment, 50, 200, 250, 20, Color.white, Color.white);
        sentenceEquipment.setVisible(false);
        JList listeE = new JList(listeEquipment);
        view.add(sentenceEquipment);
        view.add(listeE);
        rYesEquipment.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(input.containsKey("config_equipement_salle1"))input.replace("config_equipement_salle1", "oui");
                else input.put("config_equipement_salle1","oui");

                listeE.setBounds(50, 250, 350, 300);
                listeE.setBackground(Color.red);
                sentenceEquipment.setVisible(true);
                listeE.setVisible(true);
                view.repaint();
            }
        });
        groupEquipment.add(rYesEquipment);
        view.add(rYesEquipment);

        JRadioButton rNonEquipment = new JRadioButton("Non ");
        rNonEquipment.setVisible(true);
        rNonEquipment.setBackground(Color.white);
        rNonEquipment.setBounds(365, 160,80,20);
        rNonEquipment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(input.containsKey("config_equipement_salle1"))input.replace("config_equipement_salle1", "non");
                else input.put("config_equipement_salle1","non");

                if(verifMap()) buttonValidate.setEnabled(true);
                System.out.println(input);
                sentenceEquipment.setVisible(false);
                listeE.setVisible(false);
                view.repaint();
            }
        });
        groupEquipment.add(rNonEquipment);
        view.add(rNonEquipment);


        JTextField titleSensor = new JTextField("Choisissez les capteurs :");
        titleSensor = styleJTextFieldReservation(titleSensor, 450,100,350, 50, Color.white, Color.white);
        titleSensor.setFont(new Font("Serif", Font.BOLD, 20));
        view.add(titleSensor);

        JTextField choiceSensor = new JTextField("- Souhaitez-vous des capteurs ? ");
        choiceSensor = styleJTextFieldReservation(choiceSensor, 450, 160, 175, 20,Color.white, Color.white);
        view.add(choiceSensor);


        ButtonGroup groupSensor = new ButtonGroup();
        JRadioButton rYesSensor = new JRadioButton("Oui");
        rYesSensor.setBounds(675, 160, 80,20);
        rYesSensor.setVisible(true);
        rYesSensor.setBackground(Color.white);
        sentenceSensor = styleJTextFieldReservation(sentenceSensor, 450, 200, 250, 20, Color.white, Color.white);
        sentenceSensor.setVisible(false);
        JList listeS = new JList(listeSensor);
        view.add(sentenceSensor);
        view.add(listeS);
        rYesSensor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(input.containsKey("config_capteur_salle1"))input.replace("config_capteur_salle1", "oui");
                else input.put("config_capteur_salle1","oui");

                listeS.setBackground(Color.red);
                listeS.setBounds(450, 250, 350, 300);
                listeS.setVisible(true);
                sentenceSensor.setVisible(true);
                view.repaint();
            }
        });
        groupSensor.add(rYesSensor);
        view.add(rYesSensor);

        JRadioButton rNoSensor = new JRadioButton("Non ");
        rNoSensor.setVisible(true);
        rNoSensor.setBackground(Color.white);
        rNoSensor.setBounds(760, 160,80,20);
        rNoSensor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(input.containsKey("config_capteur_salle1"))input.replace("config_capteur_salle1", "non");
                else input.put("config_capteur_salle1","non");

                if(verifMap()) buttonValidate.setEnabled(true);
                sentenceSensor.setVisible(false);
                listeS.setVisible(false);
                view.repaint();
            }
        });
        groupSensor.add(rNoSensor);
        view.add(rNoSensor);

        return view;
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

    public boolean verifMap(){
        System.out.println(input);
        if(input.containsKey("config_capteur_salle1") && input.containsKey("config_equipement_salle1"))
            return true;
        else return false;

    }
}
