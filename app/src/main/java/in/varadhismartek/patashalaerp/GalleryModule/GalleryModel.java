package in.varadhismartek.patashalaerp.GalleryModule;

import java.io.Serializable;
import java.util.ArrayList;

public class GalleryModel implements Serializable {

    private String date;
    private String galleryTitle, galleryId;
    private ArrayList<String> uriArrayList;

    public GalleryModel(String date, String galleryTitle,String galleryId, ArrayList<String> uriArrayList) {
        this.date = date;
        this.galleryTitle = galleryTitle;
        this.uriArrayList = uriArrayList;
        this.galleryId = galleryId;
    }

    public String getDate() {
        return date;
    }

    public ArrayList<String> getUriArrayList() {
        return uriArrayList;
    }

    public String getGalleryTitle() {
        return galleryTitle;
    }

    public String getGalleryId() {
        return galleryId;
    }
}
