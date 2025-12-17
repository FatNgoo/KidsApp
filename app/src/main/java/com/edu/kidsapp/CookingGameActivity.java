package com.edu.kidsapp;

import android.animation.ObjectAnimator;
import android.content.ClipData;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

/**
 * CookingGameActivity - MasterChef Game Redesigned
 * Beautiful, colorful kitchen-themed cooking game with recipes
 */
public class CookingGameActivity extends AppCompatActivity {

    // UI Components
    private ImageView imgPot, imgCustomer, imgDish;
    private TextView tvDishName, tvScore;
    private ImageButton btnSpeaker;
    private LinearLayout checklistContainer;
    private GridLayout ingredientGrid;

    // Game State
    private Recipe currentRecipe;
    private int score = 0;
    private List<String> addedIngredients = new ArrayList<>();
    private Map<String, View> checklistItems = new HashMap<>();
    
    // Text to Speech
    private TextToSpeech textToSpeech;
    private boolean ttsReady = false;

    // Recipe Database
    private List<Recipe> recipes = new ArrayList<>();
    private Random random = new Random();

    // All available ingredients
    private static final Ingredient[] ALL_INGREDIENTS = {
            new Ingredient("APPLE", "Apple", "🍎", R.drawable.food_apple),
            new Ingredient("BANANA", "Banana", "🍌", R.drawable.food_banana),
            new Ingredient("CABBAGE", "Cabbage", "🥬", R.drawable.food_cabbage),
            new Ingredient("TOMATO", "Tomato", "🍅", R.drawable.food_apple), // Replace with actual drawable
            new Ingredient("CARROT", "Carrot", "🥕", R.drawable.food_banana), // Replace with actual drawable
            new Ingredient("BREAD", "Bread", "🍞", R.drawable.food_cabbage), // Replace with actual drawable
            new Ingredient("CHEESE", "Cheese", "🧀", R.drawable.food_apple), // Replace with actual drawable
            new Ingredient("EGG", "Egg", "🥚", R.drawable.food_banana) // Replace with actual drawable
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

        // Initialize recipes
        initializeRecipes();

        // Setup ingredient grid
        setupIngredientGrid();

        // Setup drop listener for pot
        setupDropListener();

        // Start first recipe
        loadNewRecipe();
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
        imgPot = findViewById(R.id.imgPot);
        imgCustomer = findViewById(R.id.imgCustomer);
        imgDish = findViewById(R.id.imgDish);
        tvDishName = findViewById(R.id.tvDishName);
        tvScore = findViewById(R.id.tvScore);
        btnSpeaker = findViewById(R.id.btnSpeaker);
        checklistContainer = findViewById(R.id.checklistContainer);
        ingredientGrid = findViewById(R.id.ingredientGrid);

        // Speaker button click listener
        btnSpeaker.setOnClickListener(v -> speakDishName());
    }

    /**
     * Speak the dish name using TTS
     */
    private void speakDishName() {
        if (ttsReady && currentRecipe != null) {
            textToSpeech.speak(currentRecipe.name, TextToSpeech.QUEUE_FLUSH, null, null);
            // Animate button
            btnSpeaker.animate().scaleX(0.8f).scaleY(0.8f).setDuration(100)
                    .withEndAction(() -> btnSpeaker.animate().scaleX(1f).scaleY(1f).setDuration(100).start())
                    .start();
        }
    }

    /**
     * Initialize recipe database
     */
    private void initializeRecipes() {
        recipes.add(new Recipe("Fruit Salad", "🥗", 
                new String[]{"APPLE", "BANANA"}, R.drawable.food_apple));
        
        recipes.add(new Recipe("Veggie Mix", "🥙", 
                new String[]{"CABBAGE", "CARROT", "TOMATO"}, R.drawable.food_cabbage));
        
        recipes.add(new Recipe("Breakfast Special", "🍳", 
                new String[]{"EGG", "BREAD", "TOMATO"}, R.drawable.food_banana));
        
        recipes.add(new Recipe("Cheese Toast", "🥪", 
                new String[]{"BREAD", "CHEESE"}, R.drawable.food_apple));
        
        recipes.add(new Recipe("Rainbow Bowl", "🌈", 
                new String[]{"APPLE", "BANANA", "CARROT", "CABBAGE"}, R.drawable.food_apple));
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
     * Setup drop listener for the pot
     */
    private void setupDropListener() {
        imgPot.setOnDragListener((view, event) -> {
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    return true;

                case DragEvent.ACTION_DRAG_ENTERED:
                    // Scale up pot
                    view.animate().scaleX(1.1f).scaleY(1.1f).setDuration(200).start();
                    return true;

                case DragEvent.ACTION_DRAG_EXITED:
                    // Reset pot scale
                    view.animate().scaleX(1.0f).scaleY(1.0f).setDuration(200).start();
                    return true;

                case DragEvent.ACTION_DROP:
                    // Reset pot scale
                    view.animate().scaleX(1.0f).scaleY(1.0f).setDuration(200).start();
                    
                    // Get dropped ingredient
                    ClipData.Item item = event.getClipData().getItemAt(0);
                    String droppedIngredient = item.getText().toString();

                    // Handle the drop
                    handleIngredientDrop(droppedIngredient);
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
     * Handle ingredient drop into pot
     */
    private void handleIngredientDrop(String ingredientId) {
        // Check if ingredient is needed for current recipe
        boolean isNeeded = false;
        for (String needed : currentRecipe.ingredients) {
            if (needed.equals(ingredientId) && !addedIngredients.contains(ingredientId)) {
                isNeeded = true;
                break;
            }
        }

        if (isNeeded) {
            // Correct ingredient!
            addedIngredients.add(ingredientId);
            updateChecklist(ingredientId);
            animateCooking();
            Toast.makeText(this, "Perfect! ✓", Toast.LENGTH_SHORT).show();

            // Check if recipe is complete
            if (addedIngredients.size() == currentRecipe.ingredients.length) {
                recipeComplete();
            }
        } else {
            // Wrong ingredient
            animateShake();
            Toast.makeText(this, "Wrong ingredient! ❌", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Update checklist to show ingredient as added
     */
    private void updateChecklist(String ingredientId) {
        View checklistItem = checklistItems.get(ingredientId);
        if (checklistItem != null) {
            ImageView checkmark = checklistItem.findViewById(R.id.imgCheckmark);
            checkmark.setVisibility(View.VISIBLE);
            checkmark.animate().scaleX(1.2f).scaleY(1.2f).setDuration(200)
                    .withEndAction(() -> checkmark.animate().scaleX(1f).scaleY(1f).setDuration(100).start())
                    .start();
        }
    }

    /**
     * Recipe completed successfully
     */
    private void recipeComplete() {
        score++;
        tvScore.setText("⭐ " + score);
        
        // Celebration animation
        imgPot.animate()
                .rotationBy(360)
                .scaleX(1.3f)
                .scaleY(1.3f)
                .setDuration(500)
                .withEndAction(() -> {
                    imgPot.animate().scaleX(1f).scaleY(1f).setDuration(200).start();
                    Toast.makeText(this, "🎉 Delicious! Recipe Complete! 🎉", Toast.LENGTH_LONG).show();
                    
                    // Load new recipe after delay
                    imgPot.postDelayed(this::loadNewRecipe, 1500);
                })
                .start();
    }

    /**
     * Load a new random recipe
     */
    private void loadNewRecipe() {
        // Pick random recipe
        currentRecipe = recipes.get(random.nextInt(recipes.size()));
        
        // Reset state
        addedIngredients.clear();
        checklistItems.clear();
        
        // Update UI
        tvDishName.setText(currentRecipe.name);
        imgDish.setImageResource(currentRecipe.dishImage);
        
        // Build checklist
        buildChecklist();
    }

    /**
     * Build the checklist for current recipe
     */
    private void buildChecklist() {
        checklistContainer.removeAllViews();
        
        for (String ingredientId : currentRecipe.ingredients) {
            // Find ingredient details
            Ingredient ingredient = findIngredient(ingredientId);
            if (ingredient != null) {
                View itemView = createChecklistItem(ingredient);
                checklistContainer.addView(itemView);
                checklistItems.put(ingredientId, itemView);
            }
        }
    }

    /**
     * Create a checklist item view
     */
    private View createChecklistItem(Ingredient ingredient) {
        View itemView = LayoutInflater.from(this).inflate(
                R.layout.item_checklist, checklistContainer, false);
        
        TextView tvName = itemView.findViewById(R.id.tvChecklistName);
        ImageView imgCheckmark = itemView.findViewById(R.id.imgCheckmark);
        
        tvName.setText(ingredient.emoji + " " + ingredient.displayName);
        imgCheckmark.setVisibility(View.GONE);
        
        return itemView;
    }

    /**
     * Find ingredient by ID
     */
    private Ingredient findIngredient(String id) {
        for (Ingredient ingredient : ALL_INGREDIENTS) {
            if (ingredient.id.equals(id)) {
                return ingredient;
            }
        }
        return null;
    }

    /**
     * Animate pot when correct ingredient added
     */
    private void animateCooking() {
        imgPot.animate()
                .scaleX(1.15f)
                .scaleY(1.15f)
                .setDuration(150)
                .withEndAction(() -> 
                    imgPot.animate().scaleX(1.0f).scaleY(1.0f).setDuration(150).start()
                )
                .start();
    }

    /**
     * Animate pot shake when wrong ingredient
     */
    private void animateShake() {
        ObjectAnimator shake = ObjectAnimator.ofFloat(imgPot, "translationX", 
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
        super.onDestroy();
    }

    /**
     * Recipe data class
     */
    private static class Recipe {
        String name;
        String emoji;
        String[] ingredients;
        int dishImage;

        Recipe(String name, String emoji, String[] ingredients, int dishImage) {
            this.name = name;
            this.emoji = emoji;
            this.ingredients = ingredients;
            this.dishImage = dishImage;
        }
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
