package model;

import com.datastax.driver.core.LocalDate;
import twitter4j.*;

public class Tweet {

    private long id;

    private String text;

    private LocalDate createdAt;

    private boolean isTruncated;

    private double latitude;

    private double longitude;

    private boolean isFavorited;

    private String username;

    private String language;

    public Tweet(
            long id,
            String text,
            LocalDate date,
            boolean truncated,
            double latitude,
            double longitude,
            boolean favorited,
            String username) {

        this.id = id;
        this.username = username;
        this.text = text;
        this.createdAt = date;
        this.isFavorited = favorited;
        this.isTruncated = truncated;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Tweet(
            long id,
            String text,
            LocalDate date,
            String username,
            String language) {

        this.id = id;
        this.username = username;
        this.text = text;
        this.createdAt = date;
        this.isFavorited = false;
        this.isTruncated = false;
        this.latitude = 0;
        this.longitude = 0;
        this.language = language;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isTruncated() {
        return isTruncated;
    }

    public void setTruncated(boolean truncated) {
        isTruncated = truncated;
    }

    public boolean isFavorited() {
        return isFavorited;
    }

    public void setFavorited(boolean favorited) {
        isFavorited = favorited;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getLatitude() { return latitude; }

    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }

    public void setLongitude(double longitude) { this.longitude = longitude; }

    public String getLanguage() { return language; }

    public void setLanguage(String language) { this.language = language; }

    @Override
    public String toString() {
        return "Tweet{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", createdAt=" + createdAt +
                ", isTruncated=" + isTruncated +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", isFavorited=" + isFavorited +
                ", username='" + username + '\'' +
                ", language='" + language + '\'' +
                '}';
    }
}
