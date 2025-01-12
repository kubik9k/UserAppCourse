package com.kubik.userappcourse.domain.user

import android.util.Log
import com.kubik.userappcourse.data.db.dao.DaoUser
import com.kubik.userappcourse.domain.models.SignInUserDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SignInUserUseCase(private val userRepository: UserRepository) {

    suspend fun signIn(
        data: SignInUserDomainModel,
        daoUser: DaoUser,
    ): Boolean {
        return withContext(Dispatchers.Default) {
            try {
                val user = userRepository.signInUser(data.toDataModel())
                if (user.login.isEmpty()) {
                    return@withContext false
                } else {
                    Log.d("MyLog", "SignInUserUseCase: user: $user")
                    userRepository.saveUserDataLocalDb(user.toDataUserModel(), daoUser)
                    Log.d("MyLog", "SignInUserUseCase: return true")
                    true
                }
            } catch (e: Exception) {
                Log.e("MyLog", "SignInUserUseCase: ${e.message}")
                false
            }
        }
    }

}