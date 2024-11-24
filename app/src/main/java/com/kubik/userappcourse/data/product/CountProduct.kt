package com.kubik.userappcourse.data.product

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

internal class CountProduct {

    suspend fun getCountProduct(address: String, successful: (Int) -> Unit) {
        val ref = Firebase.database.getReference(address)
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val number = snapshot.getValue(Int::class.java)
                if (number != null) {
                    successful(number)
                } else {
                    Log.d("MyLog", "CountProduct/getCountProduct: number = null")
                    successful(-1)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("MyLog", "CountProduct/getCountProduct: ${error.message}")
                successful(-1)
            }
        })
    }

    suspend fun setCountProduct(address: String, countProduct: Int, successful: (Boolean) -> Unit) {
        val ref = Firebase.database.getReference(address)
        ref.setValue(countProduct).addOnSuccessListener {
            successful(true)
        }.addOnFailureListener {
            Log.e("MyLog", "Data/CountProduct error: ${it.message}")
            successful(false)
        }
    }

}