package com.kubik.userappcourse.ui.authentication.sign_in

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kubik.userappcourse.data.db.dao.DaoUser
import com.kubik.userappcourse.domain.user.CheckSignInUserUseCase
import com.kubik.userappcourse.domain.user.SignInUserUseCase
import com.kubik.userappcourse.domain.user.UserRepository
import com.kubik.userappcourse.ui.authentication.models.SignInUserModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class SignInViewModel : ViewModel() {

    private val _isSuccessfulSignIn =
        MutableSharedFlow<Boolean>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val isSuccessfulSignIn = _isSuccessfulSignIn.asSharedFlow()
    private val _isSignIn =
        MutableSharedFlow<Boolean>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val isSignIn = _isSignIn.asSharedFlow()

    fun signIn(data: SignInUserModel, daoUser: DaoUser, userRepository: UserRepository) {
        viewModelScope.launch(Dispatchers.Default) {
            _isSuccessfulSignIn.emit(
                SignInUserUseCase(userRepository).signIn(data.toDomainModel(), daoUser)
            )
        }
    }

    fun checkSignInUser(daoUser: DaoUser, userRepository: UserRepository) {
        viewModelScope.launch(Dispatchers.Default) {
            _isSignIn.emit(CheckSignInUserUseCase(userRepository).checkSignIn(daoUser))
        }
    }

}