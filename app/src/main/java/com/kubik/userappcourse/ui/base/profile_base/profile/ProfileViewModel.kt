package com.kubik.userappcourse.ui.base.profile_base.profile

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kubik.userappcourse.data.db.dao.DaoUser
import com.kubik.userappcourse.data.user_data.UserRepositoryImpl
import com.kubik.userappcourse.domain.user.ClearLocalDataUserUseCase
import com.kubik.userappcourse.domain.user.GetUserDataUseCase
import com.kubik.userappcourse.domain.user.UserRepository
import com.kubik.userappcourse.ui.authentication.models.UserModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    private val userRepository: UserRepository = UserRepositoryImpl()

    val userData = MutableLiveData<UserModel>()
    val exit = MutableLiveData<Boolean>()

    fun getUserData(daoUser: DaoUser) {
        viewModelScope.launch(Dispatchers.Default) {
            GetUserDataUseCase(userRepository).getData(daoUser) {
                Handler(Looper.getMainLooper()).post {
                    userData.value = it
                }
            }
        }
    }

    fun exitUser(daoUser: DaoUser) {
        viewModelScope.launch(Dispatchers.IO) {
            ClearLocalDataUserUseCase(userRepository).clear(daoUser) {
                Handler(Looper.getMainLooper()).post {
                    exit.value = true
                }
            }
        }
    }
}