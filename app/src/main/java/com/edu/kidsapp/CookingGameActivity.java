package com.edu.kidsapp;

import android.animation.ObjectAnimator;
import android.content.ClipData;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

/**
 * CookingGameActivity - Master Chef Drag & Drop Game
 * User drags food items into pot to match customer orders
 */
public class CookingGameActivity extends AppCompatActivity {

    // UI Components
    private ImageView imgPot, imgApple, imgBanana, imgCabbage;
    private TextView tvOrder, tvScore;

    // Game State
    private String targetIngredient = "APPLE";
    private int score = 0;
    private String[] ingredients = {"APPLE", "BANANA", "CABBAGE"};
    private String[] ingredientEmojis = {"🍎", "🍌", "🥬"};
    private Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooking_game);

        // Hide action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Initialize views
        initializeViews();

        // Setup drag listeners for food items
        setupDragListeners();

        // Setup drop listener for pot
        setupDropListener();

        // Set initial order
        updateOrder();
    }

    /**
     * Initialize all views
     */
    private void initializeViews() {
        imgPot = findViewById(R.id.img_pot);
        imgApple = findViewById(R.id.img_apple);
        imgBanana = findViewById(R.id.img_banana);
        imgCabbage = findViewById(R.id.img_cabbage);
        tvOrder = findViewById(R.id.tvOrder);
        tvScore = findViewById(R.id.tvScore);
    }

    /**
     * Setup touch listeners for draggable food items
     */
    private void setupDragListeners() {
        View.OnTouchListener dragListener = (view, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                // Get the tag (ingredient name)
                String tag = (String) view.getTag();
                
                // Create ClipData with the tag
                ClipData data = ClipData.newPlainText("ingredient", tag);
                
                // Start drag and drop
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDragAndDrop(data, shadowBuilder, view, 0);
                
                return true;
            }
            return false;
        };

        // Apply listener to all food items
        imgApple.setOnTouchListener(dragListener);
        imgBanana.setOnTouchListener(dragListener);
        imgCabbage.setOnTouchListener(dragListener);
    }

    /**
     * Setup drop listener for the pot
     */
    private void setupDropListener() {
        imgPot.setOnDragListener((view, event) -> {
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    return true;

                case DragEvent.ACTION_DRAG_ENTERED:
                    // Visual feedback: scale up pot slightly
                    view.animate().scaleX(1.1f).scaleY(1.1f).setDuration(200).start();
                    return true;

                case DragEvent.ACTION_DRAG_EXITED:
                    // Reset pot scale
                    view.animate().scaleX(1.0f).scaleY(1.0f).setDuration(200).start();
                    return true;

                case DragEvent.ACTION_DROP:
                    // Reset pot scale
                    view.animate().scaleX(1.0f).scaleY(1.0f).setDuration(200).start();
                    
                    // Get the dropped ingredient
                    ClipData.Item item = event.getClipData().getItemAt(0);
                    String droppedIngredient = item.getText().toString();

                    // Check if it matches the target
                    if (droppedIngredient.equals(targetIngredient)) {
                        handleCorrectDrop();
                    } else {
                        handleWrongDrop();
                    }
                    return true;

                case DragEvent.ACTION_DRAG_ENDED:
                    // Reset pot scale in case drag was cancelled
                    view.animate().scaleX(1.0f).scaleY(1.0f).setDuration(200).start();
                    return true;

                default:
                    return false;
            }
        });
    }

    /**
     * Handle correct ingredient drop
     */
    private void handleCorrectDrop() {
        // Show success message
        Toast.makeText(this, "Yummy! Correct! 😋", Toast.LENGTH_SHORT).show();

        // Increase score
        score++;
        tvScore.setText("Score: " + score);

        // Play cooking animation (scale pulse)
        animateCooking();

        // Pick new random target
        pickNewTarget();
        updateOrder();
    }

    /**
     * Handle wrong ingredient drop
     */
    private void handleWrongDrop() {
        // Show error message
        Toast.makeText(this, "Eww! Wrong ingredient! 😖", Toast.LENGTH_SHORT).show();

        // Shake the pot
        animateShake();
    }

    /**
     * Pick a new random target ingredient
     */
    private void pickNewTarget() {
        targetIngredient = ingredients[random.nextInt(ingredients.length)];
    }

    /**
     * Update the order display
     */
    private void updateOrder() {
        String emoji = "";
        switch (targetIngredient) {
            case "APPLE":
                emoji = "🍎";
                break;
            case "BANANA":
                emoji = "🍌";
                break;
            case "CABBAGE":
                emoji = "🥬";
                break;
        }
        tvOrder.setText("I want a " + targetIngredient + "! " + emoji);
    }

    /**
     * Animate pot when correct ingredient is dropped (cooking effect)
     */
    private void animateCooking() {
        imgPot.animate()
                .scaleX(1.2f)
                .scaleY(1.2f)
                .setDuration(200)
                .withEndAction(() -> 
                    imgPot.animate()
                            .scaleX(1.0f)
                            .scaleY(1.0f)
                            .setDuration(200)
                            .start()
                )
                .start();
    }

    /**
     * Animate pot shake when wrong ingredient is dropped
     */
    private void animateShake() {
        ObjectAnimator shake = ObjectAnimator.ofFloat(imgPot, "translationX", 0f, -25f, 25f, -25f, 25f, 0f);
        shake.setDuration(400);
        shake.start();
    }
}
