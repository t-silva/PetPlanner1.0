package com.petplanner.petplanner.DAO;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.petplanner.petplanner.Repo.Pet;
import com.petplanner.petplanner.Repo.Users;
import java.util.List;

@Dao
public interface PetDao {
    @Query("SELECT * FROM pet")
    List<Pet> getAll();
    @Query("SELECT * FROM pet WHERE pID IN (:pID)")
    List<Pet> loadAllByIds(int pID);

    @Query("SELECT * FROM pet WHERE pID IN (:id)")
    Pet findById(int id);
    @Query("SELECT * FROM pet WHERE idOwner IN (:idOwner)")
    List<Pet> getPetsByOwner(int idOwner);

    @Insert
    void insert(Pet pet);

    @Delete
    void delete(Pet pet);
}
