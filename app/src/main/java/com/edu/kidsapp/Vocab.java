package com.edu.kidsapp;

/**
 * Vocab - Data model for vocabulary flashcards
 * Contains English word, Vietnamese translation, and image resource
 */
public class Vocab {
    private String english;
    private String vietnamese;
    private String imageRes; // Resource name or path

    public Vocab(String english, String vietnamese, String imageRes) {
        this.english = english;
        this.vietnamese = vietnamese;
        this.imageRes = imageRes;
    }

    // Getters and Setters
    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getVietnamese() {
        return vietnamese;
    }

    public void setVietnamese(String vietnamese) {
        this.vietnamese = vietnamese;
    }

    public String getImageRes() {
        return imageRes;
    }

    public void setImageRes(String imageRes) {
        this.imageRes = imageRes;
    }
}
