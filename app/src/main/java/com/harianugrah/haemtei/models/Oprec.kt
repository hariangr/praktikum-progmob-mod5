package com.harianugrah.haemtei.models

import com.google.gson.annotations.SerializedName

data class Oprec(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("start_date") var startDate: String? = null,
    @SerializedName("end_date") var endDate: String? = null,
    @SerializedName("createdAt") var createdAt: String? = null,
    @SerializedName("updatedAt") var updatedAt: String? = null,
    @SerializedName("publishedAt") var publishedAt: String? = null,
    @SerializedName("owner") var owner: User? = User(),
    @SerializedName("sies") var sies: List<Sie> = arrayListOf(),
    @SerializedName("registered") var registered: Boolean? = null,
    @SerializedName("thumbnail" ) var thumbnail : ApiImage? = ApiImage()
)