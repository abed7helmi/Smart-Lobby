package episen.si.ing1.pds.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.HashMap;
import java.util.Map;

public class MyPermission {
    private JFrame frame;
    private JPanel pageBody;
    private JPanel view;
    private JButton confirm;

    private Map<String, String> input = new HashMap<>();


    public MyPermission(JFrame f){
        input.clear();
        this.frame = f;

    }


    public void choicepermission(JPanel pb){
        this.pageBody = pb;
        pageBody.setBackground(Color.WHITE);
        view = view();

        view.setLayout(new BorderLayout());


        view.setBackground(Color.white);


        JPanel panneau1 = new JPanel();
        panneau1.setLayout(null);
        panneau1.setBackground(Color.white);
        panneau1.setPreferredSize(new Dimension(200, 150));

        view.add(panneau1, BorderLayout.NORTH);


        JPanel panneau2 = new JPanel();
        panneau2.setLayout(new BorderLayout());
        panneau2.setBackground(Color.blue);
        panneau2.setPreferredSize(new Dimension(200, 150));

        view.add(panneau2, BorderLayout.CENTER);

        JPanel panneau3 = new JPanel();
        panneau3.setLayout(null);
        panneau3.setBackground(Color.white);
        panneau3.setPreferredSize(new Dimension(200, 150));

        view.add(panneau3, BorderLayout.SOUTH);



        JLabel infoLabel = new JLabel("Creer Droits : ");
        infoLabel = styleJLabelBadge(infoLabel, 20, 20,250,20);
        panneau1.add(infoLabel);

        JTextField device = new JTextField("Ajouter Equipement :");
        device = styleJTextFieldBadge( device, 50, 50, 140, 20);
        panneau1.add(device);

        String[] devices = {"Fenetre X45","PC 48","Capteur 45"};
        JComboBox mydevice = new JComboBox(devices);
        mydevice.setEditable(true);
        mydevice.setBounds(200,50, 180, 20);
        panneau1.add(mydevice);


        JTextField PermissionName = new JTextField("Libelle droit:");
        PermissionName = styleJTextFieldBadge(PermissionName, 350, 15, 80, 20);

        JTextField valuePermissionName = new JTextField(" ");
        valuePermissionName.setBounds(440, 15, 100, 20);


        confirm = new JButton("Confirmer");
        confirm.setEnabled(false);
        JButton cancel = new JButton("Annuler");


        cancel.setBounds(300, 60, 150, 30);
        confirm.setBounds(500, 60, 150, 30);



        panneau3.add(cancel);
        panneau3.add(confirm);
        panneau3.add(PermissionName);
        panneau3.add(valuePermissionName );




        Object[][] donnees = {
                {"952596", "Sykes", "Acces fenetres",true,"Sykes", "952596"},
                {"9465", "Van de Kampf","Acces fenetres",true,"Sykes", "9465"},
                {"4556", "Cuthbert", "Acces fenetres", true,"Sykes", "4556"},
                {"45546", "Valance", "Acces fenetres", false,"Sykes", "45546"},
                {"8456", "Schrödinger", "Acces fenetres", false,"Sykes", "8456"},
                {"4556", "Duke", "Acces capteurs", false,"Sykes", "4556"},
                {"788", "Trump", "Acces PC", true,"Sykes", "788"},
        };

        String[] entetes = {"Equipement", "Active", "Nb utilisation", "Durée","Salle","Suppression equipement"};

        JTable tableau = new JTable(donnees, entetes);




        tableau.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());

        tableau.getColumnModel().getColumn(5).setCellEditor(new ButtonEditor(new JTextField()));






        panneau2.add(tableau.getTableHeader(), BorderLayout.NORTH);
        panneau2.add(tableau, BorderLayout.CENTER);

       JTextField messageErrorLib = styleJTextFieldError(panneau3,570, 15, 30, 20);

        valuePermissionName.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {}
            @Override
            public void focusLost(FocusEvent e) {
                Object source = e.getSource();
                String m = (((JTextField)source).getText()).trim();
                if(m.matches("[a-zA-Z_0-9]+")) {
                    // System.out.println("waaw");
                    input.put("permissionlib", ((JTextField)source).getText().trim());
                    messageErrorLib.setText(" ");
                    if(verifMap()) confirm.setEnabled(true);
                }else {
                    //System.out.println("wiiw");
                    messageErrorLib.setText("X");
                    messageErrorLib.setForeground(Color.red);
                }
            }
        });


        //panneau2.add(viewtable);




        /*view.add(tableau);




        view.add(infoLabel);

        view.add(device);

        view.add(mydevice);
        view.add(PermissionName);
        view.add(valuePermissionName);
        view.add(confirm);
        view.add(cancel);*/



        pageBody.add(view);
        pageBody.repaint();
        frame.repaint();
    }

    public boolean verifMap(){
        if((input.containsKey("permissionlib")))
            return true;
        else {
            confirm.setEnabled(true);
            return false;
        }
    }




    public JPanel view(){
        JPanel view = new JPanel();
        view.setBackground(Color.white);
        sizeComposant(new Dimension(950,600), view);
        view.setLayout(null);



        return view;

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


    public JLabel styleJLabelBadge(JLabel l, int x, int y, int w, int h){
        sizeComposant(new Dimension(200, 200) ,l);
        l.setBorder(BorderFactory.createMatteBorder(0,0, 1, 0, Color.black));
        l.setBounds(x,y,w,h);
        l.setFont(new Font("Serif", Font.BOLD, 20));
        return l;
    }


    public void sizeComposant(Dimension dim, Component c){
        c.setPreferredSize(dim);
        c.setMaximumSize(dim);
        c.setMinimumSize(dim);
    }

    public JTextField styleJTextFieldBadge(JTextField t, int x, int y, int w, int h) {
        t.setEditable(false);
        t.setBackground(Color.WHITE);
        t.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        t.setBounds(x, y, w, h);
        return t;
    }

}
