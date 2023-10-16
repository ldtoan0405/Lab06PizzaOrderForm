import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class PizzaGUIFrame extends JFrame implements ActionListener {
    private JRadioButton thinCrustRadio, regularCrustRadio, deepDishCrustRadio;
    private JComboBox<String> sizeComboBox;
    private JCheckBox[] toppingsCheckBoxes;
    private JTextArea receiptTextArea;

    private JButton orderButton, clearButton, quitButton;

    public PizzaGUIFrame() {
        // Set up the frame
        setLayout(new BorderLayout());

        // Create panels
        JPanel crustPanel = createTitledPanel("Crust Type");
        JPanel sizePanel = createTitledPanel("Pizza Size");
        JPanel toppingsPanel = createTitledPanel("Toppings");
        JPanel receiptPanel = createTitledPanel("Receipt");

        // Initialize components
        thinCrustRadio = new JRadioButton("Thin");
        regularCrustRadio = new JRadioButton("Regular");
        deepDishCrustRadio = new JRadioButton("Deep-dish");

        ButtonGroup crustGroup = new ButtonGroup();
        crustGroup.add(thinCrustRadio);
        crustGroup.add(regularCrustRadio);
        crustGroup.add(deepDishCrustRadio);

        String[] sizes = {"Small", "Medium", "Large", "Super"};
        sizeComboBox = new JComboBox<>(sizes);

        toppingsCheckBoxes = new JCheckBox[6];
        for (int i = 0; i < 6; i++) {
            toppingsCheckBoxes[i] = new JCheckBox("Topping " + (i + 1));
        }

        receiptTextArea = new JTextArea(10, 30);
        receiptTextArea.setEditable(false);
        JScrollPane receiptScrollPane = new JScrollPane(receiptTextArea);

        orderButton = new JButton("Order");
        clearButton = new JButton("Clear");
        quitButton = new JButton("Quit");

        // Add action listeners
        orderButton.addActionListener(this);
        clearButton.addActionListener(this);
        quitButton.addActionListener(this);

        // Add components to panels
        crustPanel.add(thinCrustRadio);
        crustPanel.add(regularCrustRadio);
        crustPanel.add(deepDishCrustRadio);

        sizePanel.add(sizeComboBox);

        for (JCheckBox checkBox : toppingsCheckBoxes) {
            toppingsPanel.add(checkBox);
        }

        receiptPanel.add(receiptScrollPane);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(orderButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(quitButton);

        // Add panels to the frame
        add(crustPanel, BorderLayout.NORTH);
        add(sizePanel, BorderLayout.WEST);
        add(toppingsPanel, BorderLayout.CENTER);
        add(receiptPanel, BorderLayout.EAST);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
    }

    private JPanel createTitledPanel(String title) {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder(title));
        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == orderButton) {
            // Handle order button click
            String crustType = getSelectedCrust();
            String size = (String) sizeComboBox.getSelectedItem();
            ArrayList<String> selectedToppings = getSelectedToppings();

            if (crustType == null || size == null || selectedToppings.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please select crust, size, and at least one topping.",
                        "Incomplete Order", JOptionPane.WARNING_MESSAGE);
            } else {
                double baseCost = getBaseCost(size);
                double toppingCost = selectedToppings.size() * 1.00;
                double subTotal = baseCost + toppingCost;
                double tax = 0.07 * subTotal;
                double total = subTotal + tax;

                // Display the receipt in the JTextArea
                receiptTextArea.setText(
                        "=========================================\n" +
                                "Type of Crust & Size\tPrice\n" +
                                crustType + " - " + size + "\t$" + baseCost + "\n\n" +
                                "Ingredients\t\tPrice\n" +
                                getSelectedToppingsAsString(selectedToppings) + "\n\n" +
                                "Sub-total:\t$" + subTotal + "\n" +
                                "Tax:\t$" + tax + "\n" +
                                "-----------------------------------------\n" +
                                "Total:\t$" + total + "\n" +
                                "========================================="
                );
            }
        } else if (e.getSource() == clearButton) {
            // Handle clear button click
            clearForm();
        } else if (e.getSource() == quitButton) {
            // Handle quit button click
            int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to quit?", "Confirm Quit",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (response == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }

    // Helper method to get selected crust type
    private String getSelectedCrust() {
        if (thinCrustRadio.isSelected()) {
            return "Thin";
        } else if (regularCrustRadio.isSelected()) {
            return "Regular";
        } else if (deepDishCrustRadio.isSelected()) {
            return "Deep-dish";
        } else {
            return null; // No crust selected
        }
    }

    // Helper method to get base cost based on selected size
    private double getBaseCost(String size) {
        switch (size) {
            case "Small":
                return 8.00;
            case "Medium":
                return 12.00;
            case "Large":
                return 16.00;
            case "Super":
                return 20.00;
            default:
                return 0.00;
        }
    }

    // Helper method to get selected toppings as a formatted string
    private String getSelectedToppingsAsString(ArrayList<String> selectedToppings) {
        StringBuilder toppingsString = new StringBuilder();
        for (String topping : selectedToppings) {
            toppingsString.append(topping).append("\t$1.00\n");
        }
        return toppingsString.toString();
    }

    // Helper method to get list of selected toppings
    private ArrayList<String> getSelectedToppings() {
        ArrayList<String> selectedToppings = new ArrayList<>();
        for (JCheckBox checkBox : toppingsCheckBoxes) {
            if (checkBox.isSelected()) {
                selectedToppings.add(checkBox.getText());
            }
        }
        return selectedToppings;
    }

    // Helper method to clear the form
    private void clearForm() {
        thinCrustRadio.setSelected(false);
        regularCrustRadio.setSelected(false);
        deepDishCrustRadio.setSelected(false);
        sizeComboBox.setSelectedIndex(0);

        for (JCheckBox checkBox : toppingsCheckBoxes) {
            checkBox.setSelected(false);
        }

        receiptTextArea.setText("");
    }


}
