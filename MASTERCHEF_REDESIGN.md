# 🍳 MasterChef Game - Complete Redesign

## 📋 Tổng Quan

Game MasterChef đã được thiết kế lại hoàn toàn với giao diện đẹp mắt, nhiều màu sắc, và hệ thống nấu ăn với công thức phức tạp hơn.

## 🎨 Tính Năng Mới

### 1. **Giao Diện Game**

#### Hàng Trên (Top Row):
- **Bên trái**: Card màu hồng với ảnh khách hàng (placeholder - bạn có thể thay thế)
- **Bên phải**: Card màu vàng hiển thị:
  - Tên món ăn
  - Hình ảnh món ăn
  - **Nút loa 🔊**: Nhấn để đọc to tên món ăn (Text-to-Speech)
- **Góc phải**: Điểm số với biểu tượng sao ⭐

#### Hàng Giữa (Middle Row):
- **Bên trái**: Khu vực nồi (pot) với khung màu nâu gỗ
  - Nơi kéo thả nguyên liệu
  - Text "🍳 Drop Here" ở trên
  - Hiệu ứng phóng to khi kéo vào
- **Bên phải**: Bảng checklist nguyên liệu
  - Hiển thị công thức món ăn
  - Mỗi nguyên liệu có emoji và tên
  - Tích xanh ✓ xuất hiện khi thả đúng nguyên liệu

#### Hàng Dưới (Bottom Row):
- **Khu vực nguyên liệu** với nền màu xanh lá
- GridLayout 4 cột chứa các nguyên liệu
- Mỗi nguyên liệu có:
  - Hình ảnh
  - Tên hiển thị
  - Khung trắng bo tròn
  - Có thể kéo thả

### 2. **Hệ Thống Game**

#### Công Thức (Recipes):
- **Fruit Salad** 🥗: Apple + Banana
- **Veggie Mix** 🥙: Cabbage + Carrot + Tomato
- **Breakfast Special** 🍳: Egg + Bread + Tomato
- **Cheese Toast** 🥪: Bread + Cheese
- **Rainbow Bowl** 🌈: Apple + Banana + Carrot + Cabbage

#### Nguyên Liệu (Ingredients):
8 nguyên liệu với emoji:
- 🍎 Apple
- 🍌 Banana
- 🥬 Cabbage
- 🍅 Tomato
- 🥕 Carrot
- 🍞 Bread
- 🧀 Cheese
- 🥚 Egg

#### Gameplay:
1. Khách hàng order một món ăn ngẫu nhiên
2. Bảng checklist hiển thị nguyên liệu cần thiết
3. Người chơi kéo thả nguyên liệu từ grid dưới vào nồi
4. Khi thả đúng: xuất hiện tích xanh, hiệu ứng nồi nhảy
5. Khi thả sai: nồi lắc, hiển thị lỗi
6. Hoàn thành công thức: điểm +1, nồi xoay 360°, load món mới

### 3. **Màu Sắc Kitchen Theme**

Đã thêm bộ màu kitchen theme chuyên dụng:
- `kitchen_red` (#FF6B6B) - Đỏ cà chua
- `kitchen_yellow` (#FFD93D) - Vàng nắng
- `kitchen_orange` (#FF9F40) - Cam tươi
- `kitchen_green` (#6BCF7F) - Xanh lá
- `kitchen_blue` (#4ECDC4) - Xanh mint
- `kitchen_purple` (#C77DFF) - Tím lavender
- `kitchen_pink` (#FF85B3) - Hồng ngọt
- `kitchen_cream` (#FFF5E4) - Kem nền
- `kitchen_brown` (#D4A574) - Nâu gỗ

### 4. **Hiệu Ứng & Animation**

- ✅ **Drag & Drop**: Shadow builder khi kéo
- ✅ **Pot Scale**: Phóng to khi hover, thu nhỏ khi thả
- ✅ **Cooking Effect**: Nồi nhảy khi thả đúng
- ✅ **Shake Effect**: Nồi lắc khi thả sai
- ✅ **Checkmark**: Tích xanh xuất hiện với animation
- ✅ **Victory**: Nồi xoay 360° + phóng to khi hoàn thành
- ✅ **Speaker Bounce**: Nút loa co dãn khi nhấn

### 5. **Text-to-Speech**

- Tự động khởi tạo TTS engine
- Ngôn ngữ: English (US)
- Nhấn nút 🔊 để nghe tên món ăn
- Tự động giải phóng khi đóng activity

## 📂 Các File Đã Tạo/Sửa

### Layout Files:
1. ✅ `activity_cooking_game.xml` - Layout chính
2. ✅ `item_ingredient.xml` - Item nguyên liệu trong grid
3. ✅ `item_checklist.xml` - Item trong checklist

### Drawable Resources:
1. ✅ `bg_customer_card.xml` - Background card khách hàng (hồng)
2. ✅ `bg_order_card.xml` - Background card order (vàng)
3. ✅ `bg_checklist_card.xml` - Background checklist (trắng viền xanh)
4. ✅ `bg_ingredient_item.xml` - Background item nguyên liệu (trắng viền cam)
5. ✅ `bg_pot_area.xml` - Background khu vực nồi (kem viền nâu)
6. ✅ `ic_checkmark.xml` - Icon tích xanh
7. ✅ `ic_speaker.xml` - Icon loa
8. ✅ `ic_customer_placeholder.xml` - Icon khách hàng mặc định

### Java Files:
1. ✅ `CookingGameActivity.java` - Logic game hoàn toàn mới

### Values:
1. ✅ `colors.xml` - Thêm 11 màu kitchen theme

## 🎯 Điều Bạn Cần Làm

### 1. Thêm Ảnh Nguyên Liệu Thực

Hiện tại code dùng drawable tạm:
```java
// Trong CookingGameActivity.java, dòng 59-66
new Ingredient("TOMATO", "Tomato", "🍅", R.drawable.food_apple), // Thay đổi
new Ingredient("CARROT", "Carrot", "🥕", R.drawable.food_banana), // Thay đổi
new Ingredient("BREAD", "Bread", "🍞", R.drawable.food_cabbage), // Thay đổi
new Ingredient("CHEESE", "Cheese", "🧀", R.drawable.food_apple), // Thay đổi
new Ingredient("EGG", "Egg", "🥚", R.drawable.food_banana) // Thay đổi
```

**Cần thêm vào `res/drawable/`:**
- `food_tomato.png` hoặc `.xml`
- `food_carrot.png` hoặc `.xml`
- `food_bread.png` hoặc `.xml`
- `food_cheese.png` hoặc `.xml`
- `food_egg.png` hoặc `.xml`

Sau đó cập nhật trong code:
```java
new Ingredient("TOMATO", "Tomato", "🍅", R.drawable.food_tomato),
new Ingredient("CARROT", "Carrot", "🥕", R.drawable.food_carrot),
new Ingredient("BREAD", "Bread", "🍞", R.drawable.food_bread),
new Ingredient("CHEESE", "Cheese", "🧀", R.drawable.food_cheese),
new Ingredient("EGG", "Egg", "🥚", R.drawable.food_egg)
```

### 2. Thêm Ảnh Khách Hàng

Để thay ảnh placeholder:
1. Thêm file ảnh vào `res/drawable/` (ví dụ: `customer_1.png`, `customer_2.png`)
2. Trong `CookingGameActivity.java`, thêm code để đổi ảnh:
```java
// Trong method loadNewRecipe(), thêm:
int[] customerImages = {
    R.drawable.customer_1,
    R.drawable.customer_2,
    R.drawable.customer_3
};
imgCustomer.setImageResource(customerImages[random.nextInt(customerImages.length)]);
```

### 3. Thêm Ảnh Nồi

Hiện tại dùng `@drawable/ic_pot`. Bạn có thể:
1. Giữ nguyên nếu đã có
2. Hoặc thêm ảnh nồi đẹp hơn vào `res/drawable/pot_cooking.png`
3. Cập nhật trong layout:
```xml
android:src="@drawable/pot_cooking"
```

### 4. Thêm Ảnh Món Ăn

Trong `initializeRecipes()`, mỗi món cần ảnh riêng:
```java
recipes.add(new Recipe("Fruit Salad", "🥗", 
    new String[]{"APPLE", "BANANA"}, R.drawable.dish_fruit_salad));

recipes.add(new Recipe("Veggie Mix", "🥙", 
    new String[]{"CABBAGE", "CARROT", "TOMATO"}, R.drawable.dish_veggie_mix));
// ...
```

Thêm file vào `res/drawable/`:
- `dish_fruit_salad.png`
- `dish_veggie_mix.png`
- `dish_breakfast_special.png`
- `dish_cheese_toast.png`
- `dish_rainbow_bowl.png`

## 🎨 Tùy Chỉnh Thêm

### Thêm Màu Sắc Mới
Vào `colors.xml` thêm:
```xml
<color name="kitchen_custom">#YOUR_HEX</color>
```

### Thêm Công Thức Mới
Trong `initializeRecipes()`:
```java
recipes.add(new Recipe("Tên Món", "🍽️", 
    new String[]{"INGREDIENT1", "INGREDIENT2"}, R.drawable.dish_image));
```

### Thêm Nguyên Liệu Mới
Trong `ALL_INGREDIENTS`:
```java
new Ingredient("ID", "Tên Hiển Thị", "emoji", R.drawable.food_image)
```

### Thay Đổi Số Cột Grid
Trong `activity_cooking_game.xml`:
```xml
<GridLayout
    ...
    android:columnCount="5"> <!-- Đổi từ 4 sang 5 -->
```

## 🚀 Build & Run

1. Sync Gradle
2. Build project
3. Run trên emulator/device
4. Tận hưởng game MasterChef mới!

## 💡 Gợi Ý Cải Tiến Thêm

1. **Âm thanh**: Thêm hiệu ứng âm thanh khi thả nguyên liệu
2. **Level**: Tăng độ khó theo điểm (nhiều nguyên liệu hơn)
3. **Timer**: Thêm đồng hồ đếm ngược
4. **Combo**: Thưởng điểm x2 khi làm nhanh
5. **Animation**: Hiệu ứng lửa bếp, bong bóng nước
6. **Power-ups**: Item đặc biệt như "hint" hoặc "skip"
7. **Leaderboard**: Bảng xếp hạng điểm cao
8. **Đa ngôn ngữ**: Tiếng Việt cho TTS

## ✨ Điểm Nổi Bật

✅ Giao diện đẹp, nhiều màu sắc, phù hợp trẻ em
✅ Không dùng gradient (theo yêu cầu)
✅ Hệ thống công thức đa dạng
✅ Checklist trực quan với tích xanh
✅ Text-to-Speech hỗ trợ học từ vựng
✅ Animation mượt mà, hấp dẫn
✅ Code có cấu trúc, dễ mở rộng
✅ Grid 4 cột với tên nguyên liệu
✅ Drag & Drop tự nhiên

Chúc bạn thành công! 🎉
