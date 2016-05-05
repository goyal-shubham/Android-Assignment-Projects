package edu.scu.sgoyal.zoodirectory;

/**
 * Created by shubhamgoyal on 4/29/16.
 */
public class Animal {
    private String name;
    private String filename;

    public Animal(String name, String filename) {
        this.name = name;
        this.filename = filename;
    }

    public String getName() {
        return name;
    }

    public String getFilename() {
        return filename;
    }
}
