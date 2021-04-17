package episen.si.ing1.pds.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class Reservation extends JFrame{
    private boolean verifInput = false;
    private Map input = new HashMap();


    public void realizeReservation(){
        String page = "criteres";
        setSize(1200, 800);
        JPanel pageBody = new JPanel();
        pageBody.setLayout(new BorderLayout());
        Menu menu = new Menu();
        TitleReservation title = new TitleReservation();

        pageBody.add(title.titleReservation(), BorderLayout.NORTH);

        RentalAdvancement rentalAdvancement = new RentalAdvancement(page);

        pageBody.add(rentalAdvancement.rentalAdvancement(), BorderLayout.CENTER);

        JPanel choice = reservationCritere();

        //button continue and return
        buttonReturnContinue(choice, page, pageBody);

        pageBody.add(choice, BorderLayout.SOUTH);
        pageBody.setBackground(Color.WHITE);
        getContentPane().add(menu.menu(), BorderLayout.WEST);
        getContentPane().add(pageBody, BorderLayout.CENTER);

        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void buttonReturnContinue(JPanel choice, String page, JPanel pageBody){
        JButton buttonContinue = new JButton("Continuer");
        JButton buttonReturn = new JButton("Retourner");
        buttonContinue.setBounds(780, 500, 100, 50);
        buttonContinue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("test");
                pageBody.remove(choice);
                pageBody.repaint();
                System.out.println(input);
            }
        });
        buttonReturn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        buttonReturn.setBounds(660, 500, 100, 50);
        choice.add(buttonContinue);
        choice.add(buttonReturn);
    }

    public JPanel reservationCritere(){
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
        valueStartDate.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {}
            @Override
            public void focusLost(FocusEvent e) {
                Object source = e.getSource();
                String m = (((JTextField)source).getText()).trim();
                if(m.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}")) input.put("start_date", ((JTextField)source).getText());
                else styleJTextFieldError(choice, "Veuillez respecte le format" ,470, 80, 170, 20);
            }
        });

        JTextField valueEndDate = new JTextField(" ");
        valueEndDate.setBounds(350, 110, 100, 20);
        valueEndDate.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {}
            @Override
            public void focusLost(FocusEvent e) {
                Object source = e.getSource();
                String m = (((JTextField)source).getText()).trim();
                if(m.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}")) input.put("end_date", ((JTextField)source).getText());
                else styleJTextFieldError(choice, "Veuillez respecter le format",470, 110, 170, 20);
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
        JLabel roomLabel = new JLabel("Les salles : ");;
        roomLabel = styleJLabelReservation(roomLabel,20, 140,250,50);


        JTextField openSpace = new JTextField("Open-space : ");
        openSpace = styleJTextFieldReservation(openSpace,20, 200, 100, 20);

        JCheckBox checkBoxOpenSpace = new JCheckBox();
        checkBoxOpenSpace = styleJCheckBoxReservation(checkBoxOpenSpace, 120, 200, 20, 20);
        checkBoxOpenSpace.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                JTextField quantityOpenSpace = new JTextField("- nombre d'open-space : ");
                quantityOpenSpace = styleJTextFieldReservation(quantityOpenSpace,400, 200, 175, 20);

                JTextField valueOpenSpace = new JTextField(" ", 20);
                valueOpenSpace.setBackground(Color.WHITE);
                valueOpenSpace.setBounds(575, 200, 50, 20);
                valueOpenSpace.addFocusListener(new FocusListener() {
                    @Override
                    public void focusGained(FocusEvent e) {}
                    @Override
                    public void focusLost(FocusEvent e) {
                        Object source = e.getSource();
                        String m = (((JTextField)source).getText()).trim();
                        if(m.matches("\\d+")) input.put("numberOpenSpace", ((JTextField)source).getText());
                        else styleJTextFieldError(choice, "Veuillez saisir un nombre positif" ,400, 250, 200, 20);
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
                quantityMeetingRoom = styleJTextFieldReservation(quantityMeetingRoom,400, 230, 175, 20);

                JTextField valueMeetingRoom = new JTextField(" ", 20);
                valueMeetingRoom.setBackground(Color.WHITE);
                valueMeetingRoom.setBounds(575, 230, 50, 20);
                valueMeetingRoom.addFocusListener(new FocusListener() {
                    @Override
                    public void focusGained(FocusEvent e) {}
                    @Override
                    public void focusLost(FocusEvent e) {
                        Object source = e.getSource();
                        String m = (((JTextField)source).getText()).trim();
                        if(m.matches("\\d+")) input.put("numberMeetingRoom", ((JTextField)source).getText());
                        else styleJTextFieldError(choice, "Veuillez saisir un nombre positif" ,400, 250, 200, 20);
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
                quantitySingleOffice = styleJTextFieldReservation(quantitySingleOffice,650, 200, 175, 20);

                JTextField valueSingleOffice= new JTextField(" ", 20);
                valueSingleOffice.setBackground(Color.WHITE);
                valueSingleOffice.setBounds(825, 200, 50, 20);
                valueSingleOffice.addFocusListener(new FocusListener() {
                    @Override
                    public void focusGained(FocusEvent e) {}
                    @Override
                    public void focusLost(FocusEvent e) {
                        Object source = e.getSource();
                        String m = (((JTextField)source).getText()).trim();
                        if(m.matches("\\d+")) input.put("numberSingleOffice", ((JTextField)source).getText());
                        else styleJTextFieldError(choice, "Veuillez saisir un nombre positif" ,400, 250, 200, 20);
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
                JTextField quantityBoxClosedOffice = new JTextField("- nombre de bureau individuel : ");
                quantityBoxClosedOffice = styleJTextFieldReservation(quantityBoxClosedOffice,650, 230, 175, 20);

                JTextField valueBoxClosedOffice = new JTextField(" ", 20);
                valueBoxClosedOffice.setBackground(Color.WHITE);
                valueBoxClosedOffice.setBounds(825, 230, 50, 20);
                valueBoxClosedOffice.addFocusListener(new FocusListener() {
                    @Override
                    public void focusGained(FocusEvent e) {}
                    @Override
                    public void focusLost(FocusEvent e) {
                        Object source = e.getSource();
                        String m = (((JTextField)source).getText()).trim();
                        if(m.matches("\\d+")) input.put("numberBoxClosedOffice", ((JTextField)source).getText());
                        else styleJTextFieldError(choice, "Veuillez saisir un nombre positif" ,400, 250, 200, 20);
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
        valueEmployee.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {}
            @Override
            public void focusLost(FocusEvent e) {
                Object source = e.getSource();
                String m = (((JTextField)source).getText()).trim();
                if(m.matches("\\d+")) input.put("numberEmployee", ((JTextField)source).getText());
                else styleJTextFieldError(choice, "Veuillez saisir un nombre positif" ,400, 250, 200, 20);
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
    public void styleJTextFieldError(JPanel choice, String message, int x, int y, int w, int h) {
        JTextField t = new JTextField(message);
        t.setEditable(false);
        t.setBackground(Color.WHITE);
        t.setForeground(Color.RED);
        t.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        t.setBounds(x, y, w, h);
        choice.add(t);
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
}
