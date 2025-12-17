package com.edu.kidsapp;

import java.util.List;

/**
 * SpeakingLesson - A speaking/pronunciation lesson with vocabulary items
 */
public class SpeakingLesson {
    private int id;
    private String title;
    private String description;
    private String category;  // Animals, Colors, Numbers, Food, etc.
    private List<VocabItem> vocabItems;
    private int iconResId;
    private boolean isLocked;

    public SpeakingLesson(int id, String title, String description, String category,
                         List<VocabItem> vocabItems, int iconResId, boolean isLocked) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.vocabItems = vocabItems;
        this.iconResId = iconResId;
        this.isLocked = isLocked;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public List<VocabItem> getVocabItems() {
        return vocabItems;
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

    public int getVocabCount() {
        return vocabItems != null ? vocabItems.size() : 0;
    }
}
