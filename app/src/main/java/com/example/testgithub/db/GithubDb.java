package com.example.testgithub.db;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;


import com.example.testgithub.model.OrgRepos;

/**
 * Main database description.
 */
@Database(entities = {OrgRepos.class}, version = 1)
public abstract class GithubDb extends RoomDatabase {

    abstract public OrgRepDao orgRepDao();

}
