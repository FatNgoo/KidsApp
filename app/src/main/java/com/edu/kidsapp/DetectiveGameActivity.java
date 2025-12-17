package com.edu.kidsapp;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * DetectiveGameActivity - Hidden Object Game Redesigned
 * Beautiful, colorful detective-themed game where players find hidden objects
 */
public class DetectiveGameActivity extends AppCompatActivity {

    // UI Components
    private ImageView imgCurrentObject, imgScene;
    private TextView tvCurrentObjectName, tvProgress, tvHintCount, tvMagnifierCount;
    private ImageButton btnSpeakObject;
    private CardView btnHint, btnMagnifier, btnSkip;
    private FrameLayout sceneContainer;
    private LinearLayout objectListContainer;

    // Game State
    private List<HiddenObject> allObjects = new ArrayList<>();
    private List<HiddenObject> objectsToFind = new ArrayList<>();
    private HiddenObject currentTarget;
    private int foundCount = 0;
    private int hintCount = 3;
    private int magnifierCount = 2;
    private Random random = new Random();

    // Text to Speech
    private TextToSpeech textToSpeech;
    private boolean ttsReady = false;

    // Available objects database
    private static final ObjectData[] OBJECT_DATABASE = {
            new ObjectData("KEY", "Key", "🔑", R.drawable.clue_key),
            new ObjectData("MAGNIFIER", "Magnifier", "🔍", R.drawable.clue_magnifier),
            new ObjectData("FOOTPRINT", "Footprint", "👣", R.drawable.clue_footprint),
            new ObjectData("WATCH", "Watch", "⌚", R.drawable.ic_clue_placeholder),
            new ObjectData("COIN", "Coin", "🪙", R.drawable.ic_clue_placeholder),
            new ObjectData("BOOK", "Book", "📖", R.drawable.ic_clue_placeholder),
            new ObjectData("HAT", "Hat", "🎩", R.drawable.ic_clue_placeholder),
            new ObjectData("PHONE", "Phone", "📱", R.drawable.ic_clue_placeholder)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detective_game);

        // Hide action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Initialize TTS
        initTextToSpeech();

        // Initialize views
        initializeViews();

        // Setup hint buttons
        setupHintButtons();

        // Start game
        startNewGame();
    }

    /**
     * Initialize text to speech
     */
    private void initTextToSpeech() {
        textToSpeech = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                int result = textToSpeech.setLanguage(Locale.US);
                ttsReady = (result != TextToSpeech.LANG_MISSING_DATA && 
                           result != TextToSpeech.LANG_NOT_SUPPORTED);
            }
        });
    }

    /**
     * Initialize all views
     */
    private void initializeViews() {
        imgCurrentObject = findViewById(R.id.imgCurrentObject);
        tvCurrentObjectName = findViewById(R.id.tvCurrentObjectName);
        btnSpeakObject = findViewById(R.id.btnSpeakObject);
        imgScene = findViewById(R.id.imgScene);
        sceneContainer = findViewById(R.id.sceneContainer);
        tvProgress = findViewById(R.id.tvProgress);
        tvHintCount = findViewById(R.id.tvHintCount);
        tvMagnifierCount = findViewById(R.id.tvMagnifierCount);
        btnHint = findViewById(R.id.btnHint);
        btnMagnifier = findViewById(R.id.btnMagnifier);
        btnSkip = findViewById(R.id.btnSkip);
        objectListContainer = findViewById(R.id.objectListContainer);

        // Speaker button
        btnSpeakObject.setOnClickListener(v -> speakObjectName());
    }

    /**
     * Speak current object name using TTS
     */
    private void speakObjectName() {
        if (ttsReady && currentTarget != null) {
            textToSpeech.speak(currentTarget.data.displayName, TextToSpeech.QUEUE_FLUSH, null, null);
            // Animate button
            btnSpeakObject.animate().scaleX(0.8f).scaleY(0.8f).setDuration(100)
                    .withEndAction(() -> btnSpeakObject.animate().scaleX(1f).scaleY(1f).setDuration(100).start())
                    .start();
        }
    }

    /**
     * Setup hint button listeners
     */
    private void setupHintButtons() {
        // Hint: Highlight the current target
        btnHint.setOnClickListener(v -> {
            if (hintCount > 0 && currentTarget != null && !currentTarget.found) {
                hintCount--;
                tvHintCount.setText("💡 " + hintCount);
                highlightObject(currentTarget);
                Toast.makeText(this, "Look for the highlighted object!", Toast.LENGTH_SHORT).show();
            } else if (hintCount == 0) {
                Toast.makeText(this, "No hints left!", Toast.LENGTH_SHORT).show();
            }
        });

        // Magnifier: Zoom effect on current target
        btnMagnifier.setOnClickListener(v -> {
            if (magnifierCount > 0 && currentTarget != null && !currentTarget.found) {
                magnifierCount--;
                tvMagnifierCount.setText("🔍 " + magnifierCount);
                pulseObject(currentTarget);
                Toast.makeText(this, "Object is pulsing!", Toast.LENGTH_SHORT).show();
            } else if (magnifierCount == 0) {
                Toast.makeText(this, "No magnifiers left!", Toast.LENGTH_SHORT).show();
            }
        });

        // Skip: Skip current object
        btnSkip.setOnClickListener(v -> {
            if (currentTarget != null && !currentTarget.found) {
                new AlertDialog.Builder(this)
                        .setTitle("Skip Object?")
                        .setMessage("Skip " + currentTarget.data.displayName + "?\nYou won't get points for it.")
                        .setPositiveButton("Skip", (dialog, which) -> {
                            markAsSkipped(currentTarget);
                            pickNextTarget();
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        });
    }

    /**
     * Start a new game
     */
    private void startNewGame() {
        // Reset state
        allObjects.clear();
        objectsToFind.clear();
        foundCount = 0;
        hintCount = 3;
        magnifierCount = 2;

        // Pick 5 random objects
        List<ObjectData> shuffled = new ArrayList<>();
        for (ObjectData data : OBJECT_DATABASE) {
            shuffled.add(data);
        }
        java.util.Collections.shuffle(shuffled);

        for (int i = 0; i < Math.min(5, shuffled.size()); i++) {
            objectsToFind.add(new HiddenObject(shuffled.get(i)));
        }

        // Place objects randomly in scene
        placeObjectsInScene();

        // Build object list UI
        buildObjectList();

        // Pick first target
        pickNextTarget();

        // Update UI
        updateProgress();
        tvHintCount.setText("💡 " + hintCount);
        tvMagnifierCount.setText("🔍 " + magnifierCount);
    }

    /**
     * Place hidden objects randomly in the scene
     */
    private void placeObjectsInScene() {
        sceneContainer.post(() -> {
            int containerWidth = sceneContainer.getWidth();
            int containerHeight = sceneContainer.getHeight();

            for (HiddenObject obj : objectsToFind) {
                ImageView objView = new ImageView(this);
                objView.setImageResource(obj.data.drawableId);
                
                int size = 50 + random.nextInt(30); // 50-80dp
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(size, size);
                
                // Random position (with margins to avoid edges)
                params.leftMargin = 50 + random.nextInt(Math.max(1, containerWidth - 150));
                params.topMargin = 50 + random.nextInt(Math.max(1, containerHeight - 150));
                
                objView.setLayoutParams(params);
                objView.setOnClickListener(v -> onObjectClicked(obj));
                
                obj.view = objView;
                allObjects.add(obj);
                sceneContainer.addView(objView);
            }
        });
    }

    /**
     * Handle object click
     */
    private void onObjectClicked(HiddenObject obj) {
        if (obj.found || obj.skipped) return;

        if (obj == currentTarget) {
            // Correct object!
            obj.found = true;
            foundCount++;

            // Animate and hide
            obj.view.animate()
                    .scaleX(1.5f)
                    .scaleY(1.5f)
                    .alpha(0f)
                    .setDuration(300)
                    .withEndAction(() -> obj.view.setVisibility(View.GONE))
                    .start();

            // Update object list
            updateObjectInList(obj);

            // Show success
            Toast.makeText(this, "✓ Found " + obj.data.displayName + "!", Toast.LENGTH_SHORT).show();

            // Update progress
            updateProgress();

            // Check if game complete
            if (foundCount == objectsToFind.size()) {
                gameComplete();
            } else {
                // Pick next target
                sceneContainer.postDelayed(this::pickNextTarget, 500);
            }
        } else {
            // Wrong object
            Toast.makeText(this, "That's not the target!", Toast.LENGTH_SHORT).show();
            shakeView(obj.view);
        }
    }

    /**
     * Pick next target to find
     */
    private void pickNextTarget() {
        // Find an unfound object
        for (HiddenObject obj : objectsToFind) {
            if (!obj.found && !obj.skipped) {
                currentTarget = obj;
                imgCurrentObject.setImageResource(obj.data.drawableId);
                tvCurrentObjectName.setText(obj.data.displayName);
                
                // Animate card
                findViewById(R.id.cardFind).animate()
                        .scaleX(1.05f).scaleY(1.05f)
                        .setDuration(200)
                        .withEndAction(() -> 
                                findViewById(R.id.cardFind).animate()
                                        .scaleX(1f).scaleY(1f)
                                        .setDuration(200).start()
                        ).start();
                return;
            }
        }
    }

    /**
     * Mark object as skipped
     */
    private void markAsSkipped(HiddenObject obj) {
        obj.skipped = true;
        if (obj.view != null) {
            obj.view.setAlpha(0.3f);
        }
        updateObjectInList(obj);
        updateProgress();
    }

    /**
     * Build the object list UI
     */
    private void buildObjectList() {
        objectListContainer.removeAllViews();

        for (HiddenObject obj : objectsToFind) {
            View itemView = createObjectListItem(obj);
            obj.listItemView = itemView;
            objectListContainer.addView(itemView);
        }
    }

    /**
     * Create object list item
     */
    private View createObjectListItem(HiddenObject obj) {
        View itemView = LayoutInflater.from(this).inflate(
                R.layout.item_detective_object, objectListContainer, false);

        ImageView imgObject = itemView.findViewById(R.id.imgObject);
        TextView tvObjectName = itemView.findViewById(R.id.tvObjectName);
        ImageView imgFoundCheck = itemView.findViewById(R.id.imgFoundCheck);

        imgObject.setImageResource(obj.data.drawableId);
        tvObjectName.setText(obj.data.displayName);
        imgFoundCheck.setVisibility(View.GONE);

        return itemView;
    }

    /**
     * Update object in list when found or skipped
     */
    private void updateObjectInList(HiddenObject obj) {
        if (obj.listItemView != null) {
            ImageView imgFoundCheck = obj.listItemView.findViewById(R.id.imgFoundCheck);
            
            if (obj.found) {
                imgFoundCheck.setVisibility(View.VISIBLE);
                obj.listItemView.setBackgroundResource(R.drawable.bg_object_item_found);
                imgFoundCheck.animate()
                        .scaleX(1.3f).scaleY(1.3f)
                        .setDuration(200)
                        .withEndAction(() -> imgFoundCheck.animate().scaleX(1f).scaleY(1f).setDuration(100).start())
                        .start();
            } else if (obj.skipped) {
                obj.listItemView.setAlpha(0.5f);
            }
        }
    }

    /**
     * Update progress display
     */
    private void updateProgress() {
        tvProgress.setText(foundCount + "/" + objectsToFind.size());
    }

    /**
     * Highlight object (hint effect)
     */
    private void highlightObject(HiddenObject obj) {
        if (obj.view != null) {
            ObjectAnimator.ofFloat(obj.view, "alpha", 1f, 0.3f, 1f, 0.3f, 1f)
                    .setDuration(1000)
                    .start();
        }
    }

    /**
     * Pulse object (magnifier effect)
     */
    private void pulseObject(HiddenObject obj) {
        if (obj.view != null) {
            obj.view.animate()
                    .scaleX(1.5f).scaleY(1.5f)
                    .setDuration(300)
                    .withEndAction(() -> 
                            obj.view.animate()
                                    .scaleX(1f).scaleY(1f)
                                    .setDuration(300)
                                    .start()
                    ).start();
        }
    }

    /**
     * Shake view animation
     */
    private void shakeView(View view) {
        ObjectAnimator.ofFloat(view, "translationX", 0f, -15f, 15f, -15f, 15f, 0f)
                .setDuration(400)
                .start();
    }

    /**
     * Game complete
     */
    private void gameComplete() {
        new AlertDialog.Builder(this)
                .setTitle("🎉 Case Solved!")
                .setMessage("You found " + foundCount + " out of " + objectsToFind.size() + " objects!\n\nExcellent detective work!")
                .setPositiveButton("Play Again", (dialog, which) -> startNewGame())
                .setNegativeButton("Exit", (dialog, which) -> finish())
                .setCancelable(false)
                .show();
    }

    @Override
    protected void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }

    /**
     * Hidden object in scene
     */
    private static class HiddenObject {
        ObjectData data;
        ImageView view;
        View listItemView;
        boolean found = false;
        boolean skipped = false;

        HiddenObject(ObjectData data) {
            this.data = data;
        }
    }

    /**
     * Object data class
     */
    private static class ObjectData {
        String id;
        String displayName;
        String emoji;
        int drawableId;

        ObjectData(String id, String displayName, String emoji, int drawableId) {
            this.id = id;
            this.displayName = displayName;
            this.emoji = emoji;
            this.drawableId = drawableId;
        }
    }
}
