package com.petplanner.petplanner.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.petplanner.petplanner.Repo.Atividade;
import com.petplanner.petplanner.Repo.Urina;

import java.util.List;

@Dao
public interface AtvDAO {
    @Query("SELECT * FROM Atividade")
    List<Atividade> getAll();
    @Query("SELECT * FROM atividade WHERE idPet IN (:idPet)")
    List<Atividade> getAllByIds(int idPet);
    @Query("SELECT * FROM atividade WHERE idPet IN (:idPet) AND TIMESTAMP IN (:date)")
    Atividade findByDate(int idPet, String date);
    @Query("SELECT EXISTS(SELECT * FROM atividade WHERE idPet IN(:idPET) AND TIMESTAMP IN(:date))")
    boolean hasItem(int idPET, String date);
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insert(Atividade todayAtividade);
    @Delete
    void delete(Atividade todayAtividade);
}
