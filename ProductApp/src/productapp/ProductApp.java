package productapp;

import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ProductApp extends JFrame {

    private JTextField txtCustomer;
    private JTextField txtPrice;
    private JComboBox<String> cmbProduct;
    private JTextArea txtResults;

    private ProductCalculations calculations;

    public ProductApp() {

        calculations = new ProductCalculations();

        setTitle("Product App");
        setSize(600, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Menu bar
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenu toolsMenu = new JMenu("Tools");

        JMenuItem exitItem = new JMenuItem("Exit");
        JMenuItem processItem = new JMenuItem("Process");
        JMenuItem clearItem = new JMenuItem("Clear");

        fileMenu.add(exitItem);
        toolsMenu.add(processItem);
        toolsMenu.add(clearItem);

        menuBar.add(fileMenu);
        menuBar.add(toolsMenu);

        setJMenuBar(menuBar);

        // Main form
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        panel.add(new JLabel("Customer Information:"));
        txtCustomer = new JTextField();
        panel.add(txtCustomer);

        panel.add(new JLabel("Select Product:"));

        String[] products = {
            "Cozy Nights Monarch",
            "Restonic Barbados",
            "Sealy Phuket"
        };

        cmbProduct = new JComboBox<>(products);
        panel.add(cmbProduct);

        panel.add(new JLabel("Enter Product Price:"));
        txtPrice = new JTextField();
        panel.add(txtPrice);

        JButton btnProcess = new JButton("PROCESS");
        panel.add(btnProcess);

        JButton btnClear = new JButton("CLEAR");
        panel.add(btnClear);

        txtResults = new JTextArea(8, 30);
        txtResults.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(txtResults);

        add(panel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Button actions
        btnProcess.addActionListener(e -> processSale());
        processItem.addActionListener(e -> processSale());

        btnClear.addActionListener(e -> clearForm());
        clearItem.addActionListener(e -> clearForm());

        exitItem.addActionListener(e -> System.exit(0));
    }

    private void processSale() {

        String customer = txtCustomer.getText().trim();
        String product = (String) cmbProduct.getSelectedItem();

        if (customer.isEmpty()) {
            JOptionPane.showMessageDialog(
                this, "Please enter customer information.");
            return;
        }

        double price;

        try {
            price = Double.parseDouble(txtPrice.getText().trim());

            if (price <= 0) {
                JOptionPane.showMessageDialog(
                    this, "Price must be greater than zero.");
                return;
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                this, "Please enter a valid product price.");
            return;
        }

        double vat = calculations.getVat(price);
        double commission = calculations.getCommission(price);
        double total = calculations.getTotalSaleAmount(price);

        String report =
            "PRODUCT SALES REPORT\n" +
            "--------------------------------\n" +
            "Customer: " + customer + "\n" +
            "Product: " + product + "\n" +
            String.format("Price: R%.2f%n", price) +
            String.format("VAT (15.5%%): R%.2f%n", vat) +
            String.format("Commission (5%%): R%.2f%n", commission) +
            String.format("Total Sale Amount: R%.2f%n", total);

        txtResults.setText(report);

        // Save information to data.txt
        try (BufferedWriter writer =
                new BufferedWriter(new FileWriter("data.txt", true))) {

            writer.write("Date: " +
                LocalDateTime.now().format(
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

            writer.newLine();
            writer.write("Customer: " + customer);
            writer.newLine();
            writer.write("Product: " + product);
            writer.newLine();
            writer.write(String.format("Price: R%.2f", price));
            writer.newLine();
            writer.write(String.format("VAT: R%.2f", vat));
            writer.newLine();
            writer.write(String.format("Commission: R%.2f", commission));
            writer.newLine();
            writer.write(String.format("Total Sale Amount: R%.2f", total));
            writer.newLine();
            writer.write("--------------------------------");
            writer.newLine();

            JOptionPane.showMessageDialog(
                this, "Sale processed and saved to data.txt!");

        } catch (IOException e) {

            JOptionPane.showMessageDialog(
                this, "Error saving data: " + e.getMessage());
        }
    }

    private void clearForm() {

        txtCustomer.setText("");
        txtPrice.setText("");
        cmbProduct.setSelectedIndex(0);
        txtResults.setText("");

    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new ProductApp().setVisible(true);
        });
    }
}