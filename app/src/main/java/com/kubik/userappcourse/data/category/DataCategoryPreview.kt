package com.kubik.userappcourse.data.category

import com.kubik.userappcourse.R
import com.kubik.userappcourse.data.models.HomeDataModel
import com.kubik.userappcourse.data.models.SubcategoryDataModel

internal class DataCategoryPreview {

    suspend fun getData() = listOf(
        HomeDataModel("Beauty and health", R.drawable.home, "beauty_and_health"),
        HomeDataModel("Home appliances", R.drawable.home, "home_appliances"),
        HomeDataModel("Laptops and computers", R.drawable.home, "laptops_and_computers"),
        HomeDataModel("Phone and gadgets", R.drawable.home, "phone_and_gadgets"),
        HomeDataModel("TV and video", R.drawable.home, "tv_and_video"),
        //TODO: если необходимо будет то подгрузить из бд
    )

    suspend fun getNameCategory(numCategory: Int): String = getData()[numCategory].name
    suspend fun getNameCategoryDb(numCategory: Int): String = getData()[numCategory].nameDbItem

    suspend fun getSubcategoryData(numItem: Int): List<SubcategoryDataModel> {
        return when (numItem) {
            0 -> listOf(
                SubcategoryDataModel("Hair care", "hair_care", "hair_care_num"),
                SubcategoryDataModel(
                    "Haircut and shaving", "haircut_and_shaving", "haircut_and_shaving_num"
                ),
                SubcategoryDataModel(
                    "Health products", "health_products", "health_products_num"
                ),
            );
            1 -> listOf(
                SubcategoryDataModel(
                    "Cleaning equipment and accessories",
                    "cleaning_equipment_and_accessories",
                    "cleaning_equipment_and_accessories_num"
                ),
                SubcategoryDataModel(
                    "Climate technology", "climate_technology", "climate_technology_num"
                ),
                SubcategoryDataModel("Kitchen", "kitchen", "kitchen_num"),
            );
            2 -> listOf(
                SubcategoryDataModel(
                    "Computer components", "computer_components", "computer_components_num"
                ),
                SubcategoryDataModel(
                    "Input devices and storage",
                    "input_devices_and_storage",
                    "input_devices_and_storage_num"
                ),
                SubcategoryDataModel(
                    "Laptops and computer", "laptops_and_computer", "laptops_and_computer_num"
                ),
                SubcategoryDataModel(
                    "Network equipment", "network_equipment", "network_equipment_num"
                ),
                SubcategoryDataModel(
                    "Output devices", "output_devices", "output_devices_num"
                ),
            );
            3 -> listOf(
                SubcategoryDataModel("Gadgets", "gadgets", "gadgets_num"),
                SubcategoryDataModel("Phone", "phone", "phone_num"),
                SubcategoryDataModel("Portable sound", "portable_sound", "portable_sound_num"),
                SubcategoryDataModel("Tablets", "tablets", "tablets_num"),
            );
            else -> listOf(
                SubcategoryDataModel(
                    "Audio and accessories", "audio_and_accessories", "audio_and_accessories"
                ),
                SubcategoryDataModel("Consoles", "consoles", "consoles"),
                SubcategoryDataModel("TV", "tv", "tv"),
            )
        }
        //TODO: если необходимо будет то подгрузить из бд
    }

    suspend fun getSubcategoryName(numCategory: Int, numSubcategory: Int): String =
        getSubcategoryData(numCategory)[numSubcategory].name

    suspend fun getSubcategoryNameDb(numCategory: Int, numSubcategory: Int): String =
        getSubcategoryData(numCategory)[numSubcategory].nameDbItem

    suspend fun getNameDbSubcategoryCountProduct(numCategory: Int, numSubcategory: Int)
            : String = getSubcategoryData(numCategory)[numSubcategory].nameDbCountItem

}