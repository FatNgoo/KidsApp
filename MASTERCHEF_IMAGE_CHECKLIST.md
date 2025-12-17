# 📸 MasterChef Game - Danh Sách Ảnh Cần Thêm

## 🎯 Ảnh BẮT BUỘC cần thêm vào `res/drawable/`

### 1. Nguyên Liệu (5 ảnh)
Hiện tại đang dùng drawable tạm, cần thay thế:

```
✅ food_apple.png/xml    - Đã có
✅ food_banana.png/xml   - Đã có
✅ food_cabbage.png/xml  - Đã có
❌ food_tomato.png       - CẦN THÊM 🍅
❌ food_carrot.png       - CẦN THÊM 🥕
❌ food_bread.png        - CẦN THÊM 🍞
❌ food_cheese.png       - CẦN THÊM 🧀
❌ food_egg.png          - CẦN THÊM 🥚
```

**Kích thước đề xuất**: 100x100dp hoặc 200x200px (transparent PNG)

### 2. Món Ăn / Dishes (5 ảnh)
Hiển thị trong order card:

```
❌ dish_fruit_salad.png      - Trái cây trộn 🥗
❌ dish_veggie_mix.png       - Rau củ trộn 🥙
❌ dish_breakfast_special.png - Bữa sáng 🍳
❌ dish_cheese_toast.png     - Bánh mì phô mai 🥪
❌ dish_rainbow_bowl.png     - Bát cầu vồng 🌈
```

**Kích thước đề xuất**: 100x100dp hoặc 200x200px

### 3. Nồi (1 ảnh)
```
❓ ic_pot.png/xml - Kiểm tra xem đã có chưa
```

Nếu chưa có, cần thêm ảnh nồi nấu ăn đẹp.
**Kích thước đề xuất**: 200x200dp

### 4. Khách Hàng (Tùy chọn - nhiều ảnh)
Để thay thế placeholder:

```
❌ customer_1.png - Khách hàng 1 👦
❌ customer_2.png - Khách hàng 2 👧
❌ customer_3.png - Khách hàng 3 👨
❌ customer_4.png - Khách hàng 4 👩
```

**Kích thước đề xuất**: 120x120dp
**Note**: Có thể thêm nhiều hơn để ngẫu nhiên

---

## 🔧 Cách Thêm Ảnh

### Bước 1: Chuẩn bị file ảnh
- Format: PNG với background trong suốt (preferred)
- Hoặc: JPG nếu có background
- Hoặc: Vector XML (tốt nhất cho scaling)

### Bước 2: Copy vào project
```
Copy file vào:
f:\EAPP\KidsApp\app\src\main\res\drawable\

Hoặc density-specific:
drawable-mdpi/    (48x48)
drawable-hdpi/    (72x72)
drawable-xhdpi/   (96x96)
drawable-xxhdpi/  (144x144)
drawable-xxxhdpi/ (192x192)
```

### Bước 3: Cập nhật code

#### Trong `CookingGameActivity.java` (dòng 59-66):

**TÌM:**
```java
new Ingredient("TOMATO", "Tomato", "🍅", R.drawable.food_apple),
new Ingredient("CARROT", "Carrot", "🥕", R.drawable.food_banana),
new Ingredient("BREAD", "Bread", "🍞", R.drawable.food_cabbage),
new Ingredient("CHEESE", "Cheese", "🧀", R.drawable.food_apple),
new Ingredient("EGG", "Egg", "🥚", R.drawable.food_banana)
```

**THAY BẰNG:**
```java
new Ingredient("TOMATO", "Tomato", "🍅", R.drawable.food_tomato),
new Ingredient("CARROT", "Carrot", "🥕", R.drawable.food_carrot),
new Ingredient("BREAD", "Bread", "🍞", R.drawable.food_bread),
new Ingredient("CHEESE", "Cheese", "🧀", R.drawable.food_cheese),
new Ingredient("EGG", "Egg", "🥚", R.drawable.food_egg)
```

#### Trong method `initializeRecipes()` (dòng 134-147):

**TÌM:**
```java
recipes.add(new Recipe("Fruit Salad", "🥗", 
    new String[]{"APPLE", "BANANA"}, R.drawable.food_apple));

recipes.add(new Recipe("Veggie Mix", "🥙", 
    new String[]{"CABBAGE", "CARROT", "TOMATO"}, R.drawable.food_cabbage));

recipes.add(new Recipe("Breakfast Special", "🍳", 
    new String[]{"EGG", "BREAD", "TOMATO"}, R.drawable.food_banana));

recipes.add(new Recipe("Cheese Toast", "🥪", 
    new String[]{"BREAD", "CHEESE"}, R.drawable.food_apple));

recipes.add(new Recipe("Rainbow Bowl", "🌈", 
    new String[]{"APPLE", "BANANA", "CARROT", "CABBAGE"}, R.drawable.food_apple));
```

**THAY BẰNG:**
```java
recipes.add(new Recipe("Fruit Salad", "🥗", 
    new String[]{"APPLE", "BANANA"}, R.drawable.dish_fruit_salad));

recipes.add(new Recipe("Veggie Mix", "🥙", 
    new String[]{"CABBAGE", "CARROT", "TOMATO"}, R.drawable.dish_veggie_mix));

recipes.add(new Recipe("Breakfast Special", "🍳", 
    new String[]{"EGG", "BREAD", "TOMATO"}, R.drawable.dish_breakfast_special));

recipes.add(new Recipe("Cheese Toast", "🥪", 
    new String[]{"BREAD", "CHEESE"}, R.drawable.dish_cheese_toast));

recipes.add(new Recipe("Rainbow Bowl", "🌈", 
    new String[]{"APPLE", "BANANA", "CARROT", "CABBAGE"}, R.drawable.dish_rainbow_bowl));
```

---

## 🎨 Tạo Ảnh Nhanh (Nếu chưa có designer)

### Option 1: Dùng Emoji (Nhanh nhất)
Không cần ảnh thật, dùng emoji to:
```java
// Giữ nguyên code hiện tại, emoji đã được hiển thị
```

### Option 2: Free Resources
- **Flaticon**: https://www.flaticon.com/
- **Freepik**: https://www.freepik.com/
- **Pixabay**: https://pixabay.com/
- Keywords: "food icon", "ingredient", "cooking"

### Option 3: Generate với AI
- **DALL-E**: Generate food images
- **Midjourney**: Create cute food illustrations
- **Canva**: Design simple food icons

### Option 4: Android Vector Drawables
Tạo file XML đơn giản:

**Ví dụ: `food_tomato.xml`**
```xml
<?xml version="1.0" encoding="utf-8"?>
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="64dp"
    android:height="64dp"
    android:viewportWidth="64"
    android:viewportHeight="64">
    <path
        android:fillColor="#FF6B6B"
        android:pathData="M32,12C20,12 10,22 10,34C10,46 20,56 32,56C44,56 54,46 54,34C54,22 44,12 32,12Z"/>
    <path
        android:fillColor="#4CAF50"
        android:pathData="M32,8L28,12L36,12Z"/>
</vector>
```

---

## ✅ Checklist Trước Khi Chạy

### Minimum (Game có thể chạy):
- [ ] Thêm 5 ảnh nguyên liệu (tomato, carrot, bread, cheese, egg)
- [ ] Cập nhật code `ALL_INGREDIENTS`
- [ ] Thêm 5 ảnh món ăn
- [ ] Cập nhật code `initializeRecipes()`
- [ ] Sync Gradle
- [ ] Build & Run

### Optional (Làm đẹp hơn):
- [ ] Thêm ảnh nồi đẹp (`ic_pot.png`)
- [ ] Thêm ảnh khách hàng (`customer_*.png`)
- [ ] Thêm code random khách hàng
- [ ] Test TTS (Text-to-Speech)
- [ ] Thêm âm thanh hiệu ứng

---

## 📝 Template File Names

Copy checklist này:
```
Nguyên liệu:
□ food_tomato.png
□ food_carrot.png  
□ food_bread.png
□ food_cheese.png
□ food_egg.png

Món ăn:
□ dish_fruit_salad.png
□ dish_veggie_mix.png
□ dish_breakfast_special.png
□ dish_cheese_toast.png
□ dish_rainbow_bowl.png

Khác:
□ ic_pot.png (nếu chưa có)
□ customer_1.png (optional)
□ customer_2.png (optional)
□ customer_3.png (optional)
```

---

## 🚨 Lưu Ý Quan Trọng

1. **Tên file**: 
   - Chỉ dùng `lowercase` và `underscore`
   - KHÔNG dùng space, dash, hoặc ký tự đặc biệt
   - ✅ Đúng: `food_tomato.png`
   - ❌ Sai: `Food-Tomato.png`, `food tomato.png`

2. **Kích thước**:
   - PNG: Nên >= 100x100px
   - Vector XML: Không quan trọng (scale tốt)

3. **Transparency**:
   - Nên dùng PNG với background trong suốt
   - JPG sẽ có background trắng

4. **Rebuild**:
   - Sau khi thêm ảnh: **Build > Clean Project**
   - Rồi: **Build > Rebuild Project**
   - Đảm bảo R.drawable.xxx tự động generate

---

**Nếu gặp lỗi "cannot resolve symbol", hãy:**
1. Sync Gradle
2. Invalidate Caches & Restart (File menu)
3. Kiểm tra tên file có đúng không

Chúc may mắn! 🎨✨
