package com.petplanner.petplanner;


import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class AdapterPetsChoose extends CursorAdapter {

    public AdapterPetsChoose(Context context, Cursor cursor){
        super (context, cursor, 0);
    }
    @Override

    public View newView(Context context, Cursor cursor, ViewGroup parent){
        return LayoutInflater.from(context).inflate(R.layout.custom_bottom_list,parent,false);
    }
    @Override
    public void bindView (View view, Context context, Cursor cursor){
        //  RadioButton txtNome = (RadioButton) view.findViewById(R.id.radioButton1);
        ListView lvPets = (ListView) view.findViewById(R.id.lvPets);
        CursorAdapter petsAdapter = new SimpleCursorAdapter(
                context,
                R.layout.simple_pets_item,
                cursor,
                new String[]{"NOME", "IMGRESID"},
                new int[]{R.id.txtPetName, R.id.petPhoto},
                0) {
        };
        lvPets.setAdapter(petsAdapter);

        //     if(cursor.moveToFirst()) {
        //RadioButton rdb = (RadioButton) view.findViewById(R.id.rdbPet);
        //TextView name = (TextView) view.findViewById(R.id.txtPetNome);
        //name.setText(cursor.getString(1));


        //String txt = cursor.getString(cursor.getColumnIndexOrThrow("NOME"));
        //Integer img = cursor.getInt((cursor.getColumnIndexOrThrow("IMGRESID")));
//        txtNome.setText(txt);
//        txtNome.setBackgroundResource(img);
    }

}
