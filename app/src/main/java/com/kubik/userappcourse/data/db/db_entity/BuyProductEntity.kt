package com.kubik.userappcourse.data.db.db_entity

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kubik.userappcourse.domain.models.GetBasketProductDomainModel

@Entity(tableName = "buy_product")
data class BuyProductEntity(
    @PrimaryKey(autoGenerate = true) val idDb: Int = 0,
    @ColumnInfo(name = "address_buy_product") val addressBuyProduct: String = "",
    @ColumnInfo(name = "address_product") val addressProduct: String = "",
    @ColumnInfo(name = "count") val count: Int = 0,
    @ColumnInfo(name = "id") var id: String = "",
    @ColumnInfo(name = "title") var title: String = "",
    @ColumnInfo(name = "description") var description: String = "",
    @ColumnInfo(name = "image_uri") var imageUri: Uri = Uri.EMPTY,
    @ColumnInfo(name = "price") var price: Int = 0,
) {
    fun toGetBasketDomainModel() = GetBasketProductDomainModel(
        addressBuyProduct = addressBuyProduct,
        addressProduct = addressProduct,
        count = count,
        status = 2,
        id = id,
        title = title,
        description = description,
        imageUri = imageUri,
        price = price
    )
}