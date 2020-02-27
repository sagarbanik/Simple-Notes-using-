package com.b1.sagar.simplenotes.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.b1.sagar.simplenotes.entity.Notes;

import java.util.List;

@Dao
public interface NotesDao {

    @Query("SELECT * FROM Notes")
    List<Notes> getAllContacts();

    @Insert
    void insert(Notes notes);

    @Delete
    void delete(Notes notes);
    @Update
    public void update(Notes notes);

}
