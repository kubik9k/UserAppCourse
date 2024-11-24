package com.kubik.userappcourse.data.db.repository

import com.kubik.userappcourse.data.db.dao.DaoProduct
import com.kubik.userappcourse.data.db.db_entity.BuyProductEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RepositoryDatabaseProduct(private val daoProduct: DaoProduct) {

    suspend fun setProduct(product: BuyProductEntity) {
        withContext(Dispatchers.IO) {
            daoProduct.setProduct(product)
        }
    }

    suspend fun getListBuyProduct(successful: (List<BuyProductEntity>) -> Unit) {
        withContext(Dispatchers.IO) {
            successful(daoProduct.getListProduct())
        }
    }

    suspend fun clearDataBuyProduct() {
        withContext(Dispatchers.IO) {
            daoProduct.clearTableBuyProduct()
        }
    }

}