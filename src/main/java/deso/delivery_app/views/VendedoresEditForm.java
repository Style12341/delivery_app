package deso.delivery_app.views;

import deso.delivery_app.models.Vendedor;
import deso.delivery_app.utils.Coordenada;

import javax.swing.*;

public class VendedoresEditForm {
    private JPanel content;
    private JTextField CUITField;
    private JTextField DireccionField;
    private JTextField NombreField;
    private JButton SaveButton;
    private JButton CancelButton;
    private JTextField latitudTextField;
    private JTextField longitudTextField;

    public VendedoresEditForm(Vendedor v) {
        //Fill fields
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
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(content);
            topFrame.dispose();
        });
        CancelButton.addActionListener(e -> {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(content);
            topFrame.dispose();
        });
    }

    public JPanel getRootPanel() {
        return content;
    }
}
