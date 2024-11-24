package com.kubik.userappcourse.domain.product

import android.net.Uri
import com.kubik.userappcourse.data.db.dao.DaoProduct
import com.kubik.userappcourse.data.db.db_entity.BuyProductEntity
import com.kubik.userappcourse.data.models.BuyProductDataModel
import com.kubik.userappcourse.domain.models.BuyProductDomainModel
import com.kubik.userappcourse.domain.models.GetBasketProductDomainModel
import com.kubik.userappcourse.domain.models.GetProductDomainModel

interface ProductRepository {

    suspend fun getCountProduct(address: String): Int

    suspend fun setCountProduct(address: String, countProduct: Int): Boolean

    suspend fun getListProduct(address: String): List<GetProductDomainModel>

    suspend fun getProduct(address: String): GetProductDomainModel

    suspend fun getUriImage(address: String): Uri

    suspend fun sendBuyProduct(product: BuyProductDataModel): Boolean

    suspend fun getCountBuyProduct(addressCount: String): Int

    suspend fun getListBuyProduct(address: String): List<BuyProductDomainModel>

    suspend fun setLocalBuyProduct(daoProduct: DaoProduct, product: BuyProductEntity)

    suspend fun getListLocalBuyProduct(daoProduct: DaoProduct): List<GetBasketProductDomainModel>

    suspend fun clearLocalBuyProduct(daoProduct: DaoProduct)

    suspend fun clearProductByAddress(address: String): Boolean
}