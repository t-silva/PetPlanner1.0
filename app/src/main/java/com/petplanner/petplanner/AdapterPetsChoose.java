package com.petplanner.petplanner;


import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.ContentFrameLayout;

public class AdapterPetsChoose extends CursorAdapter {

    public AdapterPetsChoose(Context context, Cursor cursor){
        super (context, cursor, 0);
    }
    @Override

    public View newView(Context context, Cursor cursor, ViewGroup parent){
        return LayoutInflater.from(context).inflate(R.layout.custom_bottom_list,parent,false);
    }
    @Override
    public void bindView (View view, final Context context, Cursor cursor){
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

    }


}

