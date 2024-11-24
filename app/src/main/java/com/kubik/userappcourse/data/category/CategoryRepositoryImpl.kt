package com.kubik.userappcourse.data.category

import android.content.Context
import com.kubik.userappcourse.domain.category.CategoryRepository
import com.kubik.userappcourse.domain.models.HomeDomainModel
import com.kubik.userappcourse.domain.models.SubcategoryDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class CategoryRepositoryImpl : CategoryRepository {

    override suspend fun getListsCategory(successful: (List<HomeDomainModel>) -> Unit) {
        withContext(Dispatchers.Default) {
            successful(DataCategoryPreview().getData().map { it.toDomain() })
        }
    }

    override suspend fun saveNumCategory(
        context: Context,
        numCategory: Int,
        successful: () -> Unit,
    ) {
        withContext(Dispatchers.IO) {
            SaveSpNumCategory(context).saveNumCategory(numCategory)
            successful()
        }
    }

    override suspend fun getNumCategory(context: Context): Int {
        return suspendCoroutine { corutinuation ->
            runBlocking(Dispatchers.IO) {
                corutinuation.resume(SaveSpNumCategory(context).getNumCategory())
            }
        }
    }

    override suspend fun getNameCategory(numCategory: Int): String {
        return suspendCoroutine { corutinuation ->
            runBlocking(Dispatchers.IO) {
                corutinuation.resume(DataCategoryPreview().getNameCategory(numCategory))
            }
        }
    }

    override suspend fun getNameDbCategory(numCategory: Int): String {
        return suspendCoroutine { corutinuation ->
            runBlocking(Dispatchers.IO) {
                corutinuation.resume(DataCategoryPreview().getNameCategoryDb(numCategory))
            }
        }
    }

    override suspend fun getListSubcategory(
        numCategory: Int,
        successful: (List<SubcategoryDomainModel>) -> Unit,
    ) {
        withContext(Dispatchers.IO) {
            successful(
                DataCategoryPreview().getSubcategoryData(numCategory).map { it.toDomain() }
            )
        }
    }

    override suspend fun saveNumSubcategory(
        context: Context,
        numSubcategory: Int,
        successful: () -> Unit,
    ) {
        withContext(Dispatchers.IO) {
            SaveSpNumSubcategory(context).saveNumSubcategory(numSubcategory)
            successful()
        }
    }

    override suspend fun getNumSubcategory(context: Context): Int {
        return suspendCoroutine { corutinuation ->
            runBlocking(Dispatchers.IO) {
                corutinuation.resume(SaveSpNumSubcategory(context).getNumSubcategory())
            }
        }
    }

    override suspend fun getNameSubcategory(numCategory: Int, numSubcategory: Int): String {
        return suspendCoroutine { corutinuation ->
            runBlocking(Dispatchers.IO) {
                corutinuation.resume(
                    DataCategoryPreview().getSubcategoryName(numCategory, numSubcategory)
                )
            }
        }
    }

    override suspend fun getNameDbSubcategory(numCategory: Int, numSubcategory: Int): String {
        return suspendCoroutine { corutinuation ->
            runBlocking(Dispatchers.IO) {
                corutinuation.resume(
                    DataCategoryPreview().getSubcategoryNameDb(numCategory, numSubcategory)
                )
            }
        }
    }

    override suspend fun getNameCountDbSubcategory(numCategory: Int, numSubcategory: Int): String {
        return suspendCoroutine { corutinuation ->
            runBlocking(Dispatchers.IO) {
                corutinuation.resume(
                    DataCategoryPreview().getNameDbSubcategoryCountProduct(
                        numCategory, numSubcategory
                    )
                )
            }
        }
    }

}