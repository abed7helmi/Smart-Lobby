package episen.si.ing1.pds.client;



import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Staff:Listener for table class
class ButtonEditor extends DefaultCellEditor
{
    protected JButton btn;
    private String lbl;
    private Boolean clicked;
    private String requestt;
    AllBadge allbadge;

    public ButtonEditor(JTextField txt,String request,AllBadge b) {
        super(txt);
        this.requestt=request;
        allbadge=b;
        btn=new JButton();
        btn.setOpaque(true);
        btn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(requestt.equals("Delete")) {

                    String request = "deletepermission";
                    Client.map.get(request).put("company_id", AcceuilPersonnel.id_company);
                    Client.map.get(request).put("idemploye",lbl);

                    String result = Client.sendBd("deletepermission");


                }

                if(requestt.equals("Detail")) {
                    allbadge.changePageEdit(lbl);
                }
                fireEditingStopped();
            }
        });
    }


    @Override
    public Component getTableCellEditorComponent(JTable table, Object obj,
                                                 boolean selected, int row, int col) {
        lbl=(obj==null) ? "":obj.toString();
        btn.setText(lbl);
        clicked=true;
        return btn;
    }


    @Override
    public Object getCellEditorValue() {

        if(clicked)
        {
            if(requestt.equals("Delete")) {
                JOptionPane.showMessageDialog(btn,  " employe "+lbl+" supprimé");
            }else if(requestt.equals("Detail")) {
                JOptionPane.showMessageDialog(btn,  "vous pouvez modifier l'employé d'ID "+lbl);
            }
        }

        clicked=false;
        return new String(lbl);
    }

    @Override
    public boolean stopCellEditing() {

        clicked=false;
        return super.stopCellEditing();
    }

    @Override
    protected void fireEditingStopped() {
        // TODO Auto-generated method stub
        super.fireEditingStopped();
    }
}

