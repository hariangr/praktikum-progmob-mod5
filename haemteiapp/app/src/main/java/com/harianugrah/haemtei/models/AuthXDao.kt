package com.harianugrah.haemtei.models
import androidx.room.*

@Dao
interface AuthXDao {
    @Query("SELECT * FROM AuthX")
    fun getAll(): List<AuthX>

    @Query("SELECT * FROM AuthX WHERE id == (:id)")
    fun getById(id: Int): AuthX

    @Insert
    fun insertAll(vararg authxs: AuthX)

    @Delete
    fun delete(authx: AuthX)

    @Update()
    fun update(authx: AuthX)
}
