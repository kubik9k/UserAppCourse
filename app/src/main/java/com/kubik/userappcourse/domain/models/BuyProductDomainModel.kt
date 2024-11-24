package com.kubik.userappcourse.domain.models

import com.kubik.userappcourse.data.models.BuyProductDataModel

data class BuyProductDomainModel(
    val addressBuyProduct: String = "",
    val addressProduct: String = "",
    val count: Int = 0,
    var status: Int = 0,
) {
    fun toData() = BuyProductDataModel(addressBuyProduct, addressProduct,count, status)
}