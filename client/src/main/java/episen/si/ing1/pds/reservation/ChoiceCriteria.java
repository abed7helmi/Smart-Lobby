package episen.si.ing1.pds.reservation;

import episen.si.ing1.pds.client.Client;
import episen.si.ing1.pds.client.Ihm;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ChoiceCriteria{
    public static Map<String, String> input = new HashMap<>();
    private final String page = "criteria";
    private final JButton buttonContinue = new JButton("> Continuer");
    private final JFrame frame;
    private JCheckBox checkBoxOpenSpace = new JCheckBox();
    private JCheckBox checkBoxMeetingRoom = new JCheckBox();
    private JCheckBox checkBoxClosedOffice = new JCheckBox();
    private JCheckBox checkBoxSingleOffice = new JCheckBox();
    private final JTextField valueOpenSpace = new JTextField();
    private final JTextField valueClosedOffice = new JTextField();
    private final JTextField valueMeetingRoom = new JTextField();
    private final JTextField valueSingleOffice = new JTextField();
    private final JPanel pageBody = new JPanel();
    private String company_id = "";

    public ChoiceCriteria(JFrame f,String id)  {
        restartData();
        frame = f;
        company_id = id;
        input.put("company_id", company_id);
    }

    public JPanel realizeReservation(){
        pageBody.setLayout(new BorderLayout());
        TitleReservation title = new TitleReservation();

        pageBody.add(title.titleReservation(), BorderLayout.NORTH);
        RentalAdvancement rentalAdvancement = new RentalAdvancement(page);
        JPanel advancement = rentalAdvancement.rentalAdvancement();
        pageBody.add(advancement, BorderLayout.CENTER);

        JPanel choice = reservationCritere();

        buttonContinue.setBounds(780, 500, 100, 50);
        buttonContinue.setBackground(new Color(255, 255,255));
        buttonContinue.setForeground(Color.BLACK);
        buttonContinue.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        buttonContinue.setEnabled(false);
        buttonContinue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean changeValue = false;
                boolean verifyOpenSpace = input.containsKey("numberOpenSpace");
                boolean verifyClosedOffice = input.containsKey("numberClosedOffice");
                boolean verifySingleOffice = input.containsKey("numberSingleOffice");
                boolean verifyMeetingRoom = input.containsKey("numberMeetingRoom");
                String sauvOpenSpace = "", sauvClosedOffice = "";
                if(!verifyOpenSpace) input.put("numberOpenSpace", "0");
                else {
                    sauvOpenSpace = input.get("numberOpenSpace");
                }
                if(!verifyClosedOffice) input.put("numberClosedOffice", "0");
                else {
                    sauvClosedOffice = input.get("numberClosedOffice");
                }
                if(!verifySingleOffice) input.put("numberSingleOffice", "0");
                if(!verifyMeetingRoom) input.put("numberMeetingRoom", "0");

                int nbrEmployee = Integer.parseInt(input.get("numberEmployee"));
                int somme = (Integer.parseInt(input.get("numberOpenSpace")) * 50) + (Integer.parseInt(input.get("numberClosedOffice")) * 20)
                        + (Integer.parseInt(input.get("numberSingleOffice")));

                if( somme + 50 < nbrEmployee) {
                    int newValue = ((Integer.parseInt(input.get("numberEmployee")) - somme) / 50) + 1 + (Integer.parseInt(input.get("numberOpenSpace")));
                    input.replace("numberOpenSpace", newValue+"");
                    changeValue = true;
                } else if(  somme + 20 < nbrEmployee) {
                    input.replace("numberOpenSpace", Integer.parseInt(input.get("numberOpenSpace")) + 1+"" );
                    changeValue = true;
                } else if( somme < nbrEmployee){
                    int newValue = ((Integer.parseInt(input.get("numberEmployee")) - somme) / 20) + 1 +  (Integer.parseInt(input.get("numberClosedOffice")));
                    input.replace("numberClosedOffice", newValue+"");
                    changeValue = true;
                }
                if(changeValue){
                    int result = JOptionPane.showConfirmDialog(null, "Au vue des donnees que vous avez rentre, nous avons ajuste votre demande par rapport a la capacite de chaque piece");
                    if(result == JOptionPane.NO_OPTION){
                        input.replace("numberOpenSpace", sauvOpenSpace);
                        input.replace("numberClosedOffice", sauvClosedOffice);
                    }
                }
                choice.setVisible(false);
                advancement.setVisible(false);
                pageBody.repaint();


                String request = "requestLocation1";

                Client.map.get(request).put("end_date", input.get("end_date"));
                Client.map.get(request).put("start_date", input.get("start_date"));
                Client.map.get(request).put("numberOpenSpace", input.get("numberOpenSpace"));
                Client.map.get(request).put("numberClosedOffice", input.get("numberClosedOffice"));
                Client.map.get(request).put("numberSingleOffice", input.get("numberSingleOffice"));
                Client.map.get(request).put("numberMeetingRoom", input.get("numberMeetingRoom"));

                if(input.containsKey("location")) Client.map.get(request).put("location", input.get("location"));

                String result = Client.sendBd(request);
                changePage(result);
            }
        });
        choice.add(buttonContinue);
        pageBody.add(choice, BorderLayout.SOUTH);
        pageBody.setBackground(Color.white);
        pageBody.setVisible(true);
        frame.getContentPane().add(pageBody, BorderLayout.CENTER);
        frame.repaint();

        return pageBody;
    }

    public JPanel reservationCritere(){
        JPanel choice = new JPanel();
        choice.setLayout(null);
        Dimension dimChoice = new Dimension(950, 600);
        Ihm.sizeComposant(dimChoice, choice);
        choice.setBackground(Color.WHITE);
        choice.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        //label of date
        JTextField startDate = new JTextField("Debut de la reservation : (YYYY-MM-DD) ");
        startDate = Ihm.styleJTextFieldReservation(startDate, 20, 80, 230, 20, Color.WHITE, Color.WHITE);

        JTextField endDate = new JTextField("Fin de la reservation : (YYYY-MM-DD)");
        endDate = Ihm.styleJTextFieldReservation(endDate,20, 110, 230, 20, Color.WHITE, Color.WHITE);

        Calendar date = Calendar.getInstance();
        date.setTime(new Date());
        Format f = new SimpleDateFormat("yyyy-MM-dd");

        JTextField valueStartDate = new JTextField(f.format(date.getTime()));
        valueStartDate = Ihm.styleJTextFieldReservation(valueStartDate, 260,80,100,20, Color.WHITE, Color.WHITE);
        input.put("start_date", (valueStartDate.getText()).trim());

        date.add(Calendar.YEAR,1);

        JTextField valueEndDate = new JTextField(f.format(date.getTime()));
        valueEndDate = Ihm.styleJTextFieldReservation(valueEndDate, 260,110,100,20, Color.WHITE, Color.WHITE);
        input.put("end_date", (valueEndDate.getText()).trim());

        JLabel dateLabel = new JLabel("Date : ");
        dateLabel = styleJLabelReservation(dateLabel, 20, 20,250,50);

        choice.add(dateLabel);
        choice.add(startDate);
        choice.add(valueStartDate);
        choice.add(endDate);
        choice.add(valueEndDate);

        //number of employee
        JButton buttonValidate = new JButton("Faire une proposition");
        buttonValidate.setBackground(new Color(255, 255,255));
        buttonValidate.setForeground(Color.BLACK);
        buttonValidate.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JLabel nbEmployee = new JLabel("Nombre de collaborateur :");
        nbEmployee = styleJLabelReservation(nbEmployee,20, 140,250,50);

        JTextField nbEmployeeTextField = new JTextField("Veuillez indiquer un nombre de collaborateurs au maximun : ");
        nbEmployeeTextField = Ihm.styleJTextFieldReservation(nbEmployeeTextField, 20, 200, 350, 20, Color.WHITE, Color.WHITE);

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
                    input.put("numberEmployee",((JTextField)source).getText().trim());
                    messageErrorEmployee.setText(" ");
                    if(verifMap()) buttonContinue.setEnabled(true);

                    valueOpenSpace.setText("");
                    valueMeetingRoom.setText("");
                    valueClosedOffice.setText("");
                    valueSingleOffice.setText("");
                } else if( !(m.equals("")) ) messageError(messageErrorEmployee);
            }
        });

        choice.add(nbEmployeeTextField);
        choice.add(valueEmployee);
        choice.add(nbEmployee);

        //label of room
        JLabel roomLabel = new JLabel("Les salles : ");
        roomLabel = styleJLabelReservation(roomLabel,20, 230,250,50);

        JTextField openSpace = new JTextField("Open-space : ");
        openSpace = Ihm.styleJTextFieldReservation(openSpace,20, 290, 100, 20, Color.WHITE, Color.WHITE);

        checkBoxOpenSpace = styleJCheckBoxReservation(checkBoxOpenSpace, 120, 290, 20, 20);
        checkBoxOpenSpace.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(input.containsKey("numberEmployee")) buttonValidate.setEnabled(true);
            }
        });

        JTextField quantityOpenSpace = new JTextField("- nombre d'open-space : ");
        quantityOpenSpace = Ihm.styleJTextFieldReservation(quantityOpenSpace,380, 290, 175, 20, Color.WHITE, Color.WHITE);

        valueOpenSpace.setText("");
        valueOpenSpace.setBackground(Color.white);
        valueOpenSpace.setBounds(555, 290, 30, 20);
        choice.add(quantityOpenSpace);
        choice.add(valueOpenSpace);
        choice.repaint();

        JTextField messageErrorOpenSpace = styleJTextFieldError(choice,585, 290, 20, 20);
        valueOpenSpace.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {}
            @Override
            public void focusLost(FocusEvent e) {
                Object source = e.getSource();
                String m = (((JTextField)source).getText()).trim();
                if(m.matches("\\d+")) {
                    input.put("numberOpenSpace", ((JTextField)source).getText().trim());
                    messageErrorOpenSpace.setText(" ");
                    if(verifMap()) buttonContinue.setEnabled(true);
                }else if( !(m.equals("")) ) messageError(messageErrorOpenSpace);
            }
        });

        JTextField meetingRoom = new JTextField("Salle de reunion : ");
        meetingRoom = Ihm.styleJTextFieldReservation(meetingRoom, 20, 320, 100, 20, Color.WHITE, Color.WHITE);

        checkBoxMeetingRoom = styleJCheckBoxReservation(checkBoxMeetingRoom, 120, 320, 20, 20);

        JTextField quantityMeetingRoom = new JTextField("- nombre de salle de reunion : ");
        quantityMeetingRoom = Ihm.styleJTextFieldReservation(quantityMeetingRoom,380, 320, 175, 20, Color.WHITE, Color.WHITE);

        valueMeetingRoom.setText("");
        valueMeetingRoom.setBackground(Color.white );
        valueMeetingRoom.setBounds(555, 320, 30, 20);
        choice.add(valueMeetingRoom);
        choice.add(quantityMeetingRoom);
        choice.repaint();

        JTextField messageErrorMeetingRoom = styleJTextFieldError(choice,585, 320, 20, 20);
        valueMeetingRoom.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {}
            @Override
            public void focusLost(FocusEvent e) {
                Object source = e.getSource();
                String m = (((JTextField)source).getText()).trim();
                if(m.matches("\\d+")) {
                    input.put("numberMeetingRoom",((JTextField)source).getText().trim());
                    messageErrorMeetingRoom.setText(" ");
                    if(verifMap()) buttonContinue.setEnabled(true);
                }else if( !(m.equals("")) ) messageError(messageErrorMeetingRoom);
            }
        });

        JTextField singleOffice = new JTextField("Bureau individuel : ");
        singleOffice = Ihm.styleJTextFieldReservation(singleOffice, 200, 290, 110, 20, Color.WHITE, Color.WHITE);

        checkBoxSingleOffice = styleJCheckBoxReservation(checkBoxSingleOffice,310, 290, 20, 20);

        JTextField quantitySingleOffice = new JTextField("- nombre de bureau individuel : ");
        quantitySingleOffice = Ihm.styleJTextFieldReservation(quantitySingleOffice,620, 320, 175, 20, Color.WHITE, Color.WHITE);

        valueSingleOffice.setText("");
        valueSingleOffice.setBackground(Color.white);
        valueSingleOffice.setBounds(795, 320, 30, 20);
        choice.add(quantitySingleOffice);
        choice.add(valueSingleOffice);
        choice.repaint();

        JTextField messageErrorSingleOffice = styleJTextFieldError(choice, 825, 320, 20, 20);
        valueSingleOffice.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {}
            @Override
            public void focusLost(FocusEvent e) {
                Object source = e.getSource();
                String m = (((JTextField)source).getText()).trim();
                if(m.matches("\\d+")) {
                    input.put("numberSingleOffice",((JTextField)source).getText().trim());
                    messageErrorSingleOffice.setText(" ");
                    if(verifMap()) buttonContinue.setEnabled(true);
                }else if( !(m.equals("")) ) messageError(messageErrorSingleOffice);
            }
        });

        JTextField closedOffice = new JTextField("Bureau ferme : ");
        closedOffice = Ihm.styleJTextFieldReservation(closedOffice, 200, 320, 110, 20,Color.WHITE, Color.WHITE);

        checkBoxClosedOffice = styleJCheckBoxReservation(checkBoxClosedOffice,310, 320, 20, 20);
        checkBoxClosedOffice.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(input.containsKey("numberEmployee")) buttonValidate.setEnabled(true);
            }
        });

        JTextField quantityClosedOffice = new JTextField("- nombre de bureau ferme : ");
        quantityClosedOffice = Ihm.styleJTextFieldReservation(quantityClosedOffice,620, 290, 175, 20, Color.WHITE, Color.WHITE);

        valueClosedOffice.setText("");
        valueClosedOffice.setBackground(Color.white);
        valueClosedOffice.setBounds(795, 290, 30, 20);
        choice.add(quantityClosedOffice);
        choice.add(valueClosedOffice);
        choice.repaint();

        JTextField messageErrorClosedOffice = styleJTextFieldError(choice, 825, 290, 20, 20);
        valueClosedOffice.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {}
            @Override
            public void focusLost(FocusEvent e) {
                Object source = e.getSource();
                String m = (((JTextField)source).getText()).trim();
                if(m.matches("\\d+")) {
                    input.put("numberClosedOffice",((JTextField)source).getText().trim());
                    messageErrorClosedOffice.setText(" ");
                    if(verifMap()) buttonContinue.setEnabled(true);
                }else if( !(m.equals("")) ) messageError(messageErrorClosedOffice);
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

                    input.put("numberClosedOffice",nbrClosedOffice+"");
                    input.put("numberOpenSpace", nbrOpenSpace+"");

                    if(verifMap()) buttonContinue.setEnabled(true);

                }else if( (checkBoxClosedOffice.isSelected())) {
                    if( (Integer.parseInt(input.get("numberEmployee")) % 20) == 0) nbrClosedOffice = (Integer.parseInt(input.get("numberEmployee")) / 20);
                    else nbrClosedOffice = (Integer.parseInt(input.get("numberEmployee")) / 20) + 1;
                    valueClosedOffice.setText(nbrClosedOffice+"");

                    input.put("numberClosedOffice",nbrClosedOffice+"");

                    if(verifMap()) buttonContinue.setEnabled(true);

                } else if( (checkBoxOpenSpace.isSelected())) {
                    if( (Integer.parseInt(input.get("numberEmployee")) % 50) == 0) nbrOpenSpace = (Integer.parseInt(input.get("numberEmployee")) / 50);
                    else nbrOpenSpace = (Integer.parseInt(input.get("numberEmployee")) / 50) + 1;
                    valueOpenSpace.setText(nbrOpenSpace+"");

                    input.put("numberOpenSpace", nbrOpenSpace+"");

                    if(verifMap()) buttonContinue.setEnabled(true);
                } else if( !(checkBoxClosedOffice.isSelected()) && !(checkBoxOpenSpace.isSelected())) {
                    if(Integer.parseInt(input.get("numberEmployee")) >= 50){
                        if( (Integer.parseInt(input.get("numberEmployee")) % 50) == 0) nbrOpenSpace = (Integer.parseInt(input.get("numberEmployee")) / 50);
                        else nbrOpenSpace = (Integer.parseInt(input.get("numberEmployee")) / 50) + 1;
                        valueOpenSpace.setText(nbrOpenSpace+"");

                        input.put("numberOpenSpace", nbrOpenSpace+"");
                    } else {
                        if( (Integer.parseInt(input.get("numberEmployee")) % 20) == 0) nbrClosedOffice = (Integer.parseInt(input.get("numberEmployee")) / 20);
                        else nbrClosedOffice = (Integer.parseInt(input.get("numberEmployee")) / 20) + 1;
                        valueClosedOffice.setText(nbrClosedOffice+"");

                        input.put("numberClosedOffice",nbrClosedOffice+"");
                    }
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

        JPanel location = new JPanel();
        location.setBounds(20,480,500,100);
        location.setBackground(new Color(0, 102,204));
        Border line = BorderFactory.createLineBorder(Color.white);
        location.setBorder(BorderFactory.createTitledBorder(line, "Veuillez indiquer un lieu geographique",
                1,3,new Font("Serif", Font.BOLD, 15),Color.white));

        ButtonGroup groupLocation = new ButtonGroup();
        JRadioButton locationNorth = new JRadioButton("Nord de la ville");
        styleLocationReservation(location, groupLocation, locationNorth, "nord");

        JRadioButton locationSouth = new JRadioButton("Sud de la ville");
        styleLocationReservation(location, groupLocation, locationSouth, "sud");

        JRadioButton locationEast = new JRadioButton("Est de la ville");
        styleLocationReservation(location, groupLocation, locationEast, "est");

        JRadioButton locationWest = new JRadioButton("Ouest de la ville");
        styleLocationReservation(location, groupLocation,locationWest, "ouest");

        JRadioButton locationCenter = new JRadioButton("Centre de la ville");
        styleLocationReservation(location, groupLocation,locationCenter,"centre");

        JRadioButton locationAnyway = new JRadioButton("Peu importe");
        styleLocationReservation(location, groupLocation, locationAnyway, "anyway");

        choice.add(locationLabel);
        choice.add(location);

        return choice;
    }
    public JLabel styleJLabelReservation(JLabel l, int x, int y, int w, int h){
        Ihm.sizeComposant(new Dimension(200, 200) ,l);
        l.setBorder(BorderFactory.createMatteBorder(0,0, 1, 0, Color.black));
        l.setBounds(x,y,w,h);
        l.setFont(new Font("Serif", Font.BOLD, 20));
        return l;
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
    public void styleLocationReservation(JPanel location, ButtonGroup group, JRadioButton c, String data){
        group.add(c);
        c.setOpaque(false);
        c.setForeground(Color.white);
        if(data.equals("anyway")){
            c.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    input.remove("location");
                }
            });
        } else {
            c.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    input.put("location", data);
                }
            });
        }
        c.setBackground(Color.WHITE);
        location.add(c);
    }
    public JCheckBox styleJCheckBoxReservation(JCheckBox c, int x, int y, int w, int h){
        c.setBackground(Color.WHITE);
        c.setBounds(x, y, w, h);
        return c;
    }
    public boolean verifMap(){
        if((input.containsKey("start_date") && input.containsKey("end_date") && input.containsKey("numberEmployee"))
                && ( (input.containsKey("numberOpenSpace") || input.containsKey("numberMeetingRoom") ||
                input.containsKey("numberSingleOffice")|| input.containsKey("numberClosedOffice"))))
            return true;
        else return false;
    }
    public void changePage(String proposals){
        Choice selectChoice = new Choice(frame);
        selectChoice.choice(pageBody, proposals);
    }
    public void messageError (JTextField message){
        message.setText("X");
        message.setForeground(Color.red);
    }
    public static void restartData(){
        ChoiceCriteria.input.clear();
        Choice.proposalSelected.clear();
        Choice.mapProposals.clear();
        ViewWithPlan.listDeviceIdRoom.clear();
        ViewWithPlan.configRoom.clear();
        ViewWithPlan.listButton.clear();
        ViewWithPlan.listDeviceId.clear();
        ChoiceDevice.config.clear();
    }
}
