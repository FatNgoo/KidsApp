package com.edu.kidsapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.ClipData;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.speech.tts.TextToSpeech;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * CookingGameActivity - Interactive MasterChef Game
 * Features chef-customer dialogue and step-by-step cooking process
 */
public class CookingGameActivity extends AppCompatActivity {

    // Cooking Steps
    private enum CookingStep {
        CUSTOMER_ORDER,      // Customer orders food
        CHEF_ACKNOWLEDGE,    // Chef responds "Waiting five minutes"
        CHEF_NEED_PAN,       // Chef puts pan on stove
        CHEF_NEED_CHICKEN,   // Chef requests chicken
        CHEF_NEED_OIL,       // Chef requests oil
        COOKING,             // Chef is cooking
        CHEF_DONE,           // Chef says "Wow, yummy yummy"
        CUSTOMER_THANKS      // Customer says "Thank you very much"
    }

    // UI Components
    private ImageView imgCustomer, imgChef, imgPan, imgStove;
    private TextView tvScore, tvCookingStatus;
    private FrameLayout customerSpeechBubble, chefSpeechBubble;
    private TextView tvCustomerSpeech, tvChefSpeech;
    private GridLayout ingredientGrid;

    // Game State
    private CookingStep currentStep = CookingStep.CUSTOMER_ORDER;
    private String currentDish = "Fried Chicken";
    private int score = 0;
    private List<String> collectedIngredients = new ArrayList<>();

    // Text to Speech
    private TextToSpeech textToSpeech;
    private boolean ttsReady = false;

    // Handler for delays
    private Handler handler = new Handler(Looper.getMainLooper());

    // All available ingredients
    private static final Ingredient[] ALL_INGREDIENTS = {
            new Ingredient("CHICKEN", "Chicken", "🍗", R.drawable.food_apple),
            new Ingredient("OIL", "Oil", "🛢️", R.drawable.food_banana),
            new Ingredient("SALT", "Salt", "🧂", R.drawable.food_cabbage),
            new Ingredient("PEPPER", "Pepper", "🌶️", R.drawable.food_apple),
            new Ingredient("BREAD", "Bread", "🍞", R.drawable.food_banana),
            new Ingredient("CHEESE", "Cheese", "🧀", R.drawable.food_cabbage),
            new Ingredient("EGG", "Egg", "🥚", R.drawable.food_apple),
            new Ingredient("TOMATO", "Tomato", "🍅", R.drawable.food_banana)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooking_game);

        // Hide action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Initialize text to speech
        initTextToSpeech();

        // Initialize views
        initializeViews();

        // Setup ingredient grid
        setupIngredientGrid();

        // Start cooking sequence
        startCookingSequence();
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
        imgCustomer = findViewById(R.id.imgCustomer);
        imgChef = findViewById(R.id.imgChef);
        imgPan = findViewById(R.id.imgPan);
        imgStove = findViewById(R.id.imgStove);
        tvScore = findViewById(R.id.tvScore);
        tvCookingStatus = findViewById(R.id.tvCookingStatus);
        
        customerSpeechBubble = findViewById(R.id.customerSpeechBubble);
        chefSpeechBubble = findViewById(R.id.chefSpeechBubble);
        tvCustomerSpeech = findViewById(R.id.tvCustomerSpeech);
        tvChefSpeech = findViewById(R.id.tvChefSpeech);
        
        ingredientGrid = findViewById(R.id.ingredientGrid);
    }

    /**
     * Setup ingredient grid with drag functionality
     */
    private void setupIngredientGrid() {
        ingredientGrid.removeAllViews();
        
        for (Ingredient ingredient : ALL_INGREDIENTS) {
            View itemView = createIngredientItem(ingredient);
            ingredientGrid.addView(itemView);
        }
    }

    /**
     * Create an ingredient item view
     */
    private View createIngredientItem(Ingredient ingredient) {
        View itemView = LayoutInflater.from(this).inflate(
                R.layout.item_ingredient, ingredientGrid, false);
        
        ImageView imgIngredient = itemView.findViewById(R.id.imgIngredient);
        TextView tvIngredientName = itemView.findViewById(R.id.tvIngredientName);
        
        imgIngredient.setImageResource(ingredient.drawableId);
        tvIngredientName.setText(ingredient.displayName);
        
        // Set tag for drag and drop
        itemView.setTag(ingredient.id);
        
        // Setup drag listener
        itemView.setOnTouchListener((view, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("ingredient", ingredient.id);
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDragAndDrop(data, shadowBuilder, view, 0);
                return true;
            }
            return false;
        });
        
        return itemView;
    }

    /**
     * Start the cooking sequence
     */
    private void startCookingSequence() {
        currentStep = CookingStep.CUSTOMER_ORDER;
        collectedIngredients.clear();
        
        // Step 1: Customer orders
        showCustomerDialogue("I want order " + currentDish);
        speakText("I want order " + currentDish);
        
        // Move to next step after 3 seconds
        handler.postDelayed(() -> {
            currentStep = CookingStep.CHEF_ACKNOWLEDGE;
            showChefDialogue("Waiting five minutes");
            speakText("Waiting five minutes");
            
            // Move to pan placement
            handler.postDelayed(() -> {
                currentStep = CookingStep.CHEF_NEED_PAN;
                placePanOnStove();
                
                // Request chicken
                handler.postDelayed(() -> {
                    currentStep = CookingStep.CHEF_NEED_CHICKEN;
                    showChefDialogue("I need chicken");
                    speakText("I need chicken");
                    enableIngredientDragDrop("CHICKEN");
                }, 2000);
            }, 3000);
        }, 3000);
    }

    /**
     * Show customer dialogue in speech bubble
     */
    private void showCustomerDialogue(String message) {
        tvCustomerSpeech.setText(message);
        customerSpeechBubble.setVisibility(View.VISIBLE);
        customerSpeechBubble.setAlpha(0f);
        customerSpeechBubble.animate().alpha(1f).setDuration(300).start();
    }

    /**
     * Hide customer dialogue
     */
    private void hideCustomerDialogue() {
        customerSpeechBubble.animate().alpha(0f).setDuration(300)
            .withEndAction(() -> customerSpeechBubble.setVisibility(View.GONE))
            .start();
    }

    /**
     * Show chef dialogue in speech bubble
     */
    private void showChefDialogue(String message) {
        tvChefSpeech.setText(message);
        chefSpeechBubble.setVisibility(View.VISIBLE);
        chefSpeechBubble.setAlpha(0f);
        chefSpeechBubble.animate().alpha(1f).setDuration(300).start();
    }

    /**
     * Hide chef dialogue
     */
    private void hideChefDialogue() {
        chefSpeechBubble.animate().alpha(0f).setDuration(300)
            .withEndAction(() -> chefSpeechBubble.setVisibility(View.GONE))
            .start();
    }

    /**
     * Speak text using TTS
     */
    private void speakText(String text) {
        if (ttsReady && textToSpeech != null) {
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        }
    }

    /**
     * Place pan on stove with bounce effect
     */
    private void placePanOnStove() {
        hideChefDialogue();
        imgPan.setVisibility(View.VISIBLE);
        imgPan.setAlpha(0f);
        imgPan.setScaleX(0.3f);
        imgPan.setScaleY(0.3f);
        imgPan.setTranslationY(-200f);
        
        // Bounce animation - pan drops onto stove
        imgPan.animate()
            .alpha(1f)
            .scaleX(1.2f)
            .scaleY(1.2f)
            .translationY(0f)
            .setDuration(400)
            .withEndAction(() -> {
                // Settle back to normal size
                imgPan.animate()
                    .scaleX(1.0f)
                    .scaleY(1.0f)
                    .setDuration(150)
                    .start();
            })
            .start();
    }

    /**
     * Enable drag and drop for specific ingredient
     */
    private void enableIngredientDragDrop(String ingredientId) {
        // Setup drop listener on chef
        imgChef.setOnDragListener((view, event) -> {
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    return true;

                case DragEvent.ACTION_DRAG_ENTERED:
                    view.animate().scaleX(1.1f).scaleY(1.1f).setDuration(200).start();
                    return true;

                case DragEvent.ACTION_DRAG_EXITED:
                    view.animate().scaleX(1.0f).scaleY(1.0f).setDuration(200).start();
                    return true;

                case DragEvent.ACTION_DROP:
                    view.animate().scaleX(1.0f).scaleY(1.0f).setDuration(200).start();
                    
                    // Get dropped ingredient
                    ClipData.Item item = event.getClipData().getItemAt(0);
                    String droppedIngredient = item.getText().toString();

                    // Handle the drop
                    handleIngredientDelivery(droppedIngredient, ingredientId);
                    return true;

                case DragEvent.ACTION_DRAG_ENDED:
                    view.animate().scaleX(1.0f).scaleY(1.0f).setDuration(200).start();
                    return true;

                default:
                    return false;
            }
        });
    }

    /**
     * Handle ingredient delivery to chef
     */
    private void handleIngredientDelivery(String droppedIngredient, String expectedIngredient) {
        if (droppedIngredient.equals(expectedIngredient)) {
            // Correct ingredient!
            collectedIngredients.add(droppedIngredient);
            hideChefDialogue();
            
            // Animate chef receiving ingredient
            animateChefReceive();
            
            Toast.makeText(this, "Perfect! ✓", Toast.LENGTH_SHORT).show();
            
            // Move to next step
            handler.postDelayed(() -> {
                if (currentStep == CookingStep.CHEF_NEED_CHICKEN) {
                    // Request oil next
                    currentStep = CookingStep.CHEF_NEED_OIL;
                    showChefDialogue("I need some oil");
                    speakText("I need some oil");
                    enableIngredientDragDrop("OIL");
                } else if (currentStep == CookingStep.CHEF_NEED_OIL) {
                    // Start cooking
                    currentStep = CookingStep.COOKING;
                    startCooking();
                }
            }, 1500);
        } else {
            // Wrong ingredient
            Toast.makeText(this, "Wrong ingredient! ❌", Toast.LENGTH_SHORT).show();
            animateShake(imgChef);
        }
    }

    /**
     * Animate chef receiving ingredient with happy jump
     */
    private void animateChefReceive() {
        // Jump up and down with joy
        imgChef.animate()
            .scaleX(1.15f)
            .scaleY(1.15f)
            .translationY(-15f)
            .setDuration(200)
            .withEndAction(() -> 
                imgChef.animate()
                    .scaleX(1.0f)
                    .scaleY(1.0f)
                    .translationY(0f)
                    .setDuration(200)
                    .start()
            )
            .start();
    }

    /**
     * Start cooking animation
     */
    private void startCooking() {
        hideChefDialogue();
        
        // Show cooking status
        tvCookingStatus.setVisibility(View.VISIBLE);
        tvCookingStatus.setText("🔥");
        
        // Animate pan cooking
        animatePanCooking();
        
        // Finish cooking after 3 seconds
        handler.postDelayed(() -> {
            currentStep = CookingStep.CHEF_DONE;
            tvCookingStatus.setVisibility(View.GONE);
            
            showChefDialogue("Wow, yummy yummy");
            speakText("Wow, yummy yummy");
            
            // Serve to customer
            handler.postDelayed(() -> {
                serveDishToCustomer();
            }, 2000);
        }, 3000);
    }

    /**
     * Animate pan cooking with steam and rotation
     */
    private void animatePanCooking() {
        // Rotate pan while cooking
        ObjectAnimator rotation = ObjectAnimator.ofFloat(imgPan, "rotation", 0f, -3f, 3f, -3f, 3f, 0f);
        rotation.setDuration(400);
        rotation.setRepeatCount(7);
        rotation.start();
        
        // Pan also bounces slightly
        ObjectAnimator bounce = ObjectAnimator.ofFloat(imgPan, "translationY", 0f, -5f, 0f, -5f, 0f);
        bounce.setDuration(400);
        bounce.setRepeatCount(7);
        bounce.start();
        
        // Animate cooking status (steam rising)
        tvCookingStatus.setAlpha(1f);
        tvCookingStatus.setScaleX(1f);
        tvCookingStatus.setScaleY(1f);
        
        ObjectAnimator steamRise = ObjectAnimator.ofFloat(tvCookingStatus, "translationY", 0f, -30f);
        ObjectAnimator steamFade = ObjectAnimator.ofFloat(tvCookingStatus, "alpha", 1f, 0f);
        steamRise.setDuration(800);
        steamFade.setDuration(800);
        steamRise.setRepeatCount(ObjectAnimator.INFINITE);
        steamFade.setRepeatCount(ObjectAnimator.INFINITE);
        steamRise.setRepeatMode(ObjectAnimator.RESTART);
        steamFade.setRepeatMode(ObjectAnimator.RESTART);
        steamRise.start();
        steamFade.start();
    }

    /**
     * Serve dish to customer
     */
    private void serveDishToCustomer() {
        hideChefDialogue();
        
        // Hide pan
        imgPan.animate().alpha(0f).setDuration(300)
            .withEndAction(() -> imgPan.setVisibility(View.GONE))
            .start();
        
        // Customer thanks
        handler.postDelayed(() -> {
            currentStep = CookingStep.CUSTOMER_THANKS;
            hideCustomerDialogue();
            
            handler.postDelayed(() -> {
                showCustomerDialogue("Thank you very much");
                speakText("Thank you very much");
                
                // Update score
                score++;
                tvScore.setText("⭐ " + score);
                
                // Celebrate
                animateCelebration();
                
                // Start new round
                handler.postDelayed(() -> {
                    hideCustomerDialogue();
                    startCookingSequence();
                }, 4000);
            }, 500);
        }, 1000);
    }

    /**
     * Animate celebration with customer bouncing happily
     */
    private void animateCelebration() {
        // Customer bounces up and down with excitement
        imgCustomer.animate()
            .scaleX(1.25f)
            .scaleY(1.25f)
            .rotation(10f)
            .setDuration(250)
            .withEndAction(() -> 
                imgCustomer.animate()
                    .scaleX(1.1f)
                    .scaleY(1.1f)
                    .rotation(-10f)
                    .setDuration(250)
                    .withEndAction(() ->
                        imgCustomer.animate()
                            .scaleX(1.0f)
                            .scaleY(1.0f)
                            .rotation(0f)
                            .setDuration(250)
                            .start()
                    )
                    .start()
            )
            .start();
            
        // Chef also celebrates with a wave
        imgChef.animate()
            .scaleX(1.15f)
            .scaleY(1.15f)
            .setDuration(300)
            .withEndAction(() -> 
                imgChef.animate()
                    .scaleX(1.0f)
                    .scaleY(1.0f)
                    .setDuration(300)
                    .start()
            )
            .start();
            
        Toast.makeText(this, "🎉 Delicious! 🎉", Toast.LENGTH_LONG).show();
    }

    /**
     * Animate shake effect
     */
    private void animateShake(View view) {
        ObjectAnimator shake = ObjectAnimator.ofFloat(view, "translationX", 
                0f, -25f, 25f, -25f, 25f, 0f);
        shake.setDuration(400);
        shake.start();
    }

    @Override
    protected void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        super.onDestroy();
    }

    /**
     * Ingredient data class
     */
    private static class Ingredient {
        String id;
        String displayName;
        String emoji;
        int drawableId;

        Ingredient(String id, String displayName, String emoji, int drawableId) {
            this.id = id;
            this.displayName = displayName;
            this.emoji = emoji;
            this.drawableId = drawableId;
        }
    }
}
