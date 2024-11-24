package com.kubik.userappcourse.ui.base.base_basket.history.adapter

import com.kubik.userappcourse.ui.base.models.GetBasketProductModel

interface HistoryAdapterListener {
    fun onClick(product: GetBasketProductModel)
}