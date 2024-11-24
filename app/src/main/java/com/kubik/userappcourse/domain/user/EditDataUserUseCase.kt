package com.kubik.userappcourse.domain.user

import com.kubik.userappcourse.data.db.dao.DaoUser
import com.kubik.userappcourse.domain.models.UserDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EditDataUserUseCase(private val userRepository: UserRepository) {

    suspend fun edit(user: UserDomainModel, daoUser: DaoUser, successful: (Boolean) -> Unit) {
        withContext(Dispatchers.Default) {
            val resultUpdateDataOnServer =
                userRepository.updateDataUserOnServer(user.toDataUserModel())
            if (resultUpdateDataOnServer) {
                userRepository.saveUserDataLocalDb(user.toDataUserModel(), daoUser)
                successful(true)
            } else successful(false)
        }
    }

}