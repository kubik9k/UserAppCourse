package com.kubik.userappcourse.domain.user

import com.kubik.userappcourse.data.db.dao.DaoUser
import com.kubik.userappcourse.data.models.SignInUserDataModel
import com.kubik.userappcourse.data.models.UserDataModel
import com.kubik.userappcourse.domain.models.UserDomainModel

interface UserRepository {

    suspend fun getLoginUser(daoUser: DaoUser): String

    //user search login successful return true
    suspend fun checkLoginUser(login: String): Boolean

    //user register successful return true
    suspend fun signUpUser(user: UserDataModel): Boolean

    suspend fun signInUser(data: SignInUserDataModel): UserDomainModel

    suspend fun updateDataUserOnServer(user: UserDataModel): Boolean

    suspend fun saveUserDataLocalDb(user: UserDataModel, daoUser: DaoUser)

    suspend fun checkSaveLocalData(daoUser: DaoUser, successful: (Boolean) -> Unit)

    suspend fun getUserDataLocalDb(daoUser: DaoUser): UserDomainModel

    suspend fun clearLocalDataUser(daoUser: DaoUser)
}