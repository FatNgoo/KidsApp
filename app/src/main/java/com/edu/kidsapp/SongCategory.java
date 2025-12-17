package com.edu.kidsapp;

/**
 * SongCategory - Model for songs in Music Room
 * Each song has lyrics with blanks and belongs to a category/theme
 */
public class SongCategory {
    private int id;
    private String title;
    private String subtitle;
    private String category;  // Animals, Colors, Numbers, etc.
    private String[] lyrics;
    private String[] correctAnswers;
    private int iconResId;
    private boolean isLocked;

    public SongCategory(int id, String title, String subtitle, String category,
                        String[] lyrics, String[] correctAnswers, int iconResId, boolean isLocked) {
        this.id = id;
        this.title = title;
        this.subtitle = subtitle;
        this.category = category;
        this.lyrics = lyrics;
        this.correctAnswers = correctAnswers;
        this.iconResId = iconResId;
        this.isLocked = isLocked;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getCategory() {
        return category;
    }

    public String[] getLyrics() {
        return lyrics;
    }

    public String[] getCorrectAnswers() {
        return correctAnswers;
    }

    public int getIconResId() {
        return iconResId;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public int getTotalBlanks() {
        return correctAnswers.length;
    }
}
