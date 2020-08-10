package com.petplanner.petplanner.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.petplanner.petplanner.Repo.Agua;

import java.util.List;

@Dao
public interface AguaDAO {
    @Query("SELECT * FROM agua")
    List<Agua> getAll();
    @Query("SELECT * FROM agua WHERE idPet IN (:idPet)")
    List<Agua> getAllByIds(int idPet);
    @Query("SELECT * FROM agua WHERE idPet IN (:idPet) AND TIMESTAMP IN (:date)")
    Agua findByDate(int idPet, String date);
    @Query("SELECT EXISTS(SELECT * FROM agua WHERE idPet IN(:idPET) AND TIMESTAMP IN(:date))")
    boolean hasItem(int idPET, String date);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Agua todayAgua);
    @Delete
    void delete(Agua todayAgua);
}
