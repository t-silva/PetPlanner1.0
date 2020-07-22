package com.petplanner.petplanner;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class intentteste extends AppCompatActivity {
    public static final String EXTRA_idPET = "idPet";
    Cursor cursor,cursorH;
    SQLiteOpenHelper petplannerDB,petplannerDBH;
    SQLiteDatabase bd,bdH;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intent_teste);
       int idPet = (Integer) getIntent().getExtras().get(EXTRA_idPET);
        try{
            petplannerDB = new PetplannerBD(getApplicationContext());
            bd = petplannerDB.getReadableDatabase();
            //Gerando cursor para perfil
            cursor = bd.query(
                    "PETS",
                    new String[] {"_id","NOME","RACA","IDADE", "SEXO","IMGRESID"},
                    "_id = ?",
                    new String[]{Integer.toString(idPet)},
                    null,
                    null,
                    null,
                    null);
            if (cursor.moveToFirst() ) {
                de.hdodenhof.circleimageview.CircleImageView imgPerfil = findViewById(R.id.fotoCapa);
                imgPerfil.setImageResource(cursor.getInt(5));
                TextView txtNome = findViewById(R.id.txtNome);
                txtNome.setText(cursor.getString(1));
                TextView txtRaca = findViewById(R.id.txtRaca);
                txtRaca.setText(cursor.getString(2));
                TextView txtIdade = findViewById(R.id.txtIdade);
                txtIdade.setText(cursor.getString(3));
                TextView txtSexo = findViewById(R.id.txtSexo);
                txtSexo.setText(cursor.getString(4));
            }

            cursorH = bd.query(
                    "HUMOR",
                    new String[]{"_id", "TIMESTAMP","STATUS"},
                    "_id = ?",
                    new String[]{Integer.toString(idPet)},
                    null,
                    null,
                    null,
                    null);
            if(cursorH.moveToFirst()) {
                ListView lvTeste = (ListView) findViewById(R.id.lvTeste);
                CursorAdapter petsAdapter = new SimpleCursorAdapter(
                        this,
                        android.R.layout.simple_list_item_2,
                        cursorH,
                        new String[]{"TIMESTAMP", "STATUS"},
                        new int[]{android.R.id.text1, android.R.id.text2},
                        0);
                lvTeste.setAdapter(petsAdapter);

            }

        } catch (SecurityException e){
            Toast.makeText(this, "Banco de dados Não Disponível",Toast.LENGTH_SHORT).show();
        }
    }
}
