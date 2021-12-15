package com.harianugrah.haemtei

import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import com.harianugrah.haemtei.models.ErrorResult
import com.harianugrah.haemtei.models.LoginResult
import org.json.JSONObject


class MainActivity : AppCompatActivity() {
    val TAG = "LOGIN_MAINACT";
    lateinit var queue: RequestQueue;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        queue = Volley.newRequestQueue(this)

        val inputIdentifier = findViewById<TextInputLayout>(R.id.inputIdentifier);
        val inputPassword = findViewById<TextInputLayout>(R.id.inputPassword);

        val btnLogin = findViewById<Button>(R.id.btnSaveEdit);
        btnLogin.setOnClickListener {
            val identifierVal = inputIdentifier.editText!!.text.toString();
            val passwordVal = inputPassword.editText!!.text.toString();

            val jsonBody = JSONObject()
            jsonBody.put("identifier", identifierVal)
            jsonBody.put("password", passwordVal)

            val loginReq = JsonObjectRequest(
                Request.Method.POST, Constant.EP_LOGIN, jsonBody,
                {

                Log.v(TAG, it["jwt"].toString())

                },
                {

                    var errMsg: String = ""
                    errMsg = if (it.networkResponse.statusCode == 400) {
                        "NIM/Email atau password salah"
                    } else {
                        val res = Gson().fromJson(
                            String(it.networkResponse.data),
                            ErrorResult::class.java
                        );
                        res.error?.message!!
                    }

                    Snackbar.make(
                        btnLogin,
                        "NIM/Email atau password salah",
                        Snackbar.LENGTH_SHORT
                    ).show();

                }
            )
            loginReq.tag = TAG

            queue.add(loginReq)
        }
    }

    override fun onStop() {
        super.onStop()
        queue.cancelAll(TAG);
    }
}