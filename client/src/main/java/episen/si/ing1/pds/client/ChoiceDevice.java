package episen.si.ing1.pds.client;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChoiceDevice {
    public ChoiceDevice(JFrame frame, Map<String, String> input) {
        this.frame = frame;
        this.input = input;
    }
    private final JFrame frame;
    private Map<String , String> input = new HashMap<>();
    private final JButton buttonValidate = new JButton("Valider");
    private JPanel pageBody;
    private final String page = "device";
    private String[] listeEquipment = {"ordinateur fixe","fenetre", "television connecte"};
    private String[] listeSensor = {"capteur de temperature","capteur de luminosite", "capteur de mouvement"};
    private JTextField selectionE = new JTextField();
    private JTextField quantityE = new JTextField();
    private JTextField selectionS = new JTextField();
    private JTextField quantityS = new JTextField();
    private JButton validateQuantityE = new JButton("Valider");
    private JButton validateQuantityS = new JButton("Valider");
    private JTextField messageErrorE = new JTextField();
    private JTextField messageErrorS = new JTextField();
    private String roomName = "";

    //private ArrayList<Map <String, String>> array = new ArrayList<>();



    public void choice(JPanel pb, JPanel oldView, JButton room, Map<JButton,String> listButton){
        roomName = room.getText();

        this.pageBody = pb;
        pageBody.setBackground(Color.CYAN);
        JPanel view = view();
        view.setBackground(Color.white);
        RentalAdvancement rentalAdvancement = new RentalAdvancement(page);
        JPanel advancement = rentalAdvancement.rentalAdvancement();
        pageBody.add(advancement, BorderLayout.CENTER);
        buttonValidate.setBounds(780, 10, 100, 50);
        buttonValidate.setEnabled(false);
        buttonValidate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                advancement.setVisible(false);
                listButton.replace(room, "validated");
                view.setVisible(false);
                room.setBackground(Color.green);
                ViewWithPlan backViewPlan = new ViewWithPlan(frame, input);
                backViewPlan.back(oldView,pb,room,listButton);
            }
        });
        view.add(buttonValidate);
        view.setVisible(true);
        pageBody.add(view, BorderLayout.SOUTH);
        pageBody.repaint();
        frame.repaint();
    }
    public JPanel view(){
        JPanel view = new JPanel();
        view.setBackground(Color.WHITE);
        sizeComposant(new Dimension(950,600), view);
        view.setLayout(null);

        JTextField room = new JTextField(roomName);
        room = styleJTextFieldReservation(room, 50,20,350, 50, Color.white, Color.white);
        view.add(room);

        JTextField titleEquipment = new JTextField("Choisissez les equipements :");
        titleEquipment = styleJTextFieldReservation(titleEquipment, 50,100,350, 50, Color.white, Color.white);
        titleEquipment.setFont(new Font("Serif", Font.BOLD, 20));
        view.add(titleEquipment);

        JTextField choiceEquipment = new JTextField("- Souhaitez-vous des equipements ? ");
        choiceEquipment = styleJTextFieldReservation(choiceEquipment, 50, 160, 200, 20,Color.white, Color.white);
        view.add(choiceEquipment);

        ButtonGroup groupEquipment = new ButtonGroup();
        JRadioButton rYesEquipment = new JRadioButton("Oui ");
        rYesEquipment.setVisible(true);
        rYesEquipment.setBackground(Color.white);
        rYesEquipment.setBounds(275, 160, 80,20);
        JList listeE = new JList(listeEquipment);

        view.add(listeE);

        view.add(messageErrorE);
        rYesEquipment.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(input.containsKey("config_equipement_" + roomName))input.replace("config_equipement_" + roomName, "oui");
                else input.put("config_equipement_" + roomName,"oui");

                listeE.setBounds(50, 250, 350, 250);
                listeE.setBackground(Color.white);
                listeE.setVisible(true);
                listeE.setBorder(new TitledBorder("Veuillez selectionner les equipements."));
                listeE.addListSelectionListener(new ListSelectionListener() {
                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        if(!e.getValueIsAdjusting()){
                            String text = (String)listeE.getSelectedValue();
                            selectionE.setText("Quelle quantite pour "+ text +" ?");
                            selectionE = styleJTextFieldReservation(selectionE, 50, 500, 250, 20, Color.white, Color.white);
                            selectionE.setVisible(true);

                            quantityE.setBackground(Color.white);
                            quantityE.setBounds(350, 505, 30,20);
                            quantityE.setVisible(true);

                            messageErrorE = styleJTextFieldError(view, quantityE.getWidth() + quantityE.getX(), 500, 20, 20);

                            validateQuantityE.setBounds(50,530,100,20);
                            validateQuantityE.setVisible(true);
                            validateQuantityE.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    String value = quantityE.getText().trim();
                                    verifNumber(value, input, messageErrorE, text);
                                    validateQuantityE.setVisible(false);
                                    quantityE.setVisible(false);
                                    selectionE.setVisible(false);
                                    quantityE.setText("");
                                }
                            });
                        }
                    }
                });
                view.repaint();
            }
        });
        view.add(validateQuantityE);
        view.add(quantityE);
        view.add(selectionE);
        groupEquipment.add(rYesEquipment);
        view.add(rYesEquipment);

        JRadioButton rNonEquipment = new JRadioButton("Non ");
        rNonEquipment.setVisible(true);
        rNonEquipment.setBackground(Color.white);
        rNonEquipment.setBounds(365, 160,80,20);
        rNonEquipment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                input.put("config_equipement_" + roomName,"non");

                if(verifMap()) buttonValidate.setEnabled(true);
                visibleListe(view, listeE, selectionE,quantityE, validateQuantityE, messageErrorE);
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
        JList listeS = new JList(listeSensor);
        view.add(listeS);
        rYesSensor.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                input.put("config_capteur_" + roomName,"oui");

                listeS.setBackground(Color.white);
                listeS.setBounds(450, 250, 350, 250);
                listeS.setVisible(true);
                listeS.setBorder(new TitledBorder("Veuillez selectionner les capteurs."));
                listeS.addListSelectionListener(new ListSelectionListener() {
                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        if(!e.getValueIsAdjusting()){
                            String text = (String)listeS.getSelectedValue();
                            selectionS.setText("Quelle quantite pour "+ text +" ?");
                            selectionS = styleJTextFieldReservation(selectionS, 450, 500, 250, 20, Color.white, Color.white);
                            selectionS.setVisible(true);

                            quantityS.setBackground(Color.white);
                            quantityS.setBounds(750, 505, 30,20);
                            quantityS.setVisible(true);

                            messageErrorS = styleJTextFieldError(view, quantityS.getWidth() + quantityS.getX(), 500, 20, 20);

                            validateQuantityS.setBounds(450,530,100,20);
                            validateQuantityS.setVisible(true);
                            validateQuantityS.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    String value = quantityS.getText().trim();
                                    verifNumber(value, input, messageErrorS, text);
                                    validateQuantityS.setVisible(true);
                                    quantityS.setVisible(true);
                                    selectionS.setVisible(true);
                                    quantityS.setText("");
                                }
                            });
                        }
                    }
                });
                view.repaint();
            }
        });
        view.add(validateQuantityS);
        view.add(quantityS);
        view.add(selectionS);
        groupSensor.add(rYesSensor);
        view.add(rYesSensor);

        JRadioButton rNoSensor = new JRadioButton("Non ");
        rNoSensor.setVisible(true);
        rNoSensor.setBackground(Color.white);
        rNoSensor.setBounds(760, 160,80,20);
        rNoSensor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                input.put("config_capteur_" + roomName,"non");

                if(verifMap()) buttonValidate.setEnabled(true);
                visibleListe(view, listeS, selectionS,quantityS, validateQuantityS, messageErrorS);
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
        if(input.containsKey("config_capteur_"+ roomName) && input.containsKey("config_equipement_"+ roomName))
            return true;
        else return false;
    }
    public JTextField styleJTextFieldError(JPanel view, int x, int y, int w, int h) {
        JTextField t = new JTextField();
        t.setEditable(false);
        t.setBackground(Color.WHITE);
        t.setForeground(Color.RED);
        t.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        t.setBounds(x, y, w, h);
        view.add(t);
        return t;
    }
    public void visibleListe(JPanel view, JList list, JTextField selection, JTextField quantity, JButton validate, JTextField messageError){
        list.setVisible(false);
        selection.setVisible(false);
        quantity.setVisible(false);
        validate.setVisible(false);
        messageError.setVisible(false);
        view.repaint();
    }

    public void verifNumber(String str, Map<String, String> input, JTextField messageError, String text){
        if(str.matches("\\d+") && (Integer.parseInt(str) > 0)){
            if(input.containsKey(text))input.replace(text,str);
            else input.put(text, str);

            messageError.setText(" ");
        } else {
            messageError.setText("X");
            messageError.setForeground(Color.RED);
        }
    }
}
