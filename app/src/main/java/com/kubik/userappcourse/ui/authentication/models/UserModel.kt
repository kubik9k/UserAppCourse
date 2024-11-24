package com.kubik.userappcourse.ui.authentication.models

import android.os.Parcelable
import com.kubik.userappcourse.domain.models.UserDomainModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    val login: String = "",
    var password: String = "",
    var first_name: String = "",
    var last_name: String = "",
    val type_user: Int = 1,
) : Parcelable {
    fun toUserDomainModel() = UserDomainModel(login, password, first_name, last_name, type_user)
}