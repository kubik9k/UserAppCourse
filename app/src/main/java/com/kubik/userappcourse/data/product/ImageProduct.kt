package com.kubik.userappcourse.data.product

import android.net.Uri
import android.util.Log
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class ImageProduct() {


    suspend fun getUriImage(address: String, successful: (Uri) -> Unit) {
        withContext(Dispatchers.IO) {
            if(address != ""){
                val ref = Firebase.storage.getReference(address)
                ref.downloadUrl.addOnSuccessListener {
                    successful(it)
                }.addOnFailureListener {
                    Log.e("MyLog", "Data/ImageProduct error: ${it.message}")
                    successful(Uri.EMPTY)
                }
            }else successful(Uri.EMPTY)
        }
    }

}
