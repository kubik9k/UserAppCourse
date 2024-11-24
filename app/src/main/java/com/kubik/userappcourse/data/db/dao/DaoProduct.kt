package com.kubik.userappcourse.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kubik.userappcourse.data.db.db_entity.BuyProductEntity

@Dao
interface DaoProduct {

    @Insert(entity = BuyProductEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun setProduct(item: BuyProductEntity)

    @Query("SELECT * FROM buy_product")
    fun getListProduct(): List<BuyProductEntity>

    @Query("DELETE from buy_product")
    fun clearTableBuyProduct()
}