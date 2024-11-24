package com.kubik.userappcourse.data.db.repository

import com.kubik.userappcourse.data.db.dao.DaoUser
import com.kubik.userappcourse.data.db.db_entity.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RepositoryDatabaseUser(private val daoUser: DaoUser) {

    suspend fun updateUser(userEntity: UserEntity) {
        withContext(Dispatchers.IO) {
            daoUser.saveUser(userEntity)
        }
    }

    suspend fun getUser(successful: (UserEntity) -> Unit) {
        withContext(Dispatchers.IO) {
            successful(daoUser.getUser())
        }
    }

    suspend fun getSizeTable(successful: (Int) -> Unit) {
        withContext(Dispatchers.IO) {
            successful(daoUser.getSizeTable())
        }
    }

    suspend fun clearDataUser() {
        withContext(Dispatchers.IO) {
            daoUser.clearDataUser()
        }
    }
}