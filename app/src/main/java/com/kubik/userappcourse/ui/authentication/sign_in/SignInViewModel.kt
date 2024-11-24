package com.kubik.userappcourse.ui.authentication.sign_in

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kubik.userappcourse.data.db.dao.DaoUser
import com.kubik.userappcourse.domain.user.CheckSignInUserUseCase
import com.kubik.userappcourse.domain.user.SignInUserUseCase
import com.kubik.userappcourse.domain.user.UserRepository
import com.kubik.userappcourse.ui.auntification.models.SignInUserModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignInViewModel : ViewModel() {

    val isSuccessfulSignIn = MutableLiveData<Boolean>()
    val isSignIn = MutableLiveData<Boolean>()

    fun signIn(data: SignInUserModel, daoUser: DaoUser, userRepository: UserRepository) {
        viewModelScope.launch {
            SignInUserUseCase(userRepository).signIn(data.toDomainModel(), daoUser) {
                Handler(Looper.getMainLooper()).post {
                    Log.d("MyLog", "SignInViewModel: $it")
                    isSuccessfulSignIn.value = it
                }
            }
        }
    }

    fun checkSignInUser(daoUser: DaoUser, userRepository: UserRepository) {
        viewModelScope.launch(Dispatchers.Default) {
            CheckSignInUserUseCase(userRepository).checkSignIn(daoUser) {
                Handler(Looper.getMainLooper()).post {
                    isSignIn.value = it
                }
            }
        }
    }

}