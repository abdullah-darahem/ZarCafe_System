package ui;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
/**
 *
 * @author Rawan
 */
import db.DBConnection;
import java.awt.BasicStroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import javax.swing.text.JTextComponent;

// --- مكتبات جديدة عشان الصور والتصميم ---
import javax.swing.ImageIcon;
import java.awt.Image;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.Box;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Font;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.JTextField;

import db.DBConnection;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalDate;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;

public class D__User_ORDERS extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(D__User_ORDERS.class.getName());

    /**
     * Creates new form NewJFrame
     */
    // Declare a table model for the receipt table
// Declares a table model to manage data shown in the receipt table.
    DefaultTableModel receitmodel;

// Stores the current balance of the customer.
    double currentBalance = 0.0;
    String userPhone;

// Constructor that receives the customer's phone number.
    public D__User_ORDERS(String phone) {

        // Initializes all GUI components created by the form designer.
        initComponents();
        this.userPhone = phone;

        // Centers the application window on the screen.
        setLocationRelativeTo(null);

        // Gets the table model used by the receipt table.
        receitmodel = (DefaultTableModel) tbl_receipt.getModel();

// Sets the  style
        tbl_receipt.setRowHeight(30);
        tbl_receipt.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tbl_receipt.getTableHeader().setBackground(new Color(102, 51, 0));
        tbl_receipt.getTableHeader().setForeground(Color.WHITE);

        styleField(txt_custName, "gold");
        styleField(txt_custPhone, "gold");
        styleField(txt_balance, "red");

        styleButton(btn_saveOrder);
        styleButton(btn_reset);
        styleButton(jButton3);
        styleButton(btn_delete);

        // Loads customer information and balance using the phone number.
        loadUserData(phone);

        // Loads menu items and their images from the database.
        loadMenuFromDB();

    }

// This method loads menu items from the database and displays them as cards
    private void loadMenuFromDB() {
        // Remove all existing components from the menu panel
        // This clears any old menu items before loading new ones
        pnlMenuItems.removeAll();

        // Start a try block to handle potential database errors
        try {
            // Establish a connection to the database
            Connection con = DBConnection.getConnection();

            // SQL query to retrieve available menu items.
            // SELECT *              → selects all columns.
            // FROM Menu_Items       → reads data from the Menu_Items table.
            // WHERE IS_AVAILABLE=1  → returns only items marked as available.
            String sql = "SELECT * FROM Menu_Items WHERE IS_AVAILABLE = 1";

            // Create a statement object to execute the SQL query
            Statement st = con.createStatement();

            // Execute the query and store the results
            ResultSet rs = st.executeQuery(sql);

            // Loop through each row in the result set
            while (rs.next()) {
                // Extract the item name from the current database row
                String name = rs.getString("ITEM_NAME");

                // Extract the item price from the current database row
                int price = rs.getInt("PRICE");

                // Extract the image file path from the current database row
                String imgPath = rs.getString("IMAGE_PATH");

                // Create a new panel to represent this menu item as a card
                JPanel card = new JPanel();

                // Set the card background color to white
                card.setBackground(Color.WHITE);

                // Add a light gray border around the card
                // The border is 1 pixel thick with RGB color (220, 220, 220)
                card.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));

                // Use vertical box layout to stack components from top to bottom
                card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

                // Set the preferred size of the card to 170 pixels wide, 250 pixels tall
                card.setPreferredSize(new Dimension(170, 250));

                // Create a label to display the item image
                JLabel lblImage = new JLabel();

                // Center align the image within the card
                lblImage.setAlignmentX(Component.CENTER_ALIGNMENT);

                // Try to load and display the item image
                try {
                    // Check if an image path exists and is not empty
                    if (imgPath != null && !imgPath.isEmpty()) {
                        // Create an image icon from the file path
                        ImageIcon icon = new ImageIcon(imgPath);

                        // Resize the image to 140 pixels wide, 110 pixels tall
                        // Use smooth scaling for better image quality
                        Image img = icon.getImage().getScaledInstance(140, 110, Image.SCALE_SMOOTH);

                        // Set the resized image as the label's icon
                        lblImage.setIcon(new ImageIcon(img));
                    } else {
                        // If no image path exists, display text instead
                        lblImage.setText("No Image");

                        // Set the text font to Segoe UI, bold, size 12
                        lblImage.setFont(new Font("Segoe UI", Font.BOLD, 12));
                    }
                } catch (Exception e) {
                    // If image loading fails, display error text
                    lblImage.setText("Err Img");
                }

                // Create a label to display the item name
                JLabel lblName = new JLabel(name);

                // Style the name label with dark brown text
                lblName.setFont(new Font("Segoe UI", Font.BOLD, 15));
                lblName.setForeground(new Color(102, 51, 0));

                // Center align the name within the card
                lblName.setAlignmentX(Component.CENTER_ALIGNMENT);

                // Create a label to display the item price
                JLabel lblPrice = new JLabel(price + " LE");

                // Style the price label with dark gray text
                lblPrice.setFont(new Font("Segoe UI", Font.PLAIN, 13));
                lblPrice.setForeground(Color.DARK_GRAY);

                // Center align the price within the card
                lblPrice.setAlignmentX(Component.CENTER_ALIGNMENT);

                // Create a button to add this item to the shopping cart
                JButton btnAdd = new JButton("Add to Cart");

                // Set the button background to brown color
                btnAdd.setBackground(new Color(165, 117, 68));

                // Set the button text color to white
                btnAdd.setForeground(Color.WHITE);

                // Remove the focus border when button is clicked
                btnAdd.setFocusPainted(false);

                // Change cursor to hand pointer when hovering over button
                btnAdd.setCursor(new Cursor(Cursor.HAND_CURSOR));

                // Center align the button within the card
                btnAdd.setAlignmentX(Component.CENTER_ALIGNMENT);

                // Add a click event listener to the button
                // When clicked, call addItem method with the item name and price
                btnAdd.addActionListener(e -> addItem(name, price));

                // Begin assembling the card components
                // Add 10 pixels of vertical space at the top of the card
                card.add(Box.createVerticalStrut(10));

                // Add the image label
                card.add(lblImage);

                // Add 5 pixels of vertical space after the image
                card.add(Box.createVerticalStrut(5));

                // Add the item name label
                card.add(lblName);

                // Add the item price label
                card.add(lblPrice);

                // Add 5 pixels of vertical space after the price
                card.add(Box.createVerticalStrut(5));

                // Add the "Add to Cart" button
                card.add(btnAdd);

                // Add 10 pixels of vertical space at the bottom of the card
                card.add(Box.createVerticalStrut(10));

                // Add the completed card to the menu items panel
                pnlMenuItems.add(card);
            }

            // Close the database connection to free resources
            con.close();

            // Recalculate the layout of the menu panel
            // This is necessary after adding new components
            pnlMenuItems.revalidate();

            // Redraw the menu panel to show the new cards
            pnlMenuItems.repaint();

        } catch (Exception e) {
            // If any error occurs, show a message dialog to the user
            // Display the error message for debugging purposes
            JOptionPane.showMessageDialog(this, "Error loading menu: " + e.getMessage());
        }
    }

// This method adds an item to the receipt (shopping cart) table
// It checks if the item already exists in the receipt and either:
// 1. Increases the quantity if the item is already in the receipt
// 2. Adds a new row if the item is not in the receipt
    private void addItemToReceipt(String itemName, int price) {
        // Create a flag to track whether the item was found in the receipt
        // Start with false since we haven't found it yet
        boolean found = false;

        // Loop through each row in the receipt table
        // Start from row 0 and continue to the last row
        for (int i = 0; i < receitmodel.getRowCount(); i++) {
            // Get the item name from the first column (column 0) of current row
            String existingName = receitmodel.getValueAt(i, 0).toString();

            // Check if this row contains the item we want to add
            // Use equalsIgnoreCase to match regardless of uppercase/lowercase
            if (existingName.equalsIgnoreCase(itemName)) {
                // Item found in the receipt
                // Get the current quantity from the second column (column 1)
                int oldQ = Integer.parseInt(receitmodel.getValueAt(i, 1).toString());

                // Increase the quantity by 1
                int newQ = oldQ + 1;

                // Update the quantity in the table (column 1)
                receitmodel.setValueAt(newQ, i, 1);

                // Calculate new total price: new quantity × price per item
                // Update the total price in the table (column 2)
                receitmodel.setValueAt(newQ * price, i, 2);

                // Set the flag to true since we found and updated the item
                found = true;

                // Exit the loop since we found the item
                break;
            }
        }

        // Check if the item was NOT found in the receipt
        if (!found) {
            // Item is not in the receipt, so add it as a new row
            // Create a new row with three columns:
            // Column 0: Item name
            // Column 1: Quantity (start with 1)
            // Column 2: Total price (quantity × price = 1 × price)
            receitmodel.addRow(new Object[]{itemName, 1, price});
        }

        // Update the total price label at the bottom of the receipt
        // This recalculates and displays the sum of all item totals
        updateTotalLabel();
    }
// This method loads customer data from the database using their phone number
// It displays customer information and wallet balance in the form fields

    private void loadUserData(String phone) {
        // Start a try block to handle potential database errors
        try {
            // Establish a connection to the database
            Connection con = DBConnection.getConnection();

            // Create a SQL query with a parameter placeholder (?)
            // This searches for customers with the specified phone number
            String sql = "SELECT * FROM Customers WHERE PHONE_NUMBER = ?";

            // Create a prepared statement to safely insert the phone number
            // Prepared statements help prevent SQL injection attacks
            PreparedStatement pst = con.prepareStatement(sql);

            // Replace the question mark (?) with the actual phone number parameter
            // The number 1 indicates this is the first parameter in the query
            pst.setString(1, phone);

            // Execute the query and store the results
            ResultSet rs = pst.executeQuery();

            // Check if the query returned at least one row (customer exists)
            if (rs.next()) {
                // Customer found in the database

                // Display the phone number in the phone text field
                txt_custPhone.setText(phone);

                // Get the customer name from the USER_NAME column and display it
                txt_custName.setText(rs.getString("USER_NAME"));

                // Get the customer's wallet balance from the database
                // Store it in the currentBalance variable for later calculations
                currentBalance = rs.getDouble("WALLET_BALANCE");

                // Display the balance in the balance text field with "LE" suffix
                txt_balance.setText(currentBalance + " LE");

                // Apply color coding based on the balance amount
                // If balance is positive (greater than 0)
                if (currentBalance > 0) {
                    // Set the balance field border to green (positive balance)
                    styleField(txt_balance, "green");
                } else {
                    // Set the balance field border to red (zero or negative balance)
                    styleField(txt_balance, "red");
                }

                // Make the customer name field read-only
                // Users cannot edit an existing customer's name
                txt_custName.setEditable(false);

                // Make the customer phone field read-only
                // Users cannot edit an existing customer's phone number
                txt_custPhone.setEditable(false);

            } else {
                // No customer found with this phone number (new customer)

                // Set the current balance to 0.0 for a new customer
                currentBalance = 0.0;

                // Display zero balance in the balance field
                txt_balance.setText("0.0 LE");

                // Set the balance field border to red
                // This indicates a new customer with zero balance
                styleField(txt_balance, "red");

                // Note: For a new customer, the name and phone fields remain editable
                // This allows the user to enter information for a new customer
            }

            // Close the database connection to free resources
            con.close();

        } catch (Exception e) {
            // If any error occurs during database operations
            // Print the error message to the console for debugging
            System.out.println("Error loading user data: " + e.getMessage());
        }
    }

    // This method adds a menu item to the receipt (shopping cart)
// It handles both new items and existing items by increasing quantity
    private void addItem(String itemName, int price) {
        // Create a flag to track if the item is already in the receipt
        // Initialize to false because we haven't checked yet
        boolean found = false;

        // Loop through each row in the receipt table
        // Start from the first row (index 0) to the last row
        for (int i = 0; i < receitmodel.getRowCount(); i++) {
            // Get the item name from column 0 of the current row
            // Convert it to string for comparison
            String existingName = receitmodel.getValueAt(i, 0).toString();

            // Check if this row contains the item we want to add
            // Use equalsIgnoreCase to match names regardless of uppercase/lowercase letters
            if (existingName.equalsIgnoreCase(itemName)) {
                // Item already exists in the receipt

                // Get the current quantity from column 1 of this row
                // Parse the string value to an integer for calculation
                int oldQ = Integer.parseInt(receitmodel.getValueAt(i, 1).toString());

                // Increase the quantity by 1
                int newQ = oldQ + 1;

                // Calculate the new total cost for this item
                // Multiply the new quantity by the item price
                int newcost = newQ * price;

                // Update the quantity in the table (column 1)
                receitmodel.setValueAt(newQ, i, 1);

                // Update the total cost in the table (column 2)
                receitmodel.setValueAt(newcost, i, 2);

                // Mark that we found and updated the item
                found = true;

                // Exit the loop since we found the item
                break;
            }
        }

        // Check if the item was NOT found in the receipt
        if (!found) {
            // This is a new item not currently in the receipt

            // Set initial quantity to 1 for a new item
            int q = 1;

            // Calculate the total cost: quantity × price per item
            int cost = q * price;

            // Add a new row to the receipt table with three columns:
            // Column 0: Item name
            // Column 1: Quantity (1)
            // Column 2: Total cost for this item
            receitmodel.addRow(new Object[]{itemName, q, cost});
        }

        // Update the total price display at the bottom of the receipt
        // This recalculates the sum of all items in the cart
        updateTotalLabel();
    }

// This method calculates and updates the total price of all items in the receipt
// It returns the total amount as an integer
    private int updateTotalLabel() {
        // Initialize total to zero before starting calculations
        int total = 0;

        // Loop through each row in the receipt table
        for (int i = 0; i < receitmodel.getRowCount(); i++) {
            // Get the total cost from column 2 of the current row
            // Parse the string value to an integer for addition
            total += Integer.parseInt(receitmodel.getValueAt(i, 2).toString());
        }

        // Display the calculated total in the Total label
        // Convert the integer total to a string for display
        Total.setText(String.valueOf(total));

        // Return the total amount for any code that needs to use this value
        return total;
    }

// This helper method saves all items from the receipt into the order details table
// It links each item to the main order using the order ID
    private void saveOrderDetails(Connection con, int orderId) throws SQLException {
        // SQL query to insert order items
        // The query finds the ITEM_ID using the item name from the menu table
        // LIMIT 1 ensures we only get one match even if there are duplicate item names
        String sql = "INSERT INTO Order_Items (ORDER_ID, ITEM_ID, QUANTITY, PRICE) VALUES (?, (SELECT ITEM_ID FROM Menu_Items WHERE ITEM_NAME=? LIMIT 1), ?, ?)";

        // Create a prepared statement for the order details insertion
        PreparedStatement ps = con.prepareStatement(sql);

        // Loop through each row in the receipt table (each item in the order)
        for (int i = 0; i < receitmodel.getRowCount(); i++) {
            // Get the item name from column 0 of the current row
            String itemName = receitmodel.getValueAt(i, 0).toString();

            // Get the quantity from column 1 and convert to integer
            int qty = Integer.parseInt(receitmodel.getValueAt(i, 1).toString());

            // Get the total price for this item from column 2 and convert to double
            double price = Double.parseDouble(receitmodel.getValueAt(i, 2).toString());

            // Set the order ID as the first parameter
            ps.setInt(1, orderId);

            // Set the item name as the second parameter
            ps.setString(2, itemName);

            // Set the quantity as the third parameter
            ps.setInt(3, qty);

            // Set the total price for this item as the fourth parameter
            ps.setDouble(4, price);

            // Execute the insert statement for this item
            ps.executeUpdate();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jFrame1 = new javax.swing.JFrame();
        btn_addBalance = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        txt_balance = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txt_custName = new javax.swing.JTextField();
        txt_custPhone = new javax.swing.JTextField();
        panel1 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        pnlMenuItems = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        orderpanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_receipt = new javax.swing.JTable();
        btn_saveOrder = new javax.swing.JButton();
        btn_reset = new javax.swing.JButton();
        btn_delete = new javax.swing.JButton();
        Total = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        btnSave = new javax.swing.JButton();

        jButton3.setText("jButton3");

        jButton4.setText("jButton4");

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(153, 153, 153));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_addBalance.setBackground(new java.awt.Color(153, 255, 153));
        btn_addBalance.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        btn_addBalance.setText("Add");
        btn_addBalance.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_addBalanceMouseEntered(evt);
            }
        });
        btn_addBalance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addBalanceActionPerformed(evt);
            }
        });
        getContentPane().add(btn_addBalance, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 80, 90, 30));

        jButton5.setBackground(new java.awt.Color(243, 207, 170));
        jButton5.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/profile.png"))); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 50, 40));

        jButton6.setBackground(new java.awt.Color(243, 207, 170));
        jButton6.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/home.png"))); // NOI18N
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 20, 50, 40));

        txt_balance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_balanceActionPerformed(evt);
            }
        });
        getContentPane().add(txt_balance, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 50, 190, 90));

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 245, 217));
        jLabel3.setText("Balance :");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 70, 90, 30));

        jLabel1.setBackground(new java.awt.Color(255, 255, 204));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 51, 0));
        jLabel1.setText("ORDER PAGE");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 0, -1, 31));

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 245, 217));
        jLabel2.setText(" Customer Name :");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 60, -1, 22));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 245, 217));
        jLabel4.setText("Phone Number :");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 100, 121, -1));
        getContentPane().add(txt_custName, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 60, 201, -1));

        txt_custPhone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_custPhoneActionPerformed(evt);
            }
        });
        getContentPane().add(txt_custPhone, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 100, 201, -1));

        panel1.setBackground(new java.awt.Color(255, 255, 255));
        panel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel7.setFont(new java.awt.Font("Segoe UI Black", 3, 24)); // NOI18N
        jLabel7.setText("Menu");

        pnlMenuItems.setLayout(new java.awt.GridLayout(0, 2, 10, 10));
        jScrollPane3.setViewportView(pnlMenuItems);

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addGap(175, 175, 175)
                .addComponent(jLabel7)
                .addContainerGap(295, Short.MAX_VALUE))
            .addGroup(panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3))
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 633, Short.MAX_VALUE))
        );

        getContentPane().add(panel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 150, 540, -1));

        jLabel24.setBackground(new java.awt.Color(255, 255, 255));
        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(102, 51, 0));
        jLabel24.setText("RECEIPT");
        getContentPane().add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(119, 143, -1, 31));

        orderpanel.setBackground(new java.awt.Color(255, 255, 255));
        orderpanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        orderpanel.setLayout(new java.awt.GridLayout(1, 0));

        tbl_receipt.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Item", "Quantity", "Cost"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tbl_receipt);

        orderpanel.add(jScrollPane1);

        getContentPane().add(orderpanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 212, 290, 542));

        btn_saveOrder.setBackground(new java.awt.Color(165, 117, 68));
        btn_saveOrder.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        btn_saveOrder.setText("Save");
        btn_saveOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_saveOrderActionPerformed(evt);
            }
        });
        getContentPane().add(btn_saveOrder, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 443, 80, 30));

        btn_reset.setBackground(new java.awt.Color(165, 117, 68));
        btn_reset.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        btn_reset.setText("Reset");
        btn_reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_resetActionPerformed(evt);
            }
        });
        getContentPane().add(btn_reset, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 480, 80, 33));

        btn_delete.setBackground(new java.awt.Color(255, 51, 51));
        btn_delete.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        btn_delete.setForeground(new java.awt.Color(255, 255, 255));
        btn_delete.setText("Delete Item");
        btn_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteActionPerformed(evt);
            }
        });
        getContentPane().add(btn_delete, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 530, 120, 33));

        Total.setBackground(new java.awt.Color(165, 117, 68));
        Total.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        Total.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        getContentPane().add(Total, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 400, 60, 20));

        jLabel20.setFont(new java.awt.Font("Segoe UI Black", 1, 22)); // NOI18N
        jLabel20.setText("Total");
        getContentPane().add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 370, 70, 20));

        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/basicc.jpeg"))); // NOI18N
        getContentPane().add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -30, 1020, 840));

        btnSave.setBackground(new java.awt.Color(165, 117, 68));
        btnSave.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnSave.setText("ADD");
        btnSave.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        getContentPane().add(btnSave, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 200, 180, 40));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // This method handles the "Save Order" button click event
// It processes the complete order, applies discounts, updates the database, and generates a receipt

    private void btn_saveOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_saveOrderActionPerformed
        // Calculate the current total price of all items in the cart
        // The updateTotalLabel() method returns the subtotal
        double subtotal = (double) updateTotalLabel();

        // Check if the cart is empty (subtotal is zero)
        if (subtotal == 0) {
            // Show an error message if cart is empty
            JOptionPane.showMessageDialog(this, "Cart is empty! 🛒");

            // Exit the method since there's nothing to save
            return;
        }

        // --- DISCOUNT SYSTEM ---
        // Apply discounts based on the total order amount
        // Initialize discount percentage to 0%
        double discountPercentage = 0.0;

        // Determine discount based on subtotal amount:
        // - 15% discount for orders 500 LE or more
        // - 10% discount for orders 250 LE or more
        // - 5% discount for orders 100 LE or more
        // - No discount for orders under 100 LE
        if (subtotal >= 500) {
            discountPercentage = 15.0;
        } else if (subtotal >= 250) {
            discountPercentage = 10.0;
        } else if (subtotal >= 100) {
            discountPercentage = 5.0;
        } else {
            discountPercentage = 0.0;
        }

        // Calculate the discount amount in currency
        double discountAmount = subtotal * (discountPercentage / 100);

        // Calculate the final total after applying discount
        double finalTotal = subtotal - discountAmount;

        // Check if the customer has enough balance to pay for the order
        if (finalTotal > currentBalance) {
            // Show error message if balance is insufficient
            JOptionPane.showMessageDialog(this, "Insufficient Balance! ❌", "Error", JOptionPane.ERROR_MESSAGE);

            // Exit the method since payment cannot be processed
            return;
        }

        // Start the database transaction
        try {
            // Establish connection to the database
            Connection con = DBConnection.getConnection();

            // Get the customer phone number from the text field
            String phone = txt_custPhone.getText().trim();

            // --- CUSTOMER ID VERIFICATION ---
            // Find the customer ID using their phone number
            // Initialize customer ID to 0
            int customerId = 0;

            // SQL query to find customer by phone number
            String checkUser = "SELECT CUSTOMER_ID FROM Customers WHERE PHONE_NUMBER = ?";

            // Create prepared statement for the query
            PreparedStatement pstCheck = con.prepareStatement(checkUser);

            // Set the phone number parameter
            pstCheck.setString(1, phone);

            // Execute the query
            ResultSet rsUser = pstCheck.executeQuery();

            // Check if customer exists
            if (rsUser.next()) {
                // Customer found - get their ID
                customerId = rsUser.getInt("CUSTOMER_ID");
            } else {
                // Customer not found - show error and exit
                JOptionPane.showMessageDialog(this, "Customer not found in DB! ❌");
                return;
            }
            // ----------------------------------------

            // --- DEDUCT BALANCE ---
            // Update the customer's wallet balance by subtracting the final total
            // SQL statement to reduce wallet balance
            String updSql = "UPDATE Customers SET WALLET_BALANCE = WALLET_BALANCE - ? WHERE CUSTOMER_ID = ?";

            // Create prepared statement for the update
            PreparedStatement psUpd = con.prepareStatement(updSql);

            // Set the amount to deduct (final total)
            psUpd.setDouble(1, finalTotal);

            // Set the customer ID
            psUpd.setInt(2, customerId);

            // Execute the balance update
            psUpd.executeUpdate();

            // --- RECORD TRANSACTION HISTORY ---
            // Create a record in the wallet transaction history
            // SQL statement to insert transaction
            String hist = "INSERT INTO Wallet_Transactions (CUSTOMER_ID, AMOUNT) VALUES (?, ?)";

            // Create prepared statement for transaction history
            PreparedStatement psH = con.prepareStatement(hist);

            // Set the customer ID
            psH.setInt(1, customerId);

            // Set the transaction amount (negative for deduction)
            psH.setDouble(2, -finalTotal);

            // Execute the transaction history insertion
            psH.executeUpdate();

            // --- SAVE MAIN ORDER ---
            // Create the main order record in the Orders table
            // SQL statement to insert order
            String ordSql = "INSERT INTO Orders (CUSTOMER_ID, ORDER_DATE, SUBTOTAL, DISCOUNT, TOTAL_PRICE) VALUES (?, ?, ?, ?, ?)";

            // Create prepared statement that returns the generated order ID
            PreparedStatement psOrd = con.prepareStatement(ordSql, Statement.RETURN_GENERATED_KEYS);

            // Set customer ID
            psOrd.setInt(1, customerId);

            // Set order date to today's date
            psOrd.setDate(2, java.sql.Date.valueOf(LocalDate.now()));

            // Set subtotal (before discount)
            psOrd.setDouble(3, subtotal);

            // Set discount amount
            psOrd.setDouble(4, discountAmount);

            // Set final total (after discount)
            psOrd.setDouble(5, finalTotal);

            // Execute the order insertion
            psOrd.executeUpdate();

            // Get the auto-generated order ID
            int orderId = 0;
            ResultSet rsKey = psOrd.getGeneratedKeys();
            if (rsKey.next()) {
                orderId = rsKey.getInt(1);
            }

            // --- SAVE ORDER DETAILS ---
            // Save each item from the receipt into the order details table
            saveOrderDetails(con, orderId);

            // --- GENERATE AND DISPLAY RECEIPT ---
            // Build the receipt text for display
            // Create a string builder to construct the receipt
            StringBuilder receiptText = new StringBuilder();

            // Add receipt header
            receiptText.append("******** ZAR CAFE ********\n");
            receiptText.append("Order #: ").append(orderId).append("\n");
            receiptText.append("Date: ").append(LocalDate.now()).append("\n");
            receiptText.append("----------------------------\n");

            // Add each item from the receipt table
            for (int i = 0; i < receitmodel.getRowCount(); i++) {
                // Get item details from each row
                String itemName = receitmodel.getValueAt(i, 0).toString();
                int qty = Integer.parseInt(receitmodel.getValueAt(i, 1).toString());
                double price = Double.parseDouble(receitmodel.getValueAt(i, 2).toString());

                // Format and add item line to receipt
                // %-15s: Left-align item name in 15-character width
                // x%-3d: Left-align quantity with "x" prefix in 3-character width
                // %.1f: Price with 1 decimal place
                receiptText.append(String.format("%-15s x%-3d %.1f\n", itemName, qty, price));
            }

            // Add receipt footer with pricing summary
            receiptText.append("----------------------------\n");
            receiptText.append(String.format("Subtotal:      %.1f LE\n", subtotal));

            // Add discount line if discount was applied
            if (discountAmount > 0) {
                receiptText.append(String.format("Disc (%.0f%%):    -%.1f LE\n", discountPercentage, discountAmount));
            }

            // Add final total
            receiptText.append(String.format("TOTAL:         %.1f LE\n", finalTotal));
            receiptText.append("****************************\n");

            // Create a text area to display the receipt
            JTextArea textArea = new JTextArea(receiptText.toString());

            // Use monospaced font for proper receipt formatting
            textArea.setFont(new Font("Monospaced", Font.BOLD, 14));

            // Display the receipt in a scrollable dialog
            JOptionPane.showMessageDialog(this, new JScrollPane(textArea), "Receipt", JOptionPane.INFORMATION_MESSAGE);

            // --- CLEAN UP AND REFRESH ---
            // Reload customer data to show updated balance
            loadUserData(phone);

            // Clear the receipt table
            receitmodel.setRowCount(0);

            // Reset total display to zero
            Total.setText("0");

            // Close the database connection
            con.close();

        } catch (Exception e) {
            // Handle any errors that occur during the save process
            JOptionPane.showMessageDialog(this, "Save Error: " + e.getMessage());

            // Print error details to console for debugging
            e.printStackTrace();
        }

    }//GEN-LAST:event_btn_saveOrderActionPerformed

    private void btn_resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_resetActionPerformed
        receitmodel.setRowCount(0);
        Total.setText("0");
    }//GEN-LAST:event_btn_resetActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        this.dispose();

        new D__Order_PROFILE(userPhone).setVisible(true);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void btn_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteActionPerformed
        // 1. Find out which row in the receipt table  clicked
        int selectedRow = tbl_receipt.getSelectedRow();

        // 2. If no row is selected (index is -1), show a warning message
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an item to delete first! ⚠️");
            return; // Stop execution
        }

        // 3. Remove the selected row from the table model
        receitmodel.removeRow(selectedRow);

        // 4. Update the total price immediately after deletion
        updateTotalLabel();
    }//GEN-LAST:event_btn_deleteActionPerformed

    private void txt_custPhoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_custPhoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_custPhoneActionPerformed

    private void txt_balanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_balanceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_balanceActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
    }//GEN-LAST:event_btnSaveActionPerformed

    // This method handles the "Add Balance" button click event
// It allows adding money to a customer's wallet balance
    private void btn_addBalanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addBalanceActionPerformed

        // Get the customer phone number from the text field
        String phone = txt_custPhone.getText().trim();

        // Check if phone number is empty
        if (phone.isEmpty()) {
            // Show error message if no phone number is entered
            JOptionPane.showMessageDialog(this, "Please enter customer phone number first!");

            // Exit the method since we need a phone number
            return;
        }

        // Ask the user how much money to add
        String input = JOptionPane.showInputDialog(this, "Enter Amount to Add (LE):");

        // Check if user cancelled or entered nothing
        if (input == null || input.trim().isEmpty()) {
            // Exit if no amount was entered
            return;
        }

        // Start processing the balance addition
        try {
            // Convert the input string to a number
            double amount = Double.parseDouble(input);

            // Validate that the amount is positive
            if (amount <= 0) {
                JOptionPane.showMessageDialog(this, "Amount must be positive!");
                return;
            }

            // Connect to the database
            Connection con = DBConnection.getConnection();

            // 1. VERIFY CUSTOMER EXISTS OR CREATE NEW CUSTOMER
            // SQL query to check if customer exists
            String checkUser = "SELECT * FROM Customers WHERE PHONE_NUMBER = ?";
            PreparedStatement pstCheck = con.prepareStatement(checkUser);
            pstCheck.setString(1, phone);
            ResultSet rs = pstCheck.executeQuery();

            // Check if customer was found
            if (!rs.next()) {
                // Customer does not exist - create a new customer

                // Get customer name from the text field
                String name = txt_custName.getText().trim();

                // SQL statement to insert new customer
                // Default password is '123' and starting balance is 0.0
                String insert = "INSERT INTO Customers (USER_NAME, PHONE_NUMBER, PASSWORD, WALLET_BALANCE) VALUES (?, ?, '123', 0.0)";
                PreparedStatement pstAdd = con.prepareStatement(insert);
                pstAdd.setString(1, name);
                pstAdd.setString(2, phone);
                pstAdd.executeUpdate();
            }

            // 2. ADD BALANCE TO WALLET
            // SQL statement to increase wallet balance
            String sql = "UPDATE Customers SET WALLET_BALANCE = WALLET_BALANCE + ? WHERE PHONE_NUMBER = ?";
            PreparedStatement pst = con.prepareStatement(sql);

            // Set the amount to add
            pst.setDouble(1, amount);

            // Set the phone number
            pst.setString(2, phone);

            // Execute the balance update
            pst.executeUpdate();

            // 3. RETRIEVE UPDATED DATA AND RECORD TRANSACTION HISTORY
            // SQL query to get customer ID and new balance
            // Added CUSTOMER_ID to the SELECT because we need it for the transaction history
            PreparedStatement pstGet = con.prepareStatement("SELECT CUSTOMER_ID, WALLET_BALANCE FROM Customers WHERE PHONE_NUMBER = ?");
            pstGet.setString(1, phone);
            ResultSet rsNew = pstGet.executeQuery();

            // Process the updated customer data
            if (rsNew.next()) {
                // Get the new balance amount
                double newBalance = rsNew.getDouble("WALLET_BALANCE");

                // Get the customer ID for transaction history
                int custId = rsNew.getInt("CUSTOMER_ID");

                // Update the currentBalance variable with new amount
                currentBalance = newBalance;

                // Display the new balance in the text field
                txt_balance.setText(newBalance + " LE");

                // Change the balance field border to green (positive balance)
                styleField(txt_balance, "green");

                // --- TRANSACTION HISTORY RECORDING CODE ---
                // SQL statement to record the transaction
                String histSql = "INSERT INTO Wallet_Transactions (CUSTOMER_ID, AMOUNT, TRANS_DATE) VALUES (?, ?, ?)";
                PreparedStatement pstHist = con.prepareStatement(histSql);

                // Set customer ID
                pstHist.setInt(1, custId);

                // Set the transaction amount (positive for addition)
                pstHist.setDouble(2, amount);

                // Set transaction date to today's date
                pstHist.setString(3, java.time.LocalDate.now().toString());

                // Execute the transaction history insertion
                pstHist.executeUpdate();
                // -------------------------------------------

                // Show success message with new balance
                JOptionPane.showMessageDialog(this, "Balance Added! ✅\nNew Balance: " + newBalance);
            }

            // Close the database connection
            con.close();

        } catch (NumberFormatException e) {
            // Handle case where user enters non-numeric value
            JOptionPane.showMessageDialog(this, "Please enter a valid number!");

        } catch (Exception e) {
            // Handle any other errors
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());

            // Print error details for debugging
            e.printStackTrace();
        }

    }//GEN-LAST:event_btn_addBalanceActionPerformed

    private void btn_addBalanceMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_addBalanceMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_addBalanceMouseEntered

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
                this.dispose();

        new A__Welcome().setVisible(true);       
        
// TODO add your handling code here:

    }//GEN-LAST:event_jButton6ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        String phone = null;

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new D__User_ORDERS(phone).setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Total;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btn_addBalance;
    private javax.swing.JButton btn_delete;
    private javax.swing.JButton btn_reset;
    private javax.swing.JButton btn_saveOrder;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPanel orderpanel;
    private javax.swing.JPanel panel1;
    private javax.swing.JPanel pnlMenuItems;
    private javax.swing.JTable tbl_receipt;
    private javax.swing.JTextField txt_balance;
    private javax.swing.JTextField txt_custName;
    private javax.swing.JTextField txt_custPhone;
    // End of variables declaration//GEN-END:variables

    private void styleField(JTextField field, String type) {
        Color bgColor, borderColor, textColor;
        if ("red".equals(type)) {
            bgColor = new Color(255, 235, 238);
            borderColor = new Color(183, 28, 28);
            textColor = new Color(183, 28, 28);
        } else if ("gold".equals(type)) {
            bgColor = Color.decode("#FFF5D9");
            borderColor = Color.decode("#E67E00");
            textColor = Color.decode("#D15B00");
        } else { // green (default for money)
            bgColor = Color.decode("#E8F5E9");
            borderColor = Color.decode("#2E7D32");
            textColor = Color.decode("#1B5E20");
        }

        field.setOpaque(false);
        field.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        field.setFont(new Font("Segoe UI", Font.BOLD, 14));
        field.setForeground(textColor);
        field.setCaretColor(textColor);
        field.setHorizontalAlignment(JTextField.CENTER);
        if (field == txt_balance) {
            field.setEditable(false);
        }

        field.setUI(new javax.swing.plaf.basic.BasicTextFieldUI() {
            @Override
            protected void paintSafely(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int w = field.getWidth();
                int h = field.getHeight();
                int arc = 40;
                g2d.setColor(bgColor);
                g2d.fillRoundRect(0, 0, w, h, arc, arc);
                super.paintSafely(g);
                g2d.setColor(borderColor);
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRoundRect(0, 0, w - 1, h - 1, arc, arc);
            }
        });
    }

// Method to apply custom styling to buttons
    private void styleButton(javax.swing.JButton btn) {
        // Set font to bold size 14
        btn.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));

        // Set text color to white
        btn.setForeground(java.awt.Color.WHITE);
        // Set background color to brown
        btn.setBackground(new java.awt.Color(102, 51, 0));
        // Don't paint the border
        btn.setBorderPainted(false);
        // Don't show focus border
        btn.setFocusPainted(false);
        // Change cursor to hand when hovering over button
        btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        // Add mouse listener to change background color on hover
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                // Lighter brown color when mouse enters
                btn.setBackground(new java.awt.Color(165, 117, 68));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                // Original brown color when mouse exits
                btn.setBackground(new java.awt.Color(102, 51, 0));
            }
        });
    }

    // Custom border class that creates rounded corners
    class RoundedBorder implements javax.swing.border.Border {

        // Radius of the rounded corners
        private int radius;
        // Color of the border
        private java.awt.Color color;

        // Constructor to set radius and color
        RoundedBorder(int radius, java.awt.Color color) {
            this.radius = radius;
            this.color = color;
        }

        // Method to define the border margins around the component
        public java.awt.Insets getBorderInsets(java.awt.Component c) {
            // Return insets with top/bottom of 5px and sides of half the radius
            return new java.awt.Insets(5, radius / 2, 5, radius / 2);
        }

        // Method to specify if border should be opaque
        public boolean isBorderOpaque() {
            return true;
        }

        // Method to draw the border on the component
        public void paintBorder(java.awt.Component c, java.awt.Graphics g, int x, int y, int width, int height) {
            // Cast graphics object to Graphics2D for advanced drawing
            java.awt.Graphics2D g2 = (java.awt.Graphics2D) g;
            // Enable anti-aliasing for smooth edges
            g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
            // Set the border color
            g2.setColor(color);
            // Set stroke thickness to 2 pixels
            g2.setStroke(new java.awt.BasicStroke(2));
            // Draw the rounded rectangle border
            g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }
    }

}
