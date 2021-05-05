package episen.si.ing1.pds.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AcceuilPersonnel {

    private JFrame frame;
    private JPanel pageBody = new JPanel();
    public static String id_company;


    public AcceuilPersonnel(JFrame f,String i)  {

        this.id_company=i;
        frame = f;
    }



    public JPanel acceuil(){
        frame.setSize(1200, 800);
        pageBody.setLayout(new BorderLayout());
        //Menu menu = new Menu("Smart Lobby", id);
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

                Client.map.get("getpermissions").put("company_id", id_company);
                String request = "getpermissions";

                String result = Client.sendBd("getpermissions");
                String company = result.split(",")[0];
                //System.out.println(result);
               // System.out.println(company);
                choice.setVisible(false);

                pageBody.repaint();
                changePageNewBadge(id_company,result);
            }
        });


        TestPermission.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Client.map.get("getdevices").put("company_id", id_company);


                String result = Client.sendBd("getdevices");
                //String company = result.split(",")[0];
                System.out.println(result);
                //System.out.println(company);

                choice.setVisible(false);

                pageBody.repaint();
                changePageTest(id_company,result);
            }
        });

        AllBadges.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String request = "getbadges";

                Client.map.get(request).put("company_id", id_company);

                String result = Client.sendBd("getbadges");

                choice.setVisible(false);

                pageBody.repaint();
                changePageAll(id_company,result);
            }
        });



        pageBody.add(choice, BorderLayout.SOUTH);
        pageBody.setBackground(Color.WHITE);
        //frame.getContentPane().add(menu.menu("Smart Lobby", id), BorderLayout.WEST);
        frame.getContentPane().add(pageBody, BorderLayout.CENTER);

        frame.repaint();

        return pageBody;
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

    public void changePageNewBadge(String id,String permissions){
        NewBadge Badge = new NewBadge(frame,id,permissions);
        Badge.choice(pageBody);
    }

    public void changePageAll(String id,String allemployes){
        AllBadge AllBadges = new AllBadge(frame,id,allemployes);
        AllBadges.badgesvue(pageBody);
    }

    public void changePageTest(String i,String r){
        TestBadge testBadges = new TestBadge(frame,i,r);
        testBadges.testbadge(pageBody);
    }


    public void sizeComposant(Dimension dim, Component c){
        c.setPreferredSize(dim);
        c.setMaximumSize(dim);
        c.setMinimumSize(dim);
    }
}
