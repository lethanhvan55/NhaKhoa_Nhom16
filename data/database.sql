CREATE DATABASE QL_NK;
GO

USE QL_NK;
GO

-- =========================================================
-- 1. BỆNH NHÂN
-- =========================================================
CREATE TABLE BenhNhan (
MaBenhNhan CHAR(6) PRIMARY KEY,
HoTen NVARCHAR(100) NOT NULL,
NgaySinh DATE NOT NULL,
GioiTinh BIT NOT NULL,
SoDT VARCHAR(10) NOT NULL UNIQUE,
Email VARCHAR(100) NULL,
DiaChi NVARCHAR(255) NULL,

CONSTRAINT CK_BenhNhan_MaBenhNhan CHECK (MaBenhNhan LIKE 'BN[0-9][0-9][0-9][0-9]'),
CONSTRAINT CK_BenhNhan_SoDT CHECK (SoDT LIKE '0[3-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]'),
CONSTRAINT CK_BenhNhan_NgaySinh CHECK (NgaySinh <= CAST(GETDATE() AS DATE))
);
GO

-- =========================================================
-- 2. NHÂN VIÊN
-- =========================================================
CREATE TABLE NhanVien (
MaNV CHAR(6) PRIMARY KEY,
HoTen NVARCHAR(40) NOT NULL,
SoDT VARCHAR(10) NULL UNIQUE,
NgayVaoLam DATE NOT NULL DEFAULT CAST(GETDATE() AS DATE),
GioiTinh BIT NULL,
ChucVu NVARCHAR(30) NOT NULL,
TrangThai NVARCHAR(30) NOT NULL,
Email VARCHAR(30) NULL,
DiaChi NVARCHAR(60) NULL,

CONSTRAINT CK_NhanVien_MaNV CHECK (MaNV LIKE 'NV[0-9][0-9][0-9][0-9]'),
CONSTRAINT CK_NhanVien_SoDT CHECK (SoDT IS NULL OR SoDT LIKE '0[3-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]'),
CONSTRAINT CK_NhanVien_ChucVu CHECK (ChucVu IN (N'Quản lý', N'Lễ tân', N'Nha sĩ')),
CONSTRAINT CK_NhanVien_TrangThai CHECK (TrangThai IN (N'Đang làm', N'Tạm nghỉ')),
CONSTRAINT CK_NhanVien_NgayVaoLam CHECK (NgayVaoLam <= CAST(GETDATE() AS DATE))
);
GO

-- =========================================================
-- 3. PHÒNG ĐIỀU TRỊ
-- =========================================================
CREATE TABLE PhongDieuTri (
MaPhong CHAR(6) PRIMARY KEY,
TenPhong NVARCHAR(100) NOT NULL,
LoaiPhong NVARCHAR(50) NOT NULL,
ViTri NVARCHAR(50) NOT NULL,
TrangThai NVARCHAR(20) NOT NULL DEFAULT N'Trống',
MoTa NVARCHAR(500) NULL,

CONSTRAINT CK_PhongDieuTri_MaPhong CHECK (MaPhong LIKE 'P[0-9][0-9][0-9][0-9][0-9]'),
CONSTRAINT CK_PhongDieuTri_LoaiPhong CHECK (LoaiPhong IN (N'VIP', N'Thường', N'Tiểu phẫu', N'Vật tư', N'Chụp X-Quang', N'Xét nghiệm')),
CONSTRAINT CK_PhongDieuTri_TrangThai CHECK (TrangThai IN (N'Trống', N'Đang sử dụng', N'Bảo trì'))
);
GO

-- =========================================================
-- 4. DỊCH VỤ
-- =========================================================
CREATE TABLE DichVu (
  MaDV CHAR(6) PRIMARY KEY,
  TenDV NVARCHAR(100) NOT NULL,
  DonGia DECIMAL(18,2) NOT NULL,
  ThoiGianDuKien INT NOT NULL,
  TrangThai NVARCHAR(20) NOT NULL,
  CONSTRAINT CK_DichVu_MaDV CHECK (MaDV LIKE 'DV[0-9][0-9][0-9][0-9]'),
  CONSTRAINT CK_DichVu_DonGia CHECK (DonGia >= 0),
  CONSTRAINT CK_DichVu_TrangThai CHECK (TrangThai IN (N'Đang cung cấp', N'Ngừng cung cấp'))
);
GO
-- =========================================================
-- 5. VẬT TƯ
-- =========================================================
CREATE TABLE VatTu (
MaVT CHAR(6) PRIMARY KEY,
TenVatTu NVARCHAR(100) NOT NULL,
DonViTinh NVARCHAR(50) NOT NULL,
SoLuongTon INT NOT NULL DEFAULT 0,
SoLuongToiThieu INT NOT NULL DEFAULT 0,
HanSuDung DATE NULL,
MoTa NVARCHAR(500) NULL,
HinhAnh NVARCHAR(255) NULL,

CONSTRAINT CK_VatTu_MaVT CHECK (MaVT LIKE 'VT[0-9][0-9][0-9][0-9]'),

CONSTRAINT CK_VatTu_SoLuongTon CHECK (SoLuongTon >= 0),

CONSTRAINT CK_VatTu_SoLuongToiThieu CHECK (SoLuongToiThieu >= 0)

);
GO
-- =========================================================
-- 6. KHUYẾN MÃI
-- =========================================================
CREATE TABLE KhuyenMai (
MaKM CHAR(6) PRIMARY KEY,
MaThayThe VARCHAR(30) NOT NULL UNIQUE,
TenKM NVARCHAR(50) NOT NULL,
NgayBatDau DATE NOT NULL,
NgayKetThuc DATE NOT NULL,
DieuKien NVARCHAR(50) NOT NULL,
ChietKhau DECIMAL(5,2) NOT NULL,
SoLuong INT NOT NULL,
TrangThai NVARCHAR(50) NOT NULL,
CONSTRAINT CK_KhuyenMai_MaKM CHECK (MaKM LIKE 'KM[0-9][0-9][0-9][0-9]'),

CONSTRAINT CK_KhuyenMai_NgayKetThuc CHECK (NgayKetThuc >= NgayBatDau),

CONSTRAINT CK_KhuyenMai_ChietKhau CHECK (ChietKhau >= 0 AND ChietKhau <= 100),
CONSTRAINT CK_KhuyenMai_SoLuong CHECK (SoLuong > 0),

CONSTRAINT CK_KhuyenMai_TrangThai CHECK (TrangThai IN (N'Đang áp dụng', N'Hết hạn'))
);
GO

-- =========================================================
-- 7. KẾ HOẠCH ĐIỀU TRỊ
-- =========================================================
CREATE TABLE KeHoachDieuTri (
MaKeHoach CHAR(6) PRIMARY KEY,
MaBenhNhan CHAR(6) NOT NULL,
ChanDoan NVARCHAR(500) NOT NULL,
NgayBatDau DATE NOT NULL,
NgayKetThuc DATE NOT NULL,
ChiPhiDuKien DECIMAL(18,2) NOT NULL,
TrangThai NVARCHAR(50) NOT NULL,

CONSTRAINT CK_KeHoachDieuTri_MaKH CHECK (MaKeHoach LIKE 'KH[0-9][0-9][0-9][0-9]'),
CONSTRAINT FK_KH_BenhNhan FOREIGN KEY (MaBenhNhan) REFERENCES BenhNhan(MaBenhNhan),
CONSTRAINT CK_KeHoachDieuTri_NgayKetThuc CHECK (NgayKetThuc >= NgayBatDau),
CONSTRAINT CK_KeHoachDieuTri_ChiPhi CHECK (ChiPhiDuKien >= 0)
);
GO

-- =========================================================
-- 8. HỒ SƠ BỆNH NHÂN
-- =========================================================
CREATE TABLE HoSoBenhNhan (
MaHoSo CHAR(6) PRIMARY KEY,
MaBenhNhan CHAR(6) NOT NULL,
MaNV CHAR(6) NOT NULL,
MaKeHoach CHAR(6) NULL,
NgayKham DATE NOT NULL,
TinhTrangRangMieng NVARCHAR(500) NOT NULL,
ChanDoan NVARCHAR(500) NOT NULL,
PhuongPhapDieuTri NVARCHAR(500) NOT NULL,
TienSuBenh NVARCHAR(500) NULL,
DiUng NVARCHAR(500) NULL,
BenhLyNen NVARCHAR(500) NULL,
NguoiLienHe NVARCHAR(100) NULL,
SoDTNLH VARCHAR(10) NULL,
GhiChu NVARCHAR(500) NULL,

CONSTRAINT CK_HoSo_MaHoSo CHECK (MaHoSo LIKE 'HS[0-9][0-9][0-9][0-9]'),
CONSTRAINT FK_HoSo_BenhNhan FOREIGN KEY (MaBenhNhan) REFERENCES BenhNhan(MaBenhNhan),
CONSTRAINT FK_HoSo_NhanVien FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNV),
CONSTRAINT FK_HoSo_KeHoach FOREIGN KEY (MaKeHoach) REFERENCES KeHoachDieuTri(MaKeHoach),
CONSTRAINT CK_HoSo_NgayKham CHECK (NgayKham <= CAST(GETDATE() AS DATE))
);
GO

-- =========================================================
-- 9. LỊCH LÀM VIỆC
-- =========================================================
CREATE TABLE LichLamViec (
MaLich CHAR(11) PRIMARY KEY,
MaPhong CHAR(6) NOT NULL,
MaNhanVien CHAR(6) NOT NULL,
NgayLam DATE NOT NULL,
CaLam NVARCHAR(10) NOT NULL,
GioBatDau DATETIME2 NOT NULL,
GioKetThuc DATETIME2 NOT NULL,
TrangThai NVARCHAR(20) NOT NULL DEFAULT N'Chưa phân công',

CONSTRAINT FK_LLV_Phong FOREIGN KEY (MaPhong) REFERENCES PhongDieuTri(MaPhong),
CONSTRAINT FK_LLV_NhanVien FOREIGN KEY (MaNhanVien) REFERENCES NhanVien(MaNV),
CONSTRAINT CK_LLV_CaLam CHECK (CaLam IN (N'Sáng', N'Chiều', N'Tối')),
CONSTRAINT CK_LLV_ThoiGian CHECK (GioBatDau < GioKetThuc),
CONSTRAINT CK_LLV_TrangThai CHECK (TrangThai IN (N'Đã phân công', N'Chưa phân công'))
);
GO

-- =========================================================
-- 10. LỊCH HẸN
-- =========================================================
CREATE TABLE LichHen (
MaLichHen CHAR(8) PRIMARY KEY,
MaBenhNhan CHAR(6) NOT NULL,
MaDichVu CHAR(6) NOT NULL,
MaPhong CHAR(6) NOT NULL,
MaNV CHAR(6) NOT NULL,
NgayHen DATE NOT NULL,
GioHen DATETIME2 NOT NULL,
TrangThai NVARCHAR(20) NOT NULL DEFAULT N'Đã đặt',
GhiChu NVARCHAR(255) NULL,

CONSTRAINT FK_LichHen_BenhNhan FOREIGN KEY (MaBenhNhan) REFERENCES BenhNhan(MaBenhNhan),
CONSTRAINT FK_LichHen_DichVu FOREIGN KEY (MaDichVu) REFERENCES DichVu(MaDV),
CONSTRAINT FK_LichHen_Phong FOREIGN KEY (MaPhong) REFERENCES PhongDieuTri(MaPhong),
CONSTRAINT FK_LichHen_NhanVien FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNV),
CONSTRAINT CK_LichHen_TrangThai CHECK (TrangThai IN (N'Đã đặt', N'Đang chờ khám', N'Hoàn thành', N'Hủy', N'Không đến'))
);
GO

-- =========================================================
-- 11. TÀI KHOẢN
-- =========================================================
CREATE TABLE TaiKhoan (
TenDangNhap VARCHAR(50) PRIMARY KEY,
MatKhau VARCHAR(255) NOT NULL,
Quyen NVARCHAR(20) NOT NULL,
MaNV CHAR(6) NOT NULL UNIQUE,
TrangThai NVARCHAR(20) NOT NULL DEFAULT N'Hoạt động',

CONSTRAINT FK_TaiKhoan_NhanVien FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNV),
CONSTRAINT CK_TaiKhoan_Quyen CHECK (Quyen IN (N'Quản lý', N'Lễ tân', N'Nha sĩ')),
CONSTRAINT CK_TaiKhoan_TrangThai CHECK (TrangThai IN (N'Hoạt động', N'Khóa')),
CONSTRAINT CK_TaiKhoan_MatKhau CHECK (LEN(MatKhau) >= 6)
);
GO

-- =========================================================
-- 12. PHIẾU NHẬP HÀNG
-- =========================================================
CREATE TABLE PhieuNhapHang (
MaPhieuNhap CHAR(6) PRIMARY KEY,
NgayNhap DATE NOT NULL DEFAULT CAST(GETDATE() AS DATE),
MaNV CHAR(6) NOT NULL,
NhaCungCap NVARCHAR(50) NOT NULL,
GhiChu NVARCHAR(50) NULL,
CONSTRAINT CK_PhieuNhap_MaPN CHECK (MaPhieuNhap LIKE 'PN[0-9][0-9][0-9][0-9]'),
CONSTRAINT FK_PNH_NhanVien FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNV)
);
GO

-- =========================================================
-- 13. CHI TIẾT PHIẾU NHẬP
-- =========================================================
CREATE TABLE ChiTietPhieuNhap (
MaPhieuNhap CHAR(6) NOT NULL,
MaVatTu CHAR(6) NOT NULL,
SoLuong INT NOT NULL,
DonGia DECIMAL(18,2) NOT NULL,
HanSuDung DATE NOT NULL,

CONSTRAINT PK_ChiTietPhieuNhap PRIMARY KEY (MaPhieuNhap, MaVatTu),
CONSTRAINT FK_CTPN_PhieuNhap FOREIGN KEY (MaPhieuNhap) REFERENCES PhieuNhapHang(MaPhieuNhap),
CONSTRAINT FK_CTPN_VatTu FOREIGN KEY (MaVatTu) REFERENCES VatTu(MaVT),
CONSTRAINT CK_CTPN_SoLuong CHECK (SoLuong > 0),
CONSTRAINT CK_CTPN_DonGia CHECK (DonGia >= 0)
);
GO

-- =========================================================
-- 14. HÓA ĐƠN
-- =========================================================
CREATE TABLE HoaDon (
MaHD CHAR(6) PRIMARY KEY,
MaBenhNhan CHAR(6) NOT NULL,
MaNV CHAR(6) NOT NULL,
NgayLap DATETIME2 NOT NULL DEFAULT SYSDATETIME(),
ChietKhau DECIMAL(5,2) NOT NULL DEFAULT 0,
MaKM CHAR(6) NULL,
TrangThai NVARCHAR(50) NOT NULL,

CONSTRAINT CK_HoaDon_MaHD CHECK (MaHD LIKE 'HD[0-9][0-9][0-9][0-9]'),
CONSTRAINT FK_HoaDon_BenhNhan FOREIGN KEY (MaBenhNhan) REFERENCES BenhNhan(MaBenhNhan),
CONSTRAINT FK_HoaDon_NhanVien FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNV),
CONSTRAINT FK_HoaDon_KhuyenMai FOREIGN KEY (MaKM) REFERENCES KhuyenMai(MaKM),
CONSTRAINT CK_HoaDon_ChietKhau CHECK (ChietKhau >= 0 AND ChietKhau <= 100),
CONSTRAINT CK_HoaDon_TrangThai CHECK (TrangThai IN (N'Đã thanh toán', N'Chưa thanh toán'))
);
GO

-- =========================================================
-- 15. CHI TIẾT HÓA ĐƠN
-- =========================================================
CREATE TABLE ChiTietHoaDon (
MaHD CHAR(6) NOT NULL,
MaDV CHAR(6) NOT NULL,
SoLuong INT NOT NULL,
DonGia DECIMAL(18,2) NOT NULL,

CONSTRAINT PK_ChiTietHoaDon PRIMARY KEY (MaHD, MaDV),
CONSTRAINT FK_CTHD_HoaDon FOREIGN KEY (MaHD) REFERENCES HoaDon(MaHD),
CONSTRAINT FK_CTHD_DichVu FOREIGN KEY (MaDV) REFERENCES DichVu(MaDV),
CONSTRAINT CK_CTHD_SoLuong CHECK (SoLuong > 0),
CONSTRAINT CK_CTHD_DonGia CHECK (DonGia >= 0)
);
GO
--=======================================================
-- 16 Chi tiết VatTu_DichVu
--=======================================================

CREATE TABLE ChiTietVatTu_DichVu (
                                     MaVT CHAR(6) NOT NULL,
                                     MaDV CHAR(6) NOT NULL,
                                     SoLuongSuDung INT NOT NULL DEFAULT 1,

                                     CONSTRAINT PK_ChiTietVatTu_DichVu
                                         PRIMARY KEY (MaVT, MaDV),

                                     CONSTRAINT FK_ChiTietVatTu_DichVu_VatTu
                                         FOREIGN KEY (MaVT)
                                             REFERENCES VatTu(MaVT),

                                     CONSTRAINT FK_ChiTietVatTu_DichVu_DichVu
                                         FOREIGN KEY (MaDV)
                                             REFERENCES DichVu(MaDV),

                                     CONSTRAINT CK_ChiTietVatTu_DichVu_SoLuong
                                         CHECK (SoLuongSuDung > 0)
);
GO

-- ================= TRIGGER =================
CREATE TRIGGER TRG_AUTO_NV
    ON NhanVien
    INSTEAD OF INSERT
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @START INT;

SELECT @START = ISNULL(MAX(CAST(RIGHT(MaNV, 4) AS INT)), 0)
FROM NhanVien
WHERE MaNV LIKE 'NV[0-9][0-9][0-9][0-9]';

;WITH CTE AS (
    SELECT
        ROW_NUMBER() OVER (ORDER BY (SELECT 1)) AS RN,
        HoTen,
        SoDT,
        ISNULL(NgayVaoLam, CAST(GETDATE() AS DATE)) AS NgayVaoLam,
        GioiTinh,
        ChucVu,
        TrangThai,
        Email,
        DiaChi
    FROM inserted
),
      DATA_NV AS (
          SELECT
              'NV' + RIGHT('0000' + CAST(@START + RN AS VARCHAR(4)), 4) AS MaNV_Moi,
     HoTen,
     SoDT,
     NgayVaoLam,
     GioiTinh,
     ChucVu,
     TrangThai,
     Email,
     DiaChi
 FROM CTE
     )
 INSERT INTO NhanVien (MaNV, HoTen, SoDT, NgayVaoLam, GioiTinh, ChucVu, TrangThai, Email, DiaChi)
SELECT MaNV_Moi, HoTen, SoDT, NgayVaoLam, GioiTinh, ChucVu, TrangThai, Email, DiaChi
FROM DATA_NV;

;WITH CTE AS (
    SELECT
        ROW_NUMBER() OVER (ORDER BY (SELECT 1)) AS RN,
        ChucVu,
        TrangThai
    FROM inserted
)
 INSERT INTO TaiKhoan (TenDangNhap, MatKhau, Quyen, MaNV, TrangThai)
SELECT
    'NV' + RIGHT('0000' + CAST(@START + RN AS VARCHAR(4)), 4),
    '123456',
    ChucVu,
    'NV' + RIGHT('0000' + CAST(@START + RN AS VARCHAR(4)), 4),
    CASE WHEN TrangThai = N'Đang làm' THEN N'Hoạt động' ELSE N'Khóa' END
    FROM CTE;
END;
GO