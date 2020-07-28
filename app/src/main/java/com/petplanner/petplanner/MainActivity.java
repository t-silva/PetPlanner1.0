package com.petplanner.petplanner;
import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    SQLiteOpenHelper petplannerDB;
    SQLiteDatabase bd,bdw,bdw2;
    Cursor cursor, cursorH,cursorAtual,cursorU,cursorAtv,cursorFezes;
    String today;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",java.util.Locale.getDefault());
    Date date;
    int[] idPet = new int[1];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }
     @Override
    protected void onStart() {
         date = new Date();
         super.onStart();
         setContentView(R.layout.activity_main);
         TextView txtData = findViewById(R.id.txtDate);
         Calendar calendar = Calendar.getInstance();
         today = dateFormat.format(date);
         String[] output = today.split("-");
         String date_f =  output[2]+ "/" + output[1];
         /*String fMonth;
         if ( month < 10) {
             fMonth = "0" + month;
         }
         else {
             fMonth = String.valueOf(month);
         }
         String data = day + "/" + fMonth;*/
         txtData.setText(date_f);

// Carregar ID sendo utilizado
         petplannerDB = new PetplannerBD(getApplicationContext());
         bd = petplannerDB.getReadableDatabase();
         cursorAtual = bd.query(
                 "ATUAL",
                 new String[] {"_id","_idAtual"},
                 "_id = 1",
                 null,
                 null,
                 null,
                 null,
                 null);
         if(cursorAtual.moveToFirst()) {
             idPet[0] = cursorAtual.getInt(1);
         }
         carrega(idPet[0]);
         carrega(idPet[0]);
// FIM
         TextView historico = findViewById(R.id.textHistorico);
         historico.setOnClickListener(new View.OnClickListener(){
             @Override
             public void onClick(View view){
                 // Toast.makeText(MainActivity.this,idPet[0],Toast.LENGTH_LONG).show();
                 Intent intent = new Intent(MainActivity.this, intHistorico.class);
                 intent.putExtra(intHistorico.EXTRA_idPET, (int) idPet[0]);
                 startActivity(intent);
             }
         });
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
                 TextView txtHstatus = findViewById(R.id.txtHumor_status);
                 if(txtHstatus.getText().equals(getString(R.string.humorExc))){
                     rdHumor.check(R.id.rdbExc);
                 }
                 else if(txtHstatus.getText().equals(getString(R.string.humorHappy))){
                     rdHumor.check(R.id.rdbHappy);
                 }
                 else if(txtHstatus.getText().equals(getString(R.string.humorOk))){
                     rdHumor.check(R.id.rdbOk);
                 }
                 else if(txtHstatus.getText().equals(getString(R.string.humorBad))){
                     rdHumor.check(R.id.rdbBad);
                 }
                 rdHumor.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                     public void onCheckedChanged(RadioGroup group, int checkedId)
                     {
                         Button btnHumor = findViewById(R.id.btnHumor);
                         TextView txtHumor = findViewById(R.id.txtHumor_status);
                         String status = "Definir";
                         switch (checkedId){
                             case R.id.rdbExc:
                                 btnHumor.setBackgroundResource(R.drawable.humor_exc);
                                 txtHumor.setText(getString(R.string.humorExc));
                                 status = getString(R.string.humorExc);
                                 break;
                             case R.id.rdbHappy:
                                 btnHumor.setBackgroundResource(R.drawable.humor_happy);
                                 txtHumor.setText(getString(R.string.humorHappy));
                                 status = getString(R.string.humorHappy);
                                 break;
                             case R.id.rdbOk:
                                 btnHumor.setBackgroundResource(R.drawable.humor_ok);
                                 txtHumor.setText(getString(R.string.humorOk));
                                 status = getString(R.string.humorOk);
                                 break;
                             case R.id.rdbBad:
                                 btnHumor.setBackgroundResource(R.drawable.humor_bad);
                                 txtHumor.setText(getString(R.string.humorBad));
                                 status = getString(R.string.humorBad);
                         }

                         bd = petplannerDB.getWritableDatabase();
                         ContentValues cvH = new ContentValues();
                         cvH.put("_id", idPet[0]);
                         cvH.put("TIMESTAMP", today);
                         cvH.put("STATUS", status);
                         if (cursorH.moveToFirst()){
                             bd.update("HUMOR",cvH,"_id = ? AND TIMESTAMP = ?",new String[]{String.valueOf(idPet[0]), today });
                         }
                         else{
                             Toast.makeText(MainActivity.this, "Primeiro Registro",Toast.LENGTH_SHORT).show();
                             bd.insert("HUMOR", null,cvH);
                         }
                         carrega(idPet[0]); //Preciso carregar de novo para atualizar o cursorH
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
                         final ContentValues cvU = new ContentValues();
                         cvU.put("_idPet", idPet[0]);
                         cvU.put("TIMESTAMP", today);
                         switch (checkUri){
                             case R.id.rdbUriNormal:
                                 txtUri.setText(getString(R.string.uri_normal));
                                 bottomSheetDialogUri.dismiss();
                                 cvU.put("FEZ", 1);
                                 //Toast.makeText(MainActivity.this, "Atualizar BD",Toast.LENGTH_SHORT).show();
                                 break;
                             case R.id.rdbUriNaoFez:
                                 txtUri.setText(getString(R.string.uri_nFez));
                                 cvU.put("FEZ", 0);
                                 bottomSheetDialogUri.dismiss();
                                 //Toast.makeText(MainActivity.this, "Atualizar BD",Toast.LENGTH_SHORT).show();
                                 break;
                             default:
                                 Toast.makeText(MainActivity.this, R.string.selecione,Toast.LENGTH_SHORT).show();
                         }
                         if (cursorU.moveToFirst()){
                             bd.update("URINA",cvU,"_idPET = ? AND TIMESTAMP = ?",new String[]{String.valueOf(idPet[0]), today });
                         }
                         else{
                             Toast.makeText(MainActivity.this, "Primeiro Registro",Toast.LENGTH_SHORT).show();
                             bd.insert("URINA", null,cvU);
                         }
                     }
                 });
                 bottomSheetDialogUri.setContentView(bottomSheetUri);
                 carrega(idPet[0]);
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
                         final ContentValues cvAtv = new ContentValues();
                         cvAtv.put("_idPet", idPet[0]);
                         cvAtv.put("TIMESTAMP", today);
                         EditText edtTipo = bottomSheetAtv.findViewById(R.id.edtAtvTipo);
                         EditText edtTempo = bottomSheetAtv.findViewById(R.id.edtAtvTempo);
                         if (edtTempo.getText().toString().length() <1 || edtTipo.getText().toString().length() <1){
                             Toast.makeText(MainActivity.this, R.string.digite_atividade,Toast.LENGTH_SHORT).show();
                         }
                         else {
                             cvAtv.put("TIPO", edtTipo.getText().toString());
                             cvAtv.put("TEMPO", edtTempo.getText().toString());
                             TextView txtAtividade = findViewById(R.id.txtAtividade_status);
                             txtAtividade.setText(edtTipo.getText().toString());
                             TextView txtTempoAtv = findViewById(R.id.txtTempoAtv);
                             String tempo = edtTempo.getText().toString()+"´";
                             txtTempoAtv.setText(tempo);
                             if (cursorAtv.moveToFirst()){
                                 bd.update("ATIVIDADE",cvAtv,"_idPET = ? AND TIMESTAMP = ?",new String[]{String.valueOf(idPet[0]), today });
                             }
                             else{
                                 Toast.makeText(MainActivity.this, "Primeiro Registro",Toast.LENGTH_SHORT).show();
                                 bd.insert("ATIVIDADE", null,cvAtv);
                             }
                             //Toast.makeText(MainActivity.this, "Atualizar BD | Arrumar espaçamento de digitação | Validar inputs ",Toast.LENGTH_SHORT).show();
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
                 final View bottomSheetFezes = LayoutInflater.from(getApplicationContext())
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
                         EditText edtObs = bottomSheetFezes.findViewById(R.id.edtFezes);
                         final ContentValues cvF = new ContentValues();
                         cvF.put("_idPet", idPet[0]);
                         cvF.put("TIMESTAMP", today);
                         cvF.put("OBS", edtObs.getText().toString());
                         switch (checkFezes){
                             case R.id.rdbFezesNormal:
                                 txtFezes.setText(getString(R.string.fezes_normal));
                                 bottomSheetDialogFezes.dismiss();
                                 cvF.put("STATUS",R.string.fezes_normal);
                                 Toast.makeText(MainActivity.this, "Atualizar BD",Toast.LENGTH_SHORT).show();
                                 break;
                             case R.id.rdbFezesMole:
                                 txtFezes.setText(getString(R.string.fezes_mole));
                                 cvF.put("STATUS",R.string.fezes_mole);
                                 bottomSheetDialogFezes.dismiss();
                                 Toast.makeText(MainActivity.this, "Atualizar BD",Toast.LENGTH_SHORT).show();
                                 break;
                             case R.id.rdbFezesNaofez:
                                 txtFezes.setText(getString(R.string.fezesNaoFez));
                                 cvF.put("STATUS",R.string.fezesNaoFez);
                                 bottomSheetDialogFezes.dismiss();
                                 Toast.makeText(MainActivity.this, "Atualizar BD",Toast.LENGTH_SHORT).show();
                                 break;
                             default:
                                 Toast.makeText(MainActivity.this, R.string.selecione,Toast.LENGTH_SHORT).show();
                         }

                         if (cursorFezes.moveToFirst()){
                             bd.update("FEZES",cvF,"_idPET = ? AND TIMESTAMP = ?",new String[]{String.valueOf(idPet[0]), today });
                         }
                         else{
                             Toast.makeText(MainActivity.this, "Primeiro Registro",Toast.LENGTH_SHORT).show();
                             bd.insert("FEZES", null,cvF);
                         }

                     }
                 });
                 bottomSheetDialogFezes.setContentView(bottomSheetFezes);
                 Objects.requireNonNull(bottomSheetDialogFezes.getWindow()).setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
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
                 petplannerDB = new PetplannerBD(getApplicationContext());
                 bd = petplannerDB.getReadableDatabase();
                 cursor =  bd.query(
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
                         idPet[0] = (int) id;
                         bd = petplannerDB.getWritableDatabase();
                         ContentValues cvAtual = new ContentValues();
                         cvAtual.put("_idAtual", idPet[0]);
                         bd.update("ATUAL",cvAtual,"_id = 1",null);
                         carrega((int) id);
                         //Toast.makeText(MainActivity.this, "Carregar id" + idPet[0],Toast.LENGTH_SHORT).show();
                         bottomSheetChoose.dismiss();
                     }
                 });
                 AdapterPetsChoose adapter = new AdapterPetsChoose(getApplicationContext(),cursor);
                 adapter.bindView(bottomSheetPets,getApplicationContext(),cursor);
                 bottomSheetChoose.setContentView(bottomSheetPets);
                 bottomSheetChoose.show();
             }
         });
    }

    public void carrega(int i) {
        TextView txtHumor = findViewById(R.id.txtHumor_status);
        Button BtnHum = findViewById(R.id.btnHumor);
        TextView txtUrina = findViewById(R.id.txtUrina_status);
        TextView txtAtividade = findViewById(R.id.txtAtividade_status);
        TextView txtFezes = findViewById(R.id.txtFezes_status);
        try{
            petplannerDB = new PetplannerBD(getApplicationContext());
            bd = petplannerDB.getReadableDatabase();
        //Gerando cursor para perfil
            cursor = bd.query(
                    "PETS",
                    new String[] {"_id","NOME","RACA","IDADE", "SEXO","IMGRESID"},
                    "_id = ?",
                    new String[]{Integer.toString(i)},
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
              /*
                txtFezes.setText(cursor.getString(8));*/
            }
          //  petplannerDBH = new PetplannerBD(getApplicationContext());
           // bdH = petplannerDB.getReadableDatabase();

        //Gerando cursor para humor
            cursorH = bd.query(
                    "HUMOR",
                    new String[] {"_id","TIMESTAMP","STATUS"},
                    "_id = ? AND TIMESTAMP = ?",
                    new String[]{Integer.toString(i), today},
                    null,
                    null,
                    null,
                    null);
                if(cursorH.moveToFirst()){
                    txtHumor.setText(cursorH.getString(2));
               //     Toast.makeText(MainActivity.this, cursorH.getString(2), Toast.LENGTH_SHORT).show();
                    if ( cursorH.getString(2).equals(getString(R.string.humorExc))) {
                        BtnHum.setBackgroundResource(R.drawable.humor_exc);
                    }
                    else if (cursorH.getString(2).equals(getString(R.string.humorOk))) {
                        BtnHum.setBackgroundResource(R.drawable.humor_ok);
                    }
                    else if (cursorH.getString(2).equals(getString(R.string.humorBad))) {
                        BtnHum.setBackgroundResource(R.drawable.humor_bad);
                    }
                    else if (cursorH.getString(2).equals(getString(R.string.humorHappy))) {
                        BtnHum.setBackgroundResource(R.drawable.humor_happy);
                    }
                }
                else {
                    txtHumor.setText("-");
                    BtnHum.setBackgroundResource(R.drawable.add_custom);
                    bdw = petplannerDB.getWritableDatabase();
                         ContentValues cvHw = new ContentValues();
                         cvHw.put("_id", i);
                         cvHw.put("TIMESTAMP", today);
                         cvHw.put("STATUS", "-");
                         bdw.insert("HUMOR", null,cvHw);
                //    Toast.makeText(this, "Criado TIMESTAMP", Toast.LENGTH_SHORT).show();
                }
        //Gerando cursor para perfil
            cursorU = bd.query(
                    "URINA",
                    new String[] {"_idPET","TIMESTAMP","FEZ","OBS"},
                    "_idPET = ? AND TIMESTAMP = ?",
                    new String[]{Integer.toString(i), today},
                    null,
                    null,
                    null,
                    null);
            if(cursorU.moveToFirst()){
                if (cursorU.getInt(2) == 1)
                    txtUrina.setText(getString(R.string.uri_normal));
                else txtUrina.setText(getString(R.string.uri_nFez));
            }
            else {
                txtUrina.setText(" - ");
            }
            TextView txtTempoAtv = findViewById(R.id.txtTempoAtv);

        //Gerando cursor para perfil
            cursorAtv = bd.query(
                    "ATIVIDADE",
                    new String[] {"_idPET","TIMESTAMP","TIPO","TEMPO"},
                    "_idPET = ? AND TIMESTAMP = ?",
                    new String[]{Integer.toString(i), today},
                    null,
                    null,
                    null,
                    null);
            if(cursorAtv.moveToFirst()){
                txtAtividade.setText(cursorAtv.getString(2));
                String tempo = cursorAtv.getString(3)+"'";
                txtTempoAtv.setText(tempo);
            }
            else {
                ContentValues cvA = new ContentValues();
                txtAtividade.setText(" - ");
                txtTempoAtv.setText("");
                bdw2 = petplannerDB.getWritableDatabase();
                cvA.put("_idPet", i);
                cvA.put("TIMESTAMP", today);
                cvA.put("TIPO", "");
                cvA.put("TEMPO", 0);
                bdw2.insert("ATIVIDADE", null,cvA);
                Toast.makeText(this, "Criado TIMESTAMP", Toast.LENGTH_SHORT).show();
            }

        //Gerando cursor para FEZES
            cursorFezes = bd.query(
                    "FEZES",
                    new String[] {"_idPET","TIMESTAMP","STATUS","OBS"},
                    "_idPET = ? AND TIMESTAMP = ?",
                    new String[]{Integer.toString(i), today},
                    null,
                    null,
                    null,
                    null);
            if(cursorFezes.moveToFirst()){
                txtFezes.setText(getString(cursorFezes.getInt(2)));
            }
            else {
                txtFezes.setText(" - ");
            }

        } catch (SecurityException e){
            Toast.makeText(this, "Banco de dados Não Disponível",Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        //carrega(3);
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

    }



}
