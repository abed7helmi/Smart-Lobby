package episen.si.ing1.pds.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class HomePage extends JFrame {
    private JFrame frame;

    public HomePage() {
        setSize(500, 500);
        frame = this;
        JPanel title = new JPanel(new BorderLayout());
        JLabel l1 = new JLabel("Choix de l'entreprise :");
        l1.setForeground(new Color(255,255,255));
        l1.setFont(new Font(l1.getFont().getName() , l1.getFont().getStyle(), 20));
        l1.setPreferredSize(new Dimension( 100, 100));
        title.setBackground(Color.CYAN);
        title.add(l1);

        JLabel l2 = new JLabel("Deja inscrite : ");
        JTextField companyName = new JTextField(10);
        companyName.setBounds(150, 150,  200, 50);
        l2.setBounds(50, 165, 100, 20);
        JButton entry = new JButton("Entrer");
        entry.setBounds(380, 150, 100, 50);

        JPanel registeredCompany = new JPanel();
        registeredCompany.setLayout(null);
        registeredCompany.setBackground(Color.WHITE);

        registeredCompany.add(l2);
        registeredCompany.add(companyName);
        registeredCompany.add(entry);

        entry.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String m = (companyName.getText()).trim();
                Client.map.get("homePage1").put("company_name", m);
                Boolean result = Boolean.parseBoolean(Client.sendBd("homePage1"));
                System.out.println(result+"result");
                System.out.println(Client.map);
                if (result) {
                    Menu Menu = new Menu("Smart Lobby");
                    frame.dispose();
                } else
                    JOptionPane.showMessageDialog(null, "Le nom renseigne n'existe pas", "",
                                JOptionPane.ERROR_MESSAGE);
            }
        });
        getContentPane().add(title, BorderLayout.NORTH);
        getContentPane().add(registeredCompany, BorderLayout.CENTER);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}