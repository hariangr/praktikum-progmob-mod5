package com.harianugrah.haemtei.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AuthX(
    @ColumnInfo(name = "username") var username: String,
    @ColumnInfo(name = "email") var email: String,
    @ColumnInfo(name = "jwtToken") var jwtToken: String,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
)

