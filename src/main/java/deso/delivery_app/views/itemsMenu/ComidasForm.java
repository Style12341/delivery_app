package deso.delivery_app.views.itemsMenu;

import deso.delivery_app.controllers.ItemMenuController;
import deso.delivery_app.models.Bebida;
import deso.delivery_app.models.Categoria;
import deso.delivery_app.models.Plato;
import deso.delivery_app.views.AdminLayoutForm;

import javax.swing.*;

public class ComidasForm {
    private JPanel content;
    private JTextField precioField;
    private JTextField idVendedorField;
    private JButton ActionButton;
    private JButton CancelButton;
    private JCheckBox veganoCheckBox;
    private JCheckBox celiacoCheckBox;
    private JTextArea descripcionField;
    private JTextField pesoField;
    private JTextField nombreField;
    private JLabel titleField;
    private final ItemMenuController controller;


    public ComidasForm() {
        controller = new ItemMenuController();
        // Add events to buttons
        titleField.setText("Crear Plato");
        ActionButton.setText("Crear");
        ActionButton.addActionListener(e -> {
            String nombre = getNombreField();
            String descripcion = getDescripcionField();
            boolean vegano = getVeganoCheckBox();
            boolean celiaco = getCeliacoCheckBox();
            double precio = getPrecioField();
            double peso = getPesoField();
            long idVendedor = getIdVendedorField();
            Plato p = new Plato(nombre, descripcion, precio, peso, vegano, celiaco);
            controller.crearPlato(p, idVendedor);
            JOptionPane.showMessageDialog(null, "Plato creado con éxito");
            backToIndex();
        });
        CancelButton.addActionListener(e -> {
            backToIndex();
        });
    }

    private long getIdVendedorField() {
        return Long.parseLong(idVendedorField.getText());
    }

    public ComidasForm(Plato p) {
        //Fill fields
        controller = new ItemMenuController();
        // Add events to buttons
        nombreField.setText(p.getNombre());
        descripcionField.setText(p.getDescripcion());
        veganoCheckBox.setSelected(p.aptoVegano());
        celiacoCheckBox.setSelected(p.aptoCeliaco());
        pesoField.setText(String.valueOf(p.peso()));
        precioField.setText(String.valueOf(p.getPrecio()));
        idVendedorField.setText(String.valueOf(p.getVendedor().getId()));
        titleField.setText("Modificar Plato");
        ActionButton.setText("Modificar");
        ActionButton.addActionListener(e -> {
            p.setNombre(getNombreField());
            p.setDescripcion(getDescripcionField());
            p.setPrecio(getPrecioField());
            p.setPeso(getPesoField());
            p.setVegano(getVeganoCheckBox());
            p.setCeliaco(getCeliacoCheckBox());
            controller.modificar(p);
            JOptionPane.showMessageDialog(null, "Plato modificado con éxito");
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

    public double getPesoField() {
        return Double.parseDouble(pesoField.getText());
    }

    public String getNombreField() {
        return nombreField.getText();
    }

    public String getDescripcionField() {
        return descripcionField.getText();
    }

    public double getPrecioField() {
        return Double.parseDouble(precioField.getText());
    }

    public boolean getVeganoCheckBox() {
        return veganoCheckBox.isSelected();
    }

    public boolean getCeliacoCheckBox() {
        return celiacoCheckBox.isSelected();
    }
}
