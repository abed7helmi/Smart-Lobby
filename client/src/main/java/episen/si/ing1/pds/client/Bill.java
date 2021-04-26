package episen.si.ing1.pds.client;

import com.fasterxml.jackson.databind.JsonNode;
import sun.jvm.hotspot.ui.JavaThreadsPanel;

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

    public Bill(Map<String, String> input, JFrame f) {
        this.input = input;
        this.frame = f;
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
        validate.setBounds(700,20,100,50);
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

        String[] columns = {"Salle", "Batiment","Etage","Configuration"};
        String[][] dataTest ={
                {"room1","batiment1","etage2","non"},
                {"room1","batiment1","etage2","non"}
        };

        JTable te = new JTable(dataTest, columns);
        JScrollPane scroll = new JScrollPane(te);
        table.add(scroll, BorderLayout.CENTER);

        JTextField bill = new JTextField("Voici un recapitulatif de votre commande : ");
        styleJTextFieldReservation(bill, 20,20,400,50,Color.white, Color.white, view);
        bill.setBorder(BorderFactory.createMatteBorder(0,0, 1, 0, Color.black));
        bill.setFont(new Font("Serif", Font.BOLD, 20));

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
}
