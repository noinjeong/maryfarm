package com.numberONE.maryfarm.Retrofit;

public class Thumbnail {
    private String title, thumbImg1, thumbImg2, thumbImg3, plantId;

    public Thumbnail(String title, String thumbImg1, String thumbImg2, String thumbImg3, String plantId) {
        this.title = title;
        this.thumbImg1 = thumbImg1;
        this.thumbImg2 = thumbImg2;
        this.thumbImg3 = thumbImg3;
        this.plantId = plantId;
    }

    public String getTitle() {
        return title;
    }

    public String getThumbImg1() {
        return thumbImg1;
    }

    public String getThumbImg2() {
        return thumbImg2;
    }

    public String getThumbImg3() {
        return thumbImg3;
    }

    public String getPlantId() {
        return plantId;
    }

    @Override
    public String toString() {
        return "Thumbnail{" +
                "title='" + title + '\'' +
                ", thumbImg1='" + thumbImg1 + '\'' +
                ", thumbImg2='" + thumbImg2 + '\'' +
                ", thumbImg3='" + thumbImg3 + '\'' +
                ", plantId='" + plantId + '\'' +
                '}';
    }
}
