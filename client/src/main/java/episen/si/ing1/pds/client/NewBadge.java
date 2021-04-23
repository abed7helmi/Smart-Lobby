package episen.si.ing1.pds.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class NewBadge {
    private Map<String, String> input = new HashMap<>();
    private JFrame frame;
    private JPanel pageBody;
    private JPanel view;


    public NewBadge(JFrame f){
        input.clear();
        this.frame = f;

    }

    public void choice(JPanel pb){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        this.pageBody = pb;
        pageBody.setBackground(Color.WHITE);
        view = view();
        JTextField messageErrorStartDate = styleJTextFieldError(view ,740, 60, 170, 20);
        JTextField messageErrorEndDate = styleJTextFieldError(view ,670, 330, 170, 20);
        JLabel infoLabel = new JLabel("INFOS EMPLOYE : ");
        infoLabel = styleJLabelReservation(infoLabel, 20, 20,200,20);

        JTextField NomEmploye = new JTextField("Nom :");
        NomEmploye = styleJTextFieldReservation(NomEmploye, 20, 80, 50, 20);

        JTextField PrenomEmploye = new JTextField("Prenom :");
        PrenomEmploye = styleJTextFieldReservation(PrenomEmploye, 300, 80, 60, 20);

        JTextField DateContract = new JTextField("Date fin contrat (YYYY-MM-DD)");
        DateContract = styleJTextFieldReservation(DateContract, 500, 80, 200, 20);

        JTextField valuenom = new JTextField(" ");
        valuenom.setBounds(100, 80, 100, 20);

        JTextField valueprenom = new JTextField(" ");
        valueprenom.setBounds(380, 80, 100, 20);

        JTextField valuecontract = new JTextField(" ");
        valuecontract.setBounds(720, 80, 100, 20);


        JLabel permissionLabel = new JLabel("DROITS : ");
        permissionLabel = styleJLabelReservation(permissionLabel, 20, 150,200,20);


        JTextField permissionEmploye = new JTextField("Selectionnez Droits :");
        permissionEmploye = styleJTextFieldReservation(permissionEmploye, 50, 200, 200, 20);

        String[] permissions = {"Droit Access aux Fenetres","Droit Equipe Laurent","Droit access aux capteurs"};
        JComboBox myPermissions = new JComboBox(permissions);
        myPermissions.setEditable(true);
        myPermissions.setBounds(200,200, 300, 20);


        JButton showpermission = new JButton("Detail droits selectionné");
        JButton newpermission = new JButton("Nouveau Droits");


        showpermission.setBounds(550, 200, 200, 30);
        newpermission.setBounds(750, 200, 150, 30);


        JLabel BadgeLabel = new JLabel("Badge : ");
        BadgeLabel = styleJLabelReservation(BadgeLabel, 20, 300,200,20);


        JTextField PuceLabel = new JTextField("Puce :");
        PuceLabel = styleJTextFieldReservation(PuceLabel, 300, 350, 50, 20);

        JTextField valuepuce = new JTextField(" ");
        valuepuce.setBounds(350, 350, 100, 20);

        JTextField datebadge = new JTextField("Date fin (YYYY-MM-DD):");
        datebadge = styleJTextFieldReservation(datebadge, 500, 350, 150, 20);

        JTextField valuedatebadge = new JTextField(" ");
        valuedatebadge.setBounds(650, 350, 100, 20);


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
                        Date today1 = dateFormat.parse(dateFormat.format(new Date()));
                        Date date1 = dateFormat.parse(m);
                        if(today1.equals(date1) || today1.before(date1)){
                            if(input.containsKey("contract_date"))
                                input.replace("contract_date", ((JTextField)source).getText().trim());
                            else
                                input.put("contract_date", ((JTextField)source).getText().trim());
                            ((JTextField)view.getComponentAt(740, 60)).setText(" ");
                            if(verifMap()) confirm.setEnabled(true);
                            System.out.println(input);
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
                        Date today1 = dateFormat.parse(dateFormat.format(new Date()));
                        Date date1 = dateFormat.parse(m);
                        if(today1.equals(date1) || today1.before(date1)){
                            if(input.containsKey("badge_date"))input.replace("badge_date", ((JTextField)source).getText().trim());
                            else input.put("badge_date", ((JTextField)source).getText().trim());
                            messageErrorEndDate.setText(" ");
                            if(verifMap()) confirm.setEnabled(true);
                            System.out.println(input);
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
        System.out.println(input);
        if((input.containsKey("contract_date") && input.containsKey("badge_date") && input.containsKey("numberEmployee"))
                && ( (input.containsKey("numberOpenSpace") || input.containsKey("numberMeetingRoom") ||
                input.containsKey("numberSingleOffice")|| input.containsKey("numberBoxClosedOffice"))))
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


    public JTextField styleJTextFieldReservation(JTextField t, int x, int y, int w, int h) {
        t.setEditable(false);
        t.setBackground(Color.WHITE);
        t.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        t.setBounds(x, y, w, h);
        return t;
    }

    public JLabel styleJLabelReservation(JLabel l, int x, int y, int w, int h){
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
