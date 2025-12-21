package ui;

import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.io.File; // عشان الملفات
import java.nio.file.Files; // عشان النسخ
import java.nio.file.StandardCopyOption; // خيارات النسخ
import javax.swing.filechooser.FileNameExtensionFilter; // فلتر الصور
import db.DBConnection;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
/**
 *
 * @author moham
 */
public final class E__Admin_MENU extends javax.swing.JFrame {

// Public variable to store the ID of the currently selected item
    public int id = 0;

// Public variable to store the file path for saving images
    public String finalPathToSave = "";

// Logger object for recording system events and debugging messages
    private static final java.util.logging.Logger logger
            = java.util.logging.Logger.getLogger(E__Admin_MENU.class.getName());

// Table model to manage data display in the orders table
    DefaultTableModel modelOrders;

// This is the constructor method for the Admin management window
    public E__Admin_MENU() {
        // Initialize and set up all visual components (buttons, text fields, tables)
        initComponents();

        // Position the window in the center of the screen
        setLocationRelativeTo(null);

        // Apply custom styling to all tables in the window
        designTables();

        // Apply visual styling to text input fields with colored borders
        // Style the item name field with gold border
        styleField(txtName, "gold");

        // Style the price field with gold border
        styleField(txtPrice, "gold");

        // Style the stock quantity field with green border
        styleField(txtStock, "green");

        // Load all menu item records from the database into the table
        getAllRecords();

        // Set initial button states for the update and delete buttons
        // Disable the update button (user must select an item first)
        btnUpdate.setEnabled(false);

        // Disable the delete button (user must select an item first)
        btnDelete.setEnabled(false);
    }

// This method retrieves all menu items and their sales data, displaying them in a table
    public void getAllRecords() {
        // Get the table model for the orders table
        DefaultTableModel dtm = (DefaultTableModel) tbl_orders.getModel();

        // Clear all existing rows from the table
        dtm.setRowCount(0);

        try {
            // Connect to the database
            Connection con = DBConnection.getConnection();

            // Create a statement object for executing SQL queries
            Statement st = con.createStatement();

            // SQL query to get menu items with aggregated sales data
            // Note: We get IMAGE_PATH but don't display it in the table to save space
            String query = "SELECT m.ITEM_ID, m.ITEM_NAME, m.PRICE, m.STOCK_QUANTITY, m.IS_AVAILABLE, "
                    + "IFNULL(SUM(oi.PRICE * oi.QUANTITY), 0) as TotalRevenue "
                    + "FROM Menu_Items m "
                    + "LEFT JOIN Order_Items oi ON m.ITEM_ID = oi.ITEM_ID "
                    + "GROUP BY m.ITEM_ID, m.ITEM_NAME, m.PRICE, m.STOCK_QUANTITY, m.IS_AVAILABLE";

            // Execute the query and get results
            ResultSet rs = st.executeQuery(query);

            // Process each menu item record
            while (rs.next()) {
                // Extract data for each column from the current result row
                String id = rs.getString("ITEM_ID");
                String name = rs.getString("ITEM_NAME");
                String price = rs.getString("PRICE");
                String stock = rs.getString("STOCK_QUANTITY");
                String revenue = rs.getString("TotalRevenue");

                // Convert the numeric availability flag to readable text
                int statusBool = rs.getInt("IS_AVAILABLE");
                String status = (statusBool == 1) ? "Available" : "Not Available";

                // Add the item data as a new row in the table
                dtm.addRow(new Object[]{id, name, price, stock, revenue, status});
            }

            // Close the database connection
            con.close();

        } catch (Exception e) {
            // Show error message if data loading fails
            JOptionPane.showMessageDialog(null, "Error loading data: " + e.getMessage());
        }
    }

// Helper method to validate that required fields are filled
    public boolean validateFields() {
        // Check if all three required fields have text
        if (!txtName.getText().equals("") && !txtPrice.getText().equals("") && !txtStock.getText().equals("")) {
            // All fields are filled - return false (meaning NOT invalid)
            return false;
        } else {
            // At least one field is empty - return true (meaning IS invalid)
            return true;
        }
    }

    public void designTables() {
        tbl_orders.getTableHeader().setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 16));
        tbl_orders.getTableHeader().setBackground(new java.awt.Color(102, 51, 0));
        tbl_orders.getTableHeader().setForeground(java.awt.Color.WHITE);

        tbl_orders.setRowHeight(40);

        javax.swing.table.DefaultTableCellRenderer centerRenderer = new javax.swing.table.DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(javax.swing.JLabel.CENTER);
        for (int i = 0; i < tbl_orders.getColumnCount(); i++) {
            tbl_orders.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
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

        jPanel1 = new javax.swing.JPanel();
        jButton6 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        btnSelectImage = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtPrice = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_orders = new javax.swing.JTable();
        txtName = new javax.swing.JTextField();
        txtStock = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton6.setBackground(new java.awt.Color(243, 207, 170));
        jButton6.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jButton6.setText("←");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 60, 30));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Available", "Not Available" }));
        jPanel1.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 190, 150, 50));

        btnSelectImage.setBackground(new java.awt.Color(165, 117, 68));
        btnSelectImage.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnSelectImage.setText("Select Image");
        btnSelectImage.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSelectImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelectImageActionPerformed(evt);
            }
        });
        jPanel1.add(btnSelectImage, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 90, 180, 40));

        btnClear.setBackground(new java.awt.Color(255, 153, 102));
        btnClear.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnClear.setText("CLEAR");
        btnClear.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });
        jPanel1.add(btnClear, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 200, 110, 40));

        btnSave.setBackground(new java.awt.Color(165, 117, 68));
        btnSave.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnSave.setText("ADD");
        btnSave.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        jPanel1.add(btnSave, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 200, 180, 40));

        btnUpdate.setBackground(new java.awt.Color(165, 117, 68));
        btnUpdate.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnUpdate.setText("UPDATE");
        btnUpdate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });
        jPanel1.add(btnUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 200, 180, 40));

        btnDelete.setBackground(new java.awt.Color(255, 79, 79));
        btnDelete.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnDelete.setText("DELETE");
        btnDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        jPanel1.add(btnDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 200, 110, 40));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 245, 217));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Stock");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 60, 170, 30));

        txtPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPriceActionPerformed(evt);
            }
        });
        jPanel1.add(txtPrice, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 90, 200, 40));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 245, 217));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Price");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 60, 170, 30));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 245, 217));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Name");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 60, 170, 30));

        jButton5.setBackground(new java.awt.Color(243, 207, 170));
        jButton5.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/home.png"))); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 20, 50, 30));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("MANAGE MENU & STOCK.");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 10, -1, -1));

        tbl_orders.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        tbl_orders.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Name", "Price", "Stock", "Revenue", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_orders.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_ordersMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_orders);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 280, 910, 480));

        txtName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNameActionPerformed(evt);
            }
        });
        jPanel1.add(txtName, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 90, 200, 40));

        txtStock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtStockActionPerformed(evt);
            }
        });
        jPanel1.add(txtStock, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 90, 180, 40));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/basicc.jpeg"))); // NOI18N
        jLabel4.setText("jLabel4");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(-30, 0, 1020, 850));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        this.dispose();

        new A__Welcome().setVisible(true);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void tbl_ordersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_ordersMouseClicked
        // Get the index of the row that was clicked
        int index = tbl_orders.getSelectedRow();

        // Get the table model that contains the data
        TableModel model = tbl_orders.getModel();

        // Save the ID from the first column of the selected row
        // This ID will be used to identify which record to update or delete
        String idStr = model.getValueAt(index, 0).toString();
        id = Integer.parseInt(idStr);

        // Extract data from other columns of the selected row
        String name = model.getValueAt(index, 1).toString();
        String price = model.getValueAt(index, 2).toString();
        String stock = model.getValueAt(index, 3).toString();
        String status = model.getValueAt(index, 5).toString();

        // Display the extracted data in the text input fields
        // Show the item name in the name text field
        txtName.setText(name);

        // Show the price in the price text field
        txtPrice.setText(price);

        // Show the stock quantity in the stock text field
        txtStock.setText(stock);

        // Set the availability status in the combo box
        // If status is "Available", select that option in the combo box
        if (status.equals("Available")) {
            jComboBox1.setSelectedItem("Available");
        } else {
            // Otherwise, select "Not Available" in the combo box
            jComboBox1.setSelectedItem("Not Available");
        }

        // Reset the image path variable to empty
        // This ensures that if the user updates without choosing a new image,
        // the old image won't be replaced with an empty path
        finalPathToSave = "";

        // Update button states for better user interface flow
        // Disable the Save button since we're in edit mode, not add mode
        btnSave.setEnabled(false);

        // Enable the Update button since we have selected an item to edit
        btnUpdate.setEnabled(true);

        // Enable the Delete button since we have selected an item that can be deleted
        btnDelete.setEnabled(true);

    }//GEN-LAST:event_tbl_ordersMouseClicked

    private void txtNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNameActionPerformed

    private void txtStockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtStockActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtStockActionPerformed

    private void txtPriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPriceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPriceActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        txtName.setText("");
        txtPrice.setText("");
        txtStock.setText("");
        finalPathToSave = "";
        btnSave.setEnabled(true);
        btnUpdate.setEnabled(false);
        btnDelete.setEnabled(false);
        jComboBox1.setSelectedIndex(0);
    }//GEN-LAST:event_btnClearActionPerformed

    // This method handles the "Save" button click event
// It adds a new menu item to the database

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:

    // First, check if all required fields are filled
    if (validateFields()) {
        // Show error message if any required field is empty
        JOptionPane.showMessageDialog(this, "Please Fill All Fields!");
        
    } else {
        // All fields are filled, proceed with saving
        try {
            // Connect to the database
            Connection con = DBConnection.getConnection();
            
            // Determine the availability status from the combo box
            String statusStr = (String) jComboBox1.getSelectedItem();
            
            // Convert the status text to a numeric value (1 for available, 0 for not available)
            int isAvailable = (statusStr.equals("Available")) ? 1 : 0;
            
            // SQL statement to insert a new menu item
            // Added IMAGE_PATH to the query
            PreparedStatement ps = con.prepareStatement("INSERT INTO Menu_Items (ITEM_NAME, PRICE, STOCK_QUANTITY, IS_AVAILABLE, IMAGE_PATH) VALUES(?,?,?,?,?)");
            
            // Set the first parameter: item name from the name text field
            ps.setString(1, txtName.getText());
            
            // Set the second parameter: price from the price text field
            ps.setString(2, txtPrice.getText());
            
            // Set the third parameter: stock quantity from the stock text field
            ps.setString(3, txtStock.getText());
            
            // Set the fourth parameter: availability status (1 or 0)
            ps.setInt(4, isAvailable);
            
            // Set the fifth parameter: image file path
            
            // Check if an image path was selected
            if (finalPathToSave.isEmpty()) {
                // If no image path exists, send an empty string
                ps.setString(5, "");
                
            } else {
                // If an image path exists, send the path
                ps.setString(5, finalPathToSave);
            }
            
            // Execute the insert statement to add the new item
            ps.executeUpdate();
            
            // Show success message to the user
            JOptionPane.showMessageDialog(this, "Item Added Successfully ✅");
            
            // Refresh the admin page by closing the current window
            // and opening a new instance of the admin window
            setVisible(false);
            new E__Admin_MENU().setVisible(true);
            
        } catch (Exception e) {
            // Show error message if saving fails
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    }//GEN-LAST:event_btnSaveActionPerformed
// This method handles the "Update" button click event
// It updates an existing menu item in the database
    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
    // First, validate that all required fields are filled
    if (validateFields()) {
        // Show error message if any required field is empty
        JOptionPane.showMessageDialog(this, "Please Fill All Fields!");
        
    } else {
        // All fields are filled, proceed with updating
        try {
            // Connect to the database
            Connection con = DBConnection.getConnection();
            
            // Get the availability status from the combo box
            String statusStr = (String) jComboBox1.getSelectedItem();
            
            // Convert status text to numeric value (1 for available, 0 for not available)
            int isAvailable = (statusStr.equals("Available")) ? 1 : 0;
            
            // Declare SQL query and prepared statement variables
            String sql;
            PreparedStatement ps;
            
            // Check if the user selected a new image
            if (!finalPathToSave.isEmpty()) {
                // User selected a new image - update all fields including image path
                sql = "UPDATE Menu_Items SET ITEM_NAME=?, PRICE=?, STOCK_QUANTITY=?, IS_AVAILABLE=?, IMAGE_PATH=? WHERE ITEM_ID=?";
                ps = con.prepareStatement(sql);
                
                // Fill in the data for the update query
                ps.setString(1, txtName.getText());      // Item name
                ps.setString(2, txtPrice.getText());     // Item price
                ps.setString(3, txtStock.getText());     // Stock quantity
                ps.setInt(4, isAvailable);               // Availability status
                ps.setString(5, finalPathToSave);        // New image path
                ps.setInt(6, id);                        // Item ID to identify which record to update
                
            } else {
                // User did NOT select a new image - update all fields except image path
                sql = "UPDATE Menu_Items SET ITEM_NAME=?, PRICE=?, STOCK_QUANTITY=?, IS_AVAILABLE=? WHERE ITEM_ID=?";
                ps = con.prepareStatement(sql);
                
                // Fill in the data for the update query
                ps.setString(1, txtName.getText());      // Item name
                ps.setString(2, txtPrice.getText());     // Item price
                ps.setString(3, txtStock.getText());     // Stock quantity
                ps.setInt(4, isAvailable);               // Availability status
                ps.setInt(5, id);                        // Item ID to identify which record to update
            }
            
            // Execute the update statement
            ps.executeUpdate();
            
            // Show success message to the user
            JOptionPane.showMessageDialog(this, "Item Updated Successfully 🔄");
            
            // Refresh the admin page by closing the current window
            // and opening a new instance of the admin window
            setVisible(false);
            new E__Admin_MENU().setVisible(true);
            
        } catch (Exception e) {
            // Show error message if updating fails
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }


    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:

        int a = JOptionPane.showConfirmDialog(null, "Do you want to Delete this item?", "Select", JOptionPane.YES_NO_OPTION);
        if (a == 0) {
            try {
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement("DELETE FROM Menu_Items WHERE ITEM_ID=?");
                ps.setInt(1, id);
                ps.executeUpdate();

                JOptionPane.showMessageDialog(this, "Item Deleted Successfully 🗑️");

                setVisible(false);
                new E__Admin_MENU().setVisible(true);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Cannot Delete! This item has orders linked to it.\nTry changing Status to 'Not Available' instead.");
            }
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    // This method handles the "Select Image" button click event
// It allows the user to choose an image file and prepares it for saving

    private void btnSelectImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelectImageActionPerformed
        // TODO add your handling code here:

    // Create a file chooser dialog for selecting files
    JFileChooser chooser = new JFileChooser();
    
    // Restrict the file chooser to show only image files
    // Only files with .jpg, .png, or .jpeg extensions will be displayed
    chooser.setFileFilter(new FileNameExtensionFilter("Images", "jpg", "png", "jpeg"));
    
    // Open the file chooser dialog (null means center on screen)
    chooser.showOpenDialog(null);
    
    // Get the selected file from the dialog
    File f = chooser.getSelectedFile();
    
    // Check if the user actually selected a file (not cancelled)
    if (f != null) {
        try {
            // Generate a unique filename using current system time
            // This prevents filename conflicts if multiple users upload images
            String filename = "img_" + System.currentTimeMillis() + ".jpg";
            
            // Create destination file path within the project's images folder
            // Images will be saved to "src/images/" directory
            File dest = new File("src/images/" + filename);
            
            // Copy the selected file to the destination folder
            // REPLACE_EXISTING ensures any file with the same name gets overwritten
            Files.copy(f.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
            
            // Store the complete file path for later use in database operations
            finalPathToSave = "src/images/" + filename;
            
            // Show success message to the user with the saved filename
            JOptionPane.showMessageDialog(this, "Image Selected: " + filename);
            
        } catch (Exception e) {
            // Show error message if image upload/copy fails
            JOptionPane.showMessageDialog(this, "Error Uploading Image: " + e.getMessage());
        }
    }

    }//GEN-LAST:event_btnSelectImageActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        this.dispose();

        new E__Admin_1().setVisible(true);
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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new E__Admin_MENU().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSelectImage;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbl_orders;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtPrice;
    private javax.swing.JTextField txtStock;
    // End of variables declaration//GEN-END:variables

    private void styleField(JTextField field, String type) {
        Color bgColor, borderColor, textColor;

        if ("gold".equals(type)) {
            bgColor = Color.decode("#FFF5D9");
            borderColor = Color.decode("#E67E00");
            textColor = Color.decode("#D15B00");
        } else {
            bgColor = Color.decode("#E8F5E9");
            borderColor = Color.decode("#2E7D32");
            textColor = Color.decode("#1B5E20");
        }

        field.setOpaque(false);
        field.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); 
        field.setForeground(textColor);
        field.setCaretColor(textColor); // 
        field.setHorizontalAlignment(JTextField.CENTER); // 

 
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
}
