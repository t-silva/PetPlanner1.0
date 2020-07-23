package com.petplanner.petplanner;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class intHistorico extends AppCompatActivity {
    public static final String EXTRA_idPET = "idPet";
    Cursor cursor,cursorH;
    SQLiteOpenHelper petplannerDB,petplannerDBH;
    SQLiteDatabase bd,bdH;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.historico_activity);

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

                BarChart barChart = (BarChart) findViewById(R.id.barchart);
                ArrayList<String> labels = new ArrayList<String>();
                ArrayList<BarEntry> entries = new ArrayList<>();

                if (cursorH.moveToFirst()) {
                    float y = 0;
                    int v = 0;
                    for(cursorH.moveToFirst(); !cursorH.isAfterLast(); cursorH.moveToNext()) {

                        if (cursorH.getString(2).equals(getString(R.string.humorExc))){
                            y = 10f;
                        }
                       else  if (cursorH.getString(2).equals(getString(R.string.humorHappy))){
                            y = 8f;
                        }
                        else  if (cursorH.getString(2).equals(getString(R.string.humorOk))){
                            y=6f;
                        }
                        else  if (cursorH.getString(2).equals(getString(R.string.humorBad))){
                            y = 4f;
                        }
                        String date_nf = cursorH.getString(1);
                        String[] output = date_nf.split("-");

                        String date_f = output[1] + "/" + output[2];
                      labels.add(date_f);
                       entries.add(new BarEntry(y,v++));
                    }
                    BarDataSet bardataset = new BarDataSet(entries, "HUMOR");
                    barChart.getAxisLeft().setAxisMinValue(0f);
                    barChart.getAxisLeft().setAxisMaxValue(14f);
                    int factor = 1; // increase this to decrease the bar width. Decrease to increase he bar width
                    BarData data = new BarData(labels, bardataset);
                    barChart.setData(data); // set the data and list of labels into chart
                   // barChart.setDescription("Set Bar Chart Description Here");  // set the description
                  bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
                    barChart.animateY(1000);
                }

        } catch (SecurityException e){
            Toast.makeText(this, "Banco de dados Não Disponível",Toast.LENGTH_SHORT).show();
        }
    }
}







                 /*





        BarData data = new BarData(labels, bardataset);
        barChart.setData(data); // set the data and list of labels into chart
        barChart.setDescription("Set Bar Chart Description Here");  // set the description
        bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
        barChart.animateY(5000);
*/