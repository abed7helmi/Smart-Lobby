package episen.si.ing1.pds.client;



import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

        //WHEN BUTTON IS CLICKED
        btn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println("waaw");
                System.out.println(lbl);
                System.out.println(requestt);
                if(requestt.equals("Delete")) {
                    System.out.println("waaw");
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

    //OVERRIDE A COUPLE OF METHODS
    @Override
    public Component getTableCellEditorComponent(JTable table, Object obj,
                                                 boolean selected, int row, int col) {

        //SET TEXT TO BUTTON,SET CLICKED TO TRUE,THEN RETURN THE BTN OBJECT
        lbl=(obj==null) ? "":obj.toString();
        btn.setText(lbl);
        clicked=true;
        return btn;
    }

    //IF BUTTON CELL VALUE CHNAGES,IF CLICKED THAT IS
    @Override
    public Object getCellEditorValue() {

        if(clicked)
        {
            //SHOW US SOME MESSAGE
            if(requestt.equals("Delete")) {
                JOptionPane.showMessageDialog(btn,  " employe "+lbl+" supprimé");
            }else if(requestt.equals("Detail")) {
                JOptionPane.showMessageDialog(btn,  "vous pouvez modifier l'employé d'ID "+lbl);
            }
        }
        //SET IT TO FALSE NOW THAT ITS CLICKED
        clicked=false;
        return new String(lbl);
    }

    @Override
    public boolean stopCellEditing() {

        //SET CLICKED TO FALSE FIRST
        clicked=false;
        return super.stopCellEditing();
    }

    @Override
    protected void fireEditingStopped() {
        // TODO Auto-generated method stub
        super.fireEditingStopped();
    }
}

