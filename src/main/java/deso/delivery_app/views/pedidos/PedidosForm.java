package deso.delivery_app.views.pedidos;

import deso.delivery_app.controllers.ClienteController;
import deso.delivery_app.controllers.PedidoController;
import deso.delivery_app.controllers.VendedorController;
import deso.delivery_app.models.Cliente;
import deso.delivery_app.models.Pedido;
import deso.delivery_app.models.Vendedor;
import deso.delivery_app.utils.Coordenada;
import deso.delivery_app.views.AdminLayoutForm;

import javax.swing.*;

public class PedidosForm {
    private JTextField ClienteField;
    private JTextField VendedorField;
    private JButton CancelButton;
    private JButton ActionButton;
    private JPanel content;
    private final VendedorController controllerV = new VendedorController();
    private final ClienteController controllerC = new ClienteController();
    private final PedidoController controllerP = new PedidoController();

    public PedidosForm() {
        //Fill fields

        // Add events to buttons
        ActionButton.setText("Crear");
        ActionButton.addActionListener(e -> {
            long vendedorId = Long.parseLong(VendedorField.getText());
            long clienteId = Long.parseLong(ClienteField.getText());
            Vendedor v = controllerV.buscar(vendedorId);
            Cliente c = controllerC.buscar(clienteId);
            if (v == null || c == null) {
                JOptionPane.showMessageDialog(null, "Vendedor o cliente no encontrado");
                return;
            }
            Pedido p = new Pedido();
            controllerP.crear(p, v, c);
            backToIndex();
        });
        CancelButton.addActionListener(e -> {
            backToIndex();
        });
    }
    private void backToIndex() {
        PedidosIndexForm vif = new PedidosIndexForm();
        AdminLayoutForm.getInstance().replaceContent(vif.getRootPanel());
    }

    public JPanel getRootPanel() {
        return content;
    }
}
