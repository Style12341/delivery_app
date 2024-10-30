package deso.delivery_app.views.clientes;

import deso.delivery_app.controllers.ClienteController;
import deso.delivery_app.models.Cliente;
import deso.delivery_app.views.AdminLayoutForm;
import deso.delivery_app.views.components.ButtonColumn;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.List;

public class ClientesIndexForm {
    private JPanel panel1;
    private JButton crearClienteButton;
    private JTextField NombreField;
    private JTable listClientes;
    private JTextField ApellidoField;
    private JButton buscarButton;
    private JTextField DireccionFIeld;
    private JTextField EmailField;
    private ClienteController controller;

    public ClientesIndexForm() {
        controller = new ClienteController();
        createTable(controller.getLista());
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent _e) {
                System.out.println("Inside event handler for search button");
                // Extract fields and generate filters
                String nombre = getNombreField();
                String apellido = getApellidoField();
                String direccion = getDireccionField();
                String email = getEmailField();
                createTable(controller.getLista(nombre, apellido, email, direccion));
            }
        });

        crearClienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent _e) {
                ClientesForm vendedoresCreateForm = new ClientesForm();
                AdminLayoutForm.getInstance().replaceContent(vendedoresCreateForm.getRootPanel());
            }
        });
    }

    public void createTable(List<Cliente> cs) {
        Object[][] data = new Object[cs.size()][8];
        for (int i = 0; i < cs.size(); i++) {
            Cliente c = cs.get(i);
            data[i][0] = c.getId();
            data[i][1] = c.getNombre();
            data[i][2] = c.getApellido();
            data[i][3] = c.getCuit();
            data[i][4] = c.getEmail();
            data[i][5] = c.getDireccion();
            data[i][6] = "Editar";
            data[i][7] = "Eliminar";
        }
        String[] columnNames = {"Id", "Nombre","Apellido", "Cuit", "Email", "Direccion", "Editar", "Eliminar"};

        assert listClientes != null;
        listClientes.setModel(new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                int editarIndex = listClientes.getColumnModel().getColumnIndex("Editar");
                int eliminarIndex = listClientes.getColumnModel().getColumnIndex("Eliminar");
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
                Cliente c = controller.buscar(id);
                ClientesForm clientesEditForm = new ClientesForm(c);
                AdminLayoutForm.getInstance().replaceContent(clientesEditForm.getRootPanel());
            }
        };
        int editarIndex = listClientes.getColumnModel().getColumnIndex("Editar");
        int eliminarIndex = listClientes.getColumnModel().getColumnIndex("Eliminar");
        ButtonColumn buttonColumn = new ButtonColumn(listClientes, delete, eliminarIndex);
        ButtonColumn buttonColumn2 = new ButtonColumn(listClientes, openEdit, editarIndex);
        buttonColumn.setMnemonic(KeyEvent.VK_D);

    }


    public String getNombreField() {
        return NombreField.getText();
    }

    public String getDireccionField() {
        return DireccionFIeld.getText();
    }

    public String getEmailField() {
        return EmailField.getText();
    }

    public String getApellidoField() {
        return ApellidoField.getText();
    }

    public JPanel getRootPanel() {
        return panel1;
    }
}
