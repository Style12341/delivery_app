package deso.delivery_app.views.itemsMenu;

import deso.delivery_app.controllers.ItemMenuController;
import deso.delivery_app.models.ItemMenu;
import deso.delivery_app.utils.Coordenada;
import deso.delivery_app.views.AdminLayoutForm;

import javax.swing.*;

public class ItemsMenuEditForm {
    private JPanel content;
    private JTextField CUITField;
    private JTextField DireccionField;
    private JTextField NombreField;
    private JButton SaveButton;
    private JButton CancelButton;
    private JCheckBox veganoCheckBox;
    private JCheckBox celiacoCheckBox;
    private JComboBox categoriaComboBox;
    private JTextField latitudTextField;
    private JTextField longitudTextField;
    private ItemMenuController controller;

    public ItemsMenuEditForm(ItemMenu v) {
        //Fill fields
        controller = new ItemMenuController();
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
        ItemsMenuIndexForm vif = new ItemsMenuIndexForm();
        AdminLayoutForm.getInstance().replaceContent(vif.getRootPanel());
    }
}
