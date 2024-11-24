package com.kubik.userappcourse.domain.models

import com.kubik.userappcourse.data.models.SignInUserDataModel

data class SignInUserDomainModel(
    val login: String = "",
    val password: String = "",
) {
    fun toDataModel() = SignInUserDataModel(login, password)
}