# 🚀 MasterChef Interactive Game - Quick Start Guide

## 📱 Chạy Game

### Bước 1: Build Project
```bash
cd e:\ENGLISHAPP\KidsApp
.\gradlew build
```

### Bước 2: Install vào Device/Emulator
```bash
.\gradlew installDebug
```

### Bước 3: Mở Game
- Tìm app "Kids App" trên device
- Chọn "MasterChef" game

## 🎮 Cách Chơi

### Bước 1-3: Xem và Nghe (Không cần tương tác)
1. **Customer speaks**: "I want order fried chicken"
2. **Chef responds**: "Waiting five minutes"
3. **Pan appears** on stove

### Bước 4: Người chơi tương tác - Đưa Chicken
- Chef nói: "I need chicken"
- **ACTION**: Kéo 🍗 Chicken từ ingredients grid
- Thả vào đầu bếp (imgChef)
- ✓ Nếu đúng: "Perfect!"

### Bước 5: Người chơi tương tác - Đưa Oil
- Chef nói: "I need some oil"
- **ACTION**: Kéo 🛢️ Oil từ ingredients grid
- Thả vào đầu bếp
- ✓ Nếu đúng: "Perfect!"

### Bước 6-10: Xem và Nghe (Không cần tương tác)
6. Cooking animation (3 giây)
7. Chef: "Wow, yummy yummy"
8. Serve to customer
9. Customer: "Thank you very much"
10. Score +1, new round starts!

## 🎯 Game Flow Diagram

```
START
  ↓
[Customer Orders] 📢 "I want order fried chicken"
  ↓ (3s)
[Chef Acknowledges] 💬 "Waiting five minutes"
  ↓ (3s)
[Pan on Stove] 🍳
  ↓ (2s)
[Chef Needs Chicken] 🗣️ "I need chicken"
  ↓
[PLAYER: Drag 🍗 to Chef] ← INTERACTIVE
  ↓
[Chef Receives] ✅
  ↓ (1.5s)
[Chef Needs Oil] 🗣️ "I need some oil"
  ↓
[PLAYER: Drag 🛢️ to Chef] ← INTERACTIVE
  ↓
[Chef Receives] ✅
  ↓ (1.5s)
[Cooking] 🔥 (3s animation)
  ↓
[Chef Done] 😋 "Wow, yummy yummy"
  ↓ (2s)
[Serve to Customer] 🍽️
  ↓ (1s)
[Customer Thanks] 🙏 "Thank you very much"
  ↓
[Score +1] ⭐
  ↓ (4s)
LOOP BACK TO START
```

## 🎨 Visual Preview

```
╔═══════════════════════════════════════╗
║            ⭐ Score: 5               ║
╠═══════════════════════════════════════╣
║                                       ║
║      ┌──────────────────────┐        ║
║      │ I want order fried   │        ║ ← Speech Bubble
║      │ chicken               │        ║
║      └─────────┬─────────────┘        ║
║                ↓                      ║
║            👤 Customer                ║
║                                       ║
╠═══════════════════════════════════════╣
║  ┌────────────┐                      ║
║  │ I need     │                      ║ ← Chef's Bubble
║  │ chicken    │                      ║
║  └──────┬─────┘                      ║
║         ↓                            ║
║    👨‍🍳 Chef          ┌────────┐     ║
║                      │   🍳   │     ║ ← Stove
║                      └────────┘     ║
║                                     ║
╠═════════════════════════════════════╣
║        🥘 Ingredients                ║
║  ┌────┬────┬────┬────┐              ║
║  │ 🍗 │ 🛢️ │ 🧂 │ 🌶️ │ ← Drag these!║
║  │ 🍞 │ 🧀 │ 🥚 │ 🍅 │              ║
║  └────┴────┴────┴────┘              ║
╚═══════════════════════════════════════╝
```

## ✅ Testing Checklist

### Functional Tests:
- [ ] App builds without errors
- [ ] Game launches successfully
- [ ] Customer speech bubble appears
- [ ] TTS reads customer dialogue
- [ ] Chef speech bubble appears
- [ ] TTS reads chef dialogue
- [ ] Pan appears on stove
- [ ] Can drag chicken ingredient
- [ ] Chef receives chicken correctly
- [ ] Wrong ingredient shows error
- [ ] Can drag oil ingredient
- [ ] Cooking animation plays
- [ ] Chef says "yummy yummy"
- [ ] Dish served to customer
- [ ] Customer says thank you
- [ ] Score increments
- [ ] New round starts automatically

### UI Tests:
- [ ] All text readable
- [ ] Speech bubbles properly positioned
- [ ] Animations smooth
- [ ] No UI overlap issues
- [ ] Ingredients grid displays correctly
- [ ] Characters visible and clear

### Audio Tests:
- [ ] TTS works for all dialogues
- [ ] Volume appropriate
- [ ] No speech overlap
- [ ] Clear pronunciation

## 🐛 Common Issues & Fixes

### Issue: TTS not working
**Fix:**
```
Settings → Language & Input → Text-to-Speech
Install English (US) voice pack
```

### Issue: Can't drag ingredients
**Fix:**
- Check touch is on ingredient item, not empty space
- Ensure device supports drag & drop
- Try longer press before dragging

### Issue: Speech bubbles not showing
**Fix:**
- Check if TextView IDs match:
  - tvCustomerSpeech
  - tvChefSpeech
- Verify layout includes speech bubble FrameLayouts

### Issue: Game crashes on start
**Fix:**
```bash
# Clean and rebuild
.\gradlew clean
.\gradlew build
```

### Issue: Animations laggy
**Fix:**
- Enable Developer Options
- Set "Animator duration scale" to 0.5x
- Reduce handler delays if needed

## 📊 Performance Tips

### Optimize for Low-End Devices:
1. Reduce animation durations
2. Simplify speech bubble design
3. Use smaller image assets
4. Increase handler delays slightly

### Memory Management:
- TTS properly cleaned up in `onDestroy()`
- Handler callbacks removed
- No bitmap leaks

## 🎓 Educational Notes

### Vocabulary Taught:
- "I want order..." (requesting)
- "Waiting five minutes" (time concept)
- "I need..." (necessity)
- "Thank you very much" (gratitude)
- Ingredient names: chicken, oil, salt, etc.

### Skills Developed:
- **Listening comprehension** - Understanding English requests
- **Following instructions** - Sequential task completion
- **Motor skills** - Drag and drop coordination
- **Patience** - Waiting for cooking process
- **Vocabulary** - Food and kitchen terms

## 🎯 Success Metrics

Good gameplay should have:
- ✅ 90%+ correct ingredient selections
- ✅ Smooth transitions between steps
- ✅ Clear audio for all dialogues
- ✅ Engaging animations throughout
- ✅ No crashes or freezes
- ✅ Fun and educational experience

## 📞 Support

If you encounter issues:
1. Check this guide first
2. Review `MASTERCHEF_INTERACTIVE_GUIDE.md`
3. Check Android logs: `adb logcat`
4. Verify all resource files exist

## 🎉 Enjoy!

Game này giúp bé:
- 🗣️ Học tiếng Anh tự nhiên
- 🎯 Hiểu quy trình nấu ăn
- 🎮 Vui chơi có học
- 👨‍🍳 Trở thành đầu bếp nhỏ!

**Have fun cooking! 🍳✨**
