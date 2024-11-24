package com.kubik.userappcourse.domain.product

import com.kubik.userappcourse.data.db.dao.DaoProduct
import com.kubik.userappcourse.ui.base.models.GetBasketProductModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductHistoryUseCase(private val productRepository: ProductRepository) {

    suspend fun getListProduct(
        productDao: DaoProduct,
        successful: (List<GetBasketProductModel>) -> Unit,
    ) {
        withContext(Dispatchers.Default) {
            successful(
                productRepository.getListLocalBuyProduct(daoProduct = productDao).map { it.toUi() })
        }
    }

    suspend fun clearData(productDao: DaoProduct) {
        withContext(Dispatchers.Default) {
            productRepository.clearLocalBuyProduct(daoProduct = productDao)
        }
    }

}