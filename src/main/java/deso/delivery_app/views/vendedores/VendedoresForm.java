package deso.delivery_app.views.vendedores;

import deso.delivery_app.controllers.VendedorController;
import deso.delivery_app.models.Vendedor;
import deso.delivery_app.utils.Coordenada;
import deso.delivery_app.views.AdminLayoutForm;

import javax.swing.*;

public class VendedoresForm {
    private JTextField CUITField;
    private JTextField DireccionField;
    private JTextField NombreField;
    private JButton CancelButton;
    private JButton ActionButton;
    private JTextField latitudTextField;
    private JTextField longitudTextField;
    private JPanel content;
    private final VendedorController controller;

    public VendedoresForm() {
        //Fill fields
        controller = new VendedorController();
        // Add events to buttons
        ActionButton.setText("Crear");
        ActionButton.addActionListener(e -> {
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
    public VendedoresForm(Vendedor v){
        //Fill fields
        controller = new VendedorController();
        CUITField.setText(v.getCuit());
        DireccionField.setText(v.getDireccion());
        NombreField.setText(v.getNombre());
        Coordenada c = v.getCoordenadas();
        latitudTextField.setText(String.valueOf(c.getLat()));
        longitudTextField.setText(String.valueOf(c.getLng()));
        // Add events to buttons
        ActionButton.setText("Guardar");
        ActionButton.addActionListener(e -> {
            v.setCuit(CUITField.getText());
            v.setDireccion(DireccionField.getText());
            v.setNombre(NombreField.getText());
            v.setCoordenadas(new Coordenada(Double.parseDouble(latitudTextField.getText()), Double.parseDouble(longitudTextField.getText())));
            controller.modificar(v);
            backToIndex();
        });
        CancelButton.addActionListener(e -> {
            backToIndex();
        });
    }

    private void backToIndex() {
        VendedoresIndexForm vif = new VendedoresIndexForm();
        AdminLayoutForm.getInstance().replaceContent(vif.getRootPanel());
    }

    public JPanel getRootPanel() {
        return content;
    }
}
