package deso.delivery_app.views.pedidos;

import deso.delivery_app.ESTADO_PEDIDO;
import deso.delivery_app.controllers.PedidoController;
import deso.delivery_app.models.Pedido;
import deso.delivery_app.models.Vendedor;
import deso.delivery_app.views.AdminLayoutForm;
import deso.delivery_app.views.components.ButtonColumn;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Objects;

public class PedidosIndexForm {
    private JPanel panel1;
    private JButton crearPedidoButton;
    private JTable listPedidos;
    private JButton buscarButton;
    private JTextField vendedorField;
    private JTextField nombreClienteField;
    private JComboBox<String> estadoComboBox;
    private JTextField precioMinField;
    private JTextField precioMaxField;
    private JTextField idField;
    private PedidoController controller;

    public PedidosIndexForm() {
        controller = new PedidoController();
        createTable(controller.getLista());
        createComboBox();
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent _e) {
                System.out.println("Inside event handler for search button");
                // Extract fields and generate filters
                // id, estado, vendedor, cliente, precioMin, precioMax
                long id = getIdField();
                ESTADO_PEDIDO estado = getEstadoField();
                String vendedor = getVendedorField();
                long vendedor_id = getVendedorId();
                String nombreCliente = getClienteField();
                System.out.println("Cliente: " + nombreCliente);
                double precioMin = getPrecioMinField();
                double precioMax = getPrecioMaxField();
                createTable(controller.getLista(id, estado, vendedor_id, vendedor, nombreCliente, precioMin, precioMax));
            }
        });

        crearPedidoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent _e) {
                PedidosForm vendedoresCreateForm = new PedidosForm();
                AdminLayoutForm.getInstance().replaceContent(vendedoresCreateForm.getRootPanel());
            }
        });
    }

    private void createComboBox() {
        estadoComboBox.addItem("TODOS");
        estadoComboBox.addItem("PENDIENTE");
        estadoComboBox.addItem("EN_ENVIO");
        estadoComboBox.addItem("RECIBIDO");
    }

    public void createTable(List<Pedido> ps) {
        Object[][] data = new Object[ps.size()][7];
        for (int i = 0; i < ps.size(); i++) {
            Pedido p = ps.get(i);
            data[i][0] = p.getId();
            data[i][1] = p.getEstado().toString();
            data[i][2] = p.getVendedor().getNombre();
            data[i][3] = p.getCliente().getNombre().concat(" ").concat(p.getCliente().getApellido());
            data[i][4] = p.getPrecioAcumulado();
            data[i][5] = "Editar";
            data[i][6] = "Eliminar";
        }
        String[] columnNames = {"Id", "Estado", "Vendedor", "Cliente", "Precio Total", "Editar", "Eliminar"};

        assert listPedidos != null;
        listPedidos.setModel(new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                int editarIndex = listPedidos.getColumnModel().getColumnIndex("Editar");
                int eliminarIndex = listPedidos.getColumnModel().getColumnIndex("Eliminar");
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
                Pedido p = controller.buscar(id);
                PedidosForm pedidosEditForm = new PedidosForm(p);
                AdminLayoutForm.getInstance().replaceContent(pedidosEditForm.getRootPanel());
            }
        };
        ButtonColumn buttonColumn = new ButtonColumn(listPedidos, delete, 6);
        ButtonColumn buttonColumn2 = new ButtonColumn(listPedidos, openEdit, 5);
        buttonColumn.setMnemonic(KeyEvent.VK_D);
    }

    public long getIdField() {
        if(idField.getText().isEmpty()) {
            return -1;
        }
        return Long.parseLong(idField.getText());
    }

    public ESTADO_PEDIDO getEstadoField() {
        return ESTADO_PEDIDO.valueOf(Objects.requireNonNull(estadoComboBox.getSelectedItem().toString()));
    }

    public String getVendedorField() {
        if (getVendedorId() == -1)
            return vendedorField.getText();
        else {
            return "";
        }
    }

    public long getVendedorId() {
        try {
            return Long.parseLong(vendedorField.getText());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public String getClienteField() {
        return nombreClienteField.getText();
    }

    public Double getPrecioMinField() {
        try {
            return Double.parseDouble(precioMinField.getText());
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    public Double getPrecioMaxField() {
        try {
            return Double.parseDouble(precioMaxField.getText());
        } catch (NumberFormatException e) {
            return Double.MAX_VALUE;
        }
    }

    public JPanel getRootPanel() {
        return panel1;
    }
}
