package episen.si.ing1.pds.client;

import episen.si.ing1.pds.client.Mapping.Mapping;
import episen.si.ing1.pds.client.reservation.ChoiceCriteria;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;


public class Ihm extends JFrame{
    private JFrame frame = new JFrame();
   private JPanel pageBody = new JPanel();
    public  static JButton buttonVoid = new JButton("Annuler");
    public  static JButton buttonContinue = new JButton("> Continuer");
    private JPanel pageBody1 ;
    private JPanel pageBody2 ;
    private JPanel pageBody3 ;
    private JPanel pageBody4 ;
    private JPanel pageBody7 ;
    private String company_id ="";
    public static String path = System.getenv("PDSIMG");
    private CardLayout pages;

    public Ihm(String name, String page, String id) {
        pages = new CardLayout();
        company_id = id;
        frame = this;

        pageBody.setLayout(pages);
        ChoiceCriteria reservation = new ChoiceCriteria(frame, company_id);
        pageBody1 = reservation.realizeReservation();

        Mapping m = new Mapping();
        m.mapping();
        pageBody2 = m.getPanel();

        AcceuilPersonnel personnel = new AcceuilPersonnel(frame,company_id);
        pageBody7=personnel.acceuil();

        Window window=new Window(pages,pageBody);
        pageBody4=window.firstMenu;

        Indicators indicator = new Indicators();
        pageBody3 = indicator.getIndicator();

        if(page.equals("realize")){
            pageBody.add(pageBody1,"realize");
            pageBody.add(pageBody2,"consult");
            pageBody.add(pageBody4,"Configurer fenêtre");
            pageBody.add(pageBody3,"indicator");
            pageBody.add(pageBody7,"staff");
        } else if(page.equals("consult")){
            pageBody.add(pageBody2,"consult");
            pageBody.add(pageBody1,"realize");
            pageBody.add(pageBody4,"Configurer fenêtre");
            pageBody.add(pageBody3,"indicator");
            pageBody.add(pageBody7,"staff");
        }
        else if(page.equals("Configurer fenêtre")){
            pageBody.add(pageBody4,"Configurer fenêtre");
            pageBody.add(pageBody2,"consult");
            pageBody.add(pageBody1,"realize");
            pageBody.add(pageBody3,"indicator");
            pageBody.add(pageBody7,"staff");
        }
        else if(page.equals("indicator")){
            pageBody.add(pageBody3,"indicator");
            pageBody.add(pageBody2,"consult");
            pageBody.add(pageBody1,"realize");
            pageBody.add(pageBody7,"staff");
            pageBody.add(pageBody4,"Configurer fenêtre");
        }else if(page.equals("staff")){
            pageBody.add(pageBody7,"staff");
            pageBody.add(pageBody3,"indicator");
            pageBody.add(pageBody2,"consult");
            pageBody.add(pageBody1,"realize");
            pageBody.add(pageBody4,"Configurer fenêtre");

        }

        buttonVoid = navJButton(buttonVoid,670,10,100,50);
        buttonContinue = navJButton(buttonContinue, 780,10,100,50);


        frame.add(pageBody, BorderLayout.CENTER);

        JPanel menu = new JPanel();
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.add(Box.createVerticalStrut(100));

        JButton realize = new JButton("Realiser une location");
        realize.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pages.show(pageBody,"realize");
            }
        });

        sizeComposant(new Dimension(Integer.MAX_VALUE, 75), realize);
        setColor(realize,Color.white,new Color(0, 102,204));

        JButton consult = new JButton("Consulter une location");
        consult.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pageBody.remove(pageBody2);
                m.mapping();
                pageBody2 = m.getPanel();
                pageBody.add(pageBody2,"consult");
                pages.show(pageBody,"consult");
            }
        });
        sizeComposant(new Dimension(Integer.MAX_VALUE, 75), consult);
        setColor(consult,Color.white,new Color(0, 102,204));

        JButton staff = new JButton("Personnel");
        staff.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pages.show(pageBody, "staff");
            }
        });
        sizeComposant(new Dimension(Integer.MAX_VALUE, 75), staff);
        setColor(staff,Color.white,new Color(0, 102,204));

        JButton configWindow = new JButton("Configurer fenêtre");
        configWindow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pages.show(pageBody, "Configurer fenêtre");
            }
        });
        sizeComposant(new Dimension(Integer.MAX_VALUE, 75), configWindow);
        setColor(configWindow,Color.white,new Color(0, 102,204));

        JButton indicatorButton = new JButton("Indicateurs et locations");
        setColor(indicatorButton,Color.white,new Color(0, 102,204));
        indicatorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pages.show(pageBody,"indicator");
            }
        });
        sizeComposant(new Dimension(Integer.MAX_VALUE, 75), indicatorButton);
        setColor(consult,Color.white,new Color(0, 102,204));

        JPanel underMenu = new JPanel(new FlowLayout(FlowLayout.LEFT));
        underMenu.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        underMenu.setBackground(new Color(0, 102,204));

        try{
            ImageIcon iconHome = new ImageIcon(ImageIO.read(new File(path+"maison.png")));
            iconHome = new ImageIcon(iconHome.getImage().getScaledInstance(18, 18, Image.SCALE_DEFAULT));
            JButton home = new JButton(iconHome);
            home.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Menu Menu = new Menu("Smart Lobby", company_id);
                    frame.dispose();
                }
            });
            setColor(home,Color.white,new Color(0, 102,204));
            underMenu.add(home);
        } catch(Exception e){}


        JButton disconnect = new JButton("Deconnecter");
        disconnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        disconnect.setMaximumSize(new Dimension(100, 100));
        setColor(disconnect,Color.white,new Color(0, 102,204));
        
        underMenu.add(disconnect);

        menu.add(realize);
        menu.add(consult);
        menu.add(configWindow);
        menu.add(staff);
        menu.add(indicatorButton);
        menu.add(Box.createGlue());
        menu.add(underMenu);

        menu.setBackground(new Color(0, 102,204));
        menu.setPreferredSize(new Dimension(250, 800));

        frame.add(menu , BorderLayout.WEST);
        frame.setSize(1200, 800);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
    }

    public void setColor(JButton button,Color font, Color back){
        button.setForeground(font);
        button.setBackground(back);
    }

    public static JButton navJButton(JButton button, int x,int y,int w,int h){
        button.setBounds(x, y, w, h);
        button.setBackground(new Color(255,255,255));
        button.setForeground(Color.black);
        button.setBorder(BorderFactory.createLineBorder(Color.black));
        return button;
    }
    public static void sizeComposant(Dimension dim, Component c){
        c.setPreferredSize(dim);
        c.setMaximumSize(dim);
        c.setMinimumSize(dim);
    }
    public static JTextField styleJTextFieldReservation(JTextField t, int x, int y, int w, int h, Color c1, Color c2) {
        t.setEditable(false);
        t.setBackground(c1);
        t.setBorder(BorderFactory.createLineBorder(c2));
        t.setBounds(x, y, w, h);
        return t;
    }
}
