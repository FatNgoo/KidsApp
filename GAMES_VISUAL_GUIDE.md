# 🎨 Visual Guide - Games Layout Structure

## 🍳 MASTERCHEF GAME LAYOUT

```
┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃  TOP ROW - Customer & Order                   ┃
┃  ┌────────┐  ┌──────────────────────┐  ⭐ 0  ┃
┃  │Customer│  │ Fruit Salad   🔊      │        ┃
┃  │  👤   │  │ 🥗                    │        ┃
┃  └────────┘  └──────────────────────┘        ┃
┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫
┃  MIDDLE ROW - Pot & Checklist                 ┃
┃  ┌──────────────┐  ┌───────────────┐         ┃
┃  │              │  │ Recipe        │         ┃
┃  │   🍳 Drop    │  │ ─────────────│         ┃
┃  │    Here      │  │ 🍎 Apple     │         ┃
┃  │              │  │ 🍌 Banana ✓  │         ┃
┃  │     🍲       │  │               │         ┃
┃  │              │  │               │         ┃
┃  └──────────────┘  └───────────────┘         ┃
┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫
┃  BOTTOM ROW - Ingredients Grid                ┃
┃  ┌─────────────────────────────────────────┐  ┃
┃  │ 🥘 Ingredients                          │  ┃
┃  │ ───────────────────────────────────────│  ┃
┃  │ [🍎]   [🍌]   [🥬]   [🍅]              │  ┃
┃  │ Apple  Banana Cabbage Tomato           │  ┃
┃  │                                         │  ┃
┃  │ [🥕]   [🍞]   [🧀]   [🥚]              │  ┃
┃  │ Carrot Bread  Cheese  Egg              │  ┃
┃  └─────────────────────────────────────────┘  ┃
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛
```

### Color Scheme
- **Customer Card**: Pink (#FF85B3)
- **Order Card**: Yellow (#FFD93D)
- **Pot Area**: Cream (#FFF5E4) + Brown border
- **Checklist**: White + Blue border
- **Ingredients**: Green (#6BCF7F)
- **Background**: Cream (#FFF5E4)

---

## 🔎 DETECTIVE GAME LAYOUT

```
┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃  ROW 1 - FIND Card (Current Target)           ┃
┃  ┌─────────────────────────────────────────┐  ┃
┃  │ FIND: [🔑] Key              🔊         │  ┃
┃  └─────────────────────────────────────────┘  ┃
┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫
┃  ROW 2 - Scene Image (Hidden Objects)         ┃
┃  ┌─────────────────────────────────────────┐  ┃
┃  │                                         │  ┃
┃  │     🔑              🔍                  │  ┃
┃  │                                         │  ┃
┃  │                👣                      │  ┃
┃  │                                         │  ┃
┃  │         ⌚                    📱        │  ┃
┃  │                                         │  ┃
┃  └─────────────────────────────────────────┘  ┃
┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫
┃  ROW 3 - Hint Buttons                         ┃
┃  ┌──────────┐ ┌──────────┐ ┌──────────┐      ┃
┃  │   💡     │ │   🔍     │ │   ⏭️    │      ┃
┃  │ Hint  3  │ │ Zoom  2  │ │  Skip    │      ┃
┃  └──────────┘ └──────────┘ └──────────┘      ┃
┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫
┃  ROW 4 - Objects List                    3/5  ┃
┃  ┌─────────────────────────────────────────┐  ┃
┃  │ 🔎 Objects to Find                     │  ┃
┃  │ ───────────────────────────────────────│  ┃
┃  │ [🔑✓] [🔍] [👣✓] [⌚] [📱]             │  ┃
┃  │  Key   Mag Foot Watch Phone            │  ┃
┃  └─────────────────────────────────────────┘  ┃
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛
```

### Color Scheme
- **FIND Card**: Blue (#3498DB) + Gold border (#F39C12)
- **Scene Container**: White + Navy border
- **Hint Button**: Orange (#E67E22)
- **Magnifier Button**: Teal (#1ABC9C)
- **Skip Button**: Red (#E74C3C)
- **Objects List**: Purple (#9B59B6)
- **Background**: Light (#ECF0F1)

---

## 🎮 Interaction Flow

### MasterChef Flow
```
Start
  ↓
Load Random Recipe (e.g., Fruit Salad)
  ↓
Show Recipe: Apple + Banana
  ↓
Display Checklist: [ ] Apple, [ ] Banana
  ↓
Player drags Apple → Pot
  ↓
✓ Correct! → [✓] Apple
  ↓
Player drags Banana → Pot
  ↓
✓ Correct! → [✓] Banana
  ↓
Recipe Complete! 🎉
  ↓
Score +1, Load New Recipe
```

### Detective Flow
```
Start
  ↓
Pick 5 Random Objects
  ↓
Place Objects in Scene (random positions)
  ↓
Show First Target: "FIND: Key 🔑"
  ↓
Player clicks on scene...
  ↓
┌─────────────┬─────────────┐
│ Correct?    │ Wrong?      │
│ ↓           │ ↓           │
│ ✓ Found!    │ ❌ Shake    │
│ ↓           │ ↓           │
│ Mark found  │ Try again   │
│ ↓           │             │
│ Next target └─────────────┘
└─────────────┐
              ↓
         All found?
              ↓
         Yes: Game Complete! 🎉
         No:  Continue...
```

---

## 📱 UI Components Detail

### MasterChef Components
1. **Customer Card** (120x120dp)
   - Pink background
   - White border (3dp)
   - Placeholder icon
   
2. **Order Card** (flexible width)
   - Yellow background
   - Dish name (20sp, bold)
   - Dish image (40x40dp)
   - Speaker button (40x40dp)
   
3. **Pot Area** (flexible, square-ish)
   - Cream background
   - Brown border (4dp)
   - Pot image (fills area)
   - "Drop Here" text
   
4. **Checklist** (140dp width)
   - White background
   - Blue border (2dp)
   - Scrollable list
   - Items: emoji + name + checkmark
   
5. **Ingredient Grid** (4 columns)
   - Green background
   - White border (3dp)
   - Items: 80x100dp each
   - Image (50dp) + Name (12sp)

### Detective Components
1. **FIND Card** (full width)
   - Blue background
   - Gold border (4dp)
   - Current object image (60dp)
   - Object name (20sp, bold)
   - Speaker button (48dp)
   
2. **Scene Container** (fills space)
   - White background
   - Navy border (3dp)
   - Scene image (centerCrop)
   - Hidden objects (50-80dp, random)
   
3. **Hint Buttons** (3 equal width)
   - Each: 32dp icon + text
   - Orange/Teal/Red backgrounds
   - Corner radius: 12dp
   
4. **Objects List** (horizontal scroll)
   - Purple background
   - White border (2dp)
   - Items: 80dp width each
   - Image (50dp) + Name (11sp) + Check

---

## 🎨 Typography

### MasterChef
- **Dish Name**: 20sp, sans-serif-black, bold
- **Ingredient Name**: 12sp, sans-serif-medium, bold
- **Score**: 24sp, bold

### Detective
- **FIND Label**: 24sp, sans-serif-black, bold
- **Object Name (Card)**: 20sp, sans-serif-black, bold
- **Object Name (List)**: 11sp, sans-serif-medium, bold
- **Progress**: 20sp, bold

---

## 🔄 Animations

### MasterChef
- ✓ Ingredient → Pot: Drag shadow
- ✓ Correct drop: Pot scales 1.0 → 1.15 → 1.0 (300ms)
- ✓ Wrong drop: Pot shakes X: ±25px (400ms)
- ✓ Recipe complete: Pot rotates 360° + scales 1.3 (500ms)
- ✓ Checkmark: Scales 1.3 → 1.0 (200ms)
- ✓ Speaker: Scales 0.8 → 1.0 (200ms)

### Detective
- ✓ Object found: Scales 1.5 + Alpha 1 → 0 (300ms)
- ✓ Wrong click: Shakes X: ±15px (400ms)
- ✓ Hint: Alpha 1 → 0.3 → 1 (blink, 1000ms)
- ✓ Magnifier: Scales 1.0 → 1.5 → 1.0 (600ms)
- ✓ FIND card: Scales 1.05 → 1.0 (400ms)
- ✓ Checkmark: Scales 1.3 → 1.0 (200ms)

---

## 📐 Spacing & Padding

### MasterChef
- Screen padding: 16dp
- Card margins: 8-12dp
- Item margins: 4dp
- Internal padding: 8-16dp

### Detective
- Screen padding: 16dp
- Card margins: 12dp
- Button margins: 8dp
- Internal padding: 8-16dp

---

## 🎯 Touch Targets

All interactive elements ≥ 48dp for easy touch:
- ✓ Speaker buttons: 48dp
- ✓ Hint buttons: 48dp+ (with padding)
- ✓ Ingredients: 80dp
- ✓ Hidden objects: 50-80dp

---

Xem chi tiết implementation trong code! 🚀
