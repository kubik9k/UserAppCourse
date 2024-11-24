package com.kubik.userappcourse.data.models

import com.kubik.userappcourse.domain.models.HomeDomainModel

data class HomeDataModel(
    val name: String = "",
    val imgId: Int = 0,
    val nameDbItem: String = "",
) {
    fun toDomain() = HomeDomainModel(name, imgId, nameDbItem)
}