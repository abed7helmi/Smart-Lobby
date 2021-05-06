package episen.si.ing1.pds.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Staff:Manage permission interface
public class MyPermission {
    private JFrame frame;
    private JPanel pageBody;
    private JPanel view;
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

        for(int i = 0; i< value.length; i++){
            listEquipment.add(value[i]);
        }

        equipementArray = new String[listEquipment.size()];
        equipementArray = listEquipment.toArray(equipementArray);

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

                if (result.equals("good")){
                    JOptionPane d = new JOptionPane();
                    d.showMessageDialog(view,
                            "equipement bien ajouté",
                            " Confirmation",
                            JOptionPane.INFORMATION_MESSAGE);
                }else{
                    JOptionPane d = new JOptionPane();
                    d.showMessageDialog(view,
                            "erreur d'ajout",
                            " Attention",
                            JOptionPane.WARNING_MESSAGE);
                }
                pageBody.repaint();

            }
        });

        JTextField PermissionName = new JTextField("Libelle droit:");
        PermissionName = styleJTextFieldBadge(PermissionName, 350, 15, 80, 20);

        JTextField valuePermissionName = new JTextField(" ");
        valuePermissionName.setBounds(440, 15, 250, 20);

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

        Object[][] donnees= new Object[permissionArray.length][7];

        for (int i = 0; i < permissionArray.length; i++) {
            for (int j = 0; j < 7; j++) {
                donnees[i][j] = permissionArray[i].split(",")[j];
            }
        }

        String[] entetes = {"Equipement","Nom" ,"Active", "Salle", "Nb utilisation","Durée","Suppression equipement"};
        JTable tableau = new JTable(donnees, entetes);

        tableau.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer());
        tableau.getColumnModel().getColumn(6).setCellEditor(new ButtonEditorDevice(new JTextField(),"Delete",idpermission,this));

        panneau2.add(tableau.getTableHeader(), BorderLayout.NORTH);
        panneau2.add(tableau, BorderLayout.CENTER);

        pageBody.add(view);
        pageBody.repaint();
        frame.repaint();
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
