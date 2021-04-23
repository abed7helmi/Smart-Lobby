package episen.si.ing1.pds.client;

import javax.swing.*;
import java.awt.*;

public class BadgePermissions {

    private JFrame frame;
    private JPanel pageBody = new JPanel();
    private JPanel view;

    public BadgePermissions(JFrame f) {

        frame = f;
    }

    public void choice(JPanel pb) {
        this.pageBody = pb;
        pageBody.setBackground(Color.WHITE);


        view = view();
        view.setLayout(new BorderLayout());



        view.setBackground(Color.white);

        String labels[] = { "Employe 1", "Employe 1", "Employe 1", "Employe 1","Employe 1", "Employe 1", "Employe 1", "Employe 1", "Employe 1", "Employe 61","Employe 15", "Employe 17", "Employe 14", "Employe 18","I", "J","feouiej" };

        JList list = new JList(labels);
        JScrollPane EmployeList = new JScrollPane(list);

        String labelschosen[] = {"Employe 1", "Employe 2" };

        JList listchosen = new JList(labelschosen);
        JScrollPane EmployeListChosen = new JScrollPane(listchosen);


        JTextField NomEmploye = new JTextField("Les Employes choisits:");
       /* NomEmploye = styleJTextFieldReservation(NomEmploye, 300, 220, 50, 20);*/

        JTextField NomEmploye2 = new JTextField("Choisir un Employe");
        /*NomEmploye2 = styleJTextFieldReservation(NomEmploye2, 100, 220, 50, 20);*/

        JPanel panneau1 = new JPanel(new BorderLayout());
        panneau1.setBackground(Color.yellow);
        panneau1.setPreferredSize(new Dimension(200, 200));


        JPanel panneau3 = new JPanel(new BorderLayout());
        panneau3.setBackground(Color.white);
        panneau3.add(NomEmploye,BorderLayout.EAST);
        panneau3.add(NomEmploye2,BorderLayout.WEST);

        JPanel panneau4 = new JPanel();
        panneau4.setLayout(null);
        panneau4.setBackground(Color.white);








        panneau4.setPreferredSize(new Dimension(200, 400));



        panneau1.add(EmployeListChosen, BorderLayout.EAST);
        panneau1.add(EmployeList, BorderLayout.WEST);
        panneau1.add(panneau3, BorderLayout.CENTER);






        JLabel permissionLabel = new JLabel("DROITS : ");
        permissionLabel = styleJLabelReservation(permissionLabel, 20, 20,200,20);

        JTextField permissionEmploye = new JTextField("Selectionnez Droits :");
        permissionEmploye = styleJTextFieldReservation(permissionEmploye, 50, 100, 200, 20);


        String[] permissions = {"Droit Access aux Fenetres","Droit Equipe Laurent","Droit access aux capteurs"};
        JComboBox myPermissions = new JComboBox(permissions);
        myPermissions.setEditable(true);
        myPermissions.setBounds(250,100, 200, 20);


        JButton showpermission = new JButton("Detail droits selectionné");
        JButton newpermission = new JButton("Nouveau Droits");


        showpermission.setBounds(500, 100, 200, 30);
        newpermission.setBounds(750, 100, 150, 30);

        JTextField validtytime = new JTextField("Durée validité :");
        validtytime = styleJTextFieldReservation(validtytime, 300, 200, 150, 20);

        JTextField datevalidity = new JTextField(" ");
        datevalidity.setBounds(500, 200, 100, 20);



        JButton confirm = new JButton("Confirmer");
        confirm.setEnabled(false);
        JButton cancel = new JButton("Annuler");


        cancel.setBounds(300, 300, 150, 30);
        confirm.setBounds(500, 300, 150, 30);

        panneau4.add(permissionLabel);
        panneau4.add(permissionEmploye);

        panneau4.add(cancel);
        panneau4.add(confirm);

        panneau4.add(myPermissions);
        panneau4.add(showpermission);

        panneau4.add(newpermission);
        panneau4.add(validtytime);

        panneau4.add(datevalidity);




        view.add(panneau1, BorderLayout.NORTH);
        view.add(panneau4, BorderLayout.SOUTH);
        pageBody.add(view);
        pageBody.repaint();
        frame.repaint();

    }


    public JPanel view() {
        JPanel view = new JPanel();
        view.setBackground(Color.white);
        sizeComposant(new Dimension(950, 600), view);
        view.setLayout(null);


        return view;

    }


    public JLabel styleJLabelReservation(JLabel l, int x, int y, int w, int h) {
        sizeComposant(new Dimension(200, 200), l);
        l.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
        l.setBounds(x, y, w, h);
        l.setFont(new Font("Serif", Font.BOLD, 20));
        return l;
    }


    public void sizeComposant(Dimension dim, Component c) {
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

