package com.kubik.userappcourse.domain.product

import com.kubik.userappcourse.Constants
import com.kubik.userappcourse.data.db.dao.DaoProduct
import com.kubik.userappcourse.data.db.dao.DaoUser
import com.kubik.userappcourse.domain.models.GetBasketProductDomainModel
import com.kubik.userappcourse.domain.user.UserRepository
import com.kubik.userappcourse.ui.base.models.GetBasketProductModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetBuyProductUseCase(
    private val productRepository: ProductRepository,
    private val repositoryUser: UserRepository,
) {
    private var addressBasket = ""

    suspend fun get(
        daoUser: DaoUser,
        daoProduct: DaoProduct,
        successful: (List<GetBasketProductModel>) -> Unit,
    ) {
        withContext(Dispatchers.Default) {
            initAddress(daoUser)
            val listBasket = mutableListOf<GetBasketProductDomainModel>()
            val listProductBuy = productRepository.getListBuyProduct(addressBasket)
            listProductBuy.forEach {
                val product = productRepository.getProduct(it.addressProduct)
                if (!product.isEmpty()) {
                    val uri = productRepository.getUriImage(product.imageUrl)
                    val basketProduct = GetBasketProductDomainModel(
                        addressBuyProduct = it.addressBuyProduct,
                        addressProduct = it.addressProduct, count = it.count,
                        status = it.status, id = product.id,
                        title = product.title, description = product.description,
                        imageUri = uri, price = product.price
                    )
                    if (it.status == 2) {
                        saveLocalData(daoProduct, basketProduct)
                        productRepository.clearProductByAddress(basketProduct.addressBuyProduct)
                    } else {
                        listBasket.add(basketProduct)
                    }
                }
            }
            successful(listBasket.map { it.toUi() })
        }
    }

    private suspend fun saveLocalData(
        daoProduct: DaoProduct,
        product: GetBasketProductDomainModel,
    ) {
        withContext(Dispatchers.Default) {
            productRepository.setLocalBuyProduct(daoProduct, product.toBuyProductEntity())
        }
    }

    private suspend fun initAddress(daoUser: DaoUser) {
        val login = repositoryUser.getLoginUser(daoUser)
        addressBasket = "${Constants.BASE_PRODUCT_BUY_ADDRESS}$login"
    }

}