package com.ridecoder.greetingcardmaker.threewaysolutions.Models;

import java.io.Serializable;

public class ImageData implements Serializable {
    String id,catagary_id,photo_title,photo,color,text_color;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCatagary_id() {
        return catagary_id;
    }

    public void setCatagary_id(String catagary_id) {
        this.catagary_id = catagary_id;
    }

    public String getPhoto_title() {
        return photo_title;
    }

    public void setPhoto_title(String photo_title) {
        this.photo_title = photo_title;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getText_color() {
        return text_color;
    }

    public void setText_color(String text_color) {
        this.text_color = text_color;
    }
}
