package com.harianugrah.haemtei

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import com.harianugrah.haemtei.adapter.ListAdapterAccount
import com.harianugrah.haemtei.models.AppDatabase
import com.harianugrah.haemtei.models.AuthX
import com.harianugrah.haemtei.models.ErrorResult
import com.harianugrah.haemtei.models.LoginResult
import org.json.JSONObject


class MainActivity : AppCompatActivity() {
    val TAG = "LOGIN_MAINACT";
    lateinit var queue: RequestQueue;
    lateinit var recyclerView: RecyclerView;
    var authxList: ArrayList<AuthX> = ArrayList();

    fun refreshList() {
        val authxs = AppDatabase.getInstance(this).authXDao().getAll();
        authxList.clear();
        authxList.addAll(authxs);
        recyclerView = findViewById<RecyclerView>(R.id.rvAuthx)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter =
            ListAdapterAccount(authxList, fun(toShow) {
                AuthSingleton.currentUser = toShow;

                Toast.makeText(this, "Hi ${toShow.username}", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, ListActivity::class.java);
                startActivity(intent)
            }, fun(toEdit) {
                val intent = Intent(this, EditActivity::class.java);
                intent.putExtra(EditActivity.INTENT_AUTHX, toEdit.id)
                startActivity(intent)
            },fun(toDel) {
                AppDatabase.getInstance(this).authXDao().delete(toDel)
                refreshList()
            })
    }

    override fun onResume() {
        super.onResume()
        refreshList();
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        queue = Volley.newRequestQueue(this)

        val inputIdentifier = findViewById<TextInputLayout>(R.id.regPassword);
        val inputPassword = findViewById<TextInputLayout>(R.id.inputPassword);

        refreshList();

        val btnRegister = findViewById<TextView>(R.id.btnToLogin)
        btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java);
            finish()
            startActivity(intent)
        }

        val btnLogin = findViewById<Button>(R.id.btnRegRegist);
        btnLogin.setOnClickListener {
            val identifierVal = inputIdentifier.editText!!.text.toString();
            val passwordVal = inputPassword.editText!!.text.toString();

            val jsonBody = JSONObject()
            jsonBody.put("identifier", identifierVal)
            jsonBody.put("password", passwordVal)

            val loginReq = JsonObjectRequest(
                Request.Method.POST, Constant.EP_LOGIN, jsonBody,
                {
                    val res = Gson().fromJson(it.toString(), LoginResult::class.java)
                    val authX = AuthX(res.user!!.username!!, res.user!!.email!!, res!!.jwt!!, res!!.user!!.id)

                    AppDatabase.getInstance(this).authXDao().insertAll(authX)

                    AuthSingleton.currentUser = authX;

                    Toast.makeText(this, "Hi ${authX.username}", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, ListActivity::class.java);
                    startActivity(intent)
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