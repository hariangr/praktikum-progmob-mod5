package com.harianugrah.haemtei

class Constant {
    companion object {
        const val BASE_URL = "https://progmod5.herokuapp.com";

        const val EP_LOGIN = "$BASE_URL/api/auth/local";
        const val EP_FIND_OPREC = "$BASE_URL/api/oprecs";
        const val EP_REGISTER = "$BASE_URL/api/auth/local/register";
        const val EP_OPREC_ONE = "$BASE_URL/api/oprecs";
    }
}