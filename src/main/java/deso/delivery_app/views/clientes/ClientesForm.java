package deso.delivery_app.views.clientes;

import deso.delivery_app.controllers.ClienteController;
import deso.delivery_app.models.Cliente;
import deso.delivery_app.utils.Coordenada;
import deso.delivery_app.views.AdminLayoutForm;

import javax.swing.*;

public class ClientesForm {
    private JTextField CUITField;
    private JTextField DireccionField;
    private JTextField NombreField;
    private JButton CancelButton;
    private JButton ActionButton;
    private JTextField latitudTextField;
    private JTextField longitudTextField;
    private JPanel content;
    private JTextField ApellidoField;
    private JTextField EmailField;
    private final ClienteController controller;

    public ClientesForm() {
        //Fill fields
        controller = new ClienteController();
        // Add events to buttons
        ActionButton.setText("Crear");
        ActionButton.addActionListener(e -> {
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
    public ClientesForm(Cliente c) {
        //Fill fields
        controller = new ClienteController();
        CUITField.setText(c.getCuit());
        DireccionField.setText(c.getDireccion());
        NombreField.setText(c.getNombre());
        ApellidoField.setText(c.getApellido());
        EmailField.setText(c.getEmail());
        Coordenada coord = c.getCoordenadas();
        latitudTextField.setText(String.valueOf(coord.getLat()));
        longitudTextField.setText(String.valueOf(coord.getLng()));
        // Add events to buttons
        ActionButton.setText("Guardar");
        ActionButton.addActionListener(e -> {
            c.setCuit(CUITField.getText());
            c.setDireccion(DireccionField.getText());
            c.setNombre(NombreField.getText());
            c.setEmail(EmailField.getText());
            c.setApellido(ApellidoField.getText());
            c.setCoordenadas(new Coordenada(Double.parseDouble(latitudTextField.getText()), Double.parseDouble(longitudTextField.getText())));
            controller.modificar(c);
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
