package com.kubik.userappcourse.data.user_data

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.kubik.userappcourse.Constants
import com.kubik.userappcourse.data.models.SignInUserDataModel
import com.kubik.userappcourse.data.models.UserDataModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

internal class SignInUser {

    suspend fun signIn(data: SignInUserDataModel, successful: (UserDataModel) -> Unit) {
        withContext(Dispatchers.IO) {
            val docRef = FirebaseFirestore.getInstance().collection(Constants.USER_COLLECTION_NAME)
                .document(data.login)
            docRef.get().addOnSuccessListener { documentSnapshot ->
                val user = UserDataModel()
                user.password =
                    documentSnapshot.getString(Constants.USER_COLLECTION_DOCUMENT_PASSWORD) ?: ""
                if (data.password == user.password) {
                    user.login = data.login
                    user.first_name =
                        documentSnapshot.getString(Constants.USER_COLLECTION_DOCUMENT_FIRST_NAME)
                            ?: ""
                    user.last_name =
                        documentSnapshot.getString(Constants.USER_COLLECTION_DOCUMENT_LAST_NAME)
                            ?: ""
                    try {
                        user.type_user =
                            (documentSnapshot.get(Constants.USER_COLLECTION_DOCUMENT_TYPE) ?: 2)
                                .toString().toInt()
                    } catch (e: Exception) {
                        user.type_user = 2
                    }
                    Log.d("MyLog", "data: signInUser: result: ${user}")
                    successful(user)
                } else {
                    Log.d("MyLog", "data: signInUser: result: empty user")
                    successful(UserDataModel())
                }
            }.addOnFailureListener {
                Log.d("MyLog", "SignInUser 'data' : ${it.message}")
                runBlocking {
                    signIn(data, successful)
                }
            }
        }
    }

}