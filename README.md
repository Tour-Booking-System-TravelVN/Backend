# Xây dựng hệ thống quản lý tour du lịch trong nước cho công ty TravelVN

## 1. Giới thiệu đề tài

Hệ thống quản lý tour du lịch trong nước TravelVN hỗ trợ doanh nghiệp trong việc tổ chức, vận hành và kinh doanh các tour du lịch nội địa một cách chuyên nghiệp và hiện đại. Hệ thống cung cấp đầy đủ các chức năng cho nhiều vai trò: người quản lý, người điều hành tour, hướng dẫn viên du lịch và khách hàng.

---

## 2. Thông tin nhóm thực hiện

| Họ tên                 | Vai trò                                                                                                      |
| ---------------------- | ------------------------------------------------------------------------------------------------------------ |
| **Kiều Đức Thịnh**     | Nhóm trưởng - Phụ trách fullstack các chức năng phía **khách hàng**                                          |
| **Bùi Tuấn Anh**       | Phát triển **giao diện mobile (Kotlin)** cho **khách hàng** và **hướng dẫn viên**, chức năng **nhắn tin**    |
| **Đàm Khắc Quang Anh** | Phát triển **backend** cho **người quản lý** và **người điều hành tour**, **kết nối API** frontend tương ứng |
| **Đào Việt Anh**       | Phát triển **frontend** cho **người quản lý** và **người điều hành tour**                                    |

---

## 3. Danh sách chức năng chính (Use Case)

### 3.1. Chức năng dành cho Người quản lý

- Quản lý tài khoản hệ thống, nhân viên
- Duyệt đánh giá
- Xem báo cáo thống kê doanh thu, chi phí, lợi nhuận
- Xem và tìm kiếm khách hàng, tour, đơn vị tour
- Quản lý thông tin tài khoản cá nhân

### 3.2. Chức năng dành cho Người điều hành tour

- Quản lý tour, đơn vị tour, khách hàng
- Quản lý chương trình giảm giá, lễ hội
- Duyệt hủy tour
- Xem và tìm kiếm hướng dẫn viên
- Xem thông tin khách hàng trong tour
- Quản lý tài khoản cá nhân

### 3.3. Chức năng dành cho Hướng dẫn viên

- Quản lý tài khoản cá nhân
- Quản lý nhóm chat: nhắn tin, xóa/thu hồi tin, chặn/cho phép khách hàng tham gia

### 3.4. Chức năng dành cho Khách hàng

- Đăng ký/Đăng nhập, xem/sửa thông tin, đổi mật khẩu
- Tìm kiếm, đặt, thanh toán tour (PayOS API)
- Gửi yêu cầu hủy tour
- Đánh giá tour sau khi hoàn thành
- Tham gia nhóm chat qua xác thực mã đặt chỗ, nhắn tin, thu hồi tin

### 3.5. Chức năng hệ thống

- Tự động cập nhật trạng thái tour và hủy nhóm chat sau 7 ngày kế từ khi tour kết thúc

---

## 4. Công nghệ sử dụng

- **Frontend Web**: HTML, CSS (Bootstrap), JavaScript, jQuery
- **Frontend Mobile**: Kotlin (Android)
- **Backend**: Spring Boot (Java), RESTful API, JWT Authentication
- **Cơ sở dữ liệu**: MySQL, Firebase
- **Thanh toán**: Tích hợp API PayOS
- **Bảo mật**: JWT

---

## 5. Hướng dẫn build và chạy

### 5.1 Backend (Spring Boot)

```bash
# 1. Clone source code
$ git clone <repository-url>
$ cd Backend

# 2. Cấu hình file application.properties
# (Chỉnh sửa thông tin kết nối database, JWT secret, PayOS API key...)

# 3. Build project
$ ./mvnw clean install

# 4. Chạy ứng dụng
$ ./mvnw spring-boot:run
```

### 5.2 Frontend Web (Người quản lý, điều hành, khách hàng)

```bash
# Mở file index.html trong thư mục tương ứng với vai trò
# hoặc triển khai trên server như Apache/Nginx nếu cần.
```

### 5.3 Frontend Mobile (Kotlin)

```bash
# Mở bằng Android Studio
# Build và chạy giả lập hoặc thiết bị thực tế
```

---

