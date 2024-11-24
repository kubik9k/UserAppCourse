package com.kubik.userappcourse.ui.base.models

import android.net.Uri
import android.os.Parcelable
import com.kubik.userappcourse.domain.models.GetProductDomainModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetProductModel(
    var id: String = "0",
    var title: String = "",
    var description: String = "",
    var imageUrl: String = "",
    var price: Int = 0,
    var count: Int = 0,
    var imageUri: Uri = Uri.EMPTY,
) : Parcelable {
    fun toDomain() = GetProductDomainModel(id, title, description, imageUrl, price, count)

    fun isEmpty(): Boolean {
        return (id == "0" && title == "" && description == "" && imageUrl == "")
    }

}
