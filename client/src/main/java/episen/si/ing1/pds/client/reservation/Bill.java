package episen.si.ing1.pds.client.reservation;

import episen.si.ing1.pds.client.Client;
import episen.si.ing1.pds.client.Ihm;
import episen.si.ing1.pds.client.Menu;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class Bill {
    private final String page = "bill";
    private final JFrame frame;
    private String response ="";
    private int numberRoom= 0;
    private JPanel pageBody;

    public Bill(JFrame f)  {
        this.frame = f;
    }

    public void confirmation(JPanel pb){
        this.pageBody = pb;
        pageBody.setBackground(Color.WHITE);

        JPanel view = viewBill();
        RentalAdvancement rentalAdvancement = new RentalAdvancement(page);
        JPanel advancement = rentalAdvancement.rentalAdvancement();

        pageBody.add(advancement, BorderLayout.CENTER);
        pageBody.add(view, BorderLayout.SOUTH);
        pageBody.repaint();
        frame.repaint();
    }
    public JPanel viewBill(){
        JPanel viewBill = new JPanel();
        frame.repaint();

        viewBill.setBackground(Color.WHITE);
        sizeComposant(new Dimension(950,600), viewBill);
        viewBill.setLayout(null);

        JTextField title = new JTextField();
        title = Ihm.styleJTextFieldReservation(title, 20,20,500,50,Color.WHITE, Color.white);
        title.setBorder(BorderFactory.createMatteBorder(0,0, 1, 0, Color.black));
        title.setFont(new Font("Serif", Font.BOLD, 18));
        title.setVisible(true);
        title.setText("Recapitualtif de votre commande : " + ChoiceCriteria.input.get("start_date") + " / "+ ChoiceCriteria.input.get("end_date"));
        viewBill.add(title);

        JButton validate = new JButton("Confirmer");
        validate.setBounds(780, 10, 100, 50);
        validate.setBackground(Color.orange);
        validate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                response = prepareRequest();
                if(  !(response.equals("Not done") && !(response.equals("")))  ){
                    frame.dispose();
                    episen.si.ing1.pds.client.Menu menu = new episen.si.ing1.pds.client.Menu("Smart Lobby", ChoiceCriteria.input.get("company_id"));
                    ChoiceCriteria.restartData();
                    menu.reservationDone(numberRoom);
                } else{
                    Ihm ihm = new Ihm("Smart Lobby","realize", ChoiceCriteria.input.get("company_id"));
                    ChoiceCriteria.restartData();
                    JOptionPane.showMessageDialog(null,"Nous sommes desoles mais l'offre n'est plus disponible. Veuillez reessayer.");
                }
            }
        });

        JButton cancel = new JButton("Annuler");
        cancel.setBounds(650, 10, 100, 50);
        cancel.setBackground(Color.WHITE);
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                episen.si.ing1.pds.client.Menu menu = new episen.si.ing1.pds.client.Menu("Smart Lobby", Menu.company_id);
                ChoiceCriteria.restartData();
            }
        });
        viewBill.add(cancel);


        JPanel table = new JPanel(new BorderLayout());
        table.setBounds(20,100,700,400);

        String[] columns = {"Salle", "Batiment","Etage","Configuration (Capteur/Equipement)"};
        String[][] dataTest = fillTable();

        JTable dataTable = new JTable();
        DefaultTableModel tableModel = new DefaultTableModel(dataTest, columns);
        dataTable.setModel(tableModel);

        TableColumn column = dataTable.getColumnModel().getColumn(0);
        column.setPreferredWidth(50);

        column = dataTable.getColumnModel().getColumn(1);
        column.setPreferredWidth(50);

        column = dataTable.getColumnModel().getColumn(2);
        column.setPreferredWidth(10);

        dataTable.setEnabled(false);
        JScrollPane scroll = new JScrollPane(dataTable);
        table.add(scroll, BorderLayout.CENTER);


        JTextField priceJTextField = new JTextField("Prix total :");
        priceJTextField.setForeground(Color.BLACK);
        JTextField valuePrice = new JTextField(priceTotal()+"");
        valuePrice.setForeground(Color.BLACK);

        priceJTextField = Ihm.styleJTextFieldReservation(priceJTextField, 750,100,65,50, Color.WHITE,Color.white);
        valuePrice = Ihm.styleJTextFieldReservation(valuePrice, 825,100,100,50, Color.WHITE,Color.white);

        JTextField dateJTextField = new JTextField( "Date : " +  ChoiceCriteria.input.get("start_date") + " / "+ ChoiceCriteria.input.get("end_date") );
        dateJTextField = Ihm.styleJTextFieldReservation(dateJTextField, 750,150,175,50, Color.WHITE,Color.white);

        viewBill.add(dateJTextField);
        viewBill.add(priceJTextField);
        viewBill.add(valuePrice);
        viewBill.add(table);
        viewBill.add(validate);
        viewBill.repaint();
        return viewBill;
    }
    public void sizeComposant(Dimension dim, Component c){
        c.setPreferredSize(dim);
        c.setMaximumSize(dim);
        c.setMinimumSize(dim);
    }

    public String[][] fillTable(){
        String[][] data = new String[ViewWithPlan.configRoom.size()][4];
        int i = 0;
        for(Map<String, String> m : Choice.proposalSelected.values()){
            if(m.get("room_wording").contains("reunion")){
                data[i][0] = (m.get("room_wording")).split("reunion")[0] + "reunion";
            } else data[i][0] = (m.get("room_wording")).split("salle")[0];
            data[i][1] = m.get("building_name");
            data[i][2] = m.get("floor_number");
            data[i][3] = ViewWithPlan.configRoom.get(m.get("room_id")).get("config_sensor");
            data[i][3] = data[i][3] + " / " + ViewWithPlan.configRoom.get(m.get("room_id")).get("config_equipment");
            i++;
        }
        numberRoom = i;
        return data;
    }

    public String prepareRequest(){
        Client.map.get("requestLocation5").put("company_id", Menu.company_id);
        String manager_id = Client.sendBd("requestLocation5");
        Client.map.get("requestLocation4").put("end_date", ChoiceCriteria.input.get("end_date"));
        Client.map.get("requestLocation4").put("start_date", ChoiceCriteria.input.get("start_date"));
        float price = 0;
        int i = 0;
        for(Map<String, String> map : Choice.proposalSelected.values()){
            if( !((map.get("room_id")+"").equals("")) ) {
                Client.map.get("requestLocation4").put("room"+i, map.get("room_id"));
                i++;
            }
        }
        Client.map.get("requestLocation4").put("gs_manager_id", manager_id);

        for(Map.Entry map : ViewWithPlan.listDeviceIdRoom.entrySet()){
            if( !((map.getValue()+"").equals("")) ) Client.map.get("requestLocation4").put(map.getKey()+"" , map.getValue()+"");
        }
        response = Client.sendBd("requestLocation4");
        return response;
    }
    public Float priceTotal(){
        float price = Float.parseFloat(ChoiceCriteria.input.get("price"));
        System.out.println(ViewWithPlan.configRoom);
        for(Map<String, String> map : Choice.proposalSelected.values()){
            if( !((map.get("price")+"").equals("")) ) price = price + Float.parseFloat(map.get("price"));
        }// price room

        /*for(Map<String, String> map : ViewWithPlan.configRoom.values()){
            if( map.containsKey("price") ) price = price + Float.parseFloat(map.get("price")+"");
        }//price device*/

        Client.map.get("requestLocation4").put("price", price+"");
        return price;
    }
}
