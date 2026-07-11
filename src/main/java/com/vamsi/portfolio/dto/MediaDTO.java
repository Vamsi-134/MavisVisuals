package com.vamsi.portfolio.dto;

public class MediaDTO {

    private Integer id;
    private String title;
    private String description;
    private String category;
    private String type;
    private String filename;
    private boolean coverImage;
    private String imageUrl;
    

    public MediaDTO() {
    }

    public MediaDTO(Integer id, String title, String description,
                    String category, String type, String filename) {

        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.type = type;
        this.filename = filename;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
    public boolean isCoverImage() {
        return coverImage;
    }

    public void setCoverImage(boolean coverImage) {
        this.coverImage = coverImage;
    }
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
}