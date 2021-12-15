package com.harianugrah.haemtei.models

import com.google.gson.annotations.SerializedName

data class ApiImage(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("alternativeText") var alternativeText: String? = null,
    @SerializedName("caption") var caption: String? = null,
    @SerializedName("width") var width: Int? = null,
    @SerializedName("height") var height: Int? = null,
    @SerializedName("formats") var formats: Formats? = Formats()

)


data class Formats(

    @SerializedName("thumbnail") var thumbnail: ApiImageSub? = ApiImageSub(),
    @SerializedName("large") var large: ApiImageSub? = ApiImageSub(),
    @SerializedName("medium") var medium: ApiImageSub? = ApiImageSub(),
    @SerializedName("small") var small: ApiImageSub? = ApiImageSub()

)

data class ApiImageSub(

    @SerializedName("name") var name: String? = null,
    @SerializedName("hash") var hash: String? = null,
    @SerializedName("ext") var ext: String? = null,
    @SerializedName("mime") var mime: String? = null,
    @SerializedName("width") var width: Int? = null,
    @SerializedName("height") var height: Int? = null,
    @SerializedName("size") var size: Double? = null,
    @SerializedName("path") var path: String? = null,
    @SerializedName("url") var url: String? = null

)