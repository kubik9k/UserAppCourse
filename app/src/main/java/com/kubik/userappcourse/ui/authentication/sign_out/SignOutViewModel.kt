package com.kubik.userappcourse.ui.authentication.sign_out

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kubik.userappcourse.domain.user.SignOutUserUseCase
import com.kubik.userappcourse.domain.user.UserRepository
import com.kubik.userappcourse.ui.authentication.models.UserModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignOutViewModel : ViewModel() {

    val isSuccessfulSignUp = MutableLiveData<Boolean>()
    val exitsLogin = MutableLiveData<Int>(0)

    fun registerUser(user: UserModel, userRepository: UserRepository) {
        viewModelScope.launch(Dispatchers.IO) {
            SignOutUserUseCase(userRepository).signUp(
                user = user.toUserDomainModel(),
                resultUserSignIn = {
                    Handler(Looper.getMainLooper()).post{
                        isSuccessfulSignUp.value = it
                    }
                },
                loginExists = {
                    Handler(Looper.getMainLooper()).post{
                        try {
                            exitsLogin.value = exitsLogin.value!! + 1
                        } catch (e: ExceptionInInitializerError) {
                            Log.e("MyLog", e.message.toString())
                        }
                    }
                }
            )
        }
    }

}