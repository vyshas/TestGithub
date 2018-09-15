package com.example.testgithub.db;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.testgithub.model.OrgRepos;

import java.util.List;

@Dao
public abstract class OrgRepDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertOrgRepos(List<OrgRepos> orgRepos);

    @Query("SELECT * FROM OrgRepos")
    public abstract LiveData<List<OrgRepos>> loadOrgRepositories();

}
