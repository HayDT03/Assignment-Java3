/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package haydtpc04386_assignment_gd2;


import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;
import java.sql.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author dutru
 */
public class ProductManager extends javax.swing.JFrame {

    /**
     * Creates new form ProductManager
     */
    public ProductManager() {
        initComponents();
        setResizable(false);
        initData();
        LoadDataToComboBox();
        btnCapNhat.setEnabled(false);
        btnXoa.setEnabled(false);
        btnNext.setEnabled(false);
        btnPre.setEnabled(false);
        lblHinhAnh.setHorizontalAlignment(SwingConstants.CENTER);
        txtNameFile.setEnabled(false);
        txtNameFile.setHorizontalAlignment(SwingConstants.CENTER);
        
    }
    static String name ="UserAdmin";
    static String pass = "123456";
    static String srcImage = "C:\\FPT POLY\\SOF203-JAVA3\\HayDTPC04386_LAB_Assignment\\HayDTPC04386_Assignment_Final\\HayDTPC04386_Assignment_Final\\src\\Images";
    static String url = "jdbc:sqlserver://localhost:1433;databaseName=HayDTPC04386_QLySanPham_DoDungHocTap;encrypt=true;trustServerCertificate=true";
    /**
     * Creates new form SanPhamAll
     */
    
    String head[] = {"Mã sản phẩm","Tên hàng hóa","Số lượng","Đơn giá", "Ngày nhập", "Thương hiệu", "Loại hàng", "Xuất xứ"};
    DefaultTableModel model = new DefaultTableModel(head, 0);
    Scanner sc = new Scanner(System.in);
    private ArrayList<SanPham> list = new ArrayList<SanPham>();
    int index = 0;
    public void initData() {
        try {
            model.setRowCount(0);
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection(url, name, pass);
            Statement st = con.createStatement();
            String sql = "SELECT A.MAHANGHOA, A.TENHANGHOA, A.SOLUONG, A.DONGIA,\n"
                    + "CONVERT(NVARCHAR(30), A.NGAYNHAP, 101) AS NGAYNHAP, \n"
                    + "C.TENTHUONGHIEU, B.TENLOAIHANG, D.TENXUATXU FROM dbo.HANGHOA A\n"
                    + "INNER JOIN dbo.LOAIHANG B ON A.MALOAIHANG = B.MALOAIHANG\n"
                    + "INNER JOIN dbo.THUONGHIEU C ON A.MA_THUONGHIEU = C.MA_THUONGHIEU\n"
                    + "INNER JOIN dbo.XUATXU D ON A.MA_XUATXU = D.MA_XUATXU";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Vector row = new Vector();
                row.add(rs.getString(1));
                row.add(rs.getString(2));
                row.add(rs.getString(3));
                row.add(rs.getString(4));
                row.add(rs.getString(5));
                row.add(rs.getString(6));
                row.add(rs.getString(7));
                row.add(rs.getString(8));
                model.addRow(row);
            }
            tblSanPham.setModel(model);
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void LoadDataToComboBox(){
        try{
            
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection(url, name, pass);
            Statement st = con.createStatement();
            String sql = "SELECT TENTHUONGHIEU FROM THUONGHIEU";
            ResultSet rs = st.executeQuery(sql);
            String check = null;
            while(rs.next()){
                String s = rs.getString(1);
                if (s.equals(check) == false){
                    cboThuongHieu.addItem(s);
                    check = s;
                }
                
            }
            Statement st2 = con.createStatement();
            String sql2 = "SELECT TENLOAIHANG FROM LOAIHANG";
            ResultSet rs2 = st.executeQuery(sql2);
            check = null;
            while(rs2.next()){
                String s = rs2.getString(1);
                if (s.equals(check) == false){
                    cboLoaiHang.addItem(s);
                    check = s;
                }
                
            }
            Statement st3 = con.createStatement();
            String sql3 = "SELECT TENXUATXU FROM XUATXU";
            ResultSet rs3 = st.executeQuery(sql3);
            check = null;
            while(rs3.next()){
                String s = rs3.getString(1);
                if (s.equals(check) == false){
                    cboXuatXu.addItem(s);
                    check = s;
                }
                
            }
            con.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    public void fillForm(){
        int row = tblSanPham.getSelectedRow();
        txtMaSanPham.setText(tblSanPham.getValueAt(row, 0).toString());
        txtTenHangHoa.setText(tblSanPham.getValueAt(row, 1).toString());
        txtSoLuong.setText(tblSanPham.getValueAt(row, 2).toString());
        txtDonGia.setText(tblSanPham.getValueAt(row, 3).toString());
        txtNgayNhap.setText(tblSanPham.getValueAt(row, 4).toString());
        cboThuongHieu.setSelectedItem(tblSanPham.getValueAt(row, 5).toString());
        cboLoaiHang.setSelectedItem(tblSanPham.getValueAt(row, 6).toString());
        cboXuatXu.setSelectedItem(tblSanPham.getValueAt(row, 7).toString());

    }
    
    public  void LoadDuLieu() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection(name, pass, url);
            Statement st = con.createStatement();
            String sql = "SELECT A.MAHANGHOA, A.TENHANGHOA, A.SOLUONG, A.DONGIA,\n"
                    + "CONVERT(NVARCHAR(30), A.NGAYNHAP, 101) AS NGAYNHAP, \n"
                    + "C.TENTHUONGHIEU, B.TENLOAIHANG, D.TENXUATXU FROM dbo.HANGHOA A\n"
                    + "INNER JOIN dbo.LOAIHANG B ON A.MALOAIHANG = B.MALOAIHANG\n"
                    + "INNER JOIN dbo.THUONGHIEU C ON A.MA_THUONGHIEU = C.MA_THUONGHIEU\n"
                    + "INNER JOIN dbo.XUATXU D ON A.MA_XUATXU = D.MA_XUATXU";
            ResultSet rs = st.executeQuery(sql);
            list.clear();
            while (rs.next()) {
                String MaSP = rs.getString(1);
                String TenHH = rs.getString(2);
                int SoLuong = rs.getInt(3);
                double DonGia  = rs.getDouble(4);
                String NgayNhap = rs.getString(5);
                String ThuongHieu = rs.getString(6);
                String LoaiHang = rs.getString(7);
                String XuatXu = rs.getString(8);
                SanPham sv = new SanPham(MaSP, TenHH, SoLuong, DonGia, NgayNhap, ThuongHieu, LoaiHang, XuatXu);
                list.add(sv);
            }
            con.close();
        } catch (Exception e) {
            System.out.println("Loi: " + e);
        }
    }
    public String viTriBanGhi() {
        int viTri = tblSanPham.getSelectedRow();
        return (viTri + 1) + " Trên " + (tblSanPham.getRowCount());
    }
    public void firstSP() {
        index = 0;
        tblSanPham.setRowSelectionInterval(index, index);
        fillForm();
        lblBanGhi.setText(viTriBanGhi());
        btnXoa.setEnabled(true);
        btnNext.setEnabled(true);
    }

    public void preSP() {
        if (tblSanPham.getRowCount() != 0) {
            index = tblSanPham.getSelectedRow();
            if (index == 0) {
                lastSP();
            } else {
                index--;
            }
            tblSanPham.setRowSelectionInterval(index, index);
            fillForm();
            lblBanGhi.setText(viTriBanGhi());
        }
    }

    public void lastSP() {
        index = tblSanPham.getRowCount() - 1;
        tblSanPham.setRowSelectionInterval(index, index);
        fillForm();
        lblBanGhi.setText(viTriBanGhi());
        btnXoa.setEnabled(true);
        btnNext.setEnabled(true);

    }

    public void nextSP() {
        if (tblSanPham.getRowCount() != 0) {
            index = tblSanPham.getSelectedRow();
            if (index == tblSanPham.getRowCount() - 1) {
                firstSP();
            } else {
                index++;
            }
            tblSanPham.setRowSelectionInterval(index, index);
            fillForm();
            lblBanGhi.setText(viTriBanGhi());
        }

    }
    public void LamMoi(){
        txtMaSanPham.setText("");
        txtTenHangHoa.setText("");
        txtSoLuong.setText("");
        txtDonGia.setText("");
        txtNgayNhap.setText("");
        cboThuongHieu.setSelectedIndex(0);
        cboLoaiHang.setSelectedIndex(0);
        cboXuatXu.setSelectedIndex(0);
    }
    public boolean CheckForm(){
        if (txtMaSanPham.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã sản phẩm.");
            txtMaSanPham.requestFocus();
            return false;
        }
        if (txtTenHangHoa.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên hàng hóa.");
            txtTenHangHoa.requestFocus();
            return false;
        }
        if (txtSoLuong.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số lượng.");
            txtSoLuong.requestFocus();
            return false;
        }
        try {
            Integer.parseInt(txtSoLuong.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số lượng là số !!!");
            txtSoLuong.requestFocus();
            return false;
        }
          
        if (txtDonGia.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đơn giá.");
            txtDonGia.requestFocus();
            return false;
        }
        try {
            Double.parseDouble(txtDonGia.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đơn giá là số !!!");
            txtDonGia.requestFocus();
            return false;
        }

        if (txtNgayNhap.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập ngày nhập hàng.");
            txtNgayNhap.requestFocus();
            return false;
        }
//        if (txtThuongHieu.getText().equals("")) {
//            JOptionPane.showMessageDialog(this, "Vui lòng nhập thương hiệu.");
//            txtThuongHieu.requestFocus();
//            return false;
//        }
//        if (txtLoaiHang.getText().equals("")) {
//            JOptionPane.showMessageDialog(this, "Vui lòng nhập loại hàng.");
//            txtLoaiHang.requestFocus();
//            return false;
//        }
//        if (txtXuatXu.getText().equals("")) {
//            JOptionPane.showMessageDialog(this, "Vui lòng nhập xuất xứ.");
//            txtXuatXu.requestFocus();
//            return false;
//        }

        return true;
    }
    public boolean CheckDuplicate(){
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection(url, name, pass);
            Statement st = con.createStatement();
            String sql = "SELECT MAHANGHOA FROM HANGHOA";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                if(txtMaSanPham.getText().equals(rs.getString(1))){
                    return true;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
    public void ThemSP() {
        SanPham sp = new SanPham();
        if(CheckDuplicate() && CheckForm()){
            CapNhatSP();
        }
        else if (CheckForm()) {
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                Connection con = DriverManager.getConnection(url, name, pass);
                String sql = "insert into HANGHOA values(?,?,?,?,?,?,?,?)";
                PreparedStatement st = con.prepareStatement(sql);
                String sql2 = "select ma_thuonghieu from thuonghieu where tenthuonghieu=?";
                PreparedStatement st2 = con.prepareStatement(sql2);
                st2.setString(1, cboThuongHieu.getSelectedItem().toString());
                ResultSet rs =  st2.executeQuery();
                rs.next();
                String MA_THUONGHIEU = rs.getString(1);
                String sql3 = "select maloaihang from loaihang where tenloaihang=?";
                PreparedStatement st3 = con.prepareStatement(sql3);
                st3.setString(1, cboLoaiHang.getSelectedItem().toString());
                ResultSet rs2 =  st3.executeQuery();
                rs2.next();
                String MA_LOAIHANG = rs2.getString(1);
                String sql4 = "select ma_xuatxu from xuatxu where tenxuatxu=?";
                PreparedStatement st4 = con.prepareStatement(sql4);
                st4.setString(1, cboXuatXu.getSelectedItem().toString());
                ResultSet rs3 =  st4.executeQuery();
                rs3.next();
                String MA_XUATXU = rs3.getString(1);
                st.setString(1, txtMaSanPham.getText());
                st.setString(2, txtTenHangHoa.getText());
                st.setString(3, txtSoLuong.getText());
                st.setString(4, txtDonGia.getText());
                st.setString(5, txtNgayNhap.getText());
                st.setString(6, MA_THUONGHIEU);
                st.setString(7, MA_LOAIHANG);
                st.setString(8, MA_XUATXU);
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
    public void CapNhatSP(){
        if (CheckForm()) {
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                Connection con = DriverManager.getConnection(url, name, pass);
                String sql2 = "select ma_thuonghieu from thuonghieu where tenthuonghieu=?";
                PreparedStatement st2 = con.prepareStatement(sql2);
                st2.setString(1, cboThuongHieu.getSelectedItem().toString());
                ResultSet rs =  st2.executeQuery();
                rs.next();
                String MA_THUONGHIEU = rs.getString(1);
                String sql3 = "select maloaihang from loaihang where tenloaihang=?";
                PreparedStatement st3 = con.prepareStatement(sql3);
                st3.setString(1, cboLoaiHang.getSelectedItem().toString());
                ResultSet rs2 =  st3.executeQuery();
                rs2.next();
                String MA_LOAIHANG = rs2.getString(1);
                String sql4 = "select ma_xuatxu from xuatxu where tenxuatxu=?";
                PreparedStatement st4 = con.prepareStatement(sql4);
                st4.setString(1, cboXuatXu.getSelectedItem().toString());
                ResultSet rs3 =  st4.executeQuery();
                rs3.next();
                String MA_XUATXU = rs3.getString(1);
                String sql = "update HANGHOA SET TENHANGHOA = ?, SOLUONG = ?, DONGIA = ?, NGAYNHAP = ?, MA_THUONGHIEU = ?, MALOAIHANG = ?, MA_XUATXU = ? WHERE MAHANGHOA = ?";
                PreparedStatement st = con.prepareStatement(sql);
                st.setString(1, txtTenHangHoa.getText());
                st.setString(2, txtSoLuong.getText());
                st.setString(3, txtDonGia.getText());
                st.setString(4, txtNgayNhap.getText());
                st.setString(5, MA_THUONGHIEU);
                st.setString(6, MA_LOAIHANG);
                st.setString(7, MA_XUATXU);
                st.setString(8, txtMaSanPham.getText());
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
    public void XoaSP(){
        int choose = (JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa chứ ?", "", JOptionPane.YES_NO_OPTION));
        if (choose == JOptionPane.YES_OPTION) {
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                Connection con = DriverManager.getConnection(url, name, pass);
                String sql = "delete from HANGHOA where MAHANGHOA = ?";
                PreparedStatement st = con.prepareStatement(sql);
                st.setString(1, txtMaSanPham.getText());
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
    public void TimKiemSP(){
        if (txtMaSanPham.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã sản phẩm cần tìm!!!!");
            txtMaSanPham.requestFocus();
        }else{
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                Connection con = DriverManager.getConnection(url, name, pass);
                String sql = "select * from dbo.HANGHOA where MAHANGHOA = ?";
                PreparedStatement st = con.prepareStatement(sql);
                st.setString(1, txtMaSanPham.getText().toString());
                st.execute();
                ResultSet rs = st.executeQuery();

                if (rs.next()) {
//                String s = rs.getString(1);
//                String s1 = rs.getString(2);
//                String s2 = rs.getString(3);
                    //Sets Records in TextFields.
                    String sql2 = "select TENTHUONGHIEU from thuonghieu where MA_THUONGHIEU=?";
                    PreparedStatement st2 = con.prepareStatement(sql2);
                    st2.setString(1, rs.getString(6).toString());
                    ResultSet rs2 = st2.executeQuery();
                    rs2.next();
                    String TENTHUONGHIEU = rs2.getString(1);
                    String sql3 = "select TENLOAIHANG from loaihang where MALOAIHANG=?";
                    PreparedStatement st3 = con.prepareStatement(sql3);
                    st3.setString(1, rs.getString(7).toString());
                    ResultSet rs3 = st3.executeQuery();
                    rs3.next();
                    String TENLOAIHANG = rs3.getString(1);
                    String sql4 = "select TENXUATXU from xuatxu where MA_XUATXU=?";
                    PreparedStatement st4 = con.prepareStatement(sql4);
                    st4.setString(1, rs.getString(8).toString());
                    ResultSet rs4 = st4.executeQuery();
                    rs4.next();
                    String TENXUATXU = rs4.getString(1);
                    txtMaSanPham.setText(rs.getString(1));
                    txtTenHangHoa.setText(rs.getString(2));
                    txtSoLuong.setText(rs.getString(3));
                    txtDonGia.setText(rs.getString(4));
                    txtNgayNhap.setText(rs.getString(5));
                    cboThuongHieu.setSelectedItem(TENTHUONGHIEU);
                    cboLoaiHang.setSelectedItem(TENLOAIHANG);
                    cboXuatXu.setSelectedItem(TENXUATXU);
                    for (int i = 0; i < tblSanPham.getRowCount(); i++) {
                        if (rs.getString(1).equals(tblSanPham.getValueAt(i, 0))) {
                            index = i;
                            tblSanPham.setRowSelectionInterval(index, index);
                            lblBanGhi.setText(viTriBanGhi());
                            break;
                        }
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Không tìm thấy sản phẩm có mã: " + txtMaSanPham.getText());
                    txtMaSanPham.requestFocus();
                }
            } catch (Exception e) {
                System.out.println(e);
                JOptionPane.showMessageDialog(this, "Lỗi");
            }
        }
    }
    public void SapXepTheoGiaGiam(){
         try {
            model.setRowCount(0);
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection(url, name, pass);
            Statement st = con.createStatement();
            String sql = "SELECT A.MAHANGHOA, A.TENHANGHOA, A.SOLUONG, A.DONGIA,\n"
                    + "CONVERT(NVARCHAR(30), A.NGAYNHAP, 101) AS NGAYNHAP, \n"
                    + "C.TENTHUONGHIEU, B.TENLOAIHANG, D.TENXUATXU FROM dbo.HANGHOA A\n"
                    + "INNER JOIN dbo.LOAIHANG B ON A.MALOAIHANG = B.MALOAIHANG\n"
                    + "INNER JOIN dbo.THUONGHIEU C ON A.MA_THUONGHIEU = C.MA_THUONGHIEU\n"
                    + "INNER JOIN dbo.XUATXU D ON A.MA_XUATXU = D.MA_XUATXU ORDER BY DONGIA DESC";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Vector row = new Vector();
                row.add(rs.getString(1));
                row.add(rs.getString(2));
                row.add(rs.getString(3));
                row.add(rs.getString(4));
                row.add(rs.getString(5));
                row.add(rs.getString(6));
                row.add(rs.getString(7));
                row.add(rs.getString(8));
                model.addRow(row);
            }
            tblSanPham.setModel(model);
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void SapXepTheoGiaTang(){
         try {
            model.setRowCount(0);
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection(url, name, pass);
            Statement st = con.createStatement();
            String sql = "SELECT A.MAHANGHOA, A.TENHANGHOA, A.SOLUONG, A.DONGIA,\n"
                    + "CONVERT(NVARCHAR(30), A.NGAYNHAP, 101) AS NGAYNHAP, \n"
                    + "C.TENTHUONGHIEU, B.TENLOAIHANG, D.TENXUATXU FROM dbo.HANGHOA A\n"
                    + "INNER JOIN dbo.LOAIHANG B ON A.MALOAIHANG = B.MALOAIHANG\n"
                    + "INNER JOIN dbo.THUONGHIEU C ON A.MA_THUONGHIEU = C.MA_THUONGHIEU\n"
                    + "INNER JOIN dbo.XUATXU D ON A.MA_XUATXU = D.MA_XUATXU ORDER BY DONGIA ASC";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Vector row = new Vector();
                row.add(rs.getString(1));
                row.add(rs.getString(2));
                row.add(rs.getString(3));
                row.add(rs.getString(4));
                row.add(rs.getString(5));
                row.add(rs.getString(6));
                row.add(rs.getString(7));
                row.add(rs.getString(8));
                model.addRow(row);
            }
            tblSanPham.setModel(model);
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void ReadImages(File path){
        try {
            Image img = ImageIO.read(path);
            lblHinhAnh.setText("");
            int width = lblHinhAnh.getWidth();
            int height = lblHinhAnh.getHeight();
            lblHinhAnh.setIcon(new ImageIcon(img.getScaledInstance(width, height, 0)));
            txtNameFile.setText(path.getName());
        } catch (IOException ex) {
            Logger.getLogger(ProductManager.class.getName()).log(Level.SEVERE, null, ex);
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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtMaSanPham = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtTenHangHoa = new javax.swing.JTextField();
        txtSoLuong = new javax.swing.JTextField();
        txtNgayNhap = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtDonGia = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        btnLamMoi = new javax.swing.JButton();
        btnCapNhat = new javax.swing.JButton();
        btnTimKiem = new javax.swing.JButton();
        btnThoat = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        btnFirst = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        lblBanGhi = new javax.swing.JLabel();
        btnPre = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        btnGiaGiam = new javax.swing.JButton();
        btnGiaTang = new javax.swing.JButton();
        btnMacDinh = new javax.swing.JButton();
        btnQuayLai = new javax.swing.JButton();
        cboThuongHieu = new javax.swing.JComboBox<>();
        cboLoaiHang = new javax.swing.JComboBox<>();
        cboXuatXu = new javax.swing.JComboBox<>();
        btnThemThuongHieu = new javax.swing.JButton();
        btnThemLoaiHang = new javax.swing.JButton();
        btnThemXuatXu = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        lblHinhAnh = new javax.swing.JLabel();
        btnChonAnh = new javax.swing.JButton();
        txtNameFile = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 0, 0));
        jLabel1.setText("Quản lý sản phẩm");

        jLabel2.setText("Mã sản phẩm:");

        txtMaSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaSanPhamActionPerformed(evt);
            }
        });

        jLabel3.setText("Tên hàng hóa:");

        jLabel4.setText("Số lượng:");

        jLabel5.setText("Đơn giá:");

        jLabel6.setText("Ngày nhập:");

        jLabel7.setText("Xuất xứ:");

        jLabel8.setText("Loại hàng:");

        jLabel9.setText("Thương hiệu:");

        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
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
        tblSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblSanPham);

        btnLamMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Refresh.png"))); // NOI18N
        btnLamMoi.setText("Làm mới");
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });

        btnCapNhat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Accept.png"))); // NOI18N
        btnCapNhat.setText("Cập nhật");
        btnCapNhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatActionPerformed(evt);
            }
        });

        btnTimKiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Search.png"))); // NOI18N
        btnTimKiem.setText("Tìm kiếm");
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        btnThoat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Exit.png"))); // NOI18N
        btnThoat.setText("Thoát");
        btnThoat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThoatActionPerformed(evt);
            }
        });

        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Add.png"))); // NOI18N
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Delete.png"))); // NOI18N
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCapNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThoat, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(btnLamMoi)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnThem)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCapNhat)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnXoa)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnTimKiem)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnThoat)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setLayout(null);

        btnFirst.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btnFirst.setText("|<");
        btnFirst.setPreferredSize(new java.awt.Dimension(55, 21));
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });
        jPanel2.add(btnFirst);
        btnFirst.setBounds(0, 0, 50, 30);

        btnNext.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btnNext.setText(">>");
        btnNext.setPreferredSize(new java.awt.Dimension(55, 21));
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });
        jPanel2.add(btnNext);
        btnNext.setBounds(240, 0, 51, 30);

        lblBanGhi.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        lblBanGhi.setForeground(new java.awt.Color(0, 0, 255));
        lblBanGhi.setText("0 Trên 0");
        jPanel2.add(lblBanGhi);
        lblBanGhi.setBounds(140, 10, 90, 20);

        btnPre.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btnPre.setText("<<");
        btnPre.setPreferredSize(new java.awt.Dimension(55, 21));
        btnPre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreActionPerformed(evt);
            }
        });
        jPanel2.add(btnPre);
        btnPre.setBounds(60, 0, 50, 30);

        btnLast.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btnLast.setText(">|");
        btnLast.setPreferredSize(new java.awt.Dimension(55, 21));
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });
        jPanel2.add(btnLast);
        btnLast.setBounds(300, 0, 47, 30);

        btnGiaGiam.setText("Giá giảm dần");
        btnGiaGiam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGiaGiamActionPerformed(evt);
            }
        });
        jPanel2.add(btnGiaGiam);
        btnGiaGiam.setBounds(360, 0, 100, 30);

        btnGiaTang.setText("Giá tăng dần");
        btnGiaTang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGiaTangActionPerformed(evt);
            }
        });
        jPanel2.add(btnGiaTang);
        btnGiaTang.setBounds(470, 0, 100, 30);

        btnMacDinh.setText("Mặc định");
        btnMacDinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMacDinhActionPerformed(evt);
            }
        });
        jPanel2.add(btnMacDinh);
        btnMacDinh.setBounds(580, 0, 100, 30);

        btnQuayLai.setText("Quay lại trang chủ");
        btnQuayLai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuayLaiActionPerformed(evt);
            }
        });

        cboLoaiHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboLoaiHangActionPerformed(evt);
            }
        });

        btnThemThuongHieu.setText("Thêm ");
        btnThemThuongHieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemThuongHieuActionPerformed(evt);
            }
        });

        btnThemLoaiHang.setText("Thêm ");
        btnThemLoaiHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemLoaiHangActionPerformed(evt);
            }
        });

        btnThemXuatXu.setText("Thêm ");
        btnThemXuatXu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemXuatXuActionPerformed(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        lblHinhAnh.setText("Hình ảnh sản phẩm");

        btnChonAnh.setText("Chọn hình ảnh");
        btnChonAnh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChonAnhActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblHinhAnh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnChonAnh, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
                    .addComponent(txtNameFile))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblHinhAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addComponent(txtNameFile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnChonAnh)
                .addGap(10, 10, 10))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(368, 368, 368)
                .addComponent(jLabel1))
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel9)
                    .addComponent(jLabel8)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtMaSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 431, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTenHangHoa, javax.swing.GroupLayout.PREFERRED_SIZE, 431, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 429, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(txtDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, 429, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(txtNgayNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 429, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(cboThuongHieu, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(btnThemThuongHieu))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(cboLoaiHang, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(btnThemLoaiHang))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(cboXuatXu, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(btnThemXuatXu)))
                .addGap(6, 6, 6)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 679, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 837, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(640, 640, 640)
                .addComponent(btnQuayLai))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel1)
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jLabel2)
                        .addGap(17, 17, 17)
                        .addComponent(jLabel3)
                        .addGap(17, 17, 17)
                        .addComponent(jLabel4)
                        .addGap(17, 17, 17)
                        .addComponent(jLabel5)
                        .addGap(17, 17, 17)
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel9)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(txtMaSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addComponent(txtTenHangHoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addComponent(txtDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addComponent(txtNgayNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cboThuongHieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnThemThuongHieu))
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cboLoaiHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnThemLoaiHang))
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addComponent(cboXuatXu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnThemXuatXu)))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(btnQuayLai))
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

    private void btnCapNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatActionPerformed
        CapNhatSP();
    }//GEN-LAST:event_btnCapNhatActionPerformed

    private void txtMaSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaSanPhamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaSanPhamActionPerformed

    private void tblSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamMouseClicked
        fillForm();
        lblBanGhi.setText(viTriBanGhi());
        btnCapNhat.setEnabled(true);
        btnXoa.setEnabled(true);
        btnNext.setEnabled(true);
        btnPre.setEnabled(true);
    }//GEN-LAST:event_tblSanPhamMouseClicked

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        LamMoi();
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        ThemSP();
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnThemThuongHieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemThuongHieuActionPerformed
        JOptionPane.showMessageDialog(this, "Tính năng đang phát triển.");
    }//GEN-LAST:event_btnThemThuongHieuActionPerformed

    private void btnThemLoaiHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemLoaiHangActionPerformed
        JOptionPane.showMessageDialog(this, "Tính năng đang phát triển.");
    }//GEN-LAST:event_btnThemLoaiHangActionPerformed

    private void btnThemXuatXuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemXuatXuActionPerformed
        JOptionPane.showMessageDialog(this, "Tính năng đang phát triển.");
    }//GEN-LAST:event_btnThemXuatXuActionPerformed

    private void btnThoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThoatActionPerformed
        int comfirm = JOptionPane.showConfirmDialog(this, "Bạn chắc chắn muốn thoát chứ ?");
        if (comfirm == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(this, "Cảm ơn bạn đã sử dụng dịch vụ.");
            System.exit(0);
        }
    }//GEN-LAST:event_btnThoatActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        XoaSP();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        TimKiemSP();
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void btnGiaGiamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGiaGiamActionPerformed
        SapXepTheoGiaGiam();
    }//GEN-LAST:event_btnGiaGiamActionPerformed

    private void btnGiaTangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGiaTangActionPerformed
        SapXepTheoGiaTang();
    }//GEN-LAST:event_btnGiaTangActionPerformed

    private void btnMacDinhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMacDinhActionPerformed
        initData();
    }//GEN-LAST:event_btnMacDinhActionPerformed

    private void cboLoaiHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboLoaiHangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboLoaiHangActionPerformed

    private void btnChonAnhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChonAnhActionPerformed
        try {
            JFileChooser jfc = new JFileChooser(srcImage);
            jfc.showOpenDialog(null);
            File file = jfc.getSelectedFile();
            ReadImages(file);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }//GEN-LAST:event_btnChonAnhActionPerformed

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
            java.util.logging.Logger.getLogger(ProductManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ProductManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ProductManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ProductManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ProductManager().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCapNhat;
    private javax.swing.JButton btnChonAnh;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnGiaGiam;
    private javax.swing.JButton btnGiaTang;
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnMacDinh;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPre;
    private javax.swing.JButton btnQuayLai;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnThemLoaiHang;
    private javax.swing.JButton btnThemThuongHieu;
    private javax.swing.JButton btnThemXuatXu;
    private javax.swing.JButton btnThoat;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JComboBox<String> cboLoaiHang;
    private javax.swing.JComboBox<String> cboThuongHieu;
    private javax.swing.JComboBox<String> cboXuatXu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblBanGhi;
    private javax.swing.JLabel lblHinhAnh;
    private javax.swing.JTable tblSanPham;
    private javax.swing.JTextField txtDonGia;
    private javax.swing.JTextField txtMaSanPham;
    private javax.swing.JTextField txtNameFile;
    private javax.swing.JTextField txtNgayNhap;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTenHangHoa;
    // End of variables declaration//GEN-END:variables
}
