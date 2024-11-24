package com.kubik.userappcourse.ui.base.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetBasketProductModel(
    val addressBuyProduct: String = "",
    val addressProduct: String = "",
    val count: Int = 0,
    var status: Int = 0,
    var id: String = "",
    var title: String = "",
    var description: String = "",
    var imageUri: Uri = Uri.EMPTY,
    var price: Int = 0,
) : Parcelable