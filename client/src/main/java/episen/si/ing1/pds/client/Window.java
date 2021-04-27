package episen.si.ing1.pds.client;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window extends JFrame implements ActionListener {
    private JButton  ok,plan,list, vide,red,green, store, tint,valid;
    private JTextField txt2,txt1,txt3,txt4,txt5,txt6;
    JPanel firstMenu= new JPanel(new GridLayout(10,2,100,10));
    private JPanel mplan=new JPanel(new GridLayout(1,3,0,0));
    private JPanel Jplan=new JPanel(new BorderLayout());
    private JPanel listWindow=new JPanel(new GridLayout(10,1,0,0));
    private Dimension dim=new Dimension(5,5);
    private JPanel onstate=new JPanel(new GridLayout(6,2,0,0));
    private JPanel jstate=new JPanel(new GridLayout(3,1,0,0));
    private JPanel managestore=new JPanel(new GridLayout(4,2,0,0));
    private JPanel managetint=new JPanel(new GridLayout(3,1,0,0));
    private JPanel state=new JPanel(new BorderLayout());
    private JLabel je,jl1,jl2,jl3,jl4,jl5,jl6,p1,p2,p3,p4,p5;
    public Window(){
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setTitle("configuration fenêtres");
        this.setSize(1200,800);
        this.setLocationRelativeTo(null);
        vide=new JButton();
        ok=new JButton("ok");
        ok.addActionListener(this);
        valid=new JButton("valide");
        valid.addActionListener(this);
        vide.setBackground(Color.CYAN);
        plan=new JButton("Accéder au Plan");
        plan.addActionListener(this);
        list=new JButton("Accéder à la liste des fenêtres");
        list.addActionListener(this);
        JLabel l1 = new JLabel("                                                                                           CONFIGURATION DES FENETRES                                ");
        l1.setPreferredSize(new Dimension( 50, 50));
        p1=new JLabel("100%");
        p2=new JLabel("75%");
        p3=new JLabel("50%");
        p4=new JLabel("25%");
        p5=new JLabel("0%");
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
        getContentPane().add(firstMenu, BorderLayout.CENTER);
        JLabel lstate=new JLabel("ETAT ACTUELLE DE LA FENETRE");
        jl1=new JLabel("température intérieure");
        jl2=new JLabel("Température extérieure");
        jl3=new JLabel("Luminosité intérieure");
        jl4=new JLabel("Luminosité extérieure");
        jl5=new JLabel("Pourcentage de teinte");
        jl6=new JLabel("Pourcentage de store");
        txt1=new JTextField();
        txt2=new JTextField();
        txt3=new JTextField();
        txt4=new JTextField();
        txt5=new JTextField();
        txt6=new JTextField();
        onstate.add(jl1);
        onstate.add(txt1);
        onstate.add(jl2);
        onstate.add(txt2);
        onstate.add(jl3);
        onstate.add(txt3);
        onstate.add(jl4);
        onstate.add(txt4);
        onstate.add(jl5);
        onstate.add(txt5);
        onstate.add(jl6);
        onstate.add(txt6);
        state.add(lstate,BorderLayout.NORTH);
        state.add(onstate,BorderLayout.CENTER);
        this.setVisible(true);

    }
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals("Accéder au Plan")){
            getContentPane().remove(firstMenu);
            ImageIcon lred = new ImageIcon(new ImageIcon("C:\\Users\\windows 10\\Desktop\\pds\\R3\\red.png").getImage().getScaledInstance(10,10,Image.SCALE_DEFAULT));
            red = new JButton(lred + "   hors service");
            ImageIcon lgreen = new ImageIcon(new ImageIcon("C:\\Users\\windows 10\\Desktop\\pds\\R3\\green.png").getImage().getScaledInstance(10,10,Image.SCALE_DEFAULT));
            green = new JButton(lgreen + "   disponible");
            ImageIcon lplan = new ImageIcon(new ImageIcon("C:\\Users\\windows 10\\Desktop\\pds\\R3\\plan.jpg").getImage().getScaledInstance(800,800,Image.SCALE_DEFAULT));
            plan = new JButton(lplan);
            je=new JLabel("cliquez sur l'emplacement");
            mplan.add(je);
            mplan.add(green);
            mplan.add(red);
            Jplan.add(mplan, BorderLayout.NORTH);
            Jplan.add(plan, BorderLayout.CENTER);
            getContentPane().add(Jplan, BorderLayout.CENTER);
            this.setVisible(true);
        }
        if (e.getActionCommand().equals("Accéder à la liste des fenêtres")){
            System.out.println("ok");
            getContentPane().remove(firstMenu);
            JComboBox jlist = new JComboBox();
            JComboBox jlist1 = new JComboBox();
            JComboBox jlist2 = new JComboBox();
            JComboBox jlist3 = new JComboBox();
            jlist.addItem("e");
            JPanel menulist =new JPanel(new GridLayout(4,4,0,0));
            JLabel l=new JLabel("nom batiment");
            JLabel la=new JLabel("numéro étage");
            JLabel lb=new JLabel("nom salle");
            JLabel ld=new JLabel("libelle");
            menulist.add(l);
            menulist.add(jlist);
            menulist.add(la);
            menulist.add(jlist1);
            menulist.add(lb);
            menulist.add(jlist2);
            menulist.add(ld);
            menulist.add(jlist3);
            listWindow.add(menulist);
            ok.setLocation(10, 340);
            listWindow.add(ok);
            getContentPane().add(listWindow, BorderLayout.CENTER);
            this.setVisible(true);
        }
        if (e.getActionCommand().equals("ok")){
            getContentPane().remove(listWindow);
            getContentPane().remove(Jplan);
            store=new JButton("gérer les stores");
            tint=new JButton("gérer la teinte");
            tint.addActionListener(this);
            store.addActionListener(this);
            jstate.add(state);
            jstate.add(store);
            jstate.add(tint);
            getContentPane().add(jstate, BorderLayout.CENTER);
            this.setVisible(true);
        }
        if (e.getActionCommand().equals("gérer les stores")){
            getContentPane().remove(jstate);
            JPanel managestore1=new JPanel(new GridLayout(6,3,0,0));
            JPanel managestore2=new JPanel(new GridLayout(6,3,0,0));
            managestore1.add(new JLabel("pourcentage fermeture"));
            managestore1.add(new JLabel("température intérieure"));
            managestore1.add(new JLabel("température extérieure"));
            managestore1.add(new JLabel("100%"));
            managestore1.add(new JLabel("1°"));
            managestore1.add(new JLabel("5°"));
            managestore1.add(new JLabel("75%"));
            managestore1.add(new JLabel("11°"));
            managestore1.add(new JLabel("15°"));
            managestore1.add(new JLabel("50%"));
            managestore1.add(new JLabel("21°"));
            managestore1.add(new JLabel("25°"));
            managestore1.add(new JLabel("25%"));
            managestore1.add(new JLabel("31°"));
            managestore1.add(new JLabel("35°"));
            managestore1.add(new JLabel("0%"));
            managestore1.add(new JLabel("51°"));
            managestore1.add(new JLabel("55°"));
            managestore2.add(new JLabel("pourcentage ouverture"));
            managestore2.add(new JLabel("température intérieure"));
            managestore2.add(new JLabel("température extérieure"));
            managestore2.add(p1);
            managestore2.add(new JLabel("5°"));
            managestore2.add(new JLabel("1°"));
            managestore2.add(p2);
            managestore2.add(new JLabel("15°"));
            managestore2.add(new JLabel("11°"));
            managestore2.add(p3);
            managestore2.add(new JLabel("25°"));
            managestore2.add(new JLabel("21°"));
            managestore2.add(p4);
            managestore2.add(new JLabel("35°"));
            managestore2.add(new JLabel("31°"));
            managestore2.add(p5);
            managestore2.add(new JLabel("55°"));
            managestore2.add(new JLabel("51°"));
            managestore.add(managestore1);
            managestore.add(managestore2);
            managestore.add(jl1);
            managestore.add(txt1);
            managestore.add(jl2);
            managestore.add(txt2);
            managestore.add(valid);
            getContentPane().add(managestore, BorderLayout.CENTER);
            this.setVisible(true);
        }
        if (e.getActionCommand().equals("gérer la teinte")){
            getContentPane().remove(jstate);
            JPanel managetint1=new JPanel(new GridLayout(6,3,0,0));
            JPanel managetint2=new JPanel(new GridLayout(2,2,0,0));
            managetint2.add(jl3);
            managetint2.add(txt1);
            managetint2.add(jl4);
            managetint2.add(txt2);
            managetint.add(managetint1);
            managetint.add(managetint2);
            managetint.add(valid);
            getContentPane().add(managetint, BorderLayout.CENTER);
            this.setVisible(true);
        }
        if (e.getActionCommand().equals("valide")){
            getContentPane().remove(managestore);
            getContentPane().remove(managetint);
            onstate.add(jl1);
            onstate.add(txt1);
            onstate.add(jl2);
            onstate.add(txt2);
            onstate.add(jl3);
            onstate.add(txt3);
            onstate.add(jl4);
            onstate.add(txt4);
            onstate.add(jl5);
            onstate.add(txt5);
            onstate.add(jl6);
            onstate.add(txt6);
            // state.add(lstate,BorderLayout.NORTH);
            getContentPane().add(state,BorderLayout.CENTER);
            this.setVisible(true);
        }
    }
    public static void main(String[] args){
        Window window=new Window();
        window.setVisible(true);
    }
}
