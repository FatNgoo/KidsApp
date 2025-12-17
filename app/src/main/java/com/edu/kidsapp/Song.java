package com.edu.kidsapp;

/**
 * Song - Model class for Music Room songs
 * Contains lyrics with blank spaces for fill-in-the-blank exercises
 */
public class Song {
    private int id;
    private String title;
    private String subtitle;
    private String[] lyrics;  // Each line of lyrics, "_____" represents blank space
    private String[] correctAnswers;  // Correct words for each blank in order
    private int audioResourceId;  // Placeholder for future audio implementation

    public Song(int id, String title, String subtitle, String[] lyrics, String[] correctAnswers) {
        this.id = id;
        this.title = title;
        this.subtitle = subtitle;
        this.lyrics = lyrics;
        this.correctAnswers = correctAnswers;
        this.audioResourceId = 0;  // Placeholder
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String[] getLyrics() {
        return lyrics;
    }

    public String[] getCorrectAnswers() {
        return correctAnswers;
    }

    public int getAudioResourceId() {
        return audioResourceId;
    }

    public void setAudioResourceId(int audioResourceId) {
        this.audioResourceId = audioResourceId;
    }

    /**
     * Count total number of blanks in the song
     */
    public int getTotalBlanks() {
        return correctAnswers.length;
    }
}
