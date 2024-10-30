package deso.delivery_app.views.itemsMenu;

import deso.delivery_app.TIPO_ITEM;
import deso.delivery_app.controllers.ItemMenuController;
import deso.delivery_app.controllers.VendedorController;
import deso.delivery_app.models.ItemMenu;
import deso.delivery_app.models.Vendedor;
import deso.delivery_app.utils.Coordenada;
import deso.delivery_app.views.AdminLayoutForm;

import javax.swing.*;
import java.util.Objects;

public class ItemsMenuCreateForm {
    private JTextField CUITField;
    private JTextField DireccionField;
    private JTextField NombreField;
    private JButton CancelButton;
    private JButton CreateButton;
    private JTextField latitudTextField;
    private JTextField longitudTextField;
    private JPanel content;
    private JComboBox categoriaComboBox;
    private JCheckBox veganoCheckBox;
    private JCheckBox celiacoCheckBox;
    private JTextField idVendedorField;
    private final ItemMenuController controller;

    public ItemsMenuCreateForm() {
        //Fill fields
        controller = new ItemMenuController();
        // Add events to buttons
        CreateButton.addActionListener(e -> {
            TIPO_ITEM tipo = getCategoriaField();
            switch (tipo) {
                case BEBIDA:
                    break;
                case COMIDA:
                    break;
            }
            backToIndex();
        });
        CancelButton.addActionListener(e -> {
            backToIndex();
        });
    }

    private void backToIndex() {
        ItemsMenuIndexForm imif = new ItemsMenuIndexForm();
        AdminLayoutForm.getInstance().replaceContent(imif.getRootPanel());
    }

    public JPanel getRootPanel() {
        return content;
    }

    public TIPO_ITEM getCategoriaField() {
        // Get the enum of the selected item
        return TIPO_ITEM.valueOf(Objects.requireNonNull(categoriaComboBox.getSelectedItem()).toString());
    }

    public long getVendedorField() {
        return Long.parseLong(idVendedorField.getText());
    }


    public boolean getVeganoCheck() {
        return veganoCheckBox.isSelected();
    }

    public boolean getCeliacoCheck() {
        return celiacoCheckBox.isSelected();
    }
}
