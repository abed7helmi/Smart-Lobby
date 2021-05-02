package episen.si.ing1.pds.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class NewBadge {
    private Map<String, String> input = new HashMap<>();
    private JFrame frame;
    private JPanel pageBody;
    private JPanel view;
    private List<String> listPermissions = new ArrayList<>();
    private String[] permissionsArray;
    private String allpermissions;
    private String idcompany;


    public NewBadge(JFrame f,String i,String r){
        input.clear();

        this.frame = f;
        this.idcompany=i;
        this.allpermissions=r;

    }

    public void choice(JPanel pb){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        this.pageBody = pb;
        pageBody.setBackground(Color.WHITE);
        view = view();
        JTextField messageErrorStartDate = styleJTextFieldError(view ,740, 60, 170, 20);
        JTextField messageErrorEndDate = styleJTextFieldError(view ,450, 330, 170, 20);
        JTextField messageErrorPerDate = styleJTextFieldError(view ,450, 250, 170, 20);
        JLabel infoLabel = new JLabel("INFOS EMPLOYE : ");
        infoLabel = styleJLabelBadge(infoLabel, 20, 20,200,20);

        JTextField NomEmploye = new JTextField("Nom :");
        NomEmploye = styleJTextFieldBadge(NomEmploye, 20, 80, 50, 20);

        JTextField PrenomEmploye = new JTextField("Prenom :");
        PrenomEmploye = styleJTextFieldBadge(PrenomEmploye, 300, 80, 60, 20);

        JTextField DateContract = new JTextField("Date fin contrat (YYYY-MM-DD)");
        DateContract = styleJTextFieldBadge(DateContract, 500, 80, 200, 20);

        JTextField valuenom = new JTextField(" ");
        valuenom.setBounds(100, 80, 100, 20);

        JTextField valueprenom = new JTextField(" ");
        valueprenom.setBounds(380, 80, 100, 20);

        JTextField valuecontract = new JTextField(" ");
        valuecontract.setBounds(720, 80, 100, 20);


        JLabel permissionLabel = new JLabel("DROITS : ");
        permissionLabel = styleJLabelBadge(permissionLabel, 20, 150,200,20);


        JTextField permissionEmploye = new JTextField("Selectionnez Droits :");
        permissionEmploye = styleJTextFieldBadge(permissionEmploye, 20, 200, 120, 20);

        String[] value = allpermissions.split("#");
        for(int i = 0; i< value.length; i++){
            listPermissions.add(value[i]);
        }



        permissionsArray = new String[listPermissions.size()];
        permissionsArray = listPermissions.toArray(permissionsArray);





        //String[] permissions = {"Droit Access aux Fenetres","Droit Equipe Laurent","Droit access aux capteurs"};
        JComboBox myPermissions = new JComboBox(permissionsArray);
        myPermissions.setEditable(true);
        myPermissions.setBounds(160,200, 350, 20);

        myPermissions.setEditable(false);
        myPermissions.setForeground(Color.BLUE);
        myPermissions.setFont(new Font("Arial", Font.BOLD, 9));
        myPermissions.setMaximumRowCount(7);

        JButton showpermission = new JButton("Detail droits selectionné");
        JButton newpermission = new JButton("Nouveau Droits");


        showpermission.setBounds(550, 200, 200, 30);
        newpermission.setBounds(750, 200, 150, 30);


        JTextField datepermission = new JTextField("Date validite :");
        datepermission = styleJTextFieldBadge(datepermission, 300, 270, 120, 20);

        JTextField valuedatepermission = new JTextField(" ");
        valuedatepermission.setBounds(450, 270, 100, 20);


        JLabel BadgeLabel = new JLabel("Badge : ");
        BadgeLabel = styleJLabelBadge(BadgeLabel, 20, 300,200,20);



        JTextField PuceLabel = new JTextField("Puce :");
        PuceLabel = styleJTextFieldBadge(PuceLabel, 110, 350, 50, 20);

        JTextField valuepuce = new JTextField(" ");
        valuepuce.setBounds(200, 350, 100, 20);

        JTextField datebadge = new JTextField("Date validite:");
        datebadge = styleJTextFieldBadge(datebadge, 320, 350, 100, 20);

        JTextField valuedatebadge = new JTextField(" ");
        valuedatebadge.setBounds(450, 350, 100, 20);




        JLabel InfosAgent = new JLabel("InfosAgent : ");
        InfosAgent = styleJLabelBadge(InfosAgent, 20, 385,200,20);

        JTextField Idagent = new JTextField("ID agent :");
        Idagent = styleJTextFieldBadge(Idagent, 110, 430, 100, 20);

        JTextField valueidagent = new JTextField(" ");
        valueidagent.setBounds(200, 430, 100, 20);

        JTextField mailagent = new JTextField("Email agent :");
        mailagent = styleJTextFieldBadge(mailagent, 320, 430, 100, 20);


        JTextField valuemailagent = new JTextField(" ");
        valuemailagent.setBounds(450, 430, 100, 20);



        JButton confirm = new JButton("Confirmer");
        confirm.setEnabled(false);
        JButton cancel = new JButton("Annuler");


        cancel.setBounds(300, 500, 150, 30);
        confirm.setBounds(500, 500, 150, 30);


        newpermission.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //choice.setVisible(false);

                pageBody.repaint();
                changePage();
            }
        });

        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String request = "requestNewBadge";
                Client.map.get(request).put("contract_date", input.get("contract_date"));
                Client.map.get(request).put("badge_date", input.get("badge_date"));
                Client.map.get(request).put("nomemploye", input.get("nomemploye"));
                Client.map.get(request).put("prenomemploye", input.get("prenomemploye"));
                Client.map.get(request).put("puceemploye", input.get("puceemploye"));
                Client.map.get(request).put("idagent", input.get("idagent"));
                Client.map.get(request).put("permission", input.get("permission"));
                Client.map.get(request).put("emailagent",input.get("emailagent"));
                Client.map.get(request).put("permission_date",input.get("permission_date"));
                Client.map.get(request).put("company_id",idcompany);




                String result = Client.sendBd(request);
                System.out.println("waaaw"+result);
            }
        });


        myPermissions.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {
                JComboBox<String> combo = (JComboBox<String>) event.getSource();
                String selectedPermission = (String) combo.getSelectedItem();
                input.put("permission", selectedPermission);

            }
        });




        valuecontract.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                /*Object source = e.getSource();
                String m = (((JTextField)source).getText()).trim();
                if (m==""){
                    messageErrorStartDate.setVisible(false);
                }*/
            }
            @Override
            public void focusLost(FocusEvent e) {
                Object source = e.getSource();
                String m = (((JTextField)source).getText()).trim();
                if(m.matches("[0-9]{4}-[0-1]{1}[0-9]{1}-[0-3]{1}[0-9]{1}")) {
                    try {
                        Date today = dateFormat.parse(dateFormat.format(new Date()));
                        Date Mydate = dateFormat.parse(m);
                        if(today.equals(Mydate) || today.before(Mydate)){
                            if(input.containsKey("contract_date"))
                                input.replace("contract_date", ((JTextField)source).getText().trim());
                            else
                                input.put("contract_date", ((JTextField)source).getText().trim());
                            ((JTextField)view.getComponentAt(740, 60)).setText(" ");
                            if(verifMap()) confirm.setEnabled(true);
                            //System.out.println(input);
                        } else {
                            //messageErrorStartDate.setVisible(true);
                            messageErrorStartDate.setText("Veuillez rentrer une date valide");
                            messageErrorStartDate.setForeground(Color.red);
                        }
                    } catch(Exception a) {a.printStackTrace();}
                }else {
                    //messageErrorStartDate.setVisible(true);
                    messageErrorStartDate.setText("Veuillez respecter le format");
                    messageErrorStartDate.setForeground(Color.red);
                }
            }
        });

        valuedatebadge.addFocusListener(new FocusListener() {
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
                            if(input.containsKey("badge_date"))input.replace("badge_date", ((JTextField)source).getText().trim());
                            else input.put("badge_date", ((JTextField)source).getText().trim());
                            messageErrorEndDate.setText(" ");
                            if(verifMap()) confirm.setEnabled(true);
                            //System.out.println(input);
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



        valuedatepermission.addFocusListener(new FocusListener() {
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
                            if(input.containsKey("permission_date"))input.replace("permission_date", ((JTextField)source).getText().trim());
                            else input.put("permission_date", ((JTextField)source).getText().trim());
                            messageErrorPerDate.setText(" ");
                            if(verifMap()) confirm.setEnabled(true);
                            //System.out.println(input);
                        } else {
                            messageErrorPerDate.setText("Veuillez rentrer une date valide");
                            messageErrorPerDate.setForeground(Color.red);
                        }
                    } catch(Exception a) {a.printStackTrace();}
                }else {
                    messageErrorPerDate.setText("Veuillez respecter le format");
                    messageErrorPerDate.setForeground(Color.red);
                }
            }
        });



        JTextField messageErroremailAgent = styleJTextFieldError(view,450, 410, 100, 20);
        valuemailagent.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {}
            @Override
            public void focusLost(FocusEvent e) {
                Object source = e.getSource();
                String m = (((JTextField)source).getText()).trim();

                if (m.matches("^(.+)@(.+)$")) {
                    input.put("emailagent", ((JTextField) source).getText().trim());
                    messageErroremailAgent.setText(" ");
                    if (verifMap()) confirm.setEnabled(true);
                } else {
                    messageErroremailAgent.setText("X");
                    messageErroremailAgent.setForeground(Color.red);
                }

            }
        });



        JTextField messageErrorIdAgent = styleJTextFieldError(view,200, 410, 100, 20);
        valueidagent.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {}
            @Override
            public void focusLost(FocusEvent e) {
                Object source = e.getSource();
                String m = (((JTextField)source).getText()).trim();

                if (m.matches("[1-9]+")) {
                    input.put("idagent", ((JTextField) source).getText().trim());
                    messageErrorIdAgent.setText(" ");
                    if (verifMap()) confirm.setEnabled(true);
                } else {
                    messageErrorIdAgent.setText("X");
                    messageErrorIdAgent.setForeground(Color.red);
                }

            }
        });


        JTextField messageErrorNom = styleJTextFieldError(view,20, 45, 100, 20);
        valuenom.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {}
            @Override
            public void focusLost(FocusEvent e) {
                Object source = e.getSource();
                String m = (((JTextField)source).getText()).trim();

                    if (m.matches("[a-zA-Z]+")) {
                        input.put("nomemploye", ((JTextField) source).getText().trim());
                        messageErrorNom.setText(" ");
                        if (verifMap()) confirm.setEnabled(true);
                    } else {
                        messageErrorNom.setText("X");
                        messageErrorNom.setForeground(Color.red);
                    }

            }
        });

        JTextField messageErrorPrenom = styleJTextFieldError(view,380, 45, 20, 20);
        valueprenom.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {}
            @Override
            public void focusLost(FocusEvent e) {
                Object source = e.getSource();
                String m = (((JTextField)source).getText()).trim();
                if(m.matches("[a-zA-Z]+")) {
                   // System.out.println("waaw");
                    input.put("prenomemploye", ((JTextField)source).getText().trim());
                    messageErrorPrenom.setText(" ");
                    if(verifMap()) confirm.setEnabled(true);
                }else {
                    //System.out.println("wiiw");
                    messageErrorPrenom.setText("X");
                    messageErrorPrenom.setForeground(Color.red);
                }
            }
        });

        JTextField messageErrorPuce = styleJTextFieldError(view,200, 330, 200, 20);
        valuepuce.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {}
            @Override
            public void focusLost(FocusEvent e) {
                Object source = e.getSource();
                String m = (((JTextField)source).getText()).trim();
                if(m.matches("[a-zA-Z_0-9]{6}")) {
                    // System.out.println("waaw");
                    input.put("puceemploye", ((JTextField)source).getText().trim());
                    messageErrorPuce.setText(" ");
                    if(verifMap()) confirm.setEnabled(true);
                }else {
                    //System.out.println("wiiw");
                    messageErrorPuce.setText("code doit etre de 6 caracteres");
                    messageErrorPuce.setForeground(Color.red);
                }
            }
        });






        view.add(infoLabel);
        view.add(NomEmploye);
        view.add(PrenomEmploye);
        view.add(DateContract);
        view.add(valuenom);
        view.add(valueprenom);
        view.add(valuecontract);
        view.add(permissionLabel);
        view.add(permissionEmploye);
        view.add(myPermissions);
        view.add(newpermission);
        view.add(showpermission);
        view.add(BadgeLabel);
        view.add(PuceLabel);
        view.add(valuepuce);
        view.add(datebadge);
        view.add(valuedatebadge);
        view.add(confirm);
        view.add(cancel);
        view.add(valueidagent);
        view.add(Idagent);
        view.add(InfosAgent);
        view.add(mailagent);
        view.add(valuemailagent);
        view.add(datepermission);
        view.add(valuedatepermission);



        pageBody.add(view, BorderLayout.SOUTH);
        pageBody.repaint();
        frame.repaint();
    }

    public void changePage(){

        view.setVisible(false);
        MyPermission permission = new MyPermission(frame);
        permission.choicepermission(pageBody);
    }


    public boolean verifMap(){
        //System.out.println(input);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date contract_date = dateFormat.parse(input.get("contract_date"));
            Date badge_date = dateFormat.parse(input.get("badge_date"));
            Date permission_date = dateFormat.parse(input.get("permission_date"));
            if(contract_date.before(badge_date)){

                JOptionPane d = new JOptionPane();
                d.showMessageDialog(view,
                        "la date de fin de contrat doit etre supperieur à la date de validité de badge",
                        " Attention",
                        JOptionPane.WARNING_MESSAGE);

                return false;

            }

            if(badge_date.before(permission_date)){

                JOptionPane d = new JOptionPane();
                d.showMessageDialog(view,
                        "la date de fin de badge doit etre supperieur à la date de validité de permission",
                        " Attention",
                        JOptionPane.WARNING_MESSAGE);

                return false;

            }

        } catch (ParseException e) {
            e.printStackTrace();
        }


        if((input.containsKey("contract_date") && input.containsKey("permission_date") &&  input.containsKey("badge_date") &&
                input.containsKey("puceemploye") && input.containsKey("prenomemploye") && input.containsKey("emailagent") && input.containsKey("nomemploye")
                && input.containsKey("idagent") && input.containsKey("permission")))
            return true;
        else return false;

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


    public JTextField styleJTextFieldBadge(JTextField t, int x, int y, int w, int h) {
        t.setEditable(false);
        t.setBackground(Color.WHITE);
        t.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        t.setBounds(x, y, w, h);
        return t;
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
