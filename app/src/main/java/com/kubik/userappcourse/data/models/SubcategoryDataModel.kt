package com.kubik.userappcourse.data.models

import com.kubik.userappcourse.domain.models.SubcategoryDomainModel

data class SubcategoryDataModel(
    val name: String = "",
    val nameDbItem: String = "",
    val nameDbCountItem: String = "",
) {
    fun toDomain() = SubcategoryDomainModel(name, nameDbItem, nameDbCountItem)
}
