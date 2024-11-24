package com.kubik.userappcourse.domain.category

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NumCategoryUseCase(private val categoryRepository: CategoryRepository) {

    suspend fun saveNumCategory(context: Context, numCategory: Int, successful: () -> Unit) {
        withContext(Dispatchers.Default) {
            categoryRepository.saveNumCategory(context, numCategory, successful)
        }
    }

}