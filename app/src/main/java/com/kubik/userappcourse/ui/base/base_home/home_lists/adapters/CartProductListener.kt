package com.kubik.userappcourse.ui.base.base_home.home_lists.adapters

import com.kubik.userappcourse.ui.base.models.GetProductModel

interface CartProductListener {

    fun onClickProduct(item: GetProductModel)

}