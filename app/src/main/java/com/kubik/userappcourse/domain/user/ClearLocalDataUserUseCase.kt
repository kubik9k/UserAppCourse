package com.kubik.userappcourse.domain.user

import com.kubik.userappcourse.data.db.dao.DaoUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ClearLocalDataUserUseCase(private val userRepository: UserRepository) {

    suspend fun clear(daoUser: DaoUser, successful: () -> Unit) {
        withContext(Dispatchers.Default) {
            userRepository.clearLocalDataUser(daoUser)
            successful()
        }
    }

}