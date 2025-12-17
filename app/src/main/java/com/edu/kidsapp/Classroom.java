package com.edu.kidsapp;

/**
 * Classroom - Data model for school lobby options
 */
public class Classroom {
    private int id;
    private String title;
    private String description;
    private int iconResId;
    private int colorResId;

    /**
     * Constructor
     * @param id Unique identifier
     * @param title Classroom name
     * @param description Brief description
     * @param iconResId Drawable resource for icon
     * @param colorResId Color resource for card background
     */
    public Classroom(int id, String title, String description, int iconResId, int colorResId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.iconResId = iconResId;
        this.colorResId = colorResId;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getIconResId() {
        return iconResId;
    }

    public int getColorResId() {
        return colorResId;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIconResId(int iconResId) {
        this.iconResId = iconResId;
    }

    public void setColorResId(int colorResId) {
        this.colorResId = colorResId;
    }
}
