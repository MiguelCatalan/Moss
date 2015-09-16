package com.miguelcatalan.model.entities;

import java.io.Serializable;

/**
 * @author Miguel Catalan Bañuls
 */
public class Book implements Serializable {

    private String ID;
    private String Title;
    private String SubTitle;
    private String Description;
    private String Image;
    private boolean bookReady;

    public Book(String ID, String Title, String Description, String Image) {

        this.ID = ID;
        this.Title = Title;
        this.Description = Description;
        this.Image = Image;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getSubTitle() {
        return SubTitle;
    }

    public void setSubTitle(String subTitle) {
        SubTitle = subTitle;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setBookReady(boolean bookReady) {
        this.bookReady = bookReady;
    }

    public boolean isBookReady() {
        return bookReady;
    }
}
