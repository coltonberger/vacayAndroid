package com.vacay.vacayandroid;

public class SavedEvent {
    private String eventName;
    private String eventImage;
    private String eventIcon;
    private String eventId;
    private String eventDescription;
    private String createdAt;
    private String eventCity;
    private int  eventPrice;
    private String eventWebsite;
    private int scheduleId;

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

    public String getEventIcon() {
        return eventIcon;
    }

    public void setEventIcon(String eventIcon) {
        this.eventIcon = eventIcon;
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

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    @Override
    public String toString() {
        return "ScheduledEvent{" +
                "eventName='" + eventName + '\'' +
                ", eventImage='" + eventImage + '\'' +
                ", eventImage='" + eventIcon + '\'' +
                ", eventId='" + eventId + '\'' +
                ", eventDescription='" + eventDescription + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", eventCity='" + eventCity + '\'' +
                ", eventPrice=" + eventPrice +
                ", eventWebsite='" + eventWebsite + '\'' +
                ", scheduleId=" + scheduleId +
                '}';
    }
}
