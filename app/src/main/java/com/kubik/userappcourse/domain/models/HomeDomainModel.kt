package com.kubik.userappcourse.domain.models

import com.kubik.userappcourse.ui.base.models.HomeModel

data class HomeDomainModel(
    val name: String = "",
    val imgId: Int = 0,
    val nameDbItem: String = "",
) {
    fun toUiModels() = HomeModel(name, imgId, nameDbItem)
}