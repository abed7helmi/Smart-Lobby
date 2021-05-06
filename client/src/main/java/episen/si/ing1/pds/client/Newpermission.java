package episen.si.ing1.pds.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.*;
import java.util.List;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Newpermission {

    private Map<String, String> input = new HashMap<>();
    private JFrame frame;
    private JPanel pageBody = new JPanel();
    private JPanel view;
    private String result;
    private JList<String> listchosen;
    private JList<String> list;
    private List<String> listdevices = new ArrayList<>();
    private String[] devicesArray;



    DefaultListModel<String> listModelchosen = new DefaultListModel<>();

    public Newpermission(JFrame f,String result) {
        input.clear();
        this.result=result;
        frame = f;
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
            listdevices.add(value[i]);

        }

        devicesArray = new String[listdevices.size()];
        devicesArray = listdevices.toArray(devicesArray);

        for (int i = 0; i < devicesArray.length; i++) {
            listModel.addElement(devicesArray[i]);
        }

        list = new JList<>(listModel);
        listchosen=new JList<>(listModelchosen);
        view.setBackground(Color.white);

        JScrollPane EmployeList = new JScrollPane(list);
        JScrollPane EmployeListChosen = new JScrollPane(listchosen);

        JTextField NomEmploye = new JTextField("Les equipements choisits:");
        JTextField NomEmploye2 = new JTextField("Choisir un equipement");

        JPanel panneau1 = new JPanel(new BorderLayout());
        panneau1.setBackground(Color.yellow);
        panneau1.setPreferredSize(new Dimension(200, 400));

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


        JButton showpermission = new JButton("Detail droits selectionné");
        JButton newpermission = new JButton("Nouveau Droits");
        showpermission.setVisible(false);

        showpermission.setBounds(540, 100, 200, 30);
        newpermission.setBounds(760, 100, 150, 30);

        JTextField PermissionName = new JTextField("Libelle droit:");
        PermissionName = styleJTextFieldBadge(PermissionName, 300, 200, 80, 20);

        JTextField valuePermissionName = new JTextField(" ");
        valuePermissionName.setBounds(500, 200, 250, 20);

        JButton confirm = new JButton("Confirmer");
        confirm.setEnabled(false);
        JButton cancel = new JButton("Annuler");

        cancel.setBounds(300, 300, 150, 30);
        confirm.setBounds(500, 300, 150, 30);

        panneau4.add(permissionLabel);
        panneau4.add(permissionEmploye);
        panneau4.add(cancel);
        panneau4.add(confirm);
        panneau4.add(showpermission);
        panneau4.add(newpermission);
        panneau4.add(PermissionName);
        panneau4.add(valuePermissionName);

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

        JTextField messageError = styleJTextFieldError(view ,500, 180, 170, 20);

        valuePermissionName.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {}
            @Override
            public void focusLost(FocusEvent e) {
                Object source = e.getSource();
                String m = (((JTextField)source).getText()).trim();
                if(m.matches("[a-zA-Z_0-9]+")) {

                    input.put("valuePermissionName", ((JTextField)source).getText().trim());
                    messageError.setText(" ");
                    if(verifMap()) confirm.setEnabled(true);
                }else {

                    messageError.setText("X");
                    messageError.setForeground(Color.red);
                }
            }
        });

        StringBuilder sb = new StringBuilder();
        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String request = "CreateNewPermission";

                for (int i = 0; i < listModelchosen.size(); i++) {

                    sb.append(listModelchosen.get(i) + "&");
                }

                Client.map.get(request).put("company_id", AcceuilPersonnel.id_company);
                Client.map.get(request).put("permissionname", input.get("valuePermissionName"));
                Client.map.get(request).put("devices",sb.toString());

                String result = Client.sendBd(request);
                if (result.equals("good")){
                    JOptionPane d = new JOptionPane();
                    d.showMessageDialog(view,
                            "Pemission bien enregistrer",
                            " Confirmation",
                            JOptionPane.INFORMATION_MESSAGE);
                }else{
                    JOptionPane d = new JOptionPane();
                    d.showMessageDialog(view,
                            "Nom permission existe deja",
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

    public boolean verifMap(){

        if((input.containsKey("valuePermissionName")  && !(listModelchosen.isEmpty())))
            return true;
        else return false;

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


