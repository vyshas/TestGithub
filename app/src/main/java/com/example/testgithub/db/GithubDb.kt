package com.example.testgithub.db


import com.example.testgithub.model.OrgRepos

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Main database description.
 */
@Database(entities = [OrgRepos::class], version = 1, exportSchema = false)
abstract class GithubDb : RoomDatabase() {

    abstract fun orgRepDao(): OrgRepDao

}
