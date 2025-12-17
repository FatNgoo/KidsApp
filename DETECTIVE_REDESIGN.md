# 🔎 Detective Game - Complete Redesign

## 📋 Tổng Quan

Game Detective đã được thiết kế lại hoàn toàn với giao diện đẹp mắt, nhiều màu sắc theo chủ đề thám tử/mystery, và gameplay cải tiến với hệ thống hints và danh sách vật thể.

## 🎨 Tính Năng Mới

### 1. **Giao Diện Game**

#### Hàng 1 - FIND Card (Trên cùng):
- **Card màu xanh dương** với viền vàng nổi bật
- Hiển thị: "FIND:" + Ảnh vật thể + Tên vật thể
- **Nút loa 🔊**: Nhấn để nghe tên vật thể (Text-to-Speech)
- Animation khi load vật thể mới

#### Hàng 2 - Scene Image (Giữa):
- **Khung ảnh lớn** chứa cảnh có vật thể ẩn
- Background: Crime scene hoặc bất kỳ scene nào
- Vật thể được đặt ngẫu nhiên trong scene
- Nhấn vào vật thể để tìm thấy
- Hiệu ứng khi tìm đúng: Phóng to và biến mất

#### Hàng 3 - Hint Buttons (Nút hỗ trợ):
**3 nút hỗ trợ màu sắc khác nhau:**

1. **Hint Button** (Cam) 💡
   - Số lượng: 3 hints
   - Chức năng: Làm nổi bật vật thể cần tìm (nhấp nháy)
   - Icon: Bóng đèn

2. **Magnifier Button** (Xanh mint) 🔍
   - Số lượng: 2 magnifiers
   - Chức năng: Phóng to vật thể cần tìm (pulse effect)
   - Icon: Kính lúp

3. **Skip Button** (Đỏ) ⏭️
   - Chức năng: Bỏ qua vật thể hiện tại
   - Hiển thị dialog xác nhận
   - Icon: Skip forward

#### Hàng 4 - Objects List (Danh sách):
- **Card màu tím** với viền trắng
- Tiêu đề: "🔎 Objects to Find"
- **Progress tracker**: "X/5" (số vật thể đã tìm/tổng số)
- **Horizontal scroll** chứa danh sách vật thể
- Mỗi item có:
  - Hình ảnh vật thể
  - Tên vật thể
  - Tích xanh ✓ khi tìm thấy
  - Background đổi màu xanh khi hoàn thành

### 2. **Hệ Thống Game**

#### Database Vật Thể:
8 vật thể có sẵn (có thể mở rộng):
- 🔑 Key (Chìa khóa)
- 🔍 Magnifier (Kính lúp)
- 👣 Footprint (Dấu chân)
- ⌚ Watch (Đồng hồ)
- 🪙 Coin (Đồng xu)
- 📖 Book (Sách)
- 🎩 Hat (Mũ)
- 📱 Phone (Điện thoại)

#### Gameplay:
1. Game chọn ngẫu nhiên 5 vật thể từ database
2. Đặt ngẫu nhiên vật thể trong scene
3. Hiển thị vật thể đầu tiên trong FIND card
4. Người chơi:
   - Nhấn vào vật thể trong scene để tìm
   - Dùng hints nếu cần
   - Bỏ qua nếu không tìm được
5. Khi tìm đúng:
   - Vật thể biến mất với animation
   - Tích xanh xuất hiện trong list
   - Load vật thể tiếp theo
6. Hoàn thành: Dialog hiển thị số vật thể đã tìm

#### Tính Năng Đặc Biệt:
- ✅ **Random placement**: Vật thể ở vị trí khác nhau mỗi lần chơi
- ✅ **Text-to-Speech**: Đọc tên vật thể bằng tiếng Anh
- ✅ **Hint system**: 3 loại hỗ trợ khác nhau
- ✅ **Progress tracking**: Theo dõi tiến độ real-time
- ✅ **Animation**: Nhiều hiệu ứng đẹp mắt
- ✅ **Visual feedback**: Màu sắc và animation cho mọi hành động

### 3. **Màu Sắc Detective Theme**

Bộ màu mystery/detective chuyên dụng (12 màu):
- `detective_navy` (#2C3E50) - Xanh navy đậm
- `detective_gold` (#F39C12) - Vàng huy hiệu thám tử
- `detective_red` (#E74C3C) - Đỏ cảnh báo
- `detective_purple` (#9B59B6) - Tím bí ẩn
- `detective_teal` (#1ABC9C) - Xanh mint
- `detective_orange` (#E67E22) - Cam cảnh báo
- `detective_blue` (#3498DB) - Xanh điều tra
- `detective_green` (#27AE60) - Xanh tìm thấy
- `detective_gray` (#95A5A6) - Xám trung tính
- `detective_light` (#ECF0F1) - Nền sáng
- `detective_dark` (#34495E) - Text tối
- `detective_yellow` (#F1C40F) - Vàng highlight

### 4. **Hiệu Ứng & Animation**

- ✅ **Object Found**: Scale up + Fade out
- ✅ **Wrong Click**: Shake animation
- ✅ **Hint Effect**: Alpha blinking (nhấp nháy)
- ✅ **Magnifier Effect**: Pulse scale (co dãn)
- ✅ **Card Change**: Scale bounce khi load vật thể mới
- ✅ **Checkmark**: Scale animation khi xuất hiện
- ✅ **Speaker Bounce**: Button co dãn khi nhấn
- ✅ **List Update**: Background color transition

### 5. **Text-to-Speech**

- Tự động khởi tạo TTS engine
- Ngôn ngữ: English (US)
- Nhấn nút 🔊 để nghe tên vật thể
- Giúp trẻ học từ vựng tiếng Anh

## 📂 Các File Đã Tạo/Sửa

### Layout Files:
1. ✅ `activity_detective_game.xml` - Layout chính (4 hàng)
2. ✅ `item_detective_object.xml` - Item trong danh sách vật thể

### Drawable Resources (9 files):
1. ✅ `bg_find_card.xml` - Background card FIND (xanh viền vàng)
2. ✅ `bg_scene_container.xml` - Background khung scene
3. ✅ `bg_hint_button.xml` - Background nút hint (cam)
4. ✅ `bg_object_item.xml` - Background item vật thể (trắng viền xám)
5. ✅ `bg_object_item_found.xml` - Background khi tìm thấy (xanh)
6. ✅ `ic_hint.xml` - Icon bóng đèn hint
7. ✅ `ic_magnifier.xml` - Icon kính lúp
8. ✅ `ic_skip.xml` - Icon skip
9. ✅ `ic_clue_placeholder.xml` - Icon placeholder cho vật thể

### Java Files:
1. ✅ `DetectiveGameActivity.java` - Logic game hoàn toàn mới (450+ dòng)

### Values:
1. ✅ `colors.xml` - Thêm 12 màu detective theme

## 🎯 Điều Bạn Cần Làm

### 1. Thêm Ảnh Vật Thể

Hiện tại một số vật thể dùng placeholder. Cần thêm ảnh cho:

**Trong `DetectiveGameActivity.java`, dòng 53-60:**
```java
new ObjectData("WATCH", "Watch", "⌚", R.drawable.ic_clue_placeholder),  // Thay đổi
new ObjectData("COIN", "Coin", "🪙", R.drawable.ic_clue_placeholder),    // Thay đổi
new ObjectData("BOOK", "Book", "📖", R.drawable.ic_clue_placeholder),    // Thay đổi
new ObjectData("HAT", "Hat", "🎩", R.drawable.ic_clue_placeholder),      // Thay đổi
new ObjectData("PHONE", "Phone", "📱", R.drawable.ic_clue_placeholder)   // Thay đổi
```

**Thêm vào `res/drawable/`:**
- `clue_watch.png` - Đồng hồ ⌚
- `clue_coin.png` - Đồng xu 🪙
- `clue_book.png` - Sách 📖
- `clue_hat.png` - Mũ 🎩
- `clue_phone.png` - Điện thoại 📱

**Sau đó cập nhật code:**
```java
new ObjectData("WATCH", "Watch", "⌚", R.drawable.clue_watch),
new ObjectData("COIN", "Coin", "🪙", R.drawable.clue_coin),
new ObjectData("BOOK", "Book", "📖", R.drawable.clue_book),
new ObjectData("HAT", "Hat", "🎩", R.drawable.clue_hat),
new ObjectData("PHONE", "Phone", "📱", R.drawable.clue_phone)
```

### 2. Thêm Scene Background

Hiện tại dùng `@drawable/bg_crime_scene`. Bạn có thể:
1. Giữ nguyên nếu đã có
2. Hoặc thêm scene mới vào `res/drawable/`
3. Cập nhật trong layout:
```xml
<ImageView
    android:id="@+id/imgScene"
    ...
    android:src="@drawable/your_scene_image" />
```

### 3. Thêm Nhiều Vật Thể (Tùy chọn)

Mở rộng database trong `OBJECT_DATABASE`:
```java
new ObjectData("GLASSES", "Glasses", "👓", R.drawable.clue_glasses),
new ObjectData("DIAMOND", "Diamond", "💎", R.drawable.clue_diamond),
new ObjectData("ENVELOPE", "Envelope", "✉️", R.drawable.clue_envelope)
```

### 4. Tùy Chỉnh Số Lượng

Trong `startNewGame()`, dòng 149:
```java
for (int i = 0; i < Math.min(5, shuffled.size()); i++) {
    // Đổi 5 thành số khác để thay đổi số vật thể mỗi game
}
```

## 🎨 Tùy Chỉnh Thêm

### Thêm Màu Sắc
Vào `colors.xml`:
```xml
<color name="detective_custom">#YOUR_HEX</color>
```

### Điều Chỉnh Hints
Trong `startNewGame()`:
```java
hintCount = 5;      // Tăng số hints
magnifierCount = 3; // Tăng số magnifiers
```

### Thay Đổi Kích Thước Vật Thể
Trong `placeObjectsInScene()`, dòng 161:
```java
int size = 50 + random.nextInt(30); // Đổi 50-80 thành 40-60 (nhỏ hơn)
```

### Thêm Levels/Độ Khó
Tạo nhiều scenes khác nhau:
```java
private int currentLevel = 1;
private String[] scenes = {
    "@drawable/scene_1",
    "@drawable/scene_2",
    "@drawable/scene_3"
};
```

## 🚀 Build & Run

1. Sync Gradle
2. Build project
3. Run trên emulator/device
4. Tận hưởng game Detective mới!

## 💡 Gợi Ý Cải Tiến Thêm

1. **Timer**: Thêm đồng hồ đếm ngược cho challenge
2. **Score System**: Tính điểm dựa trên thời gian và số hints dùng
3. **Levels**: Nhiều scenes khác nhau với độ khó tăng dần
4. **Sound Effects**: Âm thanh khi tìm thấy, hint, wrong click
5. **Multiplayer**: Chơi đối kháng với bạn bè
6. **Daily Challenges**: Thử thách mới mỗi ngày
7. **Achievements**: Huy hiệu cho milestone
8. **Leaderboard**: Bảng xếp hạng online
9. **Zoom**: Cho phép zoom scene để tìm dễ hơn
10. **Combo**: Thưởng điểm khi tìm nhanh liên tiếp

## ✨ Điểm Nổi Bật

✅ Giao diện đẹp, nhiều màu sắc mystery theme
✅ Không dùng gradient (theo yêu cầu)
✅ 4 hàng rõ ràng: FIND card, Scene, Hints, List
✅ 3 loại hỗ trợ: Hint, Magnifier, Skip
✅ Text-to-Speech học từ vựng
✅ Random placement mỗi lần chơi
✅ Animation mượt mà, hấp dẫn
✅ Visual feedback cho mọi hành động
✅ Progress tracking real-time
✅ Code có cấu trúc tốt, dễ mở rộng
✅ Scalable: Dễ thêm vật thể và scenes

## 📊 So Sánh Với Version Cũ

| Tính Năng | Version Cũ | Version Mới |
|-----------|------------|-------------|
| Số vật thể | 3 cố định | 5 random từ 8 |
| Vị trí vật thể | Cố định | Random mỗi lần |
| Giao diện | 1 màn đen | 4 hàng colorful |
| Hints | Không có | 3 loại hỗ trợ |
| TTS | Không có | Có ✓ |
| Danh sách | Không có | Có scroll list |
| Progress | Text đơn giản | Card với số/tổng |
| Animation | Cơ bản | Nhiều hiệu ứng |

Chúc bạn thành công! 🎉🔍
