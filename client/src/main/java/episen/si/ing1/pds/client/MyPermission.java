package episen.si.ing1.pds.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyPermission {
    private JFrame frame;
    private JPanel pageBody;
    private JPanel view;
    private JButton confirm;

    private String idcompany;
    private String result;
    private List<String> listEquipment = new ArrayList<>();
    private List<String> listPermission = new ArrayList<>();
    private String[] equipementArray;
    private String[] permissionArray;
    private String namepermission;
    private String mypermission;

    private Map<String, String> input = new HashMap<>();


    public MyPermission(JFrame f,String id,String result,String per){
        input.clear();
        this.frame = f;
        this.idcompany=id;
        this.result=result;
        this.mypermission=per;

    }


    public void choicepermission(JPanel pb){



        this.pageBody = pb;
        pageBody.setBackground(Color.WHITE);
        view = view();

        view.setLayout(new BorderLayout());


        view.setBackground(Color.white);


        JPanel panneau1 = new JPanel();
        panneau1.setLayout(null);
        panneau1.setBackground(Color.white);
        panneau1.setPreferredSize(new Dimension(200, 150));

        view.add(panneau1, BorderLayout.NORTH);


        JPanel panneau2 = new JPanel();
        panneau2.setLayout(new BorderLayout());
        panneau2.setBackground(Color.blue);
        panneau2.setPreferredSize(new Dimension(200, 150));

        view.add(panneau2, BorderLayout.CENTER);

        JPanel panneau3 = new JPanel();
        panneau3.setLayout(null);
        panneau3.setBackground(Color.white);
        panneau3.setPreferredSize(new Dimension(200, 150));

        view.add(panneau3, BorderLayout.SOUTH);



        JLabel infoLabel = new JLabel("Creer Droits : ");
        infoLabel = styleJLabelBadge(infoLabel, 20, 20,250,20);
        panneau1.add(infoLabel);

        JTextField device = new JTextField("Ajouter Equipement :");
        device = styleJTextFieldBadge( device, 50, 50, 140, 20);
        panneau1.add(device);



        String devices=result.split("//")[3];

        String[] value = devices.split("#");
        //System.out.println("sa7ayt;");
        //System.out.println(value[0]);

        for(int i = 0; i< value.length; i++){
            listEquipment.add(value[i]);
        }



        equipementArray = new String[listEquipment.size()];
        equipementArray = listEquipment.toArray(equipementArray);


       // String[] devices = {"Fenetre X45","PC 48","Capteur 45"};
        JComboBox mydevice = new JComboBox(equipementArray);
        mydevice.setEditable(false);
        mydevice.setForeground(Color.BLUE);
        mydevice.setFont(new Font("Arial", Font.BOLD, 9));
        mydevice.setMaximumRowCount(5);
        mydevice.setBounds(200,50, 300, 20);
        panneau1.add(mydevice);


        input.put("idcompany", idcompany);

        String idpermission=result.split("//")[0];
        input.put("idpermission", idpermission);

        namepermission=result.split("//")[1];
        input.put("permission", namepermission);

        mydevice.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {

                String request = "requestadddevice";
                mydevice.setVisible(true);
                JComboBox<String> combo = (JComboBox<String>) event.getSource();
                String selecteddevice = (String) combo.getSelectedItem();
                input.put("device", selecteddevice);

                Client.map.get(request).put("device", input.get("device"));
                Client.map.get(request).put("idcompany", input.get("idcompany"));
                Client.map.get(request).put("permission", input.get("permission"));
                Client.map.get(request).put("idpermission", input.get("idpermission"));


                String result = Client.sendBd(request);
                System.out.println("waaaw"+result);


                String request2 = "requestDetailBadge";

                Client.map.get(request2).put("permission", input.get("permission"));

                Client.map.get(request2).put("company_id",idcompany);





                String result2 = Client.sendBd(request2);

                pageBody.repaint();

                reloadpage(idcompany,result2,mypermission);

            }
        });




        JTextField PermissionName = new JTextField("Libelle droit:");
        PermissionName = styleJTextFieldBadge(PermissionName, 350, 15, 80, 20);

        JTextField valuePermissionName = new JTextField(" ");
        valuePermissionName.setBounds(440, 15, 250, 20);


        confirm = new JButton("Confirmer");
        confirm.setEnabled(true);
        JButton cancel = new JButton("Annuler");


        cancel.setBounds(300, 60, 150, 30);
        confirm.setBounds(500, 60, 150, 30);



        panneau3.add(cancel);
        panneau3.add(confirm);
        panneau3.add(PermissionName);
        panneau3.add(valuePermissionName );





        namepermission=result.split("//")[1];


        valuePermissionName.setText(namepermission);


        String permissions=result.split("//")[2];
        String[] permissionvalue = permissions.split("#");

        for(int i = 0; i< permissionvalue.length; i++){
            listPermission.add(permissionvalue[i]);
        }

        permissionArray = new String[listPermission.size()];
        permissionArray = listPermission.toArray(permissionArray);

        for (int i = 0; i < permissionArray.length; i++) {
                System.out.println(permissionArray[i]);
        }

        //Object[][] donnees =permissionArray;

        /*Object[][] donnees = {
                {"952596", "Sykes", "Acces fenetres",true,"Sykes", "952596"},
                {"9465", "Van de Kampf","Acces fenetres",true,"Sykes", "9465"},
                {"4556", "Cuthbert", "Acces fenetres", true,"Sykes", "4556"},
                {"45546", "Valance", "Acces fenetres", false,"Sykes", "45546"},
                {"8456", "Schrödinger", "Acces fenetres", false,"Sykes", "8456"},
                {"4556", "Duke", "Acces capteurs", false,"Sykes", "4556"},
                {"788", "Trump", "Acces PC", true,"Sykes", "788"},
        };*/

       Object[][] donnees= new Object[permissionArray.length][7];

        for (int i = 0; i < permissionArray.length; i++) {
            for (int j = 0; j < 7; j++) {
               // System.out.println(String.format("Entrez a[%d][%d] : ", i, j));
                //System.out.println(permissionArray[i].split(",")[j]);
                donnees[i][j] = permissionArray[i].split(",")[j];
            }
        }

        String[] entetes = {"Equipement","Nom" ,"Active", "Salle", "Nb utilisation","Durée","Suppression equipement"};

        JTable tableau = new JTable(donnees, entetes);




        tableau.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer());

        tableau.getColumnModel().getColumn(6).setCellEditor(new ButtonEditor(new JTextField()));






        panneau2.add(tableau.getTableHeader(), BorderLayout.NORTH);
        panneau2.add(tableau, BorderLayout.CENTER);

       JTextField messageErrorLib = styleJTextFieldError(panneau3,570, 15, 30, 20);

        /*valuePermissionName.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {}
            @Override
            public void focusLost(FocusEvent e) {
                Object source = e.getSource();
                String m = (((JTextField)source).getText()).trim();
                if(m.matches("[a-zA-Z_0-9]+")) {
                    // System.out.println("waaw");
                    input.put("permissionlib", ((JTextField)source).getText().trim());
                    messageErrorLib.setText(" ");
                    if(verifMap()) confirm.setEnabled(true);
                }else {
                    //System.out.println("wiiw");
                    messageErrorLib.setText("X");
                    messageErrorLib.setForeground(Color.red);
                }
            }
        });*/


        //panneau2.add(viewtable);




        /*view.add(tableau);




        view.add(infoLabel);

        view.add(device);

        view.add(mydevice);
        view.add(PermissionName);
        view.add(valuePermissionName);
        view.add(confirm);
        view.add(cancel);*/



        pageBody.add(view);
        pageBody.repaint();
        frame.repaint();
    }

    public boolean verifMap(){
        if((input.containsKey("permissionlib")))
            return true;
        else {
            confirm.setEnabled(true);
            return false;
        }
    }

    public void reloadpage(String id,String result,String per){

        view.setVisible(false);
        MyPermission permission = new MyPermission(frame,id,result,per);
        permission.choicepermission(pageBody);
    }


    public JPanel view(){
        JPanel view = new JPanel();
        view.setBackground(Color.white);
        sizeComposant(new Dimension(950,600), view);
        view.setLayout(null);



        return view;

    }

    public JTextField styleJTextFieldError(JPanel choice, int x, int y, int w, int h) {
        JTextField t = new JTextField();
        t.setEditable(false);
        t.setBackground(Color.WHITE);
        t.setForeground(Color.RED);
        t.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        t.setBounds(x, y, w, h);
        choice.add(t);
        return t;
    }


    public JLabel styleJLabelBadge(JLabel l, int x, int y, int w, int h){
        sizeComposant(new Dimension(200, 200) ,l);
        l.setBorder(BorderFactory.createMatteBorder(0,0, 1, 0, Color.black));
        l.setBounds(x,y,w,h);
        l.setFont(new Font("Serif", Font.BOLD, 20));
        return l;
    }


    public void sizeComposant(Dimension dim, Component c){
        c.setPreferredSize(dim);
        c.setMaximumSize(dim);
        c.setMinimumSize(dim);
    }

    public JTextField styleJTextFieldBadge(JTextField t, int x, int y, int w, int h) {
        t.setEditable(false);
        t.setBackground(Color.WHITE);
        t.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        t.setBounds(x, y, w, h);
        return t;
    }

}
