package com.petplanner.petplanner.DAO;
import android.net.Uri;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.petplanner.petplanner.Repo.Pet;
import com.petplanner.petplanner.Repo.Urina;

import java.util.List;

@Dao
public interface UriDAO {
    @Query("SELECT * FROM Urina")
    List<Urina> getAll();
    @Query("SELECT * FROM urina WHERE idPet IN (:idPet)")
    List<Urina> getAllByIds(int idPet);
    @Query("SELECT * FROM urina WHERE idPet IN (:idPet) AND TIMESTAMP IN (:date)")
    Urina findByDate(int idPet,String date);
    @Query("SELECT EXISTS(SELECT * FROM urina WHERE idPet IN(:idPET) AND TIMESTAMP IN(:date))")
    boolean hasItem(int idPET, String date);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Urina odayUrina);
    @Delete
    void delete(Urina todayUrina);
}
