package episen.si.ing1.pds.client.Indicators.views;

import episen.si.ing1.pds.client.Indicators.DialogCommand;
import episen.si.ing1.pds.client.Indicators.tablemodels.TableModel;
import episen.si.ing1.pds.client.Indicators.tools.OccupancyTools;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class OccupancyRate implements DialogCommand {

    @Override
    public void execute(JPanel panel) {
        panel.setLayout(new BorderLayout(40, 40));
        panel.add(new JLabel("Taux d'occupation global : " + OccupancyTools.getGlobalOccupancyRate(), SwingConstants.CENTER), BorderLayout.NORTH);

        JPanel context = new JPanel(new GridLayout(2, 1, 10, 10));

        JTable jt=new JTable();
        JScrollPane sp = new JScrollPane(jt);
        sp.setVisible(false);

        JPanel filterSection = new JPanel(new FlowLayout());
        filterSection.add(new JLabel("Répartition par: "), FlowLayout.LEFT);

        JButton byCompany = new JButton("Entreprise");
        byCompany.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Map> data = OccupancyTools.getOccupancyByCompany();
                jt.setModel(new TableModel(data));
                sp.setVisible(true);

            }
        });

        filterSection.add(byCompany, FlowLayout.CENTER);
        JButton byBuilding = new JButton("Batiment");
        byBuilding.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Map> data = OccupancyTools.getOccupancyByBuilding();
                jt.setModel(new TableModel(data));
                sp.setVisible(true);
            }
        });
        filterSection.add(byBuilding, FlowLayout.RIGHT);

        context.add(filterSection);




        context.add(sp);

        panel.add(context, BorderLayout.CENTER);
    }
}
