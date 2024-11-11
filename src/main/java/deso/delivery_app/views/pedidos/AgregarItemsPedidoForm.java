package deso.delivery_app.views.pedidos;

import deso.delivery_app.TIPO_ITEM;
import deso.delivery_app.controllers.ItemMenuController;
import deso.delivery_app.controllers.ItemPedidoController;
import deso.delivery_app.models.*;
import deso.delivery_app.views.AdminLayoutForm;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Objects;

public class AgregarItemsPedidoForm {
    private JTextField NombreField;
    private JTextField precioMinField;
    private JTextField precioMaxField;
    private JComboBox<String> categoriaComboBox;
    private JCheckBox veganoCheck;
    private JCheckBox celiacoCheck;
    private JTextField vendedorField;
    private JButton buscarButton;
    private JTable listItemsMenu;
    private JPanel panel1;
    private JButton anadirItemsButton;
    private JButton volverButton;
    private ItemMenuController itemMenuController;
    private Vendedor vendedor;
    private Pedido pedido;
    private ItemPedidoController itemPedidoController;

    public AgregarItemsPedidoForm(Pedido p) {
        vendedor = p.getVendedor();
        pedido = p;
        itemMenuController = new ItemMenuController();
        itemPedidoController = new ItemPedidoController();
        createTable(itemMenuController.getLista(vendedor.getId()));
        createComboBox();
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent _e) {
                System.out.println("Inside event handler for search button");
                // Extract fields and generate filters
                // id, nombre, precio, categoria, vendedor, vegano, celiaco
                String nombreItem = getNombreField();
                Double precioMin = getPrecioMinField();
                Double precioMax = getPrecioMaxField();
                TIPO_ITEM categoria = getCategoriaField();
                boolean vegano = getVeganoCheck();
                boolean celiaco = getCeliacoCheck();
                createTable(itemMenuController.getLista(nombreItem, categoria, precioMin, precioMax, vendedor.getId(), "", celiaco, vegano));
            }
        });
        anadirItemsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTable table = listItemsMenu;
                int total = 0;
                for (int i = 0; i < table.getRowCount(); i++) {
                    int cantidadColIdx = table.getColumnModel().getColumnIndex("Cantidad");
                    int cantidad = Integer.parseInt((String)table.getValueAt(i, cantidadColIdx));
                    if (cantidad > 0) {
                        total += cantidad;
                        long id = (long) table.getValueAt(i, 0);
                        itemPedidoController.crear(pedido.getId(), id, cantidad);
                    }
                }
                JOptionPane.showMessageDialog(null, Integer.toString(total) + " items añadidos al pedido");
                backToDetalle();
            }
        });

        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backToDetalle();
            }
        });
    }

    private void backToDetalle() {
        DetallePedidoIndexForm dpif = new DetallePedidoIndexForm(pedido);
        AdminLayoutForm.getInstance().replaceContent(dpif.getRootPanel());
    }

    private void createComboBox() {
        categoriaComboBox.addItem("TODOS");
        categoriaComboBox.addItem("COMIDA");
        categoriaComboBox.addItem("BEBIDA");
    }

    public void createTable(List<ItemMenu> ims) {
        String[] columnNames = {"Id","Nombre", "Precio", "Categoria", "Vegano", "Celiaco", "Cantidad"};
        Object[][] data = new Object[ims.size()][columnNames.length];
        for (int i = 0; i < ims.size(); i++) {
            ItemMenu im = ims.get(i);
            data[i][0] = im.getId();
            data[i][1] = im.getNombre();
            data[i][2] = im.getPrecio();
            data[i][3] = im.getCategoria().getTipoItem().name();
            data[i][4] = im.aptoVegano() ? "SI" : "NO";
            data[i][5] = im.aptoCeliaco() ? "SI" : "NO";
            data[i][6] = "0";
        }

        assert listItemsMenu != null;
        listItemsMenu.setModel(new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                int cantidadIndex = listItemsMenu.getColumnModel().getColumnIndex("Cantidad");
                return column == cantidadIndex;
            }
        });


    }

    public String getNombreField() {
        return NombreField.getText();
    }

    public JPanel getRootPanel() {
        return panel1;
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

    public TIPO_ITEM getCategoriaField() {
        // Get the enum of the selected item
        return TIPO_ITEM.valueOf(Objects.requireNonNull(categoriaComboBox.getSelectedItem()).toString());
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

    public boolean getVeganoCheck() {
        return veganoCheck.isSelected();
    }

    public boolean getCeliacoCheck() {
        return celiacoCheck.isSelected();
    }
}
