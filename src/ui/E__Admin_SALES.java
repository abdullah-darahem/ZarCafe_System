package ui;

// Import required SQL classes for database operations
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

// Import Swing classes for UI dialogs and tables
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

// Import database connection class
import db.DBConnection;

import javax.swing.*;
import java.awt.*;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
/**
 *
 * @author moham
 */
public final class E__Admin_SALES extends javax.swing.JFrame {

    // Logger for debugging and logging system messages
    private static final java.util.logging.Logger logger
            = java.util.logging.Logger.getLogger(E__Admin_SALES.class.getName());

    // Table model for orders table
    DefaultTableModel modelOrders;

    /**
     * Creates new form SaEles
     */
    // This is the constructor for the Admin window
// It sets up the initial state of the admin dashboard
    public E__Admin_SALES() {
        // Initialize all visual components (buttons, tables, labels, etc.)
        initComponents();

        // Center the window on the screen for better user experience
        setLocationRelativeTo(null);

        // Get the table model for the orders table
        // This model manages the data displayed in the orders table
        modelOrders = (javax.swing.table.DefaultTableModel) tbl_orders.getModel();

        // Apply custom styling to all tables in the window
        designTables();

        // Load the list of dates that have orders from the database
        // These dates will appear in the dropdown selection box
        loadActiveDates();

        // (Optional) Automatically load a report for the most recent date when window opens
        // Check if there are any dates available in the dropdown
        if (cmbDate.getItemCount() > 0) {
            // Get the first date from the dropdown (most recent date)
            loadDailyReport(cmbDate.getItemAt(0));

            // Apply visual styling to the report display fields
            // Style the best seller field with green border
            styleField(txtBestSeller, "green");

            // Style the total income field with gold border
            styleField(txtTotalIncome, "gold");
        }
    }

// This method populates the dropdown menu with dates that have active orders
    public void loadActiveDates() {
        try {
            // Clear any existing dates from the dropdown
            cmbDate.removeAllItems();

            // Connect to the database
            Connection con = DBConnection.getConnection();

            // SQL query to get unique order dates (without duplicates)
            // DATE() function extracts just the date part from ORDER_DATE
            // Results are sorted newest to oldest (DESC)
            String sql = "SELECT DISTINCT DATE(ORDER_DATE) AS SaleDate FROM Orders ORDER BY SaleDate DESC";

            // Create prepared statement
            PreparedStatement pst = con.prepareStatement(sql);

            // Execute query and get results
            ResultSet rs = pst.executeQuery();

            // Add each date to the dropdown
            while (rs.next()) {
                cmbDate.addItem(rs.getString("SaleDate"));
            }

            // Close the database connection
            con.close();

        } catch (Exception e) {
            // Show error message if date loading fails
            JOptionPane.showMessageDialog(this, "Error loading dates: " + e.getMessage());
        }
    }

// This method loads and displays a daily sales report for a specific date
    public void loadDailyReport(String date) {
        // 1. Clear the orders table before loading new data
        modelOrders.setRowCount(0);

        // --- Initialize calculation variables (must be done at the beginning) ---
        double totalDayRevenue = 0.0;    // Accumulates total sales for the day
        String bestItemName = "None";    // Name of the best-selling item
        int maxQty = -1;                 // Highest quantity sold (starts at -1 to ensure first item beats it)

        try {
            // Connect to the database
            Connection con = DBConnection.getConnection();

            // 2. SMART QUERY: Gets aggregated sales data for a specific date
            String sql = "SELECT m.ITEM_NAME, m.PRICE, SUM(oi.QUANTITY) as TOTAL_QTY, SUM(oi.QUANTITY * oi.PRICE) as TOTAL_REV "
                    + "FROM Order_Items oi "
                    + "JOIN Orders o ON oi.ORDER_ID = o.ORDER_ID "
                    + "JOIN Menu_Items m ON oi.ITEM_ID = m.ITEM_ID "
                    + "WHERE DATE(o.ORDER_DATE) = ? "
                    + "GROUP BY m.ITEM_ID, m.ITEM_NAME "
                    + "ORDER BY TOTAL_REV DESC"; // Sort by total revenue (highest first)

            // Create prepared statement
            PreparedStatement pst = con.prepareStatement(sql);

            // Set the date parameter
            pst.setString(1, date);

            // Execute query and get results
            ResultSet rs = pst.executeQuery();

            // 3. FILL TABLE AND PERFORM CALCULATIONS
            // Process each menu item's sales data
            while (rs.next()) {
                // Extract data from the current result row
                String name = rs.getString("ITEM_NAME");
                double price = rs.getDouble("PRICE");
                int qty = rs.getInt("TOTAL_QTY");
                double rev = rs.getDouble("TOTAL_REV");

                // Add a new row to the orders table with item details
                modelOrders.addRow(new Object[]{name, price, qty, rev});

                // --- PERFORM ADDITIONAL CALCULATIONS (inside the loop) ---
                // Accumulate total revenue for the day
                totalDayRevenue += rev;

                // Compare quantities to find the best-selling item
                if (qty > maxQty) {
                    maxQty = qty;           // Update highest quantity
                    bestItemName = name;    // Update best-selling item name
                }
            }

            // --- UPDATE SCREEN WITH FINAL RESULTS (after the loop finishes) ---
            // This location is correct because variables have now accumulated all data
            // Display total income in the text field
            txtTotalIncome.setText("Total Income: " + totalDayRevenue + " LE");

            // Display best seller information in the text field
            txtBestSeller.setText("Best Seller: " + bestItemName + " (" + maxQty + ")");

            // Close the database connection
            con.close();

        } catch (Exception e) {
            // Show error message if report loading fails
            JOptionPane.showMessageDialog(this, "Error loading report: " + e.getMessage());
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
        cmbDate = new javax.swing.JComboBox<>();
        jButton5 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_orders = new javax.swing.JTable();
        txtTotalIncome = new javax.swing.JTextField();
        txtBestSeller = new javax.swing.JTextField();
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

        cmbDate.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        cmbDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbDateActionPerformed(evt);
            }
        });
        jPanel1.add(cmbDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 50, 160, 60));

        jButton5.setBackground(new java.awt.Color(243, 207, 170));
        jButton5.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/home.png"))); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 30, 50, 30));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("DAILY SALES REPORT");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 10, -1, -1));

        tbl_orders.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        tbl_orders.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Item Name", "Unit Price", "Total Qty", "Total Revenue"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Double.class, java.lang.String.class, java.lang.Double.class
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
        tbl_orders.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_ordersMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_orders);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 190, 920, 570));

        txtTotalIncome.setText("jTextField1");
        txtTotalIncome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotalIncomeActionPerformed(evt);
            }
        });
        jPanel1.add(txtTotalIncome, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 90, 240, 90));

        txtBestSeller.setText("jTextField1");
        txtBestSeller.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBestSellerActionPerformed(evt);
            }
        });
        jPanel1.add(txtBestSeller, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, 270, 80));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/basicc.jpeg"))); // NOI18N
        jLabel4.setText("jLabel4");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(-30, 0, 1020, 850));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(35, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 9, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        this.dispose();

        new A__Welcome().setVisible(true);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void tbl_ordersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_ordersMouseClicked

    }//GEN-LAST:event_tbl_ordersMouseClicked

    private void cmbDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbDateActionPerformed
        if (cmbDate.getSelectedItem() != null) {
            String selectedDate = cmbDate.getSelectedItem().toString();
            loadDailyReport(selectedDate);
        }
    }//GEN-LAST:event_cmbDateActionPerformed

    private void txtBestSellerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBestSellerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBestSellerActionPerformed

    private void txtTotalIncomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalIncomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalIncomeActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new E__Admin_SALES().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cmbDate;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbl_orders;
    private javax.swing.JTextField txtBestSeller;
    private javax.swing.JTextField txtTotalIncome;
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
        field.setEditable(false);
        field.setFocusable(false);
        field.setBorder(null);
        field.setFont(new Font("Segoe UI", Font.BOLD, 18));
        field.setForeground(textColor);
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

                String text = field.getText();
                if (text != null && !text.trim().isEmpty()) {
                    g2d.setColor(textColor);
                    g2d.setFont(field.getFont());
                    FontMetrics fm = g2d.getFontMetrics();
                    int x = (w - fm.stringWidth(text)) / 2;
                    int y = (h - fm.getHeight()) / 2 + fm.getAscent();
                    g2d.drawString(text, x, y);
                }

                g2d.setColor(borderColor);
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRoundRect(0, 0, w - 1, h - 1, arc, arc);
            }
        });
    }
}
