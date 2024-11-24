package com.kubik.userappcourse.ui.base.base_home.home

import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kubik.userappcourse.domain.category.CategoryRepository
import com.kubik.userappcourse.domain.category.GetCategoryUseCase
import com.kubik.userappcourse.domain.category.NumCategoryUseCase
import com.kubik.userappcourse.ui.base.models.HomeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    val listAdapter = MutableLiveData<List<HomeModel>>()
    val isSaveNumCategory = MutableLiveData<Int>(0)
    private lateinit var categoryRepository: CategoryRepository

    fun initViewModel(newCategoryRepository: CategoryRepository) {
        categoryRepository = newCategoryRepository
    }

    fun loadListAdapter() {
        viewModelScope.launch(Dispatchers.Default) {
            GetCategoryUseCase(categoryRepository).getListCategory { newList: List<HomeModel> ->
                Handler(Looper.getMainLooper()).post {
                    listAdapter.value = newList
                }
            }
        }
    }

    fun saveNumCategory(
        context: Context,
        numCategory: Int,
    ) {
        viewModelScope.launch(Dispatchers.Default) {
            NumCategoryUseCase(categoryRepository).saveNumCategory(context, numCategory) {
                Handler(Looper.getMainLooper()).post {
                    isSaveNumCategory.value = isSaveNumCategory.value!! + 1
                }
            }
        }
    }

}