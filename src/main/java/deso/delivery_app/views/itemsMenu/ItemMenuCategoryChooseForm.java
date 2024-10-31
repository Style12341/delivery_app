package deso.delivery_app.views.itemsMenu;

import deso.delivery_app.views.AdminLayoutForm;

import javax.swing.*;
import java.awt.event.*;

public class ItemMenuCategoryChooseForm extends JDialog {
    private JPanel contentPane;
    private JButton buttonCancel;
    private JButton comidaButton;
    private JButton bebidaButton;

    public ItemMenuCategoryChooseForm() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonCancel);


        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });
        bebidaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AdminLayoutForm.getInstance().replaceContent(new BebidasForm().getRootPanel());
                onCancel();
            }
        });
        comidaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AdminLayoutForm.getInstance().replaceContent(new ComidasForm().getRootPanel());
                onCancel();
            }
        });
        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onCancel() {
        dispose();
    }
}
