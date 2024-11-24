package com.kubik.userappcourse.data

import com.kubik.userappcourse.Constants
import com.kubik.userappcourse.data.models.BuyProductDataModel
import com.kubik.userappcourse.data.models.ProductDataModel
import com.kubik.userappcourse.data.models.UserDataModel

fun getUserMap(user: UserDataModel) = hashMapOf(
    Constants.USER_COLLECTION_DOCUMENT_FIRST_NAME to user.first_name,
    Constants.USER_COLLECTION_DOCUMENT_LAST_NAME to user.last_name,
    Constants.USER_COLLECTION_DOCUMENT_PASSWORD to user.password,
    Constants.USER_COLLECTION_DOCUMENT_TYPE to user.type_user,
)

fun getProductMap(product: ProductDataModel) = hashMapOf(
    Constants.PRODUCT_TITLE to product.title,
    Constants.PRODUCT_DESCRIPTION to product.description,
    Constants.PRODUCT_IMAGE_URL_STORAGE to product.imageUrl,
    Constants.PRODUCT_PRICE to product.price,
    Constants.PRODUCT_COUNT to product.count,
)

fun getBuyProductMap(product: BuyProductDataModel) = hashMapOf(
    Constants.PRODUCT_ADDRESS to product.addressProduct,
    Constants.PRODUCT_COUNT to product.count,
    Constants.PRODUCT_STATUS to product.status,
)
