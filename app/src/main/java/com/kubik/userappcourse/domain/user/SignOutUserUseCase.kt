package com.kubik.userappcourse.domain.user

import com.kubik.userappcourse.domain.models.UserDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SignOutUserUseCase(private val userRepository: UserRepository) {

    suspend fun signUp(
        user: UserDomainModel,
        resultUserSignIn: (Boolean) -> Unit,
        loginExists: () -> Unit,
    ) {
        return withContext(Dispatchers.Default) {
            if (userRepository.checkLoginUser(login = user.login)) loginExists()
            else {
                resultUserSignIn(userRepository.signUpUser(user = user.toDataUserModel()))
            }
        }
    }

}