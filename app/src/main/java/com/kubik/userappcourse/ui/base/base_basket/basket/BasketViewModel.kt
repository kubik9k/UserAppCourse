package com.kubik.userappcourse.ui.base.base_basket.basket

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kubik.userappcourse.data.db.dao.DaoProduct
import com.kubik.userappcourse.data.db.dao.DaoUser
import com.kubik.userappcourse.data.product.ProductRepositoryImpl
import com.kubik.userappcourse.data.user_data.UserRepositoryImpl
import com.kubik.userappcourse.domain.product.GetBuyProductUseCase
import com.kubik.userappcourse.ui.base.models.GetBasketProductModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BasketViewModel : ViewModel() {

    private val productRepository = ProductRepositoryImpl()
    private val userRepository = UserRepositoryImpl()

    val listBasket = MutableLiveData<List<GetBasketProductModel>>()

    fun getList(daoUser: DaoUser, daoProduct: DaoProduct) {
        viewModelScope.launch(Dispatchers.Default) {
            GetBuyProductUseCase(productRepository, userRepository).get(daoUser, daoProduct) {
                Handler(Looper.getMainLooper()).post {
                    listBasket.value = it
                }
            }
        }
    }

}