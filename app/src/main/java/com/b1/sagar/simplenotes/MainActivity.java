package com.b1.sagar.simplenotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.b1.sagar.simplenotes.dao.NotesDao;
import com.b1.sagar.simplenotes.db.MyDatabase;
import com.b1.sagar.simplenotes.entity.Notes;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private MyDatabase myDatabase;
    private Button btnAddNote,btnAllNote;
    private EditText etTitle,etText;

    public static NotesDao notesDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAddNote = findViewById(R.id.btnAddNote);
        btnAllNote = findViewById(R.id.btnAllNote);

        etTitle = findViewById(R.id.etTitle);
        etText = findViewById(R.id.etText);

        myDatabase = Room.databaseBuilder(MainActivity.this,
                MyDatabase.class,"my_database")
                .allowMainThreadQueries().build();

        notesDao =myDatabase.notesDao();

        btnAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String title = etTitle.getText().toString();
                final String text = etText.getText().toString();

                //Long tsLong = System.currentTimeMillis()/1000;
                //String ts = tsLong.toString();
                String currentTime = getCurrentTimeStamp();

                Notes notes = new Notes();
                notes.title = title;
                notes.text = text;
                notes.timestamp = currentTime;

                notesDao.insert(notes);
                Toast.makeText(MainActivity.this, "Added Successfully", Toast.LENGTH_SHORT).show();


                etTitle.setText("");
                etText.setText("");

            }
        });

        btnAllNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DetailsNotesActivity.class);
                startActivity(intent);
            }
        });

    }

    public static String getCurrentTimeStamp(){
        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
            String currentDateTime = dateFormat.format(new Date()); // Find todays date

            return currentDateTime;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }
}
