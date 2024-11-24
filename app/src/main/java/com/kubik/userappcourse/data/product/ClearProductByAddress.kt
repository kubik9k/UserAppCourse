package com.kubik.userappcourse.data.product

import android.util.Log
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class ClearProductByAddress {

    suspend fun clearProduct(
        address: String,
        successful: (Boolean) -> Unit,
    ) {
        withContext(Dispatchers.IO) {
            val ref = Firebase.database.getReference(address)
            ref.removeValue().addOnSuccessListener {
                successful(true)
            }.addOnFailureListener {
                Log.e("MyLog", "data/ClearProductByAddress error: ${it.message}")
                successful(false)
            }
        }
    }

}