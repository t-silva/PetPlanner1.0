package com.petplanner.petplanner.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.petplanner.petplanner.Repo.Alimentacao;

import java.util.List;

@Dao
public interface AliDAO {
    @Query("SELECT * FROM alimentacao")
    List<Alimentacao> getAll();
    @Query("SELECT * FROM alimentacao WHERE idPet IN (:idPet)")
    List<Alimentacao> getAllByIds(int idPet);
    @Query("SELECT * FROM alimentacao WHERE idPet IN (:idPet) AND TIMESTAMP IN (:date)")
    Alimentacao findByDate(int idPet, String date);
    @Query("SELECT EXISTS(SELECT * FROM alimentacao WHERE idPet IN(:idPET) AND TIMESTAMP IN(:date))")
    boolean hasItem(int idPET, String date);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Alimentacao todayAlimentacao);
    @Delete
    void delete(Alimentacao todayAlimentacao);
}
