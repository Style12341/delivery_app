package deso.delivery_app.views.clientes;

import deso.delivery_app.controllers.ClienteController;
import deso.delivery_app.models.Cliente;
import deso.delivery_app.utils.Coordenada;
import deso.delivery_app.views.AdminLayoutForm;

import javax.swing.*;

public class ClientesEditForm {
    private JPanel content;
    private JTextField CUITField;
    private JTextField DireccionField;
    private JTextField NombreField;
    private JButton SaveButton;
    private JButton CancelButton;
    private JTextField latitudTextField;
    private JTextField longitudTextField;
    private JTextField ApellidoField;
    private JTextField EmailField;
    private ClienteController controller;

    public ClientesEditForm(Cliente c) {
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
        SaveButton.addActionListener(e -> {
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

    public JPanel getRootPanel() {
        return content;
    }

    private void backToIndex() {
        ClientesIndexForm vif = new ClientesIndexForm();
        AdminLayoutForm.getInstance().replaceContent(vif.getRootPanel());
    }
}
