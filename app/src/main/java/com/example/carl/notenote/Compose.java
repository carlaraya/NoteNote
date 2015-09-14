package com.example.carl.notenote;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.lang.String;

public class Compose extends AppCompatActivity {
    private EditText titleEditText;
    private EditText contentEditText;
    public final static String KEY_MESSAGE = "com.example.carl.notenote.MESSAGE";
    private int noteIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        titleEditText = (EditText) findViewById(R.id.compose_title);
        contentEditText = (EditText) findViewById(R.id.compose_content);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();

        noteIndex = intent.getIntExtra(getString(R.string.extra_note_id), -1);
        // If note index is retrieved, it means we'll edit an existing note.
        // Else, we'll add a new one.
        if (noteIndex > -1) {
            NoteDbHelper db = new NoteDbHelper(this);
            Note note = db.getNote(noteIndex);

            titleEditText.setText(note.title);
            contentEditText.setText(note.content);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_compose, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch(id) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_discard_item: // If we pressed the discard item button, do this
                if (noteIndex > -1) {
                    deleteData();
                }
                onBackPressed();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        goBack();
    }

    @Override
    public void onPause() {
        super.onPause();
        saveData();
    }

    private void goBack() {
        NavUtils.navigateUpFromSameTask(this);
    }

    void saveData() {
        NoteDbHelper db = new NoteDbHelper(this);
        Note note = new Note();
        note.id = noteIndex;
        note.title = titleEditText.getText().toString();
        note.content = contentEditText.getText().toString();

        if (noteIndex > -1) {
            db.updateNote(note);
        } else {
            db.addNote(note);
        }
    }

    void deleteData() {
        NoteDbHelper db = new NoteDbHelper(this);
        db.deleteNote(noteIndex);
    }

}
