package com.edu.kidsapp;

/**
 * GameModel - Data model for minigames in Game Lobby
 * Contains game info, ticket cost, and target activity
 */
public class GameModel {
    private int id;
    private String name;
    private int imageRes;
    private int ticketCost;
    private Class<?> destinationActivity;

    public GameModel(int id, String name, int imageRes, int ticketCost, Class<?> destinationActivity) {
        this.id = id;
        this.name = name;
        this.imageRes = imageRes;
        this.ticketCost = ticketCost;
        this.destinationActivity = destinationActivity;
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

    public int getImageRes() {
        return imageRes;
    }

    public void setImageRes(int imageRes) {
        this.imageRes = imageRes;
    }

    public int getTicketCost() {
        return ticketCost;
    }

    public void setTicketCost(int ticketCost) {
        this.ticketCost = ticketCost;
    }

    public Class<?> getDestinationActivity() {
        return destinationActivity;
    }

    public void setDestinationActivity(Class<?> destinationActivity) {
        this.destinationActivity = destinationActivity;
    }
}
