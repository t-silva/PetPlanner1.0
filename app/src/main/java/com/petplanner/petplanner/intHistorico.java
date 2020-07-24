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
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Float.MAX_VALUE;

public class intHistorico extends AppCompatActivity {
    public static final String EXTRA_idPET = "idPet";
    Cursor cursor,cursorH,cursorAtv,cursorMax;
    SQLiteOpenHelper petplannerDB;
    SQLiteDatabase bd;
    float max;
    private CombinedChart mChart;

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
            //RECUPERANDO MAIOR VALOR
            cursorMax = bd.query(
                    "ATIVIDADE",
                    new String[]{"_idPet","TEMPO"},
                    "_idPet = ? AND TEMPO=(SELECT MAX(TEMPO) FROM ATIVIDADE )",
                    new String[]{Integer.toString(idPet)},
                    null,
                    null,
                    null,
                    null
            );

            if(cursorMax.moveToFirst()) {
                max = cursorMax.getFloat(1);
                Toast.makeText(this,"Max: "+ max,Toast.LENGTH_LONG).show();
            }
            else {
                max =1;
                Toast.makeText(this,"NAO ACHOU MAX",Toast.LENGTH_LONG).show();
            }
            // RECUPERANDO MAIOR VALOR


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

            cursorAtv = bd.query(
                    "ATIVIDADE",
                    new String[]{"_idPet", "TIMESTAMP","TEMPO"},
                    "_idPet = ?",
                    new String[]{Integer.toString(idPet)},
                    null,
                    null,
                    null,
                    null);



        /*      Iniciando Gráficos    */

            mChart = (CombinedChart) findViewById(R.id.combinedChar);
            mChart.getDescription().setText("Descrição Teste");
            mChart.setDrawGridBackground(true); // Desenha o grid
            mChart.setDrawBarShadow(true);
            mChart.setHighlightFullBarEnabled(true);
            mChart.setDrawOrder(new CombinedChart.DrawOrder[]{ //Definindo ordem (linha sobre Barras)
                    CombinedChart.DrawOrder.BAR, CombinedChart.DrawOrder.LINE
            });

        /* Lidando com legendas */

            Legend l = mChart.getLegend();
            l.setWordWrapEnabled(true);
            l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
            l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
            l.setOrientation(Legend.LegendOrientation.HORIZONTAL);

        /*      Fim     */

        /*      Lidando com os Eixos     */

            YAxis rightAxis = mChart.getAxisRight();
            rightAxis.setDrawGridLines(false);
            rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

            YAxis leftAxis = mChart.getAxisLeft();
            leftAxis.setDrawGridLines(false);
            leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

            XAxis xAxis = mChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);
            xAxis.setAxisMinimum(0f);
            xAxis.setGranularity(1f);

            /*      Lidando com labels inferiores   */

            /*final ArrayList<String> mDays = new ArrayList<>();



            xAxis.setValueFormatter(new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    return mDays[(int) value % mDays.size()];
                }
            });*/

            /*      FIM         */

        /*      FIM     */

            CombinedData data = new CombinedData();
            data.setData(generateLineData("Humor"));
            data.setData(generateBarData("Atividade Física"));
            xAxis.setAxisMaximum(data.getXMax() + 0.25f);


            mChart.setData(data);

            mChart.invalidate();

    /*      Fim Iniciando Gráficos       */




        } catch (SecurityException e){
            Toast.makeText(this, "Banco de dados Não Disponível",Toast.LENGTH_SHORT).show();
        }
    }

    private BarData generateBarData(String label){
        ArrayList<BarEntry> entries  = new ArrayList<BarEntry>();
        entries = getBarEntries(entries);
        BarDataSet set1 = new BarDataSet(entries,label);
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
        BarData d = new BarData(set1);
        d.setBarWidth(0.45f);
        Paint mpaint = mChart.getRenderer().getPaintRender();
            mpaint.setShader(
                    new LinearGradient(0,0,0,600,
                            Color.rgb(73,99,255),
                            Color.rgb(123,0,143),
                            Shader.TileMode.CLAMP)
            );

        return d;
        //bardataset.setBarSpacePercent(80f);

        /*YAxis rightAxis = barChart.getAxisRight();
        YAxis leftAxis = barChart.getAxisLeft();
        XAxis xAxisBar = barChart.getXAxis();
        rightAxis.setEnabled(false); // Remove legenda a direita
        //leftAxis.setDrawLabels(false); //remove legendas da esquerda
        leftAxis.setDrawAxisLine(false); //Remove linha da esquerda
        leftAxis.enableGridDashedLine(1f,4f,10f);
        xAxisBar.setDrawLabels(true);
        xAxisBar.setPosition(XAxis.XAxisPosition.BOTTOM);
        //barChart.getAxisLeft().setAxisMaxValue(14f);
        barChart.getLegend().setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        xAxisBar.setDrawAxisLine(false);
        xAxisBar.setDrawGridLines(false);
        barChart.setDescription(null);  // set the description
        xAxisBar.setTextColor(Color.WHITE);
        barChart.animateY(1000);
        //barChart.setBackgroundResource(R.drawable.activity_bcg);
       */


    }

    private ArrayList<BarEntry> getBarEntries(ArrayList<BarEntry> entries) {
        if (cursorAtv.moveToFirst()) {
            float a = 0;
            int b = 1;
            for (cursorAtv.moveToFirst(); !cursorAtv.isAfterLast(); cursorAtv.moveToNext()) {
                a = cursorAtv.getFloat(2);
                entries.add(new BarEntry(b++,a));
            }

        }
        return  entries;

    }

    private LineData generateLineData(String label){
        LineData d = new LineData();
        ArrayList<Entry> entries = new ArrayList<>();
        entries = getLineEntriesData(entries);
        LineDataSet set = new LineDataSet(entries,label);
        set.setColor(Color.rgb(66, 192, 120)); //cor da linha
        set.setFillColor(Color.rgb(66, 192, 120)); //cor da linha
        set.setLineWidth(2.5f); //Tamanho da linha
        set.setDrawCircles(false); //remove circulos
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER); //estilo da linha
        set.setDrawValues(true);
        set.setAxisDependency(YAxis.AxisDependency.LEFT); //dependencia do eixo Y
        d.addDataSet(set);
        return d;


        /*LineData data = new LineData(labels,linedataset);
        lineChart.setData(data); // set the data and list of labels into chart


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
*/
    }

    private ArrayList<Entry> getLineEntriesData(ArrayList<Entry> entries) {
        if (cursorH.moveToFirst()) {
            float y = 0;
            int v = 1;
            for (cursorH.moveToFirst(); !cursorH.isAfterLast(); cursorH.moveToNext()) {
                if (cursorH.getString(2).equals(getString(R.string.humorExc))) {
                    y = max;
                } else if (cursorH.getString(2).equals(getString(R.string.humorHappy))) {
                    y = max * 3 / 4;
                } else if (cursorH.getString(2).equals(getString(R.string.humorOk))) {
                    y = max * 2 / 4;
                } else if (cursorH.getString(2).equals(getString(R.string.humorBad))) {
                    y = max / 4;
                }
                String date_nf = cursorH.getString(1);
                String[] output = date_nf.split("-");
                String date_f = output[1] + "/" + output[2];
                entries.add(new Entry(v++,y));
            }
        }
        /*entries.add(new Entry(1, 20));
        entries.add(new Entry(2, 10));
        entries.add(new Entry(3, 8));
        entries.add(new Entry(4, 40));
        entries.add(new Entry(5, 37));*/


        return entries;
    }
}