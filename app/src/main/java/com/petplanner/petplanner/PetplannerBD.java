package com.petplanner.petplanner;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PetplannerBD extends SQLiteOpenHelper {
    private static final String NOMEBD = "petplaner";
    private static final int VERSAOBD = 1;
    PetplannerBD(Context context){
        super(context, NOMEBD, null, VERSAOBD);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        criaBancoPets(db);
        insereDadosPets(db);
        insereDadosHumor(db);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
    private void criaBancoPets(SQLiteDatabase db) {
        String sqlPet = "CREATE TABLE PETS (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "NOME STRING, " +
                "RACA STRING, " +
                "IDADE STRING, " +
                "SEXO STRING, " +
                "IMGRESID STRING " +
                ")";
        db.execSQL(sqlPet);
        String sqlHumor = "CREATE TABLE HUMOR (" +
                "_idPET INTEGER , " +
                "TIMESTAMP INTEGER, " +
                "STATUS STRING, " +
                "FOREIGN KEY (_idPET) REFERENCES PETS(_id)" +
                ")";
        db.execSQL(sqlHumor);
        String sqlFezes = "CREATE TABLE FEZES (" +
                "_idPET INTEGER , " +
                "TIMESTAMP INTEGER, " +
                "STATUS STRING, " +
                "OBS STRING," +
                "FOREIGN KEY (_idPET) REFERENCES PETS(_id)" +
                ")";
        db.execSQL(sqlFezes);
        String sqlUrina = "CREATE TABLE URINA (" +
                "_idPET INTEGER , " +
                "TIMESTAMP INTEGER, " +
                "FEZ INTEGER, " +
                "OBS STRING, " +
                "FOREIGN KEY (_idPET) REFERENCES PETS(_id)" +
                ")";
        db.execSQL(sqlUrina);
        String sqlAtv = "CREATE TABLE ATIVIDADE (" +
                "_idPET INTEGER , " +
                "TIMESTAMP INTEGER, " +
                "TIPO STRING, " +
                "TEMPO INTEGER, " +
                "FOREIGN KEY (_idPET) REFERENCES PETS(_id)" +
                ")";
        db.execSQL(sqlAtv);
        String sqlAlim = "CREATE TABLE ALIMENTACAO (" +
                "_idPET INTEGER , " +
                "TIMESTAMP INTEGER, " +
                "TIPO STRING, " +
                "QUANTIDADE REAL, " +
                "FOREIGN KEY (_idPET) REFERENCES PETS(_id)" +
                ")";
        db.execSQL(sqlAlim);
        String sqlAgua = "CREATE TABLE AGUA (" +
                "_idPET INTEGER , " +
                "TIMESTAMP INTEGER, " +
                "TOMOU INTEGER, " +
                "FOREIGN KEY (_idPET) REFERENCES PETS(_id)" +
                ")";
        db.execSQL(sqlAgua);
        String sqlAtual = "CREATE TABLE ATUAL (" +
                "_id INTEGER PRIMARY KEY,"+
                "_idAtual INTEGER" +
                ")";
        //Criando banco
        db.execSQL(sqlAtual);
        ContentValues cvAtual = new ContentValues();
        cvAtual.put("_id", 1);
        cvAtual.put("_idAtual", 2);
        db.insert("ATUAL",null,cvAtual);
    }
    private void insereDadosPets(SQLiteDatabase db) {
        insertPet(db, "Lucky","Vira Lata","4a 8m","Macho", R.drawable.a1);
        insertPet(db, "Rex","Box","8m","Macho",R.drawable.a2);
        insertPet(db, "Totó","Siamês","1a 6m","Fêmea", R.drawable.a4);
        insertPet(db, "talita","Rusk","2a 4m","LGTBQI+", R.drawable.a5);
        //insertPet(db, "Parangolíssima","Doberman","4a 3m","Fêmea","Normal", "Escura", "Parque", "Mole", R.drawable.a6);
        //insertPet(db, "Mel","Vira Lata","5a 2m","Fêmea","Bravo", "Clara", "Parque", "Vermes", R.drawable.a7);
        //insertPet(db, "Snow","Samoieda","1a 8m","Macho","Bom", "Não fez", "Ibirapuera", "Normal", R.drawable.a8);
    }
    private void insereDadosHumor(SQLiteDatabase db) {
       // insertHumor(db, 1,"Excelente");
        //insertPet(db, "Parangolíssima","Doberman","4a 3m","Fêmea","Normal", "Escura", "Parque", "Mole", R.drawable.a6);
        //insertPet(db, "Mel","Vira Lata","5a 2m","Fêmea","Bravo", "Clara", "Parque", "Vermes", R.drawable.a7);
        //insertPet(db, "Snow","Samoieda","1a 8m","Macho","Bom", "Não fez", "Ibirapuera", "Normal", R.drawable.a8);
    }
    private void insertHumor(SQLiteDatabase db,
                           int _idPET,  String STATUS) {
        ContentValues cvHumor = new ContentValues();
        cvHumor.put("_idPet", _idPET);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        cvHumor.put("TIMESTAMP", dateFormat.format(date));
        cvHumor.put("STATUS", STATUS);
        db.insert("HUMOR", null, cvHumor);
    }
    private void insertPet(SQLiteDatabase db,
                           String nome, String raca, String idade, String sexo, int imgResID) {
        ContentValues cvPet = new ContentValues();
        cvPet.put("NOME", nome);
        cvPet.put("RACA", raca);
        cvPet.put("IDADE", idade);
        cvPet.put("SEXO", sexo);
        cvPet.put("IMGRESID", imgResID);
        db.insert("PETS", null, cvPet);
    }
}