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


    public void choice(JPanel pb, JPanel oldView){
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
                ViewWithPlan backViewPlan = new ViewWithPlan(frame, input);
                backViewPlan.back(oldView,pb);
            }
        });
        view.add(buttonValidate);
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
        choiceEquipment = styleJTextFieldReservation(choiceEquipment, 50, 160, 200, 20,Color.white, Color.white);
        view.add(choiceEquipment);

        JCheckBox checkEquipment2 = new JCheckBox("Non ");
        checkEquipment2.setBackground(Color.white);
        checkEquipment2.setBounds(365, 160,80,20);
        checkEquipment2.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == 1) {
                    if(input.containsKey("config_equipement_salle1"))input.replace("config_equipement_salle1", "non");
                    else input.put("config_equipement_salle1","non");

                    if(verifMap()) buttonValidate.setEnabled(true);
                    System.out.println(input);
                } else {
                    input.remove("config_equipement_salle1");
                }
            }
        });
        view.add(checkEquipment2);

        JCheckBox checkBoxEquipment = new JCheckBox("Oui ");
        checkBoxEquipment.setBounds(275, 160, 80,20);
        checkBoxEquipment.setBackground(Color.white);
        checkBoxEquipment.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                System.out.println("selected");
                JTextField sentenceEquipment = new JTextField("- Veuillez selectionner les equipements.");
                sentenceEquipment = styleJTextFieldReservation(sentenceEquipment, 50, 200, 250, 20, Color.white, Color.white);

                String[] listeEquipment = {"ordinateur fixe","fenetre", "television connecte"};
                JList listeE = new JList(listeEquipment);
                listeE.setBounds(50, 250, 350, 300);
                listeE.setBackground(Color.red);
                listeE.setVisible(true);
                view.add(sentenceEquipment);
                view.add(listeE);
                view.repaint();
                frame.repaint();
            }
        });
        view.add(checkBoxEquipment);

        JTextField titleSensor = new JTextField("Choisissez les capteurs :");
        titleSensor = styleJTextFieldReservation(titleSensor, 450,100,350, 50, Color.white, Color.white);
        titleSensor.setFont(new Font("Serif", Font.BOLD, 20));
        view.add(titleSensor);

        JTextField choiceSensor = new JTextField("- Souhaitez-vous des capteurs ? ");
        choiceSensor = styleJTextFieldReservation(choiceSensor, 450, 160, 200, 20,Color.white, Color.white);
        view.add(choiceSensor);

        JCheckBox checkBoxSenSor2 = new JCheckBox("Non ");
        checkBoxSenSor2.setBackground(Color.white);
        checkBoxSenSor2.setBounds(760, 160,80,20);
        checkBoxSenSor2.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == 1) {
                    if(input.containsKey("config_capteur_salle1"))input.replace("config_capteur_salle1", "non");
                    else input.put("config_capteur_salle1","non");

                    if(verifMap()) buttonValidate.setEnabled(true);
                    System.out.println(input);
                } else {
                    input.remove("config_capteur_salle1");
                    System.out.println(input);
                }
            }
        });
        view.add(checkBoxSenSor2);

        JCheckBox checkBoxSensor = new JCheckBox("Oui ");
        checkBoxSensor.setBackground(Color.white);
        checkBoxSensor.setBounds(675, 160, 80,20);
        checkBoxSensor.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                System.out.println("selected");
                JTextField sentenceSensor = new JTextField("- Veuillez selectionner les capteurs.");
                sentenceSensor = styleJTextFieldReservation(sentenceSensor, 450, 200, 250, 20, Color.white, Color.white);

                String[] listeSensor = {"capteur de temperature","capteur de luminosite", "capteur de mouvement"};
                JList listeS = new JList(listeSensor);
                listeS.setBackground(Color.red);
                listeS.setBounds(450, 250, 350, 300);
                listeS.setVisible(true);
                view.add(sentenceSensor);
                view.add(listeS);
                view.repaint();
                frame.repaint();
            }
        });
        view.add(checkBoxSensor);

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
