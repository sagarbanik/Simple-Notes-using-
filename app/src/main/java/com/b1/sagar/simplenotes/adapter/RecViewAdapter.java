package com.b1.sagar.simplenotes.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.b1.sagar.simplenotes.DetailsNotesActivity;
import com.b1.sagar.simplenotes.MainActivity;
import com.b1.sagar.simplenotes.R;
import com.b1.sagar.simplenotes.dao.NotesDao;
import com.b1.sagar.simplenotes.entity.Notes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class RecViewAdapter extends RecyclerView.Adapter<RecViewAdapter.Viewholder> {


    Context context;
    List<Notes> tblNotesList;


    Notes notes;
    public static NotesDao notesDao;


    //Constructor
    public RecViewAdapter(Context context, List<Notes> tblNotesList){

        this.context = context;
        this.tblNotesList = tblNotesList;

    }



    //3 override methods

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        notes = tblNotesList.get(position);

        holder.txt_id.setText("ID : " + notes.id);
        holder.txt_title.setText("Title : " + notes.title);
        holder.txt_txt.setText("Text : " + notes.text);
        holder.txt_timestamp.setText("Timestamp : " + notes.timestamp);

    }

    @Override
    public int getItemCount() {
        return tblNotesList.size();
    }




    public void dialogeItemCallback(int itemPos){
        MainActivity.notesDao.delete(tblNotesList.get(itemPos));
    }

    //method to update adapter data
    public void updateAdapterData(List<Notes> notesList) {
        this.tblNotesList = notesList;
        RecViewAdapter.this.notifyDataSetChanged();
    }



    class Viewholder extends RecyclerView.ViewHolder {


        TextView txt_id,txt_title,txt_txt,txt_timestamp;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            txt_id = itemView.findViewById(R.id.txt_id);
            txt_title = itemView.findViewById(R.id.txt_title);
            txt_txt = itemView.findViewById(R.id.txt_txt);
            txt_timestamp = itemView.findViewById(R.id.txt_timestamp);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showOptionDialog(getAdapterPosition());
                }
            });

        }
    }



    public void showOptionDialog(final int adapterPos ) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("CRUD options");

        String [] items = {"Update", "Delete", "Add New"};
        ListAdapter listAdapter = new ArrayAdapter<String>(
                context,
                android.R.layout.simple_list_item_1,
                items
        );

        builder.setAdapter(listAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (which == 0) {
                    dialog.dismiss();
                    showUpdateDialog( adapterPos );
                } else if(which == 1) {
                    showDeleteDialog(adapterPos);
                } else if(which == 2) {
                    ((DetailsNotesActivity)context).finish();
                    context.startActivity(
                            new Intent(context, MainActivity.class)
                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    );
                }
            }
        });

        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void showUpdateDialog(final int adapterPos ) {

        String updatedTime = getCurrentTimeStamp();

        final EditText etTitle, etText;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        final Notes notes = tblNotesList.get(adapterPos);

        View dialogView = LayoutInflater.from(context)
                .inflate(R.layout.update_dialog_layout, null, false);

        etTitle = dialogView.findViewById(R.id.etTitle);
        etText = dialogView.findViewById(R.id.etText);


        etTitle.setText( notes.title );
        etText.setText( notes.text );
        notes.timestamp = updatedTime;


        builder.setView( dialogView );

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                int rowId = notes.id;
                String title = etTitle.getText().toString();
                String text = etText.getText().toString();

                /*MainActivity.contactDao.updateSingle(
                        rowId, name, email, phone
                );*/

                notes.title = title;
                notes.text = text;


                MainActivity.notesDao.update(notes);

                tblNotesList.set(adapterPos, notes);
                RecViewAdapter.this.notifyDataSetChanged();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void showDeleteDialog(final int adapterPos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Warning!");
        builder.setMessage("Are you sure ?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Notes notes = tblNotesList.get(adapterPos);
                MainActivity.notesDao.delete(notes);
                tblNotesList.remove(adapterPos);
                RecViewAdapter.this.notifyDataSetChanged();
            }
        });

        builder.setNeutralButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
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
