package com.kubik.userappcourse.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kubik.userappcourse.data.db.db_entity.UserEntity

@Dao
interface DaoUser {

    @Insert(entity = UserEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun saveUser(userEntity: UserEntity)

    @Query("SELECT * FROM user LIMIT 1")
    fun getUser(): UserEntity

    @Query("SELECT COUNT(*) FROM user;")
    fun getSizeTable(): Int

    @Query("DELETE FROM user")
    fun clearDataUser()
}