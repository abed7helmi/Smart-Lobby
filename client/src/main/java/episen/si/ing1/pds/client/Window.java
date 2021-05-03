package episen.si.ing1.pds.client;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Window extends JFrame implements ActionListener {

    JButton  ok,plan,list, vide, store, tint, p1,p2,p3,p4,p5,p11,p22,p33,p44,p55,p111,p222,p333,p444,p555;
    private JTextField txt2,txt1,txt3,txt4,txt5,txt6;
    JPanel firstMenu= new JPanel(new GridLayout(10,2,100,10));
    private JPanel mplan=new JPanel(new GridLayout(1,3,0,0));
    private JPanel Jplan=new JPanel(new BorderLayout());
     JPanel listWindow=new JPanel(new GridLayout(10,2,0,0));
    private Dimension dim=new Dimension(5,5);
    private JPanel onstate=new JPanel(new GridLayout(6,2,0,0));
    private JPanel jstate=new JPanel(new GridLayout(5,1,0,0));
    private JPanel managestore=new JPanel(new GridLayout(1,2,0,0));
    private JPanel managetint=new JPanel(new GridLayout(3,1,0,0));
    private JPanel state=new JPanel(new BorderLayout());

    public Window(){
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setTitle("configuration fenêtres");
        this.setSize(1200,800);
        this.setLocationRelativeTo(null);
        vide=new JButton();
        ok=new JButton("ok");
        ok.addActionListener(this);
        vide.setBackground(Color.CYAN);
        plan=new JButton("Accéder au Plan");
        plan.addActionListener(this);
        plan.setBackground(Color.lightGray);
        list=new JButton("Accéder à la liste des fenêtres");
        list.addActionListener(this);
        list.setBackground(Color.lightGray);
        JLabel l1 = new JLabel("                                                                                           CONFIGURATION DES FENETRES                                ");
        l1.setPreferredSize(new Dimension( 50, 50));
        p1=new JButton("100%");
        p1.addActionListener(this);
        p2=new JButton("75%");
        p2.addActionListener(this);
        p3=new JButton("50%");
        p3.addActionListener(this);
        p4=new JButton("25%");
        p4.addActionListener(this);
        p5=new JButton("0%");
        p5.addActionListener(this);
        p11=new JButton("100%");
        p11.addActionListener(this);
        p22=new JButton("75%");
        p22.addActionListener(this);
        p33=new JButton("50%");
        p33.addActionListener(this);
        p44=new JButton("25%");
        p44.addActionListener(this);
        p55=new JButton("0%");
        p55.addActionListener(this);
        p111=new JButton("100%");
        p111.addActionListener(this);
        p222=new JButton("75%");
        p222.addActionListener(this);
        p333=new JButton("50%");
        p333.addActionListener(this);
        p444=new JButton("25%");
        p444.addActionListener(this);
        p555=new JButton("0%");
        p555.addActionListener(this);
        JButton realize = new JButton("Réaliser une location");
        realize.setBackground(Color.CYAN);
        JButton consult = new JButton("Consulter une location");
        consult.setBackground(Color.CYAN);
        consult.addActionListener(this);
        JButton staff = new JButton("Personnel");
        staff.setBackground(Color.CYAN);
        firstMenu.add(l1);
        firstMenu.add(plan);
        firstMenu.add(list);
        txt1=new JTextField();
        txt2=new JTextField();
        txt3=new JTextField();
        txt4=new JTextField();
        txt5=new JTextField();
        txt6=new JTextField();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String result= Client.sendBd("requestWindow1");
        String tab=result.split("#")[1];
        System.out.println(tab);
        System.out.println(tab.charAt(1));
        Ihm ihm=new Ihm("ok","oka","okay");
        if (e.getActionCommand().equals("Accéder au Plan")){
            ImageIcon lplan = new ImageIcon(new ImageIcon("C:\\Users\\windows 10\\Desktop\\pds\\R3\\plan.jpg").getImage().getScaledInstance(800,800,Image.SCALE_DEFAULT));
            plan = new JButton(lplan);
            mplan.add(new JLabel("cliquez sur l'emplacement   rouge(hors service)    vert(disponible)"));
            Jplan.add(mplan, BorderLayout.NORTH);
            Jplan.add(plan, BorderLayout.CENTER);
            ihm.pageBody.add(Jplan,"Accéder au plan");
            ihm.pages.show(ihm.pageBody,"Accéder au plan");
        }
        if (e.getActionCommand().equals("Accéder à la liste des fenêtres")) {
            getContentPane().remove(firstMenu);
            listWindow.add(new JLabel("cliquez sur la fenêtres de votre choix"));
            for (int i=0; i<tab.length();i++){
                JButton j=new JButton(""+tab.charAt(i));
                j.setBackground(Color.lightGray);
                j.addActionListener(this);
                listWindow.add(j);
            }
            //ihm.pageBody.add(listWindow,"Accéder à la liste des fenetres");
            //ihm.pages.show(ihm.pageBody,"Accéder à la liste des fenetres");
            getContentPane().add(listWindow, BorderLayout.CENTER);
            this.setVisible(true);
        }
        if (e.getActionCommand().equals("1")||e.getActionCommand().equals("2")||e.getActionCommand().equals("3")||e.getActionCommand().equals("d")||e.getActionCommand().equals("f")){

            store=new JButton("gérer les stores");
            store.setBackground(Color.lightGray);
            tint=new JButton("gérer la teinte");
            tint.setBackground(Color.lightGray);
            tint.addActionListener(this);
            store.addActionListener(this);
            onstate.add(new JLabel("Température intérieure"));
            onstate.add(txt1);
            onstate.add(new JLabel("Température extérieure"));
            onstate.add(txt2);
            onstate.add(new JLabel("luminosité intérieur"));
            onstate.add(txt3);
            onstate.add(new JLabel("luminosité extérieur"));
            onstate.add(txt4);
            onstate.add(new JLabel("pourcentage de teinte"));
            onstate.add(txt5);
            onstate.add(new JLabel("pourcentage des stores"));
            onstate.add(txt6);
            state.add(new JLabel("etat actuelle de la fenêtre"),BorderLayout.NORTH);
            state.add(onstate,BorderLayout.CENTER);
            jstate.add(state);
            jstate.add(store);
            jstate.add(tint);
            ihm.pageBody.add(jstate,"ok");
            ihm.pages.show(ihm.pageBody,"ok");
        }
        if (e.getActionCommand().equals("gérer les stores")){
            JPanel managestore1=new JPanel(new GridLayout(6,3,0,0));
            JPanel managestore2=new JPanel(new GridLayout(6,3,0,0));
            managestore1.add(new JLabel("pourcentage fermeture"));
            managestore1.add(new JLabel("température intérieure"));
            managestore1.add(new JLabel("température extérieure"));
            managestore1.add(p11);
            managestore1.add(new JLabel("1°"));
            managestore1.add(new JLabel("5°"));
            managestore1.add(p22);
            managestore1.add(new JLabel("11°"));
            managestore1.add(new JLabel("15°"));
            managestore1.add(p33);
            managestore1.add(new JLabel("21°"));
            managestore1.add(new JLabel("25°"));
            managestore1.add(p44);
            managestore1.add(new JLabel("31°"));
            managestore1.add(new JLabel("35°"));
            managestore1.add(p55);
            managestore1.add(new JLabel("51°"));
            managestore1.add(new JLabel("55°"));
            managestore2.add(new JLabel("pourcentage ouverture"));
            managestore2.add(new JLabel("température intérieure"));
            managestore2.add(new JLabel("température extérieure"));
            managestore2.add(p111);
            managestore2.add(new JLabel("5°"));
            managestore2.add(new JLabel("1°"));
            managestore2.add(p222);
            managestore2.add(new JLabel("15°"));
            managestore2.add(new JLabel("11°"));
            managestore2.add(p333);
            managestore2.add(new JLabel("25°"));
            managestore2.add(new JLabel("21°"));
            managestore2.add(p444);
            managestore2.add(new JLabel("35°"));
            managestore2.add(new JLabel("31°"));
            managestore2.add(p555);
            managestore2.add(new JLabel("55°"));
            managestore2.add(new JLabel("51°"));
            managestore.add(managestore1);
            managestore.add(managestore2);
            managestore.setPreferredSize(dim);
            managestore.setBackground(Color.GRAY);
            ihm.pageBody.add(managestore,"gérer les stores");
            ihm.pages.show(ihm.pageBody,"gérer les stores");
        }
        if (e.getActionCommand().equals("gérer la teinte")){
            JPanel managetint1=new JPanel(new GridLayout(6,3,0,0));
            managetint1.add(new JLabel("pourcentage de teinte"));
            managetint1.add(new JLabel("luminosité intérieure"));
            managetint1.add(new JLabel("luminosité extérieure"));
            managetint1.add(p1);
            managetint1.add(new JLabel("20"));
            managetint1.add(new JLabel("25"));
            managetint1.add(p2);
            managetint1.add(new JLabel("30"));
            managetint1.add(new JLabel("15"));
            managetint1.add(p3);
            managetint1.add(new JLabel("34"));
            managetint1.add(new JLabel("67"));
            managetint1.add(p4);
            managetint1.add(new JLabel("45"));
            managetint1.add(new JLabel("13"));
            managetint1.add(p5);
            managetint1.add(new JLabel("45"));
            managetint1.add(new JLabel("56"));
            managetint.add(managetint1);
            ihm.pageBody.add(managetint,"gérer la teinte");
            ihm.pages.show(ihm.pageBody,"gérer la teinte");
        }
        if (e.getActionCommand().equals("100%")||e.getActionCommand().equals("75%")||e.getActionCommand().equals("50%")||e.getActionCommand().equals("25%")||e.getActionCommand().equals("0%")){
            onstate.removeAll();
            if (e.getSource().equals(p11))txt1=new JTextField("1°");
            if (e.getSource().equals(p11))txt2=new JTextField("5°");
            if (e.getSource().equals(p11))txt3=new JTextField("null");
            if (e.getSource().equals(p11))txt4=new JTextField("null");
            if (e.getSource().equals(p11))txt5=new JTextField("null");
            if (e.getSource().equals(p11))txt6=new JTextField("100%");
            onstate.add(new JLabel("Température intérieure"));
            onstate.add(txt1);
            onstate.add(new JLabel("Température extérieure"));
            onstate.add(txt2);
            onstate.add(new JLabel("luminosité intérieur"));
            onstate.add(txt3);
            onstate.add(new JLabel("luminosité extérieur"));
            onstate.add(txt4);
            onstate.add(new JLabel("pourcentage de teinte"));
            onstate.add(txt5);
            onstate.add(new JLabel("pourcentage des stores"));
            onstate.add(txt6);
            state.add(onstate,BorderLayout.CENTER);
            ihm.pageBody.add(state,"valide");
            ihm.pages.show(ihm.pageBody,"valide");
        }
    }

}
