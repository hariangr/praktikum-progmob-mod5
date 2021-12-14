package com.harianugrah.ngampus.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "nick") val nick: String,
    @ColumnInfo(name = "nim") val nim: String,
    @ColumnInfo(name = "is_male") val is_male: Boolean,
    @ColumnInfo(name = "avatar_b64") val avatar_b64: String,
    @ColumnInfo(name = "angkatan") val angkatan: Int,
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
)

