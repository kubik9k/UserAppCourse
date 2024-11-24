package com.kubik.userappcourse.domain.models

import com.kubik.userappcourse.data.models.ProductDataModel
import com.kubik.userappcourse.ui.base.models.GetProductModel

data class GetProductDomainModel(
    var id: String = "",
    var title: String = "",
    var description: String = "",
    var imageUrl: String = "",
    var price: Int = 0,
    var count: Int = 0,
) {
    fun toUi() = GetProductModel(id, title, description, imageUrl, price, count)

    fun toDataSendModel() = ProductDataModel(title, description, imageUrl, price, count)

    fun isEmpty(): Boolean =
        (title == "" && description == "" && imageUrl == "" && price == 0 && count == 0)
}
