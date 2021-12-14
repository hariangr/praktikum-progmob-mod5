package com.harianugrah.ngampus.models

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<User>


    @Query("SELECT * FROM user WHERE uid == (:id)")
    fun getById(id: Int): User

    @Query("SELECT * FROM user WHERE name LIKE '%' || :name || '%' OR " +
            "nick LIKE '%' || :name || '%'")
    fun findByName(name: String): List<User>

    @Insert
    fun insertAll(vararg users: User)

    @Delete
    fun delete(user: User)
}
