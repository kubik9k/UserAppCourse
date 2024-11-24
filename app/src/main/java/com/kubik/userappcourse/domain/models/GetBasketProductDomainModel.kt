package com.kubik.userappcourse.domain.models

import android.net.Uri
import com.kubik.userappcourse.data.db.db_entity.BuyProductEntity
import com.kubik.userappcourse.ui.base.models.GetBasketProductModel

data class GetBasketProductDomainModel(
    val addressBuyProduct: String = "",
    val addressProduct: String = "",
    val count: Int = 0,
    var status: Int = 0,
    var id: String = "",
    var title: String = "",
    var description: String = "",
    var imageUri: Uri = Uri.EMPTY,
    var price: Int = 0,
) {
    fun toUi() = GetBasketProductModel(
        addressBuyProduct,
        addressProduct,
        count,
        status,
        id,
        title,
        description,
        imageUri,
        price
    )

    fun toBuyProductEntity() = BuyProductEntity(
        addressProduct = addressProduct,
        addressBuyProduct = addressBuyProduct,
        count = count,
        id = id,
        title = title,
        description = description,
        imageUri = imageUri,
        price = price
    )
}