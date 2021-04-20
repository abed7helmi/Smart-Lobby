package episen.si.ing1.pds.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IhmPersonnel {


    static public void main (String[] args) {
        Cadre cadre = new Cadre();
        cadre.cadre();
    }
}

/*class Cadre implements ActionListener {
    JButton bouton;
    private JPanel title, registeredCompany;
    private JTextField companyName, unregisteredCompanyName;
    private JButton entry, entryUnregistered;
    private GridBagConstraints gc = new GridBagConstraints();
    public void cadre() {
        /*JFrame fenetre = new JFrame();
        fenetre.setTitle("Un exemple sur MacBook");
        fenetre.setSize(400, 100); fenetre.setLocationRelativeTo(null);
        bouton = new JButton("Clique moi"); bouton.addActionListener(this);
        fenetre.getContentPane().add(bouton);
        /*fenetre.getContentPane().setLayout(new FlowLayout());*/
        /*fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.setVisible(true);*/
        /*JFrame fenetre = new JFrame();
        fenetre.setSize(500, 500);

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

        fenetre.getContentPane().add(title, BorderLayout.NORTH);
        fenetre.getContentPane().add(registeredCompany, BorderLayout.CENTER);
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.setVisible(true);
    }




    @Override
    public void actionPerformed(ActionEvent e) {
        bouton.setText("Bravo");
    }
}*/
class Cadre implements ActionListener {
    public JButton staff;
    JPanel pageBody;
    public void cadre() {
        JFrame fenetre = new JFrame();
        //fenetre.setSize(1200, 800);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        fenetre.setSize(screenSize.width, screenSize.height);

        pageBody = new JPanel();

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


        staff = new JButton("Personnel");
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
        ImageIcon iconHome = new ImageIcon(new ImageIcon("C:\\Users\\Helmi\\Bureau\\PDS2\\images\\maison.png").getImage().getScaledInstance(18, 18, Image.SCALE_DEFAULT));
        JButton home = new JButton(iconHome);
        home.addActionListener(this);
        home.setBackground(Color.CYAN);

        ImageIcon iconRefresh = new ImageIcon(new ImageIcon("C:\\Users\\Helmi\\Bureau\\PDS2\\images\\actualiser.png").getImage().getScaledInstance(18, 18, Image.SCALE_DEFAULT));
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

        fenetre.getContentPane().add(menu, BorderLayout.WEST);
        fenetre.getContentPane().add(pageBody, BorderLayout.CENTER);

        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source==staff){
            JButton CreateBadge = new JButton("Nouveau Badge");
            CreateBadge.addActionListener(this);
            CreateBadge.setMinimumSize(new Dimension(Integer.MAX_VALUE, 75));
            CreateBadge.setPreferredSize(new Dimension(Integer.MAX_VALUE, 75));
            CreateBadge.setMaximumSize(new Dimension(Integer.MAX_VALUE, 75));
            CreateBadge.setBackground(Color.CYAN);

            JButton ManageBadge = new JButton("Gestion Badge");
            ManageBadge .addActionListener(this);
            ManageBadge .setMinimumSize(new Dimension(Integer.MAX_VALUE, 75));
            ManageBadge .setPreferredSize(new Dimension(Integer.MAX_VALUE, 75));
            ManageBadge .setMaximumSize(new Dimension(Integer.MAX_VALUE, 75));
            ManageBadge .setBackground(Color.CYAN);

            pageBody.add(CreateBadge);


        }

    }
}


