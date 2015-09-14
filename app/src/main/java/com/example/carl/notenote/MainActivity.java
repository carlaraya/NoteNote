package com.example.carl.notenote;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    // RecyclerView is that thing in this activity's xml
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private List<Note> notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Do the superclass' thing
        super.onCreate(savedInstanceState);
        // Set the layout
        setContentView(R.layout.activity_main);

        // Get the recycler view from the xml
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        // I guess this manages the layout of each note?
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Retrieve all stored notes
        notes = loadNotes();

        adapter = new NoteAdapter(loadNotes()); // adapter = new NoteAdapter(notes);  <i think this works too>
        recyclerView.setAdapter(adapter);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_compose) {
            composeMessage();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Update stuff after going back from child activity
    @Override
    public void onResume() {
        super.onResume();
        notes = loadNotes();
        adapter.notifyItemRangeChanged(0, adapter.getItemCount());
    }

    // Get notes from db
    public List<Note> loadNotes() {
        NoteDbHelper db = new NoteDbHelper(this);
        return db.getAllNotes();
    }

    // Do this when the compose message button is pressed
    public void composeMessage() {
        Intent intent = new Intent(this, Compose.class);
        startActivity(intent);
    }
}
