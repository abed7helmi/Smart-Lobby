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
import java.util.*;
import java.util.List;

public class ChoiceDevice {
    private String room_id ="";
    private final JFrame frame;
    private Map<String , String> input = new HashMap<>();
    private final JButton buttonValidate = new JButton("Valider");
    private JPanel pageBody;
    private final String page = "device";
    private JTextField selectionE = new JTextField();
    private final JTextField quantityE = new JTextField();
    private JTextField selectionS = new JTextField();
    private final JTextField quantityS = new JTextField();
    private final JButton validateQuantityE = new JButton("Valider");
    private final JButton validateQuantityS = new JButton("Valider");
    private JTextField messageErrorE = new JTextField();
    private JTextField messageErrorS = new JTextField();
    private String roomName = "";
    private String resultRequest= "";
    private final List<String> listEquipment = new ArrayList<>();
    private final List<String> listSensor = new ArrayList<>();
    private String[] equipementArray;
    private String[] sensorArray;
    private Map<String ,Map<String,String>> configRoom = new HashMap<>();
    private Map<String, Map<String,String>> proposalSelected = new HashMap<>();
    private final Map<String, String> config = new HashMap<>();
    private final List keyConfigSensor  = new ArrayList();
    private final List keyConfigEquipment  = new ArrayList();
    private List listDeviceId = new ArrayList();
    private Map<String, String> listDeviceIdRoom = new HashMap<>();
    private List deviceIdInRoom = new ArrayList();

    public ChoiceDevice(JFrame frame, Map<String, String> input, String id, Map<String, Map<String,String>> configRoom, Map<String, Map<String,String>> ps, List ldI, Map<String, String> listIdRoom) {
        this.frame = frame;
        this.input = input;
        this.room_id = id;
        this.configRoom = configRoom;
        listDeviceIdRoom = listIdRoom;
        proposalSelected = ps;
        listDeviceId = ldI;

        System.out.println("test"+ listDeviceId);


        Client.map.get("requestLocation2").put("room_id", room_id);
        resultRequest = Client.sendBd("requestLocation2");

        String[] value = resultRequest.split(",");
        for(int i = 0; i< value.length; i++){
            if(value[i].contains("capteur")) listSensor.add((value[i]).replace('_', ' '));
            else listEquipment.add(value[i]);
        }
        equipementArray = new String[listEquipment.size()];
        sensorArray = new String[listSensor.size()];

        equipementArray = listEquipment.toArray(equipementArray);
        sensorArray = listSensor.toArray(sensorArray);
    }

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
                String list = deviceIdInRoom.toString();
                listDeviceIdRoom.put(room_id, list);
                advancement.setVisible(false);
                listButton.replace(room, "validated");
                view.setVisible(false);
                room.setBackground(Color.green);
                ViewWithPlan backViewPlan = new ViewWithPlan(frame, input);
                backViewPlan.back(oldView,pb,room,listButton, configRoom, proposalSelected, listDeviceId, listDeviceIdRoom);
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
        JList listeE = new JList(equipementArray);
        JScrollPane scrollEquipment = new JScrollPane(listeE);
        scrollEquipment.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        view.add(scrollEquipment);

        view.add(messageErrorE);
        rYesEquipment.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                config.put("config_equipment", "oui");

                scrollEquipment.setBounds(50, 250, 350, 250);
                listeE.setBackground(Color.white);
                scrollEquipment.setVisible(true);
                listeE.setBorder(new TitledBorder("Veuillez selectionner les equipements."));
                listeE.addListSelectionListener(new ListSelectionListener() {
                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        if(!e.getValueIsAdjusting()){
                            String equipment = ((String)listeE.getSelectedValue()).split("/")[0];
                            String text = (equipment.split("--")[0]).trim();
                            String price = (equipment.split("--")[1].trim()).split("euros")[0].trim();
                            System.out.println("price"+price);

                            selectionE.setText("Quelle quantite pour "+ text +" ?");
                            selectionE = styleJTextFieldReservation(selectionE, 50, 500, 300, 20, Color.white, Color.white);
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
                                    if(verifNumber(value, messageErrorE, text, price)){
                                        validateQuantityE.setVisible(false);
                                        quantityE.setVisible(false);
                                        selectionE.setVisible(false);
                                        quantityE.setText("");
                                        messageErrorE.setVisible(false);
                                        if(verifMap()) buttonValidate.setEnabled(true);
                                    }
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
                if(config.containsKey("config_equipment")){
                    config.replace("config_equipment","non");
                    for(String key : config.keySet()){
                        if( !(key.equals("config_equipment")) && !( key.equals("config_capteur")) )keyConfigEquipment.add(key);
                    }
                    for(int i =0 ; i < keyConfigEquipment.size(); i++){
                        config.remove(keyConfigEquipment.get(i));
                    }
                } else config.put("config_equipment","non");

                if(verifMap()) buttonValidate.setEnabled(true);
                visibleListe(view, scrollEquipment, selectionE,quantityE, validateQuantityE, messageErrorE);
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
        JList listeS = new JList(sensorArray);
        JScrollPane scrollSensor = new JScrollPane(listeS);
        scrollSensor.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        view.add(scrollSensor);
        rYesSensor.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                config.put("config_capteur","oui");

                listeS.setBackground(Color.white);
                scrollSensor.setBounds(450, 250, 350, 250);
                scrollSensor.setVisible(true);
                listeS.setBorder(new TitledBorder("Veuillez selectionner les capteurs."));
                listeS.addListSelectionListener(new ListSelectionListener() {
                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        if(!e.getValueIsAdjusting()){
                            String sensor = ((String)listeS.getSelectedValue()).split("/")[0];
                            String text = (sensor.split("--")[0]).trim();
                            String price = (sensor.split("--")[1].trim()).split("euros")[0].trim();
                            System.out.println("price"+price);

                            selectionS.setText("Quelle quantite pour "+ text +" ?");
                            selectionS = styleJTextFieldReservation(selectionS, 450, 500, 300, 20, Color.white, Color.white);
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
                                    if(verifNumber(value, messageErrorS, text, price)) {
                                        validateQuantityS.setVisible(false);
                                        quantityS.setVisible(false);
                                        selectionS.setVisible(false);
                                        quantityS.setText("");
                                        messageErrorS.setVisible(false);
                                        if(verifMap()) buttonValidate.setEnabled(true);
                                    }
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
                if(config.containsKey("config_capteur")){
                    config.replace("config_capteur","non");
                    for(String key : config.keySet()){
                        if( !( key.equals("config_capteur")) && !( key.equals("config_equipment")) )  keyConfigSensor.add(key);
                    }
                    for(int i =0 ; i < keyConfigSensor.size(); i++){
                        config.remove(keyConfigSensor.get(i));
                    }
                } else config.put("config_capteur","non");

                if(verifMap()) buttonValidate.setEnabled(true);
                visibleListe(view, scrollSensor, selectionS,quantityS, validateQuantityS, messageErrorS);
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
        if(config.containsKey("config_capteur") && config.containsKey("config_equipment")){
            configRoom.put(room_id, config);
            return true;
        }
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
    public void visibleListe(JPanel view, JScrollPane scroll, JTextField selection, JTextField quantity, JButton validate, JTextField messageError){
        scroll.setVisible(false);
        selection.setVisible(false);
        quantity.setVisible(false);
        validate.setVisible(false);
        messageError.setVisible(false);
        view.repaint();
    }

    public boolean verifNumber(String str, JTextField messageError, String text, String price){
        if(str.matches("\\d+") && (Integer.parseInt(str) > 0)){
            Client.map.get("requestLocation3").put("device_wording",text.trim());
            Client.map.get("requestLocation3").put("quantity",str);

            System.out.println("taille des doublons " + listDeviceId);

            for(int i = 0; i < listDeviceId.size(); i++){
                Client.map.get("requestLocation3").put("device"+i, listDeviceId.get(i)+"");
            }

            String verifDispo = Client.sendBd("requestLocation3");
            if(verifDispo.contains(",")){
                String[] value = verifDispo.split(",");
                //List deviceIdInRoom = new ArrayList();
                if(value.length == Integer.parseInt(str)){
                    stockDevice(value,deviceIdInRoom, text,str,messageError, price);
                    return true;
                } else {
                    int result = JOptionPane.showConfirmDialog(null, "On n'a seulement "+ value.length +" . Souhaitez-vous prendre les "+ value.length+ " ?");
                    if( result == JOptionPane.YES_OPTION) {
                        stockDevice(value,deviceIdInRoom, text,str,messageError, price);
                        return true;
                    } else return false;
                }
            } else {
                JOptionPane.showMessageDialog(null,"Nous n'avons plus le composant souhaité");
                return false;
            }
        } else {
            messageError.setText("X");
            messageError.setForeground(Color.RED);
            return false;
        }
    }

    public void stockDevice(String[] value, List deviceIdInRoom, String text, String str, JTextField messageError, String price){
        int count = 0;
        for(int i = 0; i < value.length; i++) {
            listDeviceId.add(value[i]);
            deviceIdInRoom.add(value[i]);
        }

        config.put(text, deviceIdInRoom.size()+"");
        float valuePrice = 0;

        valuePrice = Float.parseFloat(deviceIdInRoom.size()+"") * Float.parseFloat(price);
        if(config.containsKey("price")){
            valuePrice = valuePrice + Float.parseFloat(config.get("price"));
            System.out.println("///////////////////");
            System.out.println(valuePrice);
            System.out.println("///////////////////");
            config.put("price", valuePrice+"");
        } else config.put("price", valuePrice+"");

        messageError.setText(" ");
    }
}
