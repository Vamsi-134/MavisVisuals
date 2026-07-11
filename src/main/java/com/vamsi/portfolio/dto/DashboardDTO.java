package com.vamsi.portfolio.dto;

public class DashboardDTO {

    private long totalImages;
    private long totalVideos;
    private long totalCategories;
    private long totalMedia;

    public long getTotalImages() {
        return totalImages;
    }

    public void setTotalImages(long totalImages) {
        this.totalImages = totalImages;
    }

    public long getTotalVideos() {
        return totalVideos;
    }

    public void setTotalVideos(long totalVideos) {
        this.totalVideos = totalVideos;
    }

    public long getTotalCategories() {
        return totalCategories;
    }

    public void setTotalCategories(long totalCategories) {
        this.totalCategories = totalCategories;
    }

    public long getTotalMedia() {
        return totalMedia;
    }

    public void setTotalMedia(long totalMedia) {
        this.totalMedia = totalMedia;
    }
}