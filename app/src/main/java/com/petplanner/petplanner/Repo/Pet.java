package com.petplanner.petplanner.Repo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Users.class,
                                parentColumns = "uid",
                                childColumns = "idOwner",
                                onDelete = ForeignKey.CASCADE))
public class Pet {
    @PrimaryKey(autoGenerate = true)
    public int pID;
    public String name;
    public String raca;
    public String sexo;
    public int age;
    @ColumnInfo(name = "idOwner")
    public int idOwner;
    @ColumnInfo(name = "imgResID")
    public int imgResID;

    public void insertPet(int idOwner,String name, String raca, String sexo, int age, int imgResID){
        this.idOwner = idOwner;
        this.name = name;
        this.raca = raca;
        this.sexo = sexo;
        this.age = age;
        this.imgResID = imgResID;
    }
    public void setIdOwner(int idOwner){
        this.idOwner = idOwner;
    }
    public int getIdOwner(){
        return this.idOwner;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }

    public void setRaca(String raca){
        this.raca = raca;
    }
    public String getRaca(){
        return this.raca;
    }
    public void setAge(int age){
        this.age = age;
    }
    public int getAge(){
        return this.age;
    }
    public void setImgResID(int imgResID){
        this.imgResID = imgResID;
    }
    public int getImgResID(){
        return this.imgResID;
    }
    public void setSexo(String sexo){
        this.sexo = sexo;
    }
    public String getSexo(){
        return this.sexo;
    }
    public int getpID(){
        return this.pID;
    }
}
