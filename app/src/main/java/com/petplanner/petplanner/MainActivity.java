package com.petplanner.petplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;

public class MainActivity extends AppCompatActivity /*implements AdapterView.OnItemSelectedListener*/{

    SQLiteOpenHelper petplannerDB, petplannerDB2;
    SQLiteDatabase bd;
    Cursor cursor;
    //CursorAdapter petBdAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Button buttonShow = findViewById(R.id.btnHumor);
        buttonShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        MainActivity.this,R.style.BottonSheetDialogTheme
                );
                View bottomSheetView = LayoutInflater.from(getApplicationContext())
                        .inflate(
                                R.layout.bottom_sheet_layout1,
                                (LinearLayout)findViewById(R.id.bottomSheet1)
                        );
                bottomSheetView.findViewById(R.id.btnOk).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MainActivity.this,"Share...", Toast.LENGTH_LONG).show();
                        bottomSheetDialog.dismiss();
                    }
                });
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });
        LinearLayout lnChoose = findViewById(R.id.lnChoose);

        lnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        MainActivity.this,R.style.BottonSheetDialogTheme
                );
                View bottomSheetPets = LayoutInflater.from(getApplicationContext())
                        .inflate(
                                R.layout.custom_bottom_list,
                                (LinearLayout)findViewById(R.id.bottomSheetPets)
                        );
                petplannerDB2 = new PetplannerBD(getApplicationContext());
                bd = petplannerDB.getReadableDatabase();
                cursor = cursor = bd.query(
                        "PETS",
                        new String[] {"_id","NOME","IMGRESID"},
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
                AdapterPetsChoose adapter = new AdapterPetsChoose(getApplicationContext(),cursor);
                adapter.bindView(bottomSheetPets,getApplicationContext(),cursor);
                bottomSheetDialog.setContentView(bottomSheetPets);
                bottomSheetDialog.show();
            }
        });
        carrega(1);

    }

    public void carrega(int i) {
        int Iditem = (int) i;
        try{
            petplannerDB = new PetplannerBD(getApplicationContext());
            bd = petplannerDB.getReadableDatabase();
            cursor = bd.query(
                    "PETS",
                    new String[] {"_id","NOME","RACA","IDADE", "SEXO", "HUMOR","URINA","ATV","FEZES","IMGRESID"},
                    "_id = ?",
                    new String[]{Integer.toString(Iditem)},
                    null,
                    null,
                    null,
                    null);

            if (cursor.moveToFirst()) {
                de.hdodenhof.circleimageview.CircleImageView imgPerfil = findViewById(R.id.fotoCapa);
                imgPerfil.setImageResource(cursor.getInt(9));
                TextView txtNome = findViewById(R.id.txtNome);
                txtNome.setText(cursor.getString(1));
                TextView txtRaca = findViewById(R.id.txtRaca);
                txtRaca.setText(cursor.getString(2));
                TextView txtIdade = findViewById(R.id.txtIdade);
                txtIdade.setText(cursor.getString(3));
                TextView txtSexo = findViewById(R.id.txtSexo);
                txtSexo.setText(cursor.getString(4));
                TextView txtHumor = findViewById(R.id.txtHumor_status);
                txtHumor.setText(cursor.getString(5));
                Button BtnHum = findViewById(R.id.btnHumor);
                if ( cursor.getString(5).equals("Excelente")) {
                    BtnHum.setBackgroundResource(R.drawable.humor_exc);
                }
                if (cursor.getString(5).equals("Normal")) {
                    BtnHum.setBackgroundResource(R.drawable.humor_ok);
                }
                if (cursor.getString(5).equals("Bravo")) {
                    BtnHum.setBackgroundResource(R.drawable.humor_bad);
                }

                TextView txtUrina = findViewById(R.id.txtUrina_status);
                txtUrina.setText(cursor.getString(6));
                TextView txtAtividade = findViewById(R.id.txtAtividade_status);
                txtAtividade.setText(cursor.getString(7));
                TextView txtFezes = findViewById(R.id.txtPoop_status);
                txtFezes.setText(cursor.getString(8));
            }

        } catch (SecurityException e){
            Toast.makeText(this, "Banco de dados Não Disponível",Toast.LENGTH_SHORT).show();
        }

    }




    /*@Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

        int Iditem = (int) id;
        try{
            petplannerDB = new PetplannerBD(this);
            bd = petplannerDB.getReadableDatabase();
            cursor = bd.query(
                    "PETS",
                    new String[] {"RACA","IDADE", "SEXO", "HUMOR","URINA","ATV","FEZES","IMGRESID"},
                    "_id = ?",
                    new String[]{Integer.toString(Iditem)},
                    null,
                    null,
                    null,
                    null);

            if (cursor.moveToFirst()) {
                Button BtnHum = findViewById(R.id.btnHumor);
                de.hdodenhof.circleimageview.CircleImageView imgPerfil = findViewById(R.id.item_foto);
                imgPerfil.setImageResource(cursor.getInt(7));
                TextView txtRaca = findViewById(R.id.txtRaca);
                txtRaca.setText(cursor.getString(0));
                TextView txtIdade = findViewById(R.id.txtIdade);
                txtIdade.setText(cursor.getString(1));
                TextView txtSexo = findViewById(R.id.txtSexo);
                txtSexo.setText(cursor.getString(2));
                TextView txtHumor = findViewById(R.id.txtHumor_status);
                txtHumor.setText(cursor.getString(3));
                if ( cursor.getString(3).equals("Excelente")) {
                    BtnHum.setBackgroundResource(R.drawable.humor_exc);
                }
                if (cursor.getString(3).equals("Normal")) {
                    BtnHum.setBackgroundResource(R.drawable.humor_ok);
                }
                if (cursor.getString(3).equals("Bravo")) {
                    BtnHum.setBackgroundResource(R.drawable.humor_bad);
                }

                TextView txtUrina = findViewById(R.id.txtUrina_status);
                txtUrina.setText(cursor.getString(4));
                TextView txtAtividade = findViewById(R.id.txtAtividade_status);
                txtAtividade.setText(cursor.getString(5));
                TextView txtFezes = findViewById(R.id.txtPoop_status);
                txtFezes.setText(cursor.getString(6));
            }

        } catch (SecurityException e){
            Toast.makeText(this, "Banco de dados Não Disponível",Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

    }*/



}
