package com.edu.kidsapp;

/**
 * LevelModel - Data model for individual level nodes in the Adventure Map
 */
public class LevelModel {
    private int id;
    private String name;
    private boolean isLocked;
    private boolean isCompleted;
    private int stars; // 0-3 stars

    public LevelModel(int id, String name, boolean isLocked, boolean isCompleted, int stars) {
        this.id = id;
        this.name = name;
        this.isLocked = isLocked;
        this.isCompleted = isCompleted;
        this.stars = stars;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }
}
