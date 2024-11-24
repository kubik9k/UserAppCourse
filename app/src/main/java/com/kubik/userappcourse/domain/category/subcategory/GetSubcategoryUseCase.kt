package com.kubik.userappcourse.domain.category.subcategory

import android.content.Context
import com.kubik.userappcourse.domain.category.CategoryRepository
import com.kubik.userappcourse.ui.base.models.SubcategoryModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetSubcategoryUseCase(private val categoryRepository: CategoryRepository) {

    suspend fun getSubcategory(context: Context, successful: (List<SubcategoryModel>) -> Unit) {
        withContext(Dispatchers.Default) {
            val numCategory = categoryRepository.getNumCategory(context)
            categoryRepository.getListSubcategory(numCategory) {
                successful(it.map { it.toSubcategory() })
            }
        }
    }

    suspend fun getNameSubcategory(context: Context, successful: (String) -> Unit) {
        withContext(Dispatchers.Default) {
            val numCategory = categoryRepository.getNumCategory(context)
            val numSubcategory = categoryRepository.getNumSubcategory(context)
            successful(categoryRepository.getNameSubcategory(numCategory, numSubcategory))
        }
    }

}