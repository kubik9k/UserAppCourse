package com.kubik.userappcourse.domain.product

import android.content.Context
import com.kubik.userappcourse.Constants
import com.kubik.userappcourse.data.db.dao.DaoUser
import com.kubik.userappcourse.domain.category.CategoryRepository
import com.kubik.userappcourse.domain.models.BuyProductDomainModel
import com.kubik.userappcourse.domain.user.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BuyProductUseCase(
    private val productRepository: ProductRepository,
    private val categoryRepository: CategoryRepository,
    private val repositoryUser: UserRepository,
) {
    private var baseUrlProduct = ""
    private var baseUrlProductBuy = ""

    suspend fun buyProduct(context: Context, id: String, daoUser: DaoUser, successful: () -> Unit) {
        withContext(Dispatchers.Default) {
            initAddress(context, daoUser)
            baseUrlProduct += id
            baseUrlProductBuy += id
            val countBuyProduct =
                productRepository.getCountBuyProduct(baseUrlProductBuy + "/" + Constants.PRODUCT_COUNT)
            val countProductStore =
                productRepository.getCountProduct(baseUrlProduct + "/" + Constants.PRODUCT_COUNT)
            productRepository.setCountProduct(
                baseUrlProduct + "/" + Constants.PRODUCT_COUNT,
                countProductStore - 1
            )
            val buyProduct =
                BuyProductDomainModel(baseUrlProductBuy, baseUrlProduct, countBuyProduct + 1, 0)
            if (countBuyProduct > 0) buyProduct.status = 3
            sendProduct(buyProduct, successful)
        }
    }

    private suspend fun sendProduct(product: BuyProductDomainModel, successful: () -> Unit) {
        if (productRepository.sendBuyProduct(product.toData())) successful()
        else sendProduct(product, successful)
    }

    private suspend fun initAddress(context: Context, daoUser: DaoUser) {
        val numCategory: Int = categoryRepository.getNumCategory(context)
        val nameDbCategory: String = categoryRepository.getNameDbCategory(numCategory)
        val numSubcategory = categoryRepository.getNumSubcategory(context)
        val nameDbSubcategory = categoryRepository.getNameDbSubcategory(numCategory, numSubcategory)
        baseUrlProduct = "${Constants.BASE_PRODUCT_ADDRESS}$nameDbCategory/$nameDbSubcategory/"

        val userLogin = repositoryUser.getLoginUser(daoUser)
        baseUrlProductBuy =
            "${Constants.BASE_PRODUCT_BUY_ADDRESS}$userLogin/${nameDbCategory}_${nameDbSubcategory}_"
    }

}