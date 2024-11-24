package com.kubik.userappcourse.domain.user

import com.kubik.userappcourse.data.db.dao.DaoUser
import com.kubik.userappcourse.ui.authentication.models.UserModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetUserDataUseCase(private val userRepository: UserRepository) {

    suspend fun getData(daoUser: DaoUser, successful: (user: UserModel) -> Unit) {
        withContext(Dispatchers.Default) {
            successful(userRepository.getUserDataLocalDb(daoUser).toUi())
        }
    }

}