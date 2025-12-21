package ui;

import java.awt.Color;
import javax.swing.text.JTextComponent;
// Import required SQL classes for database operations
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

// Import Swing classes for UI dialogs and tables
import javax.swing.JOptionPane;

// Import database connection class
import db.DBConnection;
import java.sql.*;
import javax.swing.*;
import java.awt.*;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
/**
 *
 * @author DELL
 */
public class E__Admin_1 extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(E__Admin_1.class.getName());

    /**
     * Creates new form LoginFrame
     */
// Constructor of the A__Welcome1 class.
// It runs automatically when an object of this class is created.
public E__Admin_1() {

    // Initializes all GUI components created by the form designer.
    initComponents();

    // Centers the window on the screen.
    setLocationRelativeTo(null);

    // Applies a custom visual style to the user count text field.
    styleField(txtUserCount);

    // Applies a custom visual style to the total revenue text field.
    styleField(txtTotalRevenue);

    // Applies a custom visual style to the order count text field.
    styleField(txtOrderCount);

    // Loads all dashboard data from the database.
    loadDashboardData();
}


// This method is responsible for reading dashboard data from the database
// and displaying it inside the dashboard fields.
public void loadDashboardData() {

    // Try block to safely handle database operations.
    try {

        // Creates a connection to the database using the DBConnection class.
        Connection con = DBConnection.getConnection();

        // Creates a Statement object to execute SQL queries.
        Statement st = con.createStatement();


        // Executes an SQL query that counts how many users exist in the Customers table.
        // SELECT count(*)      → counts all matching rows.
        // FROM Customers       → searches inside the Customers table.
        // WHERE ROLE = 'User'  → filters rows where the role is User.
        ResultSet rsUsers = st.executeQuery(
            "SELECT count(*) FROM Customers WHERE ROLE = 'User'"
        );

        // Moves the cursor to the first row of the result.
        if (rsUsers.next()) {

            // Gets the first column value (the count result)
            // and displays it in the user count text field.
            txtUserCount.setText(" Users: " + rsUsers.getInt(1));
        }

        // Closes the ResultSet to free memory.
        rsUsers.close();


        // SQL query to find the best-selling menu item.
        // SELECT m.ITEM_NAME              → selects the item name.
        // SUM(oi.QUANTITY)                → calculates total quantity sold.
        // FROM Order_Items oi             → reads from order items table.
        // JOIN Menu_Items m               → joins menu items table.
        // ON oi.ITEM_ID = m.ITEM_ID       → links items using item ID.
        // GROUP BY m.ITEM_NAME            → groups results by item name.
        // ORDER BY TotalSold DESC         → sorts by highest sales first.
        // LIMIT 1                         → returns only the top item.
        String queryBest =
            "SELECT m.ITEM_NAME, SUM(oi.QUANTITY) as TotalSold " +
            "FROM Order_Items oi " +
            "JOIN Menu_Items m ON oi.ITEM_ID = m.ITEM_ID " +
            "GROUP BY m.ITEM_NAME " +
            "ORDER BY TotalSold DESC LIMIT 1";

        // Executes the best-seller query.
        ResultSet rsBest = st.executeQuery(queryBest);

        // Checks if a best-selling item exists.
        if (rsBest.next()) {

            // Displays the name of the best-selling item.
            txtOrderCount.setText(" Best: " + rsBest.getString("ITEM_NAME"));

        } else {

            // Displays a dash if no data is found.
            txtOrderCount.setText(" Best: -");
        }

        // Closes the ResultSet.
        rsBest.close();


        // Variable to store the total number of orders.
        int totalOrders = 0;

        // Executes an SQL query to count all orders.
        // SELECT count(*) → counts all rows.
        // FROM Orders     → reads from Orders table.
        ResultSet rsOrd = st.executeQuery(
            "SELECT count(*) FROM Orders"
        );

        // Reads the count result.
        if (rsOrd.next()) {

            // Stores the total number of orders.
            totalOrders = rsOrd.getInt(1);
        }

        // Closes the ResultSet.
        rsOrd.close();


        // Variable to store total revenue.
        int totalRevenue = 0;

        // Executes an SQL query to calculate total revenue.
        // SELECT sum(TOTAL_PRICE) → adds all total prices.
        // FROM Orders             → reads from Orders table.
        ResultSet rsRev = st.executeQuery(
            "SELECT sum(TOTAL_PRICE) FROM Orders"
        );

        // Reads the revenue value.
        if (rsRev.next()) {

            // Converts the result to integer and stores it.
            totalRevenue = (int) rsRev.getDouble(1);
        }

        // Closes the ResultSet.
        rsRev.close();


        // Displays revenue and order count together in one text field.
        // Example format: "440 LE | 3 Ords"
        txtTotalRevenue.setText(
            totalRevenue + " LE | " + totalOrders + " Ords"
        );


        // Closes the database connection.
        con.close();

    } catch (Exception e) {

        // Prints a simple error message.
        System.out.println("Error: " + e);

        // Prints the full error details for debugging.
        e.printStackTrace();
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

        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        txtUserCount = new javax.swing.JTextField();
        txtTotalRevenue = new javax.swing.JTextField();
        txtOrderCount = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setBackground(new java.awt.Color(165, 117, 68));
        jButton1.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        jButton1.setText("View Receipts");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 450, 150, 30));

        jButton2.setBackground(new java.awt.Color(165, 117, 68));
        jButton2.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        jButton2.setText("MANAGE STOCK");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 450, 150, 30));

        jButton3.setBackground(new java.awt.Color(165, 117, 68));
        jButton3.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        jButton3.setText("MANAGE USERS");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 450, 140, -1));

        jButton6.setBackground(new java.awt.Color(243, 207, 170));
        jButton6.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/home.png"))); // NOI18N
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(1120, 20, 50, 40));

        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.GridLayout(1, 3, 10, 0));

        jPanel3.setOpaque(false);

        txtUserCount.setText(" Users:  5");
        txtUserCount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUserCountActionPerformed(evt);
            }
        });

        txtTotalRevenue.setText(" Revenue: 150 LE");
        txtTotalRevenue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotalRevenueActionPerformed(evt);
            }
        });

        txtOrderCount.setText(" Orders: 15");
        txtOrderCount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtOrderCountActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtUserCount, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addComponent(txtOrderCount, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(txtTotalRevenue, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUserCount, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTotalRevenue, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtOrderCount, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel3);

        jPanel2.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 330, 500, -1));

        jLabel3.setFont(new java.awt.Font("Sour Gummy SemiExpanded Thin", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 204));
        jLabel3.setText("CAFE MANAGEMENT DASHBOARD");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 300, 330, 40));

        jLabel4.setFont(new java.awt.Font("Sour Gummy SemiExpanded Thin", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 204));
        jLabel4.setText("Welcome, Admin!");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 190, 270, 40));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Zar_Cafe.jpeg"))); // NOI18N
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, -120, 1230, 1040));

        jLabel5.setFont(new java.awt.Font("Sour Gummy SemiExpanded Thin", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 204));
        jLabel5.setText("Welcome, Admin!");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 300, 270, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 1217, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 1094, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:

        E__Admin_USERS about = new E__Admin_USERS();
        about.setVisible(true);
        this.dispose();
        this.setVisible(false);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        E__Admin_SALES log = new E__Admin_SALES();
        log.setVisible(true);
        this.dispose();
        this.setVisible(false);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtUserCountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUserCountActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUserCountActionPerformed

    private void txtTotalRevenueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalRevenueActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalRevenueActionPerformed

    private void txtOrderCountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtOrderCountActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtOrderCountActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        E__Admin_MENU log = new E__Admin_MENU();
        log.setVisible(true);
        this.dispose();
        this.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new E__Admin_1().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTextField txtOrderCount;
    private javax.swing.JTextField txtTotalRevenue;
    private javax.swing.JTextField txtUserCount;
    // End of variables declaration//GEN-END:variables

    // ديه علشان الشكل بس يعني مش مهمة اوي اننا نعرف كل حاجة في الكود بتاعها
    private void styleField(JTextField field) {
        // --- 1. تعريف الألوان (النسخة الذهبية) ---
        Color bgColor = Color.decode("#FFF5D9");     // خلفية كريمية ذهبية فاتحة
        Color borderColor = Color.decode("#E67E00"); // حدود برتقالية ذهبية
        Color textColor = Color.decode("#D15B00");   // نص برتقالي ذهبي غامق

        // --- 2. تجهيز الحقل ---
        field.setOpaque(false);
        field.setEditable(false);
        field.setFocusable(false);
        field.setBorder(null);

        // --- 3. الخط والنص ---
        field.setFont(new Font("Segoe UI", Font.BOLD, 18));
        field.setForeground(textColor);
        field.setHorizontalAlignment(JTextField.CENTER);

        // --- 4. واجهة رسومية مخصصة لرسم الخلفية والحدود والنص ---
        field.setUI(new javax.swing.plaf.basic.BasicTextFieldUI() {
            @Override
            protected void paintSafely(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int w = field.getWidth();
                int h = field.getHeight();
                int arc = 40; // نصف قطر الزوايا (قابل للتعديل)

                // ✅ رسم الخلفية الذهبية
                g2d.setColor(bgColor);
                g2d.fillRoundRect(0, 0, w, h, arc, arc);

                // ✅ رسم النص
                String text = field.getText();
                if (text != null && !text.trim().isEmpty()) {
                    g2d.setColor(textColor);
                    g2d.setFont(field.getFont());
                    FontMetrics fm = g2d.getFontMetrics();
                    int x = (w - fm.stringWidth(text)) / 2;
                    int y = (h - fm.getHeight()) / 2 + fm.getAscent();
                    g2d.drawString(text, x, y);
                }

                // ✅ رسم الحدود الذهبية
                g2d.setColor(borderColor);
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRoundRect(0, 0, w - 1, h - 1, arc, arc);
            }
        });
    }
}
