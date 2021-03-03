package episen.si.ing1.pds.client;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Statement;

public class Frame extends JFrame implements ActionListener{
    private final static Logger logger = LoggerFactory.getLogger(Frame.class.getName());

    Client client;
    Statement s;

    private JButton buttonDel;
    private JButton buttonDisplay;
    private JButton button ;

    private JLabel insertLabel = new JLabel("Input :");

    private JTextArea result = new JTextArea(5,20);
    private JTextArea input = new JTextArea(5,20);

    public Frame(Client a, DataSource d) throws Exception{
        Connection c = d.send();
        c.setAutoCommit(true);
        s = c.createStatement();

        this.client=a;
        JFrame frame = new JFrame();
        frame.getContentPane().setLayout(new FlowLayout());

        frame.setTitle("IHM");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);

        buttonDel = new JButton("Supprimer Tous le contenu");
        buttonDisplay = new JButton("Voir Tous le contenu");
        button = new JButton("ENVOYER");
        button.addActionListener(this);
        buttonDisplay.addActionListener(this);
        buttonDel.addActionListener(this);
        frame.getContentPane().add(buttonDel);
        frame.getContentPane().add(buttonDisplay);
        frame.getContentPane().add(button);

        frame.getContentPane().add(insertLabel);
        frame.getContentPane().add(input);
        frame.getContentPane().add(result);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent event) {
        if (event.getSource()==button) {
            JOptionPane d = new JOptionPane();
            String ch = input.getText();
            try {
                client.insert(ch, s);
            } catch (Exception e) {
                logger.info("erreur");
                d.showMessageDialog(this, "Erreur Enregistrement",
                        "SmartLobbyAjout", JOptionPane.ERROR_MESSAGE);
            }
            d.showMessageDialog(this, "Votre phrase est bien enregistré",
                    "SmartLobbyAjout", JOptionPane.INFORMATION_MESSAGE);

        }else if (event.getSource() == buttonDisplay){
            JOptionPane d = new JOptionPane();
            System.out.println("Les resultat dans la base sont");
            try {
                result.setText(client.select(s));
            }catch (Exception e){
                logger.info("erreur");
                d.showMessageDialog(this, "Erreur Affichage",
                        "SmartLobbyAjout", JOptionPane.ERROR_MESSAGE);
            }
        }else if (event.getSource()==buttonDel){
            JOptionPane d = new JOptionPane();
            String ch = input.getText();
            try {
                client.delete(ch,s);
            }catch (Exception e){
                logger.info("erreur");
                d.showMessageDialog(this, "Erreur de Delete",
                        "SmartLobbyAjout", JOptionPane.ERROR_MESSAGE);
            }
            d.showMessageDialog(this, "Contenu Supprimé",
                    "SmartLobbyAjout", JOptionPane.WARNING_MESSAGE);
        }
    }
}
