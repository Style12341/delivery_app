package deso.delivery_app.views.clientes;

import deso.delivery_app.controllers.ClienteController;
import deso.delivery_app.models.Cliente;
import deso.delivery_app.utils.Coordenada;
import deso.delivery_app.views.AdminLayoutForm;

import javax.swing.*;

public class ClientesCreateForm {
    private JTextField CUITField;
    private JTextField DireccionField;
    private JTextField NombreField;
    private JButton CancelButton;
    private JButton CreateButton;
    private JTextField latitudTextField;
    private JTextField longitudTextField;
    private JPanel content;
    private JTextField ApellidoField;
    private JTextField EmailField;
    private final ClienteController controller;

    public ClientesCreateForm() {
        //Fill fields
        controller = new ClienteController();
        // Add events to buttons
        CreateButton.addActionListener(e -> {
            String CUIT = CUITField.getText();
            String direccion = DireccionField.getText();
            String nombre = NombreField.getText();
            String apellido = ApellidoField.getText();
            String email = EmailField.getText();
            Coordenada coord = new Coordenada(Double.parseDouble(latitudTextField.getText()), Double.parseDouble(longitudTextField.getText()));
            Cliente c = new Cliente(nombre, apellido, CUIT, email, direccion, coord);
            controller.crear(c);
            backToIndex();
        });
        CancelButton.addActionListener(e -> {
            backToIndex();
        });
    }

    private void backToIndex() {
        ClientesIndexForm vif = new ClientesIndexForm();
        AdminLayoutForm.getInstance().replaceContent(vif.getRootPanel());
    }

    public JPanel getRootPanel() {
        return content;
    }
}
