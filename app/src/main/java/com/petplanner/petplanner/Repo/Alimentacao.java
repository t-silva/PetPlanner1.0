package com.petplanner.petplanner.Repo;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Pet.class,
                                parentColumns = "pID",
                                childColumns = "idPet",
                                onDelete = ForeignKey.CASCADE))
public class Alimentacao {
    @PrimaryKey @NonNull
    public String TIMESTAMP;
    public int idPet;
    public String TIPO;
    public Float QTD;


    public void insertAli(int idPet,String date, String tipo, float quantidade){
        this.idPet = idPet;
        this.TIMESTAMP = date;
        this.TIPO = tipo;
        this.QTD = quantidade;
    }
    public void setTIMESTAMP(String date) {
        this.TIMESTAMP = date;
    }
    public String getTimeStamp(){
        return this.TIMESTAMP;
    }
    public void setTipo(String status){
        this.TIPO = status;
    }
    public void setIdPet(int idPet){this.idPet = idPet;}
    public String getTIPO(){
        return this.TIPO;
    }
    public void setQTD(Float quantidade){
        this.QTD = quantidade;
    }
    public float getQTD(){
        return this.QTD;
    }
}
