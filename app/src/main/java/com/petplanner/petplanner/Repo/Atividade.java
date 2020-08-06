package com.petplanner.petplanner.Repo;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Pet.class,
                                parentColumns = "pID",
                                childColumns = "idPet",
                                onDelete = ForeignKey.CASCADE))
public class Atividade {
    @PrimaryKey @NonNull
    public String TIMESTAMP;
    public int idPet;
    public String TIPO;
    public int TEMPO;

    public void insertAtv(int idPet,String date, String tipo, int tempo){
        this.idPet = idPet;
        this.TIMESTAMP = date;
        this.TIPO = tipo;
        this.TEMPO = tempo;
    }

    public void setTIMESTAMP(String date) {
        this.TIMESTAMP = date;
    }
    public String getTimeStamp(){
        return this.TIMESTAMP;
    }
    public void setTipo(String tipo){
        this.TIPO = tipo;
    }
    public String getTipo(){
        return this.TIPO;
    }
    public void setTempo(int tempo){
        this.TEMPO = tempo;
    }
    public int getTempo(){
        return this.TEMPO;
    }
    public void setIdPet(int idPet){this.idPet = idPet;}
    public int getIdPet(){return this.idPet;}
}
