package deso.delivery_app.views.pedidos;

import deso.delivery_app.controllers.VendedorController;
import deso.delivery_app.models.Vendedor;
import deso.delivery_app.utils.Coordenada;
import deso.delivery_app.views.AdminLayoutForm;

import javax.swing.*;

public class PedidosCreateForm {
    private JTextField CUITField;
    private JTextField DireccionField;
    private JTextField NombreField;
    private JButton CancelButton;
    private JButton CreateButton;
    private JTextField latitudTextField;
    private JTextField longitudTextField;
    private JPanel content;
    private final VendedorController controller;

    public PedidosCreateForm() {
        //Fill fields
        controller = new VendedorController();
        // Add events to buttons
        CreateButton.addActionListener(e -> {
            String CUIT = CUITField.getText();
            String direccion = DireccionField.getText();
            String nombre = NombreField.getText();
            Coordenada c = new Coordenada(Double.parseDouble(latitudTextField.getText()), Double.parseDouble(longitudTextField.getText()));
            Vendedor v = new Vendedor(nombre, direccion, CUIT, c);
            controller.crear(v);
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
