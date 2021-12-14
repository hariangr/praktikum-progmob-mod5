package com.harianugrah.ngampus.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "nick") var nick: String,
    @ColumnInfo(name = "nim") var nim: String,
    @ColumnInfo(name = "is_male") var is_male: Boolean,
    @ColumnInfo(name = "avatar_b64") var avatar_b64: String,
    @ColumnInfo(name = "angkatan") var angkatan: Int,
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
)

