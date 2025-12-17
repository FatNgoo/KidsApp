# Music Room Feature - Documentation

## 📝 Overview
Music Room là tính năng học tiếng Anh qua bài hát với trò chơi điền từ vào chỗ trống (Fill-in-the-Blank). Người dùng nghe bài hát và điền các từ còn thiếu vào lời bài hát.

## 🎨 UI/UX Design
Thiết kế đồng bộ với app hiện tại:
- **Màu chủ đạo**: Pastel Purple (`#CE93D8`) - đại diện cho Music Room
- **Màu phụ**: Pastel Blue, Pastel Green, Pastel Orange
- **Layout**: Card-based design với MaterialCardView
- **Font**: sans-serif-black (bold titles), sans-serif-medium (content)
- **Background**: background3.png (giống Word Workshop)

## 📂 Files Created

### 1. Layout File
**Path**: `app/src/main/res/layout/fragment_music_room.xml`
- Header card với tiêu đề "🎵 MUSIC ROOM"
- Song title card hiển thị tên bài hát
- Music player controls (Play/Pause button)
- Lyrics section với các chỗ trống để điền
- Word Bank với các từ để chọn
- Check Answer button
- Score display card

### 2. Fragment Class
**Path**: `app/src/main/java/com/edu/kidsapp/MusicRoomFragment.java`

**Chức năng chính**:
- ✅ Load bài hát mẫu "Twinkle Twinkle Little Star"
- ✅ Hiển thị lời bài hát với chỗ trống (`_____`)
- ✅ Tạo Word Bank với các từ được shuffle
- ✅ Cho phép click từ để điền vào chỗ trống
- ✅ Click vào blank để xóa từ và trả về Word Bank
- ✅ Simulate phát nhạc với progress indicator
- ✅ Kiểm tra đáp án và hiển thị kết quả
- ✅ Visual feedback (màu xanh = đúng, màu cam = sai)
- ✅ Hiển thị điểm số
- ✅ Reset game để chơi lại

### 3. Model Class
**Path**: `app/src/main/java/com/edu/kidsapp/Song.java`

**Properties**:
```java
- int id
- String title
- String subtitle
- String[] lyrics (lời bài hát với "_____" đại diện blank)
- String[] correctAnswers (đáp án đúng)
- int audioResourceId (placeholder cho audio)
```

### 4. Drawable Resources
**Path**: `app/src/main/res/drawable/`
- `letter_slot_empty.xml` - Background cho chỗ trống chưa điền (dashed border)
- `letter_slot_filled.xml` - Background cho chỗ trống đã điền (solid border)

### 5. Navigation Updates
**Path**: `app/src/main/res/navigation/nav_graph.xml`
- Thêm `musicRoomFragment` destination
- Thêm action `action_school_lobby_to_music_room`

**Path**: `app/src/main/java/com/edu/kidsapp/SchoolLobbyFragment.java`
- Cập nhật `handleClassroomClick()` để navigate đến Music Room

## 🎮 Gameplay Flow

1. **Start**: User clicks "Music Room" từ School Lobby
2. **View Song**: Hiển thị tên bài hát và lời bài hát với blank
3. **Play Music** (Optional): Click "▶️ Play Song" để simulate phát nhạc
4. **Fill Blanks**: 
   - Click từ trong Word Bank
   - Từ sẽ điền vào blank tiếp theo
   - Click vào blank đã điền để xóa
5. **Check Answers**: Click "✓ Check Answers"
6. **View Results**: 
   - Blank đúng → màu xanh (green)
   - Blank sai → màu cam (orange)
   - Hiển thị score
   - Dialog khuyến khích
7. **Retry**: Click "Try Again" hoặc "Back"

## 🎵 Sample Song
**Twinkle Twinkle Little Star** (5 blanks):
```
Twinkle, twinkle, little _____ (star)
How I wonder what you _____ (are)
Up above the world so _____ (high)
Like a diamond in the _____ (sky)
Twinkle, twinkle, little star
How I _____ what you are (wonder)
```

## 🔄 Future Enhancements

### Phase 1 (Current)
- ✅ Basic fill-in-the-blank gameplay
- ✅ Simulated music playback
- ✅ Answer checking
- ✅ Visual feedback

### Phase 2 (Recommended)
- 🎵 Real audio playback integration
- 📚 Multiple songs database
- 🏆 Scoring system with rewards (gold, tickets)
- 💾 Progress tracking
- 🎚️ Difficulty levels (Easy/Medium/Hard)

### Phase 3 (Advanced)
- 🎤 Voice input để check pronunciation
- 🎬 Video integration
- 🌐 Song categories (Nursery Rhymes, Pop, Educational)
- 👥 Multiplayer mode
- 📊 Analytics and performance tracking

## 🎨 Color Scheme
```xml
pastel_purple: #CE93D8  (Main Music Room color)
pastel_blue:   #81D4FA  (Accents)
pastel_green:  #A5D6A7  (Success/Word Bank)
pastel_orange: #FFAB91  (Incorrect/Actions)
text_dark:     #37474F  (Text)
card_white:    #FFFFFF  (Cards)
bg_app:        #FFF9C4  (Background sections)
```

## 📱 Screen Preview
```
┌─────────────────────────────────────┐
│   🎵 MUSIC ROOM                     │
│   Listen and fill in missing words │
├─────────────────────────────────────┤
│                                     │
│  ┌───────────────────────────────┐ │
│  │ Twinkle Twinkle Little Star   │ │
│  │ Classic Nursery Rhyme         │ │
│  └───────────────────────────────┘ │
│                                     │
│  ┌───────────────────────────────┐ │
│  │      🎵                        │ │
│  │   ▶️ Play Song                 │ │
│  │   Ready to play               │ │
│  └───────────────────────────────┘ │
│                                     │
│  ┌───────────────────────────────┐ │
│  │ 📝 Lyrics                      │ │
│  │ Twinkle, twinkle, little ___  │ │
│  │ How I wonder what you ___     │ │
│  │ Up above the world so ___     │ │
│  └───────────────────────────────┘ │
│                                     │
│  ┌───────────────────────────────┐ │
│  │ 💡 Word Bank                   │ │
│  │ [star] [are] [high] [sky]     │ │
│  │ [wonder]                      │ │
│  └───────────────────────────────┘ │
│                                     │
│      [✓ Check Answers]             │
│                                     │
└─────────────────────────────────────┘
```

## 🔧 Technical Implementation

### Key Components
1. **Dynamic UI Generation**: Lyrics và blanks được tạo động dựa trên Song data
2. **State Management**: HashMap để track user answers và blank positions
3. **Interactive Elements**: Click listeners cho words và blanks
4. **Visual Feedback**: Background changes và color coding
5. **Simulated Playback**: Handler + Runnable cho music progress

### Design Patterns
- **Model-View Pattern**: Song model tách biệt với UI
- **Map-based Tracking**: Efficient blank/answer management
- **Dynamic Layout**: FlexboxLayout cho responsive Word Bank
- **Material Design**: MaterialCardView, MaterialButton

## ✅ Testing Checklist
- [x] Build successful (assembleDebug)
- [x] Navigation works from School Lobby
- [x] Lyrics display correctly with blanks
- [x] Word Bank shows shuffled words
- [x] Can fill blanks by clicking words
- [x] Can clear blanks by clicking them
- [x] Check Answers validates correctly
- [x] Score displays properly
- [x] Dialog shows appropriate message
- [x] Try Again resets game
- [x] UI matches app design system

## 📝 Notes
- Placeholder được sử dụng cho music playback (simulated với Handler)
- Sample song "Twinkle Twinkle Little Star" được hardcode
- Trong tương lai có thể extend với database of songs
- Tương thích với Android Material Design 3
- Supports scroll view cho các bài hát dài

---

**Created**: December 17, 2025
**Status**: ✅ Complete & Working
**Build**: Successful
