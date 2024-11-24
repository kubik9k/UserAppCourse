package com.kubik.userappcourse.data.user_data

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.kubik.userappcourse.Constants
import com.kubik.userappcourse.data.getUserMap
import com.kubik.userappcourse.data.models.UserDataModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UpdateUserData {

    suspend fun update(user: UserDataModel, successful: (Boolean) -> Unit) {
        withContext(Dispatchers.IO) {
            val docRef = FirebaseFirestore.getInstance().collection(Constants.USER_COLLECTION_NAME)
                .document(user.login)
            docRef.update(getUserMap(user = user)).addOnSuccessListener {
                successful(true)
            }.addOnFailureListener {
                Log.d("MyLog", "data / UpdateUserData: ${it.message}")
                successful(false)
            }
        }
    }

}