package com.kubik.userappcourse.data.models

import com.kubik.userappcourse.domain.models.GetProductDomainModel

data class GetProductDataModel(
    var id: String = "",
    var title: String = "",
    var description: String = "",
    var imageUrl: String = "",
    var price: Int = 0,
    var count: Int = 0,
){
    fun toDomain() = GetProductDomainModel(id, title, description, imageUrl, price, count)
}
