package episen.si.ing1.pds.client;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {
    private JButton back, next, window, window_buildin, window_floor, window_room;
    private JPanel contain1= new JPanel(new GridLayout(10,2,100,10));
    private JPanel contain2=new JPanel(new GridLayout(10,1,0,0));
    private JPanel contain3=new JPanel(new BorderLayout());
    private JPanel contain4=new JPanel(new BorderLayout());
    private JPanel contain5=new JPanel(new GridLayout(1 ,100,100,10));
    private JPanel underMenu = new JPanel(new GridLayout(1,3,0,0));
    private Dimension dim=new Dimension(50,50);
    public Window(){
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setTitle("configuration fenêtres");
        this.setSize(800,800);
        this.setLocationRelativeTo(null);
        window=new JButton("fenêtres");
        window_buildin=new JButton("Toutes les fenêtres du bâtiment");
        window_floor=new JButton("Toutes les fenêtres de l'étage");
        window_room=new JButton("Toutes les fenêtres de la salle");
        window.setPreferredSize(dim);
        window_room.setPreferredSize(dim);
        window_floor.setPreferredSize(dim);
        window_buildin.setPreferredSize(dim);
        contain4.setBackground(Color.CYAN);
        contain2.setBackground(Color.CYAN);
        JLabel l1 = new JLabel("                                                                                                CONFIGURATION FENETRES");
        l1.setPreferredSize(new Dimension( 50, 50));
        //l1.setBackground(Colo);
        contain1.add(contain5);
        contain1.add(l1);
        contain1.add(window_buildin);
        contain1.add(window_floor);
        contain1.add(window_room);
        contain1.add(window);
        JButton realize = new JButton("Realiser une location");
        realize.setBackground(Color.CYAN);
        JButton consult = new JButton("Consulter une location");
        consult.setBackground(Color.CYAN);
        JButton staff = new JButton("Personnel");
        staff.setBackground(Color.CYAN);
        underMenu.setBackground(Color.CYAN);
        underMenu.setMaximumSize(dim);
        JButton disconnect = new JButton("Deconnecter");
        disconnect.setBackground(Color.CYAN);
        ImageIcon iconHome = new ImageIcon(new ImageIcon("C:\\Users\\windows 10\\Desktop\\pds\\R3\\house.png").getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT));
        JButton home = new JButton(iconHome);
        home.setBackground(Color.CYAN);
        ImageIcon iconRefresh = new ImageIcon(new ImageIcon("C:\\Users\\windows 10\\Desktop\\pds\\R3\\refresh.png").getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT));
        JButton refresh = new JButton(iconRefresh);
        refresh.setBackground(Color.CYAN);
        ImageIcon iconBack = new ImageIcon(new ImageIcon("C:\\Users\\windows 10\\Desktop\\pds\\R3\\back.png").getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT));
        back=new JButton(iconBack);
        ImageIcon iconNext = new ImageIcon(new ImageIcon("C:\\Users\\windows 10\\Desktop\\pds\\R3\\next.png").getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT));
        next=new JButton(iconNext);
        underMenu.add(disconnect);
        underMenu.add(home);
        underMenu.add(refresh);
        contain2.add(realize);
        contain2.add(consult);
        contain2.add(staff);
        contain5.add(back);
        contain5.add(next);
        contain4.add(contain2, BorderLayout.CENTER);
        contain4.add(underMenu, BorderLayout.SOUTH);
        getContentPane().add(contain4, BorderLayout.WEST);
        getContentPane().add(contain1, BorderLayout.CENTER);

    }
    public static void main(String[] args){
        Window window=new Window();
        window.setVisible(true);
    }
}
