package com.kubik.userappcourse.domain.models

import com.kubik.userappcourse.data.models.ProductDataModel

data class ProductDomainModel(
    val title: String = "",
    val description: String = "",
    var imageUrl: String = "",
    val price: Int = 0,
    val count: Int = 0,
){
    fun toData() = ProductDataModel(title, description, imageUrl, price, count)
}
