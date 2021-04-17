package episen.si.ing1.pds.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class HomePage extends JFrame {
    private GridBagConstraints gc = new GridBagConstraints();
    private PrintWriter output;
    private BufferedReader input;
    private JFrame frame;

    public HomePage(JFrame f) {
        frame = f;
    }

    public void firstPage(){
        setSize(500, 500);

        gc.fill = GridBagConstraints.BOTH;
        gc.insets = new Insets(70,0,70,5);
        gc.weightx = 3;
        gc.weighty = 1;
        gc.gridx = 0;
        gc.gridy = 0;

        JPanel title = new JPanel(new BorderLayout());
        JLabel l1 = new JLabel("Choix de l'entreprise :");
        l1.setForeground(new Color(255,255,255));
        l1.setFont(new Font(l1.getFont().getName() , l1.getFont().getStyle(), 20));
        l1.setPreferredSize(new Dimension( 100, 100));
        title.setBackground(Color.CYAN);
        title.add(l1);

        JLabel l2 = new JLabel("Deja inscrite : ");
        JTextField companyName = new JTextField(10);
        JButton entry = new JButton("Entrer");

        JPanel registeredCompany = new JPanel();
        registeredCompany.setBackground(Color.WHITE);
        registeredCompany.setLayout(new GridBagLayout());
        gc.insets.left = 30;
        registeredCompany.add(l2, gc);
        gc.gridx = 1;
        gc.insets.left = 0;
        registeredCompany.add(companyName, gc);
        gc.gridx = 2;
        registeredCompany.add(entry, gc);

        JLabel unregistered = new JLabel("Pas inscrit : ");
        JTextField unregisteredCompanyName = new JTextField(10);
        JButton entryUnregistered = new JButton("Envoyer");

        gc.gridx = 0;
        gc.gridy = 1;
        gc.insets.left = 30;
        registeredCompany.add(unregistered, gc);
        gc.gridx = 1;
        gc.gridy = 1;
        gc.insets.left = 0;
        registeredCompany.add(unregisteredCompanyName, gc);
        gc.gridx = 2;
        gc.gridy = 1;
        registeredCompany.add(entryUnregistered, gc);


        entry.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                output.println("select=" + companyName.getText());
                try{
                    boolean test = Boolean.parseBoolean(input.readLine());
                    if(test) {
                        NewPage(title, registeredCompany);
                    } else
                        JOptionPane.showMessageDialog(null, "Le nom renseigne n'existe pas","",
                                JOptionPane.ERROR_MESSAGE);

                }catch (IOException a) {
                    a.printStackTrace();
                }
            }
        });
        entryUnregistered.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(unregisteredCompanyName.getText());
                output.println("insert=" + unregisteredCompanyName.getText());
                try{
                    System.out.println(input.readLine());
                    JOptionPane.showMessageDialog(null, "Bienvenue !!","",
                            JOptionPane.INFORMATION_MESSAGE);
                    NewPage(title, registeredCompany);
                }catch (IOException a) {
                    a.printStackTrace();
                }
            }
        });

        getContentPane().add(title, BorderLayout.NORTH);
        getContentPane().add(registeredCompany, BorderLayout.CENTER);

        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void NewPage(JPanel t, JPanel registeredCompany){
        frame.remove(t);
        frame.remove(registeredCompany);
        frame.repaint();
        frame.setSize(1200,800);
        frame.setLocationRelativeTo(null);
    }
}
