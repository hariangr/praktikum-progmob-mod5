package com.harianugrah.haemtei.models

import com.google.gson.annotations.SerializedName


data class OprecsResult (

    @SerializedName("data" ) var data : List<Oprec> = arrayListOf()

)