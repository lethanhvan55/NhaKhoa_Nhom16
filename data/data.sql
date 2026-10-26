USE QL_NK;
GO

-- =========================================================
-- 1. DU LIEU BENH NHAN
-- =========================================================
INSERT INTO BenhNhan
    (MaBenhNhan, HoTen, NgaySinh, GioiTinh, SoDT, Email, DiaChi)
VALUES
('BN0001', N'Nguyễn Văn An', '1995-05-12', 1, '0901122334', 'an.nguyen@gmail.com', N'Quận 1, TP.HCM'),
('BN0002', N'Trần Thị Bình', '1990-08-20', 0, '0901122335', 'binh.tran@gmail.com', N'Quận 3, TP.HCM'),
('BN0003', N'Lê Minh Cường', '1988-02-15', 1, '0901122336', 'cuong.le@gmail.com', N'Thủ Đức, TP.HCM'),
('BN0004', N'Phạm Thị Dung', '2000-11-05', 0, '0901122337', 'dung.pham@gmail.com', N'Bình Thạnh, TP.HCM'),
('BN0005', N'Hoàng Quốc Em', '1992-12-30', 1, '0901122338', 'em.hoang@gmail.com', N'Gò Vấp, TP.HCM'),
('BN0006', N'Võ Thanh Giang', '1998-04-18', 1, '0901122339', 'giang.vo@gmail.com', N'Quận 5, TP.HCM'),
('BN0007', N'Đặng Mỹ Hạnh', '1993-07-22', 0, '0901122340', 'hanh.dang@gmail.com', N'Quận 10, TP.HCM'),
('BN0008', N'Bùi Gia Khang', '1985-09-10', 1, '0901122341', 'khang.bui@gmail.com', N'Tân Bình, TP.HCM'),
('BN0009', N'Ngô Thị Lan', '1997-03-25', 0, '0901122342', 'lan.ngo@gmail.com', N'Phú Nhuận, TP.HCM'),
('BN0010', N'Phan Nhật Minh', '1991-06-08', 1, '0901122343', 'minh.phan@gmail.com', N'Quận 7, TP.HCM');
GO

-- =========================================================
-- 2. DU LIEU NHAN VIEN
-- Chèn từng nhân viên để trigger tạo mã và tài khoản lần lượt
-- =========================================================
INSERT INTO NhanVien
    (HoTen, SoDT, NgayVaoLam, GioiTinh, ChucVu, TrangThai, Email, DiaChi)
VALUES
(N'Dr. Nguyễn Hải Sơn', '0988111221', '2022-01-10', 1, N'Quản lý', N'Đang làm', 'son.nguyen@nhakhoa.com', N'Quận 1, TP.HCM');
GO

INSERT INTO NhanVien
    (HoTen, SoDT, NgayVaoLam, GioiTinh, ChucVu, TrangThai, Email, DiaChi)
VALUES
(N'Dr. Trần Mai Linh', '0988111222', '2022-03-15', 0, N'Nha sĩ', N'Đang làm', 'linh.tran@nhakhoa.com', N'Quận 3, TP.HCM');
GO

INSERT INTO NhanVien
    (HoTen, SoDT, NgayVaoLam, GioiTinh, ChucVu, TrangThai, Email, DiaChi)
VALUES
(N'Lê Thu Thảo', '0988111223', '2023-05-20', 0, N'Nha sĩ', N'Đang làm', 'thao.le@nhakhoa.com', N'Bình Thạnh, TP.HCM');
GO

INSERT INTO NhanVien
    (HoTen, SoDT, NgayVaoLam, GioiTinh, ChucVu, TrangThai, Email, DiaChi)
VALUES
(N'Phạm Văn Hoàng', '0988111224', '2023-06-01', 1, N'Lễ tân', N'Đang làm', 'hoang.pham@nhakhoa.com', N'Gò Vấp, TP.HCM');
GO

INSERT INTO NhanVien
    (HoTen, SoDT, NgayVaoLam, GioiTinh, ChucVu, TrangThai, Email, DiaChi)
VALUES
(N'Đỗ Hoàng Yến', '0988111225', '2024-01-15', 0, N'Lễ tân', N'Đang làm', 'yen.do@nhakhoa.com', N'Quận 10, TP.HCM');
GO

-- Kiểm tra mã nhân viên và tài khoản do trigger tạo
SELECT * FROM NhanVien;
SELECT * FROM TaiKhoan;
GO

-- =========================================================
-- 3. DU LIEU PHONG DIEU TRI
-- =========================================================
INSERT INTO PhongDieuTri
    (MaPhong, TenPhong, LoaiPhong, ViTri, TrangThai, MoTa)
VALUES
('P00001', N'Phòng khám VIP 1', N'VIP', N'Tầng 1 - Khu A', N'Trống', N'Phòng khám nha khoa cao cấp'),
('P00002', N'Phòng tiểu phẫu', N'Tiểu phẫu', N'Tầng 1 - Khu B', N'Trống', N'Chuyên nhổ răng khôn và cắm Implant'),
('P00003', N'Phòng tổng quát 1', N'Thường', N'Tầng 2 - Khu A', N'Đang sử dụng', N'Khám và cạo vôi răng'),
('P00004', N'Phòng X-Quang', N'Chụp X-Quang', N'Tầng 2 - Khu B', N'Trống', N'Chụp phim nha khoa'),
('P00005', N'Phòng xét nghiệm', N'Xét nghiệm', N'Tầng trệt', N'Trống', N'Xét nghiệm trước tiểu phẫu');
GO

-- =========================================================
-- 4. DU LIEU DICH VU
-- =========================================================
INSERT INTO DichVu
    (MaDV, TenDV, DonGia, ThoiGianDuKien, TrangThai)
VALUES
('DV0001', N'Khám và tư vấn tổng quát', 100000, 20, N'Đang cung cấp'),
('DV0002', N'Cạo vôi răng', 250000, 30, N'Đang cung cấp'),
('DV0003', N'Trám răng thẩm mỹ (Composite)', 350000, 40, N'Đang cung cấp'),
('DV0004', N'Nhổ răng sữa', 150000, 15, N'Đang cung cấp'),
('DV0005', N'Nhổ răng khôn (Tiểu phẫu)', 1500000, 60, N'Đang cung cấp'),
('DV0006', N'Tẩy trắng răng bằng Laser', 2000000, 45, N'Đang cung cấp'),
('DV0007', N'Chụp phim X-Quang răng', 120000, 10, N'Đang cung cấp');
GO


-- =========================================================
-- 5. DU LIEU VAT TU
-- =========================================================
INSERT INTO VatTu(MaVT, TenVatTu, DonViTinh, SoLuongTon,SoLuongToiThieu, HanSuDung, MoTa, HinhAnh)
VALUES
('VT0001', N'Thuốc tê dạng ống Lignocaine', N'Ống',150, 20, '2028-12-31',N'Thuốc tê sử dụng khi tiểu phẫu', N'images/thuoc_te.png'),

('VT0002', N'Chất trám Composite', N'Tuýp',30, 5, '2027-06-30',N'Vật liệu trám răng thẩm mỹ', N'images/composite.png'),

('VT0003', N'Bông gòn y tế', N'Gói',200, 30, NULL,N'Dụng cụ sát khuẩn và cầm máu', N'images/bong_gon.png'),

('VT0004', N'Kim tiêm nha khoa', N'Cái',500, 50, '2029-01-01',N'Kim tiêm dùng một lần', N'images/kim_tiem.png'),

('VT0005', N'Khẩu trang y tế', N'Hộp',50, 10, NULL,N'Vật tư bảo hộ y tế', N'images/khau_trang.png');
GO


-- =========================================================
-- 6. DU LIEU KHUYEN MAI
-- MaThayThe là mã khách nhập để áp dụng khuyến mãi
-- =========================================================
INSERT INTO KhuyenMai
    (MaKM, MaThayThe, TenKM, NgayBatDau, NgayKetThuc, DieuKien, ChietKhau, SoLuong, TrangThai)
VALUES
('KM0001', 'CAOVOI15', N'Ưu đãi hè - Giảm giá cạo vôi răng', '2026-06-01', '2026-06-30', N'Áp dụng cho khách hàng sử dụng dịch vụ cạo vôi răng', 15.00, 100, N'Hết hạn'),
('KM0002', 'KHAITRUONG10', N'Mừng khai trương chi nhánh', '2026-04-01', '2026-05-30', N'Hóa đơn trên 500.000 đồng', 10.00, 200, N'Hết hạn');
GO

-- =========================================================
-- 7. DU LIEU KE HOACH DIEU TRI
-- =========================================================
INSERT INTO KeHoachDieuTri
    (MaKeHoach, MaBenhNhan, ChanDoan, NgayBatDau, NgayKetThuc, ChiPhiDuKien, TrangThai)
VALUES
('KH0001', 'BN0001', N'Sâu răng hàm số 6, viêm nướu nhẹ', '2026-04-01', '2026-04-10', 600000, N'Đang thực hiện'),
('KH0002', 'BN0002', N'Răng khôn mọc lệch gây đau', '2026-04-05', '2026-04-06', 1500000, N'Hoàn thành');
GO

-- =========================================================
-- 8. DU LIEU HO SO BENH NHAN
-- =========================================================
INSERT INTO HoSoBenhNhan
    (MaHoSo, MaBenhNhan, MaNV, MaKeHoach, NgayKham, TinhTrangRangMieng, ChanDoan, PhuongPhapDieuTri, TienSuBenh, DiUng, BenhLyNen, NguoiLienHe, SoDTNLH, GhiChu)
VALUES
('HS0001', 'BN0001', 'NV0002', 'KH0001', '2026-04-01', N'Nhiều vôi răng, có lỗ sâu ở răng hàm dưới', N'Sâu ngà nông', N'Cạo vôi răng và trám răng Composite', N'Không', N'Không', N'Không', N'Nguyễn Văn Ba', '0901234567', N'Bệnh nhân nhạy cảm với ê buốt'),
('HS0002', 'BN0002', 'NV0003', 'KH0002', '2026-04-05', N'Răng số 8 hàm dưới mọc ngang', N'Viêm lợi trùm do răng khôn', N'Tiểu phẫu nhổ răng khôn', N'Không', N'Kháng sinh Penicillin', N'Huyết áp nhẹ', N'Trần Thị Hoa', '0901234568', N'Đã uống thuốc giảm đau trước khi đến');
GO

-- =========================================================
-- 9. DU LIEU LICH LAM VIEC
-- =========================================================
INSERT INTO LichLamViec
    (MaLich, MaPhong, MaNhanVien, NgayLam, CaLam, GioBatDau, GioKetThuc, TrangThai)
VALUES
('LLV20260401', 'P00003', 'NV0002', '2026-04-01', 'Sáng', '2026-04-01 08:00:00', '2026-04-01 12:00:00', N'Đã phân công'),
('LLV20260402', 'P00002', 'NV0003', '2026-04-05', 'Chiều', '2026-04-05 13:30:00', '2026-04-05 17:30:00', N'Đã phân công');
GO

-- =========================================================
-- 10. DU LIEU LICH HEN
-- =========================================================
INSERT INTO LichHen
    (MaLichHen, MaBenhNhan, MaDichVu, MaPhong, MaNV, NgayHen, GioHen, TrangThai, GhiChu)
VALUES
('LH000001', 'BN0001', 'DV0002', 'P00003', 'NV0002', '2026-04-01', '2026-04-01 09:00:00', N'Hoàn thành', N'Khách đến đúng giờ'),
('LH000002', 'BN0002', 'DV0005', 'P00002', 'NV0003', '2026-04-05', '2026-04-05 14:00:00', N'Hoàn thành', N'Tiểu phẫu thành công');
GO

-- =========================================================
-- 11. DU LIEU PHIEU NHAP HANG
-- Gán mã rõ ràng vì bảng không được xác nhận có trigger tự sinh mã
-- =========================================================
INSERT INTO PhieuNhapHang
    (MaPhieuNhap, NgayNhap, MaNV, NhaCungCap, GhiChu)
VALUES
('PN0001', '2026-04-01', 'NV0004', N'Công ty TNHH Thiết Bị Y Tế Sài Gòn', N'Nhập vật tư định kỳ tháng 4'),
('PN0002', '2026-04-05', 'NV0004', N'Dược Phẩm Phương Nam', N'Nhập thuốc tê và bông băng');
GO

-- =========================================================
-- 12. DU LIEU CHI TIET PHIEU NHAP
-- =========================================================
INSERT INTO ChiTietPhieuNhap
    (MaPhieuNhap, MaVatTu, SoLuong, DonGia, HanSuDung)
VALUES
('PN0001', 'VT0001', 50, 45000, '2028-12-31'),
('PN0001', 'VT0002', 15, 120000, '2027-06-30'),
('PN0002', 'VT0003', 100, 15000, '2030-01-01');
GO

-- =========================================================
-- 13. DU LIEU HOA DON
-- Gán mã rõ ràng vì bảng không được xác nhận có trigger tự sinh mã
-- =========================================================
INSERT INTO HoaDon
    (MaHD, MaBenhNhan, MaNV, NgayLap, ChietKhau, MaKM, TrangThai)
VALUES
('HD0001', 'BN0001', 'NV0004', '2026-04-01', 0.00, NULL, N'Đã thanh toán'),
('HD0002', 'BN0002', 'NV0004', '2026-04-05', 10.00, 'KM0002', N'Đã thanh toán');
GO

-- =========================================================
-- 14. DU LIEU CHI TIET HOA DON
-- =========================================================
INSERT INTO ChiTietHoaDon
    (MaHD, MaDV, SoLuong, DonGia)
VALUES
('HD0001', 'DV0002', 1, 250000),
('HD0001', 'DV0003', 1, 350000),
('HD0002', 'DV0005', 1, 1500000);
GO

-- =========================================================
-- 16. DU LIEU CHI TIET VAT TU - DICH VU
-- =========================================================
INSERT INTO ChiTietVatTu_DichVu
    (MaVT, MaDV, SoLuongSuDung)
VALUES
('VT0001', 'DV0005', 2),
('VT0001', 'DV0004', 1),
('VT0002', 'DV0003', 1),
('VT0003', 'DV0005', 2),
('VT0003', 'DV0002', 1),
('VT0004', 'DV0005', 1),
('VT0005', 'DV0001', 1),
('VT0005', 'DV0002', 1);
GO


-- =========================================================
-- 15. KIEM TRA DU LIEU SAU KHI THEM
-- =========================================================
SELECT * FROM BenhNhan;
SELECT * FROM NhanVien;
SELECT * FROM TaiKhoan;
SELECT * FROM PhongDieuTri;
SELECT * FROM DichVu;
SELECT * FROM VatTu;
SELECT * FROM KhuyenMai;
SELECT * FROM KeHoachDieuTri;
SELECT * FROM HoSoBenhNhan;
SELECT * FROM LichLamViec;
SELECT * FROM LichHen;
SELECT * FROM PhieuNhapHang;
SELECT * FROM ChiTietPhieuNhap;
SELECT * FROM HoaDon;
SELECT * FROM ChiTietHoaDon;
GO
