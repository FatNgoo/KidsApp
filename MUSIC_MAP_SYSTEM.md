# Music Map System - Complete Documentation

## 📝 Overview
Music Map là hệ thống thư viện bài hát được tổ chức theo chủ đề (categories). User chọn bài hát từ Music Map, sau đó vào Music Room để chơi fill-in-the-blank với từ vựng liên quan đến chủ đề đó.

## 🎯 Navigation Flow
```
Home 
  → School Lobby 
    → Music Map (Song Library) 
      → Music Room (Fill-in-the-Blank Game)
```

## 🎨 UI/UX Design
Thiết kế đồng bộ với MapFragment và level system:
- **Card-based layout** với RecyclerView
- **Color coding** theo categories:
  - 🐮 Animals: Pastel Green (`#A5D6A7`)
  - 🎨 Colors: Pastel Orange (`#FFAB91`)
  - 🔢 Numbers: Pastel Blue (`#81D4FA`)
  - 🎵 Nursery Rhymes: Pastel Purple (`#CE93D8`)
- **Lock system** để unlock songs theo progress
- **Badge categories** hiển thị rõ chủ đề
- **Blank count indicator** cho biết độ khó

## 📂 New Files Created

### 1. Fragment & Layout
**`MusicMapFragment.java`** - Main fragment hiển thị song library
- RecyclerView với LinearLayoutManager
- 8 bài hát mẫu theo 4 categories
- Navigate đến Music Room với song data
- Lock/unlock system
- Back button navigation

**`fragment_music_map.xml`** - Layout cho Music Map
- Header với title "🎵 Music Library"
- Subtitle "Choose a song to practice"
- RecyclerView cho danh sách bài hát
- Background đồng bộ với app

### 2. Song Item Layout
**`item_song.xml`** - Card layout cho mỗi bài hát
- **Left**: Icon container với music icon và lock overlay
- **Right**: Song info (category badge, title, subtitle, blank count)
- **Bottom-right**: Play button
- Rounded corners, elevation, pastel colors

### 3. Adapter
**`SongAdapter.java`** - RecyclerView adapter
- Bind song data to item views
- Handle locked/unlocked states
- Set category-based colors và icons
- Click listeners cho navigation

### 4. Model Class
**`SongCategory.java`** - Model cho songs
```java
- int id
- String title, subtitle, category
- String[] lyrics (với "_____" cho blanks)
- String[] correctAnswers
- int iconResId
- boolean isLocked
```

### 5. Updates to Existing Files
**`MusicRoomFragment.java`**
- ✅ Thêm method `loadSong()` để load từ arguments
- ✅ Fallback về sample song nếu không có arguments
- ✅ Nhận songId, title, subtitle, lyrics, correctAnswers từ Bundle

**`SchoolLobbyFragment.java`**
- ✅ Changed navigation: `action_school_lobby_to_music_map` (thay vì direct to music room)

**`nav_graph.xml`**
- ✅ Thêm `musicMapFragment` destination
- ✅ Thêm action `action_school_lobby_to_music_map`
- ✅ Thêm action `action_music_map_to_music_room`

## 🎵 Song Library Content

### 📚 Nursery Rhymes (2 songs)
1. **Twinkle Twinkle Little Star** ⭐
   - 5 blanks: star, are, high, sky, wonder
   - Status: Unlocked

2. **Wheels on the Bus** 🚌
   - 3 blanks: round, round, town
   - Status: Locked

### 🐮 Animals (3 songs)
1. **Old MacDonald Had a Farm** 🚜
   - 5 blanks: farm, cow, moo, moo, moo
   - Vocabulary: farm animals, sounds
   - Status: Unlocked

2. **Mary Had a Little Lamb** 🐑
   - 4 blanks: lamb, snow, went, go
   - Vocabulary: lamb, fleece, follow
   - Status: Unlocked

3. **Baa Baa Black Sheep** 🐑
   - 4 blanks: sheep, wool, full, lane
   - Vocabulary: sheep, wool, master, dame
   - Status: Locked

### 🎨 Colors (1 song)
1. **Rainbow Song** 🌈
   - 3 blanks: green, blue, rainbow
   - Vocabulary: red, yellow, pink, purple, orange, blue
   - Status: Unlocked

### 🔢 Numbers (2 songs)
1. **Five Little Ducks** 🦆
   - 4 blanks: day, away, quack, back
   - Vocabulary: counting from 5 to 0
   - Status: Unlocked

2. **Ten Little Fingers** ✋
   - 4 blanks: fingers, me, tight, wide
   - Vocabulary: body parts, actions
   - Status: Locked

## 🔐 Lock/Unlock System

### Current Implementation
- Unlocked songs: 5 songs
- Locked songs: 3 songs (requires previous completion)

### Future Enhancement
```java
// Có thể implement với ProgressManager
ProgressManager.getInstance().isSongUnlocked(songId)
ProgressManager.getInstance().markSongCompleted(songId)
```

## 🎮 User Experience Flow

### 1. Enter Music Map
```
School Lobby → Click "Music Room" → Music Map opens
```

### 2. Browse Songs
- Scroll through categorized song list
- See category badges (Animals, Colors, Numbers, Nursery Rhymes)
- View blank count to gauge difficulty
- Locked songs show lock icon and are greyed out

### 3. Select Song
- Tap on unlocked song card
- OR tap play button

### 4. Navigate to Music Room
- Transition animation (slide)
- Song data passed via Bundle arguments
- Music Room loads with selected song

### 5. Play Game
- See lyrics with blanks
- Word bank with theme-related vocabulary
- Fill in blanks
- Check answers
- Try again or return

## 📊 Category-Based Learning

### Animals Category
**Vocabulary Focus**: farm, cow, lamb, sheep, wool, moo, baa
- Old MacDonald: Animal names và sounds
- Mary Had a Little Lamb: Lamb characteristics
- Baa Baa Black Sheep: Sheep products (wool)

### Colors Category
**Vocabulary Focus**: red, yellow, pink, green, purple, orange, blue, rainbow
- Rainbow Song: All color names

### Numbers Category
**Vocabulary Focus**: five, ten, fingers, counting words
- Five Little Ducks: Counting down
- Ten Little Fingers: Counting body parts

### Nursery Rhymes Category
**Vocabulary Focus**: star, sky, wonder, bus, wheels, round
- Classic songs with general vocabulary

## 🎨 Visual Design Details

### Song Card Components
```
┌─────────────────────────────────────────┐
│  ┌────┐  [CATEGORY BADGE]              │
│  │ 🎵 │  Song Title                     │
│  │    │  Song subtitle                  │
│  └────┘  Blanks: X                    ▶│
└─────────────────────────────────────────┘
```

### Color Palette
- **Purple** (`#CE93D8`): Nursery Rhymes, default icon
- **Green** (`#A5D6A7`): Animals
- **Orange** (`#FFAB91`): Colors
- **Blue** (`#81D4FA`): Numbers, category badges
- **White** (`#FFFFFF`): Card backgrounds
- **Dark** (`#37474F`): Text

### States
- **Normal**: Full color, clickable, play button enabled
- **Locked**: 50% alpha, lock icon visible, disabled

## 🔄 Data Flow

### Music Map → Music Room
```java
Bundle bundle = new Bundle();
bundle.putInt("songId", song.getId());
bundle.putString("songTitle", song.getTitle());
bundle.putString("songSubtitle", song.getSubtitle());
bundle.putString("songCategory", song.getCategory());
bundle.putStringArray("lyrics", song.getLyrics());
bundle.putStringArray("correctAnswers", song.getCorrectAnswers());

Navigation.navigate(R.id.action_music_map_to_music_room, bundle);
```

### Music Room receives data
```java
Bundle args = getArguments();
if (args != null && args.containsKey("songTitle")) {
    // Load from arguments
    String title = args.getString("songTitle");
    String[] lyrics = args.getStringArray("lyrics");
    // ... create Song object
} else {
    // Fallback to default song
    loadSampleSong();
}
```

## 📱 Screen Layouts

### Music Map Screen
```
┌──────────────────────────────────────┐
│  ←  🎵 Music Library                │
│     Choose a song to practice       │
├──────────────────────────────────────┤
│                                      │
│  ┌────────────────────────────────┐ │
│  │  🎵  [NURSERY RHYMES]          │ │
│  │      Twinkle Twinkle           │ │
│  │      Classic bedtime song      │ │
│  │      Blanks: 5               ▶ │ │
│  └────────────────────────────────┘ │
│                                      │
│  ┌────────────────────────────────┐ │
│  │  🐮  [ANIMALS]                 │ │
│  │      Old MacDonald             │ │
│  │      Learn animal sounds       │ │
│  │      Blanks: 5               ▶ │ │
│  └────────────────────────────────┘ │
│                                      │
│  ┌────────────────────────────────┐ │
│  │  🐑  [ANIMALS]                 │ │
│  │      Mary Had a Little Lamb    │ │
│  │      Sweet lamb story          │ │
│  │      Blanks: 4               ▶ │ │
│  └────────────────────────────────┘ │
│                                      │
│  ... more songs ...                  │
│                                      │
└──────────────────────────────────────┘
```

## 🚀 Future Enhancements

### Phase 1 (Immediate)
- ✅ Music Map with categories
- ✅ Multiple songs per category
- ✅ Theme-based vocabulary
- ✅ Lock/unlock system

### Phase 2 (Next)
- 🎵 Real audio files for songs
- 🏆 Completion tracking per song
- ⭐ Star ratings per song
- 💾 Save progress
- 🎁 Rewards for completing categories

### Phase 3 (Future)
- 📚 Add more songs (50+ total)
- 🎤 Voice recording feature
- 🎬 Video accompaniment
- 📊 Progress statistics
- 🏅 Achievements per category
- 👥 Multiplayer song battles
- 🎨 Custom themes
- 📥 Download additional song packs

## 🔧 Technical Details

### RecyclerView Performance
- ViewHolder pattern
- Efficient binding
- No nested scrolling issues

### Navigation Arguments
- Safe Args pattern ready
- Bundle-based parameter passing
- Type-safe with manual checking

### State Management
- Lock states in model
- Category-based filtering ready
- Progress integration ready

## ✅ Testing Checklist
- [x] Build successful
- [x] Navigation: School → Music Map works
- [x] RecyclerView displays all songs
- [x] Category colors show correctly
- [x] Lock states display properly
- [x] Click unlocked song → Music Room
- [x] Song data passed correctly
- [x] Music Room displays correct lyrics
- [x] Word Bank has correct vocabulary
- [x] Back button works
- [x] UI matches app design system

## 📝 Code Statistics
- **New Java files**: 3 (MusicMapFragment, SongAdapter, SongCategory)
- **New XML layouts**: 2 (fragment_music_map, item_song)
- **Updated files**: 3 (MusicRoomFragment, SchoolLobbyFragment, nav_graph)
- **Total songs**: 8 (5 unlocked, 3 locked)
- **Categories**: 4 (Animals, Colors, Numbers, Nursery Rhymes)
- **Lines of code**: ~1000 lines

## 🎯 Key Benefits
1. **Organized Learning**: Songs grouped by themes
2. **Progressive Difficulty**: Lock system encourages completion
3. **Visual Clarity**: Category badges and color coding
4. **Theme Consistency**: Matches existing app design
5. **Scalable**: Easy to add more songs and categories
6. **Engaging**: Card-based UI with clear affordances
7. **Educational**: Vocabulary tied to categories

---

**Created**: December 17, 2025
**Status**: ✅ Complete & Working
**Build**: Successful
**Navigation Flow**: School Lobby → Music Map → Music Room ✓
