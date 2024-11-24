package com.kubik.userappcourse

import android.content.Context
import androidx.room.Room
import com.kubik.userappcourse.data.db.AppDatabase

object DependenciesDatabase {


    private lateinit var applicationContext: Context

    fun init(context: Context) {
        applicationContext = context
    }

    private val appDatabase: AppDatabase by lazy {
        Room.databaseBuilder(applicationContext, AppDatabase::class.java, "user_app.db")
//            .createFromAsset("room_article.db")
            .build()
    }

    val daoUser by lazy { appDatabase.getDaoUser() }
    val daoProduct by lazy { appDatabase.getDaoProduct() }

}