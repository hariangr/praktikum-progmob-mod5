package com.harianugrah.haemtei.models

import com.google.gson.annotations.SerializedName

data class Error(

    @SerializedName("status") var status: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("message") var message: String? = null,
    @SerializedName("details") var details: Details? = Details()

)