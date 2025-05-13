# Zentech-PolyCafe 🍵

[![CI Status](<<BADGE_URL>>)](<<BADGE_URL>>) [![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT) [![Java Version](https://img.shields.io/badge/Java-23-orange)](https://www.oracle.com/java/)

Ứng dụng desktop hiện đại dành cho việc quản lý quán café, phát triển bởi nhóm Zentech. Giải pháp toàn diện giúp chủ quán và nhân viên quản lý sản phẩm, đơn hàng và cải thiện trải nghiệm khách hàng.

![ZENTECH Banner](/readme/Zentech-main.jpg)

## Mục lục

- [Tính năng](#tính-năng)
- [Demo](#demo)
- [Yêu cầu hệ thống](#yêu-cầu-hệ-thống)
- [Cài đặt & Khởi động nhanh](#cài-đặt--khởi-động-nhanh)
- [Hướng dẫn sử dụng](#hướng-dẫn-sử-dụng)
- [Đóng góp](#đóng-góp)
- [Giấy phép](#giấy-phép)
- [Liên hệ & Hỗ trợ](#liên-hệ--hỗ-trợ)

## Tính năng

- **Quản lý sản phẩm**: Thêm, sửa, xóa các mặt hàng đồ uống và thực phẩm với giá cả, danh mục và mô tả chi tiết
- **Xử lý đơn đặt hàng**: Tạo, theo dõi và quản lý đơn hàng theo thời gian thực với giao diện trực quan
- **Báo cáo & Thống kê**: Phân tích doanh thu, sản phẩm bán chạy và các xu hướng khác để hỗ trợ ra quyết định kinh doanh

## Demo

| | |
|---|---|
| ![Quản lý sản phẩm](docs/img/placeholder1.png) | ![Tạo đơn hàng](docs/img/placeholder2.png) |
| ![Thống kê doanh thu](docs/img/placeholder3.png) | ![Quản lý nhân viên](docs/img/placeholder4.png) |

## Yêu cầu hệ thống

- Java 23 trở lên
- Hỗ trợ: Windows, macOS, Linux (môi trường Desktop)
- Tối thiểu: 4GB RAM, 1GB dung lượng ổ đĩa

## Cài đặt & Khởi động nhanh

1. Tải file JAR mới nhất từ [trang Releases](https://github.com/Zentech-VN/Zentech-PolyCafe/releases)
2. Chạy ứng dụng với lệnh:

```bash
java -jar polycafe.jar
```

## Hướng dẫn sử dụng

### API cơ bản

```java
// Khởi tạo quản lý sản phẩm
ProductManager productManager = new ProductManager();

// Thêm sản phẩm mới
Product coffee = new Product("Cà phê đen", 25000, Category.COFFEE);
productManager.addProduct(coffee);

// Tạo đơn hàng mới
Order order = new Order();
order.addItem(coffee, 2);
order.processPayment(PaymentMethod.CASH);
```

Xem thêm ví dụ chi tiết trong [tài liệu API](docs/api.md).

## Đóng góp

Chúng tôi rất hoan nghênh mọi đóng góp! Nếu bạn muốn tham gia phát triển Zentech-PolyCafe:

1. Fork repository này
2. Tạo branch cho tính năng của bạn (`git checkout -b feature/amazing-feature`)
3. Commit thay đổi (`git commit -m 'Add some amazing feature'`)
4. Push lên branch (`git push origin feature/amazing-feature`)
5. Mở Pull Request

Vui lòng đọc [Quy tắc đóng góp](<<CONTRIB_RULES>>) và [Quy tắc ứng xử](CODE_OF_CONDUCT.md) trước khi tham gia.

## Giấy phép

Dự án này được phân phối dưới Giấy phép MIT. Xem file [LICENSE](LICENSE) để biết thêm thông tin.

## Liên hệ & Hỗ trợ

- **Website**: [https://github.com/Zentech-VN](https://github.com/Zentech-VN)
- **Báo lỗi**: [Trang Issues](https://github.com/Zentech-VN/Zentech-PolyCafe/issues)
- **Email**: support@zentech.vn

---

&copy; 2025 Nhóm Zentech. Đã đăng ký bản quyền.