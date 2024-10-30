package deso.delivery_app.views.itemsMenu;

import deso.delivery_app.TIPO_ITEM;
import deso.delivery_app.controllers.ItemMenuController;
import deso.delivery_app.controllers.VendedorController;
import deso.delivery_app.models.ItemMenu;
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

public class ItemsMenuIndexForm {
    private JPanel panel1;
    private JButton crearItemMenuButton;
    private JTextField NombreField;
    private JTable listItemsMenu;
    private JButton buscarButton;
    private JComboBox<String> categoriaComboBox;
    private JTextField precioMinField;
    private JTextField precioMaxField;
    private JCheckBox veganoCheck;
    private JTextField vendedorField;
    private JCheckBox celiacoCheck;
    private ItemMenuController controller;

    public ItemsMenuIndexForm() {
        controller = new ItemMenuController();
        createTable(controller.getLista());
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
                String vendedor = getVendedorField();
                long vendedor_id = getVendedorId();
                boolean vegano = getVeganoCheck();
                boolean celiaco = getCeliacoCheck();
                createTable(controller.getLista(nombreItem, categoria, precioMin, precioMax, vendedor_id, vendedor, celiaco, vegano));
            }
        });

        crearItemMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent _e) {
                ItemsMenuCreateForm itemsMenuCreateForm = new ItemsMenuCreateForm();
                AdminLayoutForm.getInstance().replaceContent(itemsMenuCreateForm.getRootPanel());
            }
        });
    }

    private void createComboBox() {
        categoriaComboBox.addItem("TODOS");
        categoriaComboBox.addItem("COMIDA");
        categoriaComboBox.addItem("BEBIDA");
    }

    public void createTable(List<ItemMenu> ims) {
        String[] columnNames = {"Id", "Nombre", "Precio", "Categoria", "Vegano", "Celiaco", "Nombre Vendedor", "Editar", "Eliminar"};
        Object[][] data = new Object[ims.size()][columnNames.length];
        for (int i = 0; i < ims.size(); i++) {
            ItemMenu im = ims.get(i);
            data[i][0] = im.getId();
            data[i][1] = im.getNombre();
            data[i][2] = im.getPrecio();
            data[i][3] = im.getCategoria();
            data[i][4] = im.aptoVegano();
            data[i][5] = im.aptoCeliaco();
            data[i][6] = im.getVendedor().getNombre();
            data[i][7] = "Editar";
            data[i][8] = "Eliminar";
        }

        assert listItemsMenu != null;
        listItemsMenu.setModel(new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                int editarIndex = listItemsMenu.getColumnModel().getColumnIndex("Editar");
                int eliminarIndex = listItemsMenu.getColumnModel().getColumnIndex("Eliminar");
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
                ItemMenu im = controller.buscar(id);
                ItemsMenuEditForm itemsMenuEditForm = new ItemsMenuEditForm(im);
                AdminLayoutForm.getInstance().replaceContent(itemsMenuEditForm.getRootPanel());
            }
        };
        ButtonColumn buttonColumn = new ButtonColumn(listItemsMenu, delete, 5);
        ButtonColumn buttonColumn2 = new ButtonColumn(listItemsMenu, openEdit, 4);
        buttonColumn.setMnemonic(KeyEvent.VK_D);

    }

    public JButton getBuscarBtn() {
        return buscarButton;
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
