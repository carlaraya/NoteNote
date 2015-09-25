package com.example.carl.notenote;

/**
 * Created by icecream on 9/13/15.
 *
 */
public class Note {
    public int id;
    public String title;
    public String content;

    public Note() {
        // nothing
    }

    public Note(int id, String title, String content) {
        super();
        this.id = id;
        this.title = title;
        this.content = content;
    }
}
