package com.petplanner.petplanner.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.petplanner.petplanner.Repo.Humor;
import com.petplanner.petplanner.Repo.Urina;

import java.util.List;

@Dao
public interface HumorDAO {
    @Query("SELECT * FROM humor")
    List<Humor> getAll();
    @Query("SELECT * FROM humor WHERE idPet IN (:idPet)")
    List<Humor> getAllByIds(int idPet);
    @Query("SELECT * FROM humor WHERE idPet IN (:idPet) AND TIMESTAMP IN (:date)")
    Humor findByDate(int idPet, String date);
    @Query("SELECT EXISTS(SELECT * FROM humor WHERE idPet IN(:idPET) AND TIMESTAMP IN(:date))")
    boolean hasItem(int idPET, String date);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Humor todayHumor);
    @Delete
    void delete(Humor todayHumor);
}
