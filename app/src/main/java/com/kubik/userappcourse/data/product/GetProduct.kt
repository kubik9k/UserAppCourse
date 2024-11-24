package com.kubik.userappcourse.data.product

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.kubik.userappcourse.Constants
import com.kubik.userappcourse.data.models.GetProductDataModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class GetProduct {

    suspend fun getListProduct(
        address: String,
        successful: (List<GetProductDataModel>) -> Unit,
        error: () -> Unit,
    ) {
        withContext(Dispatchers.IO) {
            val ref = Firebase.database.getReference(address)
            ref.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val listProduct = mutableListOf<GetProductDataModel>()
                    for (productSnapshot in snapshot.children) {
                        val product = getProductFromSnapshot(productSnapshot)
                        listProduct.add(product)
                    }
                    Log.d("MyLog", "ServerGetProduct good: ${listProduct}")
                    successful(listProduct)
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("MyLog", "Data/Product getProduct error: ${error.message}")
                    error()
                }
            })
        }
    }

    suspend fun getProduct(
        address: String,
        successful: (GetProductDataModel) -> Unit,
        error: () -> Unit,
    ) {
        withContext(Dispatchers.IO) {
            val ref = Firebase.database.getReference(address)
            ref.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val product = getProductFromSnapshot(snapshot)
                    Log.d("MyLog", "ServerGetProduct good: ${product}")
                    successful(product)
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("MyLog", "Data/Product getProduct error: ${error.message}")
                    error()
                }
            })
        }
    }

    private fun getProductFromSnapshot(productSnapshot: DataSnapshot): GetProductDataModel {
        val product = GetProductDataModel()
        try {
            product.id = productSnapshot.key ?: ""
            val productData = productSnapshot.value as? Map<*, *>
            if (productData != null) {
                product.title = productData[Constants.PRODUCT_TITLE] as String ?: ""
                product.description = productData[Constants.PRODUCT_DESCRIPTION] as String ?: ""
                product.imageUrl =
                    productData[Constants.PRODUCT_IMAGE_URL_STORAGE] as String ?: ""
                product.price = (productData[Constants.PRODUCT_PRICE] as Long ?: 0).toInt()
                product.count = (productData[Constants.PRODUCT_COUNT] as Long ?: 0).toInt()
            }
        } catch (e: Exception) {
            Log.e("MyLog", "Data/Product error: ${e.message}")
        }
        return product
    }

}