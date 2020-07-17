package ru.job4j.cryptocompareapp.repository.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.job4j.cryptocompareapp.repository.database.entity.Datum

@Dao
interface DatumDao {
    @Query("SELECT * FROM datum ORDER BY totalVolume24HTo")
    fun getDatumList(): List<Datum>

    @Query("SELECT * FROM datum WHERE id =:id")
    fun getDatumById(id: Int): Datum

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDatumList(datumList: List<Datum>)
}