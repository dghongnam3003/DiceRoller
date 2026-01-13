# Project Documentation

## English

### Overview
This project is a development workspace for building applications. It includes a Dice Roller app that allows users to roll a virtual dice and view the result on the screen.

### Features
- Supports multiple languages
- Easy to configure
- Modular design
- Interactive UI for rolling dice

### Installation
1. Clone the repository
2. Run `./gradlew build`
3. Start the application

### Usage
- Configure the project in `build.gradle.kts`
- Add your code in the `app/` directory
- Run tests with `./gradlew test`

### Flow Description
1. **Initialization**: The app initializes the `MainActivity` and sets up the UI components.
2. **User Interaction**: The user clicks the "Roll" button to trigger the dice roll.
3. **Dice Roll Logic**: The `Dice` class generates a random number between 1 and 6 (or any specified number of sides).
4. **Result Display**: The result is displayed on the screen via a `TextView`.

## Vietnamese

### Tổng quan
Dự án này là một không gian làm việc phát triển để xây dựng các ứng dụng. Nó bao gồm một ứng dụng Dice Roller cho phép người dùng lăn một con xúc xắc ảo và xem kết quả trên màn hình.

### Tính năng
- Hỗ trợ nhiều ngôn ngữ
- Dễ dàng cấu hình
- Thiết kế mô-đun
- Giao diện tương tác để lăn xúc xắc

### Cài đặt
1. Sao chép kho lưu trữ
2. Chạy `./gradlew build`
3. Khởi động ứng dụng

### Sử dụng
- Cấu hình dự án trong `build.gradle.kts`
- Thêm mã của bạn vào thư mục `app/`
- Chạy kiểm tra với `./gradlew test`

### Mô tả luồng
1. **Khởi tạo**: Ứng dụng khởi tạo `MainActivity` và thiết lập các thành phần UI.
2. **Tương tác người dùng**: Người dùng nhấp vào nút "Roll" để kích hoạt việc lăn xúc xắc.
3. **Logic lăn xúc xắc**: Lớp `Dice` tạo ra một số ngẫu nhiên từ 1 đến 6 (hoặc bất kỳ số mặt nào được chỉ định).
4. **Hiển thị kết quả**: Kết quả được hiển thị trên màn hình thông qua `TextView`.