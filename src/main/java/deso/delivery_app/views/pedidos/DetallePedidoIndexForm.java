package deso.delivery_app.views.pedidos;

import deso.delivery_app.ESTADO_PEDIDO;
import deso.delivery_app.controllers.ItemPedidoController;
import deso.delivery_app.controllers.PedidoController;
import deso.delivery_app.models.ItemMenu;
import deso.delivery_app.models.ItemPedido;
import deso.delivery_app.models.Pedido;
import deso.delivery_app.views.AdminLayoutForm;
import deso.delivery_app.views.components.ButtonColumn;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.List;

public class DetallePedidoIndexForm {
    private JPanel panel1;
    private JLabel vendedorLabel;
    private JLabel clienteLabel;
    private JLabel precioTotalLabel;
    private JPanel DetallePedidoItemsTablePanel;
    private JButton volverButton;
    private JButton agregarItemsButton;
    private JLabel titleLabel;
    private JComboBox<String> estadoComboBox;
    private JPanel rootDetalleTablePanel;
    private JTable itemsPedidoTable;
    private Pedido pedido;
    private ItemPedidoController controller;
    private static final String PRICE_FORMAT = "$%.2f";
    private double totalPrice;

    DetallePedidoIndexForm(Pedido p) {
        PedidoController pc = new PedidoController();
        pedido = p;
        setLabels(pedido);
        createComboBox(pedido);
        setComboBox(pedido);
        controller = new ItemPedidoController();
        createDetallePedidoItemsTable(controller.getLista(pedido));

        estadoComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox) e.getSource();
                String estado = (String) cb.getSelectedItem();
                PedidoController pedidoController = new PedidoController();
                pedidoController.cambiarEstado(pedido, ESTADO_PEDIDO.valueOf(estado));
            }
        });
        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backToIndex();
            }
        });
        agregarItemsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pedido.getEstado() != ESTADO_PEDIDO.ENVIADO && pedido.getEstado() != ESTADO_PEDIDO.PREPARADO) {
                    agregarItemsAction();
                } else {
                    JOptionPane.showMessageDialog(null, "No se pueden agregar items a un pedido que ya fue enviado o preparado");
                }
            }
        });

    }

    private void agregarItemsAction() {
        //Add as child of this form a new form to add items to the pedido
        AgregarItemsPedidoForm aipf = new AgregarItemsPedidoForm(pedido);
        AdminLayoutForm.getInstance().replaceContent(aipf.getRootPanel());

    }

    public void createDetallePedidoItemsTable(List<ItemPedido> ims) {
        String[] columnNames = {"Id", "Nombre", "Precio", "Cantidad", "Total", "Eliminar"};
        Object[][] data = new Object[ims.size()][columnNames.length];
        for (int i = 0; i < ims.size(); i++) {
            ItemPedido ip = ims.get(i);
            ItemMenu im = ip.getItemMenu();
            data[i][0] = ip.getPedido().getId() + "-" + im.getId();
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
                String idComp = (String) table.getModel().getValueAt(modelRow, 0);
                long idPedido = Long.parseLong(idComp.split("-")[0]);
                long idItemMenu = Long.parseLong(idComp.split("-")[1]);

                pedido.removeItem(controller.buscar(idPedido, idItemMenu));
                controller.eliminar(idPedido, idItemMenu);
                ((DefaultTableModel) table.getModel()).removeRow(modelRow);
                updatePrice();
            }
        };
        int eliminarIndex = itemsPedidoTable.getColumnModel().getColumnIndex("Eliminar");
        ButtonColumn buttonColumn = new ButtonColumn(itemsPedidoTable, delete, eliminarIndex);
        buttonColumn.setMnemonic(KeyEvent.VK_D);
    }

    public void setLabels(Pedido p) {
        setVendedorLabel(p);
        setClienteLabel(p);
        setPrecioTotalLabel(p);
        setTituloLabel(p);
    }

    public void updatePrice() {
        setPrecioTotalLabel(pedido);
    }

    public void createComboBox(Pedido p) {
        estadoComboBox.addItem(ESTADO_PEDIDO.RECIBIDO.toString());
        estadoComboBox.addItem(ESTADO_PEDIDO.ACEPTADO.toString());
        estadoComboBox.addItem(ESTADO_PEDIDO.PREPARADO.toString());
        estadoComboBox.addItem(ESTADO_PEDIDO.ENVIADO.toString());
        setComboBox(p);
    }

    public void setComboBox(Pedido p) {
        estadoComboBox.setSelectedItem(p.getEstado().toString());
    }

    public void setVendedorLabel(Pedido p) {
        vendedorLabel.setText(p.getVendedor().getNombre());
    }

    public void setClienteLabel(Pedido p) {
        clienteLabel.setText(p.getCliente().getNombre());
    }

    public void setPrecioTotalLabel(Pedido p) {
        precioTotalLabel.setText(String.format("$%.2f", p.getPrecioAcumulado()));
    }

    public void setTituloLabel(Pedido p) {
        titleLabel.setText("Pedido #" + p.getId());
    }

    private void backToIndex() {
        PedidosIndexForm vif = new PedidosIndexForm();
        AdminLayoutForm.getInstance().replaceContent(vif.getRootPanel());
    }

    public JPanel getRootPanel() {
        return panel1;
    }
}
