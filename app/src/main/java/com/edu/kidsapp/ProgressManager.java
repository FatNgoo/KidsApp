package com.edu.kidsapp;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

/**
 * ProgressManager - Manages level progress persistence using SharedPreferences
 * Tracks completion of activities (Flashcard, Word Workshop, Mini Quiz) for each level
 */
public class ProgressManager {
    private static final String PREFS_NAME = "KidsAppProgress";
    private static final String KEY_FLASHCARD = "_flashcard";
    private static final String KEY_WORD_WORKSHOP = "_word_workshop";
    private static final String KEY_MINI_QUIZ = "_mini_quiz";

    private static ProgressManager instance;
    private SharedPreferences prefs;
    private Map<Integer, LevelProgress> progressCache;

    private ProgressManager(Context context) {
        prefs = context.getApplicationContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        progressCache = new HashMap<>();
    }

    public static synchronized ProgressManager getInstance(Context context) {
        if (instance == null) {
            instance = new ProgressManager(context);
        }
        return instance;
    }

    /**
     * Get progress for a specific level
     */
    public LevelProgress getLevelProgress(int levelId) {
        // Check cache first
        if (progressCache.containsKey(levelId)) {
            return progressCache.get(levelId);
        }

        // Load from SharedPreferences
        boolean flashcard = prefs.getBoolean(levelId + KEY_FLASHCARD, false);
        boolean wordWorkshop = prefs.getBoolean(levelId + KEY_WORD_WORKSHOP, false);
        boolean miniQuiz = prefs.getBoolean(levelId + KEY_MINI_QUIZ, false);

        LevelProgress progress = new LevelProgress(levelId, flashcard, wordWorkshop, miniQuiz);
        progressCache.put(levelId, progress);
        return progress;
    }

    /**
     * Mark an activity as completed
     */
    public void setActivityCompleted(int levelId, String activityType) {
        LevelProgress progress = getLevelProgress(levelId);
        SharedPreferences.Editor editor = prefs.edit();

        switch (activityType.toLowerCase()) {
            case "flashcard":
                progress.setFlashcardCompleted(true);
                editor.putBoolean(levelId + KEY_FLASHCARD, true);
                break;
            case "wordworkshop":
                progress.setWordWorkshopCompleted(true);
                editor.putBoolean(levelId + KEY_WORD_WORKSHOP, true);
                break;
            case "miniquiz":
                progress.setMiniQuizCompleted(true);
                editor.putBoolean(levelId + KEY_MINI_QUIZ, true);
                break;
        }

        editor.apply();
        progressCache.put(levelId, progress);
    }

    /**
     * Check if a level is unlocked (previous level must be fully completed)
     */
    public boolean isLevelUnlocked(int levelId) {
        if (levelId == 1) {
            return true; // First level is always unlocked
        }

        // Check if previous level is fully completed (has all 3 stars)
        LevelProgress previousProgress = getLevelProgress(levelId - 1);
        return previousProgress.isLevelFullyCompleted();
    }

    /**
     * Get stars earned for a level (0-3)
     */
    public int getStarsForLevel(int levelId) {
        LevelProgress progress = getLevelProgress(levelId);
        return progress.getStarsEarned();
    }

    /**
     * Reset all progress (for testing or user request)
     */
    public void resetAllProgress() {
        prefs.edit().clear().apply();
        progressCache.clear();
    }

    /**
     * Reset progress for a specific level
     */
    public void resetLevelProgress(int levelId) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(levelId + KEY_FLASHCARD);
        editor.remove(levelId + KEY_WORD_WORKSHOP);
        editor.remove(levelId + KEY_MINI_QUIZ);
        editor.apply();
        progressCache.remove(levelId);
    }
}
