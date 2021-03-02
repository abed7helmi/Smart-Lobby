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

    private JButton bouton = new JButton("Valider");
    private JLabel insertlabel = new JLabel("Votre Saisie :");
   // private JLabel intituléPrénom = new JLabel("Prénom :");
   // private Saisie prénom = new Saisie("Votre prénom");
    private Saisie insert= new Saisie("Tapez votre phrase");

    // private Saisie résultat = new Saisie("Effectuer votre saisie");
    public InterfaceSmart(Client a,DataSource d) throws Exception{
        this.c=a;
        this.data=d;
        c.test(data);
        JFrame fenetre = new JFrame();
        fenetre.getContentPane().setLayout(new FlowLayout());
        fenetre.setTitle("IHM");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        fenetre.setSize(screenSize.width, screenSize.height); fenetre.setLocationRelativeTo(null);
        bouton = new JButton("ENVOYER"); bouton.addActionListener(this);
        fenetre.getContentPane().add(bouton);
        //fenetre.getContentPane().add(intituléPrénom);
        //fenetre.getContentPane().add(prénom);
        fenetre.getContentPane().add(insertlabel);
        fenetre.getContentPane().add(insert);

      //  fenetre.getContentPane().add(résultat);

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
       // résultat.setText(prénom.getText()+' '+nom.getText());
        JOptionPane d = new JOptionPane();
        d.showMessageDialog( this, "Votre phrase est bien enregistré",
                "SmartLobbyAjout", JOptionPane.INFORMATION_MESSAGE);
        logger.info("waaw");
        String ch=insert.getText();
        try{c.Ajout(ch,data);
        }catch(Exception e){
            logger.info("erreur");
        }




    }
}

