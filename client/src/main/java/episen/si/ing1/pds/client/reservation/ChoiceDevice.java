package episen.si.ing1.pds.client.reservation;

import episen.si.ing1.pds.client.Client;
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

public class ChoiceDevice extends JFrame{
    private String room_id ="";
    private final JFrame mainFrame;
    private final JFrame frameDevice;
    private Map<String , String> input = new HashMap<>();
    private final JButton buttonValidate = new JButton("> Valider");
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
    private String[] equipmentArray;
    private String[] sensorArray;
    public static Map<String, String> config = new HashMap<>();
    private final List keyConfigSensor  = new ArrayList();
    private final List keyConfigEquipment  = new ArrayList();
    private final List deviceIdInRoom = new ArrayList();

    public ChoiceDevice(JFrame f, String id) {
        mainFrame = f;
        this.room_id = id;
        frameDevice = this;

        Client.map.get("requestLocation2").put("room_id", room_id);
        resultRequest = Client.sendBd("requestLocation2");

        String[] value = resultRequest.split(",");
        for(int i = 0; i< value.length; i++){
            if(value[i].contains("capteur")) listSensor.add((value[i]).replace('_', ' '));
            else listEquipment.add(value[i]);
        }
        equipmentArray = new String[listEquipment.size()];
        sensorArray = new String[listSensor.size()];

        equipmentArray = listEquipment.toArray(equipmentArray);
        sensorArray = listSensor.toArray(sensorArray);

        setSize(800, 700);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }

    public void choice(JButton room){
        roomName = room.getText();

        JPanel pageBody = new JPanel();
        pageBody.setLayout(new BorderLayout());
        pageBody.setBackground(Color.CYAN);
        JPanel view = view();
        view.setBackground(Color.white);
        buttonValidate.setBounds(650, 10, 100, 50);
        buttonValidate.setBackground(new Color(255, 255,255));
        buttonValidate.setForeground(Color.BLACK);
        buttonValidate.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        buttonValidate.setEnabled(false);
        buttonValidate.setEnabled(false);
        buttonValidate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String list = deviceIdInRoom.toString();
                ViewWithPlan.listDeviceIdRoom.put(room_id, list);
                ViewWithPlan.listButton.replace(room, "validated");
                room.setBackground(Color.green);
                ViewWithPlan.verifConfiguration = true;
                for(Map.Entry m : ViewWithPlan.listButton.entrySet()){
                    if( m.getKey().equals("unvalited") ) ViewWithPlan.verifConfiguration = false;
                }
                ViewWithPlan.reload();
                frameDevice.dispose();

                System.out.println("////////");
                System.out.println(ChoiceCriteria.input);
                System.out.println("////////");
                System.out.println(Choice.proposalSelected);
            }
        });
        view.add(buttonValidate);
        view.setVisible(true);
        pageBody.add(view, BorderLayout.CENTER);
        this.getContentPane().add(pageBody);
    }
    public JPanel view(){
        JPanel view = new JPanel();
        view.setBackground(Color.WHITE);
        Ihm.sizeComposant(new Dimension(950,600), view);
        view.setLayout(null);

        roomName = roomName.substring(0,1).toUpperCase() + roomName.substring(1);
        JTextField room = new JTextField(roomName);
        room = Ihm.styleJTextFieldReservation(room, 10,20,350, 50, Color.white, Color.WHITE);
        room.setBorder(BorderFactory.createMatteBorder(0,0, 1, 0, Color.black));
        room.setFont(new Font("Serif", Font.BOLD, 20));
        view.add(room);

        JTextField titleEquipment = new JTextField("Choisissez les equipements :");
        titleEquipment = Ihm.styleJTextFieldReservation(titleEquipment, 10,100,350, 50, Color.white, Color.white);
        titleEquipment.setFont(new Font("Serif", Font.BOLD, 18));
        view.add(titleEquipment);

        JTextField choiceEquipment = new JTextField("- Souhaitez-vous des equipements ? ");
        choiceEquipment = Ihm.styleJTextFieldReservation(choiceEquipment, 10, 160, 200, 20,Color.white, Color.white);
        view.add(choiceEquipment);

        ButtonGroup groupEquipment = new ButtonGroup();
        JRadioButton rYesEquipment = new JRadioButton("Oui ");
        rYesEquipment.setVisible(true);
        rYesEquipment.setBackground(Color.white);
        rYesEquipment.setBounds(225, 160, 65,20);
        JList listE = new JList(equipmentArray);
        JScrollPane scrollEquipment = new JScrollPane(listE);
        scrollEquipment.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        view.add(scrollEquipment);

        view.add(messageErrorE);
        rYesEquipment.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                config.put("config_equipment", "oui");

                scrollEquipment.setBounds(10, 250, 350, 250);
                listE.setBackground(Color.white);
                scrollEquipment.setVisible(true);
                listE.setBorder(new TitledBorder("Veuillez selectionner les equipements."));
                listE.addListSelectionListener(new ListSelectionListener() {
                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        if(!e.getValueIsAdjusting()){
                            String equipment = ((String)listE.getSelectedValue()).split("/")[0];
                            String text = (equipment.split("--")[0]).trim();
                            String price = (equipment.split("--")[1].trim()).split("euros")[0].trim();

                            selectionE.setText("Quelle quantite pour "+ text +" ?");
                            selectionE = Ihm.styleJTextFieldReservation(selectionE, 10, 500, 300, 20, Color.white, Color.white);
                            selectionE.setVisible(true);

                            quantityE.setBackground(Color.white);
                            quantityE.setBounds(310, 505, 30,20);
                            quantityE.setVisible(true);

                            messageErrorE = styleJTextFieldError(view, quantityE.getWidth() + quantityE.getX(), 500, 20, 20);

                            validateQuantityE.setBounds(10,530,100,20);
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
        rNonEquipment.setBounds(295, 160,65,20);
        rNonEquipment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(config.containsKey("config_equipment")){
                    config.replace("config_equipment","non");
                    for(String key : config.keySet()){
                        if( !(key.equals("config_equipment")) && !( key.equals("config_sensor")) )keyConfigEquipment.add(key);
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
        titleSensor = Ihm.styleJTextFieldReservation(titleSensor, 400,100,350, 50, Color.white, Color.white);
        titleSensor.setFont(new Font("Serif", Font.BOLD, 18));
        view.add(titleSensor);

        JTextField choiceSensor = new JTextField("- Souhaitez-vous des capteurs ? ");
        choiceSensor = Ihm.styleJTextFieldReservation(choiceSensor, 400, 160, 175, 20,Color.white, Color.white);
        view.add(choiceSensor);

        ButtonGroup groupSensor = new ButtonGroup();
        JRadioButton rYesSensor = new JRadioButton("Oui");
        rYesSensor.setBounds(625, 160, 65,20);
        rYesSensor.setVisible(true);
        rYesSensor.setBackground(Color.white);
        JList listS = new JList(sensorArray);
        JScrollPane scrollSensor = new JScrollPane(listS);
        scrollSensor.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        view.add(scrollSensor);
        rYesSensor.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                config.put("config_sensor","oui");

                listS.setBackground(Color.white);
                scrollSensor.setBounds(400, 250, 350, 250);
                scrollSensor.setVisible(true);
                listS.setBorder(new TitledBorder("Veuillez selectionner les capteurs."));
                listS.addListSelectionListener(new ListSelectionListener() {
                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        if(!e.getValueIsAdjusting()){
                            String sensor = ((String)listS.getSelectedValue()).split("/")[0];
                            String text = (sensor.split("--")[0]).trim();
                            String price = (sensor.split("--")[1].trim()).split("euros")[0].trim();

                            selectionS.setText("Quelle quantite pour "+ text +" ?");
                            selectionS = Ihm.styleJTextFieldReservation(selectionS, 400, 500, 300, 20, Color.white, Color.white);
                            selectionS.setVisible(true);

                            quantityS.setBackground(Color.white);
                            quantityS.setBounds(700, 505, 30,20);
                            quantityS.setVisible(true);

                            messageErrorS = styleJTextFieldError(view, quantityS.getWidth() + quantityS.getX(), 500, 20, 20);

                            validateQuantityS.setBounds(400,530,100,20);
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
        rNoSensor.setBounds(710, 160,65,20);
        rNoSensor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(config.containsKey("config_sensor")){
                    config.replace("config_sensor","non");
                    for(String key : config.keySet()){
                        if( !( key.equals("config_sensor")) && !( key.equals("config_equipment")) )  keyConfigSensor.add(key);
                    }
                    for(int i =0 ; i < keyConfigSensor.size(); i++){
                        config.remove(keyConfigSensor.get(i));
                    }
                } else config.put("config_sensor","non");

                if(verifMap()) buttonValidate.setEnabled(true);
                visibleListe(view, scrollSensor, selectionS,quantityS, validateQuantityS, messageErrorS);
            }
        });
        groupSensor.add(rNoSensor);
        view.add(rNoSensor);
        return view;
    }

    public boolean verifMap(){
        if(config.containsKey("config_sensor") && config.containsKey("config_equipment")){
            ViewWithPlan.configRoom.put(room_id, config);
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

            for(int i = 0; i < ViewWithPlan.listDeviceId.size(); i++){
                Client.map.get("requestLocation3").put("device"+i, ViewWithPlan.listDeviceId.get(i)+"");
            }

            String verifyDispo = Client.sendBd("requestLocation3");
            if(verifyDispo.contains(",")){
                String[] value = verifyDispo.split(",");
                //List deviceIdInRoom = new ArrayList();
                if(value.length == Integer.parseInt(str)){
                    stockDevice(value,deviceIdInRoom, text,messageError, price);
                    return true;
                } else {
                    int result = JOptionPane.showConfirmDialog(null, "On n'a seulement "+ value.length +" . Souhaitez-vous prendre les "+ value.length+ " ?");
                    if( result == JOptionPane.YES_OPTION) {
                        stockDevice(value,deviceIdInRoom, text,messageError, price);
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

    public void stockDevice(String[] value, List deviceIdInRoom, String text, JTextField messageError, String price){
        for(int i = 0; i < value.length; i++) {
            ViewWithPlan.listDeviceId.add(value[i]);
            deviceIdInRoom.add(value[i]);
        }

        config.put(text, deviceIdInRoom.size()+"");
        float valuePrice = 0;

        valuePrice = Float.parseFloat(deviceIdInRoom.size()+"") * Float.parseFloat(price);
        if(config.containsKey("price")){
            valuePrice = valuePrice + Float.parseFloat(config.get("price"));
            config.put("price", valuePrice+"");
        } else config.put("price", valuePrice+"");
        messageError.setText(" ");
    }
}
