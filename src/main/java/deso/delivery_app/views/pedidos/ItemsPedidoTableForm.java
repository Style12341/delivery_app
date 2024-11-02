package deso.delivery_app.views.pedidos;

import deso.delivery_app.ESTADO_PEDIDO;
import deso.delivery_app.TIPO_ITEM;
import deso.delivery_app.controllers.ItemMenuController;
import deso.delivery_app.controllers.ItemPedidoController;
import deso.delivery_app.controllers.PedidoController;
import deso.delivery_app.models.*;
import deso.delivery_app.views.AdminLayoutForm;
import deso.delivery_app.views.components.ButtonColumn;
import deso.delivery_app.views.itemsMenu.BebidasForm;
import deso.delivery_app.views.itemsMenu.ComidasForm;
import deso.delivery_app.views.itemsMenu.ItemMenuCategoryChooseForm;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.List;

public class ItemsPedidoTableForm{
    private JPanel rootPanel;
    private JTable itemsPedidoTable;
    private Pedido pedido;
    private Runnable onPriceChange;
    private final ItemPedidoController controller;

    public ItemsPedidoTableForm(Pedido p) {
        pedido = p;
        controller = new ItemPedidoController();
        createTable(controller.getLista(pedido));
    }

    public void createTable(List<ItemPedido> ims) {
        String[] columnNames = {"Id", "Nombre", "Precio", "Cantidad", "Total", "Eliminar"};
        Object[][] data = new Object[ims.size()][columnNames.length];
        for (int i = 0; i < ims.size(); i++) {
            ItemPedido ip = ims.get(i);
            ItemMenu im = ip.getItemMenu();
            data[i][0] = ip.getId();
            data[i][1] = im.getNombre();
            data[i][2] = String.format("$%.2f", im.getPrecio());
            data[i][3] = ip.getCantidad();
            data[i][4] = String.format("$%.2f", ip.getPrecioTotal());
            data[i][5] = "Eliminar";
        }

        assert itemsPedidoTable != null;
        itemsPedidoTable.setModel(new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                int eliminarIndex = itemsPedidoTable.getColumnModel().getColumnIndex("Eliminar");
                //If current row has estado pedido == enviado or preparado, disable delete
                return column == eliminarIndex && pedido.getEstado() != ESTADO_PEDIDO.ENVIADO && pedido.getEstado() != ESTADO_PEDIDO.PREPARADO;
            }
        });
        Action delete = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTable table = (JTable) e.getSource();
                int modelRow = Integer.parseInt(e.getActionCommand());
                long id = (long) table.getModel().getValueAt(modelRow, 0);
                pedido.removeItem(controller.buscar(id));
                controller.eliminar(id);
                ((DefaultTableModel) table.getModel()).removeRow(modelRow);
                if (onPriceChange != null) onPriceChange.run();
            }
        };
        int eliminarIndex = itemsPedidoTable.getColumnModel().getColumnIndex("Eliminar");
        ButtonColumn buttonColumn = new ButtonColumn(itemsPedidoTable, delete, eliminarIndex);
        buttonColumn.setMnemonic(KeyEvent.VK_D);

    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

    public void onPriceChange(Runnable updatePrice) {
        this.onPriceChange = updatePrice;
    }
}
