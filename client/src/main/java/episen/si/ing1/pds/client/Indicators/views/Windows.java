package episen.si.ing1.pds.client.Indicators.views;

import episen.si.ing1.pds.client.Indicators.DialogCommand;
import episen.si.ing1.pds.client.Indicators.tools.Request;
import episen.si.ing1.pds.client.Indicators.tools.Response;
import episen.si.ing1.pds.client.Indicators.tools.Tools;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class Windows implements DialogCommand {
    @Override
    public void execute(JPanel panel) {
        panel.setLayout(new GridLayout(1, 1));
        List<Map> data = getWindowsByCompany();
        TableModel model = new episen.si.ing1.pds.client.Indicators.tablemodels.TableModel(data);
        JTable table = new JTable(model);
        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createTitledBorder("Liste des entreprises utilisants des fenetres electrochromatiques"));

        panel.add(sp);
    }


    private List<Map> getWindowsByCompany() {
        Request request = new Request();
        request.setRequestEvent("windows_by_company");

        Response response = Tools.sendToSocket(request);
        return (List<Map>) response.getResponseBody();
    }
}
