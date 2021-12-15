package com.harianugrah.haemtei.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AuthX(
    @ColumnInfo(name = "username") var name: String,
    @ColumnInfo(name = "email") var nick: String,
    @ColumnInfo(name = "jwtToken") var nim: String,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
)

