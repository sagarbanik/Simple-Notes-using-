package com.b1.sagar.simplenotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import com.b1.sagar.simplenotes.adapter.RecViewAdapter;
import com.b1.sagar.simplenotes.entity.Notes;

import java.util.List;

public class DetailsNotesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecViewAdapter adapter;
    private Context context;

    List<Notes> notesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detsils_notes);

        recyclerView = findViewById(R.id.recyclerView);
        context = DetailsNotesActivity.this;

        LinearLayoutManager layoutManager = new
                LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,false);

        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecViewAdapter(context,MainActivity.notesDao.getAllContacts());
        recyclerView.setAdapter(adapter);

        notesList = MainActivity.notesDao.getAllContacts();

        adapter.updateAdapterData(notesList);

    }





}
