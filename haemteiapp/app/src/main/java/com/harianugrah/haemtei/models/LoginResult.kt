package com.harianugrah.haemtei.models


import com.google.gson.annotations.SerializedName

data class LoginResult(

    @SerializedName("jwt") var jwt: String? = null,
    @SerializedName("user") var user: User? = User()

)