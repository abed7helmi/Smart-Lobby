package episen.si.ing1.pds.client;

import javax.swing.*;
import java.awt.*;

public class RentalAdvancement {
    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public RentalAdvancement(String page) {
        this.page = page;
    }

    private String page;



    public JPanel rentalAdvancement(){
        JPanel rentalAdvancement = new JPanel();
        rentalAdvancement.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 15));
        Dimension dimAdvancement = new Dimension(950, 70);
        sizeComposant(dimAdvancement, rentalAdvancement);
        rentalAdvancement.setBackground(Color.WHITE);
        rentalAdvancement.setBorder(BorderFactory.createMatteBorder(0,0,2,0, Color.BLACK));

        JTextField criteria= new JTextField("Criteres", 5);
        criteria.setEditable(false);
        criteria.setOpaque(false);
        if(page.equals("criteres")) criteria.setForeground(Color.RED);

        criteria.setBorder(BorderFactory.createEmptyBorder(10,20,10,0));
        JTextField choiceField = new JTextField("Choix",5);
        choiceField.setEditable(false);
        choiceField.setOpaque(false);
        choiceField.setBorder(BorderFactory.createEmptyBorder(10,2,10,0));
        JTextField equipment = new JTextField("Equipements/Capteurs",15);
        equipment.setEditable(false);
        equipment.setOpaque(false);
        equipment.setBorder(BorderFactory.createEmptyBorder(10,2,10,0));
        JTextField bill = new JTextField("Facture", 5);
        bill.setEditable(false);
        bill.setOpaque(false);
        bill.setBorder(BorderFactory.createEmptyBorder(10,2,10,0));

        rentalAdvancement.add(criteria);
        rentalAdvancement.add(fleche());
        rentalAdvancement.add(choiceField);
        rentalAdvancement.add(fleche());
        rentalAdvancement.add(equipment);
        rentalAdvancement.add(fleche());
        rentalAdvancement.add(bill);

        return rentalAdvancement;
    }

    public JLabel fleche(){
        ImageIcon iconAdvancement = new ImageIcon(new ImageIcon("C:\\Users\\cedri\\Bureau\\pds\\image\\flecheAdvancement.png")
                .getImage().getScaledInstance(50,30,Image.SCALE_DEFAULT));
        JLabel flecheAdvancement = new JLabel(iconAdvancement,JLabel.CENTER);
        return flecheAdvancement;
    }

    public void sizeComposant(Dimension dim, Component c){
        c.setPreferredSize(dim);
        c.setMaximumSize(dim);
        c.setMinimumSize(dim);
    }
}
