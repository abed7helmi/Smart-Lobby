package episen.si.ing1.pds.client;



import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Staff:Listener for table class
class ButtonEditorDevice extends DefaultCellEditor
{
    protected JButton btn;
    private String lbl;
    private Boolean clicked;
    private String requestt;
    MyPermission permission;
    private String idpermission;

    public ButtonEditorDevice(JTextField txt,String request,String idpermission,MyPermission per) {
        super(txt);
        this.requestt=request;
        this.idpermission=idpermission;
        this.permission=per;
        btn=new JButton();
        btn.setOpaque(true);

        btn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if(requestt.equals("Delete")) {

                    String request = "deletedevice";

                    Client.map.get(request).put("company_id", AcceuilPersonnel.id_company);
                    Client.map.get(request).put("iddevice",lbl);
                    Client.map.get(request).put("idpermission",idpermission);

                    String result = Client.sendBd("deletedevice");
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
                JOptionPane.showMessageDialog(btn,  " equipement "+lbl+" supprimé");
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

