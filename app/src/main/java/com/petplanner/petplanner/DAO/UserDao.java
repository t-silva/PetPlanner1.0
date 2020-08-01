package com.petplanner.petplanner.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.petplanner.petplanner.Repo.Users;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM users")
    List<Users> getAll();

    @Query("SELECT * FROM users WHERE uid IN (:userIds)")
    List<Users> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM users WHERE first_name LIKE :first AND " +
            "last_name LIKE :last LIMIT 1")
    Users findByName(String first, String last);
    @Query("SELECT * FROM users WHERE uid IN (:id)")
    Users findById(int id);

    @Insert
    void insert(Users users);

    @Delete
    void delete(Users user);
}
