package com.b1.sagar.simplenotes.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.b1.sagar.simplenotes.dao.NotesDao;
import com.b1.sagar.simplenotes.entity.Notes;


@Database(entities = {Notes.class}, version = 1,exportSchema = false)
public abstract class MyDatabase extends RoomDatabase {
    public abstract NotesDao notesDao();
}
