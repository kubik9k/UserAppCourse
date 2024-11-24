package com.kubik.userappcourse.domain.category.subcategory

import android.content.Context
import com.kubik.userappcourse.domain.category.CategoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SetNumSubcategoryUseCase(private val categoryRepository: CategoryRepository) {

    suspend fun setNumSubcategory(context: Context, numSubcategory: Int, successful: () -> Unit) {
        withContext(Dispatchers.Default) {
            categoryRepository.saveNumSubcategory(context, numSubcategory, successful)
        }
    }

}