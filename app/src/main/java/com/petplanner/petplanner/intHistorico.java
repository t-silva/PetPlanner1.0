package com.petplanner.petplanner;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.CombinedChart;
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
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

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
        final int[] idPet = {(Integer) getIntent().getExtras().get(EXTRA_idPET)};
        setContentView(R.layout.historico_activity);
        LinearLayout lnChoose = findViewById(R.id.lnChoose);
        carrega(idPet);
        lnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final BottomSheetDialog bottomSheetChoose = new BottomSheetDialog(
                        intHistorico.this,R.style.BottonSheetDialogTheme
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
                        carrega(idPet);
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
    private void carrega(int idPet[]){
        try{
            petplannerDB = new PetplannerBD(getApplicationContext());
            bd = petplannerDB.getReadableDatabase();
            //Gerando cursor para perfil
            cursor = bd.query(
                    "PETS",
                    new String[] {"_id","NOME","RACA","IDADE", "SEXO","IMGRESID"},
                    "_id = ?",
                    new String[]{Integer.toString(idPet[0])},
                    null,
                    null,
                    null,
                    null);
            if (cursor.moveToFirst() ) {
                CircleImageView imgPerfil = findViewById(R.id.fotoCapa);
                imgPerfil.setImageResource(cursor.getInt(5));
                TextView txtNome = findViewById(R.id.txtNome);
                txtNome.setText(cursor.getString(1));
                TextView txtRaca = findViewById(R.id.txtRaca);
                txtRaca.setText(cursor.getString(2));
                TextView txtIdade = findViewById(R.id.txtIdade);
                txtIdade.setText(cursor.getString(3));
                TextView txtSexo = findViewById(R.id.txtSexo);
                txtSexo.setText(cursor.getString(4));
                Toast.makeText(this,cursor.getString(4),Toast.LENGTH_SHORT).show();
            }
            //RECUPERANDO MAIOR VALOR
            cursorMax = bd.query(
                    "ATIVIDADE",
                    new String[]{"_idPet","TEMPO"},
                    "_idPet = ? AND TEMPO=(SELECT MAX(TEMPO) FROM ATIVIDADE )",
                    new String[]{Integer.toString(idPet[0])},
                    null,
                    null,
                    null,
                    null
            );
            if(cursorMax.moveToFirst()) {
                max = cursorMax.getFloat(1);
//                Toast.makeText(this,"Max: "+ max,Toast.LENGTH_LONG).show();
            }
            else {
                max =1;
//                Toast.makeText(this,"NAO ACHOU MAX",Toast.LENGTH_LONG).show();
            }
            // RECUPERANDO MAIOR VALOR
                
            // Gerando cursor para Humor
            cursorH = bd.query(
                    "HUMOR",
                    new String[]{"_id", "TIMESTAMP","STATUS"},
                    "_id = ?",
                    new String[]{Integer.toString(idPet[0])},
                    null,
                    null,
                    null,
                    null);

            cursorAtv = bd.query(
                    "ATIVIDADE",
                    new String[]{"_idPet", "TIMESTAMP","TEMPO"},
                    "_idPet = ?",
                    new String[]{Integer.toString(idPet[0])},
                    null,
                    null,
                    null,
                    null);



            /*      Iniciando Gráficos    */

            mChart = (CombinedChart) findViewById(R.id.cmbcHumorAtv);
            mChart.getDescription().setText("");
            mChart.setDrawGridBackground(false); // Desenha o grid
            mChart.setDrawBarShadow(false);
            mChart.setEnabled(false);
            //mChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
            //mChart.getAxisLeft().setDrawGridLines(false);
            mChart.getXAxis().setDrawGridLines(false);
            //mChart.getXAxis().setSpaceMax(0.1f);
            mChart.setHighlightFullBarEnabled(true);
            mChart.setViewPortOffsets(7f,50f,50f,100f);
            mChart.setDrawOrder(new CombinedChart.DrawOrder[]{ //Definindo ordem (linha sobre Barras)
                    CombinedChart.DrawOrder.BAR, CombinedChart.DrawOrder.LINE
            });

            /* Lidando com legendas */

            Legend l = mChart.getLegend();
            l.setWordWrapEnabled(true);
            l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
            l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
            l.setOrientation(Legend.LegendOrientation.HORIZONTAL);

            /*      Fim     */

            /*      Lidando com os Eixos     */

            YAxis rightAxis = mChart.getAxisRight();
            rightAxis.setDrawGridLines(false);
            rightAxis.setDrawAxisLine(false);
            rightAxis.setAxisMinimum(1f); // this replaces setStartAtZero(true)
            rightAxis.setGranularity(5f);
            YAxis leftAxis = mChart.getAxisLeft();
            leftAxis.setDrawGridLines(true);
            leftAxis.setAxisMinimum(1f); // this replaces setStartAtZero(true)



            mChart.getAxisLeft().setAxisMinimum(0.1f);
//            mChart.getAxisRight().setAxisMinimum(0.1f);
            //           mChart.getXAxis().setAxisMinimum(0.1f);



            /*      FIM         */

            /*      FIM     */

            CombinedData data = new CombinedData();
            data.setData(generateLineData("Humor"));
            data.setData(generateBarData("Atividade Física"));
            XAxis xAxis = mChart.getXAxis();
            YAxis yAxis = mChart.getAxisLeft();
            yAxis.setAxisMaximum(4.4f);
            yAxis.enableGridDashedLine(1f,4f,10f);
            //yAxis.setDrawGridLines(true);
            //yAxis.setDrawGridLinesBehindData(true);

            yAxis.setGranularity(1f);
            yAxis.setDrawLabels(false);

            yAxis.setDrawAxisLine(false);
            // yAxis.setSpaceMax(0f);
            xAxis.setGranularity(1f);

            /*      Lidando com labels inferiores   */
            final ArrayList<String> mDatas;
            mDatas = createLabels(cursorAtv);
            xAxis.setValueFormatter(new IndexAxisValueFormatter(mDatas));
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setAxisMaximum(data.getXMax() + 0.95f);
            xAxis.setAxisMinimum(0f);
            xAxis.setDrawAxisLine(false);
            mChart.animateY(1000);
            mChart.setData(data);
            mChart.invalidate();
            /*      Fim Iniciando Gráficos       */
        } catch (SecurityException e){
            Toast.makeText(this, "Banco de dados Não Disponível",Toast.LENGTH_SHORT).show();
        }
    }


    private ArrayList<String> createLabels(Cursor cursorLabel) {
        ArrayList<String> labels = new ArrayList<>();
        labels.add("");
        if(cursorLabel.moveToFirst()) {
            for (cursorLabel.moveToFirst(); !cursorLabel.isAfterLast(); cursorLabel.moveToNext()) {
                String date_nf = cursorLabel.getString(1);
                String[] output = date_nf.split("-");
                String date_f = output[2] + "/" + output[1];
                labels.add(date_f);
            }
        }
        return labels;
    }
    private BarData generateBarData(String label){
        ArrayList<BarEntry> entries  = new ArrayList<BarEntry>();
        entries = getBarEntries(entries);
        BarDataSet set1 = new BarDataSet(entries,label);
        set1.setAxisDependency(YAxis.AxisDependency.RIGHT);
        set1.setGradientColor(Color.rgb(73,99,255),Color.rgb(123,0,143));
        set1.setDrawValues(false);

        BarData d = new BarData(set1);
        d.setBarWidth(0.15f);
        return d;
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
            float a ;
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
        set.setDrawValues(false);
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER); //estilo da linha
        set.setAxisDependency(YAxis.AxisDependency.LEFT); //dependencia do eixo Y
        d.addDataSet(set);
        return d;
    }
    private ArrayList<Entry> getLineEntriesData(ArrayList<Entry> entries) {
        if (cursorH.moveToFirst()) {
            float y = 0f;
            int v = 1;
            for (cursorH.moveToFirst(); !cursorH.isAfterLast(); cursorH.moveToNext()) {
                if (cursorH.getString(2).equals(getString(R.string.humorExc))) {
                    y = 4f;
                } else if (cursorH.getString(2).equals(getString(R.string.humorHappy))) {
                    y = 3f;
                } else if (cursorH.getString(2).equals(getString(R.string.humorOk))) {
                    y = 2f;
                } else if (cursorH.getString(2).equals(getString(R.string.humorBad))) {
                    y = 1f;
                }
                entries.add(new Entry(v++,y));
            }
        }
        return entries;
    }
}