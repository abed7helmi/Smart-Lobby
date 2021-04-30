package episen.si.ing1.pds.client;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestBadge {

    private JFrame frame;
    private JPanel pageBody = new JPanel();
    private Map<String, String> input = new HashMap<>();
    private String idcompany;
    private String devices;
    private List<String> listEquipment = new ArrayList<>();
    private String[] equipementArray;

    public TestBadge(JFrame f ,String i,String r)  {
        input.clear();
        this.idcompany=i;
        this.devices=r;

        frame = f;
    }

    public void testbadge(JPanel pb){

        this.pageBody = pb;
        pageBody.setBackground(Color.WHITE);

        JPanel view = view();

        JLabel infoLabel = new JLabel("Tester les droits d'un employé à un equipement : ");
        infoLabel = styleJLabelReservation(infoLabel, 20, 160,600,20);

        JTextField NomEmploye = new JTextField("Nom :");
        NomEmploye = styleJTextFieldReservation(NomEmploye, 70, 220, 50, 20);

        JTextField valuenom = new JTextField(" ");
        valuenom.setBounds(140, 220, 100, 20);

        JTextField PrenomEmploye = new JTextField("Prenom :");
        PrenomEmploye = styleJTextFieldReservation(PrenomEmploye, 270, 220, 60, 20);

        JTextField valueprenom = new JTextField(" ");
        valueprenom.setBounds(350, 220, 100, 20);

        JTextField device = new JTextField("Equipement :");
        device = styleJTextFieldReservation( device, 470, 220, 80, 20);



        String[] value = devices.split("#");
        System.out.println("sa7ayt;");
        System.out.println(value[0]);

        for(int i = 0; i< value.length; i++){
            listEquipment.add(value[i]);
        }

        //String[] devices = {"Fenetre X45","PC 48","Capteur 45"};

        equipementArray = new String[listEquipment.size()];
        equipementArray = listEquipment.toArray(equipementArray);
        System.out.println(equipementArray[0]);
        JComboBox mydevice = new JComboBox(equipementArray);
        mydevice.setEditable(true);
        mydevice.setBounds(570,220, 320, 20);


        JButton testbutton = new JButton("Tester");
        testbutton.setBounds(400, 280, 150, 30);

        testbutton.setEnabled(false);


        JTextField result = new JTextField("Resultat :");
        result = styleJTextFieldReservation(result, 400, 350, 60, 20);



        JTextField messageErrorNom = styleJTextFieldError(view,170, 195, 100, 20);
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
                    if (verifMap()) testbutton.setEnabled(true);
                } else {
                    messageErrorNom.setText("X");
                    messageErrorNom.setForeground(Color.red);
                }

            }
        });


        JTextField messageErrorPrenom = styleJTextFieldError(view,380, 195, 100, 20);
        valueprenom.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {}
            @Override
            public void focusLost(FocusEvent e) {
                Object source = e.getSource();
                String m = (((JTextField)source).getText()).trim();

                if (m.matches("[a-zA-Z]+")) {
                    input.put("prenomemploye", ((JTextField) source).getText().trim());
                    messageErrorPrenom.setText(" ");
                    if (verifMap()) testbutton.setEnabled(true);
                } else {
                    messageErrorPrenom.setText("X");
                    messageErrorPrenom.setForeground(Color.red);
                }

            }
        });




        testbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Client.map.get("testpermissions").put("company_id",idcompany);
                Client.map.get("testpermissions").put("employee_last_name",input.get("nomemploye"));
                Client.map.get("testpermissions").put("employee_first_name",input.get("prenomemploye"));
                Client.map.get("testpermissions").put("device_id",input.get("end_date"));
                String request = "testpermissions";

                String result = Client.sendBd("testpermissions");
                String company = result.split(",")[0];
                System.out.println(result);
                System.out.println(company);
                //choice.setVisible(false);

                pageBody.repaint();
                //changePage();
            }
        });





        view.add(infoLabel);
        view.add(NomEmploye);
        view.add(PrenomEmploye);
        view.add(device);
        view.add(valuenom);
        view.add(valueprenom);
        view.add(mydevice);
        view.add(testbutton);
        view.add(result);

        pageBody.add(view);
        pageBody.repaint();
        frame.repaint();

    }


    public boolean verifMap(){
        System.out.println(input);
        if((input.containsKey("nomemploye") && input.containsKey("prenomemploye")))
            return true;
        else return false;
    }

    public JPanel view(){
        JPanel view = new JPanel();
        view.setBackground(Color.white);
        sizeComposant(new Dimension(950,600), view);
        view.setLayout(null);



        return view;

    }


    public JLabel styleJLabelReservation(JLabel l, int x, int y, int w, int h){
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

    public JTextField styleJTextFieldReservation(JTextField t, int x, int y, int w, int h) {
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
