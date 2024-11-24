package com.kubik.userappcourse.domain.category

import android.content.Context
import com.kubik.userappcourse.domain.models.HomeDomainModel
import com.kubik.userappcourse.domain.models.SubcategoryDomainModel

interface CategoryRepository {

    suspend fun getListsCategory(successful: (List<HomeDomainModel>) -> Unit)

    suspend fun saveNumCategory(context: Context, numCategory: Int, successful: () -> Unit)

    suspend fun getNumCategory(context: Context): Int

    suspend fun getNameCategory(numCategory: Int): String
    suspend fun getNameDbCategory(numCategory: Int): String

    suspend fun getListSubcategory(
        numCategory: Int,
        successful: (List<SubcategoryDomainModel>) -> Unit,
    )

    suspend fun saveNumSubcategory(context: Context, numSubcategory: Int, successful: () -> Unit)

    suspend fun getNumSubcategory(context: Context): Int

    suspend fun getNameSubcategory(numCategory: Int, numSubcategory: Int): String
    suspend fun getNameDbSubcategory(numCategory: Int, numSubcategory: Int): String
    suspend fun getNameCountDbSubcategory(numCategory: Int, numSubcategory: Int): String


}