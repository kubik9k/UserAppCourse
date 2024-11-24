package com.kubik.userappcourse.domain.models

import android.net.Uri

data class AddProductDomainModel(
    var galleryUri: Uri = Uri.EMPTY,
    var title: String = "",
    var description: String = "",
    var price: Int = 0,
    var count: Int = 0,
) {
    fun toProductDomain() = ProductDomainModel(title, description, "", price, count)
}
