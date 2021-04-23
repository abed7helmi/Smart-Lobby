package episen.si.ing1.pds.client;

import javax.swing.*;
import java.awt.*;

public class MyPermission {
    private JFrame frame;
    private JPanel pageBody;


    public MyPermission(JFrame f){

        this.frame = f;

    }


    public void choicepermission(JPanel pb){
        this.pageBody = pb;
        pageBody.setBackground(Color.WHITE);
        JPanel view = view();


        JLabel infoLabel = new JLabel("Creer Droits : ");
        infoLabel = styleJLabelReservation(infoLabel, 20, 20,250,20);

        JTextField device = new JTextField("Ajouter Equipement :");
        device = styleJTextFieldReservation( device, 50, 80, 140, 20);

        String[] devices = {"Fenetre X45","PC 48","Capteur 45"};
        JComboBox mydevice = new JComboBox(devices);
        mydevice.setEditable(true);
        mydevice.setBounds(200,80, 180, 20);


        JTextField PermissionName = new JTextField("Libelle droit:");
        PermissionName = styleJTextFieldReservation(PermissionName, 350, 500, 80, 20);

        JTextField valuePermissionName = new JTextField(" ");
        valuePermissionName.setBounds(440, 500, 100, 20);


        JButton confirm = new JButton("Confirmer");
        confirm.setEnabled(false);
        JButton cancel = new JButton("Annuler");


        cancel.setBounds(300, 550, 150, 30);
        confirm.setBounds(500, 550, 150, 30);







        Object[][] donnees = {
                {"952596", "Sykes", "Acces fenetres",true,"Sykes", "Acces fenetres"},
                {"9465", "Van de Kampf","Acces fenetres",true,"Sykes", "Acces fenetres"},
                {"4556", "Cuthbert", "Acces fenetres", true,"Sykes", "Acces fenetres"},
                {"45546", "Valance", "Acces fenetres", false,"Sykes", "Acces fenetres"},
                {"8456", "Schrödinger", "Acces fenetres", false,"Sykes", "Acces fenetres"},
                {"4556", "Duke", "Acces capteurs", false,"Sykes", "Acces fenetres"},
                {"788", "Trump", "Acces PC", true,"Sykes", "Acces fenetres"},
        };

        String[] entetes = {"Equipement", "Active", "Nb utilisation", "Durée","Salle","Action"};

        JTable tableau = new JTable(donnees, entetes);



        tableau.setBounds(80,200,700,300);




        view.add(tableau);




        view.add(infoLabel);

        view.add(device);

        view.add(mydevice);
        view.add(PermissionName);
        view.add(valuePermissionName);
        view.add(confirm);
        view.add(cancel);



        pageBody.add(view);
        pageBody.repaint();
        frame.repaint();
    }




    public JPanel view(){
        JPanel view = new JPanel();
        view.setBackground(Color.white);
        sizeComposant(new Dimension(950,600), view);
        view.setLayout(null);



        return view;

    }


    public JLabel styleJLabelReservation(JLabel l, int x, int y, int w, int h){
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

    public JTextField styleJTextFieldReservation(JTextField t, int x, int y, int w, int h) {
        t.setEditable(false);
        t.setBackground(Color.WHITE);
        t.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        t.setBounds(x, y, w, h);
        return t;
    }

}
