package com.harianugrah.haemtei

import com.harianugrah.haemtei.models.AuthX

class AuthSingleton {
    companion object {
        var currentUser : AuthX? = null;
    }
}