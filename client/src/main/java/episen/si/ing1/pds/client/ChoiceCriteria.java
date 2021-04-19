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
                if(input.containsKey("numberMeetingRoom") && Integer.parseInt(input.get("numberMeetingRoom")) == 1
                        && !(input.containsKey("numberOpenSpace")) && !(input.containsKey("numberSingleOffice"))
                        && !(input.containsKey("numberBoxClosedOffice")) && Integer.parseInt(input.get("numberEmployee")) > 10){
                    String message = "Vous avez choisi une salle de reunion pour un nombre important de collaborateurs, ainsi, on réserve aussi un open-space pour votre confort";
                    JOptionPane.showMessageDialog(null, message, null, JOptionPane.WARNING_MESSAGE);
                    input.put("numberOpenSpace", 1+"");
                }
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
                String m = (((JTextField)source).getText()).trim();
                if(m.matches("[0-9]{4}-[0-1]{1}[0-9]{1}-[0-3]{1}[0-9]{1}")) {
                    try {
                        Date today1 = dateFormat.parse(dateFormat.format(new Date()));
                        Date date1 = dateFormat.parse(m);
                        if(today1.equals(date1) || today1.before(date1)){
                            if(input.containsKey("start_date"))input.replace("start_date", ((JTextField)source).getText().trim());
                            else input.put("start_date", ((JTextField)source).getText().trim());
                            ((JTextField)choice.getComponentAt(470, 80)).setText(" ");
                            if(verifMap()) buttonContinue.setEnabled(true);
                            System.out.println(input);
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
                        Date date1 = dateFormat.parse(m);
                        if(today1.equals(date1) || today1.before(date1)){
                            if(input.containsKey("end_date"))input.replace("end_date", ((JTextField)source).getText().trim());
                            else input.put("end_date", ((JTextField)source).getText().trim());
                            messageErrorEndDate.setText(" ");
                            if(verifMap()) buttonContinue.setEnabled(true);
                            System.out.println(input);
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

        //label of room
        JLabel roomLabel = new JLabel("Les salles : ");
        roomLabel = styleJLabelReservation(roomLabel,20, 140,250,50);


        JTextField openSpace = new JTextField("Open-space : ");
        openSpace = styleJTextFieldReservation(openSpace,20, 200, 100, 20);

        JCheckBox checkBoxOpenSpace = new JCheckBox();
        checkBoxOpenSpace = styleJCheckBoxReservation(checkBoxOpenSpace, 120, 200, 20, 20);
        checkBoxOpenSpace.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                JTextField quantityOpenSpace = new JTextField("- nombre d'open-space : ");
                quantityOpenSpace = styleJTextFieldReservation(quantityOpenSpace,380, 200, 175, 20);

                JTextField valueOpenSpace = new JTextField(" ", 20);
                valueOpenSpace.setBackground(Color.WHITE);
                valueOpenSpace.setBounds(555, 200, 30, 20);
                JTextField messageErrorOpenSpace = styleJTextFieldError(choice,585, 200, 20, 20);
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
                choice.add(quantityOpenSpace);
                choice.add(valueOpenSpace);
            }
        });

        JTextField meetingRoom = new JTextField("Salle de reunion : ");
        meetingRoom = styleJTextFieldReservation(meetingRoom, 20, 230, 100, 20);

        JCheckBox checkBoxMeetingRoom = new JCheckBox();
        checkBoxMeetingRoom = styleJCheckBoxReservation(checkBoxMeetingRoom, 120, 230, 20, 20);
        checkBoxMeetingRoom.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                JTextField quantityMeetingRoom = new JTextField("- nombre de salle de reunion : ");
                quantityMeetingRoom = styleJTextFieldReservation(quantityMeetingRoom,380, 230, 175, 20);

                JTextField valueMeetingRoom = new JTextField(" ", 20);
                valueMeetingRoom.setBackground(Color.WHITE);
                valueMeetingRoom.setBounds(555, 230, 30, 20);
                JTextField messageErrorOpenSpace = styleJTextFieldError(choice,585, 230, 20, 20);
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
                choice.add(quantityMeetingRoom);
                choice.add(valueMeetingRoom);
            }
        });

        JTextField singleOffice = new JTextField("Bureau individuel : ");
        singleOffice = styleJTextFieldReservation(singleOffice, 200, 200, 100, 20);


        JCheckBox checkBoxSingleOffice = new JCheckBox();
        checkBoxSingleOffice = styleJCheckBoxReservation(checkBoxSingleOffice,300, 200, 20, 20);
        checkBoxSingleOffice.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                JTextField quantitySingleOffice = new JTextField("- nombre de bureau individuel : ");
                quantitySingleOffice = styleJTextFieldReservation(quantitySingleOffice,620, 200, 175, 20);

                JTextField valueSingleOffice= new JTextField(" ", 20);
                valueSingleOffice.setBackground(Color.WHITE);
                valueSingleOffice.setBounds(795, 200, 30, 20);
                JTextField messageErrorSingleoffice = styleJTextFieldError(choice, 825, 200, 20, 20);
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
                choice.add(quantitySingleOffice);
                choice.add(valueSingleOffice);
            }
        });

        JTextField closedOffice = new JTextField("Bureau ferme : ");
        closedOffice = styleJTextFieldReservation(closedOffice, 200, 230, 100, 20);

        JCheckBox checkBoxClosedOffice = new JCheckBox();
        checkBoxClosedOffice = styleJCheckBoxReservation(checkBoxClosedOffice,300, 230, 20, 20);
        checkBoxClosedOffice.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                JTextField quantityBoxClosedOffice = new JTextField("- nombre de bureau ferme : ");
                quantityBoxClosedOffice = styleJTextFieldReservation(quantityBoxClosedOffice,620, 230, 175, 20);

                JTextField valueBoxClosedOffice = new JTextField(" ", 20);
                valueBoxClosedOffice.setBackground(Color.WHITE);
                valueBoxClosedOffice.setBounds(795, 230, 30, 20);
                JTextField messageErrorClosedOffice = styleJTextFieldError(choice, 825, 230, 20, 20);
                valueBoxClosedOffice.addFocusListener(new FocusListener() {
                    @Override
                    public void focusGained(FocusEvent e) {}
                    @Override
                    public void focusLost(FocusEvent e) {
                        Object source = e.getSource();
                        String m = (((JTextField)source).getText()).trim();
                        if(m.matches("\\d+")) {
                            if(input.containsKey("numberBoxClosedOffice"))input.replace("numberBoxClosedOffice",((JTextField)source).getText().trim());
                            else input.put("numberBoxClosedOffice",((JTextField)source).getText().trim());
                            messageErrorClosedOffice.setText(" ");
                            if(verifMap()) buttonContinue.setEnabled(true);
                        }else {
                            messageErrorClosedOffice.setText("X");
                            messageErrorClosedOffice.setForeground(Color.red);
                        }
                    }
                });
                choice.add(quantityBoxClosedOffice);
                choice.add(valueBoxClosedOffice);
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

        //number of employee
        JLabel nbEmployee = new JLabel("Nombre de collaborateur :");
        nbEmployee = styleJLabelReservation(nbEmployee,20, 260,250,50);

        JTextField nbEmployeeTextField = new JTextField("Veuillez indiquer un nombre de collaborateurs au maximun : ");
        nbEmployeeTextField = styleJTextFieldReservation(nbEmployeeTextField, 20, 320, 350, 20);

        JTextField valueEmployee = new JTextField(" ");
        valueEmployee.setBounds(370, 320, 50, 20);
        JTextField messageErrorEmployee = styleJTextFieldError(choice, 420, 320, 20, 20);
        valueEmployee.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {}
            @Override
            public void focusLost(FocusEvent e) {
                Object source = e.getSource();
                String m = (((JTextField)source).getText()).trim();
                if(m.matches("\\d+")) {
                    if(input.containsKey("numberEmployee"))input.replace("numberEmployee",((JTextField)source).getText().trim());
                    else input.put("numberEmployee",((JTextField)source).getText().trim());
                    messageErrorEmployee.setText(" ");
                    if(verifMap()) buttonContinue.setEnabled(true);
                }else{
                    messageErrorEmployee.setText("X");
                    messageErrorEmployee.setForeground(Color.RED);
                }
            }
        });

        choice.add(nbEmployeeTextField);
        choice.add(valueEmployee);
        choice.add(nbEmployee);

        //location
        JLabel locationLabel = new JLabel("Situation geographique :");
        locationLabel = styleJLabelReservation(locationLabel,20, 350,250,50);

        JTextField locationTextField = new JTextField("Veuillez indiquer un ou plusieurs lieux geographiques : ");
        locationTextField = styleJTextFieldReservation(locationTextField, 20, 410, 300, 20);

        JCheckBox locationNorth = new JCheckBox("Nord de la ville");
        locationNorth = styleJCheckBoxReservation(locationNorth,20, 440, 150, 20);

        JCheckBox locationSouth = new JCheckBox("Sud de la ville");
        locationSouth = styleJCheckBoxReservation(locationSouth,20, 470, 150, 20);

        JCheckBox locationEast = new JCheckBox("Est de la ville");
        locationEast = styleJCheckBoxReservation(locationEast,180, 440, 150, 20);

        JCheckBox locationWest = new JCheckBox("Ouest de la ville");
        locationWest = styleJCheckBoxReservation(locationWest,180, 470, 150, 20);

        JCheckBox locationCenter = new JCheckBox("Centre de la ville");
        locationCenter = styleJCheckBoxReservation(locationCenter,340, 440, 150, 20);

        JCheckBox locationAnyway = new JCheckBox("Peu importe");
        locationAnyway = styleJCheckBoxReservation(locationAnyway,340, 470, 150, 20);

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
        System.out.println(input);
        if((input.containsKey("start_date") && input.containsKey("end_date") && input.containsKey("numberEmployee"))
                && ( (input.containsKey("numberOpenSpace") || input.containsKey("numberMeetingRoom") ||
                input.containsKey("numberSingleOffice")|| input.containsKey("numberBoxClosedOffice"))))
            return true;
        else return false;
    }

    public void changePage(){
        Choice selectChoice = new Choice(input, frame);
        selectChoice.choice(pageBody);
    }
}
