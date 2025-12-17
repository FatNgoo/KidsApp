# 🍳 MasterChef Game - Interactive Cooking with Dialogue System

## 📋 Tổng Quan

Game MasterChef đã được thiết kế lại hoàn toàn với hệ thống **đối thoại tương tác** giữa đầu bếp, khách hàng và người chơi. Game mô phỏng quy trình nấu ăn thực tế với các bước rõ ràng và speech bubbles hiển thị trên đầu nhân vật.

## 🎮 Gameplay Flow

### Quy Trình Nấu Ăn Từng Bước:

1. **Customer Orders** 🙋‍♀️
   - Khách hàng xuất hiện
   - Speech bubble hiện: "I want order fried chicken"
   - TTS đọc to câu order
   - ⏱️ Delay 3 giây

2. **Chef Acknowledges** 👨‍🍳
   - Đầu bếp trả lời
   - Speech bubble hiện: "Waiting five minutes"
   - TTS đọc to câu trả lời
   - ⏱️ Delay 3 giây

3. **Chef Places Pan** 🍳
   - Đầu bếp đặt chảo lên bếp
   - Animation: chảo xuất hiện từ nhỏ đến to
   - Chảo hiện rõ trên bếp
   - ⏱️ Delay 2 giây

4. **Chef Needs Chicken** 🍗
   - Speech bubble hiện: "I need chicken"
   - TTS đọc to yêu cầu
   - **BÉ HÀNH ĐỘNG**: Kéo chicken từ ingredients area đến đầu bếp
   - Khi đúng: ✓ Animation phóng to chef, toast "Perfect!"
   - Khi sai: ❌ Animation shake, toast "Wrong ingredient!"

5. **Chef Needs Oil** 🛢️
   - Speech bubble hiện: "I need some oil"
   - TTS đọc to yêu cầu
   - **BÉ HÀNH ĐỘNG**: Kéo oil từ ingredients area đến đầu bếp
   - Validation tương tự bước trên

6. **Cooking Process** 🔥
   - Speech bubble ẩn đi
   - Icon lửa 🔥 xuất hiện ở bếp
   - Animation: chảo rung nhẹ (rotation animation)
   - ⏱️ Cooking time 3 giây

7. **Chef Done** 😋
   - Speech bubble hiện: "Wow, yummy yummy"
   - TTS đọc to câu cảm thán
   - Chảo biến mất (fade out)
   - ⏱️ Delay 2 giây

8. **Serve to Customer** 🍽️
   - Món ăn được đưa cho khách (animation)
   - ⏱️ Delay 1 giây

9. **Customer Thanks** 😊
   - Speech bubble hiện: "Thank you very much"
   - TTS đọc to lời cảm ơn
   - Animation: khách hàng phóng to nhỏ (celebration)
   - Score +1 ⭐
   - Toast: "🎉 Delicious! 🎉"
   - ⏱️ Delay 4 giây

10. **New Round** 🔄
    - Tất cả reset
    - Bắt đầu quy trình mới từ bước 1

## 🎨 UI Components

### Layout Structure:

```
┌─────────────────────────────────────┐
│           ⭐ Score (Top Right)       │
├─────────────────────────────────────┤
│                                     │
│         [Speech Bubble]             │ ← Customer's dialogue
│              👤                     │ ← Customer
│           Customer                  │
│                                     │
├─────────────────────────────────────┤
│  [Speech Bubble]                    │ ← Chef's dialogue
│       👨‍🍳                        ╔═══╗│
│      Chef                       ║🍳 ║│ ← Pan on Stove
│                                 ╚═══╝│
├─────────────────────────────────────┤
│        🥘 Ingredients               │
│  ┌────┬────┬────┬────┐              │
│  │ 🍗 │ 🛢️ │ 🧂 │ 🌶️ │              │
│  │ 🍞 │ 🧀 │ 🥚 │ 🍅 │              │
│  └────┴────┴────┴────┘              │
└─────────────────────────────────────┘
```

### Speech Bubbles:
- **White background** với rounded corners
- **Black text** rõ ràng, dễ đọc
- **Elevation/Shadow** tạo hiệu ứng nổi
- **Fade in/out animation** mượt mà
- Tự động ẩn khi hết thời gian

### Characters:
- **Customer**: Ở phía trên, to hơn (120x120dp)
- **Chef**: Ở bên trái, vừa phải (100x100dp)
- Both có thể scale animation khi tương tác

### Stove Area:
- **Background**: Kitchen theme với viền nâu
- **Pan**: Hiện khi cần, có drop target
- **Fire icon**: Hiện khi đang nấu

## 🎯 Drag & Drop Mechanics

### Ingredients Grid:
- 8 nguyên liệu trong grid 4x2
- Mỗi item có:
  - Icon/Image
  - Tên hiển thị
  - Drag shadow khi kéo

### Drop Target:
- **Chef** (imgChef) là drop target
- Khi hover: Chef phóng to 1.1x
- Khi drop đúng: Animation nhận + Toast
- Khi drop sai: Shake animation + Toast

## 🎵 Text-to-Speech

### Dialogues được đọc to:
1. "I want order fried chicken"
2. "Waiting five minutes"
3. "I need chicken"
4. "I need some oil"
5. "Wow, yummy yummy"
6. "Thank you very much"

### TTS Settings:
- Language: English (US)
- Queue mode: FLUSH (không chồng chéo)
- Auto cleanup on destroy

## ⏱️ Timing & Flow

| Step | Duration | Total Elapsed |
|------|----------|---------------|
| Customer orders | 3s | 3s |
| Chef acknowledges | 3s | 6s |
| Place pan | 2s | 8s |
| Need chicken | Wait for player | - |
| Need oil | Wait for player | - |
| Cooking | 3s | ~15s |
| Chef done | 2s | ~17s |
| Serve | 1s | ~18s |
| Customer thanks | 4s | ~22s |

**Total cycle**: ~22-30 giây (tùy tốc độ người chơi)

## 📦 Files Đã Tạo/Sửa

### Layout Files:
1. ✅ `activity_cooking_game.xml` - Layout chính với 3 khu vực
2. ✅ `speech_bubble.xml` - Template speech bubble (not used directly, inline)
3. ✅ `item_ingredient.xml` - Item nguyên liệu (existing)

### Drawable Resources:
1. ✅ `speech_bubble_tail.xml` - Triangle cho speech bubble
2. Existing: `bg_pot_area.xml`, `ic_pot.xml`, etc.

### Java Files:
1. ✅ `CookingGameActivity.java` - Logic game hoàn toàn mới
   - Enum CookingStep (8 steps)
   - Speech bubble management
   - Drag & drop handling
   - TTS integration
   - Animation system
   - Handler for delays

## 🎨 Animation Details

### Speech Bubble Animations:
```java
// Show
bubble.setAlpha(0f);
bubble.animate().alpha(1f).setDuration(300).start();

// Hide
bubble.animate().alpha(0f).setDuration(300)
    .withEndAction(() -> bubble.setVisibility(View.GONE))
    .start();
```

### Chef Receive Animation:
```java
imgChef.animate()
    .scaleX(1.15f).scaleY(1.15f)
    .setDuration(150)
    .withEndAction(() -> 
        imgChef.animate().scaleX(1.0f).scaleY(1.0f)
            .setDuration(150).start()
    ).start();
```

### Pan Cooking Animation:
```java
ObjectAnimator rotation = ObjectAnimator.ofFloat(
    imgPan, "rotation", 0f, 5f, -5f, 0f
);
rotation.setDuration(500);
rotation.setRepeatCount(5);
rotation.start();
```

### Shake Animation (Wrong ingredient):
```java
ObjectAnimator shake = ObjectAnimator.ofFloat(
    view, "translationX", 0f, -25f, 25f, -25f, 25f, 0f
);
shake.setDuration(400);
shake.start();
```

## 🔧 Customization

### Thay Đổi Món Ăn:
```java
private String currentDish = "Fried Chicken";
// Có thể mở rộng thành List<Recipe> với nhiều món
```

### Thêm Nguyên Liệu Mới:
```java
private static final Ingredient[] ALL_INGREDIENTS = {
    new Ingredient("NEW_ITEM", "Display Name", "🆕", R.drawable.new_icon),
    // ...
};
```

### Điều Chỉnh Timing:
```java
handler.postDelayed(() -> {
    // Your code
}, 3000); // Change delay here (milliseconds)
```

### Customize Dialogue:
```java
showChefDialogue("Your custom text here");
speakText("Your custom text here");
```

## 🎯 Learning Objectives

Bé học được:
1. **Listening skills**: Nghe và hiểu yêu cầu bằng tiếng Anh
2. **Following instructions**: Làm theo hướng dẫn từng bước
3. **Vocabulary**: Học từ vựng nguyên liệu, nấu ăn
4. **Sequencing**: Hiểu quy trình nấu ăn logic
5. **Interaction**: Tương tác với nhân vật trong game
6. **Patience**: Chờ đợi và theo dõi quy trình

## 🐛 Troubleshooting

### Speech bubbles không hiện:
- Check `visibility` initial state là `GONE`
- Verify `showCustomerDialogue()` và `showChefDialogue()` được gọi

### TTS không hoạt động:
- Check device có TTS engine chưa
- Verify language pack English (US) đã cài
- Test với `ttsReady` flag

### Drag & Drop không work:
- Check `setOnTouchListener` được setup đúng
- Verify `setOnDragListener` trên imgChef
- Check ClipData được tạo đúng

### Animation lag:
- Reduce animation duration
- Check handler delays không chồng chéo
- Verify không có memory leak

## 🚀 Next Steps

### Có thể mở rộng:
1. ✅ Thêm nhiều món ăn khác (Pizza, Salad, Burger...)
2. ✅ Nhiều nguyên liệu hơn cho mỗi món
3. ✅ Level system: dễ → khó
4. ✅ Timer challenge mode
5. ✅ Sound effects (sizzle, chop, ding...)
6. ✅ More characters (waiters, sous chef...)
7. ✅ Recipe book để review
8. ✅ Achievements system

## 📸 Images Cần Thêm

### Priority High:
- [ ] **chef.png** - Hình đầu bếp chuyên nghiệp
- [ ] **customer.png** - Hình khách hàng thân thiện
- [ ] **pan.png** - Hình chảo nấu
- [ ] **stove.png** - Hình bếp gas/điện
- [ ] **chicken_raw.png** - Hình gà sống
- [ ] **oil_bottle.png** - Hình chai dầu
- [ ] **fried_chicken.png** - Hình gà rán hoàn thành

### Priority Medium:
- [ ] Other ingredients với hình ảnh rõ ràng
- [ ] Kitchen background
- [ ] Plate for serving

## 🎉 Summary

Game MasterChef mới tạo trải nghiệm **tương tác hoàn chỉnh** với:
- ✅ Đối thoại giữa nhân vật
- ✅ Speech bubbles trực quan
- ✅ Quy trình nấu ăn từng bước
- ✅ Drag & drop ingredients
- ✅ Animations mượt mà
- ✅ Text-to-Speech tích hợp
- ✅ Educational và entertaining

**Chúc bé vui vẻ với vai trò đầu bếp nhỏ! 👨‍🍳🎉**
