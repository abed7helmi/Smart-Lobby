package episen.si.ing1.pds.client;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AllBadge {
    private JPanel pageBody;
    private JFrame frame;
    private JPanel view;

    public AllBadge(JFrame f){

        this.frame = f;

    }


    public void badgesvue(JPanel pb){

        this.pageBody = pb;
        pageBody.setBackground(Color.WHITE);

        view = view();
        view.setLayout(new BorderLayout());











        JButton confirm = new JButton("Confirmer");

        JButton cancel = new JButton("Annuler");







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



        tableau.setBounds(100,200,200,500);

        JButton Manage = new JButton("Gestion Multiple");


        Manage.setBounds(400, 550, 150, 30);
        view.add(Manage);


        view.add(tableau.getTableHeader(), BorderLayout.NORTH);
        view.add(tableau, BorderLayout.CENTER);



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
        BadgePermissions Badge = new BadgePermissions(frame);
        Badge.choice(pageBody);
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
