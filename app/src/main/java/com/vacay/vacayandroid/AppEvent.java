package com.vacay.vacayandroid;

public class AppEvent {
    private String eventName;
    private String eventImage;
    private String eventId;
    private String eventDescription;
    private String createdAt;
    private String eventCity;
    private int  eventPrice;
    private String eventWebsite;

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventImage() {
        return eventImage;
    }

    public void setEventImage(String eventImage) {
        this.eventImage = eventImage;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getEventCity() {
        return eventCity;
    }

    public void setEventCity(String eventCity) {
        this.eventCity = eventCity;
    }

    public int getEventPrice() {
        return eventPrice;
    }

    public void setEventPrice(int eventPrice) {
        this.eventPrice = eventPrice;
    }

    public String getEventWebsite() {
        return eventWebsite;
    }

    public void setEventWebsite(String eventWebsite) {
        this.eventWebsite = eventWebsite;
    }

    @Override
    public String toString() {
        return "AppEvent{" +
                "eventName='" + eventName + '\'' +
                ", eventImage='" + eventImage + '\'' +
                ", eventId='" + eventId + '\'' +
                ", eventDescription='" + eventDescription + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", eventCity='" + eventCity + '\'' +
                ", eventPrice=" + eventPrice +
                ", eventWebsite='" + eventWebsite + '\'' +
                '}';
    }
}
