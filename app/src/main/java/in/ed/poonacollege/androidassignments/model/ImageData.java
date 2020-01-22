package in.ed.poonacollege.androidassignments.model;

import java.util.Date;

public class ImageData implements Comparable<ImageData>{

    public String path;
    public Date date;

    public ImageData(String path, Date date) {
        this.path = path;
        this.date = date;
    }

    @Override
    public int compareTo(ImageData o) {
        return date.compareTo(o.date);
    }
}
