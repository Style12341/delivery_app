package deso.delivery_app.views;

import deso.delivery_app.controllers.VendedorController;
import deso.delivery_app.models.Vendedor;
import deso.delivery_app.views.utils.ButtonColumn;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.List;

public class VendedoresIndexForm {
    private JPanel panel1;
    private JButton crearVendedorButton;
    private JTextField NombreField;
    private JTable listVendedores;
    private JTextField DireccionField;
    private JButton buscarButton;
    private VendedorController controller;

    public VendedoresIndexForm() {
        controller = new VendedorController(this);
        createTable(null);
    }

    public void createTable(Object[][] data) {
        String[] columnNames = {"Id", "Nombre", "Cuit", "Direccion", "Editar", "Eliminar"};

        assert listVendedores != null;
        listVendedores.setModel(new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5 || column == 4;
            }
        });
        Action delete = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTable table = (JTable) e.getSource();
                int modelRow = Integer.parseInt(e.getActionCommand());
                ((DefaultTableModel) table.getModel()).removeRow(modelRow);
            }
        };
        ButtonColumn buttonColumn = new ButtonColumn(listVendedores, delete, 5);
        buttonColumn.setMnemonic(KeyEvent.VK_D);

    }

    public JButton getBuscarBtn() {
        return buscarButton;
    }

    public String getNombreField() {
        return NombreField.getText();
    }

    public String getDireccionField() {
        return DireccionField.getText();
    }

    public void updateTable(List<Vendedor> vs) {
        if (vs == null) {
            createTable(null);
            return;
        }
        Object[][] data = new Object[vs.size()][6];
        for (int i = 0; i < vs.size(); i++) {
            Vendedor v = vs.get(i);
            data[i][0] = v.getId();
            data[i][1] = v.getNombre();
            data[i][2] = v.getCuit();
            data[i][3] = v.getDireccion();
            data[i][4] = "Editar";
            data[i][5] = "Eliminar";
        }
        //listVendedores.setModel(new DefaultTableModel());
        createTable(data);
    }

    public JPanel getRootPanel() {
        return panel1;
    }
}
