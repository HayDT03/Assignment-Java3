/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package haydtpc04386_assignment_gd2;

/**
 *
 * @author dutru
 */
public class SanPham {
    private String MaSP;
    private String TenSP;
    private int SoLuong;
    private double DonGia;
    private String NgayNhap;
    private String ThuongHieu;
    private String LoaiHang;
    private String XuatXu;

    public SanPham(String MaSP, String TenSP, int SoLuong, double DonGia, String NgayNhap, String ThuongHieu, String LoaiHang, String XuatXu) {
        this.MaSP = MaSP;
        this.TenSP = TenSP;
        this.SoLuong = SoLuong;
        this.DonGia = DonGia;
        this.NgayNhap = NgayNhap;
        this.ThuongHieu = ThuongHieu;
        this.LoaiHang = LoaiHang;
        this.XuatXu = XuatXu;
    }

    public SanPham() {
    }

    public String getMaSP() {
        return MaSP;
    }

    public void setMaSP(String MaSP) {
        this.MaSP = MaSP;
    }

    public String getTenSP() {
        return TenSP;
    }

    public void setTenSP(String TenSP) {
        this.TenSP = TenSP;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int SoLuong) {
        this.SoLuong = SoLuong;
    }

    public double getDonGia() {
        return DonGia;
    }

    public void setDonGia(double DonGia) {
        this.DonGia = DonGia;
    }

    public String getNgayNhap() {
        return NgayNhap;
    }

    public void setNgayNhap(String NgayNhap) {
        this.NgayNhap = NgayNhap;
    }

    public String getThuongHieu() {
        return ThuongHieu;
    }

    public void setThuongHieu(String ThuongHieu) {
        this.ThuongHieu = ThuongHieu;
    }

    public String getLoaiHang() {
        return LoaiHang;
    }

    public void setLoaiHang(String LoaiHang) {
        this.LoaiHang = LoaiHang;
    }

    public String getXuatXu() {
        return XuatXu;
    }

    public void setXuatXu(String XuatXu) {
        this.XuatXu = XuatXu;
    }
}
