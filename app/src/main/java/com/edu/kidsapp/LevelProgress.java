package com.edu.kidsapp;

/**
 * LevelProgress - Tracks the completion status of each activity in a level
 * Used to determine star count and level unlocking
 */
public class LevelProgress {
    private int levelId;
    private boolean flashcardCompleted;
    private boolean wordWorkshopCompleted;
    private boolean miniQuizCompleted;

    public LevelProgress(int levelId) {
        this.levelId = levelId;
        this.flashcardCompleted = false;
        this.wordWorkshopCompleted = false;
        this.miniQuizCompleted = false;
    }

    public LevelProgress(int levelId, boolean flashcard, boolean wordWorkshop, boolean miniQuiz) {
        this.levelId = levelId;
        this.flashcardCompleted = flashcard;
        this.wordWorkshopCompleted = wordWorkshop;
        this.miniQuizCompleted = miniQuiz;
    }

    /**
     * Calculate total stars earned (1 star per completed activity)
     */
    public int getStarsEarned() {
        int stars = 0;
        if (flashcardCompleted) stars++;
        if (wordWorkshopCompleted) stars++;
        if (miniQuizCompleted) stars++;
        return stars;
    }

    /**
     * Check if all activities are completed (all 3 stars)
     */
    public boolean isLevelFullyCompleted() {
        return flashcardCompleted && wordWorkshopCompleted && miniQuizCompleted;
    }

    // Getters and Setters
    public int getLevelId() {
        return levelId;
    }

    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }

    public boolean isFlashcardCompleted() {
        return flashcardCompleted;
    }

    public void setFlashcardCompleted(boolean flashcardCompleted) {
        this.flashcardCompleted = flashcardCompleted;
    }

    public boolean isWordWorkshopCompleted() {
        return wordWorkshopCompleted;
    }

    public void setWordWorkshopCompleted(boolean wordWorkshopCompleted) {
        this.wordWorkshopCompleted = wordWorkshopCompleted;
    }

    public boolean isMiniQuizCompleted() {
        return miniQuizCompleted;
    }

    public void setMiniQuizCompleted(boolean miniQuizCompleted) {
        this.miniQuizCompleted = miniQuizCompleted;
    }
}
