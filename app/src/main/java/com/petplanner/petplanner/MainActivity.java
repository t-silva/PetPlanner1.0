package com.petplanner.petplanner;
import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    SQLiteOpenHelper petplannerDB, petplannerDB2;
    SQLiteDatabase bd;
    Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView txtData = findViewById(R.id.txtDate);
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        String fMonth;
        if ( month < 10) {
            fMonth = "0" + String.valueOf(month);
        }
        else {
            fMonth = String.valueOf(month);
        }
        String data = day + "/" + fMonth;
        txtData.setText(data);
    }
     @Override
    protected void onStart() {
        super.onStart();
        Button buttonHumor = findViewById(R.id.btnHumor);
        buttonHumor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        MainActivity.this,R.style.BottonSheetDialogTheme
                );
                View bottomSheetView = LayoutInflater.from(getApplicationContext())
                        .inflate(
                                R.layout.bottom_sheet_humor,
                                (LinearLayout)findViewById(R.id.bottomSheetHumor)
                        );
                RadioGroup rdHumor = (RadioGroup) bottomSheetView.findViewById(R.id.rdHumor);
                rdHumor.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    public void onCheckedChanged(RadioGroup group, int checkedId)
                    {
                        Button btnHumor = findViewById(R.id.btnHumor);
                        TextView txtHumor = findViewById(R.id.txtHumor_status);
                        switch (checkedId){
                            case R.id.rdbExc:
                                btnHumor.setBackgroundResource(R.drawable.humor_exc);
                                txtHumor.setText(getString(R.string.humorExc));
                                break;
                            case R.id.rdbHappy:
                                btnHumor.setBackgroundResource(R.drawable.humor_happy);
                                txtHumor.setText(getString(R.string.humorHappy));
                                break;
                            case R.id.rdbOk:
                                btnHumor.setBackgroundResource(R.drawable.humor_ok);
                                txtHumor.setText(getString(R.string.humorOk));
                                break;
                            case R.id.rdbBad:
                                btnHumor.setBackgroundResource(R.drawable.humor_bad);
                                txtHumor.setText(getString(R.string.humorBad));
                        }
                        Toast.makeText(MainActivity.this, "Atualizar BD",Toast.LENGTH_SHORT).show();
                        bottomSheetDialog.dismiss();
                    }
                });
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });
        ImageView imgUri = findViewById(R.id.imgUrina);
        imgUri.setOnClickListener(new View.OnClickListener() {
            TextView txtUri = findViewById(R.id.txtUrina_status);
            int checkUri;
            @Override
            public void onClick(View view) {
                final BottomSheetDialog bottomSheetDialogUri = new BottomSheetDialog(
                        MainActivity.this,R.style.BottonSheetDialogTheme
                );
                View bottomSheetUri = LayoutInflater.from(getApplicationContext())
                        .inflate(
                                R.layout.bottom_sheet_urina,
                                (LinearLayout)findViewById(R.id.bottomSheetUri)
                        );
                RadioGroup rdUri = (RadioGroup) bottomSheetUri.findViewById(R.id.rdUri);
                rdUri.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    public void onCheckedChanged(RadioGroup group, int checkedId)
                    {
                      checkUri = checkedId;
                    }
                });
                if (txtUri.getText() == getString(R.string.uri_normal) ){
                    rdUri.check(R.id.rdbUriNormal);
                }
                else if (txtUri.getText() == getString(R.string.uri_nFez) ){
                    rdUri.check(R.id.rdbUriNaoFez);
                }
                TextView txtCancel = bottomSheetUri.findViewById(R.id.txtCancela);
                txtCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        bottomSheetDialogUri.dismiss();
                    }
                });
                TextView txtOk = bottomSheetUri.findViewById(R.id.txtOk);
                txtOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (checkUri){
                            case R.id.rdbUriNormal:
                                txtUri.setText(getString(R.string.uri_normal));
                                bottomSheetDialogUri.dismiss();
                                Toast.makeText(MainActivity.this, "Atualizar BD",Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.rdbUriNaoFez:
                                txtUri.setText(getString(R.string.uri_nFez));
                                bottomSheetDialogUri.dismiss();
                                Toast.makeText(MainActivity.this, "Atualizar BD",Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                Toast.makeText(MainActivity.this, R.string.selecione,Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                bottomSheetDialogUri.setContentView(bottomSheetUri);
                bottomSheetDialogUri.show();
            }

        });
         TextView imgAtv = findViewById(R.id.txtTempoAtv);
         imgAtv.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 final BottomSheetDialog bottomSheetDialogAtv = new BottomSheetDialog(
                         MainActivity.this,R.style.BottonSheetDialogTheme
                 );
                 final View bottomSheetAtv = LayoutInflater.from(getApplicationContext())
                         .inflate(
                                 R.layout.bottom_sheet_atividade,
                                 (LinearLayout)findViewById(R.id.bottomSheetAtividade)
                         );
                 TextView txtCancel = bottomSheetAtv.findViewById(R.id.txtCancela);
                 txtCancel.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                         bottomSheetDialogAtv.dismiss();
                     }
                 });
                 TextView txtOk = bottomSheetAtv.findViewById(R.id.txtOk);
                 txtOk.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                         EditText edtTipo = bottomSheetAtv.findViewById(R.id.edtAtvTipo);
                         EditText edtTempo = bottomSheetAtv.findViewById(R.id.edtAtvTempo);
                         if (edtTempo.getText().toString().length() <1 || edtTipo.getText().toString().length() <1){
                             Toast.makeText(MainActivity.this, R.string.digite_atividade,Toast.LENGTH_SHORT).show();
                         }
                         else {
                             TextView txtAtividade = findViewById(R.id.txtAtividade_status);
                             txtAtividade.setText(edtTipo.getText().toString());
                             TextView txtTempoAtv = findViewById(R.id.txtTempoAtv);
                             txtTempoAtv.setText(edtTempo.getText().toString()+"");
                             Toast.makeText(MainActivity.this, "Atualizar BD | Arrumar espaçamento de digitação | Validar inputs ",Toast.LENGTH_SHORT).show();
                             bottomSheetDialogAtv.dismiss();
                         }


                     }
                 });
                 bottomSheetDialogAtv.setContentView(bottomSheetAtv);
                 bottomSheetDialogAtv.show();
             }

         });
         ImageView imgFezes = findViewById(R.id.imgFezes);
         imgFezes.setOnClickListener(new View.OnClickListener() {
             TextView txtFezes = findViewById(R.id.txtFezes_status);
             int checkFezes;
             @Override
             public void onClick(View view) {
                 final BottomSheetDialog bottomSheetDialogFezes = new BottomSheetDialog(
                         MainActivity.this,R.style.BottonSheetDialogTheme
                 );
                 View bottomSheetFezes = LayoutInflater.from(getApplicationContext())
                         .inflate(
                                 R.layout.bottom_sheet_fezes,
                                 (LinearLayout)findViewById(R.id.bottomSheetFezes)
                         );
                 RadioGroup rdFezes = (RadioGroup) bottomSheetFezes.findViewById(R.id.rdFezes);
                 rdFezes.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                     public void onCheckedChanged(RadioGroup group, int checkedId)
                     {
                         checkFezes = checkedId;
                     }
                 });
                 if (txtFezes.getText() == getString(R.string.fezes_normal) ){
                     rdFezes.check(R.id.rdbFezesNormal);
                 }
                 else if (txtFezes.getText() == getString(R.string.fezes_mole) ){
                     rdFezes.check(R.id.rdbFezesMole);
                 }
                 TextView txtCancel = bottomSheetFezes.findViewById(R.id.txtCancela);
                 txtCancel.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                         bottomSheetDialogFezes.dismiss();
                     }
                 });
                 TextView txtOk = bottomSheetFezes.findViewById(R.id.txtOk);
                 txtOk.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                         switch (checkFezes){
                             case R.id.rdbFezesNormal:
                                 txtFezes.setText(getString(R.string.fezes_normal));
                                 bottomSheetDialogFezes.dismiss();
                                 Toast.makeText(MainActivity.this, "Atualizar BD",Toast.LENGTH_SHORT).show();
                                 break;
                             case R.id.rdbFezesMole:
                                 txtFezes.setText(getString(R.string.fezes_mole));
                                 bottomSheetDialogFezes.dismiss();
                                 Toast.makeText(MainActivity.this, "Atualizar BD",Toast.LENGTH_SHORT).show();
                                 break;
                             case R.id.rdbFezesNaofez:
                                 txtFezes.setText(getString(R.string.fezesNaoFez));
                                 bottomSheetDialogFezes.dismiss();
                                 Toast.makeText(MainActivity.this, "Atualizar BD",Toast.LENGTH_SHORT).show();
                                 break;
                             default:
                                 Toast.makeText(MainActivity.this, R.string.selecione,Toast.LENGTH_SHORT).show();
                         }

                     }
                 });
                 bottomSheetDialogFezes.setContentView(bottomSheetFezes);
                 bottomSheetDialogFezes.show();
             }

         });
        LinearLayout lnChoose = findViewById(R.id.lnChoose);
        lnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final BottomSheetDialog bottomSheetChoose = new BottomSheetDialog(
                        MainActivity.this,R.style.BottonSheetDialogTheme
                );
                final View bottomSheetPets = LayoutInflater.from(getApplicationContext())
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
                ListView lvPets = (ListView) bottomSheetPets.findViewById(R.id.lvPets);
                lvPets.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        int idPet = (int) id;
                        carrega(idPet);
                        bottomSheetChoose.dismiss();
                    }
                });
                AdapterPetsChoose adapter = new AdapterPetsChoose(getApplicationContext(),cursor);
                adapter.bindView(bottomSheetPets,getApplicationContext(),cursor);
                bottomSheetChoose.setContentView(bottomSheetPets);
                bottomSheetChoose.show();
            }
        });
        carrega(1);
    }

    public void carrega(int i) {

        try{
            petplannerDB = new PetplannerBD(getApplicationContext());
            bd = petplannerDB.getReadableDatabase();
            cursor = bd.query(
                    "PETS",
                    new String[] {"_id","NOME","RACA","IDADE", "SEXO", "HUMOR","URINA","ATV","FEZES","IMGRESID"},
                    "_id = ?",
                    new String[]{Integer.toString(i)},
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
                if (cursor.getString(5).equals("Bom")) {
                    BtnHum.setBackgroundResource(R.drawable.humor_happy);
                }

                TextView txtUrina = findViewById(R.id.txtUrina_status);
                txtUrina.setText(cursor.getString(6));
                TextView txtAtividade = findViewById(R.id.txtAtividade_status);
                txtAtividade.setText(cursor.getString(7));
                TextView txtFezes = findViewById(R.id.txtFezes_status);
                txtFezes.setText(cursor.getString(8));
            }
        } catch (SecurityException e){
            Toast.makeText(this, "Banco de dados Não Disponível",Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        carrega(3);
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

    }



}
