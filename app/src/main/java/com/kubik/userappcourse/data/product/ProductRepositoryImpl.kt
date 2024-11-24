package com.kubik.userappcourse.data.product

import android.net.Uri
import android.util.Log
import com.kubik.userappcourse.data.db.dao.DaoProduct
import com.kubik.userappcourse.data.db.db_entity.BuyProductEntity
import com.kubik.userappcourse.data.db.repository.RepositoryDatabaseProduct
import com.kubik.userappcourse.data.models.BuyProductDataModel
import com.kubik.userappcourse.domain.models.BuyProductDomainModel
import com.kubik.userappcourse.domain.models.GetBasketProductDomainModel
import com.kubik.userappcourse.domain.models.GetProductDomainModel
import com.kubik.userappcourse.domain.product.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ProductRepositoryImpl : ProductRepository {

    override suspend fun getCountProduct(address: String): Int {
        return suspendCoroutine { continuation ->
            runBlocking(Dispatchers.IO) {
                var isFirst = true
                CountProduct().getCountProduct(address) {
                    if (isFirst) {
                        continuation.resume(it)
                        isFirst = false
                    }
                }
            }
        }
    }

    override suspend fun setCountProduct(address: String, countProduct: Int): Boolean {
        return suspendCoroutine { continuation ->
            runBlocking(Dispatchers.IO) {
                CountProduct().setCountProduct(address, countProduct) {
                    continuation.resume(it)
                }
            }
        }
    }


    override suspend fun getListProduct(address: String): List<GetProductDomainModel> {
        return suspendCoroutine { continuation ->
            runBlocking(Dispatchers.IO) {
                var isFirst = true
                GetProduct().getListProduct(address, { list ->
                    if (isFirst) {
                        continuation.resume(list.map { it.toDomain() })
                        isFirst = false
                    }
                }) {
                    continuation.resume(listOf())
                    Log.e(
                        "MyLog",
                        "Data/ProductRepositoryImpl/getListProductInSubcategory: result empty"
                    )
                    isFirst = false
                }
            }
        }
    }

    override suspend fun getProduct(address: String): GetProductDomainModel {
        return suspendCoroutine { continuation ->
            runBlocking(Dispatchers.IO) {
                var isFirst = true
                GetProduct().getProduct(address, {
                    if (isFirst) {
                        continuation.resume(it.toDomain())
                        isFirst = false
                    }
                }) {
                    Log.e(
                        "MyLog", "Data/ProductRepositoryImpl/getProduct: result empty"
                    )
                    if (isFirst) {
                        continuation.resume(GetProductDomainModel())
                        isFirst = false
                    }
                }
            }
        }
    }

    override suspend fun getUriImage(address: String): Uri {
        return suspendCoroutine { continuation ->
            runBlocking(Dispatchers.IO) {
                ImageProduct().getUriImage(address) {
                    continuation.resume(it)
                }
            }
        }
    }

    override suspend fun sendBuyProduct(product: BuyProductDataModel): Boolean {
        return suspendCoroutine { continuation ->
            runBlocking(Dispatchers.IO) {
                BuyProduct().sendProduct(product) {
                    continuation.resume(it)
                }
            }
        }
    }

    override suspend fun getCountBuyProduct(addressCount: String): Int {
        return suspendCoroutine { continuation ->
            runBlocking(Dispatchers.IO) {
                BuyProduct().getCount(addressCount) {
                    continuation.resume(it)
                }
            }
        }
    }

    override suspend fun getListBuyProduct(address: String): List<BuyProductDomainModel> {
        return suspendCoroutine { continuation ->
            runBlocking(Dispatchers.IO) {
                BuyProduct().getListProduct(address) { list ->
                    continuation.resume(list.map { it.toDomain() })
                }
            }
        }
    }

    override suspend fun setLocalBuyProduct(daoProduct: DaoProduct, product: BuyProductEntity) {
        withContext(Dispatchers.IO) {
            RepositoryDatabaseProduct(daoProduct).setProduct(product)
        }
    }

    override suspend fun getListLocalBuyProduct(daoProduct: DaoProduct): List<GetBasketProductDomainModel> {
        return suspendCoroutine { continuation ->
            runBlocking(Dispatchers.IO) {
                RepositoryDatabaseProduct(daoProduct).getListBuyProduct { list ->
                    continuation.resume(list.map { it.toGetBasketDomainModel() })
                }
            }
        }
    }

    override suspend fun clearLocalBuyProduct(daoProduct: DaoProduct) {
        runBlocking(Dispatchers.IO) {
            RepositoryDatabaseProduct(daoProduct).clearDataBuyProduct()
        }
    }

    override suspend fun clearProductByAddress(address: String): Boolean {
        return suspendCoroutine { continuation ->
            runBlocking(Dispatchers.IO) {
                ClearProductByAddress().clearProduct(address) {
                    continuation.resume(it)
                }
            }
        }
    }

}