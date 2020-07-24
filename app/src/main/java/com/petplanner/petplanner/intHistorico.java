package com.petplanner.petplanner;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
//import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.DefaultXAxisValueFormatter;
import com.github.mikephil.charting.formatter.XAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class intHistorico extends AppCompatActivity {
    public static final String EXTRA_idPET = "idPet";
    Cursor cursor,cursorH;
    SQLiteOpenHelper petplannerDB;
    SQLiteDatabase bd;

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
            // Gerando cursor para Humor
            cursorH = bd.query(
                    "HUMOR",
                    new String[]{"_id", "TIMESTAMP","STATUS"},
                    "_id = ?",
                    new String[]{Integer.toString(idPet)},
                    null,
                    null,   
                    null,
                    null);
        /*Experimentando Line Chart*/

            LineChart lineChart = (LineChart) findViewById(R.id.lnchart);
            ArrayList<String> labels = new ArrayList<>();
            ArrayList<Entry> entries = new ArrayList<>();
            if (cursorH.moveToFirst()) {
                float y = 0;
                int v = 0;
                for (cursorH.moveToFirst(); !cursorH.isAfterLast(); cursorH.moveToNext()) {
                    if (cursorH.getString(2).equals(getString(R.string.humorExc))) {
                        y = 10f;
                    } else if (cursorH.getString(2).equals(getString(R.string.humorHappy))) {
                        y = 8f;
                    } else if (cursorH.getString(2).equals(getString(R.string.humorOk))) {
                        y = 6f;
                    } else if (cursorH.getString(2).equals(getString(R.string.humorBad))) {
                        y = 4f;
                    }
                    String date_nf = cursorH.getString(1);
                    String[] output = date_nf.split("-");
                    String date_f = output[1] + "/" + output[2];
                    labels.add(date_f);
                    entries.add(new Entry(y, v++));
                }
                LineDataSet linedataset = new LineDataSet(entries,"HUMOR");
                lineChart.getAxisLeft().setAxisMinValue(0f);
                lineChart.getAxisLeft().setAxisMaxValue(14f);
                LineData data = new LineData(labels,linedataset);
                lineChart.setData(data); // set the data and list of labels into chart
                linedataset.setDrawCubic(true); //Linha curva
                linedataset.setColor(Color.rgb(66, 192, 120)); //cor da linha
                linedataset.setLineWidth(2.5f); // Tamanho da Linha
                linedataset.setDrawValues(false);
                linedataset.setDrawCircles(false); //remove circulos
                YAxis leftAxis = lineChart.getAxisLeft();
                YAxis rightAxis = lineChart.getAxisRight();
                XAxis xAxis = lineChart.getXAxis();

                leftAxis.setEnabled(false);
                rightAxis.setEnabled(false);
                xAxis.setEnabled(false);

                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setDrawGridLines(false);
                xAxis.setDrawAxisLine(false);
                lineChart.setDescription(null);  // set the description
                lineChart.animateY(1000);


            }


                /* Experimentando LineChart */


                BarChart barChart = (BarChart) findViewById(R.id.barchart);
                ArrayList<String> labels2 = new ArrayList<>();
                ArrayList<BarEntry> entries2 = new ArrayList<>();

                if (cursorH.moveToFirst()) {
                    float a = 0;
                    int b = 0;
                    for (cursorH.moveToFirst(); !cursorH.isAfterLast(); cursorH.moveToNext()) {

                        if (cursorH.getString(2).equals(getString(R.string.humorExc))) {
                            a = 10f;
                        } else if (cursorH.getString(2).equals(getString(R.string.humorHappy))) {
                            a = 8f;
                        } else if (cursorH.getString(2).equals(getString(R.string.humorOk))) {
                            a = 6f;
                        } else if (cursorH.getString(2).equals(getString(R.string.humorBad))) {
                            a = 4f;
                        }
                        String date_nf = cursorH.getString(1);
                        String[] output = date_nf.split("-");

                        String date_f = output[1] + "/" + output[2];
                        labels2.add(date_f);
                        entries2.add(new BarEntry(a, b++));
                    }
                    BarDataSet bardataset = new BarDataSet(entries2, "Atividade Física");
                    barChart.getAxisLeft().setAxisMinValue(0f);
                    barChart.getAxisLeft().setAxisMaxValue(14f);
                    BarData Bardata = new BarData(labels, bardataset);
                    barChart.setData(Bardata); // set the data and list of labels into chart
                    YAxis rightAxis = barChart.getAxisRight();
                    YAxis leftAxis = barChart.getAxisLeft();
                    XAxis xAxisBar = barChart.getXAxis();
                    rightAxis.setEnabled(false); // Remove legenda a direita
                    leftAxis.setDrawLabels(false); //remove legendas da esquerda
                    leftAxis.setDrawAxisLine(false); //Remove linha da esquerda
                    leftAxis.enableGridDashedLine(1f,4f,10f);
                    xAxisBar.setDrawLabels(true);
                    xAxisBar.setPosition(XAxis.XAxisPosition.BOTTOM);
                    barChart.getLegend().setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
                    bardataset.setColor(Color.rgb(73,99,255));
                    xAxisBar.setDrawAxisLine(false);
                    xAxisBar.setDrawGridLines(false);
                    barChart.setDescription(null);  // set the description
                    bardataset.setBarSpacePercent(80f);
                    xAxisBar.setTextColor(Color.WHITE);

                    barChart.animateY(1000);
                    //barChart.setBackgroundResource(R.drawable.activity_bcg);
                    Paint mpaint = barChart.getRenderer().getPaintRender();
                    mpaint.setShader(
                            new LinearGradient(0,0,0,600,
                           Color.rgb(73,99,255),
                            Color.rgb(123,0,143),
                            Shader.TileMode.CLAMP)
                    );
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