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

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
/**
 *
 * @author moham
 */
public class E__Admin extends javax.swing.JFrame {

    // Logger for debugging and logging system messages
    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(E__Admin.class.getName());

    // Table model for orders table
    DefaultTableModel modelOrders;

    // Table model for order details table
    DefaultTableModel modelDetails;

    /**
     * Creates new form SaEles
     */
    public E__Admin() {
        
  // Initialize all Swing components
        initComponents();

        // Center the window on the screen
        setLocationRelativeTo(null);

        // Define column names for orders table
        Object[] orderColumns = {"ID", "Name", "Phone", "Date", "Total Price"};

        // Define column names for order details table
        Object[] detailColumns = {"Item", "Price", "Quantity", "Total"};

        // Create non-editable table models
        modelOrders = new NonEditableTableModel(new Object[][]{}, orderColumns);
        modelDetails = new NonEditableTableModel(new Object[][]{}, detailColumns);

        // Set models to tables
        tbl_orders.setModel(modelOrders);
        tbl_details.setModel(modelDetails);

        // Set row height for better readability
        tbl_orders.setRowHeight(40);
        tbl_details.setRowHeight(40);

        // Set font for table content
        java.awt.Font tableFont =
                new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 16);
        tbl_orders.setFont(tableFont);
        tbl_details.setFont(tableFont);

        // Set font for table headers
        java.awt.Font headerFont =
                new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 16);

        // Style orders table header
        tbl_orders.getTableHeader().setFont(headerFont);
        tbl_orders.getTableHeader().setBackground(new java.awt.Color(102, 51, 0));
        tbl_orders.getTableHeader().setForeground(java.awt.Color.WHITE);
        tbl_orders.getTableHeader().setPreferredSize(
                new java.awt.Dimension(100, 40));

        // Style details table header
        tbl_details.getTableHeader().setFont(headerFont);
        tbl_details.getTableHeader().setBackground(new java.awt.Color(102, 51, 0));
        tbl_details.getTableHeader().setForeground(java.awt.Color.WHITE);
        tbl_details.getTableHeader().setPreferredSize(
                new java.awt.Dimension(100, 40));

        // Hide vertical grid lines for cleaner UI
        tbl_orders.setShowVerticalLines(false);
        tbl_details.setShowVerticalLines(false);

        // Create renderer to center text inside table cells
        javax.swing.table.DefaultTableCellRenderer centerRenderer =
                new javax.swing.table.DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(javax.swing.JLabel.CENTER);

        // Apply center alignment to all columns in orders table
        for (int i = 0; i < tbl_orders.getColumnCount(); i++) {
            tbl_orders.getColumnModel()
                      .getColumn(i)
                      .setCellRenderer(centerRenderer);
        }

        // Apply center alignment to all columns in details table
        for (int i = 0; i < tbl_details.getColumnCount(); i++) {
            tbl_details.getColumnModel()
                       .getColumn(i)
                       .setCellRenderer(centerRenderer);
        }

        // Load all orders from the database
        loadAllOrders();

    }

    private void loadAllOrders() {
        // Clear existing rows from the orders table
        modelOrders.setRowCount(0);

        try {
            // Get database connection
            Connection con = DBConnection.getConnection();

            // SQL query to get all orders ordered by newest first
            String sql = "SELECT * FROM Orders ORDER BY ORDER_ID DESC";

            // Prepare SQL statement
            PreparedStatement pst = con.prepareStatement(sql);

            // Execute query and get result set
            ResultSet rs = pst.executeQuery();

            // Loop through all returned records
            while (rs.next()) {

                // Read order data from result set
                int id = rs.getInt("ORDER_ID");
                String name = rs.getString("CUSTOMER_NAME");
                String phone = rs.getString("PHONE_NUMBER");
                String date = rs.getString("ORDER_DATE");
                double total = rs.getDouble("TOTAL_PRICE");

                // Add order data as a new row in the table
                modelOrders.addRow(
                        new Object[]{id, name, phone, date, total});
            }

            // Close database connection
            con.close();

        } catch (Exception e) {

            // Show error message if loading fails
            JOptionPane.showMessageDialog(
                    this,
                    "Error loading orders: " + e.getMessage()
            );
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
        jButton5 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_orders = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        txt_searchID = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_details = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        btn_show = new javax.swing.JButton();
        btn_clearSearch = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton5.setBackground(new java.awt.Color(243, 207, 170));
        jButton5.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/home.png"))); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 50, 30));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("RECEIPTS");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 50, -1, -1));

        tbl_orders.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Name", "Phone", "Date", "Total Price"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tbl_orders.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_ordersMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_orders);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, 660, 530));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/basicc.jpeg"))); // NOI18N
        jLabel4.setText("jLabel4");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(-30, 0, 740, 1000));

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel2.add(txt_searchID, new org.netbeans.lib.awtextra.AbsoluteConstraints(161, 37, 144, -1));

        jLabel3.setText("ID:");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(129, 40, -1, -1));

        tbl_details.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "item", "Price", "Quantity", "Total"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tbl_details);

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 94, -1, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("RECEIPTS DETAILS\n\n");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(161, 9, -1, 22));

        btn_show.setBackground(new java.awt.Color(165, 117, 68));
        btn_show.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_show.setText("Show Details");
        btn_show.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_showActionPerformed(evt);
            }
        });
        jPanel2.add(btn_show, new org.netbeans.lib.awtextra.AbsoluteConstraints(167, 65, 138, 17));

        btn_clearSearch.setBackground(new java.awt.Color(255, 51, 51));
        btn_clearSearch.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        btn_clearSearch.setForeground(new java.awt.Color(255, 255, 255));
        btn_clearSearch.setText("Clear ID");
        btn_clearSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_clearSearchActionPerformed(evt);
            }
        });
        jPanel2.add(btn_clearSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 40, 80, 20));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_showActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_showActionPerformed
    // Get the Order ID entered by the user and remove extra spaces
    String orderID = txt_searchID.getText().trim();

    // Check if the Order ID field is empty
    if (orderID.isEmpty()) {
        // Show warning message if no Order ID is entered
        JOptionPane.showMessageDialog(this, "Please enter Order ID first! ⚠️");
        return; // Stop execution
    }

    // Clear previous order details from the table
    modelDetails.setRowCount(0);

    try {
        // Get connection to the database
        Connection con = DBConnection.getConnection();

        // SQL query to get items of a specific order
        String sql = "SELECT * FROM Order_Items WHERE ORDER_ID = ?";
        PreparedStatement pst = con.prepareStatement(sql);

        // Set the Order ID value in the query
        pst.setString(1, orderID);

        // Execute the query and get results
        ResultSet rs = pst.executeQuery();

        // Flag to check if order details exist
        boolean found = false;

        // Loop through all order items
        while (rs.next()) {
            found = true;

            // Get item data from database
            String item = rs.getString("ITEM_NAME");
            int qty = rs.getInt("QUANTITY");
            double price = rs.getDouble("PRICE");

            // Calculate total price for the item
            double totalItem = price * qty;

            // Add item data as a new row in the details table
            modelDetails.addRow(new Object[]{item, price, qty, totalItem});
        }

        // Show message if no items were found for the entered Order ID
        if (!found) {
            JOptionPane.showMessageDialog(this,
                    "No details found for Order ID: " + orderID);
        }

        // Close database connection
        con.close();

    } catch (Exception e) {
        // Show error message if something goes wrong
        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
    }

    }//GEN-LAST:event_btn_showActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        this.dispose();

        new A__Welcome().setVisible(true);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void tbl_ordersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_ordersMouseClicked
        // This method runs when the user clicks on the orders table

        // Check if the user double-clicked on the table
        if (evt.getClickCount() == 2) {

            // Get the selected row index
            int selectedRow = tbl_orders.getSelectedRow();

            // Make sure a row is selected
            if (selectedRow != -1) {

                // Get the Order ID from the first column of the selected row
                String orderID = tbl_orders.getValueAt(selectedRow, 0).toString();

                // Put the Order ID into the search text field
                txt_searchID.setText(orderID);

                // Automatically click the Show button to load order details
                btn_show.doClick();
            }
        }

    }//GEN-LAST:event_tbl_ordersMouseClicked

    private void btn_clearSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_clearSearchActionPerformed
        // This method runs when the clear search button is clicked

        // Remove all text from the search ID field
        txt_searchID.setText("");

        // Remove all rows from the details table model
        modelDetails.setRowCount(0);

        // Remove selection highlight from the orders table
        tbl_orders.clearSelection();

        // Refresh the details table to update the display
        tbl_details.revalidate();
        tbl_details.repaint();

    }//GEN-LAST:event_btn_clearSearchActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new E__Admin().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_clearSearch;
    private javax.swing.JButton btn_show;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tbl_details;
    private javax.swing.JTable tbl_orders;
    private javax.swing.JTextField txt_searchID;
    // End of variables declaration//GEN-END:variables

// This class creates a table model where cells cannot be edited
class NonEditableTableModel extends DefaultTableModel {

    // This method prevents editing any cell in the table
    @Override
    public boolean isCellEditable(int row, int column) {
        return false; // Always return false to disable editing
    }

    // Constructor that passes data and column names to the parent class
    public NonEditableTableModel(Object[][] data, Object[] columnNames) {
        super(data, columnNames);
    }
}

}
