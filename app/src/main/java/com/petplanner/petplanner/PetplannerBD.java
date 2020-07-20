package com.petplanner.petplanner;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PetplannerBD extends SQLiteOpenHelper {
    private static final String NOMEBD = "biblioteca";
    private static final int VERSAOBD = 1;
    private String sql;
    PetplannerBD(Context context){
        super(context, NOMEBD, null, VERSAOBD);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        criaBancoDados(db);
        insereDadosInciais(db);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
    private void criaBancoDados(SQLiteDatabase db) {
        sql = "CREATE TABLE PETS (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "NOME STRING, " +
                "RACA STRING, " +
                "IDADE STRING, " +
                "SEXO STRING, " +
                "HUMOR STRING, " +
                "URINA STRING, " +
                "ATV STRING, " +
                "FEZES STRING, " +
                "IMGRESID STRING " +

                ")";
        db.execSQL(sql);
    }
    private void insereDadosInciais(SQLiteDatabase db) {
        insertPet(db, "Lucky","Vira Lata","4a 8m","Macho","Excelente", "Normal", "Passeio", "Normal", R.drawable.a1);
        insertPet(db, "Rex","Box","8m","Macho","Normal", "Escura", "Parque", "Mole", R.drawable.a2);
        insertPet(db, "Totó","Siamês","1a 6m","Fêmea","Bom", "Não fez", "Ibirapuera", "Normal", R.drawable.a4);
        insertPet(db, "talita","Rusk","2a 4m","LGTBQI+","Excelente", "Normal", "Passeio", "Normal", R.drawable.a5);
        insertPet(db, "Parangolíssima","Doberman","4a 3m","Fêmea","Normal", "Escura", "Parque", "Mole", R.drawable.a6);
        insertPet(db, "Mel","Vira Lata","5a 2m","Fêmea","Bravo", "Clara", "Parque", "Vermes", R.drawable.a7);
        insertPet(db, "Snow","Samoieda","1a 8m","Macho","Bom", "Não fez", "Ibirapuera", "Normal", R.drawable.a8);
    }
    private void insertPet(SQLiteDatabase db,
                           String nome, String raca, String idade, String sexo, String humor, String urina, String atividade, String fezes, int imgResID) {
        ContentValues cvLivro = new ContentValues();
        cvLivro.put("NOME", nome);
        cvLivro.put("RACA", raca);
        cvLivro.put("IDADE", idade);
        cvLivro.put("SEXO", sexo);
        cvLivro.put("HUMOR", humor);
        cvLivro.put("URINA", urina);
        cvLivro.put("ATV", atividade);
        cvLivro.put("FEZES", fezes);
        cvLivro.put("IMGRESID", imgResID);
        db.insert("PETS", null, cvLivro);
    }
}