package episen.si.ing1.pds.client.Indicators.views;

import episen.si.ing1.pds.client.Indicators.DialogCommand;
import episen.si.ing1.pds.client.Indicators.tablemodels.TableModel;
import episen.si.ing1.pds.client.Indicators.tools.SensorsTools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class Sensors implements DialogCommand {
    @Override
    public void execute(JPanel panel) {
        panel.setLayout(new BorderLayout(40, 40));


        JPanel header = new JPanel(new FlowLayout(FlowLayout.CENTER)), content = new JPanel(new GridLayout(1, 2, 10, 20)), p1 = new JPanel(), p2 = new JPanel();

        header.add(new JLabel("Nombre de capteurs - Taux de remplissage", SwingConstants.CENTER));

        JTable sortedByType = new JTable(), mappingRate = new JTable();
        mappingRate.setVisible(false);

        List<Map> sortedTypeList = SensorsTools.getSensorsSortedByMostUsedType();

        sortedByType.setModel(new TableModel(sortedTypeList));
        JScrollPane spST = new JScrollPane(sortedByType);
        spST.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        p1.add(spST);
        p1.setBorder(BorderFactory.createTitledBorder("Capteurs/equipements utilisés"));

        p2.setLayout(new GridLayout(3, 1, 0, 0));

        JComboBox comboBox = new JComboBox(new Vector(SensorsTools.getCompaniesList()));
        comboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if(value instanceof Map) {
                    Map lhm = (Map) value;
                    setText((String) lhm.get("name"));
                }

                if(index == -1 && value == null)
                    setText("Selectionnez une entreprise");
                return this;
            }
        });
        comboBox.setSelectedIndex(-1);

        comboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED) {
                    Map company = (Map) e.getItem();
                    List<Map> data = SensorsTools.getMappingRate((Integer) company.get("id"));
                    if(data.size() > 0) {
                        mappingRate.setModel(new TableModel(data));
                        mappingRate.setVisible(true);
                    }
                    else {
                        mappingRate.setVisible(false);
                        mappingRate.invalidate();
                        mappingRate.validate();
                        mappingRate.repaint();
                        JOptionPane.showMessageDialog(panel,"Pas de reservation avec capteurs à mapper");
                    }
                    mappingRate.setVisible(true);
                }
            }
        });
        p2.add(comboBox);
        p2.add(new JScrollPane(mappingRate));
        p2.setBorder(BorderFactory.createTitledBorder("Taux de remplissage"));

        content.add(p1);
        content.add(p2);


        panel.add(header, BorderLayout.PAGE_START);
        panel.add(content, BorderLayout.CENTER);
    }
}
