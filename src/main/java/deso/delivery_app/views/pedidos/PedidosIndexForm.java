package deso.delivery_app.views.pedidos;

import deso.delivery_app.controllers.VendedorController;
import deso.delivery_app.models.Vendedor;
import deso.delivery_app.views.AdminLayoutForm;
import deso.delivery_app.views.components.ButtonColumn;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.List;

public class PedidosIndexForm {
    private JPanel panel1;
    private JButton crearVendedorButton;
    private JTextField NombreField;
    private JTable listVendedores;
    private JTextField DireccionField;
    private JButton buscarButton;
    private VendedorController controller;

    public PedidosIndexForm() {
        controller = new VendedorController();
        createTable(controller.getLista());
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent _e) {
                System.out.println("Inside event handler for search button");
                // Extract fields and generate filters
                String nombre = getNombreField();
                String direccion = getDireccionField();
                createTable(controller.getLista(nombre, direccion));
            }
        });

        crearVendedorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent _e) {
                PedidosCreateForm vendedoresCreateForm = new PedidosCreateForm();
                AdminLayoutForm.getInstance().replaceContent(vendedoresCreateForm.getRootPanel());
            }
        });
    }

    public void createTable(List<Vendedor> vs) {
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
        String[] columnNames = {"Id", "Nombre", "Cuit", "Direccion", "Editar", "Eliminar"};

        assert listVendedores != null;
        listVendedores.setModel(new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                int editarIndex = listVendedores.getColumnModel().getColumnIndex("Editar");
                int eliminarIndex = listVendedores.getColumnModel().getColumnIndex("Eliminar");
                return column == editarIndex || column == eliminarIndex;
            }
        });
        Action delete = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTable table = (JTable) e.getSource();
                int modelRow = Integer.parseInt(e.getActionCommand());
                long id = (long) table.getModel().getValueAt(modelRow, 0);
                ((DefaultTableModel) table.getModel()).removeRow(modelRow);
                controller.eliminar(id);
            }
        };
        Action openEdit = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTable table = (JTable) e.getSource();
                int modelRow = Integer.parseInt(e.getActionCommand());
                long id = (long) table.getModel().getValueAt(modelRow, 0);
                Vendedor v = controller.buscar(id);
                PedidosEditForm vendedoresEditForm = new PedidosEditForm(v);
                AdminLayoutForm.getInstance().replaceContent(vendedoresEditForm.getRootPanel());
            }
        };
        ButtonColumn buttonColumn = new ButtonColumn(listVendedores, delete, 5);
        ButtonColumn buttonColumn2 = new ButtonColumn(listVendedores, openEdit, 4);
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


    public JPanel getRootPanel() {
        return panel1;
    }
}
