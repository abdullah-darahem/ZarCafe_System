package ui;

import db.DBConnection;
import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class D__Order_PROFILE extends javax.swing.JFrame {

// These variables store information about the currently logged-in user
// They hold data that will be used throughout this window
    String userPhone;    // Stores the phone number of the user who opened this window
    int customerId = 0;  // Stores the unique database ID for this customer, starts at 0 until loaded

    // الكونستركتور: بيستلم رقم التليفون من الصفحة اللي قبله
    public D__Order_PROFILE(String phone) {
        this.userPhone = phone;
        initComponents();
        setLocationRelativeTo(null);

        styleTable(tblWallet);
        styleTable(tblOrders);

        styleField(txtName, "gold");
        styleField(txt_custPhone, "gold"); // كان txtPhone
        styleField(txtPass, "gold");

        loadUserData();
        loadWalletHistory();
        loadOrderHistory();
    }
// This method loads the user's personal data into the top form fields

    private void loadUserData() {
        try {
            // Connect to the database
            Connection con = DBConnection.getConnection();

            // SQL query to find customer by phone number
            String sql = "SELECT * FROM Customers WHERE PHONE_NUMBER = ?";

            // Create prepared statement for secure query execution
            PreparedStatement ps = con.prepareStatement(sql);

            // Set the phone number parameter
            ps.setString(1, userPhone);

            // Execute query and get results
            ResultSet rs = ps.executeQuery();

            // Check if customer was found
            if (rs.next()) {
                // Store the customer ID for later use in other queries
                customerId = rs.getInt("CUSTOMER_ID");

                // Display customer name in the name text field
                txtName.setText(rs.getString("USER_NAME"));

                // Display phone number in the phone text field
                txt_custPhone.setText(rs.getString("PHONE_NUMBER"));

                // Display password in the password field
                // This works for both JPasswordField and JTextField types
                txtPass.setText(rs.getString("PASSWORD"));
            }

            // Close the database connection
            con.close();

        } catch (Exception e) {
            // Show error message if data loading fails
            JOptionPane.showMessageDialog(this, "Error loading user: " + e.getMessage());
        }
    }

// This method loads the wallet transaction history table
    private void loadWalletHistory() {
        // Get the table model for the wallet history table
        DefaultTableModel model = (DefaultTableModel) tblWallet.getModel();

        // Clear any existing rows from the table
        model.setRowCount(0);

        try {
            // Connect to the database
            Connection con = DBConnection.getConnection();

            // SQL query to get wallet transactions for this customer
            // Assumes Wallet_Transactions table exists in the database
            String sql = "SELECT * FROM Wallet_Transactions WHERE CUSTOMER_ID = ? ORDER BY TRANS_DATE DESC";

            // Create prepared statement
            PreparedStatement ps = con.prepareStatement(sql);

            // Set the customer ID parameter
            ps.setInt(1, customerId);

            // Execute query and get results
            ResultSet rs = ps.executeQuery();

            // Process each transaction record
            while (rs.next()) {
                // Get the transaction amount
                double amount = rs.getDouble("AMOUNT");

                // Get the transaction date
                String date = rs.getString("TRANS_DATE");

                // Format amount with plus sign for deposits (positive amounts)
                String amountStr = (amount > 0) ? "+" + amount : String.valueOf(amount);

                // Add transaction data to the table
                model.addRow(new Object[]{
                    rs.getInt("TRANS_ID"), // Transaction ID
                    amountStr + " LE", // Formatted amount with currency
                    date // Transaction date
                });
            }

            // Close the database connection
            con.close();

        } catch (Exception e) {
            // Log error to console without showing user an error message
            // This prevents errors if the wallet table doesn't exist yet
            System.err.println("Wallet Table Error: " + e.getMessage());
        }
    }

// This method loads the customer's order history table
    private void loadOrderHistory() {
        // Get the table model for the orders history table
        DefaultTableModel model = (DefaultTableModel) tblOrders.getModel();

        // Clear any existing rows from the table
        model.setRowCount(0);

        try {
            // Connect to the database
            Connection con = DBConnection.getConnection();

            // SQL query to get orders for this customer
            String sql = "SELECT * FROM Orders WHERE CUSTOMER_ID = ? ORDER BY ORDER_DATE DESC";

            // Create prepared statement
            PreparedStatement ps = con.prepareStatement(sql);

            // Set the customer ID parameter
            ps.setInt(1, customerId);

            // Execute query and get results
            ResultSet rs = ps.executeQuery();

            // Process each order record
            while (rs.next()) {
                // Add order data to the table
                model.addRow(new Object[]{
                    rs.getInt("ORDER_ID"), // Order number
                    rs.getString("ORDER_DATE"), // Order date
                    rs.getDouble("TOTAL_PRICE") + " LE" // Total price with currency
                });
            }

            // Close the database connection
            con.close();

        } catch (Exception e) {
            // Log error to console without showing user an error message
            System.err.println("Orders Table Error: " + e.getMessage());
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
        jButton6 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        btnUpdate = new javax.swing.JButton();
        txtPass = new javax.swing.JTextField();
        txtName = new javax.swing.JTextField();
        txt_custPhone = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblOrders = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblWallet = new javax.swing.JTable();
        jLabel18 = new javax.swing.JLabel();
        btnSave = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txt_custPhone1 = new javax.swing.JTextField();

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

        jButton6.setBackground(new java.awt.Color(243, 207, 170));
        jButton6.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jButton6.setText("←");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 60, 30));

        jLabel1.setBackground(new java.awt.Color(255, 255, 204));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 51, 0));
        jLabel1.setText("Profile");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 10, -1, 31));

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 245, 217));
        jLabel2.setText(" Customer Name :");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 60, -1, 22));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 245, 217));
        jLabel4.setText("Password :");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 140, 121, -1));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 245, 217));
        jLabel6.setText("Phone Number :");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 100, 121, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 245, 217));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("MY ORDERS");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 490, 170, 30));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 245, 217));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("WALLET HISTORY");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 200, 170, 30));

        btnUpdate.setBackground(new java.awt.Color(165, 117, 68));
        btnUpdate.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnUpdate.setText("UPDATE");
        btnUpdate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });
        getContentPane().add(btnUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 90, 180, 40));

        txtPass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPassActionPerformed(evt);
            }
        });
        getContentPane().add(txtPass, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 140, 201, 30));
        getContentPane().add(txtName, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 60, 201, 30));

        txt_custPhone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_custPhoneActionPerformed(evt);
            }
        });
        getContentPane().add(txt_custPhone, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 100, 201, 30));

        tblOrders.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        tblOrders.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Order ID", "Date", "Total"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblOrders.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblOrdersMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblOrders);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 530, 930, 200));

        tblWallet.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        tblWallet.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Trans ID", "Amount", "Date"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblWallet.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblWalletMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblWallet);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 240, 920, 240));

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

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 245, 217));
        jLabel5.setText("Phone Number :");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 40, 121, -1));

        txt_custPhone1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_custPhone1ActionPerformed(evt);
            }
        });
        getContentPane().add(txt_custPhone1, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 40, 201, 20));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_custPhoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_custPhoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_custPhoneActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
    }//GEN-LAST:event_btnSaveActionPerformed

    private void txt_custPhone1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_custPhone1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_custPhone1ActionPerformed

    private void txtPassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPassActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPassActionPerformed

    // This method handles mouse clicks on the orders table

    private void tblOrdersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblOrdersMouseClicked
        // Check if user double-clicked on a table row
        if (evt.getClickCount() == 2) {
            // Get the row index that was clicked
            int row = tblOrders.getSelectedRow();

            // If no row is selected, exit the method
            if (row == -1) {
                return;
            }

            // Get the order ID from the first column of the selected row
            String idStr = tblOrders.getValueAt(row, 0).toString();

            // Convert the order ID from string to integer
            int orderId = Integer.parseInt(idStr);

            try {
                // Connect to the database
                Connection con = DBConnection.getConnection();

                // 1. GET ORDER SUMMARY DETAILS (subtotal, discount, total)
                // SQL query to get price details from the main Orders table
                String sqlOrder = "SELECT SUBTOTAL, DISCOUNT, TOTAL_PRICE FROM Orders WHERE ORDER_ID = ?";
                PreparedStatement psOrder = con.prepareStatement(sqlOrder);

                // Set the order ID parameter
                psOrder.setInt(1, orderId);

                // Execute query and get results
                ResultSet rsOrder = psOrder.executeQuery();

                // Variables to store order summary information
                double subtotal = 0, discount = 0, total = 0;

                // If order details are found, store them
                if (rsOrder.next()) {
                    subtotal = rsOrder.getDouble("SUBTOTAL");
                    discount = rsOrder.getDouble("DISCOUNT");
                    total = rsOrder.getDouble("TOTAL_PRICE");
                }

                // 2. GET INDIVIDUAL ITEMS IN THIS ORDER
                // SQL query to join Order_Items and Menu_Items tables
                // Gets item names, quantities, and prices for this specific order
                String sqlItems = "SELECT m.ITEM_NAME, oi.QUANTITY, oi.PRICE "
                        + "FROM Order_Items oi "
                        + "JOIN Menu_Items m ON oi.ITEM_ID = m.ITEM_ID "
                        + "WHERE oi.ORDER_ID = ?";
                PreparedStatement psItems = con.prepareStatement(sqlItems);

                // Set the order ID parameter
                psItems.setInt(1, orderId);

                // Execute query and get results
                ResultSet rsItems = psItems.executeQuery();

                // 3. PREPARE DETAILED MESSAGE FOR DISPLAY
                // Create a string builder to construct the detailed order view
                StringBuilder details = new StringBuilder();

                // Add order header with order number
                details.append("======== ORDER #").append(orderId).append(" ========\n\n");

                // Loop through each item in the order
                while (rsItems.next()) {
                    // Format each item line with emoji, name, quantity, and price
                    details.append(String.format("🔸 %-15s (x%d)   %.1f LE\n",
                            rsItems.getString("ITEM_NAME"), // Item name
                            rsItems.getInt("QUANTITY"), // Quantity purchased
                            rsItems.getDouble("PRICE")));          // Total price for this item
                }

                // Add separator line
                details.append("\n--------------------------------\n");

                // Add subtotal line
                details.append(String.format("Subtotal:      %.1f LE\n", subtotal));

                // Add discount line only if discount was applied
                if (discount > 0) {
                    details.append(String.format("Discount:     -%.1f LE 🎉\n", discount));
                }

                // Add final total line
                details.append(String.format("TOTAL PAID:    %.1f LE\n", total));

                // Add closing separator
                details.append("============================");

                // 4. DISPLAY THE DETAILED ORDER VIEW
                // Create a text area to show the formatted order details
                JTextArea textArea = new JTextArea(details.toString());

                // Use monospaced font to keep the formatting aligned
                textArea.setFont(new Font("Monospaced", Font.BOLD, 14));

                // Make the text area non-editable (read-only)
                textArea.setEditable(false);

                // Make the background transparent for cleaner appearance
                textArea.setOpaque(false);

                // Show the order details in a scrollable dialog box
                JOptionPane.showMessageDialog(this,
                        new JScrollPane(textArea), // Put text area in a scroll pane
                        "Order Details", // Dialog title
                        JOptionPane.INFORMATION_MESSAGE);    // Information icon

                // Close the database connection
                con.close();

            } catch (Exception e) {
                // Print any errors to the console for debugging
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_tblOrdersMouseClicked

    private void tblWalletMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblWalletMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblWalletMouseClicked

         // This method handles the "Update" button click event
// It saves changes made to the user's profile information

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
    try {
        // Connect to the database
        Connection con = DBConnection.getConnection();
        
        // SQL statement to update customer information
        // Updates the user's name and password based on their customer ID
        String sql = "UPDATE Customers SET USER_NAME=?, PASSWORD=? WHERE CUSTOMER_ID=?";
        
        // Create a prepared statement for secure database update
        PreparedStatement ps = con.prepareStatement(sql);
        
        // Set the first parameter: new user name from the text field
        ps.setString(1, txtName.getText());
        
        // Set the second parameter: new password from the text field
        // Modification: Directly gets text without JPasswordField complexities
        // This works whether the field is a JPasswordField or JTextField
        ps.setString(2, txtPass.getText());
        
        // Set the third parameter: customer ID to identify which record to update
        ps.setInt(3, customerId);
        
        // Execute the update statement
        ps.executeUpdate();
        
        // Show success message to the user
        JOptionPane.showMessageDialog(this, "Profile Updated Successfully! ✅");
        
        // Close the database connection
        con.close();
        
        // Refresh the user data display
        // This ensures the screen shows the updated information
        loadUserData();
        
    } catch (Exception e) {
        // Show error message if update fails
        JOptionPane.showMessageDialog(this, "Update Error: " + e.getMessage());
    }

    }//GEN-LAST:event_btnUpdateActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        this.dispose();

new D__User_ORDERS(userPhone).setVisible(true);
    }//GEN-LAST:event_jButton6ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            // مسحنا السطر المعقد وحطينا ده بداله عشان يطبع الخطأ لو حصل
            ex.printStackTrace();
        }

        java.awt.EventQueue.invokeLater(() -> new D__Order_PROFILE("01026670191").setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton6;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblOrders;
    private javax.swing.JTable tblWallet;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtPass;
    private javax.swing.JTextField txt_custPhone;
    private javax.swing.JTextField txt_custPhone1;
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

    private void styleTable(JTable table) {
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(102, 51, 0)); // بني غامق
        table.getTableHeader().setForeground(Color.WHITE);
        table.setRowHeight(30);

        // توسيط الكلام في الخلايا
        javax.swing.table.DefaultTableCellRenderer center = new javax.swing.table.DefaultTableCellRenderer();
        center.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(center);
        }
    }
}
