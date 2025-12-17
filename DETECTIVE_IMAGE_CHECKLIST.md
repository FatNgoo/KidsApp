# 📸 Detective Game - Danh Sách Ảnh Cần Thêm

## 🎯 Ảnh KHUYẾN KHÍCH thêm vào `res/drawable/`

### 1. Vật Thể Thám Tử (5 ảnh)

Hiện tại 3 vật thể đã có, 5 vật thể dùng placeholder:

```
✅ clue_key.png          - Đã có 🔑
✅ clue_magnifier.png    - Đã có 🔍
✅ clue_footprint.png    - Đã có 👣
❌ clue_watch.png        - CẦN THÊM ⌚
❌ clue_coin.png         - CẦN THÊM 🪙
❌ clue_book.png         - CẦN THÊM 📖
❌ clue_hat.png          - CẦN THÊM 🎩
❌ clue_phone.png        - CẦN THÊM 📱
```

**Kích thước đề xuất**: 50-80dp (vuông), transparent PNG
**Style**: Phù hợp với theme thám tử/mystery

### 2. Scene Background (1 ảnh)

```
❓ bg_crime_scene.png/jpg - Kiểm tra xem đã có chưa
```

Nếu chưa có, thêm ảnh scene thám tử:
- Crime scene (hiện trường)
- Detective office (văn phòng thám tử)
- Mystery room (phòng bí ẩn)
- Library (thư viện)
- Museum (bảo tàng)

**Kích thước đề xuất**: 
- Minimum: 800x600px
- Recommended: 1920x1080px (landscape)
- Format: JPG hoặc PNG

---

## 🔧 Cách Thêm Ảnh

### Bước 1: Chuẩn bị file ảnh

**Cho vật thể:**
- Format: PNG với background trong suốt (preferred)
- Size: 50-100dp hoặc 100-200px
- Style: Icon hoặc simple illustration

**Cho scene:**
- Format: JPG (file nhỏ hơn) hoặc PNG
- Size: Landscape, >= 800x600px
- Quality: Medium-High

### Bước 2: Copy vào project

```
Copy file vào:
f:\EAPP\KidsApp\app\src\main\res\drawable\

Hoặc density-specific:
drawable-mdpi/    (1x)
drawable-hdpi/    (1.5x)
drawable-xhdpi/   (2x)
drawable-xxhdpi/  (3x)
drawable-xxxhdpi/ (4x)
```

### Bước 3: Cập nhật code

#### Trong `DetectiveGameActivity.java` (dòng 53-60):

**TÌM:**
```java
new ObjectData("WATCH", "Watch", "⌚", R.drawable.ic_clue_placeholder),
new ObjectData("COIN", "Coin", "🪙", R.drawable.ic_clue_placeholder),
new ObjectData("BOOK", "Book", "📖", R.drawable.ic_clue_placeholder),
new ObjectData("HAT", "Hat", "🎩", R.drawable.ic_clue_placeholder),
new ObjectData("PHONE", "Phone", "📱", R.drawable.ic_clue_placeholder)
```

**THAY BẰNG:**
```java
new ObjectData("WATCH", "Watch", "⌚", R.drawable.clue_watch),
new ObjectData("COIN", "Coin", "🪙", R.drawable.clue_coin),
new ObjectData("BOOK", "Book", "📖", R.drawable.clue_book),
new ObjectData("HAT", "Hat", "🎩", R.drawable.clue_hat),
new ObjectData("PHONE", "Phone", "📱", R.drawable.clue_phone)
```

#### Trong `activity_detective_game.xml` (dòng 97):

Nếu thêm scene mới:
```xml
<ImageView
    android:id="@+id/imgScene"
    ...
    android:src="@drawable/scene_detective_office" />
```

---

## 🎨 Tạo Ảnh Nhanh (Nếu chưa có designer)

### Option 1: Dùng Icon Có Sẵn
Tải free icons từ:
- **Material Icons**: https://fonts.google.com/icons
- **Flaticon**: https://www.flaticon.com/
- **Icons8**: https://icons8.com/
- Keywords: "detective", "clue", "mystery", "watch", "book", etc.

### Option 2: Free Scene Images
- **Unsplash**: https://unsplash.com/
- **Pexels**: https://www.pexels.com/
- **Pixabay**: https://pixabay.com/
- Keywords: "detective office", "crime scene", "mystery room"

### Option 3: AI Generation
- **DALL-E**: Generate detective scene
- **Midjourney**: Create mystery illustrations
- **Stable Diffusion**: Detective theme images

### Option 4: Dùng Emoji (Tạm thời)
Không cần ảnh thật ngay, emoji đã hiển thị:
```java
// Giữ nguyên placeholder, emoji ⌚🪙📖🎩📱 đã được hiển thị
```

### Option 5: Tạo Simple Icons với XML

**Ví dụ: `clue_watch.xml`**
```xml
<?xml version="1.0" encoding="utf-8"?>
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="64dp"
    android:height="64dp"
    android:viewportWidth="24"
    android:viewportHeight="24">
    <path
        android:fillColor="@color/detective_gold"
        android:pathData="M12,2C6.5,2 2,6.5 2,12s4.5,10 10,10s10,-4.5 10,-10S17.5,2 12,2zM12,20c-4.41,0 -8,-3.59 -8,-8s3.59,-8 8,-8s8,3.59 8,8S16.41,20 12,20zM12.5,7H11v6l5.25,3.15l0.75,-1.23l-4.5,-2.67z"/>
</vector>
```

---

## ✅ Checklist Trước Khi Chạy

### Minimum (Game có thể chạy NGAY):
- [x] Code đã hoàn chỉnh
- [x] Layout đã có
- [x] Placeholder icons có sẵn
- [ ] (Optional) Thêm 5 ảnh vật thể thật
- [ ] (Optional) Thêm scene background đẹp

### Khuyến khích (Làm đẹp hơn):
- [ ] Thêm 5 ảnh vật thể chất lượng cao
- [ ] Thêm scene background phù hợp
- [ ] Thêm nhiều vật thể khác (mở rộng database)
- [ ] Thêm nhiều scenes (multi-level)
- [ ] Test TTS (Text-to-Speech)

---

## 📝 Template File Names

Copy checklist này:

```
Vật thể ưu tiên:
□ clue_watch.png
□ clue_coin.png
□ clue_book.png
□ clue_hat.png
□ clue_phone.png

Scene (chọn 1 hoặc nhiều):
□ scene_crime_scene.jpg
□ scene_detective_office.jpg
□ scene_mystery_room.jpg
□ scene_library.jpg
□ scene_museum.jpg

Vật thể mở rộng (optional):
□ clue_glasses.png
□ clue_diamond.png
□ clue_envelope.png
□ clue_knife.png
□ clue_fingerprint.png
```

---

## 🚨 Lưu Ý Quan Trọng

### 1. Tên File
- Chỉ dùng `lowercase` và `underscore`
- KHÔNG dùng space, dash, uppercase
- ✅ Đúng: `clue_watch.png`, `scene_office.jpg`
- ❌ Sai: `Clue-Watch.png`, `scene office.jpg`

### 2. Kích Thước
**Vật thể:**
- Vuông: 50x50, 64x64, 100x100, 128x128
- Transparent background (PNG)

**Scene:**
- Landscape: 800x600, 1024x768, 1920x1080
- Background có thể opaque (JPG OK)

### 3. File Size
- Vật thể PNG: < 50KB
- Scene JPG: < 500KB
- Optimize để giảm size app

### 4. Rebuild
Sau khi thêm ảnh:
1. **Build > Clean Project**
2. **Build > Rebuild Project**
3. Đảm bảo `R.drawable.xxx` tự động generate
4. Nếu lỗi: **File > Invalidate Caches & Restart**

---

## 🎮 Game Có Thể Chạy Ngay!

**LƯU Ý**: Game đã có thể chạy với placeholder icons hiện tại!
- 3 vật thể có ảnh thật: Key, Magnifier, Footprint
- 5 vật thể dùng placeholder: Watch, Coin, Book, Hat, Phone
- Scene có thể dùng bg_crime_scene hiện có

Việc thêm ảnh mới chỉ để làm đẹp hơn, không ảnh hưởng gameplay!

---

## 🎨 Gợi Ý Style Ảnh

### Cho Vật Thể:
- **Flat design**: Simple, 2D, colorful
- **Line art**: Outline only
- **Realistic**: 3D rendered
- **Cartoon**: Fun, kid-friendly

### Cho Scene:
- **Crime scene**: Dark, mysterious
- **Detective office**: Vintage, classy
- **Cozy room**: Bright, colorful
- **Cartoon background**: Fun, animated

---

## 📦 Download Quick Pack

Nếu cần nhanh, tải pack có sẵn:
- **Game Asset Stores**: Unity Asset Store, itch.io
- **Free Icon Packs**: Material Design Icons
- **Stock Photos**: Free detective/mystery photos

---

**Game sẵn sàng chạy! Thêm ảnh để nâng cấp trải nghiệm!** 🎉🔍

Để chạy ngay:
```bash
# Build project
./gradlew build

# Run on device
./gradlew installDebug
```

Chúc may mắn! 🎨✨
