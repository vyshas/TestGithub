package com.example.testgithub.db


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.testgithub.model.OrgRepos

@Dao
abstract class OrgRepDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertOrgRepos(orgRepos: List<OrgRepos>)


    @Query("SELECT * FROM OrgRepos")
    abstract fun loadOrgRepositories(): LiveData<List<OrgRepos>>

}
