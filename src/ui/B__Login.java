package ui;

// Import SQL classes for database operations
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

// Import Swing classes for dialogs
import javax.swing.JOptionPane;

// Import database connection class
import db.DBConnection;

// Import text component class for styling fields
import javax.swing.text.JTextComponent;

public class B__Login extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(B__Login.class.getName());
    public B__Login() {
        initComponents();
        // Center the window on screen
        setLocationRelativeTo(null);
        
        // Apply styling to username and password fields
        styleField(txt_username);
        styleField(txt_password);    
        
        // Apply styling to all buttons
        styleButton(btn_login);
        styleButton(btn_signup);
        styleButton(jButton3);
        
        // Set up tab navigation: when Enter is pressed in username field, move to password field
        txt_username.addActionListener(e -> txt_password.requestFocus());

    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btn_signup = new javax.swing.JButton();
        btn_login = new javax.swing.JButton();
        txt_password = new javax.swing.JPasswordField();
        jButton3 = new javax.swing.JButton();
        txt_username = new javax.swing.JTextField();
        chk_showPass = new javax.swing.JCheckBox();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_signup.setBackground(new java.awt.Color(165, 117, 68));
        btn_signup.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btn_signup.setText("CREATE NEW ACCOUNT");
        btn_signup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_signupActionPerformed(evt);
            }
        });
        jPanel1.add(btn_signup, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 630, 260, 40));

        btn_login.setBackground(new java.awt.Color(165, 117, 68));
        btn_login.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btn_login.setText("LOG IN");
        btn_login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_loginActionPerformed(evt);
            }
        });
        jPanel1.add(btn_login, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 570, 260, 40));

        txt_password.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_passwordActionPerformed(evt);
            }
        });
        jPanel1.add(txt_password, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 460, 260, 40));

        jButton3.setBackground(new java.awt.Color(243, 207, 170));
        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jButton3.setText("←");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 70, 60, 30));

        txt_username.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_usernameActionPerformed(evt);
            }
        });
        jPanel1.add(txt_username, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 380, 260, 40));

        chk_showPass.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        chk_showPass.setText("show password");
        chk_showPass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chk_showPassActionPerformed(evt);
            }
        });
        jPanel1.add(chk_showPass, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 500, -1, 20));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Phone Number:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 360, 130, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Password:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 440, 90, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/basicc.jpeg"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(154, 0, 1052, 841));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variable to track whether password is currently visible or hidden
    private boolean show = false;
    private void chk_showPassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chk_showPassActionPerformed
       // If password is currently hidden, show it
       if (!show){
           // Show actual password characters (no masking)
           txt_password.setEchoChar((char)0);
           // Update flag to indicate password is now visible
           show=true;
          
       }
       // If password is currently visible, hide it
       else{
           // Mask password with asterisks (*)
           txt_password.setEchoChar('*');
           // Update flag to indicate password is now hidden
           show=false;
       }
    }//GEN-LAST:event_chk_showPassActionPerformed

    private void txt_passwordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_passwordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_passwordActionPerformed

    private void btn_loginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_loginActionPerformed
  
        // Get and clean up the login credentials from form fields
   String phone = txt_username.getText().trim(); // Get phone number and remove extra spaces
   String pass = new String(txt_password.getPassword()).trim(); // Get password and remove extra spaces

   // Check if either phone number or password field is empty
   if (phone.isEmpty() || pass.isEmpty()) {
       // Show message asking user to enter both credentials
       JOptionPane.showMessageDialog(this, "Please enter Phone Number and Password");
       return; // Stop execution if fields are empty
   }

   try {
       // Connect to the database
       Connection con = DBConnection.getConnection();
       // SQL query to find customer with matching phone and password
       String sql = "SELECT * FROM Customers WHERE PHONE_NUMBER = ? AND PASSWORD = ?";
       // Prepare the statement with the query
       PreparedStatement pst = con.prepareStatement(sql);
       
       // Set the phone number and password parameters in the query
       pst.setString(1, phone);
       pst.setString(2, pass);
       
       // Execute the query and get results
       ResultSet rs = pst.executeQuery();
       
       // If a matching customer record was found
       if (rs.next()) {
           // Get the user role from the database
           String role = rs.getString("ROLE");
           
           // Close current login window
           this.dispose();

           // Check the user's role to determine which interface to open
           if (role != null && role.equalsIgnoreCase("Admin")) {
               // Open admin interface if user is admin
               new E__Admin().setVisible(true); 
           } else {
               // Open regular order interface for regular users, passing phone number
               new D__Order(phone).setVisible(true); 
           }
           
       } else {
           // Show message if no matching credentials were found
           JOptionPane.showMessageDialog(this, "Invalid Phone Number or Password");
       }
       
       // Close the database connection
       con.close();

   } catch (Exception e) {
       // Show error message if something goes wrong
       JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
   }
      
    }//GEN-LAST:event_btn_loginActionPerformed

    private void btn_signupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_signupActionPerformed
        // TODO add your handling code here:
              B__Signup sign=new 
            B__Signup();
        sign.setVisible(true);
        this.dispose();
        this.setVisible(false);
    }//GEN-LAST:event_btn_signupActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        this.dispose();

        new A__Welcome().setVisible(true);     
    }//GEN-LAST:event_jButton3ActionPerformed

    private void txt_usernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_usernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_usernameActionPerformed

  
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
        java.awt.EventQueue.invokeLater(() -> new B__Login().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_login;
    private javax.swing.JButton btn_signup;
    private javax.swing.JCheckBox chk_showPass;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField txt_password;
    private javax.swing.JTextField txt_username;
    // End of variables declaration//GEN-END:variables

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
