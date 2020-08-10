package com.petplanner.petplanner.Repo;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Pet.class,
                                parentColumns = "pID",
                                childColumns = "idPet",
                                onDelete = ForeignKey.CASCADE))
public class Agua {
    @PrimaryKey @NonNull
    public String TIMESTAMP;
    public int idPet;
    public boolean STATUS;
    public void insertFezes(int idPet,String date, boolean status){
        this.idPet = idPet;
        this.TIMESTAMP = date;
        this.STATUS = status;
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
    public void setIdPet(int idPet){this.idPet = idPet;}
    public boolean getStatus(){
        return this.STATUS;
    }
}
