package deso.delivery_app.views.itemsMenu;

import deso.delivery_app.controllers.ItemMenuController;
import deso.delivery_app.models.Bebida;
import deso.delivery_app.views.AdminLayoutForm;

import javax.swing.*;

public class BebidasForm {
    private JPanel content;
    private JTextField NombreField;
    private JButton ActionButton;
    private JButton CancelButton;
    private JCheckBox veganoCheckBox;
    private JCheckBox celiacoCheckBox;
    private JTextArea descripcionField;
    private JLabel titleField;
    private JTextField volumenField;
    private JTextField precioField;
    private JTextField graduacionAlcoholicaField;
    private JCheckBox gaseosaCheckBox;
    private JTextField idVendedorField;
    private JTextField latitudTextField;
    private JTextField longitudTextField;
    private ItemMenuController controller;

    public BebidasForm() {
        controller = new ItemMenuController();
        // Add events to buttons
        titleField.setText("Crear Bebida");
        ActionButton.setText("Crear");
        ActionButton.addActionListener(e -> {
            String nombre = getNombreField();
            String descripcion = getDescripcionField();
            boolean vegano = getVeganoCheckBox();
            boolean celiaco = getCeliacoCheckBox();
            double precio = getPrecioField();
            double volumen = getVolumenField();
            double graduacionAlcoholica = getGraduacionAlcoholicaField();
            boolean esGaseosa = getEsGaseosaField();
            long idVendedor = getIdVendedorField();
            Bebida i = new Bebida(nombre, descripcion, precio, volumen, graduacionAlcoholica, esGaseosa, celiaco);
            i.setVegano(vegano);
            controller.crear(i, idVendedor);
            backToIndex();
        });
        CancelButton.addActionListener(e -> {
            backToIndex();
        });
    }

    private long getIdVendedorField() {
        return Long.parseLong(idVendedorField.getText());
    }

    public BebidasForm(Bebida b) {
        //Fill fields
        controller = new ItemMenuController();
        // Add events to buttons
        NombreField.setText(b.getNombre());
        descripcionField.setText(b.getDescripcion());
        veganoCheckBox.setSelected(b.aptoVegano());
        celiacoCheckBox.setSelected(b.aptoCeliaco());
        precioField.setText(String.valueOf(b.getPrecio()));
        volumenField.setText(String.valueOf(b.getVolumen()));
        graduacionAlcoholicaField.setText(String.valueOf(b.getGraduacionAlcoholica()));
        gaseosaCheckBox.setSelected(b.esGaseosa());
        idVendedorField.setText(String.valueOf(b.getVendedor().getId()));
        titleField.setText("Modificar Bebida");
        ActionButton.setText("Modificar");
        ActionButton.addActionListener(e -> {
            controller.modificar(b);
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

    public double getGraduacionAlcoholicaField() {
        return Double.parseDouble(graduacionAlcoholicaField.getText());
    }

    public boolean getEsGaseosaField() {
        return gaseosaCheckBox.isSelected();
    }

    public String getNombreField() {
        return NombreField.getText();
    }

    public String getDescripcionField() {
        return descripcionField.getText();
    }

    public double getPrecioField() {
        return Double.parseDouble(precioField.getText());
    }

    public double getVolumenField() {
        return Double.parseDouble(volumenField.getText());
    }

    public boolean getVeganoCheckBox() {
        return veganoCheckBox.isSelected();
    }

    public boolean getCeliacoCheckBox() {
        return celiacoCheckBox.isSelected();
    }

}
