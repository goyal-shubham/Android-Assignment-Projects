package edu.scu.sgoyal.photonotes;

/**
 * Created by shubhamgoyal on 5/18/16.
 */
public class viewPhotoDetails
{
    int id;
    String caption;
    String imagePath;

    public viewPhotoDetails(int id, String caption, String imagePath)
    {
        this.id = id;
        this.caption = caption;
        this.imagePath = imagePath;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getCaption()
    {
        return caption;
    }

    public void setCaption(String caption)
    {
        this.caption = caption;
    }

    public String getImagePath()
    {
        return imagePath;
    }

    public void setImagePath(String imagePath)
    {
        this.imagePath = imagePath;
    }
}
