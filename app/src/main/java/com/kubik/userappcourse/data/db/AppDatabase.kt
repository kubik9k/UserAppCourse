package com.kubik.userappcourse.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kubik.userappcourse.data.db.converter.UriConverter
import com.kubik.userappcourse.data.db.dao.DaoProduct
import com.kubik.userappcourse.data.db.dao.DaoUser
import com.kubik.userappcourse.data.db.db_entity.BuyProductEntity
import com.kubik.userappcourse.data.db.db_entity.UserEntity

@Database(version = 1, entities = [UserEntity::class, BuyProductEntity::class])
@TypeConverters(UriConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getDaoUser(): DaoUser
    abstract fun getDaoProduct(): DaoProduct
}