package ui;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
/**
 *
 * @author Rawan
 */
// Import database connection class
import db.DBConnection;

// Import event handling classes
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Import SQL classes
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

// Import Swing UI classes
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;

// Import date and time classes
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

// Import layout and UI helper classes
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.text.JTextComponent;

public class D__Order extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(D__Order.class.getName());

    /**
     * Creates new form NewJFrame
     */
    // Declare a table model for the receipt table
    DefaultTableModel receitmodel;

    // Constructor that takes customer phone number as parameter
    public D__Order(String phone) {
        // Initialize all form components
        initComponents();
        // Center the window on screen
        setLocationRelativeTo(null);

        // Get the table model from the receipt table and assign it
        receitmodel = (DefaultTableModel) tbl_receipt.getModel();
        // Set row height to 30 pixels for better visibility
        tbl_receipt.setRowHeight(30);

        // Change header font to bold size 14
        tbl_receipt.getTableHeader().setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
        // Set header background color to brown
        tbl_receipt.getTableHeader().setBackground(new java.awt.Color(102, 51, 0)); // brown color
        // Set header text color to white
        tbl_receipt.getTableHeader().setForeground(java.awt.Color.WHITE); // white text

        // Apply styling to customer name and phone text fields
        styleField(txt_custName);
        styleField(txt_custPhone);

        // Apply styling to action buttons
        styleButton(btn_saveOrder);
        styleButton(btn_reset);
        styleButton(jButton3);

        // Load customer data based on provided phone number
        loadUserData(phone);
        // Load customer's order history based on provided phone number
        loadCustomerHistory(phone);

    }

    private void loadUserData(String phone) {
        // Try to connect to database and load customer information

        try {
            // Get a connection to the database
            Connection con = DBConnection.getConnection();

            // Search for the customer in the Customers table using phone number
            String sql = "SELECT * FROM Customers WHERE PHONE_NUMBER = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, phone);

            // Execute the query and get the results
            ResultSet rs = pst.executeQuery();

            // Check if a customer record is found
            if (rs.next()) {
                // Customer found! Fill the text fields with their data
                txt_custPhone.setText(phone); // Phone number we already have
                txt_custName.setText(rs.getString("USER_NAME")); // Name from database

                // Make fields uneditable so customer data is not changed by mistake (optional)
                txt_custName.setEditable(false);
                txt_custPhone.setEditable(false);
            }

            // Close the database connection
            con.close();

        } catch (Exception e) {
            // Print error message if something goes wrong
            System.out.println("Error loading user data: " + e.getMessage());
        }
    }

    private void addItem(String itemName, int price) {
        // Flag to check if the item already exists in the receipt table
        boolean found = false;

        // Loop through all rows in the receipt table
        for (int i = 0; i < receitmodel.getRowCount(); i++) {

            // Get the item name from the current row
            String existingName = receitmodel.getValueAt(i, 0).toString();

            // Check if the item already exists (case-insensitive)
            if (existingName.equalsIgnoreCase(itemName)) {

                // Get the old quantity of the item
                int oldQ = Integer.parseInt(receitmodel.getValueAt(i, 1).toString());

                // Increase quantity by 1
                int newQ = oldQ + 1;

                // Calculate new total cost for this item
                int newcost = newQ * price;

                // Update quantity and cost in the table
                receitmodel.setValueAt(newQ, i, 1);
                receitmodel.setValueAt(newcost, i, 2);

                // Mark as found and stop the loop
                found = true;
                break;
            }
        }

        // If the item was not found in the table, add it as a new row
        if (!found) {
            int q = 1;          // Initial quantity
            int cost = q * price; // Calculate cost

            // Add new item to the receipt table
            receitmodel.addRow(new Object[]{itemName, q, cost});
        }

        // Update the total label to reflect the new total price
        updateTotalLabel();
    }

    private int updateTotalLabel() {
        // Get the number of rows in the receipt table
        int rows = receitmodel.getRowCount();

        // Initialize total price
        int total = 0;

        // Loop through all rows to sum up the cost column (column index 2)
        for (int i = 0; i < rows; i++) {
            int costt = Integer.parseInt(receitmodel.getValueAt(i, 2).toString());
            total += costt; // Add each item's total cost to the total
        }

        // Display the total price in the Total label
        Total.setText(String.valueOf(total));

        // Return the total value
        return total;
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
        txt_history = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txt_custName = new javax.swing.JTextField();
        txt_custPhone = new javax.swing.JTextField();
        panel1 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        btn_addCoffee = new javax.swing.JButton();
        btn_addIceCream = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        btn_addCake = new javax.swing.JButton();
        btn_addLatte = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        btn_addTea = new javax.swing.JButton();
        btn_addSandwich = new javax.swing.JButton();
        c = new javax.swing.JLabel();
        l = new javax.swing.JLabel();
        t = new javax.swing.JLabel();
        i = new javax.swing.JLabel();
        k = new javax.swing.JLabel();
        s = new javax.swing.JLabel();
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

        txt_history.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_historyActionPerformed(evt);
            }
        });
        getContentPane().add(txt_history, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 80, 720, 40));

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Last Orders History");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, -1, 22));

        jButton5.setBackground(new java.awt.Color(243, 207, 170));
        jButton5.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/home.png"))); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 20, 50, 40));

        jLabel1.setBackground(new java.awt.Color(255, 255, 204));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 51, 0));
        jLabel1.setText("ORDER PAGE");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 0, -1, 31));

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText(" Customer Name :");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, -1, 22));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Phone Number :");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 40, 121, -1));
        getContentPane().add(txt_custName, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 40, 201, -1));

        txt_custPhone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_custPhoneActionPerformed(evt);
            }
        });
        getContentPane().add(txt_custPhone, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 40, 201, -1));

        panel1.setBackground(new java.awt.Color(255, 255, 255));
        panel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel7.setFont(new java.awt.Font("Segoe UI Black", 3, 24)); // NOI18N
        jLabel7.setText("Menu");

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/coffee.jpg"))); // NOI18N
        jLabel5.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(102, 51, 0), new java.awt.Color(102, 51, 0), new java.awt.Color(102, 51, 0), new java.awt.Color(102, 51, 0)));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/latte.jpg"))); // NOI18N
        jLabel8.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(102, 51, 0), new java.awt.Color(102, 51, 0), new java.awt.Color(102, 51, 0), new java.awt.Color(102, 51, 0)));

        jLabel6.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        jLabel6.setText("Coffee");

        jLabel9.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        jLabel9.setText("Latte");

        btn_addCoffee.setBackground(new java.awt.Color(102, 102, 102));
        btn_addCoffee.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btn_addCoffee.setText("Add");
        btn_addCoffee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addCoffeeActionPerformed(evt);
            }
        });

        btn_addIceCream.setBackground(new java.awt.Color(102, 102, 102));
        btn_addIceCream.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btn_addIceCream.setText("Add");
        btn_addIceCream.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addIceCreamActionPerformed(evt);
            }
        });

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/tea.jpg"))); // NOI18N
        jLabel10.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(102, 51, 0), new java.awt.Color(102, 51, 0), new java.awt.Color(102, 51, 0), new java.awt.Color(102, 51, 0)));

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/ice_cream.jpg"))); // NOI18N
        jLabel11.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(102, 51, 0), new java.awt.Color(102, 51, 0), new java.awt.Color(102, 51, 0), new java.awt.Color(102, 51, 0)));

        jLabel12.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        jLabel12.setText("Tea");

        jLabel13.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        jLabel13.setText("Ice Cream");

        btn_addCake.setBackground(new java.awt.Color(102, 102, 102));
        btn_addCake.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btn_addCake.setLabel("Add");
        btn_addCake.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addCakeActionPerformed(evt);
            }
        });

        btn_addLatte.setBackground(new java.awt.Color(102, 102, 102));
        btn_addLatte.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btn_addLatte.setText("Add");
        btn_addLatte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addLatteActionPerformed(evt);
            }
        });

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cake.jpg"))); // NOI18N
        jLabel14.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(102, 51, 0), new java.awt.Color(102, 51, 0), new java.awt.Color(102, 51, 0), new java.awt.Color(102, 51, 0)));

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/sandwich.jpg"))); // NOI18N
        jLabel15.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(102, 51, 0), new java.awt.Color(102, 51, 0), new java.awt.Color(102, 51, 0), new java.awt.Color(102, 51, 0)));

        jLabel16.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        jLabel16.setText("Cake");

        jLabel17.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        jLabel17.setText("Sandwich");

        btn_addTea.setBackground(new java.awt.Color(102, 102, 102));
        btn_addTea.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btn_addTea.setText("Add");
        btn_addTea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addTeaActionPerformed(evt);
            }
        });

        btn_addSandwich.setBackground(new java.awt.Color(102, 102, 102));
        btn_addSandwich.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btn_addSandwich.setText("Add");
        btn_addSandwich.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addSandwichActionPerformed(evt);
            }
        });

        c.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        c.setText("50");

        l.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        l.setText("100");

        t.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        t.setText("40");

        i.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        i.setText("30");

        k.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        k.setText("80");

        s.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        s.setText("110");

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btn_addCake)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(k)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17)
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addComponent(btn_addSandwich)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(s)))
                .addGap(60, 60, 60))
            .addGroup(panel1Layout.createSequentialGroup()
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panel1Layout.createSequentialGroup()
                            .addGap(42, 42, 42)
                            .addComponent(btn_addTea)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(t))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                            .addGap(5, 5, 5)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addGap(78, 78, 78)
                        .addComponent(jLabel12))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel13)
                                    .addGroup(panel1Layout.createSequentialGroup()
                                        .addComponent(btn_addIceCream, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(i)))
                                .addGap(75, 75, 75))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28))))
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
            .addGroup(panel1Layout.createSequentialGroup()
                .addGap(175, 175, 175)
                .addComponent(jLabel7)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel9))
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(btn_addCoffee)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(c)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_addLatte)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(l)))
                .addGap(79, 79, 79))
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(1, 1, 1)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(3, 3, 3)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_addCoffee)
                    .addComponent(c)
                    .addComponent(btn_addLatte)
                    .addComponent(l))
                .addGap(5, 5, 5)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_addIceCream)
                            .addComponent(i))
                        .addGap(3, 3, 3)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_addTea)
                            .addComponent(t))
                        .addGap(3, 3, 3)
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(3, 3, 3)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_addSandwich)
                    .addComponent(btn_addCake)
                    .addComponent(k)
                    .addComponent(s))
                .addGap(3, 3, 3))
        );

        getContentPane().add(panel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 130, 390, -1));

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
        getContentPane().add(btn_saveOrder, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 440, 80, 33));

        btn_reset.setBackground(new java.awt.Color(165, 117, 68));
        btn_reset.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        btn_reset.setText("Reset");
        btn_reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_resetActionPerformed(evt);
            }
        });
        getContentPane().add(btn_reset, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 480, 80, 33));

        btn_delete.setBackground(new java.awt.Color(255, 51, 51));
        btn_delete.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        btn_delete.setForeground(new java.awt.Color(255, 255, 255));
        btn_delete.setText("Delete Item");
        btn_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteActionPerformed(evt);
            }
        });
        getContentPane().add(btn_delete, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 530, 120, 33));

        Total.setBackground(new java.awt.Color(165, 117, 68));
        Total.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        Total.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        getContentPane().add(Total, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 400, 50, 20));

        jLabel20.setFont(new java.awt.Font("Segoe UI Black", 1, 22)); // NOI18N
        jLabel20.setText("Total");
        getContentPane().add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 370, 70, 20));

        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/basicc.jpeg"))); // NOI18N
        getContentPane().add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -30, 890, 840));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_addCakeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addCakeActionPerformed
        addItem("Cake", 80);

    }//GEN-LAST:event_btn_addCakeActionPerformed

    private void btn_addSandwichActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addSandwichActionPerformed
        addItem("Sandwich", 110);

    }//GEN-LAST:event_btn_addSandwichActionPerformed

    private void btn_addTeaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addTeaActionPerformed
        addItem("Tea", 40);

    }//GEN-LAST:event_btn_addTeaActionPerformed

    private void btn_addLatteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addLatteActionPerformed
        addItem("Latte", 100);

    }//GEN-LAST:event_btn_addLatteActionPerformed

    private void btn_addIceCreamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addIceCreamActionPerformed
        addItem("Ice Cream", 30);

    }//GEN-LAST:event_btn_addIceCreamActionPerformed

    private void btn_addCoffeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addCoffeeActionPerformed
        addItem("Coffee", 50);

    }//GEN-LAST:event_btn_addCoffeeActionPerformed

    private void btn_saveOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_saveOrderActionPerformed
        // 1. Calculate the total price of the order
        int total = updateTotalLabel();

        // If the order is empty, show a warning and stop
        if (total == 0) {
            JOptionPane.showMessageDialog(this, "Order is empty!");
            return;
        }

        Connection con = null;
        try {
            // Get database connection
            con = DBConnection.getConnection();

            // ---------------------------------------------------------
            // Step 1: Save the main order (order header)
            // ---------------------------------------------------------
            // SQL query to insert basic order information
            String sqlOrder = "INSERT INTO Orders (CUSTOMER_NAME, PHONE_NUMBER, ORDER_DATE, TOTAL_PRICE) VALUES (?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(sqlOrder);

            // Set order values
            pst.setString(1, txt_custName.getText().trim());
            pst.setString(2, txt_custPhone.getText().trim());
            pst.setDate(3, java.sql.Date.valueOf(LocalDate.now())); // current date
            pst.setInt(4, total); // total price

            // Execute insert
            pst.executeUpdate();

            // ---------------------------------------------------------
            // Step 2: Get the ORDER_ID of the last inserted order
            // ---------------------------------------------------------
            // Query to get the highest order ID (which will be the most recent one)
            int lastOrderId = 0;
            String sqlGetID = "SELECT MAX(ORDER_ID) FROM Orders";
            PreparedStatement pstID = con.prepareStatement(sqlGetID);
            // Execute the query and get results
            ResultSet rs = pstID.executeQuery();
            if (rs.next()) {
                // Get the maximum order ID from the result
                lastOrderId = rs.getInt(1);
            }

            // ---------------------------------------------------------
            // Step 3: Save order details (all items in the receipt table)
            // ---------------------------------------------------------
            // Loop through each row in the receipt table
            for (int i = 0; i < receitmodel.getRowCount(); i++) {
                // Get item name from first column
                String itemName = receitmodel.getValueAt(i, 0).toString();
                // Get quantity from second column and convert to integer
                int quantity = Integer.parseInt(receitmodel.getValueAt(i, 1).toString());
                // Get price from third column and convert to integer
                int price = Integer.parseInt(receitmodel.getValueAt(i, 2).toString());

                // SQL query to insert order item details
                String sqlDetail = "INSERT INTO Order_Items (ORDER_ID, ITEM_NAME, QUANTITY, PRICE) VALUES (?, ?, ?, ?)";
                PreparedStatement pstDetail = con.prepareStatement(sqlDetail);

                // Set the values for the item detail parameters
                pstDetail.setInt(1, lastOrderId); // Link to the order ID
                pstDetail.setString(2, itemName);
                pstDetail.setInt(3, quantity);
                pstDetail.setInt(4, price);

                // Execute the insertion query for the item
                pstDetail.executeUpdate();
            }
            // ---------------------------------------------------------
            // Step 4: Show success message and reset receipt
            // ---------------------------------------------------------
            JOptionPane.showMessageDialog(this, "Order Saved Successfully! ✅\nOrder ID: " + lastOrderId);

            // Clear the receipt table and reset total
            receitmodel.setRowCount(0);
            Total.setText("0");

            // Close database connection
            con.close();

        } catch (Exception e) {
            // Show error message if something goes wrong
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }

    }//GEN-LAST:event_btn_saveOrderActionPerformed

    private void btn_resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_resetActionPerformed
        receitmodel.setRowCount(0);
        Total.setText("0");
    }//GEN-LAST:event_btn_resetActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        this.dispose();

        new A__Welcome().setVisible(true);        // TODO add your handling code here:
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

    private void txt_historyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_historyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_historyActionPerformed

    private void txt_custPhoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_custPhoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_custPhoneActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new D__Order(phone).setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Total;
    private javax.swing.JButton btn_addCake;
    private javax.swing.JButton btn_addCoffee;
    private javax.swing.JButton btn_addIceCream;
    private javax.swing.JButton btn_addLatte;
    private javax.swing.JButton btn_addSandwich;
    private javax.swing.JButton btn_addTea;
    private javax.swing.JButton btn_delete;
    private javax.swing.JButton btn_reset;
    private javax.swing.JButton btn_saveOrder;
    private javax.swing.JLabel c;
    private javax.swing.JLabel i;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel k;
    private javax.swing.JLabel l;
    private javax.swing.JPanel orderpanel;
    private javax.swing.JPanel panel1;
    private javax.swing.JLabel s;
    private javax.swing.JLabel t;
    private javax.swing.JTable tbl_receipt;
    private javax.swing.JTextField txt_custName;
    private javax.swing.JTextField txt_custPhone;
    private javax.swing.JTextField txt_history;
    // End of variables declaration//GEN-END:variables

    private void loadCustomerHistory(String phoneNumber) {
        // Create a string builder to build the history text
        StringBuilder history = new StringBuilder("--- Last 3 Orders History ---\n");

        try (Connection con = DBConnection.getConnection()) {
            // SQL query to get the last 3 orders for the customer, ordered by date (newest first)
            String sql = "SELECT * FROM Orders WHERE PHONE_NUMBER = ? ORDER BY ORDER_DATE DESC LIMIT 3";
            // Prepare the statement with the query
            PreparedStatement pst = con.prepareStatement(sql);
            // Set the phone number parameter in the query
            pst.setString(1, phoneNumber);
            // Execute the query and get results
            ResultSet rs = pst.executeQuery();

            // Counter to track how many orders we've processed
            int count = 0;
            // Loop through each order result
            while (rs.next()) {
                count++;
                // Get the order date from the result
                String date = rs.getString("ORDER_DATE");
                // Get the total price from the result
                double total = rs.getDouble("TOTAL_PRICE");

                // Add formatted order information to the history string
                history.append(String.format("Order %d: Date: %s | Total: %.2f EGP\n", count, date, total));
            }

            // If no orders were found for this customer
            if (count == 0) {
                // Add message indicating no previous orders
                history.append("No previous orders found for this customer.");
            }

            // Set the complete history text in the history text area
            txt_history.setText(history.toString());

        } catch (Exception e) {
            // Print error message if something goes wrong
            System.err.println("Error loading history: " + e.getMessage());
        }
    }

    // ديه علشان الشكل بس يعني مش مهمة اوي اننا نعرف كل حاجة في الكود بتاعها
    // Method to apply custom styling to text fields   
    private void styleField(JTextComponent field) {
        // Define brown color for borders and accents
        java.awt.Color brownColor = new java.awt.Color(102, 51, 0);

        // Define tan highlight color for active states
        java.awt.Color tanHighlight = new java.awt.Color(255, 224, 178);

        // Make the field transparent
        field.setOpaque(false);
        // Set background to fully transparent black
        field.setBackground(new java.awt.Color(0, 0, 0, 0));

        // Set font to bold size 15
        field.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 15));
        // Set text color to tan highlight
        field.setForeground(tanHighlight);

        // If the component is a text field, center the text horizontally
        if (field instanceof javax.swing.JTextField) {
            ((javax.swing.JTextField) field).setHorizontalAlignment(javax.swing.JTextField.CENTER);
        }

        // Apply custom rounded border with specified radius and color
        field.setBorder(new RoundedBorder(30, brownColor));

        // Add focus listener to change border color when field gains/loses focus
        field.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                // Change border to tan highlight when focused
                field.setBorder(new RoundedBorder(30, tanHighlight));
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                // Change border back to brown when focus is lost
                field.setBorder(new RoundedBorder(30, brownColor));
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
