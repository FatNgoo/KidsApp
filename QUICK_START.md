# 🎮 Quick Start Guide - Level & Star System

## 📱 Cách Sử Dụng Hệ Thống

### Cho Người Dùng (Kids):

1. **Vào Adventure Map:**
   - Home → Click "SCHOOL" 
   - Chọn "The Academy"
   - Thấy bản đồ với 10 levels

2. **Chọn Level:**
   - Level 1 luôn mở sẵn
   - Click vào Level 1 → Thấy 3 activities

3. **Hoàn Thành Activities:**
   - 📇 **Flashcard**: Học 5 từ vựng → +1 ⭐
   - ✏️ **Word Workshop**: Spelling game → +1 ⭐
   - 🎯 **Mini Quiz**: (Test mode) Tap to complete → +1 ⭐

4. **Unlock Level Tiếp Theo:**
   - Đủ 3 sao → Level 2 tự động mở
   - Quay lại Map → Click Level 2

---

## 👨‍💻 Cho Developer

### Build & Run:
```bash
cd e:\ENGLISHAPP\KidsApp
.\gradlew clean assembleDebug
# Hoặc dùng Android Studio: Build > Make Project
```

### Test Progress System:
```java
// Trong LevelDetailFragment hoặc bất kỳ Fragment nào:

// 1. Get ProgressManager instance
ProgressManager pm = ProgressManager.getInstance(requireContext());

// 2. Mark activity completed
pm.setActivityCompleted(1, "flashcard");

// 3. Check stars
int stars = pm.getStarsForLevel(1); // Returns 0-3

// 4. Check if level unlocked
boolean unlocked = pm.isLevelUnlocked(2); // true if Level 1 has 3 stars

// 5. Reset progress (for testing)
pm.resetAllProgress();
```

### Add New Level:
1. Mở [MapFragment.java](e:\ENGLISHAPP\KidsApp\app\src\main\java\com\edu\kidsapp\MapFragment.java)
2. Thêm vào method `createDummyLevels()`:
```java
levels.add(new LevelModel(11, "New Topic", false, false, 0));
```

### Add New Activity:
1. Tạo Fragment/Activity mới
2. Trong `onComplete()` method:
```java
ProgressManager.getInstance(context)
    .setActivityCompleted(levelId, "newactivity");
```
3. Update [LevelProgress.java](e:\ENGLISHAPP\KidsApp\app\src\main\java\com\edu\kidsapp\LevelProgress.java):
```java
private boolean newActivityCompleted;
// Add getter/setter và update getStarsEarned()
```

---

## 🔧 Customization

### Change Star Requirement:
Mở [ProgressManager.java](e:\ENGLISHAPP\KidsApp\app\src\main\java\com\edu\kidsapp\ProgressManager.java), method `isLevelUnlocked()`:
```java
// Thay vì check 3 sao, có thể check 2 sao:
return previousProgress.getStarsEarned() >= 2;
```

### Change Colors:
Mở [fragment_level_detail.xml](e:\ENGLISHAPP\KidsApp\app\src\main\res\layout\fragment_level_detail.xml):
```xml
<!-- Flashcard card -->
app:cardBackgroundColor="@color/pastel_blue"

<!-- Word Workshop card -->
app:cardBackgroundColor="@color/pastel_green"

<!-- Mini Quiz card -->
app:cardBackgroundColor="@color/pastel_purple"
```

### Add Level Images:
1. Thêm drawable vào `res/drawable/`
2. Mở [item_level_node.xml](e:\ENGLISHAPP\KidsApp\app\src\main\res\layout\item_level_node.xml)
3. Trong LevelAdapter, set image:
```java
holder.imgLevelIcon.setImageResource(getImageForLevel(level.getId()));
```

---

## 🐛 Troubleshooting

### Issue: Level không unlock sau khi có 3 sao
**Fix**: Quay lại MapFragment để refresh
```java
// Trong MapFragment.onResume():
levelAdapter.notifyDataSetChanged();
```

### Issue: Progress bị mất
**Check**: SharedPreferences có đang bị clear không?
```bash
# Android Studio: Device File Explorer
/data/data/com.edu.kidsapp/shared_prefs/KidsAppProgress.xml
```

### Issue: Navigation không hoạt động
**Check**: nav_graph.xml có đúng action IDs không?
```xml
<!-- Cần có các actions này: -->
action_school_lobby_to_map
action_map_to_levelDetail
action_levelDetail_to_lesson
action_levelDetail_to_wordWorkshop
```

---

## 📊 Progress Data Structure

```
SharedPreferences: "KidsAppProgress"
├─ 1_flashcard: true/false
├─ 1_word_workshop: true/false
├─ 1_mini_quiz: true/false
├─ 2_flashcard: true/false
├─ 2_word_workshop: true/false
└─ 2_mini_quiz: true/false
```

---

## 🎯 Next Steps

### Must-Have:
- [ ] Tạo Mini Quiz Fragment
- [ ] Test navigation flow hoàn chỉnh
- [ ] Add loading states

### Nice-to-Have:
- [ ] Animation khi unlock level
- [ ] Confetti effect khi complete
- [ ] Sound effects
- [ ] Achievement badges

---

**Need Help?** Check [LEVEL_SYSTEM_SUMMARY.md](./LEVEL_SYSTEM_SUMMARY.md) for full documentation.
