package episen.si.ing1.pds.client;


import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class Frame extends JFrame implements ActionListener {
    private JButton envoi;
    private JLabel nom, prenom;
    private JTextField infoRequete;
    private String text ="";
    private ConnectionDB connection = null;
    public Frame(ConnectionDB connect) {
        connection = connect;
        setTitle("Un exemple de connexion de base de données");
        setSize(500,600);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new FlowLayout());

        envoi = new JButton("Envoi");
        envoi.addActionListener(this);

        nom = new JLabel();
        prenom = new JLabel();
        infoRequete = new JTextField(20);

        getContentPane().add(infoRequete);
        getContentPane().add(envoi);




        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // end of program, connection close
    }

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if(source == envoi){
            System.out.println("text saisi :" + infoRequete.getText());
            connection.insert("Insert into entreprise (libelle_entreprise) values ('"+ infoRequete.getText() +"');");
        }
    }
}
