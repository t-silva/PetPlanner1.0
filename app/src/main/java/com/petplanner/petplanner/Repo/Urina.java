package com.petplanner.petplanner.Repo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Pet.class,
                                parentColumns = "pID",
                                childColumns = "idPet",
                                onDelete = ForeignKey.CASCADE))
public class Urina {
    @PrimaryKey (autoGenerate = true)
    public int idUri;
    public int idPet;
    public String TIMESTAMP;
    public boolean STATUS;
    public String OBS;

    public void insertUri(int idPet,String date, boolean status, String obs){
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
    public void setStatus(boolean status){
        this.STATUS = status;
    }
    public boolean getStatus(){
        return this.STATUS;
    }
    public void setObs(String obs){
        this.OBS = obs;
    }
    public String getObs(){
        return this.OBS;
    }
}
