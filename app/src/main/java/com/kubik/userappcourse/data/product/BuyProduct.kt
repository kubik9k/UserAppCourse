package com.kubik.userappcourse.data.product

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.kubik.userappcourse.Constants
import com.kubik.userappcourse.data.getBuyProductMap
import com.kubik.userappcourse.data.models.BuyProductDataModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class BuyProduct {

    suspend fun sendProduct(
        item: BuyProductDataModel,
        successful: (Boolean) -> Unit,
    ) {
        withContext(Dispatchers.IO) {
            val ref = Firebase.database.getReference(item.addressBuyProduct)
            val map = getBuyProductMap(item)
            ref.setValue(map).addOnSuccessListener {
                successful(true)
            }.addOnFailureListener {
                Log.e("MyLog", "data/BuyProduct error: ${it.message}")
                successful(false)
            }
        }
    }

    //-----------------------------

    suspend fun getCount(address: String, successful: (Int) -> Unit) {
        withContext(Dispatchers.IO) {
            val ref = Firebase.database.getReference(address)
            ref.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.value != null) {
                        try {
                            val count: Int = snapshot.value.toString().toInt()
                            successful(count)
                        } catch (e: Exception) {
                            Log.e("MyLog", "data/BuyProduct error: ${e.message}")
                            successful(0)
                        }
                    } else successful(0)
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("MyLog", "data/BuyProduct error: ${error.message}")
                    successful(0)
                }
            })
        }
    }

    //--------------------------------------------------------------------

    suspend fun getListProduct(address: String, successful: (List<BuyProductDataModel>) -> Unit) {
        withContext(Dispatchers.IO) {
            val ref = Firebase.database.getReference(address)
            ref.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val listProduct = mutableListOf<BuyProductDataModel>()
                    for (productSnapshot in snapshot.children) {
                        val product = getProductFromSnapshot(address, productSnapshot)
                        listProduct.add(product)
                    }
                    successful(listProduct)
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("MyLog", "data/BuyProduct error: ${error.message}")
                    successful(listOf())
                }
            })

        }
    }

    private fun getProductFromSnapshot(
        baseAddress: String,
        productSnapshot: DataSnapshot,
    ): BuyProductDataModel {
        val product = BuyProductDataModel()
        try {
            product.addressBuyProduct = (baseAddress + "/" + productSnapshot.key) ?: ""
            val productData = productSnapshot.value as? Map<*, *>
            if (productData != null) {
                product.addressProduct = productData[Constants.PRODUCT_ADDRESS] as String ?: ""
                product.status = (productData[Constants.PRODUCT_STATUS] as Long ?: 0).toInt()
                product.count = (productData[Constants.PRODUCT_COUNT] as Long ?: 0).toInt()
            }
        } catch (e: Exception) {
            Log.e("MyLog", "Data/Product error: ${e.message}")
        }
        return product
    }

}