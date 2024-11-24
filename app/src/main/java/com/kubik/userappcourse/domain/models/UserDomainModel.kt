package com.kubik.userappcourse.domain.models

import com.kubik.userappcourse.data.models.UserDataModel
import com.kubik.userappcourse.ui.authentication.models.UserModel

data class UserDomainModel(
    val login: String = "",
    val password: String = "",
    val first_name: String = "",
    val last_name: String = "",
    val type_user: Int = 1,
){
    fun toDataUserModel() = UserDataModel(login, password, first_name, last_name, type_user)

    fun toUi() = UserModel(login, password, first_name, last_name, type_user)
}