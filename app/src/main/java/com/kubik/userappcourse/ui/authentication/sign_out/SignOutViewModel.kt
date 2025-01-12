package com.kubik.userappcourse.ui.authentication.sign_out

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kubik.userappcourse.domain.user.SignOutUserUseCase
import com.kubik.userappcourse.domain.user.UserRepository
import com.kubik.userappcourse.ui.authentication.models.UserModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SignOutViewModel : ViewModel() {

    private val _isSuccessfulSignUp = MutableStateFlow<Boolean?>(null)
    val isSuccessfulSignUp = _isSuccessfulSignUp.asStateFlow()
    private val _exitsLogin = MutableStateFlow<Int>(0)
    val exitsLogin = _exitsLogin.asStateFlow()

    fun registerUser(user: UserModel, userRepository: UserRepository) {
        viewModelScope.launch(Dispatchers.IO) {
            SignOutUserUseCase(userRepository).signUp(
                user = user.toUserDomainModel(),
                resultUserSignIn = {
                    _isSuccessfulSignUp.value = it
                },
                loginExists = {
                    Handler(Looper.getMainLooper()).post {
                        _exitsLogin.value += 1
                    }
                }
            )
        }
    }

}