package com.edu.kidsapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.card.MaterialCardView;

/**
 * LevelDetailFragment - Shows the 3 activities for a specific level
 * Activities: Flashcard, Word Workshop, Mini Quiz
 * Each activity awards 1 star. All 3 stars needed to unlock next level.
 */
public class LevelDetailFragment extends Fragment {

    private int levelId;
    private String levelName;
    private ProgressManager progressManager;
    private LevelProgress levelProgress;

    // UI Components
    private TextView tvLevelTitle;
    private TextView tvProgressText;
    private ImageView progressStar1, progressStar2, progressStar3;
    private ImageView starFlashcard, starWordWorkshop, starMiniQuiz;
    private MaterialCardView cardFlashcard, cardWordWorkshop, cardMiniQuiz;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Get arguments passed from MapFragment
        if (getArguments() != null) {
            levelId = getArguments().getInt("levelId", 1);
            levelName = getArguments().getString("levelName", "Unknown");
        }

        // Initialize progress manager
        progressManager = ProgressManager.getInstance(requireContext());
        levelProgress = progressManager.getLevelProgress(levelId);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_level_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize views
        initViews(view);

        // Update UI based on progress
        updateProgressUI();

        // Setup click listeners
        setupClickListeners(view);
    }

    private void initViews(View view) {
        // Back button
        view.findViewById(R.id.btnBack).setOnClickListener(v -> 
            Navigation.findNavController(view).popBackStack()
        );

        // Title
        tvLevelTitle = view.findViewById(R.id.tvLevelTitle);
        tvLevelTitle.setText("Level " + levelId + ": " + levelName);

        // Progress section
        tvProgressText = view.findViewById(R.id.tvProgressText);
        progressStar1 = view.findViewById(R.id.progressStar1);
        progressStar2 = view.findViewById(R.id.progressStar2);
        progressStar3 = view.findViewById(R.id.progressStar3);

        // Activity cards
        cardFlashcard = view.findViewById(R.id.cardFlashcard);
        cardWordWorkshop = view.findViewById(R.id.cardWordWorkshop);
        cardMiniQuiz = view.findViewById(R.id.cardMiniQuiz);

        // Star indicators on cards
        starFlashcard = view.findViewById(R.id.starFlashcard);
        starWordWorkshop = view.findViewById(R.id.starWordWorkshop);
        starMiniQuiz = view.findViewById(R.id.starMiniQuiz);
    }

    private void updateProgressUI() {
        int stars = levelProgress.getStarsEarned();

        // Update top progress stars
        progressStar1.setImageResource(stars >= 1 ? 
            android.R.drawable.btn_star_big_on : android.R.drawable.btn_star_big_off);
        progressStar2.setImageResource(stars >= 2 ? 
            android.R.drawable.btn_star_big_on : android.R.drawable.btn_star_big_off);
        progressStar3.setImageResource(stars >= 3 ? 
            android.R.drawable.btn_star_big_on : android.R.drawable.btn_star_big_off);

        // Update star indicators on activity cards
        starFlashcard.setImageResource(levelProgress.isFlashcardCompleted() ? 
            android.R.drawable.btn_star_big_on : android.R.drawable.btn_star_big_off);
        starWordWorkshop.setImageResource(levelProgress.isWordWorkshopCompleted() ? 
            android.R.drawable.btn_star_big_on : android.R.drawable.btn_star_big_off);
        starMiniQuiz.setImageResource(levelProgress.isMiniQuizCompleted() ? 
            android.R.drawable.btn_star_big_on : android.R.drawable.btn_star_big_off);

        // Update progress text
        if (levelProgress.isLevelFullyCompleted()) {
            tvProgressText.setText("🎉 Level completed! Level " + (levelId + 1) + " unlocked!");
        } else {
            int remaining = 3 - stars;
            tvProgressText.setText("Complete " + remaining + " more " + 
                (remaining == 1 ? "activity" : "activities") + 
                " to unlock Level " + (levelId + 1));
        }
    }

    private void setupClickListeners(View view) {
        // Flashcard activity
        cardFlashcard.setOnClickListener(v -> {
            // Navigate to Flashcard (currently goes to existing lesson fragment)
            Bundle bundle = new Bundle();
            bundle.putInt("levelId", levelId);
            bundle.putString("activityType", "flashcard");
            Navigation.findNavController(view).navigate(R.id.action_levelDetail_to_lesson, bundle);
        });

        // Word Workshop activity
        cardWordWorkshop.setOnClickListener(v -> {
            // Navigate to Word Workshop (existing fragment)
            Bundle bundle = new Bundle();
            bundle.putInt("levelId", levelId);
            bundle.putString("activityType", "wordworkshop");
            Navigation.findNavController(view).navigate(R.id.action_levelDetail_to_wordWorkshop, bundle);
        });

        // Mini Quiz activity
        cardMiniQuiz.setOnClickListener(v -> {
            // Navigate to Mini Quiz
            Bundle bundle = new Bundle();
            bundle.putInt("levelId", levelId);
            bundle.putString("activityType", "miniquiz");
            Navigation.findNavController(view).navigate(R.id.action_levelDetail_to_miniQuiz, bundle);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        // Refresh progress when returning from an activity
        levelProgress = progressManager.getLevelProgress(levelId);
        updateProgressUI();
    }
}
