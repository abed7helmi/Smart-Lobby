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

    public Bill(Map<String, String> input, JFrame f, Map<String , String> c,Map<String, Map<String,String>> p, Map<String, Map<String, String>> config)  {
        this.input = input;
        this.frame = f;
        criteria = c;
        proposalSelected = p;
        configRooms = config;
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
                frame.dispose();
                Menu menu = new Menu("Smart Lobby");
            }
        });

        JPanel table = new JPanel(new BorderLayout());
        table.setBounds(50,100,650,500);


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
        System.out.println(configRooms.size());

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
}
