package nathja.finalproject.baitap11;

import java.io.Serializable;

public class Video1Model implements Serializable{
    private String videoId;
    private String userId;
    private String videoUrl;
    private String title;
    private String description;

    // Constructor, getters, and setters
    public Video1Model(String videoId, String userId, String videoUrl, String title, String description) {
        this.videoId = videoId;
        this.userId = userId;
        this.videoUrl = videoUrl;
        this.title = title;
        this.description = description;
    }

    public Video1Model() {
        // Default constructor required for Firebase
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}


