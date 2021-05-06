package episen.si.ing1.pds.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

//Staff: Edit all employees permissions interface
public class AllBadge {
    private JPanel pageBody;
    private JFrame frame;
    private JPanel view;
    private String idcompany;
    private String allemployees;
    private String[] employeesArray;
    private List<String> listEmployee = new ArrayList<>();

    public AllBadge(JFrame f,String id,String allemployees){
        this.idcompany=id;
        this.frame = f;
        this.allemployees=allemployees;

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

        String[] employeevalue = allemployees.split("#");

        for(int i = 0; i< employeevalue.length; i++){
            listEmployee.add(employeevalue[i]);
        }

        employeesArray = new String[listEmployee.size()];
        employeesArray = listEmployee.toArray(employeesArray);

        Object[][] donnees= new Object[employeesArray.length][6];

        for (int i = 0; i < employeesArray.length; i++) {
            for (int j = 0; j < 6; j++) {

                donnees[i][j] = employeesArray[i].split(",")[j];
            }
        }

        String columnHeaders[]={"Id employé","Nom","libellé droit","date validité","Detail","Suppression"};
        JTable tableau = new JTable(donnees, columnHeaders);
        JScrollPane tableScrollPane = new JScrollPane(tableau);
        panneau2.add(tableScrollPane);
        tableau.setGridColor(Color.BLUE);
        tableScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        tableScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        tableau.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer());
        tableau.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor(new JTextField(),"Detail",this));
        tableau.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());
        tableau.getColumnModel().getColumn(5).setCellEditor(new ButtonEditor(new JTextField(),"Delete",this));

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


    public void changePageEdit(String id){

        view.setVisible(false);
        pageBody.repaint();
        Client.map.get("getpermissions").put("company_id", AcceuilPersonnel.id_company);
        String result = Client.sendBd("getpermissions");
        String request2 = "getdetailemployee";

        Client.map.get(request2).put("idemploye", id);
        Client.map.get(request2).put("company_id", AcceuilPersonnel.id_company);
        String result2 = Client.sendBd(request2);
        EditBadge Badge = new EditBadge(frame,AcceuilPersonnel.id_company,result,result2);
        Badge.choice(pageBody);
    }

    public void changePageManage(){

        String request = "requestallbadges";
        Client.map.get(request).put("company_id",idcompany);
        String result = Client.sendBd(request);
        String request2 = "getpermissions";
        Client.map.get(request2).put("company_id",idcompany);
        String result2 = Client.sendBd(request2);
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
