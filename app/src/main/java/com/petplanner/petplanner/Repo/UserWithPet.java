package com.petplanner.petplanner.Repo;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class UserWithPet {
    @Embedded
    public Users users;
    @Relation(parentColumn = "uid", entityColumn = "idOwner", entity = Users.class)
    public List<Pet> pets;
}
