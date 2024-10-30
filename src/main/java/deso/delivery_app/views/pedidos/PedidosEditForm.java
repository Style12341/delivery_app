package deso.delivery_app.views.pedidos;

import deso.delivery_app.controllers.VendedorController;
import deso.delivery_app.models.Vendedor;
import deso.delivery_app.utils.Coordenada;
import deso.delivery_app.views.AdminLayoutForm;

import javax.swing.*;

public class PedidosEditForm {
    private JPanel content;
    private JTextField CUITField;
    private JTextField DireccionField;
    private JTextField NombreField;
    private JButton SaveButton;
    private JButton CancelButton;
    private JTextField latitudTextField;
    private JTextField longitudTextField;
    private VendedorController controller;

    public PedidosEditForm(Vendedor v) {
        //Fill fields
        controller = new VendedorController();
        CUITField.setText(v.getCuit());
        DireccionField.setText(v.getDireccion());
        NombreField.setText(v.getNombre());
        Coordenada c = v.getCoordenadas();
        latitudTextField.setText(String.valueOf(c.getLat()));
        longitudTextField.setText(String.valueOf(c.getLng()));
        // Add events to buttons
        SaveButton.addActionListener(e -> {
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

    public JPanel getRootPanel() {
        return content;
    }

    private void backToIndex() {
        PedidosIndexForm vif = new PedidosIndexForm();
        AdminLayoutForm.getInstance().replaceContent(vif.getRootPanel());
    }
}
