package episen.si.ing1.pds.client;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class Bill {
    private final String page = "bill";
    private Map<String, String> input = new HashMap<>();
    private JPanel pageBody;
    private final JFrame frame;
    private Map<String, Map<String,String>> proposalSelected = new HashMap<>();
    private Map<String, Map<String, String>> configRooms = new HashMap<>();
    private Map<String, String> listDeviceIdRoom = new HashMap<>();
    private String response ="";
    private int numberRoom= 0;

    public Bill(Map<String, String> in, JFrame f, Map<String, Map<String,String>> p, Map<String, Map<String, String>> config, Map<String ,String> listIdRoom)  {
        this.input = in;
        this.frame = f;
        proposalSelected = p;
        configRooms = config;
        listDeviceIdRoom = listIdRoom;
    }

    public void confirmation(JPanel pb){
        this.pageBody = pb;
        pageBody.setBackground(Color.WHITE);
        JPanel view = view();
        RentalAdvancement rentalAdvancement = new RentalAdvancement(page);
        JPanel advancement = rentalAdvancement.rentalAdvancement();

        pageBody.add(advancement, BorderLayout.CENTER);
        pageBody.add(view, BorderLayout.SOUTH);
        pageBody.repaint();
        frame.repaint();
        frame.setVisible(true);
    }
    public JPanel view(){
        JPanel view = new JPanel();
        view.setBackground(Color.WHITE);
        sizeComposant(new Dimension(950,600), view);
        view.setLayout(null);

        JButton validate = new JButton("Confirmer");
        validate.setBounds(780, 10, 100, 50);
        validate.setBackground(Color.orange);
        validate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                response = prepareRequest();
                if(  !(response.equals("Not done") && !(response.equals("")))  ){
                    frame.dispose();
                    Menu menu = new Menu("Smart Lobby", input.get("company_id"));
                    restartData();
                    menu.reservationDone(numberRoom);
                } else{
                    Ihm ihm = new Ihm("Smart Lobby","realize", input.get("company_id"));
                    restartData();
                    frame.dispose();
                    JOptionPane.showMessageDialog(null,"Nous sommes desoles mais l'offre n'est plus disponible. Veuillez reessayer.");
                }
            }
        });

        JButton cancel = new JButton("Annuler");
        cancel.setBounds(680, 10, 100, 50);
        cancel.setBackground(Color.WHITE);
        view.add(cancel);


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

        JTextField bill = new JTextField("Voici un recapitulatif de votre commande : ");
        bill = Ihm.styleJTextFieldReservation(bill, 20,20,700,50,Color.white, Color.white);
        bill.setBorder(BorderFactory.createMatteBorder(0,0, 1, 0, Color.black));
        bill.setFont(new Font("Serif", Font.BOLD, 18));

        bill.setText(bill.getText() + input.get("start_date") + "/"+ input.get("end_date"));

        JTextField priceJTextField = new JTextField("Prix total :");
        priceJTextField.setForeground(Color.BLACK);
        JTextField valuePrice = new JTextField(priceTotal()+"");
        valuePrice.setForeground(Color.BLACK);

        priceJTextField = Ihm.styleJTextFieldReservation(priceJTextField, 750,100,65,50, Color.WHITE,Color.white);
        valuePrice = Ihm.styleJTextFieldReservation(valuePrice, 825,100,100,50, Color.WHITE,Color.white);
        view.add(priceJTextField);
        view.add(valuePrice);

        view.add(table);
        view.add(validate);
        view.repaint();
        return view;
    }
    public void sizeComposant(Dimension dim, Component c){
        c.setPreferredSize(dim);
        c.setMaximumSize(dim);
        c.setMinimumSize(dim);
    }

    public String[][] fillTable(){
        String[][] data= new String[configRooms.size()][4];
        int i = 0;
        for(Map<String, String> m : proposalSelected.values()){
            if(m.get("room_wording").contains("reunion")){
                data[i][0] = (m.get("room_wording")).split("reunion")[0] + "reunion";
            } else data[i][0] = (m.get("room_wording")).split("salle")[0];
            data[i][1] = m.get("building_name");
            data[i][2] = m.get("floor_number");
            data[i][3] = configRooms.get(m.get("room_id")).get("config_sensor");
            data[i][3] = data[i][3] + " / " + configRooms.get(m.get("room_id")).get("config_equipment");
            i++;
        }
        numberRoom = i;
        return data;
    }

    public String prepareRequest(){
        Client.map.get("requestLocation5").put("company_id", input.get("company_id"));
        String manager_id = Client.sendBd("requestLocation5");
        System.out.println(input);
        Client.map.get("requestLocation4").put("end_date", input.get("end_date"));
        Client.map.get("requestLocation4").put("start_date", input.get("start_date"));
        float price = 0;
        int i = 0;
        for(Map<String, String> map : proposalSelected.values()){
            if( !((map.get("room_id")+"").equals("")) ) {
                Client.map.get("requestLocation4").put("room"+i, map.get("room_id"));
                i++;
            }
        }
        Client.map.get("requestLocation4").put("gs_manager_id", manager_id);

        for(Map.Entry map : listDeviceIdRoom.entrySet()){
            if( !((map.getValue()+"").equals("")) ) Client.map.get("requestLocation4").put(map.getKey()+"" , map.getValue()+"");
        }
        System.out.println(Client.map);
        response = Client.sendBd("requestLocation4");
        System.out.println(response);
        return response;
    }
    public Float priceTotal(){
        float price = 0;
        for(Map<String, String> map : proposalSelected.values()){
            if( !((map.get("price")+"").equals("")) ) price = price + Float.parseFloat(map.get("price"));
        }// price room
        for(Map<String, String> map : configRooms.values()){
            if( map.containsKey("price") ) price = price + Float.parseFloat(map.get("price")+"");
        }//price device

        Client.map.get("requestLocation4").put("price", price+"");
        return price;
    }

    public void restartData(){
        input.clear();
        configRooms.clear();
        proposalSelected.clear();
        listDeviceIdRoom.clear();
    }
}
