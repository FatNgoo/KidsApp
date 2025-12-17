# 🎨 MasterChef Game - Hướng Dẫn Màu Sắc & Layout

## 📐 Layout Structure

```
┌─────────────────────────────────────────────────────────┐
│ TOP ROW                                                 │
│ ┌──────────┐  ┌────────────────────────┐  ⭐ Score    │
│ │          │  │  Fruit Salad           │              │
│ │ 👤 Khách │  │  🥗  🔊               │              │
│ │   Hàng   │  │  (Order Display)       │              │
│ └──────────┘  └────────────────────────┘              │
│      PINK              YELLOW                          │
├─────────────────────────────────────────────────────────┤
│ MIDDLE ROW                                              │
│ ┌────────────────────────┐  ┌──────────┐              │
│ │                        │  │ Recipe   │              │
│ │    🍳 Drop Here       │  ├──────────┤              │
│ │                        │  │ ✓ Apple  │              │
│ │       🍲 Nồi          │  │ ☐ Banana │              │
│ │                        │  │          │              │
│ └────────────────────────┘  └──────────┘              │
│      CREAM/BROWN               WHITE/BLUE              │
├─────────────────────────────────────────────────────────┤
│ BOTTOM ROW                                              │
│ ┌───────────────────────────────────────────────────┐  │
│ │        🥘 Ingredients                            │  │
│ │  ┌────┐ ┌────┐ ┌────┐ ┌────┐                  │  │
│ │  │ 🍎 │ │ 🍌 │ │ 🥬 │ │ 🍅 │                  │  │
│ │  │Apple│ │Bana│ │Cabb│ │Toma│                  │  │
│ │  └────┘ └────┘ └────┘ └────┘                  │  │
│ │  ┌────┐ ┌────┐ ┌────┐ ┌────┐                  │  │
│ │  │ 🥕 │ │ 🍞 │ │ 🧀 │ │ 🥚 │                  │  │
│ │  │Carr│ │Brea│ │Chee│ │ Egg│                  │  │
│ │  └────┘ └────┘ └────┘ └────┘                  │  │
│ └───────────────────────────────────────────────────┘  │
│                    GREEN                                │
└─────────────────────────────────────────────────────────┘
```

## 🎨 Color Palette

### Card Backgrounds:
```
┌─────────────┬──────────────┬─────────────────┐
│   Card      │    Color     │   Hex Code      │
├─────────────┼──────────────┼─────────────────┤
│ Customer    │ 🌸 PINK      │ #FF85B3         │
│ Order       │ 🌟 YELLOW    │ #FFD93D         │
│ Pot Area    │ 🏜️ CREAM     │ #FFF5E4         │
│ Border      │ 🪵 BROWN     │ #D4A574         │
│ Checklist   │ ⚪ WHITE     │ #FFFFFF         │
│ Blue Border │ 💙 BLUE      │ #4ECDC4         │
│ Ingredients │ 🌿 GREEN     │ #6BCF7F         │
│ Background  │ 🍦 CREAM     │ #FFF5E4         │
└─────────────┴──────────────┴─────────────────┘
```

### Accent Colors:
```
Red     🔴 #FF6B6B  (Tomato)
Orange  🟠 #FF9F40  (Vibrant)
Purple  🟣 #C77DFF  (Lavender)
Success ✅ #4CAF50  (Checkmark)
```

## 📏 Dimensions

### Card Sizes:
- **Customer Card**: 120dp x 120dp (Square)
- **Order Card**: Dynamic width x 120dp
- **Pot Area**: Fill remaining space
- **Checklist**: 140dp width x fill height
- **Ingredient Item**: 80dp x 100dp
- **Grid**: 4 columns x 2 rows

### Corner Radius:
- Customer/Order: 20dp
- Pot Area: 24dp
- Checklist: 16dp
- Ingredient: 16dp

### Stroke Width:
- Customer/Order: 3dp (White)
- Pot Area: 4dp (Brown)
- Checklist: 2dp (Blue)
- Ingredient: 2dp (Orange)

## 🎯 Component Details

### 1. Customer Card (Top Left)
```xml
Màu nền: kitchen_pink (#FF85B3)
Viền: White 3dp
Bo góc: 20dp
Nội dung: ImageView placeholder
Icon mặc định: ic_customer_placeholder (purple person)
```

### 2. Order Card (Top Right)
```xml
Màu nền: kitchen_yellow (#FFD93D)
Viền: White 3dp
Bo góc: 20dp
Nội dung:
  - TextView: Tên món (20sp, bold)
  - ImageView: Ảnh món ăn (40dp)
  - ImageButton: Loa 🔊 (40dp)
```

### 3. Pot Area (Middle Left)
```xml
Màu nền: kitchen_cream (#FFF5E4)
Viền: kitchen_brown (#D4A574) 4dp
Bo góc: 24dp
Nội dung:
  - ImageView: Ảnh nồi (fill)
  - TextView: "🍳 Drop Here" (16sp, orange)
```

### 4. Checklist (Middle Right)
```xml
Màu nền: White
Viền: kitchen_blue (#4ECDC4) 2dp
Bo góc: 16dp
Header: "Recipe" (16sp, bold, blue)
Items:
  - TextView: "emoji + name" (14sp)
  - ImageView: Checkmark (24dp, gone by default)
```

### 5. Ingredients Area (Bottom)
```xml
Màu nền: kitchen_green (#6BCF7F)
Viền: White 3dp
Bo góc: 20dp
Header: "🥘 Ingredients" (18sp, bold, white)
Grid: 4 columns, equal spacing
```

### 6. Ingredient Item
```xml
Màu nền: White
Viền: kitchen_orange (#FF9F40) 2dp
Bo góc: 16dp
Kích thước: 80dp x 100dp
Nội dung:
  - ImageView: 50dp x 50dp
  - TextView: 12sp, bold, 1 line max
```

## 🎭 Visual Effects

### Drag & Drop:
```
1. Touch Down    → Start drag with shadow
2. Enter Pot     → Pot scale 1.0 → 1.1
3. Exit Pot      → Pot scale 1.1 → 1.0
4. Drop          → Check ingredient
5. Drag End      → Reset all
```

### Correct Drop:
```
Pot: Scale 1.0 → 1.15 → 1.0 (300ms)
Checkmark: Invisible → Visible + Scale 1.2 → 1.0
Toast: "Perfect! ✓"
```

### Wrong Drop:
```
Pot: TranslateX shake animation
     0 → -25 → 25 → -25 → 25 → 0 (400ms)
Toast: "Wrong ingredient! ❌"
```

### Recipe Complete:
```
Pot: Rotate 360° + Scale 1.3 (500ms)
Toast: "🎉 Delicious! Recipe Complete! 🎉"
Score: +1
Delay: 1500ms → New recipe
```

### Speaker Button:
```
Scale: 1.0 → 0.8 → 1.0 (200ms)
TTS: Speak dish name in English
```

## 📱 Responsive Design

Layout tự động điều chỉnh:
- **Portrait**: Layout dọc như thiết kế
- **Landscape**: Có thể cân nhắc điều chỉnh tỷ lệ
- **Small screens**: Text size co giãn
- **Large screens**: Grid có thể thêm cột

## 🔄 Game Flow

```
START
  ↓
Load Random Recipe
  ↓
Display: Dish name, image, checklist
  ↓
User drags ingredient
  ↓
Drop on pot?
  ├─ YES → Correct ingredient?
  │         ├─ YES → Add to list, check ✓
  │         │        All done?
  │         │        ├─ YES → +1 score, celebration
  │         │        │        → Load new recipe
  │         │        └─ NO → Wait for more
  │         └─ NO → Shake pot, show error
  └─ NO → Cancel drag
```

## 🎨 Design Philosophy

✅ **Colorful but not overwhelming**
- Sử dụng màu sáng, vui tươi
- Mỗi khu vực có màu riêng biệt
- Viền trắng tạo độ tương phản

✅ **Clear hierarchy**
- Top: Customer & Order (What?)
- Middle: Work area (Where?)
- Bottom: Materials (How?)

✅ **Kid-friendly**
- Font lớn, đậm
- Icon và emoji rõ ràng
- Animation vui nhộn
- Feedback tức thì

✅ **Educational value**
- Học tên nguyên liệu
- Text-to-Speech
- Kết hợp màu sắc với vật phẩm
- Tư duy logic (công thức)

---

**Chúc bạn thành công với game MasterChef mới! 🎉👨‍🍳**
