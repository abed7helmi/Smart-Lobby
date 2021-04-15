package episen.si.ing1.pds.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class Ihm extends JFrame implements ActionListener {
    private Socket socket;
    private JTextField companyName, unregisteredCompanyName;
    private JButton entry, entryUnregistered;
    private GridBagConstraints gc = new GridBagConstraints();
    private PrintWriter output;
    private BufferedReader input;
    private JPanel title, registeredCompany;


    public Ihm(String name) {
        /*socket = s;
        try {
            output = new PrintWriter(socket.getOutputStream(), true);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        setTitle(name);
        //firstPage();
        realizeLocation();

        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent e){
        Object source = e.getSource();
        if(source == entry){
            output.println("select=" + companyName.getText());
            try{
                boolean test = Boolean.parseBoolean(input.readLine());
                if(test) {
                    this.remove(title);
                    this.remove(registeredCompany);
                    this.repaint();
                    this.setSize(800,800);
                    this.setLocationRelativeTo(null);
                } else
                    JOptionPane.showMessageDialog(null, "Le nom renseigne n'existe pas","",
                            JOptionPane.ERROR_MESSAGE);

            }catch (IOException a) {
                a.printStackTrace();
            }
        } else if(source == entryUnregistered) {
            System.out.println(unregisteredCompanyName.getText());
            output.println("insert=" + unregisteredCompanyName.getText());
            try{
                System.out.println(input.readLine());
                JOptionPane.showMessageDialog(null, "Bienvenue !!","",
                    JOptionPane.INFORMATION_MESSAGE);
                this.remove(title);
                this.remove(registeredCompany);
                this.repaint();
                this.setSize(800,800);
                this.setLocationRelativeTo(null);
            }catch (IOException a) {
                a.printStackTrace();
            }
        }
    }

    public void firstPage(){
        setSize(500, 500);

        gc.fill = GridBagConstraints.BOTH;
        gc.insets = new Insets(70,0,70,5);
        gc.weightx = 3;
        gc.weighty = 1;
        gc.gridx = 0;
        gc.gridy = 0;

        title = new JPanel(new BorderLayout());
        JLabel l1 = new JLabel("Choix de l'entreprise :");
        l1.setForeground(new Color(255,255,255));
        l1.setFont(new Font(l1.getFont().getName() , l1.getFont().getStyle(), 20));
        l1.setPreferredSize(new Dimension( 100, 100));
        title.setBackground(Color.CYAN);
        title.add(l1);

        JLabel l2 = new JLabel("Deja inscrite : ");
        companyName = new JTextField(10);
        entry = new JButton("Entrer");
        entry.addActionListener(this);

        registeredCompany = new JPanel();
        registeredCompany.setBackground(Color.WHITE);
        registeredCompany.setLayout(new GridBagLayout());
        gc.insets.left = 30;
        registeredCompany.add(l2, gc);
        gc.gridx = 1;
        gc.insets.left = 0;
        registeredCompany.add(companyName, gc);
        gc.gridx = 2;
        registeredCompany.add(entry, gc);

        JLabel unregistered = new JLabel("Pas inscrit : ");
        unregisteredCompanyName = new JTextField(10);
        entryUnregistered = new JButton("Envoyer");
        entryUnregistered.addActionListener(this);

        gc.gridx = 0;
        gc.gridy = 1;
        gc.insets.left = 30;
        registeredCompany.add(unregistered, gc);
        gc.gridx = 1;
        gc.gridy = 1;
        gc.insets.left = 0;
        registeredCompany.add(unregisteredCompanyName, gc);
        gc.gridx = 2;
        gc.gridy = 1;
        registeredCompany.add(entryUnregistered, gc);

        getContentPane().add(title, BorderLayout.NORTH);
        getContentPane().add(registeredCompany, BorderLayout.CENTER);
    }

    public void realizeLocation(){
        setSize(1200, 800);
        JPanel pageBody = new JPanel();
        pageBody.setLayout(new BorderLayout());
        JPanel menu = menu();
        JPanel title = title();

        pageBody.add(title, BorderLayout.NORTH);

        JPanel rentalAdvancement = rentalAdvancement();

        pageBody.add(rentalAdvancement, BorderLayout.CENTER);

        JPanel choice = new JPanel();
        choice.setLayout(null);
        Dimension dimChoice = new Dimension(950, 600);
        choice.setMaximumSize(dimChoice);
        choice.setPreferredSize(dimChoice);
        choice.setMinimumSize(dimChoice);
        choice.setBackground(Color.WHITE);
        choice.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        //label of date
        JTextField startDate = new JTextField("Veuillez indiquer la date de debut : (YYYY-MM-DD) ");
        startDate.setEditable(false);
        startDate.setBackground(Color.WHITE);
        startDate.setBounds(20, 80, 275, 20);
        startDate.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        JTextField endDate = new JTextField("Veuillez indiquer la date de fin : (YYYY-MM-DD)");
        endDate.setEditable(false);
        endDate.setBackground(Color.WHITE);
        endDate.setBounds(20, 110, 275, 20);
        endDate.setBorder(BorderFactory.createLineBorder(Color.WHITE));

        JTextField valueStartDate = new JTextField(" ");
        valueStartDate.setBounds(350, 80, 100, 20);
        JTextField valueEndDate = new JTextField(" ");
        valueEndDate.setBounds(350, 110, 100, 20);

        JLabel dateLabel = new JLabel("Date : ");
        dateLabel.setMaximumSize(new Dimension(100, 50));
        dateLabel.setPreferredSize(new Dimension(100, 50));
        dateLabel.setMinimumSize(new Dimension(100, 50));
        dateLabel.setBorder(BorderFactory.createMatteBorder(0,0, 1, 0, Color.black));
        dateLabel.setBounds(20, 20,100,50);
        dateLabel.setFont(new Font("Serif", Font.BOLD, 20));

        choice.add(dateLabel);
        choice.add(startDate);
        choice.add(valueStartDate);
        choice.add(endDate);
        choice.add(valueEndDate);

        //label of room
        JLabel roomLabel = new JLabel("Les salles : ");
        roomLabel.setMaximumSize(new Dimension(100, 50));
        roomLabel.setPreferredSize(new Dimension(100, 50));
        roomLabel.setMinimumSize(new Dimension(100, 50));
        roomLabel.setBorder(BorderFactory.createMatteBorder(0,0, 1, 0, Color.black));
        roomLabel.setBounds(20, 140,100,50);
        roomLabel.setFont(new Font("Serif", Font.BOLD, 20));

        JTextField openSpace = new JTextField("Open-space : ");
        openSpace.setEditable(false);
        openSpace.setBackground(Color.WHITE);
        openSpace.setBounds(20, 200, 100, 20);
        openSpace.setBorder(BorderFactory.createLineBorder(Color.WHITE));

        JCheckBox checkBoxOpenSpace = new JCheckBox();
        checkBoxOpenSpace.setBounds(120, 200, 20, 20);
        checkBoxOpenSpace.setBackground(Color.WHITE);
        checkBoxOpenSpace.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                JTextField quantityOpenSpace = new JTextField("- nombre d'open-space : ");
                quantityOpenSpace.setEditable(false);
                quantityOpenSpace.setBackground(Color.WHITE);
                quantityOpenSpace.setBounds(400, 200, 150, 20);
                quantityOpenSpace.setBorder(BorderFactory.createLineBorder(Color.WHITE));

                JTextField valueOpenSpace = new JTextField(" ", 20);
                valueOpenSpace.setBackground(Color.WHITE);
                valueOpenSpace.setBounds(550, 200, 50, 20);
                choice.add(quantityOpenSpace);
                choice.add(valueOpenSpace);
            }
        });

        JTextField meetingRoom = new JTextField("Salle de reunion : ");
        meetingRoom.setEditable(false);
        meetingRoom.setBackground(Color.WHITE);
        meetingRoom.setBounds(20, 230, 100, 20);
        meetingRoom.setBorder(BorderFactory.createLineBorder(Color.WHITE));

        JCheckBox checkBoxMeetingRoom = new JCheckBox();
        checkBoxMeetingRoom.setBounds(120, 230, 20, 20);
        checkBoxMeetingRoom.setBackground(Color.WHITE);

        JTextField singleOffice = new JTextField("Bureau individuel : ");
        singleOffice.setEditable(false);
        singleOffice.setBackground(Color.WHITE);
        singleOffice.setBounds(200, 200, 100, 20);
        singleOffice.setBorder(BorderFactory.createLineBorder(Color.WHITE));

        JCheckBox checkBoxSingleOffice = new JCheckBox();
        checkBoxSingleOffice.setBounds(300, 200, 20, 20);
        checkBoxSingleOffice.setBackground(Color.WHITE);

        JTextField closedOffice = new JTextField("Bureau ferme : ");
        closedOffice.setEditable(false);
        closedOffice.setBackground(Color.WHITE);
        closedOffice.setBounds(200, 230, 100, 20);
        closedOffice.setBorder(BorderFactory.createLineBorder(Color.WHITE));

        JCheckBox checkBoxClosedOffice = new JCheckBox();
        checkBoxClosedOffice.setBounds(300, 230, 20, 20);
        checkBoxClosedOffice.setBackground(Color.WHITE);

        choice.add(roomLabel);
        choice.add(openSpace);
        choice.add(checkBoxOpenSpace);
        choice.add(meetingRoom);
        choice.add(checkBoxMeetingRoom);
        choice.add(singleOffice);
        choice.add(checkBoxSingleOffice);
        choice.add(closedOffice);
        choice.add(checkBoxClosedOffice);



        pageBody.add(choice, BorderLayout.SOUTH);
        pageBody.setBackground(Color.WHITE);
        getContentPane().add(menu, BorderLayout.WEST);
        getContentPane().add(pageBody, BorderLayout.CENTER);
    }
    public JPanel title(){
        JPanel titlePage = new JPanel();
        titlePage.setLayout(null);
        Dimension dimTitle = new Dimension(950, 100);
        titlePage.setMaximumSize(dimTitle);
        titlePage.setPreferredSize(dimTitle);
        titlePage.setMinimumSize(dimTitle);
        titlePage.setBackground(Color.WHITE);


        ImageIcon iconBack = new ImageIcon(new ImageIcon("C:\\Users\\cedri\\Bureau\\pds\\image\\flecheGauche.png").getImage().getScaledInstance(75,75,Image.SCALE_DEFAULT));
        JButton flecheBack = new JButton(iconBack);
        flecheBack.setBackground(Color.white);
        flecheBack.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        flecheBack.setBounds(0, 0, 75, 75);


        JTextField title = new JTextField("Realisation d'une location : ", 50);
        title.setFont(new Font("Serif", Font.BOLD, 35));
        title.setHorizontalAlignment(JTextField.RIGHT);
        title.setBackground(Color.WHITE);
        title.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        title.setBorder(BorderFactory.createMatteBorder(0,1, 1, 0, Color.RED));
        title.setEditable(false);
        title.setBounds(500, 0, 420, 75);

        titlePage.add(flecheBack);
        titlePage.add(title);

        return titlePage;
    }

    public JLabel fleche(){
        ImageIcon iconAdvancement = new ImageIcon(new ImageIcon("C:\\Users\\cedri\\Bureau\\pds\\image\\flecheAdvancement.png").getImage().getScaledInstance(50,30,Image.SCALE_DEFAULT));
        JLabel flecheAdvancement = new JLabel(iconAdvancement,JLabel.CENTER);
        return flecheAdvancement;
    }
    public JPanel rentalAdvancement(){
        JPanel rentalAdvancement = new JPanel();
        rentalAdvancement.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 15));
        Dimension dimAdvancement = new Dimension(950, 70);
        rentalAdvancement.setMaximumSize(dimAdvancement);
        rentalAdvancement.setPreferredSize(dimAdvancement);
        rentalAdvancement.setMinimumSize(dimAdvancement);
        rentalAdvancement.setBackground(Color.WHITE);
        rentalAdvancement.setBorder(BorderFactory.createMatteBorder(0,0,2,0, Color.BLACK));

        JTextField criteria= new JTextField("Criteres", 5);
        criteria.setEditable(false);
        criteria.setOpaque(false);
        criteria.setBorder(BorderFactory.createEmptyBorder(10,20,10,0));
        JTextField choiceField = new JTextField("Choix",5);
        choiceField.setEditable(false);
        choiceField.setOpaque(false);
        choiceField.setBorder(BorderFactory.createEmptyBorder(10,2,10,0));
        JTextField equipment = new JTextField("Equipements/Capteurs",15);
        equipment.setEditable(false);
        equipment.setOpaque(false);
        equipment.setBorder(BorderFactory.createEmptyBorder(10,2,10,0));
        JTextField bill = new JTextField("Facture", 5);
        bill.setEditable(false);
        bill.setOpaque(false);
        bill.setBorder(BorderFactory.createEmptyBorder(10,2,10,0));

        rentalAdvancement.add(criteria);
        rentalAdvancement.add(fleche());
        rentalAdvancement.add(choiceField);
        rentalAdvancement.add(fleche());
        rentalAdvancement.add(equipment);
        rentalAdvancement.add(fleche());
        rentalAdvancement.add(bill);

        return rentalAdvancement;
    }


    public JPanel menu(){

        JPanel menu = new JPanel();
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.add(Box.createVerticalStrut(100));

        JButton realize = new JButton("Realiser une location");
        realize.addActionListener(this);
        realize.setMinimumSize(new Dimension(Integer.MAX_VALUE, 75));
        realize.setPreferredSize(new Dimension(Integer.MAX_VALUE, 75));
        realize.setMaximumSize(new Dimension(Integer.MAX_VALUE, 75));
        realize.setBackground(Color.CYAN);

        JButton consult = new JButton("Consulter une location");
        consult.addActionListener(this);
        consult.setMinimumSize(new Dimension(Integer.MAX_VALUE, 75));
        consult.setPreferredSize(new Dimension(Integer.MAX_VALUE, 75));
        consult.setMaximumSize(new Dimension(Integer.MAX_VALUE, 75));
        consult.setBackground(Color.CYAN);


        JButton staff = new JButton("Personnel");
        staff.addActionListener(this);
        staff.setMinimumSize(new Dimension(Integer.MAX_VALUE, 75));
        staff.setPreferredSize(new Dimension(Integer.MAX_VALUE, 75));
        staff.setMaximumSize(new Dimension(Integer.MAX_VALUE, 75));
        staff.setBackground(Color.CYAN);

        JPanel underMenu = new JPanel(new FlowLayout(FlowLayout.LEFT));
        underMenu.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        underMenu.setBackground(Color.CYAN);

        JButton disconnect = new JButton("Deconnecter");
        disconnect.addActionListener(this);
        disconnect.setMaximumSize(new Dimension(100, 100));
        disconnect.setBackground(Color.CYAN);

        ImageIcon iconHome = new ImageIcon(new ImageIcon("C:\\Users\\cedri\\Bureau\\pds\\image\\maison.png").getImage().getScaledInstance(18,18,Image.SCALE_DEFAULT));
        JButton home = new JButton(iconHome);
        home.addActionListener(this);
        home.setBackground(Color.CYAN);

        ImageIcon iconRefresh = new ImageIcon(new ImageIcon("C:\\Users\\cedri\\Bureau\\pds\\image\\actualiser.png").getImage().getScaledInstance(18,18,Image.SCALE_DEFAULT));
        JButton refresh = new JButton(iconRefresh);
        refresh.addActionListener(this);
        refresh.setBackground(Color.CYAN);

        underMenu.add(disconnect);
        underMenu.add(home);
        underMenu.add(refresh);

        menu.add(realize);
        menu.add(consult);
        menu.add(staff);
        menu.add(Box.createGlue());
        menu.add(underMenu);

        menu.setBackground(Color.CYAN);
        menu.setPreferredSize(new Dimension(250, 800));

        return menu;
    }
}
