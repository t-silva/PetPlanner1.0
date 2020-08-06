package com.petplanner.petplanner.Repo;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Users {
    @PrimaryKey(autoGenerate = true)
    public int uid;
    //@ColumnInfo(name = "first_name")
    public String firstName;
   // @ColumnInfo(name = "last_name")
    public String lastName;
    @ColumnInfo(name = "petAtual")
    public int petAtual;

    public void insertUser(String firstName,String lastName ){
        this.firstName = firstName;
        this.lastName = lastName;
    }
    public String getFirstName() {
        return this.firstName;
    }
    public int getPetAtual(){
        return this.petAtual;
    }
    public int getUid(){
        return this.uid;
    }
    public void setFirstName(String name){
        this.firstName = name;
    }
    public void setPetAtual(int atual){
        this.petAtual = atual;
    }
    public String getLastName(){

        return this.lastName;
    }
    public void setUid(int uid){

        this.uid = uid;
    }
}