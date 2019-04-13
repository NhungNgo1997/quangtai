package com.example.pnam.doctruyen.Object;

import java.io.Serializable;

public class Truyen implements Serializable {

    private String titleTruyen;
    private String descriptionTruyen;
    private String linkTruyen;
    private String urlImgTruyen;

    public Truyen() {
    }

    public Truyen(String titleTruyen, String descriptionTruyen, String linkTruyen, String urlImgTruyen) {
        this.titleTruyen = titleTruyen;
        this.descriptionTruyen = descriptionTruyen;
        this.linkTruyen = linkTruyen;
        this.urlImgTruyen = urlImgTruyen;
    }

    public String getTitleTruyen() {
        return titleTruyen;
    }

    public void setTitleTruyen(String titleTruyen) {
        this.titleTruyen = titleTruyen;
    }

    public String getDescriptionTruyen() {
        return descriptionTruyen;
    }

    public void setDescriptionTruyen(String descriptionTruyen) {
        this.descriptionTruyen = descriptionTruyen;
    }

    public String getLinkTruyen() {
        return linkTruyen;
    }

    public void setLinkTruyen(String linkTruyen) {
        this.linkTruyen = linkTruyen;
    }

    public String getUrlImgTruyen() {
        return urlImgTruyen;
    }

    public void setUrlImgTruyen(String urlImgTruyen) {
        this.urlImgTruyen = urlImgTruyen;
    }

    @Override
    public String toString() {
        return "Truyen{" +
                "titleTruyen='" + titleTruyen + '\'' +
                ", descriptionTruyen='" + descriptionTruyen + '\'' +
                ", linkTruyen='" + linkTruyen + '\'' +
                ", urlImgTruyen='" + urlImgTruyen + '\'' +
                '}';
    }
}
