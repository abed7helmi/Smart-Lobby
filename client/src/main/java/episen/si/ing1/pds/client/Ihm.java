package episen.si.ing1.pds.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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


    public Ihm(String name,Socket s) {
        socket = s;
        try {
            output = new PrintWriter(socket.getOutputStream(), true);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        setTitle(name);
        firstPage();

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


    public void menu(){
        setSize(1200, 800);

        JPanel pageBody = new JPanel();

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
        pageBody.setBackground(Color.WHITE);

        getContentPane().add(menu, BorderLayout.WEST);
        getContentPane().add(pageBody, BorderLayout.CENTER);
    }
}
