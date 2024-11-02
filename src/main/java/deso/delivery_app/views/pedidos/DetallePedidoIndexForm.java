package deso.delivery_app.views.pedidos;

import deso.delivery_app.ESTADO_PEDIDO;
import deso.delivery_app.controllers.PedidoController;
import deso.delivery_app.models.Pedido;
import deso.delivery_app.views.AdminLayoutForm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private Pedido pedido;
    private static final String PRICE_FORMAT = "$%.2f";
    private double totalPrice;

    DetallePedidoIndexForm(Pedido p) {
        PedidoController pc = new PedidoController();
        pedido = p;
        setLabels(pedido);
        createComboBox(pedido);
        setComboBox(pedido);
        createDetallePedidoItemsTable(pedido);

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
                if(pedido.getEstado() != ESTADO_PEDIDO.ENVIADO && pedido.getEstado() != ESTADO_PEDIDO.PREPARADO) {
                    // TO-DO
                    // Renderizar algo que permita seleccionar items de menu del vendedor seleccionado para el pedido
                    // Sin poder filtrar sobre esa lista ni nada, simplemente seleccionar con una checkbox
                    // Y seleccionando la cantidad deseada en un input
                    // Después se itera sobre los items seleccionados y se crean los items pedido
                    // Se los agrega al pedido y se llamaria al update del dao supongo y se vuelve a renderizar esta vista de 0
                }
            }
        });
    }

    public void createDetallePedidoItemsTable(Pedido p) {
        ItemsPedidoTableForm itemsPedidoTableForm = new ItemsPedidoTableForm(p);
        itemsPedidoTableForm.onPriceChange(this::updatePrice);
        DetallePedidoItemsTablePanel.removeAll();
        DetallePedidoItemsTablePanel.add(itemsPedidoTableForm.getRootPanel(), BorderLayout.CENTER);
        DetallePedidoItemsTablePanel.revalidate();
        DetallePedidoItemsTablePanel.repaint();
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
