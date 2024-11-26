package com.kubik.userappcourse.ui.authentication.models

import com.kubik.userappcourse.domain.models.SignInUserDomainModel

data class SignInUserModel(
    val login: String = "",
    val password: String = "",
) {
    fun toDomainModel() = SignInUserDomainModel(login, password)
}