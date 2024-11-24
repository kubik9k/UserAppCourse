package com.kubik.userappcourse.ui.base.base_basket.basket.adapter

import com.kubik.userappcourse.ui.base.models.GetBasketProductModel

interface BasketAdapterListener {
    fun onClick(product: GetBasketProductModel)
}