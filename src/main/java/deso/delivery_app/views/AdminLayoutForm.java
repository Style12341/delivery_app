package deso.delivery_app.views;

import deso.delivery_app.views.clientes.ClientesIndexForm;
import deso.delivery_app.views.itemsMenu.ItemsMenuIndexForm;
import deso.delivery_app.views.pedidos.PedidosIndexForm;
import deso.delivery_app.views.vendedores.VendedoresIndexForm;

import javax.swing.*;
import java.awt.*;

enum PANEL {
    VENDEDOR,
    CLIENTE,
    ITEM_MENU,
    PEDIDO
}

public class AdminLayoutForm {
    private JPanel rootPanel;
    private JButton vendedoresButton;
    private JButton clientesButton;
    private JButton itemMenusButton;
    private JButton pedidosButton;
    private JPanel contentPanel;
    private JLabel titleField;
    private static AdminLayoutForm SINGLETON_INSTANCE;

    public static AdminLayoutForm getInstance() {
        if (SINGLETON_INSTANCE == null) {
            SINGLETON_INSTANCE = new AdminLayoutForm();
        }
        return SINGLETON_INSTANCE;
    }

    private AdminLayoutForm() {
        JFrame frame = new JFrame("Delivery_APP");
        frame.setContentPane(rootPanel);
        frame.setSize(1200, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // Set a layout manager for mainPanel
        contentPanel.setLayout(new BorderLayout());

        // Add button listeners
        vendedoresButton.addActionListener(e -> render(PANEL.VENDEDOR));
        //vendedoresButton.addActionListener(e -> replaceContent(new VendedoresIndexForm().getrootpanel));
        clientesButton.addActionListener(e -> render(PANEL.CLIENTE));
        itemMenusButton.addActionListener(e -> render(PANEL.ITEM_MENU));
        pedidosButton.addActionListener(e -> render(PANEL.PEDIDO));

        // Set minimum size for the frame
        frame.setMinimumSize(new Dimension(800, 600));

        // Center the frame on screen
        frame.setLocationRelativeTo(null);

        // Initial render
        render(PANEL.VENDEDOR);
        frame.setVisible(true);
    }

    public void replaceContent(JPanel panel) {
        contentPanel.removeAll();
        contentPanel.add(panel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void render(PANEL p) {
        // Clear the main panel
        contentPanel.removeAll();

        // Add the new content based on the selected panel
        switch (p) {
            case VENDEDOR:
                VendedoresIndexForm vendedoresForm = new VendedoresIndexForm();
                JPanel content = vendedoresForm.getRootPanel();
                titleField.setText("Vendedores");
                contentPanel.add(content, BorderLayout.CENTER);
                break;
            case CLIENTE:
                // Add cliente form when implemented
                ClientesIndexForm clientesForm = new ClientesIndexForm();
                titleField.setText("Clientes");
                contentPanel.add(clientesForm.getRootPanel(), BorderLayout.CENTER);
                break;
            case ITEM_MENU:
                // Add item menu form when implemented
                ItemsMenuIndexForm itemsMenuForm = new ItemsMenuIndexForm();
                titleField.setText("Items de Menú");
                contentPanel.add(itemsMenuForm.getRootPanel(), BorderLayout.CENTER);
                break;
            case PEDIDO:
                // Add pedido form when implemented
                PedidosIndexForm pedidosForm = new PedidosIndexForm();
                titleField.setText("Pedidos");
                contentPanel.add(pedidosForm.getRootPanel(), BorderLayout.CENTER);
                break;
        }

        // Refresh the panel
        contentPanel.revalidate();
        contentPanel.repaint();

        // Update the frame size if needed
    }
}