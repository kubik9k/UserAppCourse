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
        successful: (Boolean) -> Unit,
    ) {
        return withContext(Dispatchers.Default) {
            val user = userRepository.signInUser(data.toDataModel())
            if (user.login == "") {
                successful(false)
            } else {
                Log.d("MyLog", "SignInUserUseCase: user: ${user}")
                try {
                    userRepository.saveUserDataLocalDb(user.toDataUserModel(), daoUser)
                    successful(true)
                    Log.d("MyLog", "SignInUserUseCase: return true")
                } catch (e: Exception) {
                    Log.e("MyLog", "SignInUserUseCase: ${e.message}")
                    successful(false)
                    Log.d("MyLog", "SignInUserUseCase: return false")
                }
            }
        }
    }

}