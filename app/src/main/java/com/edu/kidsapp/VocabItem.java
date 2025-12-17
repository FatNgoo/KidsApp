package com.edu.kidsapp;

/**
 * VocabItem - Individual vocabulary word for pronunciation practice
 */
public class VocabItem {
    private int id;
    private String word;
    private String translation;  // Optional Vietnamese translation
    private int imageResId;      // Image of the object/animal
    private int audioResId;      // Audio file for pronunciation (placeholder)

    public VocabItem(int id, String word, String translation, int imageResId) {
        this.id = id;
        this.word = word;
        this.translation = translation;
        this.imageResId = imageResId;
        this.audioResId = 0;  // Placeholder
    }

    public int getId() {
        return id;
    }

    public String getWord() {
        return word;
    }

    public String getTranslation() {
        return translation;
    }

    public int getImageResId() {
        return imageResId;
    }

    public int getAudioResId() {
        return audioResId;
    }

    public void setAudioResId(int audioResId) {
        this.audioResId = audioResId;
    }
}
