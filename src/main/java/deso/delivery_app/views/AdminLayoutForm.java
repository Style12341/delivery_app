package deso.delivery_app.views;

import javax.swing.*;
import java.awt.*;

enum PANEL {
    VENDEDOR,
    CLIENTE,
    ITEM_MENU,
    PEDIDO
}

public class AdminLayoutForm {
    private JPanel panel1;
    private JButton vendedoresButton;
    private JButton clientesButton;
    private JButton itemMenusButton;
    private JButton pedidosButton;
    private JPanel mainPanel;

    public AdminLayoutForm() {
        JFrame frame = new JFrame("Delivery_APP");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set a layout manager for mainPanel
        mainPanel.setLayout(new BorderLayout());

        // Add button listeners
        vendedoresButton.addActionListener(e -> render(PANEL.VENDEDOR));
        clientesButton.addActionListener(e -> render(PANEL.CLIENTE));
        itemMenusButton.addActionListener(e -> render(PANEL.ITEM_MENU));
        pedidosButton.addActionListener(e -> render(PANEL.PEDIDO));

        // Set minimum size for the frame
        frame.setMinimumSize(new Dimension(800, 600));

        // Center the frame on screen
        frame.setLocationRelativeTo(null);

        // Initial render
        render(PANEL.VENDEDOR);

        frame.pack();
        frame.setVisible(true);
    }

    private void render(PANEL p) {
        // Clear the main panel
        mainPanel.removeAll();

        // Add the new content based on the selected panel
        switch (p) {
            case VENDEDOR:
                VendedoresIndexForm vendedoresForm = new VendedoresIndexForm();
                JPanel content = vendedoresForm.getRootPanel();
                mainPanel.add(content, BorderLayout.CENTER);
                break;
            case CLIENTE:
                // Add cliente form when implemented
                mainPanel.add(new JLabel("Cliente panel - To be implemented"), BorderLayout.CENTER);
                break;
            case ITEM_MENU:
                // Add item menu form when implemented
                mainPanel.add(new JLabel("Item Menu panel - To be implemented"), BorderLayout.CENTER);
                break;
            case PEDIDO:
                // Add pedido form when implemented
                mainPanel.add(new JLabel("Pedido panel - To be implemented"), BorderLayout.CENTER);
                break;
        }

        // Refresh the panel
        mainPanel.revalidate();
        mainPanel.repaint();

        // Update the frame size if needed
        SwingUtilities.getWindowAncestor(mainPanel).pack();
    }
}