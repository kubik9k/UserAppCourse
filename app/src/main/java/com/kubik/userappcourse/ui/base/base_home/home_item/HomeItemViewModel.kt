package com.kubik.userappcourse.ui.base.base_home.home_item

import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kubik.userappcourse.data.category.CategoryRepositoryImpl
import com.kubik.userappcourse.data.db.dao.DaoUser
import com.kubik.userappcourse.data.product.ProductRepositoryImpl
import com.kubik.userappcourse.data.user_data.UserRepositoryImpl
import com.kubik.userappcourse.domain.category.CategoryRepository
import com.kubik.userappcourse.domain.product.BuyProductUseCase
import com.kubik.userappcourse.domain.product.ProductRepository
import com.kubik.userappcourse.domain.user.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeItemViewModel : ViewModel() {

    //по нормальному нужно прокидывать из фрагмента но на данный момент не вижу в этом смысла
    private val categoryRepository: CategoryRepository = CategoryRepositoryImpl()
    private val productRepository: ProductRepository = ProductRepositoryImpl()
    private val userRepository: UserRepository = UserRepositoryImpl()
    val newResultSuccessful = MutableLiveData<Int>(0)

    fun buyProduct(context: Context, id: String, userDao: DaoUser) {
        viewModelScope.launch(Dispatchers.Default) {
            BuyProductUseCase(productRepository, categoryRepository, userRepository).buyProduct(
                context = context, id = id, daoUser = userDao
            ) {
                Handler(Looper.getMainLooper()).post {
                    newResultSuccessful.value = newResultSuccessful.value!! + 1
                }
            }
        }
    }

}