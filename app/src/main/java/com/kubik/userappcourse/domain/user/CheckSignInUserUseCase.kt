package com.kubik.userappcourse.domain.user

import com.kubik.userappcourse.data.db.dao.DaoUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CheckSignInUserUseCase(private val userRepository: UserRepository) {

    suspend fun checkSignIn(daoUser: DaoUser, successful: (Boolean) -> Unit) {
        withContext(Dispatchers.Default) {
            userRepository.checkSaveLocalData(daoUser) {
                successful(it)
            }
        }
    }

}