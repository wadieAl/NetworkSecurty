package N_Securty;

import DAO.UserDao;
import Entities.AESAlgorithm;
import Entities.UsersItems;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Users extends javax.swing.JFrame {

    int xMouse;
    int yMouse;
    AESAlgorithm aesAlgo;
    private byte[] keyValue = new byte[]{'T', 'h', 'e', 'B', 'e', 's', 't', 'S', 'e', 'c', 'r', 'e', 't', 'K', 'e', 'y'};

    public Users() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ImageIcon img = new ImageIcon(getClass().getResource("/IMAGES/appLogo.png"));       
        this.setIconImage(img.getImage());
        this.txtname.requestFocusInWindow();
        aesAlgo = new AESAlgorithm(keyValue);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel_close = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtname = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        btnSave = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        txtPass = new javax.swing.JPasswordField();
        jPanel3 = new javax.swing.JPanel();
        jCheckBox_NIP = new javax.swing.JCheckBox();
        jCheckBox_AlowedIPs = new javax.swing.JCheckBox();
        jCheckBox_Programs = new javax.swing.JCheckBox();
        jCheckBox_Users = new javax.swing.JCheckBox();
        jCheckBox_ClosePort = new javax.swing.JCheckBox();
        jCheckBox_Sites = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(0, 0, 102));
        jPanel1.setPreferredSize(new java.awt.Dimension(790, 84));
        jPanel1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jPanel1MouseDragged(evt);
            }
        });
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel1MousePressed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 0));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel14.setText("N-Securty");

        jLabel_close.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_close.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/x.png"))); // NOI18N
        jLabel_close.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel_close.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel_closeMousePressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel_close, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel_close, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.setPreferredSize(new java.awt.Dimension(790, 250));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("كلمة السر :");

        jLabel1.setFont(new java.awt.Font("Arabic Typesetting", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 102));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("إضافة مستخدم جديد");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        txtname.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtname.setForeground(new java.awt.Color(153, 0, 0));
        txtname.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtname.setBorder(null);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("الاسم :");

        btnSave.setBackground(new java.awt.Color(0, 0, 102));
        btnSave.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnSave.setForeground(new java.awt.Color(255, 255, 255));
        btnSave.setText("حفظ");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        jSeparator1.setBackground(new java.awt.Color(0, 0, 102));

        jSeparator2.setBackground(new java.awt.Color(0, 0, 102));

        txtPass.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPass.setBorder(null);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "الـــصـــــــــلاحـــيــــــات", 0, 0, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        jCheckBox_NIP.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox_NIP.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jCheckBox_NIP.setSelected(true);
        jCheckBox_NIP.setText("تعديل IP الشبكة");
        jCheckBox_NIP.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jCheckBox_NIP.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jCheckBox_NIP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox_NIPActionPerformed(evt);
            }
        });

        jCheckBox_AlowedIPs.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox_AlowedIPs.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jCheckBox_AlowedIPs.setSelected(true);
        jCheckBox_AlowedIPs.setText("الأيبيات المسموحة");
        jCheckBox_AlowedIPs.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jCheckBox_AlowedIPs.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jCheckBox_AlowedIPs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox_AlowedIPsActionPerformed(evt);
            }
        });

        jCheckBox_Programs.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox_Programs.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jCheckBox_Programs.setSelected(true);
        jCheckBox_Programs.setText("حظر البرامج");
        jCheckBox_Programs.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jCheckBox_Programs.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jCheckBox_Programs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox_ProgramsActionPerformed(evt);
            }
        });

        jCheckBox_Users.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox_Users.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jCheckBox_Users.setSelected(true);
        jCheckBox_Users.setText("تعديل المستخدمين");
        jCheckBox_Users.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jCheckBox_Users.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jCheckBox_Users.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox_UsersActionPerformed(evt);
            }
        });

        jCheckBox_ClosePort.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox_ClosePort.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jCheckBox_ClosePort.setSelected(true);
        jCheckBox_ClosePort.setText("اغلاق المنافذ");
        jCheckBox_ClosePort.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jCheckBox_ClosePort.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jCheckBox_ClosePort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox_ClosePortActionPerformed(evt);
            }
        });

        jCheckBox_Sites.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox_Sites.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jCheckBox_Sites.setSelected(true);
        jCheckBox_Sites.setText("حظر المواقع");
        jCheckBox_Sites.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jCheckBox_Sites.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jCheckBox_Sites.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox_SitesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(79, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jCheckBox_Sites)
                    .addComponent(jCheckBox_Users)
                    .addComponent(jCheckBox_Programs))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCheckBox_NIP, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jCheckBox_AlowedIPs, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jCheckBox_ClosePort, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jCheckBox_Users)
                        .addGap(18, 18, 18)
                        .addComponent(jCheckBox_Programs))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jCheckBox_NIP)
                        .addGap(18, 18, 18)
                        .addComponent(jCheckBox_AlowedIPs)))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCheckBox_Sites)
                    .addComponent(jCheckBox_ClosePort))
                .addContainerGap(9, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jSeparator2)
                                    .addComponent(txtname, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(215, 215, 215)
                        .addComponent(jLabel1))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(245, 245, 245)
                        .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(14, 14, 14))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtname, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2))
                                .addGap(0, 0, 0)
                                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel3)
                                .addGap(0, 0, 0)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 666, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 666, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3))
        );

        setSize(new java.awt.Dimension(666, 287));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed

        try {

            if (txtname.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "أدخل اسم المستخدم ");
            } else {
                String name = this.txtname.getText();
                String Pass = this.txtPass.getText();
                String PassEncrypted = aesAlgo.encrypt(Pass);
                boolean users = this.jCheckBox_Users.isSelected();
                boolean Nip = this.jCheckBox_NIP.isSelected();
                boolean AlowedIPs = this.jCheckBox_AlowedIPs.isSelected();
                boolean ClosePort = this.jCheckBox_ClosePort.isSelected();
                boolean Programs = this.jCheckBox_Programs.isSelected();
                boolean Sites = this.jCheckBox_Sites.isSelected();

                UsersItems item = new UsersItems();
                item.setUserNam(name);
                item.setUserPass(PassEncrypted);
                item.setUsers(users);
                item.setNetWork_IP(Nip);
                item.setAlwod_IPs(AlowedIPs);
                item.setClose_Port(ClosePort);
                item.setPrograms(Programs);
                item.setSites(Sites);

                UserDao AddDao = new UserDao();
                int result = AddDao.InsertItems(item);
                if (result != 0) {
                    JOptionPane.showMessageDialog(null, "تمت الإضافة بنجاح");
                } else {
                    JOptionPane.showMessageDialog(null, "هناك خطأ في قاعدة البيانات الرجاء التواصل مع المبرمج");
                }
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void jPanel1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - xMouse, y - yMouse);
    }//GEN-LAST:event_jPanel1MouseDragged

    private void jPanel1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MousePressed
        xMouse = evt.getX();
        yMouse = evt.getY();
    }//GEN-LAST:event_jPanel1MousePressed

    private void jCheckBox_NIPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox_NIPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox_NIPActionPerformed

    private void jCheckBox_AlowedIPsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox_AlowedIPsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox_AlowedIPsActionPerformed

    private void jCheckBox_ProgramsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox_ProgramsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox_ProgramsActionPerformed

    private void jCheckBox_UsersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox_UsersActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox_UsersActionPerformed

    private void jCheckBox_ClosePortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox_ClosePortActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox_ClosePortActionPerformed

    private void jCheckBox_SitesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox_SitesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox_SitesActionPerformed

    private void jLabel_closeMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_closeMousePressed
        this.dispose();
    }//GEN-LAST:event_jLabel_closeMousePressed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            new Users().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSave;
    private javax.swing.JCheckBox jCheckBox_AlowedIPs;
    private javax.swing.JCheckBox jCheckBox_ClosePort;
    private javax.swing.JCheckBox jCheckBox_NIP;
    private javax.swing.JCheckBox jCheckBox_Programs;
    private javax.swing.JCheckBox jCheckBox_Sites;
    private javax.swing.JCheckBox jCheckBox_Users;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel_close;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JPasswordField txtPass;
    private javax.swing.JTextField txtname;
    // End of variables declaration//GEN-END:variables
}
