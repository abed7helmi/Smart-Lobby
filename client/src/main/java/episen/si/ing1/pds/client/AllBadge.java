package episen.si.ing1.pds.client;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class AllBadge {
    private JPanel pageBody;
    private JFrame frame;
    private JPanel view;
    private String idcompany;


    public AllBadge(JFrame f,String id){
        this.idcompany=id;
        this.frame = f;

    }


    public void badgesvue(JPanel pb){





        this.pageBody = pb;
        pageBody.setBackground(Color.WHITE);
        view = view();

        view.setLayout(new BorderLayout());


        view.setBackground(Color.white);


        JPanel panneau1 = new JPanel();
        panneau1.setLayout(null);
        panneau1.setBackground(Color.white);
        panneau1.setPreferredSize(new Dimension(200, 100));

        view.add(panneau1, BorderLayout.NORTH);


        JPanel panneau2 = new JPanel();
        panneau2.setLayout(new BorderLayout());
        panneau2.setBackground(Color.blue);
        panneau2.setPreferredSize(new Dimension(200, 200));

        view.add(panneau2, BorderLayout.CENTER);

        JPanel panneau3 = new JPanel();
        panneau3.setLayout(null);
        panneau3.setBackground(Color.white);
        panneau3.setPreferredSize(new Dimension(200, 150));

        view.add(panneau3, BorderLayout.SOUTH);




        JLabel infoLabel = new JLabel("Gestion Droits: ");
        infoLabel = styleJLabelBadge(infoLabel, 20, 20,250,20);
        panneau1.add(infoLabel);





        /*JButton confirm = new JButton("Confirmer");

        JButton cancel = new JButton("Annuler");*/







        Object[][] data=
                {
                        {"id1","mohammed","gestion fenetres","21","id1","id1"},
                        {"id2","salah","gestion fenetres","3","id2","id2"},
                        {"id3","hamza","gestion fenetres","7","id3","id3"},
                        {"id4","cedric","gestion fenetres","10","id4","id4"},

                };


        String columnHeaders[]={"Id employé","Nom","libellé droit","date validité","Detail","Suppression"};

        JTable tableau = new JTable(data, columnHeaders);

        tableau.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer());

        tableau.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor(new JTextField()));

        tableau.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());

        tableau.getColumnModel().getColumn(5).setCellEditor(new ButtonEditor(new JTextField()));





        JButton Manage = new JButton("Gestion Multiple");


       Manage.setBounds(400, 60, 150, 30);;
        panneau3.add(Manage);


        panneau2.add(tableau.getTableHeader(), BorderLayout.NORTH);
        panneau2.add(tableau, BorderLayout.CENTER);



        Manage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                view.setVisible(false);

                pageBody.repaint();
                changePageManage();
            }
        });









        pageBody.add(view);
        pageBody.repaint();
        frame.repaint();






    }

    public void changePageManage(){

        String request = "requestallbadges";

        Client.map.get(request).put("company_id",idcompany);

        String result = Client.sendBd(request);

        System.out.println(result);


        String request2 = "getpermissions";
        Client.map.get(request2).put("company_id",idcompany);

        String result2 = Client.sendBd(request2);
        System.out.println("waaw");
        System.out.println(result2);
        pageBody.repaint();


        BadgePermissions Badge = new BadgePermissions(frame,result,result2);
        Badge.choice(pageBody);
    }











    public JLabel styleJLabelBadge(JLabel l, int x, int y, int w, int h){
        sizeComposant(new Dimension(200, 200) ,l);
        l.setBorder(BorderFactory.createMatteBorder(0,0, 1, 0, Color.black));
        l.setBounds(x,y,w,h);
        l.setFont(new Font("Serif", Font.BOLD, 20));
        return l;
    }




    public JPanel view(){
        JPanel view = new JPanel();
        view.setBackground(Color.white);
        sizeComposant(new Dimension(950,600), view);
        view.setLayout(null);



        return view;

    }

    public void sizeComposant(Dimension dim, Component c){
        c.setPreferredSize(dim);
        c.setMaximumSize(dim);
        c.setMinimumSize(dim);
    }
}
