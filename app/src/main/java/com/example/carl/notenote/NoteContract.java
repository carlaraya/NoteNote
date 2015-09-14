package com.example.carl.notenote;

import android.provider.BaseColumns;

/**
 * Created by icecream on 9/13/15.
 */
public class NoteContract {
    public NoteContract() {
        // nothing
    }

    public static abstract class Entry implements BaseColumns {
        public static final String TABLE_NAME = "entry";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_CONTENT = "content";
    }
}
