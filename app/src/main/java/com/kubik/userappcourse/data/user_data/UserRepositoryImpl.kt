package com.kubik.userappcourse.data.user_data

import com.kubik.userappcourse.data.db.dao.DaoUser
import com.kubik.userappcourse.data.db.repository.RepositoryDatabaseUser
import com.kubik.userappcourse.data.models.SignInUserDataModel
import com.kubik.userappcourse.data.models.UserDataModel
import com.kubik.userappcourse.domain.models.UserDomainModel
import com.kubik.userappcourse.domain.user.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class UserRepositoryImpl : UserRepository {

    override suspend fun getLoginUser(daoUser: DaoUser): String {
        return suspendCoroutine { continuation ->
            runBlocking(Dispatchers.Default) {
                RepositoryDatabaseUser(daoUser).getUser {
                    continuation.resume(it.login)
                }
            }
        }
    }

    override suspend fun checkLoginUser(login: String): Boolean {
        return suspendCoroutine { corutinuation ->
            runBlocking(Dispatchers.Default) {
                CheckExistsLoginUser().checkLogin(login) { result ->
                    corutinuation.resume(result)
                }
            }
        }
    }

    override suspend fun signUpUser(user: UserDataModel): Boolean {
        return suspendCoroutine { corutinuation ->
            runBlocking(Dispatchers.Default) {
                SignUpUser().signUp(user) {
                    corutinuation.resume(it)
                }
            }
        }
    }

    override suspend fun signInUser(data: SignInUserDataModel): UserDomainModel {
        return suspendCoroutine { corutinuation ->
            runBlocking(Dispatchers.Default) {
                SignInUser().signIn(data) {
                    corutinuation.resume(it.toDomainModel())
                }
            }
        }
    }

    override suspend fun updateDataUserOnServer(user: UserDataModel): Boolean {
        return suspendCoroutine { continuation ->
            runBlocking(Dispatchers.IO) {
                UpdateUserData().update(user) {
                    continuation.resume(it)
                }
            }
        }
    }

    override suspend fun saveUserDataLocalDb(user: UserDataModel, daoUser: DaoUser) {
        withContext(Dispatchers.Default) {
            RepositoryDatabaseUser(daoUser).updateUser(user.toEntity())
        }
    }

    override suspend fun checkSaveLocalData(daoUser: DaoUser, successful: (Boolean) -> Unit) {
        withContext(Dispatchers.Default) {
            RepositoryDatabaseUser(daoUser).getSizeTable {
                successful(it > 0)
            }
        }
    }

    override suspend fun getUserDataLocalDb(daoUser: DaoUser): UserDomainModel {
        return suspendCoroutine { continuation ->
            runBlocking(Dispatchers.IO) {
                RepositoryDatabaseUser(daoUser).getUser {
                    continuation.resume(it.toDataUser().toDomainModel())
                }
            }
        }
    }

    override suspend fun clearLocalDataUser(daoUser: DaoUser) {
        withContext(Dispatchers.IO) {
            RepositoryDatabaseUser(daoUser).clearDataUser()
        }
    }

}