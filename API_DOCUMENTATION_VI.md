# Tài liệu API

## Tổng quan

Tài liệu này cung cấp tài liệu API toàn diện cho ứng dụng Android Dice Roller. Mặc dù đây là một ứng dụng đơn giản, việc tài liệu hóa các giao diện công khai giúp duy trì tính rõ ràng của mã và cho phép mở rộng trong tương lai.

## API Công khai

### Lớp Dice

Lớp `Dice` cung cấp chức năng cốt lõi cho các hoạt động lắc xúc xắc.

#### Khai báo Lớp
```kotlin
class Dice(private val numSides: Int)
```

#### Tham số Constructor

| Tham số | Kiểu | Mô tả | Ràng buộc |
|-----------|------|-------------|-------------|
| `numSides` | `Int` | Số mặt của xúc xắc | Phải là số dương (> 0) |

#### Phương thức Công khai

##### `roll(): Int`

Tạo ra một số ngẫu nhiên đại diện cho một lần lắc xúc xắc.

**Trả về:**
- `Int`: Một số nguyên ngẫu nhiên trong khoảng từ 1 đến `numSides` (bao gồm cả hai)

**Độ phức tạp Thời gian:** O(1)  
**Độ phức tạp Không gian:** O(1)

**Ví dụ Sử dụng:**
```kotlin
val dice = Dice(6)
val result = dice.roll()
println(result) // In ra một số từ 1 đến 6
```

**Chi tiết Triển khai:**
- Sử dụng hàm `random()` tích hợp sẵn của Kotlin
- Đảm bảo phân phối đồng đều trên tất cả các giá trị có thể
- An toàn luồng (không có trạng thái có thể thay đổi được chia sẻ)

#### Ví dụ Sử dụng

##### Xúc xắc Sáu Mặt Cơ bản
```kotlin
val standardDice = Dice(6)
val rollResult = standardDice.roll()
// rollResult sẽ là 1, 2, 3, 4, 5, hoặc 6
```

##### Xúc xắc Tùy chỉnh
```kotlin
val twentySidedDice = Dice(20)
val d20Roll = twentySidedDice.roll()
// d20Roll sẽ là một số từ 1 đến 20
```

##### Nhiều Lần Lắc
```kotlin
val dice = Dice(6)
val rolls = (1..5).map { dice.roll() }
// rolls chứa 5 lần lắc xúc xắc độc lập
```

### Lớp MainActivity

Lớp `MainActivity` xử lý giao diện người dùng và điều phối các hoạt động lắc xúc xắc.

#### Khai báo Lớp
```kotlin
class MainActivity : AppCompatActivity()
```

#### Phương thức Chính

##### `onCreate(savedInstanceState: Bundle?)`

**Truy cập:** `protected override`  
**Mục đích:** Khởi tạo hoạt động và thiết lập giao diện người dùng

**Tham số:**
- `savedInstanceState: Bundle?`: Trạng thái đã lưu từ phiên bản trước (có thể null)

**Triển khai:**
```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    
    val rollButton: Button = findViewById(R.id.button2)
    rollButton.setOnClickListener { rollDice() }
}
```

##### `rollDice()`

**Truy cập:** `private`  
**Mục đích:** Xử lý logic lắc xúc xắc và cập nhật giao diện người dùng

**Luồng Triển khai:**
1. Tạo một phiên bản `Dice` mới với 6 mặt
2. Gọi phương thức `roll()` để tạo kết quả ngẫu nhiên
3. Cập nhật TextView kết quả với giá trị mới

**Ví dụ:**
```kotlin
private fun rollDice() {
    val dice = Dice(6)
    val diceRoll = dice.roll()
    
    val resultTextView: TextView = findViewById(R.id.textView)
    resultTextView.text = diceRoll.toString()
}
```

## Hợp đồng Giao diện

### Hợp đồng Lớp Dice

#### Bất biến
- `numSides` phải giữ nguyên sau khi tạo đối tượng
- `numSides` phải là một số nguyên dương

#### Điều kiện Tiền
- Constructor: `numSides > 0`

#### Điều kiện Hậu
- `roll()`: Trả về giá trị trong khoảng `[1, numSides]`
- `roll()`: Mỗi lần gọi là độc lập (không có tác dụng phụ)

#### Xử lý Lỗi
Hiện tại, lớp Dice không thực hiện xác thực rõ ràng, nhưng các phiên bản trong tương lai nên:

```kotlin
class Dice(private val numSides: Int) {
    init {
        require(numSides > 0) { "Số mặt phải là số dương" }
    }
    
    fun roll(): Int {
        return (1..numSides).random()
    }
}
```

### Hợp đồng MainActivity

#### Hợp đồng Vòng đời
- Tuân theo vòng đời hoạt động Android tiêu chuẩn
- Khởi tạo đúng cách các thành phần giao diện người dùng trong `onCreate()`
- Xử lý các tương tác của người dùng thông qua trình nghe sự kiện

#### Quản lý Trạng thái Giao diện Người dùng
- Duy trì kết quả lắc xúc xắc hiện tại trong TextView
- Cập nhật giao diện người dùng ngay lập tức khi có tương tác của người dùng
- Không có trạng thái lâu dài giữa các phiên ứng dụng

## Điểm Mở rộng

### Các Loại Xúc xắc Tùy chỉnh

API hiện tại có thể được mở rộng để hỗ trợ các loại xúc xắc khác nhau:

```kotlin
interface DiceInterface {
    fun roll(): Int
    fun getRange(): IntRange
}

class StandardDice(sides: Int) : DiceInterface {
    private val numSides = sides
    
    override fun roll(): Int = (1..numSides).random()
    override fun getRange(): IntRange = 1..numSides
}

class LoadedDice(sides: Int, private val bias: Int) : DiceInterface {
    private val numSides = sides
    
    override fun roll(): Int {
        // Triển khai với sự thiên vị đối với một số cụ thể
        return if (Random.nextFloat() < 0.3f) bias else (1..numSides).random()
    }
    
    override fun getRange(): IntRange = 1..numSides
}
```

### Hỗ trợ Nhiều Xúc xắc

Mở rộng API để xử lý nhiều xúc xắc:

```kotlin
class DiceSet(private val dice: List<Dice>) {
    fun rollAll(): List<Int> = dice.map { it.roll() }
    fun rollSum(): Int = rollAll().sum()
    fun rollAndFormat(): String = rollAll().joinToString(" + ") + " = ${rollSum()}"
}

// Sử dụng
val twoDice = DiceSet(listOf(Dice(6), Dice(6)))
val result = twoDice.rollSum() // Tổng của hai xúc xắc
```

### Hỗ trợ Hoạt ảnh

Thêm các callback hoạt ảnh:

```kotlin
interface RollAnimationListener {
    fun onRollStart()
    fun onRollComplete(result: Int)
}

class AnimatedDice(private val numSides: Int) {
    private var animationListener: RollAnimationListener? = null
    
    fun setAnimationListener(listener: RollAnimationListener) {
        this.animationListener = listener
    }
    
    fun rollWithAnimation(): Int {
        animationListener?.onRollStart()
        
        // Mô phỏng độ trễ hoạt ảnh
        val result = (1..numSides).random()
        
        animationListener?.onRollComplete(result)
        return result
    }
}
```

## Mô hình Dữ liệu

### Kết quả Lắc

Để nâng cao trong tương lai, hãy xem xét một mô hình kết quả có cấu trúc:

```kotlin
data class RollResult(
    val value: Int,
    val timestamp: Long = System.currentTimeMillis(),
    val diceType: String = "6-sided"
) {
    fun isMaxRoll(maxValue: Int): Boolean = value == maxValue
    fun isMinRoll(): Boolean = value == 1
}
```

### Cấu hình Xúc xắc

Để cấu hình xúc xắc:

```kotlin
data class DiceConfiguration(
    val sides: Int = 6,
    val label: String = "Standard Die",
    val color: String = "#FFFFFF",
    val customFaces: List<String>? = null
) {
    fun isValid(): Boolean = sides > 0 && (customFaces?.size == sides ?: true)
}
```

## Xử lý Lỗi

### Xử lý Lỗi Hiện tại

Triển khai hiện tại có xử lý lỗi tối thiểu. Dưới đây là các kịch bản lỗi tiềm ẩn:

#### Lỗi Thời gian Chạy
- **OutOfMemoryError**: Rất khó xảy ra do các hoạt động đơn giản
- **IllegalStateException**: Có thể xảy ra nếu các thành phần giao diện người dùng không được tìm thấy
- **NumberFormatException**: Không áp dụng (không có phân tích chuỗi)

#### Chiến lược Xử lý Lỗi trong Tương lai

```kotlin
sealed class DiceError : Exception() {
    object InvalidSides : DiceError()
    object RollFailed : DiceError()
    data class UIError(val component: String) : DiceError()
}

class SafeDice(private val numSides: Int) {
    fun roll(): Result<Int> {
        return try {
            if (numSides <= 0) {
                Result.failure(DiceError.InvalidSides)
            } else {
                val result = (1..numSides).random()
                Result.success(result)
            }
        } catch (e: Exception) {
            Result.failure(DiceError.RollFailed)
        }
    }
}
```

## Đặc điểm Hiệu suất

### Độ phức tạp Thời gian
- **Dice.roll()**: O(1) - Tạo số ngẫu nhiên trong thời gian không đổi
- **MainActivity.rollDice()**: O(1) - Cập nhật giao diện người dùng đơn giản

### Độ phức tạp Không gian
- **Dice**: O(1) - Chỉ lưu trữ số mặt
- **MainActivity**: O(1) - Không có cấp phát bộ nhớ động cho việc lắc xúc xắc

### Khả năng Mở rộng
- **Bộ nhớ**: Mỗi đối tượng Dice sử dụng bộ nhớ tối thiểu (~4 byte cho numSides)
- **CPU**: Tạo số ngẫu nhiên được tối ưu hóa cao
- **Đồng thời**: An toàn luồng cho các hoạt động đọc

## API Kiểm thử

### Trợ giúp Kiểm thử Đơn vị

```kotlin
object DiceTestUtils {
    fun testRandomness(dice: Dice, iterations: Int = 1000): Boolean {
        val results = (1..iterations).map { dice.roll() }
        val distribution = results.groupingBy { it }.eachCount()
        
        // Kiểm tra xem tất cả các giá trị có thể có xuất hiện
        val expectedValues = 1..dice.numSides
        return expectedValues.all { it in distribution.keys }
    }
    
    fun testBounds(dice: Dice, iterations: Int = 1000): Boolean {
        val results = (1..iterations).map { dice.roll() }
        return results.all { it in 1..dice.numSides }
    }
}
```

### Xúc xắc Giả cho Kiểm thử

```kotlin
class MockDice(private val predefinedResults: List<Int>) : DiceInterface {
    private var currentIndex = 0
    
    override fun roll(): Int {
        val result = predefinedResults[currentIndex % predefinedResults.size]
        currentIndex++
        return result
    }
    
    override fun getRange(): IntRange = 
        predefinedResults.minOrNull()!!..predefinedResults.maxOrNull()!!
}
```

## Lịch sử Phiên bản

### Phiên bản 1.0.0 (Hiện tại)
- Lớp `Dice` cơ bản với các mặt có thể cấu hình
- Phương thức `roll()` đơn giản trả về số nguyên ngẫu nhiên
- Tích hợp với `MainActivity` để cập nhật giao diện người dùng

### Các Phiên bản Tương lai

#### Phiên bản 1.1.0 (Được Lập kế hoạch)
- Thêm xác thực đầu vào vào constructor Dice
- Triển khai xử lý lỗi đúng cách với các kiểu Result
- Thêm chức năng lịch sử lắc xúc xắc

#### Phiên bản 1.2.0 (Được Lập kế hoạch)
- Hỗ trợ cho các mặt xúc xắc tùy chỉnh (không chỉ số)
- Lắc nhiều xúc xắc trong một hoạt động
- Thống kê và phân tích lắc xúc xắc

#### Phiên bản 2.0.0 (Tương lai)
- Thiết kế lại API hoàn toàn với các giao diện
- Tích hợp hệ thống hoạt ảnh
- Lưu trữ lâu dài cho lịch sử lắc xúc xắc
- Lắc xúc xắc đa người chơi qua mạng

---

*Tài liệu API này được duy trì cùng với cơ sở mã. Vui lòng cập nhật khi thực hiện thay đổi đối với các giao diện công khai.*