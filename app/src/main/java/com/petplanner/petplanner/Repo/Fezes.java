package com.petplanner.petplanner.Repo;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Pet.class,
                                parentColumns = "pID",
                                childColumns = "idPet",
                                onDelete = ForeignKey.CASCADE))
public class Fezes {
    @PrimaryKey @NonNull
    public String TIMESTAMP;
    public int idPet;
    public String STATUS;
    public String OBS;
    public void insertFezes(int idPet,String date, String status, String obs){
        this.idPet = idPet;
        this.TIMESTAMP = date;
        this.STATUS = status;
        this.OBS = obs;
    }
    public void setTIMESTAMP(String date) {
        this.TIMESTAMP = date;
    }
    public String getTimeStamp(){
        return this.TIMESTAMP;
    }
    public void setStatus(String status){
        this.STATUS = status;
    }
    public void setIdPet(int idPet){this.idPet = idPet;}
    public String getStatus(){
        return this.STATUS;
    }
    public void setObs(String obs){
        this.OBS = obs;
    }
    public String getObs(){
        return this.OBS;
    }
}
