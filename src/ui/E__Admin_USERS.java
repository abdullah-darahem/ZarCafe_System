package ui;

import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import db.DBConnection;

public final class E__Admin_USERS extends javax.swing.JFrame {
// Variables to store user selections
public int selectedUserId = 0;   // Stores the ID of the selected customer
public int selectedOrderId = 0;  // Stores the ID of the selected order

// Logger for tracking system events and errors
private static final java.util.logging.Logger logger =
        java.util.logging.Logger.getLogger(E__Admin_USERS.class.getName());

// Constructor for the advanced admin management window
public E__Admin_USERS() {
    // Initialize and set up all visual components
    initComponents();
    
    // Center the window on the screen
    setLocationRelativeTo(null);
    
    // Apply custom styling to all tables in the window
    designTables();
    
    // Load the list of all customers when the page first opens
    loadAllUsers();
    
}

// This method loads all customers into the left-side table
public void loadAllUsers() {
    // Get the table model for the users table
    DefaultTableModel dtm = (DefaultTableModel) tbl_users.getModel();
    
    // Clear all existing rows from the table
    dtm.setRowCount(0);
    
    try {
        // Connect to the database
        Connection con = DBConnection.getConnection();
        
        // Create a statement object for executing SQL queries
        Statement st = con.createStatement();
        
        // SQL query to get all customer data including wallet balance
        String query = "SELECT * FROM Customers";
        
        // Execute the query and get results
        ResultSet rs = st.executeQuery(query);
        
        // Process each customer record
        while (rs.next()) {
            // Extract data from the current result row
            int id = rs.getInt("CUSTOMER_ID");
            String name = rs.getString("USER_NAME");
            String phone = rs.getString("PHONE_NUMBER");
            double balance = rs.getDouble("WALLET_BALANCE");
            
            // Add customer data as a new row in the table
            dtm.addRow(new Object[]{id, name, phone, balance});
        }
        
        // Close the database connection
        con.close();
        
    } catch (Exception e) {
        // Show error message if customer loading fails
        JOptionPane.showMessageDialog(null, "Error loading users: " + e.getMessage());
    }
}

// This method loads the order history for a specific customer into the right-side table
public void loadUserHistory(int customerId) {
    // Get the table model for the history table
    DefaultTableModel dtm = (DefaultTableModel) tblHistory.getModel();
    
    // Clear all existing rows from the table
    dtm.setRowCount(0);
    
    try {
        // Connect to the database
        Connection con = DBConnection.getConnection();
        
        // SQL query to get orders for a specific customer, newest first
        String query = "SELECT * FROM Orders WHERE CUSTOMER_ID = ? ORDER BY ORDER_DATE DESC";
        
        // Create prepared statement
        PreparedStatement ps = con.prepareStatement(query);
        
        // Set the customer ID parameter
        ps.setInt(1, customerId);
        
        // Execute query and get results
        ResultSet rs = ps.executeQuery();
        
        // Process each order record
        while (rs.next()) {
            // Extract data from the current result row
            int orderId = rs.getInt("ORDER_ID");
            String date = rs.getString("ORDER_DATE");
            double total = rs.getDouble("TOTAL_PRICE");
            
            // Add order data as a new row in the history table
            dtm.addRow(new Object[]{orderId, date, total});
        }
        
        // Close the database connection
        con.close();
        
    } catch (Exception e) {
        // Show error message if history loading fails
        JOptionPane.showMessageDialog(null, "Error loading history: " + e.getMessage());
    }
}

// This method displays a popup window with detailed information about a specific order
private void showOrderDetailsPopup(int orderId) {
    try {
        // Connect to the database
        Connection con = DBConnection.getConnection();
        
        // 1. GET FINANCIAL DATA (subtotal, discount, total price)
        String queryMain = "SELECT SUBTOTAL, DISCOUNT, TOTAL_PRICE FROM Orders WHERE ORDER_ID = ?";
        PreparedStatement psMain = con.prepareStatement(queryMain);
        psMain.setInt(1, orderId);
        ResultSet rsMain = psMain.executeQuery();
        
        // Variables to store financial information
        double subtotal = 0, discount = 0, total = 0;
        
        // If financial data is found, store it
        if (rsMain.next()) {
            subtotal = rsMain.getDouble("SUBTOTAL");
            discount = rsMain.getDouble("DISCOUNT");
            total = rsMain.getDouble("TOTAL_PRICE");
        }
        
        // 2. GET ORDER ITEMS DETAILS
        String queryItems = "SELECT m.ITEM_NAME, oi.QUANTITY, oi.PRICE " +
                            "FROM Order_Items oi " +
                            "JOIN Menu_Items m ON oi.ITEM_ID = m.ITEM_ID " +
                            "WHERE oi.ORDER_ID = ?";
        PreparedStatement psItems = con.prepareStatement(queryItems);
        psItems.setInt(1, orderId);
        ResultSet rsItems = psItems.executeQuery();
        
        // 3. BUILD THE DETAILED MESSAGE FOR DISPLAY
        
        // Create a string builder to construct the order details
        StringBuilder details = new StringBuilder();
        
        // Add order header with order number
        details.append("====== ORDER #").append(orderId).append(" DETAILS ======\n\n");
        
        // Loop through each item in the order
        while (rsItems.next()) {
            // Format each item line with emoji, name, quantity, and price
            details.append(String.format("🔸 %-15s (x%d)   %.1f LE\n", 
                    rsItems.getString("ITEM_NAME"),        // Item name
                    rsItems.getInt("QUANTITY"),            // Quantity purchased
                    rsItems.getDouble("PRICE")));          // Total price for this item
        }
        
        // Add separator line
        details.append("\n--------------------------------\n");
        
        // Add subtotal line
        details.append(String.format("Subtotal:      %.1f LE\n", subtotal));
        
        // Add discount line only if discount was applied
        if (discount > 0) {
            details.append(String.format("Discount:     -%.1f LE (Applied)\n", discount));
        }
        
        // Add final total line
        details.append(String.format("TOTAL PAID:    %.1f LE\n", total));
        
        // Add closing separator
        details.append("============================");
        
        // Create a text area to display the formatted order details
        JTextArea textArea = new JTextArea(details.toString());
        
        // Use monospaced font to keep the formatting aligned
        textArea.setFont(new Font("Monospaced", Font.BOLD, 14));
        
        // Make the text area non-editable (read-only)
        textArea.setEditable(false);
        
        // Make the background transparent for cleaner appearance
        textArea.setOpaque(false);
        
        // Show the order details in a scrollable dialog box
        JOptionPane.showMessageDialog(this, 
            new JScrollPane(textArea),           // Put text area in a scroll pane
            "Order View",                         // Dialog title
            JOptionPane.INFORMATION_MESSAGE);     // Information icon
        
        // Close the database connection
        con.close();
        
    } catch (Exception e) {
        // Show error message if order details loading fails
        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
    }
}

public void designTables() {
        tbl_users.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tbl_users.getTableHeader().setBackground(new Color(102, 51, 0));
        tbl_users.getTableHeader().setForeground(Color.WHITE);
        tbl_users.setRowHeight(30);
        
        tbl_users.getColumnModel().getColumn(0).setPreferredWidth(40); 
        tbl_users.getColumnModel().getColumn(1).setPreferredWidth(180); 
        tbl_users.getColumnModel().getColumn(2).setPreferredWidth(120); 
        tbl_users.getColumnModel().getColumn(3).setPreferredWidth(80);  

        tblHistory.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tblHistory.getTableHeader().setBackground(new Color(102, 51, 0));
        tblHistory.getTableHeader().setForeground(Color.WHITE);
        tblHistory.setRowHeight(30);

        tblHistory.getColumnModel().getColumn(0).setPreferredWidth(60);  
        tblHistory.getColumnModel().getColumn(1).setPreferredWidth(170); 
        tblHistory.getColumnModel().getColumn(2).setPreferredWidth(80); 

        javax.swing.table.DefaultTableCellRenderer centerRenderer = new javax.swing.table.DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        
        for (int i = 0; i < tbl_users.getColumnCount(); i++) 
            tbl_users.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            
        for (int i = 0; i < tblHistory.getColumnCount(); i++) 
            tblHistory.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
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
        btnAdd = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblHistory = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_users = new javax.swing.JTable();
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

        btnAdd.setBackground(new java.awt.Color(165, 117, 68));
        btnAdd.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnAdd.setText("ADD");
        btnAdd.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });
        jPanel1.add(btnAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 660, 160, 40));

        btnUpdate.setBackground(new java.awt.Color(165, 117, 68));
        btnUpdate.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnUpdate.setText("UPDATE");
        btnUpdate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });
        jPanel1.add(btnUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 660, 180, 40));

        btnDelete.setBackground(new java.awt.Color(255, 79, 79));
        btnDelete.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnDelete.setText("DELETE");
        btnDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        jPanel1.add(btnDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 660, 130, 40));

        jButton5.setBackground(new java.awt.Color(243, 207, 170));
        jButton5.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/home.png"))); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 30, 50, 30));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("MANAGE USERS & HISTORY");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 10, -1, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 245, 217));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("User Order History");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 120, 170, 30));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 245, 217));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Users List");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 110, 170, 30));

        tblHistory.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        tblHistory.setModel(new javax.swing.table.DefaultTableModel(
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
                java.lang.Integer.class, java.lang.String.class, java.lang.Double.class
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
        tblHistory.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHistoryMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblHistory);
        if (tblHistory.getColumnModel().getColumnCount() > 0) {
            tblHistory.getColumnModel().getColumn(2).setResizable(false);
        }

        jPanel1.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 150, 360, 500));

        tbl_users.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        tbl_users.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Name", "Phone", "Balance"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_users.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_usersMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_users);
        if (tbl_users.getColumnModel().getColumnCount() > 0) {
            tbl_users.getColumnModel().getColumn(2).setResizable(false);
            tbl_users.getColumnModel().getColumn(3).setResizable(false);
            tbl_users.getColumnModel().getColumn(3).setHeaderValue("Stock");
        }

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, 510, 500));

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
    
    private void tbl_usersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_usersMouseClicked
int index = tbl_users.getSelectedRow();
        TableModel model = tbl_users.getModel();
        
        String idStr = model.getValueAt(index, 0).toString();
        selectedUserId = Integer.parseInt(idStr);
        
        loadUserHistory(selectedUserId);        
    }//GEN-LAST:event_tbl_usersMouseClicked

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        
JTextField nameField = new JTextField();
        JTextField phoneField = new JTextField();
        Object[] message = {
            "Name:", nameField,
            "Phone:", phoneField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Add New User", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                Connection con = DBConnection.getConnection();
                String sql = "INSERT INTO Customers (USER_NAME, PHONE_NUMBER, PASSWORD, WALLET_BALANCE) VALUES (?, ?, '123', 0.0)";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, nameField.getText());
                ps.setString(2, phoneField.getText());
                ps.executeUpdate();
                
                JOptionPane.showMessageDialog(this, "User Added Successfully ✅");
                loadAllUsers();
                con.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            }
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
if (selectedUserId == 0) {
            JOptionPane.showMessageDialog(this, "Please select a user to update!");
            return;
        }

        JTextField nameField = new JTextField();
        JTextField phoneField = new JTextField();
        Object[] message = {
            "New Name:", nameField,
            "New Phone:", phoneField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Update User Info", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                Connection con = DBConnection.getConnection();
                String sql = "UPDATE Customers SET USER_NAME=?, PHONE_NUMBER=? WHERE CUSTOMER_ID=?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, nameField.getText());
                ps.setString(2, phoneField.getText());
                ps.setInt(3, selectedUserId);
                ps.executeUpdate();
                
                JOptionPane.showMessageDialog(this, "User Updated Successfully 🔄");
                loadAllUsers();
                con.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            }
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        
if (selectedUserId == 0) {
            JOptionPane.showMessageDialog(this, "Please select a user first!");
            return;
        }

        int a = JOptionPane.showConfirmDialog(null, "Delete this user and ALL their history?", "Warning", JOptionPane.YES_NO_OPTION);
        if (a == 0) {
            try {
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement("DELETE FROM Customers WHERE CUSTOMER_ID=?");
                ps.setInt(1, selectedUserId);
                ps.executeUpdate();
                
                JOptionPane.showMessageDialog(this, "User Deleted Successfully 🗑️");
                
                loadAllUsers(); 
                DefaultTableModel dtm = (DefaultTableModel) tblHistory.getModel();
                dtm.setRowCount(0); 
                selectedUserId = 0;
                
                con.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            }
        }
        
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void tblHistoryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHistoryMouseClicked
        // TODO add your handling code here:
 int index = tblHistory.getSelectedRow();
    if(index == -1) return; 

    TableModel model = tblHistory.getModel();
    String idStr = model.getValueAt(index, 0).toString();
    selectedOrderId = Integer.parseInt(idStr);

    if (evt.getClickCount() == 2) {
        showOrderDetailsPopup(selectedOrderId);
    }
        
    }//GEN-LAST:event_tblHistoryMouseClicked

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        this.dispose();

        new E__Admin_1().setVisible(true);
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
            ex.printStackTrace();
        }

        java.awt.EventQueue.invokeLater(() -> new E__Admin_USERS().setVisible(true));
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tblHistory;
    private javax.swing.JTable tbl_users;
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
}
