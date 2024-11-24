package com.kubik.userappcourse.data.models

import com.kubik.userappcourse.domain.models.BuyProductDomainModel

data class BuyProductDataModel(
    var addressBuyProduct: String = "",
    var addressProduct: String = "",
    var count: Int = 0,
    var status: Int = 0,
){
    fun toDomain() = BuyProductDomainModel(addressBuyProduct, addressProduct, count, status)
}