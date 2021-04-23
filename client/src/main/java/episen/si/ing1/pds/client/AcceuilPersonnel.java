package episen.si.ing1.pds.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AcceuilPersonnel {

    private JFrame frame;
    private JPanel pageBody = new JPanel();


    public AcceuilPersonnel(JFrame f)  {

        frame = f;
    }



    public void acceuil(){
        frame.setSize(1200, 800);
        pageBody.setLayout(new BorderLayout());
        Menu menu = new Menu();
        TitleBadge title = new TitleBadge();

        pageBody.add(title.TitleBadge(), BorderLayout.NORTH);

        JPanel choice = ChoixPersonnel();


        JButton NewBadge = new JButton("Nouveau Badge");
        JButton AllBadges = new JButton("Gestion des Badges");
        JButton TestPermission = new JButton("Tester Droits");

        NewBadge.setBounds(100, 100, 300, 150);
        AllBadges.setBounds(500, 100, 300, 150);
        TestPermission.setBounds(300, 300, 300, 150);
        choice.add(NewBadge);
        choice.add(AllBadges);
        choice.add(TestPermission);

        NewBadge.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                choice.setVisible(false);

                pageBody.repaint();
                changePage();
            }
        });


        TestPermission.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                choice.setVisible(false);

                pageBody.repaint();
                changePageTest();
            }
        });

        AllBadges.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                choice.setVisible(false);

                pageBody.repaint();
                changePageAll();
            }
        });



        pageBody.add(choice, BorderLayout.SOUTH);
        pageBody.setBackground(Color.WHITE);
        frame.getContentPane().add(menu.menu(), BorderLayout.WEST);
        frame.getContentPane().add(pageBody, BorderLayout.CENTER);

        frame.repaint();
    }


    public JPanel ChoixPersonnel(){

        JPanel choice = new JPanel();
        choice.setLayout(null);
        Dimension dimChoice = new Dimension(950, 600);
        sizeComposant(dimChoice, choice);
        choice.setBackground(Color.WHITE);
        choice.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));



        return choice;
    }

    public void changePage(){
        NewBadge Badge = new NewBadge(frame);
        Badge.choice(pageBody);
    }

    public void changePageAll(){
        AllBadge AllBadges = new AllBadge(frame);
        AllBadges.badgesvue(pageBody);
    }

    public void changePageTest(){
        TestBadge testBadges = new TestBadge(frame);
        testBadges.testbadge(pageBody);
    }


    public void sizeComposant(Dimension dim, Component c){
        c.setPreferredSize(dim);
        c.setMaximumSize(dim);
        c.setMinimumSize(dim);
    }
}
