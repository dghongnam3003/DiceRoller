# Dice Roller 🎲

Một ứng dụng Android đơn giản mô phỏng việc lắc xúc xắc. Được xây dựng bằng Kotlin và Android SDK.

## Tính năng

- **Giao diện đơn giản**: Giao diện người dùng sạch sẽ và trực quan
- **Lắc xúc xắc ngẫu nhiên**: Mô phỏng xúc xắc 6 mặt với việc tạo số ngẫu nhiên
- **Kết quả tức thì**: Chạm vào nút lắc để nhận kết quả ngay lập tức
- **Thiết kế Material**: Tuân theo hướng dẫn thiết kế của Android

## Ảnh chụp màn hình

*Thêm ảnh chụp màn hình của ứng dụng của bạn ở đây*

## Bắt đầu

### Yêu cầu tiên quyết

- Android Studio Arctic Fox (2020.3.1) hoặc mới hơn
- Android SDK API 21+ (Android 5.0 Lollipop)
- Kotlin 1.9.0+

### Cài đặt

1. **Clone kho lưu trữ**
   ```bash
   git clone <đường-dẫn-kho-lưu-trữ-của-bạn>
   cd diceroller
   ```

2. **Mở trong Android Studio**
   - Khởi động Android Studio
   - Chọn "Open an Existing Project"
   - Điều hướng đến thư mục dự án và chọn nó

3. **Xây dựng và Chạy**
   - Kết nối một thiết bị Android hoặc khởi động trình mô phỏng
   - Nhấn nút "Run" hoặc nhấn `Shift + F10`

## Cấu trúc dự án

```
app/
├── src/
│   ├── main/
│   │   ├── java/com/example/diceroller/
│   │   │   └── MainActivity.kt          # Hoạt động chính với logic xúc xắc
│   │   ├── res/
│   │   │   ├── layout/
│   │   │   │   └── activity_main.xml    # Tệp bố cục chính
│   │   │   ├── values/
│   │   │   │   ├── strings.xml          # Tài nguyên chuỗi
│   │   │   │   ├── colors.xml           # Định nghĩa màu sắc
│   │   │   │   └── themes.xml           # Chủ đề ứng dụng
│   │   │   └── AndroidManifest.xml      # Bản kê khai ứng dụng
│   │   └── androidTest/                 # Kiểm thử công cụ
│   └── test/                            # Kiểm thử đơn vị
├── build.gradle.kts                     # Cấu hình xây dựng cấp ứng dụng
└── proguard-rules.pro                   # Quy tắc ProGuard
```

## Cách hoạt động

Ứng dụng chứa một lớp `Dice` đơn giản tạo ra các số ngẫu nhiên:

```kotlin
class Dice(private val numSides: Int) {
    fun roll(): Int {
        return (1..numSides).random()
    }
}
```

Khi người dùng chạm vào nút "ROLL", ứng dụng:
1. Tạo một đối tượng xúc xắc 6 mặt mới
2. Gọi phương thức `roll()` để tạo ra một số ngẫu nhiên (1-6)
3. Cập nhật TextView để hiển thị kết quả

## Các thành phần chính

- **MainActivity.kt**: Chứa logic ứng dụng chính và xử lý giao diện người dùng
- **Lớp Dice**: Xử lý cơ chế lắc xúc xắc
- **activity_main.xml**: Định nghĩa bố cục giao diện người dùng
- **strings.xml**: Chứa tài nguyên văn bản của ứng dụng

## Phát triển

### Chạy kiểm thử

```bash
# Chạy kiểm thử đơn vị
./gradlew test

# Chạy kiểm thử công cụ (yêu cầu thiết bị/trình mô phỏng)
./gradlew connectedAndroidTest
```

### Xây dựng APK

```bash
# Xây dựng gỡ lỗi
./gradlew assembleDebug

# Xây dựng phát hành
./gradlew assembleRelease
```

## Tùy chỉnh

Bạn có thể dễ dàng tùy chỉnh xúc xắc:

1. **Thay đổi số mặt**: Sửa đổi tham số `numSides` trong MainActivity
2. **Thêm nhiều xúc xắc**: Tạo nhiều đối tượng Dice
3. **Tùy chỉnh giao diện người dùng**: Chỉnh sửa các tệp bố cục trong `res/layout/`
4. **Thêm hoạt ảnh**: Triển khai hoạt ảnh lắc để cải thiện trải nghiệm người dùng

## Đóng góp

1. Fork dự án
2. Tạo nhánh tính năng của bạn (`git checkout -b feature/tính-năng-tuyệt-vời`)
3. Commit các thay đổi của bạn (`git commit -m 'Thêm một số tính năng tuyệt vời'`)
4. Push vào nhánh (`git push origin feature/tính-năng-tuyệt-vời`)
5. Mở Pull Request

## Công nghệ được sử dụng

- **Kotlin**: Ngôn ngữ lập trình chính
- **Android SDK**: Khung phát triển Android
- **Gradle**: Công cụ tự động hóa xây dựng
- **Android Studio**: Môi trường phát triển tích hợp

## Giấy phép

Dự án này được cấp phép theo Giấy phép MIT - xem tệp [LICENSE](LICENSE) để biết chi tiết.

## Lời cảm ơn

- Tài liệu Android Developers
- Tài liệu Kotlin
- Hướng dẫn Material Design

---

**Chúc bạn lắc xúc xắc vui vẻ!** 🎲