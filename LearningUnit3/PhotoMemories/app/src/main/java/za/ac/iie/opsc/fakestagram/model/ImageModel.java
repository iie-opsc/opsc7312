package za.ac.iie.opsc.fakestagram.model;

import android.graphics.Bitmap;

public class ImageModel {
    private String imageName;
    private Bitmap imageBitmap;
    private String imageUrl;

    public ImageModel(){}

    public ImageModel(String imageName, Bitmap imageBitmap) {
        this.imageName = imageName;
        this.imageBitmap = imageBitmap;
    }

    public ImageModel(String imageName, String imageUrl) {
        this.imageName = imageName;
        this.imageUrl = imageUrl;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    public void setImageBitmap(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
