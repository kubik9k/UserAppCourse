package com.kubik.userappcourse.ui.base.base_basket.history

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kubik.userappcourse.data.db.dao.DaoProduct
import com.kubik.userappcourse.data.product.ProductRepositoryImpl
import com.kubik.userappcourse.domain.product.ProductHistoryUseCase
import com.kubik.userappcourse.domain.product.ProductRepository
import com.kubik.userappcourse.ui.base.models.GetBasketProductModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoryViewModel : ViewModel() {

    private val productRepository: ProductRepository = ProductRepositoryImpl()
    private val productHistory = ProductHistoryUseCase(productRepository)

    val listHistory = MutableLiveData<List<GetBasketProductModel>>()

    fun getListHistory(daoProduct: DaoProduct) {
        viewModelScope.launch(Dispatchers.Default) {
            productHistory.getListProduct(daoProduct) {
                Handler(Looper.getMainLooper()).post {
                    listHistory.value = it
                }
            }
        }
    }

    fun clearData(daoProduct: DaoProduct) {
        viewModelScope.launch(Dispatchers.Default) {
            productHistory.clearData(daoProduct)
            Handler(Looper.getMainLooper()).post {
                listHistory.value = listOf()
            }
        }
    }

}