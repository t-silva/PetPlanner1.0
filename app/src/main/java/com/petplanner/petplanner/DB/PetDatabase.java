package com.petplanner.petplanner.DB;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.petplanner.petplanner.DAO.AtvDAO;
import com.petplanner.petplanner.DAO.HumorDAO;
import com.petplanner.petplanner.DAO.PetDao;
import com.petplanner.petplanner.DAO.UriDAO;
import com.petplanner.petplanner.DAO.UserDao;
import com.petplanner.petplanner.Repo.Atividade;
import com.petplanner.petplanner.Repo.Humor;
import com.petplanner.petplanner.Repo.Pet;
import com.petplanner.petplanner.Repo.Urina;
import com.petplanner.petplanner.Repo.Users;
@Database(entities = {Pet.class, Users.class, Urina.class, Humor.class, Atividade.class},version = 1)
public abstract class PetDatabase extends RoomDatabase {
    public abstract PetDao petDao();
    public abstract UserDao userDao();
    public abstract UriDAO uriDao();
    public abstract HumorDAO humorDAO();
    public abstract AtvDAO atvDAO();
}