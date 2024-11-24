package com.kubik.userappcourse.ui.base.base_home.home_lists

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kubik.userappcourse.data.category.CategoryRepositoryImpl
import com.kubik.userappcourse.data.product.ProductRepositoryImpl
import com.kubik.userappcourse.domain.category.CategoryRepository
import com.kubik.userappcourse.domain.category.subcategory.GetSubcategoryUseCase
import com.kubik.userappcourse.domain.category.subcategory.SetNumSubcategoryUseCase
import com.kubik.userappcourse.domain.product.GetProductListUseCage
import com.kubik.userappcourse.domain.product.ProductRepository
import com.kubik.userappcourse.ui.base.models.GetProductModel
import com.kubik.userappcourse.ui.base.models.SubcategoryModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeListViewModel : ViewModel() {

    private val categoryRepository: CategoryRepository = CategoryRepositoryImpl()
    private val productRepository: ProductRepository = ProductRepositoryImpl()
    val listSubcategory = MutableLiveData<List<SubcategoryModel>>(listOf())
    val listProduct = MutableLiveData<List<GetProductModel>>()
    private var baseListProduct = listOf<GetProductModel>()
    private var baseListSubcategory = listOf<SubcategoryModel>()
    private var allListsProducts = listOf<List<GetProductModel>>()

    fun initViewModel(context: Context) {
        viewModelScope.launch(Dispatchers.Default) {
            GetSubcategoryUseCase(categoryRepository).getSubcategory(context) {
                Handler(Looper.getMainLooper()).post {
                    baseListSubcategory = it
                    listSubcategory.value = it
                }
            }
            GetProductListUseCage(productRepository, categoryRepository).getListsCategory(context) {
                allListsProducts = it
                Handler(Looper.getMainLooper()).post {
                    baseListProduct = allListsProducts[0]
                    listProduct.value = baseListProduct
                    Log.d("MyLog", "HomeListViewModel list: ${allListsProducts[0]}")
                }
            }
        }
    }

    private fun saveNumSubcategory(
        context: Context,
        successful: () -> Unit,
    ) {
        for (i in baseListSubcategory.indices) {
            if (baseListSubcategory[i].nameDbItem == listSubcategory.value!!.first().nameDbItem) {
                viewModelScope.launch(Dispatchers.Default) {
                    Log.d(
                        "MyLog",
                        "HomeListViewModel: saveNumSubcategory: $i - ${baseListSubcategory[i].name}"
                    )
                    SetNumSubcategoryUseCase(categoryRepository).setNumSubcategory(context, i) {
                        Handler(Looper.getMainLooper()).post {
                            successful()
                        }
                    }
                }
                break;
            }
        }
    }

    fun clickItemSubcategory(positionClick: Int) {
        if (positionClick < listSubcategory.value!!.size) {
            updateListSubcategory(positionClick)
            loadListProductBySubcategory()
        } else Log.d(
            "MyLog",
            "HomeListViewModel |clickItemSubcategory| is not $positionClick position in list"
        )
    }

    fun clickProduct(context: Context, successful: () -> Unit) {
        saveNumSubcategory(context) {
            successful()
        }
    }

    fun searchText(textSearch: String) {
        viewModelScope.launch(Dispatchers.Default) {
            val updateListSearch = mutableListOf<GetProductModel>()
            baseListProduct.forEach {
                if (it.title.lowercase().contains(textSearch.lowercase())) {
                    updateListSearch.add(it)
                }
            }
            Handler(Looper.getMainLooper()).post {
                listProduct.value = updateListSearch
            }
        }
    }

    private fun loadListProductBySubcategory() {
        for (i in baseListSubcategory.indices) {
            if (baseListSubcategory[i].nameDbItem == listSubcategory.value!!.first().nameDbItem
                && i < allListsProducts.size
            ) {
                baseListProduct = allListsProducts[i]
                if (baseListProduct.size == 1) {
                    if (baseListProduct[0].isEmpty()) listProduct.value = listOf()
                    else listProduct.value = baseListProduct
                } else listProduct.value = baseListProduct
            }
        }
    }

    private fun updateListSubcategory(positionClick: Int) {
        val list = listSubcategory.value!!.toMutableList()
        val item = list[positionClick]
        list[positionClick] = list.first()
        list[0] = item
        listSubcategory.value = list
    }
}