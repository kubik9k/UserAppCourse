package com.kubik.userappcourse.domain.category

import android.content.Context
import com.kubik.userappcourse.ui.base.models.HomeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetCategoryUseCase(private val categoryRepository: CategoryRepository) {

    suspend fun getListCategory(successful: (List<HomeModel>) -> Unit) {
        withContext(Dispatchers.Default) {
            categoryRepository.getListsCategory { list ->
                successful(list.map { it.toUiModels() })
            }
        }
    }

    suspend fun getNameCategory(context: Context, successful: (String) -> Unit) {
        withContext(Dispatchers.Default) {
            val numCategory = categoryRepository.getNumCategory(context)
            successful(categoryRepository.getNameCategory(numCategory))
        }
    }

}