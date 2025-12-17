package com.edu.kidsapp;

import android.app.AlertDialog;
import android.content.ClipData;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * WordWorkshopFragment - Spelling game with drag & drop letters
 * 
 * CORE MECHANIC:
 * User sees an image (e.g., Apple) and must drag scrambled letters into empty slots
 * to form the correct word "APPLE".
 * 
 * TECHNICAL HIGHLIGHTS:
 * - Dynamic UI generation (works with any word length)
 * - FlexboxLayout for responsive letter grid (auto-wraps to new lines)
 * - setTag() pattern for clean letter-to-slot mapping
 * - Drag & Drop API for intuitive interactions
 */
public class WordWorkshopFragment extends Fragment {

    // UI Components
    private ImageView imgTargetWord;
    private TextView tvWordLabel;
    private LinearLayout layoutSlotsContainer;
    private FlexboxLayout layoutSourceLetters;

    // Game Data
    private String targetWord = "APPLE"; // Hardcoded for now, can be passed via Bundle
    private char[] wordChars;
    private List<TextView> slotViews = new ArrayList<>();
    private List<TextView> sourceLetterViews = new ArrayList<>();
    private int filledSlots = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_word_workshop, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize views
        imgTargetWord = view.findViewById(R.id.img_target_word);
        tvWordLabel = view.findViewById(R.id.tv_word_label);
        layoutSlotsContainer = view.findViewById(R.id.layout_slots_container);
        layoutSourceLetters = view.findViewById(R.id.layout_source_letters);

        // Setup game
        setupGame();
    }

    /**
     * Setup the spelling game - Main orchestrator method
     * Converts target word to char array and generates dynamic UI
     */
    private void setupGame() {
        // Convert word to char array
        wordChars = targetWord.toCharArray();
        
        // Reset counters
        filledSlots = 0;

        // Update UI hint
        tvWordLabel.setText("Spell: " + targetWord);

        // Generate slots (empty drop targets)
        generateSlots();

        // Generate scrambled source letters (draggable tiles)
        generateSourceLetters();
    }

    /**
     * STEP B1: Generate empty slots for each letter in the word
     * 
     * KEY TECHNIQUE: setTag(expectedChar)
     * Each slot stores which letter it expects via Tag.
     * This eliminates need for complex mapping logic.
     */
    private void generateSlots() {
        slotViews.clear();
        layoutSlotsContainer.removeAllViews();

        for (char expectedChar : wordChars) {
            // Create TextView for each slot
            TextView slotView = new TextView(getContext());
            
            // Style: Dashed border empty slot
            slotView.setBackgroundResource(R.drawable.bg_slot_empty);
            slotView.setTextSize(24);
            slotView.setTextColor(getResources().getColor(R.color.text_dark, null));
            slotView.setGravity(android.view.Gravity.CENTER);
            
            // Size: 80x80dp square
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    (int) (80 * getResources().getDisplayMetrics().density),
                    (int) (80 * getResources().getDisplayMetrics().density)
            );
            params.setMargins(8, 0, 8, 0);
            slotView.setLayoutParams(params);
            slotView.setPadding(16, 16, 16, 16);

            // CRUCIAL: Store expected letter in Tag for validation later
            slotView.setTag(String.valueOf(expectedChar));

            // Attach drag listener to handle drops
            slotView.setOnDragListener(slotDragListener);

            // Add to container
            layoutSlotsContainer.addView(slotView);
            slotViews.add(slotView);
        }
    }

    /**
     * STEP B2: Generate scrambled source letters for dragging
     * 
     * KEY TECHNIQUE: Collections.shuffle()
     * Randomizes letter order to create the puzzle challenge.
     * FlexboxLayout automatically wraps to new lines if word is long.
     */
    private void generateSourceLetters() {
        sourceLetterViews.clear();
        layoutSourceLetters.removeAllViews();

        // Create list of characters and shuffle them
        List<Character> letters = new ArrayList<>();
        for (char c : wordChars) {
            letters.add(c);
        }
        Collections.shuffle(letters);

        // Create TextView for each letter
        for (char letter : letters) {
            TextView letterView = new TextView(getContext());
            
            // Style: White tile with grey border
            letterView.setBackgroundResource(R.drawable.bg_letter_tile);
            letterView.setText(String.valueOf(letter));
            letterView.setTextSize(24);
            letterView.setTextColor(getResources().getColor(R.color.text_dark, null));
            letterView.setGravity(android.view.Gravity.CENTER);
            
            // Size: 80x80dp square
            FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(
                    (int) (80 * getResources().getDisplayMetrics().density),
                    (int) (80 * getResources().getDisplayMetrics().density)
            );
            params.setMargins(8, 8, 8, 8);
            letterView.setLayoutParams(params);
            letterView.setPadding(16, 16, 16, 16);

            // CRUCIAL: Store letter in Tag for drag data
            letterView.setTag(String.valueOf(letter));

            // Attach touch listener to start drag
            letterView.setOnTouchListener(sourceTouchListener);

            // Add to FlexboxLayout (auto-wraps if needed)
            layoutSourceLetters.addView(letterView);
            sourceLetterViews.add(letterView);
        }
    }

    /**
     * STEP C1: Touch listener for source letters - Initiates drag operation
     * 
     * When user touches a letter tile, this starts the drag with ClipData
     * containing the letter character.
     */
    private final View.OnTouchListener sourceTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                // Get the letter from tag
                String letter = (String) view.getTag();

                // Create ClipData with the letter (data payload for drag)
                ClipData clipData = ClipData.newPlainText("letter", letter);

                // Start drag operation with shadow
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDragAndDrop(clipData, shadowBuilder, view, 0);

                return true;
            }
            return false;
        }
    };

    /**
     * STEP C2: Drag listener for slots - Handles drop validation
     * 
     * This is the CORE LOGIC:
     * 1. Extract dragged letter from ClipData
     * 2. Extract expected letter from Slot's Tag
     * 3. Compare: If match -> Success (green, hide source, check win)
     *             If wrong -> Feedback (toast, could add shake)
     */
    private final View.OnDragListener slotDragListener = new View.OnDragListener() {
        @Override
        public boolean onDrag(View view, DragEvent dragEvent) {
            switch (dragEvent.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    // Indicate this view accepts drops
                    return true;

                case DragEvent.ACTION_DRAG_ENTERED:
                    // Visual feedback when dragging over (optional: scale up)
                    view.setAlpha(0.7f);
                    return true;

                case DragEvent.ACTION_DRAG_EXITED:
                    // Reset visual feedback
                    view.setAlpha(1.0f);
                    return true;

                case DragEvent.ACTION_DROP:
                    // Reset alpha
                    view.setAlpha(1.0f);
                    
                    // Get dragged letter from ClipData
                    ClipData.Item item = dragEvent.getClipData().getItemAt(0);
                    String draggedLetter = item.getText().toString();

                    // Get expected letter from slot's Tag
                    String expectedLetter = (String) view.getTag();

                    TextView slotView = (TextView) view;

                    // VALIDATION LOGIC
                    if (draggedLetter.equals(expectedLetter)) {
                        // ✅ CORRECT PLACEMENT
                        
                        // Change slot to green success state
                        slotView.setBackgroundResource(R.drawable.bg_slot_success);
                        slotView.setText(draggedLetter);
                        slotView.setTextColor(getResources().getColor(android.R.color.white, null));

                        // Find and hide the source letter view (it's been "used")
                        View localState = (View) dragEvent.getLocalState();
                        if (localState != null) {
                            localState.setVisibility(View.INVISIBLE);
                        }

                        // Increment filled slots counter
                        filledSlots++;

                        // Placeholder for success sound (could add MediaPlayer here)
                        Toast.makeText(getContext(), "✓ Correct!", Toast.LENGTH_SHORT).show();

                        // Check if all slots are filled (win condition)
                        if (filledSlots == wordChars.length) {
                            onGameWon();
                        }

                    } else {
                        // ❌ WRONG PLACEMENT
                        Toast.makeText(getContext(), "Try again!", Toast.LENGTH_SHORT).show();
                        
                        // Optional: Add shake animation here for better UX
                        // view.animate().translationX(-10f).setDuration(100)...
                    }

                    return true;

                case DragEvent.ACTION_DRAG_ENDED:
                    // Reset any visual changes
                    view.setAlpha(1.0f);
                    return true;

                default:
                    return true;
            }
        }
    };

    /**
     * STEP D: Handle game win
     * 
     * Shows congratulations dialog with reward (+1 Ticket)
     * Could navigate to next level or back to lesson map
     */
    private void onGameWon() {
        new AlertDialog.Builder(getContext())
                .setTitle("🎉 Correct!")
                .setMessage("You spelled '" + targetWord + "' correctly!\n\n+1 Ticket 🎟️")
                .setPositiveButton("Continue", (dialog, which) -> {
                    // Navigate back or to next level
                    requireActivity().onBackPressed();
                })
                .setNegativeButton("Play Again", (dialog, which) -> {
                    // Reset game for replay
                    setupGame();
                })
                .setCancelable(false)
                .show();
    }
}
