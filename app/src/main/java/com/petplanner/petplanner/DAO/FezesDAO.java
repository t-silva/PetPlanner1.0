package com.petplanner.petplanner.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.petplanner.petplanner.Repo.Fezes;

import java.util.List;

@Dao
public interface FezesDAO {
    @Query("SELECT * FROM Fezes")
    List<Fezes> getAll();
    @Query("SELECT * FROM fezes WHERE idPet IN (:idPet)")
    List<Fezes> getAllByIds(int idPet);
    @Query("SELECT * FROM fezes WHERE idPet IN (:idPet) AND TIMESTAMP IN (:date)")
    Fezes findByDate(int idPet, String date);
    @Query("SELECT EXISTS(SELECT * FROM fezes WHERE idPet IN(:idPET) AND TIMESTAMP IN(:date))")
    boolean hasItem(int idPET, String date);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Fezes todayFezes);
    @Delete
    void delete(Fezes todayFezes);
}
