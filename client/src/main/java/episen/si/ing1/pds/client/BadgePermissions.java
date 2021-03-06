package episen.si.ing1.pds.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

//Staff: Add permission to many employees interface
public class BadgePermissions {

    private Map<String, String> input = new HashMap<>();
    private JFrame frame;
    private JPanel pageBody = new JPanel();
    private JPanel view;
    private String result;
    private JList<String> listchosen;
    private JList<String> list;
    private List<String> listEmployees = new ArrayList<>();
    private String[] employeesArray;
    private List<String> listPermissions = new ArrayList<>();
    private String[] permissionsArray;
    private String allpermissions;

    DefaultListModel<String> listModelchosen = new DefaultListModel<>();

    public BadgePermissions(JFrame f,String result,String r) {
        input.clear();
        this.result=result;
        frame = f;
        this.allpermissions=r;
        listModelchosen.clear();
    }

    public void choice(JPanel pb) {

        this.pageBody = pb;
        pageBody.setBackground(Color.WHITE);
        view = view();
        view.setLayout(new BorderLayout());

        DefaultListModel<String> listModel = new DefaultListModel<>();

        String[] value = result.split("#");
        for(int i = 0; i< value.length; i++){
            listEmployees.add(value[i]);
        }

        employeesArray = new String[listEmployees.size()];
        employeesArray = listEmployees.toArray(employeesArray);

        for (int i = 0; i < employeesArray.length; i++) {
            listModel.addElement(employeesArray[i]);
        }

        list = new JList<>(listModel);
        listchosen=new JList<>(listModelchosen);

        view.setBackground(Color.white);
        JScrollPane EmployeList = new JScrollPane(list);
        JScrollPane EmployeListChosen = new JScrollPane(listchosen);
        JTextField NomEmploye = new JTextField("Les Employes choisits:");
        JTextField NomEmploye2 = new JTextField("Choisir un Employe");

        JPanel panneau1 = new JPanel(new BorderLayout());
        panneau1.setBackground(Color.yellow);
        panneau1.setPreferredSize(new Dimension(200, 200));

        JPanel panneau3 = new JPanel(new BorderLayout());
        panneau3.setBackground(Color.white);
        panneau3.add(NomEmploye,BorderLayout.EAST);
        panneau3.add(NomEmploye2,BorderLayout.WEST);

        JPanel panneau4 = new JPanel();
        panneau4.setLayout(null);
        panneau4.setBackground(Color.white);

        panneau4.setPreferredSize(new Dimension(200, 400));

        panneau1.add(EmployeListChosen, BorderLayout.EAST);
        panneau1.add(EmployeList, BorderLayout.WEST);
        panneau1.add(panneau3, BorderLayout.CENTER);

        JLabel permissionLabel = new JLabel("DROITS : ");
        permissionLabel = styleJLabelBadge(permissionLabel, 20, 20,200,20);

        JTextField permissionEmploye = new JTextField("Selectionnez Droits :");
        permissionEmploye = styleJTextFieldBadge(permissionEmploye, 30, 100, 120, 20);

        String[] permission = allpermissions.split("#");
        for(int i = 0; i< permission.length; i++){
            listPermissions.add(permission[i]);
        }

        permissionsArray = new String[listPermissions.size()];
        permissionsArray = listPermissions.toArray(permissionsArray);


        JComboBox myPermissions = new JComboBox(permissionsArray);
        myPermissions.setEditable(true);
        myPermissions.setBounds(150,100, 350, 20);

        myPermissions.setEditable(false);
        myPermissions.setForeground(Color.BLUE);
        myPermissions.setFont(new Font("Arial", Font.BOLD, 9));
        myPermissions.setMaximumRowCount(7);

        JButton showpermission = new JButton("Detail droits selectionne");
        JButton newpermission = new JButton("Nouveau Droits");
        showpermission.setVisible(false);

        myPermissions.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {
                showpermission.setVisible(true);
                JComboBox<String> combo = (JComboBox<String>) event.getSource();
                String selectedPermission = (String) combo.getSelectedItem();
                input.put("permission", selectedPermission);

            }
        });

        showpermission.setBounds(540, 100, 200, 30);
        newpermission.setBounds(760, 100, 150, 30);

        JTextField validtytime = new JTextField("Duree validite :");
        validtytime = styleJTextFieldBadge(validtytime, 300, 200, 150, 20);

        JTextField datevalidity = new JTextField(" ");
        datevalidity.setBounds(500, 200, 100, 20);

        JButton confirm = new JButton("Confirmer");
        confirm.setEnabled(false);
        JButton cancel = new JButton("Annuler");

        cancel.setBounds(300, 300, 150, 30);
        confirm.setBounds(500, 300, 150, 30);

        panneau4.add(permissionLabel);
        panneau4.add(permissionEmploye);
        panneau4.add(cancel);
        panneau4.add(confirm);
        panneau4.add(myPermissions);
        panneau4.add(showpermission);
        panneau4.add(newpermission);
        panneau4.add(validtytime);
        panneau4.add(datevalidity);

        showpermission.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String request = "requestDetailBadge";
                Client.map.get(request).put("permission", input.get("permission"));
                Client.map.get(request).put("company_id",AcceuilPersonnel.id_company);
                String result = Client.sendBd(request);
                pageBody.repaint();
                changePageDetail(AcceuilPersonnel.id_company,result,input.get("permission"));
            }
        });

        newpermission.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Client.map.get("getdevices").put("company_id", AcceuilPersonnel.id_company);
                String devices = Client.sendBd("getdevices");
                pageBody.repaint();
                changePageNewPermission(devices);
            }
        });

        view.add(panneau1, BorderLayout.NORTH);
        view.add(panneau4, BorderLayout.SOUTH);
        pageBody.add(view);
        pageBody.repaint();
        frame.repaint();

        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e)
            {

                if(!e.getValueIsAdjusting()) {

                    listModelchosen.addElement(list.getSelectedValue());

                    if(verifMap()) confirm.setEnabled(true);
                    pageBody.repaint();
                    frame.repaint();
                }
            }
        });

        listchosen.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e)
            {

                if(!e.getValueIsAdjusting()) {
                    listModelchosen.removeElement(listchosen.getSelectedValue());
                    if(verifMap()) confirm.setEnabled(true);
                    pageBody.repaint();
                    frame.repaint();
                }
            }
        });


        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        JTextField messageErrorEndDate = styleJTextFieldError(view ,500, 180, 170, 20);

        datevalidity.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {}
            @Override
            public void focusLost(FocusEvent e) {
                Object source = e.getSource();
                String m = (((JTextField)source).getText()).trim();
                if(m.matches("[0-9]{4}-[0-1]{1}[0-9]{1}-[0-1]{1}[0-9]{1}")) {
                    try {
                        Date today = dateFormat.parse(dateFormat.format(new Date()));
                        Date Mydate = dateFormat.parse(m);
                        if(today.equals(Mydate) || today.before(Mydate)){
                            if(input.containsKey("per_date"))input.replace("per_date", ((JTextField)source).getText().trim());
                            else input.put("per_date", ((JTextField)source).getText().trim());
                            messageErrorEndDate.setText(" ");
                            if(verifMap()) confirm.setEnabled(true);
                        } else {
                            messageErrorEndDate.setText("Veuillez rentrer une date valide");
                            messageErrorEndDate.setForeground(Color.red);
                        }
                    } catch(Exception a) {a.printStackTrace();}
                }else {
                    messageErrorEndDate.setText("Veuillez respecter le format");
                    messageErrorEndDate.setForeground(Color.red);
                }
            }
        });

        StringBuilder sb = new StringBuilder();
        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String request = "requestManyNewBadge";
                for (int i = 0; i < listModelchosen.size(); i++) {

                    sb.append(listModelchosen.get(i) + "&");
                }

                Client.map.get(request).put("company_id", AcceuilPersonnel.id_company);
                Client.map.get(request).put("permission", input.get("permission"));
                Client.map.get(request).put("date", input.get("per_date"));
                Client.map.get(request).put("employees",sb.toString());

                String result = Client.sendBd(request);

                if (result.equals("good")){
                    JOptionPane d = new JOptionPane();
                    d.showMessageDialog(view,
                            "Employés bien enregistrés",
                            " Confirmation",
                            JOptionPane.INFORMATION_MESSAGE);
                }else{
                    JOptionPane d = new JOptionPane();
                    d.showMessageDialog(view,
                            "Modification pas réussi",
                            " Attention",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });

    }


    public JPanel view() {
        JPanel view = new JPanel();
        view.setBackground(Color.white);
        sizeComposant(new Dimension(950, 600), view);
        view.setLayout(null);
        return view;
    }


    public void changePageNewPermission(String devices){

        view.setVisible(false);
        Newpermission permission = new Newpermission(frame,devices);
        permission.choice(pageBody);

    }


    public boolean verifMap(){

        if((input.containsKey("per_date") && input.containsKey("permission") && !(listModelchosen.isEmpty())))
            return true;
        else return false;

    }


    public void changePageDetail(String id,String result,String per){

        view.setVisible(false);
        MyPermission permission = new MyPermission(frame,id,result,per);
        permission.choicepermission(pageBody);
    }



    public JLabel styleJLabelBadge(JLabel l, int x, int y, int w, int h) {
        sizeComposant(new Dimension(200, 200), l);
        l.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
        l.setBounds(x, y, w, h);
        l.setFont(new Font("Serif", Font.BOLD, 20));
        return l;
    }


    public void sizeComposant(Dimension dim, Component c) {
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
}

