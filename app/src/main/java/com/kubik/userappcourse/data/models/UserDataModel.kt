package com.kubik.userappcourse.data.models

import com.kubik.userappcourse.data.db.db_entity.UserEntity
import com.kubik.userappcourse.domain.models.UserDomainModel

data class UserDataModel(
    var login: String = "",
    var password: String = "",
    var first_name: String = "",
    var last_name: String = "",
    var type_user: Int = 1,
) {
    fun toDomainModel() = UserDomainModel(login, password, first_name, last_name, type_user)

    fun toEntity() = UserEntity(
        login = login,
        password = password,
        first_name = first_name,
        last_name = last_name,
        type_user = type_user
    )
}
