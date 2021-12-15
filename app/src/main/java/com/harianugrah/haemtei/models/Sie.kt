package com.harianugrah.haemtei.models


import com.google.gson.annotations.SerializedName

data class Sie(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("createdAt") var createdAt: String? = null,
    @SerializedName("updatedAt") var updatedAt: String? = null

)