package episen.si.ing1.pds.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ChoiceCriteria{
    private Map<String, String> input = new HashMap<>();
    private final String page = "criteria";
    private JButton buttonContinue = new JButton("Continuer");
    private JPanel pageBody = new JPanel();
    private JFrame frame;
    private JCheckBox checkBoxOpenSpace = new JCheckBox();
    private JCheckBox checkBoxMeetingRoom = new JCheckBox();
    private JCheckBox checkBoxClosedOffice = new JCheckBox();
    private JCheckBox checkBoxSingleOffice = new JCheckBox();
    private JTextField valueOpenSpace = new JTextField();
    private JTextField valueClosedOffice = new JTextField();
    private JTextField valueMeetingRoom = new JTextField();
    private JTextField valueSingleOffice = new JTextField();

    public ChoiceCriteria(JFrame f)  {
        input.clear();
        frame = f;
    }

    public void realizeReservation(){
        frame.setSize(1200, 800);
        pageBody.setLayout(new BorderLayout());
        Menu menu = new Menu();
        TitleReservation title = new TitleReservation();

        pageBody.add(title.titleReservation(), BorderLayout.NORTH);
        RentalAdvancement rentalAdvancement = new RentalAdvancement(page);
        JPanel advancement = rentalAdvancement.rentalAdvancement();
        pageBody.add(advancement, BorderLayout.CENTER);

        JPanel choice = reservationCritere();
        buttonContinue.setEnabled(false);
        buttonContinue.setBounds(780, 500, 100, 50);
        buttonContinue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(input+"avant modif");
                boolean changeValue = false;
                boolean verifOpenSpace = input.containsKey("numberOpenSpace");
                boolean verifClosedOffice = input.containsKey("numberClosedOffice");
                boolean verifSingleOffice = input.containsKey("numberSingleOffice");
                boolean verifMeetingRoom = input.containsKey("numberMeetingRoom");
                String sauvOpenSpace = "", sauvClosedOffice = "";
                if(!verifOpenSpace) input.put("numberOpenSpace", "0");
                else {
                    sauvOpenSpace = input.get("numberOpenSpace");
                }
                if(!verifClosedOffice) input.put("numberClosedOffice", "0");
                else {
                    sauvClosedOffice = input.get("numberClosedOffice");
                }
                if(!verifSingleOffice) input.put("numberSingleOffice", "0");


                int nbrEmployee = Integer.parseInt(input.get("numberEmployee"));
                int somme = (Integer.parseInt(input.get("numberOpenSpace")) * 50) + (Integer.parseInt(input.get("numberClosedOffice")) * 20)
                        + (Integer.parseInt(input.get("numberSingleOffice")));

                if( somme + 50 < Integer.parseInt(input.get("numberEmployee"))) {
                    int newValue = ((Integer.parseInt(input.get("numberEmployee")) - somme) / 50) + 1 + (Integer.parseInt(input.get("numberOpenSpace")));
                    input.replace("numberOpenSpace", newValue+"");
                    changeValue = true;
                } else if(  somme + 20 < Integer.parseInt(input.get("numberEmployee"))) {
                    input.replace("numberOpenSpace", Integer.parseInt(input.get("numberOpenSpace")) + 1+"" );
                    changeValue = true;
                } else if( somme < Integer.parseInt(input.get("numberEmployee"))){
                    int newValue = ((Integer.parseInt(input.get("numberEmployee")) - somme) / 20) + 1 +  (Integer.parseInt(input.get("numberClosedOffice")));
                    input.replace("numberClosedOffice", newValue+"");
                    changeValue = true;
                }
                System.out.println(input+"apres modif");
                if(changeValue){
                    int result = JOptionPane.showConfirmDialog(null, "Au vue des donnees que vous avez rentre, nous avons ajuste votre demande par rapport a la capacite de chaque piece");
                    System.out.println("space "+ sauvOpenSpace);
                    if(result == JOptionPane.NO_OPTION){
                        System.out.println("test "+ sauvOpenSpace);
                        input.replace("numberOpenSpace", sauvOpenSpace);
                        input.replace("numberClosedOffice", sauvClosedOffice);
                    }
                }
                System.out.println(input+"apres confirm");
                choice.setVisible(false);
                advancement.setVisible(false);
                pageBody.repaint();
                changePage();
            }
        });
        choice.add(buttonContinue);
        pageBody.add(choice, BorderLayout.SOUTH);
        pageBody.setBackground(Color.WHITE);
        frame.getContentPane().add(menu.menu(), BorderLayout.WEST);
        frame.getContentPane().add(pageBody, BorderLayout.CENTER);
        frame.repaint();
    }

    public JPanel reservationCritere(){

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        JPanel choice = new JPanel();
        choice.setLayout(null);
        Dimension dimChoice = new Dimension(950, 600);
        sizeComposant(dimChoice, choice);
        choice.setBackground(Color.WHITE);
        choice.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        //label of date
        JTextField startDate = new JTextField("Veuillez indiquer la date de debut : (YYYY-MM-DD) ");
        startDate = styleJTextFieldReservation(startDate, 20, 80, 275, 20);

        JTextField endDate = new JTextField("Veuillez indiquer la date de fin : (YYYY-MM-DD)");
        endDate = styleJTextFieldReservation(endDate,20, 110, 275, 20);

        JTextField valueStartDate = new JTextField(" ");
        valueStartDate.setBounds(350, 80, 100, 20);
        JTextField messageErrorStartDate = styleJTextFieldError(choice ,470, 80, 170, 20);
        valueStartDate.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {}
            @Override
            public void focusLost(FocusEvent e) {
                Object source = e.getSource();
                String startDate = (((JTextField)source).getText()).trim();
                if(startDate.matches("[0-9]{4}-[0-1]{1}[0-9]{1}-[0-3]{1}[0-9]{1}")) {
                    try {
                        Date today1 = dateFormat.parse(dateFormat.format(new Date()));
                        Date date1 = dateFormat.parse(startDate);
                        if(today1.equals(date1) || today1.before(date1)){
                            if(input.containsKey("start_date"))input.replace("start_date", ((JTextField)source).getText().trim());
                            else input.put("start_date", ((JTextField)source).getText().trim());
                            ((JTextField)choice.getComponentAt(470, 80)).setText(" ");
                            if(verifMap()) buttonContinue.setEnabled(true);
                        } else {
                            messageErrorStartDate.setText("Veuillez rentrer une date valide");
                            messageErrorStartDate.setForeground(Color.red);
                        }
                    } catch(Exception a) {a.printStackTrace();}
                }else {
                    messageErrorStartDate.setText("Veuillez respecter le format");
                    messageErrorStartDate.setForeground(Color.red);
                }
            }
        });

        JTextField valueEndDate = new JTextField(" ");
        valueEndDate.setBounds(350, 110, 100, 20);
        JTextField messageErrorEndDate = styleJTextFieldError(choice ,470, 110, 170, 20);
        valueEndDate.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {}
            @Override
            public void focusLost(FocusEvent e) {
                Object source = e.getSource();
                String m = (((JTextField)source).getText()).trim();
                if(m.matches("[0-9]{4}-[0-1]{1}[0-9]{1}-[0-1]{1}[0-9]{1}")) {
                    try {
                        Date today1 = dateFormat.parse(dateFormat.format(new Date()));
                        Date dateEnd = dateFormat.parse(m);
                        if(input.containsKey("start_date") && dateEnd.before(dateFormat.parse(input.get("start_date"))) ){
                            messageErrorEndDate.setText("Erreur par rapport a l'autre date");
                            messageErrorEndDate.setForeground(Color.red);
                        } else if(today1.equals(dateEnd) || today1.before(dateEnd)){
                            if(input.containsKey("end_date"))input.replace("end_date", ((JTextField)source).getText().trim());
                            else input.put("end_date", ((JTextField)source).getText().trim());
                            messageErrorEndDate.setText(" ");
                            if(verifMap()) buttonContinue.setEnabled(true);
                        } else {
                            messageErrorEndDate.setText("Veuillez rentrer une date valide");
                            messageErrorEndDate.setForeground(Color.red);
                        }
                    } catch(Exception a) {a.printStackTrace();}
                }else {
                    messageErrorEndDate.setText("Veuillez respecter le format");
                    messageErrorEndDate.setForeground(Color.red);
                }
            }
        });

        JLabel dateLabel = new JLabel("Date : ");
        dateLabel = styleJLabelReservation(dateLabel, 20, 20,250,50);

        choice.add(dateLabel);
        choice.add(startDate);
        choice.add(valueStartDate);
        choice.add(endDate);
        choice.add(valueEndDate);

        //number of employee
        JButton buttonValidate = new JButton("Faire une proposition");
        JLabel nbEmployee = new JLabel("Nombre de collaborateur :");
        nbEmployee = styleJLabelReservation(nbEmployee,20, 140,250,50);

        JTextField nbEmployeeTextField = new JTextField("Veuillez indiquer un nombre de collaborateurs au maximun : ");
        nbEmployeeTextField = styleJTextFieldReservation(nbEmployeeTextField, 20, 200, 350, 20);

        JTextField valueEmployee = new JTextField(" ");
        valueEmployee.setBounds(370, 200, 50, 20);
        JTextField messageErrorEmployee = styleJTextFieldError(choice, 420, 200, 20, 20);
        valueEmployee.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {}
            @Override
            public void focusLost(FocusEvent e) {
                Object source = e.getSource();
                String m = (((JTextField)source).getText()).trim();
                if(m.matches("\\d+") && (Integer.parseInt(m) > 0)) {
                    if(input.containsKey("numberEmployee"))input.replace("numberEmployee",((JTextField)source).getText().trim());
                    else input.put("numberEmployee",((JTextField)source).getText().trim());
                    messageErrorEmployee.setText(" ");
                    if(verifMap()) buttonContinue.setEnabled(true);
                    buttonValidate.setEnabled(true);

                    valueOpenSpace.setText("");
                    valueMeetingRoom.setText("");
                    valueClosedOffice.setText("");
                    valueSingleOffice.setText("");
                }else{
                    messageErrorEmployee.setText("X");
                    messageErrorEmployee.setForeground(Color.RED);
                }
            }
        });

        choice.add(nbEmployeeTextField);
        choice.add(valueEmployee);
        choice.add(nbEmployee);

        //label of room
        JLabel roomLabel = new JLabel("Les salles : ");
        roomLabel = styleJLabelReservation(roomLabel,20, 230,250,50);

        JTextField openSpace = new JTextField("Open-space : ");
        openSpace = styleJTextFieldReservation(openSpace,20, 290, 100, 20);

        checkBoxOpenSpace = styleJCheckBoxReservation(checkBoxOpenSpace, 120, 290, 20, 20);

        JTextField quantityOpenSpace = new JTextField("- nombre d'open-space : ");
        quantityOpenSpace = styleJTextFieldReservation(quantityOpenSpace,380, 290, 175, 20);

        valueOpenSpace.setText("");
        valueOpenSpace.setBackground(Color.white);
        valueOpenSpace.setBounds(555, 290, 30, 20);
        choice.add(quantityOpenSpace);
        choice.add(valueOpenSpace);
        choice.repaint();

        checkBoxOpenSpace.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                JTextField messageErrorOpenSpace = styleJTextFieldError(choice,585, 290, 20, 20);
                valueOpenSpace.addFocusListener(new FocusListener() {
                    @Override
                    public void focusGained(FocusEvent e) {}
                    @Override
                    public void focusLost(FocusEvent e) {
                        Object source = e.getSource();
                        String m = (((JTextField)source).getText()).trim();
                        if(m.matches("\\d+")) {
                            if(input.containsKey("numberOpenSpace"))input.replace("numberOpenSpace", ((JTextField)source).getText().trim());
                            else input.put("numberOpenSpace", ((JTextField)source).getText().trim());
                            messageErrorOpenSpace.setText(" ");
                            if(verifMap()) buttonContinue.setEnabled(true);
                        }else {
                            messageErrorOpenSpace.setText("X");
                            messageErrorOpenSpace.setForeground(Color.red);
                        }
                    }
                });
            }
        });

        JTextField meetingRoom = new JTextField("Salle de reunion : ");
        meetingRoom = styleJTextFieldReservation(meetingRoom, 20, 320, 100, 20);

        checkBoxMeetingRoom = styleJCheckBoxReservation(checkBoxMeetingRoom, 120, 320, 20, 20);

        JTextField quantityMeetingRoom = new JTextField("- nombre de salle de reunion : ");
        quantityMeetingRoom = styleJTextFieldReservation(quantityMeetingRoom,380, 320, 175, 20);

        valueMeetingRoom.setText("");
        valueMeetingRoom.setBackground(Color.white );
        valueMeetingRoom.setBounds(555, 320, 30, 20);
        choice.add(valueMeetingRoom);
        choice.add(quantityMeetingRoom);
        choice.repaint();

        checkBoxMeetingRoom.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                JTextField messageErrorOpenSpace = styleJTextFieldError(choice,585, 320, 20, 20);
                valueMeetingRoom.addFocusListener(new FocusListener() {
                    @Override
                    public void focusGained(FocusEvent e) {}
                    @Override
                    public void focusLost(FocusEvent e) {
                        Object source = e.getSource();
                        String m = (((JTextField)source).getText()).trim();
                        if(m.matches("\\d+")) {
                            if(input.containsKey("numberMeetingRoom"))input.replace("numberMeetingRoom",((JTextField)source).getText().trim());
                            else input.put("numberMeetingRoom",((JTextField)source).getText().trim());
                            messageErrorOpenSpace.setText(" ");
                            if(verifMap()) buttonContinue.setEnabled(true);
                        }else {
                            messageErrorOpenSpace.setText("X");
                            messageErrorOpenSpace.setForeground(Color.RED);
                        }
                    }
                });
            }
        });

        JTextField singleOffice = new JTextField("Bureau individuel : ");
        singleOffice = styleJTextFieldReservation(singleOffice, 200, 290, 100, 20);

        checkBoxSingleOffice = styleJCheckBoxReservation(checkBoxSingleOffice,300, 290, 20, 20);

        JTextField quantitySingleOffice = new JTextField("- nombre de bureau individuel : ");
        quantitySingleOffice = styleJTextFieldReservation(quantitySingleOffice,620, 320, 175, 20);

        valueSingleOffice.setText("");
        valueSingleOffice.setBackground(Color.white);
        valueSingleOffice.setBounds(795, 320, 30, 20);
        choice.add(quantitySingleOffice);
        choice.add(valueSingleOffice);
        choice.repaint();

        checkBoxSingleOffice.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                JTextField messageErrorSingleoffice = styleJTextFieldError(choice, 825, 320, 20, 20);
                valueSingleOffice.addFocusListener(new FocusListener() {
                    @Override
                    public void focusGained(FocusEvent e) {}
                    @Override
                    public void focusLost(FocusEvent e) {
                        Object source = e.getSource();
                        String m = (((JTextField)source).getText()).trim();
                        if(m.matches("\\d+")) {
                            if(input.containsKey("numberSingleOffice"))input.replace("numberSingleOffice",((JTextField)source).getText().trim());
                            else input.put("numberSingleOffice",((JTextField)source).getText().trim());
                            messageErrorSingleoffice.setText(" ");
                            if(verifMap()) buttonContinue.setEnabled(true);
                        }else {
                            messageErrorSingleoffice.setText("X");
                            messageErrorSingleoffice.setForeground(Color.red);
                        }
                    }
                });
            }
        });

        JTextField closedOffice = new JTextField("Bureau ferme : ");
        closedOffice = styleJTextFieldReservation(closedOffice, 200, 320, 100, 20);

        checkBoxClosedOffice = styleJCheckBoxReservation(checkBoxClosedOffice,300, 320, 20, 20);

        JTextField quantityClosedOffice = new JTextField("- nombre de bureau ferme : ");
        quantityClosedOffice = styleJTextFieldReservation(quantityClosedOffice,620, 290, 175, 20);

        valueClosedOffice.setText("");
        valueClosedOffice.setBackground(Color.white);
        valueClosedOffice.setBounds(795, 290, 30, 20);
        choice.add(quantityClosedOffice);
        choice.add(valueClosedOffice);
        choice.repaint();

        checkBoxClosedOffice.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                JTextField messageErrorClosedOffice = styleJTextFieldError(choice, 825, 290, 20, 20);
                valueClosedOffice.addFocusListener(new FocusListener() {
                    @Override
                    public void focusGained(FocusEvent e) {}
                    @Override
                    public void focusLost(FocusEvent e) {
                        Object source = e.getSource();
                        String m = (((JTextField)source).getText()).trim();
                        if(m.matches("\\d+")) {
                            if(input.containsKey("numberClosedOffice"))input.replace("numberClosedOffice",((JTextField)source).getText().trim());
                            else input.put("numberClosedOffice",((JTextField)source).getText().trim());
                            messageErrorClosedOffice.setText(" ");
                            if(verifMap()) buttonContinue.setEnabled(true);
                        }else {
                            messageErrorClosedOffice.setText("X");
                            messageErrorClosedOffice.setForeground(Color.red);
                        }
                    }
                });
            }
        });

        buttonValidate.setBounds(75, 350, 175,50);
        buttonValidate.setEnabled(false);
        buttonValidate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int nbrOpenSpace, nbrClosedOffice;
                if(checkBoxOpenSpace.isSelected() && checkBoxClosedOffice.isSelected()){
                    nbrOpenSpace = (Integer.parseInt(input.get("numberEmployee")) / 50);
                    if( nbrOpenSpace % 20 == 0) nbrClosedOffice = nbrOpenSpace / 20;
                    else nbrClosedOffice = ( (Integer.parseInt(input.get("numberEmployee")) - (50 * nbrOpenSpace))/ 20) + 1;
                    valueClosedOffice.setText(nbrClosedOffice+"");
                    valueOpenSpace.setText(nbrOpenSpace+"");

                    if(input.containsKey("numberClosedOffice"))input.replace("numberClosedOffice",nbrClosedOffice+"");
                    else input.put("numberClosedOffice",nbrClosedOffice+"");

                    if(input.containsKey("numberOpenSpace"))input.replace("numberOpenSpace", nbrOpenSpace+"");
                    else input.put("numberOpenSpace", nbrOpenSpace+"");

                    if(verifMap()) buttonContinue.setEnabled(true);

                }else if( (checkBoxClosedOffice.isSelected())) {
                    if( (Integer.parseInt(input.get("numberEmployee")) % 20) == 0) nbrClosedOffice = (Integer.parseInt(input.get("numberEmployee")) / 20);
                    else nbrClosedOffice = (Integer.parseInt(input.get("numberEmployee")) / 20) + 1;
                    valueClosedOffice.setText(nbrClosedOffice+"");

                    if(input.containsKey("numberClosedOffice"))input.replace("numberClosedOffice",nbrClosedOffice+"");
                    else input.put("numberClosedOffice",nbrClosedOffice+"");

                    if(verifMap()) buttonContinue.setEnabled(true);

                } else if( (checkBoxOpenSpace.isSelected())) {
                    if( (Integer.parseInt(input.get("numberEmployee")) % 50) == 0) nbrOpenSpace = (Integer.parseInt(input.get("numberEmployee")) / 50);
                    else nbrOpenSpace = (Integer.parseInt(input.get("numberEmployee")) / 50) + 1;
                    valueOpenSpace.setText(nbrOpenSpace+"");

                    if(input.containsKey("numberOpenSpace"))input.replace("numberOpenSpace", nbrOpenSpace+"");
                    else input.put("numberOpenSpace", nbrOpenSpace+"");

                    if(verifMap()) buttonContinue.setEnabled(true);
                } else if( !(checkBoxClosedOffice.isSelected()) && !(checkBoxOpenSpace.isSelected())) {
                    if(Integer.parseInt(input.get("numberEmployee")) >= 50){
                        if( (Integer.parseInt(input.get("numberEmployee")) % 50) == 0) nbrOpenSpace = (Integer.parseInt(input.get("numberEmployee")) / 50);
                        else nbrOpenSpace = (Integer.parseInt(input.get("numberEmployee")) / 50) + 1;
                        valueOpenSpace.setText(nbrOpenSpace+"");

                        if(input.containsKey("numberOpenSpace"))input.replace("numberOpenSpace", nbrOpenSpace+"");
                        else input.put("numberOpenSpace", nbrOpenSpace+"");
                    } else {
                        if( (Integer.parseInt(input.get("numberEmployee")) % 20) == 0) nbrClosedOffice = (Integer.parseInt(input.get("numberEmployee")) / 20);
                        else nbrClosedOffice = (Integer.parseInt(input.get("numberEmployee")) / 20) + 1;
                        valueClosedOffice.setText(nbrClosedOffice+"");

                        if(input.containsKey("numberClosedOffice"))input.replace("numberClosedOffice",nbrClosedOffice+"");
                        else input.put("numberClosedOffice",nbrClosedOffice+"");
                    }
                    if(verifMap()) buttonContinue.setEnabled(true);
                    System.out.println(input);
                }
            }
        });

        choice.add(roomLabel);
        choice.add(openSpace);
        choice.add(checkBoxOpenSpace);
        choice.add(meetingRoom);
        choice.add(checkBoxMeetingRoom);
        choice.add(singleOffice);
        choice.add(checkBoxSingleOffice);
        choice.add(closedOffice);
        choice.add(checkBoxClosedOffice);
        choice.add(buttonValidate);

        //location
        JLabel locationLabel = new JLabel("Situation geographique :");
        locationLabel = styleJLabelReservation(locationLabel,20, 420,250,50);

        JTextField locationTextField = new JTextField("Veuillez indiquer un ou plusieurs lieux geographiques : ");
        locationTextField = styleJTextFieldReservation(locationTextField, 20, 480, 300, 20);

        JCheckBox locationNorth = new JCheckBox("Nord de la ville");
        locationNorth = styleJCheckBoxReservation(locationNorth,20, 510, 150, 20);
        locationNorth.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(input.containsKey("north"))input.remove("north");
                else input.put("north", "yes");
            }
        });

        JCheckBox locationSouth = new JCheckBox("Sud de la ville");
        locationSouth = styleJCheckBoxReservation(locationSouth,20, 540, 150, 20);
        locationSouth.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(input.containsKey("south"))input.remove("south");
                else input.put("south", "yes");
            }
        });

        JCheckBox locationEast = new JCheckBox("Est de la ville");
        locationEast = styleJCheckBoxReservation(locationEast,180, 510, 150, 20);
        locationEast.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(input.containsKey("east"))input.remove("east");
                else input.put("east", "yes");
            }
        });

        JCheckBox locationWest = new JCheckBox("Ouest de la ville");
        locationWest = styleJCheckBoxReservation(locationWest,180, 540, 150, 20);
        locationWest.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(input.containsKey("west"))input.remove("west");
                else input.put("west", "yes");
            }
        });

        JCheckBox locationCenter = new JCheckBox("Centre de la ville");
        locationCenter = styleJCheckBoxReservation(locationCenter,340, 510, 150, 20);
        locationCenter.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(input.containsKey("center"))input.remove("center");
                else input.put("center", "yes");
            }
        });

        JCheckBox locationAnyway = new JCheckBox("Peu importe");
        locationAnyway = styleJCheckBoxReservation(locationAnyway,340, 540, 150, 20);

        choice.add(locationLabel);
        choice.add(locationTextField);
        choice.add(locationAnyway);
        choice.add(locationCenter);
        choice.add(locationNorth);
        choice.add(locationSouth);
        choice.add(locationWest);
        choice.add(locationEast);

        return choice;
    }

    public JLabel styleJLabelReservation(JLabel l, int x, int y, int w, int h){
        sizeComposant(new Dimension(200, 200) ,l);
        l.setBorder(BorderFactory.createMatteBorder(0,0, 1, 0, Color.black));
        l.setBounds(x,y,w,h);
        l.setFont(new Font("Serif", Font.BOLD, 20));
        return l;
    }
    public JTextField styleJTextFieldReservation(JTextField t, int x, int y, int w, int h) {
        t.setEditable(false);
        t.setBackground(Color.WHITE);
        t.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        t.setBounds(x, y, w, h);
        return t;
    }
    public JTextField styleJTextFieldError(JPanel choice, int x, int y, int w, int h) {
        JTextField t = new JTextField();
        t.setEditable(false);
        t.setBackground(Color.WHITE);
        t.setForeground(Color.RED);
        t.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        t.setBounds(x, y, w, h);
        choice.add(t);
        return t;
    }
    public JCheckBox styleJCheckBoxReservation(JCheckBox c, int x, int y, int w, int h){
        c.setBackground(Color.WHITE);
        c.setBounds(x, y, w, h);
        return c;
    }

    public void sizeComposant(Dimension dim, Component c){
        c.setPreferredSize(dim);
        c.setMaximumSize(dim);
        c.setMinimumSize(dim);
    }

    public boolean verifMap(){
        if((input.containsKey("start_date") && input.containsKey("end_date") && input.containsKey("numberEmployee"))
                && ( (input.containsKey("numberOpenSpace") || input.containsKey("numberMeetingRoom") ||
                input.containsKey("numberSingleOffice")|| input.containsKey("numberClosedOffice"))))
            return true;
        else return false;
    }

    public void changePage(){
        Choice selectChoice = new Choice(input, frame);
        selectChoice.choice(pageBody);
    }
}
