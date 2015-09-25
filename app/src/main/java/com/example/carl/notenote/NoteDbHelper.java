package com.example.carl.notenote;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by icecream on 9/13/15.
 *
 */
public class NoteDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Note.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ", ";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + NoteContract.Entry.TABLE_NAME + " (" +
                    NoteContract.Entry._ID + " INTEGER PRIMARY KEY, " +
                    NoteContract.Entry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                    NoteContract.Entry.COLUMN_NAME_CONTENT + TEXT_TYPE + " )";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + NoteContract.Entry.TABLE_NAME;

    public NoteDbHelper (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public long addNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NoteContract.Entry.COLUMN_NAME_TITLE, note.title);
        values.put(NoteContract.Entry.COLUMN_NAME_CONTENT, note.content);

        long newRowId = db.insert(NoteContract.Entry.TABLE_NAME, null, values);

        db.close();
        return newRowId;
    }

    public Note getNote(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                NoteContract.Entry._ID,
                NoteContract.Entry.COLUMN_NAME_TITLE,
                NoteContract.Entry.COLUMN_NAME_CONTENT
        };
        String selection = NoteContract.Entry._ID + "=?";
        String[] selectionArgs = { String.valueOf(id) };
        Cursor c = db.query(
                NoteContract.Entry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (c != null) {
            c.moveToFirst();
        }

        Note note = new Note(Integer.parseInt(c.getString(0)), c.getString(1),  c.getString(2));
        c.close();

        return note;
    }

    public List<Note> getAllNotes() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Note> notes = new LinkedList<>();

        String[] projection = {
                NoteContract.Entry._ID,
                NoteContract.Entry.COLUMN_NAME_TITLE,
                NoteContract.Entry.COLUMN_NAME_CONTENT
        };
        Cursor c = db.query(
                NoteContract.Entry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        Note note;
        //*
        String title;
        String content;
        //*/

        if (c.moveToFirst()) {
            do {
                //*
                title = c.getString(1);
                content = c.getString(2);

                if (title.length() == 0) {
                    title = "(no title)";
                }
                if (content.length() == 0) {
                    content = "(no content)";
                }
                //*/
                note = new Note(Integer.parseInt(c.getString(0)), title, content);
                notes.add(note);
            } while (c.moveToNext());
        }

        c.close();

        return notes;
    }

    public long updateNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NoteContract.Entry.COLUMN_NAME_TITLE, note.title);
        values.put(NoteContract.Entry.COLUMN_NAME_CONTENT, note.content);

        String selection = NoteContract.Entry._ID + "=?";
        String[] selectionArgs = { String.valueOf(note.id) };

        long rowId = db.update(NoteContract.Entry.TABLE_NAME, values, selection, selectionArgs);
        db.close();
        return rowId;
    }

    public void deleteNote(long id) {
        SQLiteDatabase db = this.getWritableDatabase();

        String selection = NoteContract.Entry._ID + "=?";
        String[] selectionArgs = { String.valueOf(id) };

        db.delete(NoteContract.Entry.TABLE_NAME, selection, selectionArgs);
        db.close();
    }
}
