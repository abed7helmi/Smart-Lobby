package episen.si.ing1.pds.client;


import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.Map;

public class ViewWithPlan {
    private final String page = "choice";
    private JFrame frame;
    private Map<String , String> input = new HashMap<>();
    private JButton buttonContinue = new JButton("Continuer");
    private JButton buttonReturn = new JButton("Retour");
    private JPanel pageBody;
    private String order;
    public ViewWithPlan(JFrame frame, Map<String, String> input, String o) {
        this.frame = frame;
        this.input = input;
        this.order = o;
    }

    public void viewWithPlan(JPanel pb){
        this.pageBody = pb;
        pageBody.setBackground(Color.WHITE);
        buttonContinue.setEnabled(false);
        buttonContinue.setBounds(780, 10, 100, 50);
        buttonReturn.setEnabled(true);
        buttonReturn.setBounds(670, 10, 100, 50);
        JPanel view = view();
        view.add(buttonContinue);
        view.add(buttonReturn);
        pageBody.add(view, BorderLayout.SOUTH);
        pageBody.repaint();
        frame.repaint();
    }

    public JPanel view(){
        JPanel view = new JPanel();
        view.setBackground(Color.WHITE);
        sizeComposant(new Dimension(950,600), view);
        view.setLayout(null);

        JTextField orderSelected = new JTextField("Vous avez choisi l'offre " + order);
        orderSelected = styleJTextFieldReservation(orderSelected, 10, 10, 320, 50, Color.WHITE, Color.WHITE);
        orderSelected.setFont(new Font("Serif", Font.BOLD, 20));
        view.add(orderSelected);

        JTextField floorTextField = new JTextField("Vous etes sur l'etage : ");
        floorTextField = styleJTextFieldReservation(floorTextField ,340,20 ,150 ,20,Color.white, Color.white);
        view.add(floorTextField);
        String[] floor = {"1","2","3"};
        JComboBox liste = new JComboBox(floor);
        liste.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                System.out.println("etage "+ liste.getSelectedItem());
                view.repaint();
            }
        });

        liste.setEditable(true);
        liste.setBounds(490,20, 30, 20);
        view.add(liste);

        JPanel plan = new JPanel();
        plan.setLayout(new BorderLayout());
        plan.setBackground(Color.RED);
        sizeComposant(new Dimension(500, 400), plan);
        plan.setBounds(10,100,500,400);
        view.add(plan);

        JTextField legend = new JTextField();
        legend.setEditable(false);
        legend.setBackground(Color.GREEN);
        legend.setBounds(520, 100, 50 ,50);
        legend.setBorder(BorderFactory.createMatteBorder(1,1, 1, 1, Color.BLACK));
        JTextField information = new JTextField("Zone proposee dans le batiment XXX et l'etage "+ liste.getSelectedItem());
        information = styleJTextFieldReservation(information, 580, 100, 330,50,Color.red, Color.red);

        view.add(legend);
        view.add(information);

        return view;
    }

    public void sizeComposant(Dimension dim, Component c){
        c.setPreferredSize(dim);
        c.setMaximumSize(dim);
        c.setMinimumSize(dim);
    }
    public JTextField styleJTextFieldReservation(JTextField t, int x, int y, int w, int h, Color c1 , Color c2) {
        t.setEditable(false);
        t.setBackground(c1);
        t.setBorder(BorderFactory.createLineBorder(c2));
        t.setBounds(x, y, w, h);
        return t;
    }
}
