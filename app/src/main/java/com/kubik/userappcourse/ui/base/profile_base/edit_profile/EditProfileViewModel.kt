package com.kubik.userappcourse.ui.base.profile_base.edit_profile

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kubik.userappcourse.data.db.dao.DaoUser
import com.kubik.userappcourse.data.user_data.UserRepositoryImpl
import com.kubik.userappcourse.domain.user.EditDataUserUseCase
import com.kubik.userappcourse.domain.user.UserRepository
import com.kubik.userappcourse.ui.authentication.models.UserModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditProfileViewModel : ViewModel() {

    private val userRepository: UserRepository = UserRepositoryImpl()
    val resultEditDataUser = MutableLiveData<Boolean>()

    fun editDataUser(user: UserModel, daoUser: DaoUser) {
        viewModelScope.launch(Dispatchers.Default) {
            EditDataUserUseCase(userRepository).edit(user.toUserDomainModel(), daoUser) {
                Handler(Looper.getMainLooper()).post {
                    resultEditDataUser.value = it
                }
            }
        }
    }

}