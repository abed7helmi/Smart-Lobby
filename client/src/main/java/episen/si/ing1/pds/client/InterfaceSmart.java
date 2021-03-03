package episen.si.ing1.pds.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class InterfaceSmart extends JFrame implements ActionListener {

    private final static Logger logger = LoggerFactory.getLogger(AppMain.class.getName());

    Client c;
    DataSource data;

    private JButton boutonsuppr;
    private JButton boutonaffiche ;
    private JButton bouton ;

    private JLabel insertlabel = new JLabel("Votre Saisie :");

    private Saisie insert= new Saisie("Tapez votre phrase");

    // private Saisie résultat = new Saisie("Effectuer votre saisie");
    public InterfaceSmart(Client a,DataSource d) throws Exception{
        this.c=a;
        this.data=d;
        //c.test(data);
        JFrame fenetre = new JFrame();
        fenetre.getContentPane().setLayout(new FlowLayout());

        fenetre.setTitle("IHM");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        fenetre.setSize(screenSize.width, screenSize.height);
        fenetre.setLocationRelativeTo(null);


        boutonsuppr = new JButton("Supprimer Tous le contenu");
        boutonaffiche = new JButton("Voir Tous le contenu");
        bouton = new JButton("ENVOYER");
        bouton.addActionListener(this);
        boutonaffiche.addActionListener(this);
        boutonsuppr.addActionListener(this);
        fenetre.getContentPane().add(boutonsuppr);
        fenetre.getContentPane().add(boutonaffiche);
        fenetre.getContentPane().add(bouton);



        fenetre.getContentPane().add(insertlabel);
        fenetre.getContentPane().add(insert);



        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.setVisible(true);
    }

    private class Saisie extends JTextField {
        public Saisie(String texte) {
            super(texte, 20);
            setFont(new Font("Verdana", Font.BOLD, 12));
            setMargin(new Insets(0, 3, 0, 0));
        }



    }

    public void actionPerformed(ActionEvent event) {
        if (event.getSource()==bouton) {
            JOptionPane d = new JOptionPane();
            String ch = insert.getText();
            try {
                c.Ajout(ch, data);
            } catch (Exception e) {
                logger.info("erreur");
                d.showMessageDialog(this, "Erreur Enregistrement",
                        "SmartLobbyAjout", JOptionPane.ERROR_MESSAGE);
            }
            d.showMessageDialog(this, "Votre phrase est bien enregistré",
                    "SmartLobbyAjout", JOptionPane.INFORMATION_MESSAGE);

        }else if (event.getSource()==boutonaffiche){
            JOptionPane d = new JOptionPane();
            System.out.println("Les resultat dans la base sont");
            try {
                c.GET(data);
            }catch (Exception e){
                logger.info("erreur");
                d.showMessageDialog(this, "Erreur Affichage",
                        "SmartLobbyAjout", JOptionPane.ERROR_MESSAGE);
            }


        }else if (event.getSource()==boutonsuppr){

            JOptionPane d = new JOptionPane();

            try {
                c.Delete(data);
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

