# 🎬 MasterChef Interactive Game - Video Script

## Scene-by-Scene Breakdown (30 giây cycle)

---

### 🎬 SCENE 1: Customer Orders (0:00 - 0:03)

**Visual:**
```
╔═══════════════════════════════════════╗
║    ┌─────────────────────────────┐   ║
║    │ I want order fried chicken │   ║ ← Fade In
║    └──────────┬──────────────────┘   ║
║               ↓                      ║
║           👤 [CUSTOMER]              ║
║         (stands in center)           ║
╚═══════════════════════════════════════╝
```

**Actions:**
- Speech bubble fades in (300ms)
- TTS speaks: "I want order fried chicken"
- Customer stands still, looking expectant

**Duration:** 3 seconds

---

### 🎬 SCENE 2: Chef Acknowledges (0:03 - 0:06)

**Visual:**
```
╔═══════════════════════════════════════╗
║  ┌───────────────────┐                ║
║  │ Waiting five      │                ║ ← Fade In
║  │ minutes           │                ║
║  └─────┬─────────────┘                ║
║        ↓                              ║
║   👨‍🍳 [CHEF]        [STOVE AREA]     ║
║  (left side)         (right side)     ║
╚═══════════════════════════════════════╝
```

**Actions:**
- Customer's bubble fades out
- Chef's bubble fades in
- TTS speaks: "Waiting five minutes"
- Chef nods (subtle animation)

**Duration:** 3 seconds

---

### 🎬 SCENE 3: Pan Placement (0:06 - 0:08)

**Visual:**
```
╔═══════════════════════════════════════╗
║                                       ║
║   👨‍🍳 Chef                             ║
║                    ┌──────┐          ║
║                    │  🍳  │ ← Scale Up║
║                    │ PAN  │           ║
║                    └──────┘          ║
║                   [ON STOVE]          ║
╚═══════════════════════════════════════╝
```

**Actions:**
- Chef's bubble fades out
- Pan appears (alpha 0 → 1)
- Pan scales from 0.5x to 1.0x
- Smooth animation (500ms)

**Duration:** 2 seconds

---

### 🎬 SCENE 4: Request Chicken (0:08 - 0:11 + player time)

**Visual:**
```
╔═══════════════════════════════════════╗
║  ┌──────────────┐                    ║
║  │ I need       │                    ║ ← Fade In
║  │ chicken      │                    ║
║  └────┬─────────┘                    ║
║       ↓                              ║
║   👨‍🍳 Chef ← [DROP HERE]   🍳 Pan   ║
║       ↑                              ║
║       └── Drag from below ──┘        ║
║                                      ║
║    🥘 Ingredients                    ║
║  ┌────┬────┬────┬────┐              ║
║  │[🍗]│ 🛢️ │ 🧂 │ 🌶️ │ ← Highlight  ║
║  │ 🍞 │ 🧀 │ 🥚 │ 🍅 │              ║
║  └────┴────┴────┴────┘              ║
╚═══════════════════════════════════════╝
```

**Actions:**
- Chef's bubble appears: "I need chicken"
- TTS speaks the request
- Chicken icon highlights (subtle glow)
- **WAITING FOR PLAYER INPUT**

**Player Action:**
1. Touch chicken icon (🍗)
2. Drag upward
3. Shadow follows cursor
4. Hover over chef → Chef scales to 1.1x
5. Release → Drop!

**On Success:**
- Chef scales 1.0x → 1.15x → 1.0x (bounce)
- Toast appears: "Perfect! ✓"
- Bubble fades out

**Duration:** Variable (waits for player)

---

### 🎬 SCENE 5: Request Oil (Player time + 1.5s)

**Visual:**
```
╔═══════════════════════════════════════╗
║  ┌──────────────┐                    ║
║  │ I need some  │                    ║
║  │ oil          │                    ║
║  └────┬─────────┘                    ║
║       ↓                              ║
║   👨‍🍳 Chef ← [DROP HERE]   🍳 Pan   ║
║       ↑                              ║
║       └── Drag from below ──┘        ║
║                                      ║
║    🥘 Ingredients                    ║
║  ┌────┬────┬────┬────┐              ║
║  │ 🍗 │[🛢️]│ 🧂 │ 🌶️ │ ← Highlight  ║
║  │ 🍞 │ 🧀 │ 🥚 │ 🍅 │              ║
║  └────┴────┴────┴────┘              ║
╚═══════════════════════════════════════╝
```

**Actions:**
- Similar to Scene 4
- Oil icon (🛢️) highlighted
- Player drags oil to chef
- Same success feedback

**Duration:** Variable + 1.5s delay

---

### 🎬 SCENE 6: Cooking (Time + 3s)

**Visual:**
```
╔═══════════════════════════════════════╗
║                                       ║
║   👨‍🍳 Chef                             ║
║                    ┌──────┐          ║
║                    │  🍳  │ ← Wobble ║
║                    │  🔥  │          ║
║                    └──────┘          ║
║                  [COOKING]            ║
║               (rotation anim)         ║
╚═══════════════════════════════════════╝
```

**Actions:**
- Speech bubble disappears
- Fire emoji (🔥) appears on stove
- Pan rotates: 0° → 5° → -5° → 0° (repeat 5x)
- Duration: 500ms per cycle × 5 = 2.5s
- Total cooking time: 3s

**Duration:** 3 seconds

---

### 🎬 SCENE 7: Chef Done (Time + 2s)

**Visual:**
```
╔═══════════════════════════════════════╗
║  ┌──────────────┐                    ║
║  │ Wow, yummy   │                    ║
║  │ yummy        │                    ║
║  └────┬─────────┘                    ║
║       ↓                              ║
║   👨‍🍳 Chef          [PAN FADES]     ║
║                                      ║
║         (pan disappears)             ║
╚═══════════════════════════════════════╝
```

**Actions:**
- Fire icon disappears
- Chef's bubble appears: "Wow, yummy yummy"
- TTS speaks with emotion
- Pan fades out (alpha 1 → 0, 300ms)

**Duration:** 2 seconds

---

### 🎬 SCENE 8: Serving (Time + 1s)

**Visual:**
```
╔═══════════════════════════════════════╗
║                                       ║
║           👤 Customer                 ║
║         (waiting eagerly)             ║
║              ↑                       ║
║              │                       ║
║           [🍗🍽️]                     ║
║        (dish moves up)                ║
║              │                       ║
║         👨‍🍳 Chef                      ║
║        (serving motion)               ║
╚═══════════════════════════════════════╝
```

**Actions:**
- Virtual dish moves from chef to customer
- Simple fade/scale animation

**Duration:** 1 second

---

### 🎬 SCENE 9: Customer Thanks (Time + 4s)

**Visual:**
```
╔═══════════════════════════════════════╗
║    ┌─────────────────────────────┐   ║
║    │ Thank you very much        │   ║
║    └──────────┬──────────────────┘   ║
║               ↓                      ║
║           👤 [CUSTOMER]              ║
║         (happy animation)            ║
║            ⭐ +1                     ║
║                                      ║
║    🎉 "Delicious!" 🎉               ║
╚═══════════════════════════════════════╝
```

**Actions:**
- Customer's bubble appears: "Thank you very much"
- TTS speaks with gratitude tone
- Customer bounces (scale 1.0x → 1.2x → 1.0x)
- Score increases: "⭐ 5" → "⭐ 6"
- Toast appears: "🎉 Delicious! 🎉"

**Duration:** 4 seconds

---

### 🎬 SCENE 10: Transition to New Round (Time + smooth fade)

**Visual:**
```
╔═══════════════════════════════════════╗
║                                       ║
║         (brief pause)                 ║
║                                       ║
║    ⭐ Score: 6                        ║
║                                       ║
║  [Everything resets smoothly]         ║
║                                       ║
╚═══════════════════════════════════════╝
```

**Actions:**
- All speech bubbles fade out
- Pan gone, fire gone
- Scene resets
- Loop back to Scene 1

**Duration:** Instant transition

---

## 🎥 Camera Angles & Focus

### Wide Shot (Default):
- Shows all elements
- Customer at top
- Chef and stove in middle
- Ingredients at bottom

### Close-up Moments:
- Chef's speech bubble (readable text)
- Ingredient being dragged (follow shadow)
- Cooking animation (fire + pan wobble)
- Customer's thank you (emotional impact)

## 🎨 Visual Effects

### Fade Effects:
- Speech bubbles: 300ms fade in/out
- Pan appearance: 500ms
- Element transitions: 300ms

### Scale Effects:
- Chef receive: 1.0x → 1.15x → 1.0x (150ms each)
- Customer happy: 1.0x → 1.2x → 1.0x (300ms each)
- Drag hover: 1.0x → 1.1x (200ms)

### Motion Effects:
- Pan wobble: ±5° rotation
- Shake (error): ±25px horizontal
- Ingredient drag: shadow follows

## 🎵 Audio Cues

### Voice Lines (TTS):
1. "I want order fried chicken" - Customer voice
2. "Waiting five minutes" - Chef voice
3. "I need chicken" - Chef voice
4. "I need some oil" - Chef voice
5. "Wow, yummy yummy" - Chef voice (excited)
6. "Thank you very much" - Customer voice (grateful)

### Sound Effects (Optional):
- 🔔 Ding when customer orders
- 🔪 Chop when placing ingredient
- 🔥 Sizzle during cooking
- ✨ Success chime when done
- 🎉 Celebration fanfare for score

## 📊 Timing Summary

| Scene | Duration | Cumulative |
|-------|----------|------------|
| 1. Customer Orders | 3s | 3s |
| 2. Chef Acknowledges | 3s | 6s |
| 3. Pan Placement | 2s | 8s |
| 4. Need Chicken | ~5s | ~13s |
| 5. Need Oil | ~5s | ~18s |
| 6. Cooking | 3s | ~21s |
| 7. Chef Done | 2s | ~23s |
| 8. Serving | 1s | ~24s |
| 9. Customer Thanks | 4s | ~28s |
| 10. Transition | 1s | ~29s |
| **TOTAL** | **~29s** | **One cycle** |

## 🎬 Director's Notes

### Pacing:
- Keep dialogue clear and spaced
- Allow time to read speech bubbles
- Don't rush cooking animation
- Celebrate successes visibly

### Emotional Beats:
- Customer: Expectant → Happy
- Chef: Professional → Proud → Satisfied
- Player: Engaged → Successful → Rewarded

### Educational Value:
- Clear English pronunciation
- Visible text for reading
- Logical sequence of events
- Positive reinforcement

## 🎯 Success Criteria

A good playthrough should have:
- ✅ All dialogue audible and clear
- ✅ Smooth transitions between scenes
- ✅ No dead time or confusion
- ✅ Clear visual feedback
- ✅ Satisfying completion feeling
- ✅ Desire to play again!

---

**🎬 End of Script**

*This interactive cooking game creates a delightful, educational experience that teaches English vocabulary, sequencing, and following instructions - all while having fun! 👨‍🍳✨*
