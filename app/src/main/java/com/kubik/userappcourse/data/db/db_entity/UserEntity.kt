package com.kubik.userappcourse.data.db.db_entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kubik.userappcourse.data.models.UserDataModel

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 1,
    @ColumnInfo(name = "login")
    var login: String = "",
    @ColumnInfo(name = "password")
    var password: String = "",
    @ColumnInfo(name = "first_name")
    var first_name: String = "",
    @ColumnInfo(name = "last_name")
    var last_name: String = "",
    @ColumnInfo(name = "type_user")
    var type_user: Int = 2,
) {
    fun toDataUser() = UserDataModel(login, password, first_name, last_name, type_user)
}
