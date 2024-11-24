package com.kubik.userappcourse.data.user_data

import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.kubik.userappcourse.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

internal class CheckExistsLoginUser {

    suspend fun checkLogin(login: String, successful: (isExists: Boolean) -> Unit) {
        withContext(Dispatchers.IO) {
            val docRef = FirebaseFirestore.getInstance().collection(Constants.USER_COLLECTION_NAME)
                .document(login)
            docRef.get().addOnSuccessListener { document: DocumentSnapshot ->
                successful(document.exists())
            }.addOnFailureListener {
                Log.e("MyLog", "CheckExistsLoginUser: ${it.message}")
                runBlocking(Dispatchers.IO) {
                    checkLogin(login, successful)
                    //если захочу переписать чтобы возвращал ошибку в часть с бизнес логикой
                }
            }
        }
    }

}


