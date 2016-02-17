package com.maric.vlajko.mirror;

/**
 * Created by Vlajko on 06-Feb-16.
 */
public class MyPictures {
    private String nameOfImage;
    private String nameOfAuthor;
    private String imagePath;

    public MyPictures(String nameOfImage, String nameOfAuthor, String imagePath){
        this.nameOfImage = nameOfImage;
        this.nameOfAuthor = nameOfAuthor;
        this.imagePath = imagePath;
    }

    public String getNameOfImage() {
        return nameOfImage;
    }

    public String getNameOfAuthor() {
        return nameOfAuthor;
    }

    public String getImagePath() {
        return imagePath;
    }
}
