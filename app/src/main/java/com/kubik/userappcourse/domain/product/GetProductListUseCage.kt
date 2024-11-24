package com.kubik.userappcourse.domain.product

import android.content.Context
import android.util.Log
import com.kubik.userappcourse.Constants
import com.kubik.userappcourse.domain.category.CategoryRepository
import com.kubik.userappcourse.domain.models.SubcategoryDomainModel
import com.kubik.userappcourse.ui.base.models.GetProductModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class GetProductListUseCage(
    private val productRepository: ProductRepository,
    private val categoryRepository: CategoryRepository,
) {
    private val listAddress = mutableListOf<String>()
    private val resultList = mutableListOf<List<GetProductModel>>()

    suspend fun getListsCategory(
        context: Context,
        successful: (List<List<GetProductModel>>) -> Unit,
    ) {
        resultList.clear()
        withContext(Dispatchers.Default) {
            initAddress(context)
            listAddress.forEach { address ->
                resultList.add(productRepository.getListProduct(address)
                    .map { it.toUi() })
            }
            getUri()
            Log.d("MyLog", "GetProductListUseCage good: ${resultList}")
            successful(resultList)
        }
    }

    private suspend fun getUri() {
        for (numList in resultList.indices) {
            for (i in resultList[numList].indices) {
                resultList[numList][i].imageUri =
                    productRepository.getUriImage(resultList[numList][i].imageUrl)
            }
        }
    }


    private suspend fun initAddress(context: Context) {
        val numCategory: Int = categoryRepository.getNumCategory(context)
        val nameDbCategory: String = categoryRepository.getNameDbCategory(numCategory)
        val subcategoryList = getListSubcategory(numCategory)
        subcategoryList.forEach {
            listAddress.add("${Constants.BASE_PRODUCT_ADDRESS}$nameDbCategory/${it.nameDbItem}")
        }
    }

    private suspend fun getListSubcategory(numCategory: Int): List<SubcategoryDomainModel> {
        return suspendCoroutine { continuation ->
            runBlocking(Dispatchers.Default) {
                var isFirst = true
                categoryRepository.getListSubcategory(numCategory) {
                    if (isFirst) {
                        continuation.resume(it)
                        isFirst = false
                    }
                }
            }
        }
    }
}