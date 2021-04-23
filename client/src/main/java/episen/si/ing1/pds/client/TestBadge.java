package episen.si.ing1.pds.client;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TestBadge {

    private JFrame frame;
    private JPanel pageBody = new JPanel();

    public TestBadge(JFrame f)  {

        frame = f;
    }

    public void testbadge(JPanel pb){

        this.pageBody = pb;
        pageBody.setBackground(Color.WHITE);

        JPanel view = view();

        JLabel infoLabel = new JLabel("Tester les droits d'un employé à un equipement : ");
        infoLabel = styleJLabelReservation(infoLabel, 20, 160,600,20);

        JTextField NomEmploye = new JTextField("Nom :");
        NomEmploye = styleJTextFieldReservation(NomEmploye, 100, 220, 50, 20);

        JTextField valuenom = new JTextField(" ");
        valuenom.setBounds(170, 220, 100, 20);

        JTextField PrenomEmploye = new JTextField("Prenom :");
        PrenomEmploye = styleJTextFieldReservation(PrenomEmploye, 300, 220, 60, 20);

        JTextField valueprenom = new JTextField(" ");
        valueprenom.setBounds(380, 220, 100, 20);

        JTextField device = new JTextField("Equipement :");
        device = styleJTextFieldReservation( device, 550, 220, 80, 20);





        String[] devices = {"Fenetre X45","PC 48","Capteur 45"};
        JComboBox mydevice = new JComboBox(devices);
        mydevice.setEditable(true);
        mydevice.setBounds(650,220, 180, 20);


        JButton testbutton = new JButton("Tester");
        testbutton.setBounds(400, 280, 150, 30);




        JTextField result = new JTextField("Resultat :");
        result = styleJTextFieldReservation(result, 400, 350, 60, 20);


        view.add(infoLabel);
        view.add(NomEmploye);
        view.add(PrenomEmploye);
        view.add(device);
        view.add(valuenom);
        view.add(valueprenom);
        view.add(mydevice);
        view.add(testbutton);
        view.add(result);

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
