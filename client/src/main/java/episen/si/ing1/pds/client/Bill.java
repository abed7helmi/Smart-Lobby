package episen.si.ing1.pds.client;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class Bill {
    private final String page = "bill";
    private Map<String, String> input = new HashMap<>();
    private JPanel pageBody;
    private JFrame frame;
    private Map<String , String> criteria = new HashMap<>();
    private Map<String, Map<String,String>> proposalSelected = new HashMap<>();
    private Map<String, Map<String, String>> configRooms = new HashMap<>();
    private Map<String, String> listDeviceIdRoom = new HashMap<>();

    public Bill(Map<String, String> in, JFrame f, Map<String , String> c, Map<String, Map<String,String>> p, Map<String, Map<String, String>> config, Map<String ,String> listIdRoom)  {
        this.input = in;
        this.frame = f;
        criteria = c;
        proposalSelected = p;
        configRooms = config;
        listDeviceIdRoom = listIdRoom;
        System.out.println("/////////////////////////////"+listDeviceIdRoom);
        System.out.println("///////////////////");
        System.out.println(criteria);
        System.out.println("///////////////////");
        System.out.println(proposalSelected);
        System.out.println("///////////////////");
        System.out.println(configRooms);
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
                prepareRequest();
                frame.dispose();
                Menu menu = new Menu("Smart Lobby", input.get("company_id"));
            }
        });

        JPanel table = new JPanel(new BorderLayout());
        table.setBounds(50,100,750,400);


        String[] columns = {"Salle", "Batiment","Etage","Configuration (Capteur/Equipement)"};
        String[][] dataTest = fillTable();

        JTable te = new JTable(dataTest, columns);
        te.setEnabled(false);
        JScrollPane scroll = new JScrollPane(te);
        table.add(scroll, BorderLayout.CENTER);

        JTextField bill = new JTextField("Voici un recapitulatif de votre commande : ");
        styleJTextFieldReservation(bill, 20,20,600,50,Color.white, Color.white, view);
        bill.setBorder(BorderFactory.createMatteBorder(0,0, 1, 0, Color.black));
        bill.setFont(new Font("Serif", Font.BOLD, 20));

        bill.setText(bill.getText() + input.get("start_date") + "/"+ input.get("end_date"));

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
    public void styleJTextFieldReservation(JTextField t, int x, int y, int w, int h, Color c1 , Color c2, JPanel view) {
        t.setEditable(false);
        t.setBackground(c1);
        t.setBorder(BorderFactory.createLineBorder(c2));
        t.setBounds(x, y, w, h);
        view.add(t);
    }

    public String[][] fillTable(){
        String[][] data= new String[configRooms.size()][4];
        int i = 0;
        for(Map<String, String> m : proposalSelected.values()){
            data[i][0] = (m.get("room_wording")).split("salle")[0];
            data[i][1] = m.get("building_name");
            data[i][2] = m.get("floor_number");
            data[i][3] = configRooms.get(m.get("room_id")).get("config_capteur");
            data[i][3] = data[i][3] + " / " + configRooms.get(m.get("room_id")).get("config_equipment");
            i++;
        }
        return data;
    }

    public void prepareRequest(){
        Client.map.get("requestLocation5").put("company_id", input.get("company_id"));
        String manager_id = Client.sendBd("requestLocation5");

        Client.map.get("requestLocation4").put("end_date", input.get("end_date"));
        Client.map.get("requestLocation4").put("start_date", input.get("start_date"));
        float price = 0;
        int i = 0;
        for(Map<String, String> map : proposalSelected.values()){
            price = price + Float.parseFloat(map.get("price"));
            Client.map.get("requestLocation6").put("room"+i, map.get("room_id"));
            i++;
        }//price of rooms
        for(Map<String, String> map : configRooms.values()){
            price = price + Float.parseFloat(map.get("price"));
        }//price of device in room

        Client.map.get("requestLocation4").put("price", price+"");
        Client.map.get("requestLocation4").put("gs_manager_id", manager_id);

        for(Map.Entry map : listDeviceIdRoom.entrySet()){
            Client.map.get("requestLocation7").put(map.getKey()+"" , map.getValue()+"");
        }

        System.out.println(Client.map);

        //Client.sendBd("requestLocation4");
        Client.sendBd("requestLocation6");
        Client.sendBd("requestLocation7");

    }
}
