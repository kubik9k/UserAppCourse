package com.kubik.userappcourse.domain.models

import com.kubik.userappcourse.ui.base.models.SubcategoryModel

data class SubcategoryDomainModel(
    val name: String = "",
    val nameDbItem: String = "",
    val nameDbCountItem: String = "",
) {
    fun toSubcategory() = SubcategoryModel(name, nameDbItem)
}

