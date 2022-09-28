/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package haydtpc04386_assignment_gd2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author dutru
 */
public class AccountManager extends javax.swing.JFrame {

    /**
     * Creates new form AccountManager
     */
    public AccountManager() {
        initComponents();
        setResizable(false);
        initData();
        LoadDataToComboBox();
        CheckVaiTro.checkVaiTro = "Quản lý";
    }
    static String name ="UserAdmin";
    static String pass = "123456";
    static String url = "jdbc:sqlserver://localhost:1433;databaseName=HayDTPC04386_QLySanPham_DoDungHocTap;encrypt=true;trustServerCertificate=true";
    /**
     * Creates new form SanPhamAll
     */
    
    String head[] = {"Tên tài khoản", "Mật khẩu", "Vai trò"};
    DefaultTableModel model = new DefaultTableModel(head, 0);
    Scanner sc = new Scanner(System.in);
    int index = 0;
    public void initData() {
        try {
            model.setRowCount(0);
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection(url, name, pass);
            Statement st = con.createStatement();
            String sql = "SELECT * FROM NGUOIDUNG";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Vector row = new Vector();
                row.add(rs.getString(1));
                row.add(rs.getString(2));
                row.add(rs.getString(3));

                model.addRow(row);
            }
            tblTaiKhoan.setModel(model);
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public void fillForm(){
        int row = tblTaiKhoan.getSelectedRow();
        txtTenTaiKhoan.setText(tblTaiKhoan.getValueAt(row, 0).toString());
        txtMatKhau.setText(tblTaiKhoan.getValueAt(row, 1).toString());
        cboVaiTro.setSelectedItem(tblTaiKhoan.getValueAt(row, 2));
    }
    public void LoadDataToComboBox(){
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection(url, name, pass);
            Statement st = con.createStatement();
            String sql = "SELECT VAITRO FROM dbo.NGUOIDUNG";
            ResultSet rs = st.executeQuery(sql);
            String check = null;
            while(rs.next()){
                String s = rs.getString(1);
                if (s.equals(check) == false){
                    cboVaiTro.addItem(s);
                    check = s;
                }
                
            }
            con.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    public String viTriBanGhi() {
        int viTri = tblTaiKhoan.getSelectedRow();
        return (viTri + 1) + " Trên " + (tblTaiKhoan.getRowCount());
    }
    public void firstSP() {
        index = 0;
        tblTaiKhoan.setRowSelectionInterval(index, index);
        fillForm();
        lblBanGhi.setText(viTriBanGhi());
    }

    public void preSP() {
        if (tblTaiKhoan.getRowCount() != 0) {
            index = tblTaiKhoan.getSelectedRow();
            if (index == 0) {
                lastSP();
            } else {
                index--;
            }
            tblTaiKhoan.setRowSelectionInterval(index, index);
            fillForm();
            lblBanGhi.setText(viTriBanGhi());
        }
    }

    public void lastSP() {
        index = tblTaiKhoan.getRowCount() - 1;
        tblTaiKhoan.setRowSelectionInterval(index, index);
        fillForm();
        lblBanGhi.setText(viTriBanGhi());

    }

    public void nextSP() {
        if (tblTaiKhoan.getRowCount() != 0) {
            index = tblTaiKhoan.getSelectedRow();
            if (index == tblTaiKhoan.getRowCount() - 1) {
                firstSP();
            } else {
                index++;
            }
            tblTaiKhoan.setRowSelectionInterval(index, index);
            fillForm();
            lblBanGhi.setText(viTriBanGhi());
        }

    }
    public void LamMoi(){
        txtMatKhau.setText("");
        txtTenTaiKhoan.setText("");
        cboVaiTro.setSelectedIndex(1);
    }
    public boolean CheckForm(){
        if (txtTenTaiKhoan.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên tài khoản.");
            txtTenTaiKhoan.requestFocus();
            return false;
        }
        if (txtMatKhau.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mật khẩu.");
            txtMatKhau.requestFocus();
            return false;
        }
        return true;
    }
    public boolean CheckDuplicate(){
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection(url, name, pass);
            Statement st = con.createStatement();
            String sql = "SELECT TENDANGNHAP FROM dbo.NGUOIDUNG";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                if(txtTenTaiKhoan.getText().equals(rs.getString(1))){
                    return true;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
    public void ThemTK() {
        SanPham sp = new SanPham();
        if(CheckForm() && CheckDuplicate()){
            CapNhatTK();
        }else if (CheckForm()) {
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                Connection con = DriverManager.getConnection(url, name, pass);
                String sql = "insert into NGUOIDUNG values(?,?,?)";
                PreparedStatement st = con.prepareStatement(sql);
                st.setString(1, txtTenTaiKhoan.getText());
                st.setString(2, txtMatKhau.getText());
                st.setString(3, cboVaiTro.getSelectedItem().toString());
                st.executeUpdate();
                JOptionPane.showMessageDialog(this, "Thêm thành công");
                con.close();
                initData();
                LamMoi();
            } catch (Exception ex) {
                System.out.println(ex);
                JOptionPane.showMessageDialog(this, "Lỗi!!!");
                ex.printStackTrace();
            }
        }
    }
    public void XoaTK(){
        int choose = (JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa?", "", JOptionPane.YES_NO_OPTION));
        if (choose == JOptionPane.YES_OPTION) {
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                Connection con = DriverManager.getConnection(url, name, pass);
                String sql = "delete from nguoidung where TENDANGNHAP = ?";
                PreparedStatement st = con.prepareStatement(sql);
                st.setString(1, txtTenTaiKhoan.getText());
                st.execute();
                JOptionPane.showMessageDialog(this, "Xóa thành công!");
                con.close();
                initData();
                LamMoi();
            } catch (Exception e) {
                System.out.println(e);
                JOptionPane.showMessageDialog(this, "Lỗi");
            }
        } else
            return;
    }
    public void CapNhatTK(){
        if (CheckForm()) {
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                Connection con = DriverManager.getConnection(url, name, pass);
                String sql = "update NGUOIDUNG SET MATKHAU = ?, VAITRO = ? WHERE TENDANGNHAP = ?";
                PreparedStatement st = con.prepareStatement(sql);
                st.setString(1, txtMatKhau.getText());
                st.setString(2, cboVaiTro.getSelectedItem().toString());
                st.setString(3, txtTenTaiKhoan.getText());
                st.executeUpdate();
                JOptionPane.showMessageDialog(this, "Cập nhật dữ liệu thành công");
                con.close();
                initData();
            } catch (Exception e) {
                System.out.println(e);
                JOptionPane.showMessageDialog(this, "Lỗi");
            }
        }
    }
    public void TimKiemTK(){
        try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                Connection con = DriverManager.getConnection(url, name, pass);
                String sql = "select * from NGUOIDUNG where TENDANGNHAP = ?";
                PreparedStatement st = con.prepareStatement(sql);
                st.setString(1, txtTenTaiKhoan.getText());
                st.execute();
                ResultSet rs = st.executeQuery();  
            if (rs.next()) {  
                String s = rs.getString(1);  
                String s1 = rs.getString(2);  
                String s2 = rs.getString(3);  
                //Sets Records in TextFields.  
                txtTenTaiKhoan.setText(s); 
                txtMatKhau.setText(s1);  
                cboVaiTro.setSelectedItem(s2);
                for(int i = 0; i < tblTaiKhoan.getRowCount(); i++){
                    if (s.equals(tblTaiKhoan.getValueAt(i, 0))) {
                        index = i;
                        tblTaiKhoan.setRowSelectionInterval(index, index);
                        lblBanGhi.setText(viTriBanGhi());
                        break;
                    }
                }
                
            } else {  
                JOptionPane.showMessageDialog(null, "Không tìm thấy!!!");
                LamMoi();
            }
            } catch (Exception e) {
                System.out.println(e);
                JOptionPane.showMessageDialog(this, "Lỗi");
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

        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        btnQuayLai = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtTenTaiKhoan = new javax.swing.JTextField();
        txtMatKhau = new javax.swing.JTextField();
        cboVaiTro = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblTaiKhoan = new javax.swing.JTable();
        btnTimKiem = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        btnCapNhat = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnFirst = new javax.swing.JButton();
        btnPre = new javax.swing.JButton();
        lblBanGhi = new javax.swing.JLabel();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        btnTimKiem1 = new javax.swing.JButton();

        jButton2.setText("jButton2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 0, 0));
        jLabel1.setText("Quản lý tài khoản");

        btnQuayLai.setText("Quay lại trang chủ");
        btnQuayLai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuayLaiActionPerformed(evt);
            }
        });

        jLabel2.setText("Tên tài khoản:");

        jLabel3.setText("Mật khẩu:");

        jLabel4.setText("Vai trò:");

        tblTaiKhoan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblTaiKhoan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblTaiKhoanMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblTaiKhoan);

        btnTimKiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Refresh.png"))); // NOI18N
        btnTimKiem.setText("Làm mới");
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Add.png"))); // NOI18N
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnCapNhat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Accept.png"))); // NOI18N
        btnCapNhat.setText("Cập nhật");
        btnCapNhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatActionPerformed(evt);
            }
        });

        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Delete.png"))); // NOI18N
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnFirst.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btnFirst.setText("|<");
        btnFirst.setPreferredSize(new java.awt.Dimension(55, 21));
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });

        btnPre.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btnPre.setText("<<");
        btnPre.setPreferredSize(new java.awt.Dimension(55, 21));
        btnPre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreActionPerformed(evt);
            }
        });

        lblBanGhi.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        lblBanGhi.setForeground(new java.awt.Color(0, 0, 255));
        lblBanGhi.setText("0 Trên 0");

        btnNext.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btnNext.setText(">>");
        btnNext.setPreferredSize(new java.awt.Dimension(55, 21));
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnLast.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btnLast.setText(">|");
        btnLast.setPreferredSize(new java.awt.Dimension(55, 21));
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });

        btnTimKiem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Search.png"))); // NOI18N
        btnTimKiem1.setText("Tìm kiếm");
        btnTimKiem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiem1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(btnPre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addComponent(lblBanGhi)
                        .addGap(10, 10, 10)
                        .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(155, 155, 155)
                                .addComponent(jLabel1))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4))
                                .addGap(11, 11, 11)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtTenTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cboVaiTro, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(13, 13, 13)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnCapNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnTimKiem1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(8, 8, 8))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnQuayLai)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel1)
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(jLabel2)
                        .addGap(12, 12, 12)
                        .addComponent(jLabel3)
                        .addGap(12, 12, 12)
                        .addComponent(jLabel4))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(txtTenTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(txtMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(cboVaiTro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnThem)
                        .addGap(7, 7, 7)
                        .addComponent(btnXoa))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnCapNhat)
                        .addGap(7, 7, 7)
                        .addComponent(btnTimKiem1)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(btnPre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(lblBanGhi))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTimKiem)))
                .addGap(9, 9, 9)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnQuayLai)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnQuayLaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuayLaiActionPerformed
        if (CheckVaiTro.getCheckVaiTro().equals("Quản lý")) {
            setVisible(false);
            MainFrameManage mfM = new MainFrameManage();
            mfM.setVisible(true);
        }else if(CheckVaiTro.getCheckVaiTro().equals("Nhân viên")){
            setVisible(false);
            MainFrameStaff mfS = new MainFrameStaff();
            mfS.setVisible(true);
        }else{
            setVisible(false);
            MainFrameManage mfM = new MainFrameManage();
            mfM.setVisible(true);
        }
    }//GEN-LAST:event_btnQuayLaiActionPerformed

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        firstSP();
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnPreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreActionPerformed
        preSP();
    }//GEN-LAST:event_btnPreActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        nextSP();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        lastSP();
    }//GEN-LAST:event_btnLastActionPerformed

    private void tblTaiKhoanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblTaiKhoanMouseClicked
        fillForm();
        lblBanGhi.setText(viTriBanGhi());
    }//GEN-LAST:event_tblTaiKhoanMouseClicked

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
       ThemTK();
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        XoaTK();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnCapNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatActionPerformed
        CapNhatTK();
    }//GEN-LAST:event_btnCapNhatActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        LamMoi();
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void btnTimKiem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiem1ActionPerformed
        TimKiemTK();
    }//GEN-LAST:event_btnTimKiem1ActionPerformed

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AccountManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AccountManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AccountManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AccountManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AccountManager().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCapNhat;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPre;
    private javax.swing.JButton btnQuayLai;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnTimKiem1;
    private javax.swing.JButton btnXoa;
    private javax.swing.JComboBox<String> cboVaiTro;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblBanGhi;
    private javax.swing.JTable tblTaiKhoan;
    private javax.swing.JTextField txtMatKhau;
    private javax.swing.JTextField txtTenTaiKhoan;
    // End of variables declaration//GEN-END:variables
}
