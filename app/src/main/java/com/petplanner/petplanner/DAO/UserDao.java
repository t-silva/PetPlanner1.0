package com.petplanner.petplanner.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.petplanner.petplanner.Repo.Pet;
import com.petplanner.petplanner.Repo.Users;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM users")
    List<Users> getAll();

    @Query("SELECT * FROM users WHERE uid IN (:userIds)")
    List<Users> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM users WHERE firstname LIKE :first AND " +
            "lastname LIKE :last LIMIT 1")
    Users findByName(String first, String last);
    @Query("SELECT * FROM users WHERE uid IN (:id)")
    Users findById(int id);
    @Query("SELECT firstname from users where uid IN(:uid)")
    String getFirstName(int uid);
    @Query("SELECT petAtual FROM users WHERE uid IN(:uid)")
    int getAtual(int uid);

    @Transaction
    @Query("SELECT * FROM Pet WHERE idOwner = :uid")
    List<Pet> getAllPets(int uid);
    @Insert
    void insert(Users users);
    @Query("UPDATE users SET petAtual=:petAtual WHERE uid = :uid")
    void updateAtual(int petAtual,int uid);
    @Update(entity = Users.class)
    void updateUser(Users user);

    @Delete
    void delete(Users user);
}
