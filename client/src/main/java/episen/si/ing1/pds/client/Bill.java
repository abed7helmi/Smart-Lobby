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
    }

    public JPanel view(){
        JPanel view = new JPanel();
        view.setBackground(Color.RED);
        sizeComposant(new Dimension(950,600), view);
        view.setLayout(null);

        JButton validate = new JButton("Confirmer");
        validate.setBounds(20,20,100,50);
        validate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pageBody.setVisible(false);
            }
        });

        view.add(validate);
        return view;
    }
    public void sizeComposant(Dimension dim, Component c){
        c.setPreferredSize(dim);
        c.setMaximumSize(dim);
        c.setMinimumSize(dim);
    }
}
