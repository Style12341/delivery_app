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
        // Add events to buttons
        SaveButton.addActionListener(e -> {
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
