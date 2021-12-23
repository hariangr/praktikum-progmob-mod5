package com.harianugrah.haemtei.models


import com.google.gson.annotations.SerializedName


data class ErrorResult(

    @SerializedName("data") var data: String? = null,
    @SerializedName("error") var error: Error? = Error()

)