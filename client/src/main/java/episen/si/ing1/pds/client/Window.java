package episen.si.ing1.pds.client;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;


public class Window extends JFrame implements ActionListener {

    JButton  ok,plan,list, vide, store, tint, p1,p2,p3,p4,p5,p11,p22,p33,p44,p55,p111,p222,p333,p444,p555;
    private JTextField txt2,txt1,txt3,txt4,txt5,txt6;
    protected JPanel firstMenu= new JPanel(new GridLayout(10,2,100,10));
    private JPanel mplan=new JPanel(new GridLayout(1,3,0,0));
    private JPanel Jplan=new JPanel(new BorderLayout());
    private JPanel listWindow=new JPanel(new GridLayout(10,2,0,0));
    private JPanel jstate=new JPanel(new GridLayout(5,1,0,0));
    private JPanel managestore=new JPanel(new GridLayout(1,2,0,0));
    private JPanel managetint=new JPanel(new GridLayout(3,1,0,0));

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
        if (e.getActionCommand().equals("Accéder au Plan")){
            Jplan.removeAll();
            ImageIcon lplan = new ImageIcon(new ImageIcon("C:\\Users\\windows 10\\Desktop\\pds\\R3\\plan.jpg").getImage().getScaledInstance(800,800,Image.SCALE_DEFAULT));
            plan = new JButton(lplan);
            mplan.add(new JLabel("cliquez sur l'emplacement   rouge(hors service)    vert(disponible)"));
            Jplan.add(mplan, BorderLayout.NORTH);
            Jplan.add(plan, BorderLayout.CENTER);
            Ihm.pageBody.add(Jplan,"Accéder au plan");
            Ihm.pages.show(Ihm.pageBody,"Accéder au plan");
        }
        if (e.getActionCommand().equals("Accéder à la liste des fenêtres")) {
            listWindow.removeAll();
            listWindow.add(new JLabel("cliquez sur la fenêtres de votre choix"));
            String m ="";
            Client.map.get("requestWindow").put("device_id", m);
            ObjectMapper mapper = new ObjectMapper(new JsonFactory());
            Map<String, Map<String, String>> equipments = null;
            try {
                equipments = mapper.readValue(Client.sendBd("requestWindow"),new TypeReference<Map<String, Map<String, String>>>(){});
            } catch (JsonProcessingException jsonProcessingException) {
                jsonProcessingException.printStackTrace();
            }
            String[] equipmentList = new String[equipments.size()];
            for(int i=0;i<equipmentList.length;i++) {
                equipmentList[i]= String.valueOf(equipments.get("device_id"));
            }
            for(int i=0;i<equipmentList.length;i++) {
                JButton j=new JButton(""+i);
                j.setBackground(Color.lightGray);
                j.addActionListener(this);
                listWindow.add(j);
            }
            listWindow.add(new JButton("1"));
            Ihm.pageBody.add(listWindow,"Accéder à la liste des fenetres");
            Ihm.pages.show(Ihm.pageBody,"Accéder à la liste des fenetres");

        }
        if (e.getActionCommand().equals("1")||e.getActionCommand().equals("2")||e.getActionCommand().equals("0")||e.getActionCommand().equals("d")||e.getActionCommand().equals("f")){
            jstate.removeAll();
            JPanel onstate=new JPanel(new GridLayout(6,2,0,0));
            JPanel state=new JPanel(new BorderLayout());
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
            getContentPane().add(jstate);
            setVisible(true);
            Ihm.pageBody.add(jstate,"ok");
            Ihm.pages.show(Ihm.pageBody,"ok");
        }
        if (e.getActionCommand().equals("gérer les stores")){
            managestore.removeAll();
            JPanel managestore1=new JPanel(new GridLayout(6,3,0,0));
            JPanel managestore2=new JPanel(new GridLayout(6,3,0,0));
            managestore1.add(new JLabel("pourcentage fermeture"));
            managestore1.add(new JLabel("température extérieure"));
            managestore1.add(new JLabel("température intérieure"));
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
            managestore2.add(new JLabel("température extérieure"));
            managestore2.add(new JLabel("température intérieure"));
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
            managestore.setBackground(Color.GRAY);
            Ihm.pageBody.add(managestore,"gérer les stores");
            Ihm.pages.show(Ihm.pageBody,"gérer les stores");
        }
        if (e.getActionCommand().equals("gérer la teinte")){
            managetint.removeAll();
            JPanel managetint1=new JPanel(new GridLayout(6,3,0,0));
            managetint1.add(new JLabel("pourcentage de teinte"));
            managetint1.add(new JLabel("luminosité intérieure"));
            managetint1.add(new JLabel("luminosité extérieure"));
            managetint1.add(p1);
            managetint1.add(new JLabel("20"));
            managetint1.add(new JLabel("65"));
            managetint1.add(p2);
            managetint1.add(new JLabel("30"));
            managetint1.add(new JLabel("50"));
            managetint1.add(p3);
            managetint1.add(new JLabel("50"));
            managetint1.add(new JLabel("50"));
            managetint1.add(p4);
            managetint1.add(new JLabel("45"));
            managetint1.add(new JLabel("13"));
            managetint1.add(p5);
            managetint1.add(new JLabel("56"));
            managetint1.add(new JLabel("20"));
            managetint.add(managetint1);
            Ihm.pageBody.add(managetint,"gérer la teinte");
            Ihm.pages.show(Ihm.pageBody,"gérer la teinte");
        }
        if (e.getActionCommand().equals("100%")||e.getActionCommand().equals("75%")||e.getActionCommand().equals("50%")||e.getActionCommand().equals("25%")||e.getActionCommand().equals("0%")){
            JPanel state=new JPanel(new BorderLayout());
            JPanel onstate=new JPanel(new GridLayout(6,2,0,0));
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
            Ihm.pageBody.add(state,"valide");
            Ihm.pages.show(Ihm.pageBody,"valide");
        }
    }

}