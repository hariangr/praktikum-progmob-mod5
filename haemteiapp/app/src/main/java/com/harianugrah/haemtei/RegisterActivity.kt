package com.harianugrah.haemtei

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import com.harianugrah.haemtei.models.AppDatabase
import com.harianugrah.haemtei.models.AuthX
import com.harianugrah.haemtei.models.ErrorResult
import com.harianugrah.haemtei.models.LoginResult
import org.json.JSONObject

class RegisterActivity : AppCompatActivity() {
    val TAG = "REGISTER_ACT";
    lateinit var queue: RequestQueue;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        setSupportActionBar(findViewById(R.id.registerToolbar))

        queue = Volley.newRequestQueue(this)

        supportActionBar?.title = "Register"

        val btnToLogin = findViewById<TextView>(R.id.btnRegRegist)
        btnToLogin.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            finish()
            startActivity(intent)
        }

        val inName = findViewById<TextInputLayout>(R.id.regName)
        val inEmail = findViewById<TextInputLayout>(R.id.regEmail)
        val inPassword = findViewById<TextInputLayout>(R.id.regPassword)
        val inAkt = findViewById<SeekBar>(R.id.regAkt)
        val inNim = findViewById<TextInputLayout>(R.id.regNim)

        val textAkt = findViewById<TextView>(R.id.textAngkatan)
        val checkAgree = findViewById<CheckBox>(R.id.checkAgree)
        val radAutolog = findViewById<RadioButton>(R.id.radioAutolog)

        inAkt.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                inNim.prefixText = String.format("%02d", p1)
                textAkt.text = "Angkatan " + String.format("%02d", p1)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })


        val btnRegisterNow = findViewById<Button>(R.id.btnRegRegist)
        btnRegisterNow.setOnClickListener {
            if (!checkAgree.isChecked) {
                Snackbar.make(
                    btnRegisterNow,
                    "Kamu harus setuju dengan persyaratan pengguna",
                    Snackbar.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val autolog = radAutolog.isChecked

            val nama = inName.editText!!.text.toString()
            val email = inEmail.editText!!.text.toString()
            val password = inPassword.editText!!.text.toString()
            val nim = String.format("%02d", inAkt.progress) + inNim.editText!!.text.toString()

            if (nama.isEmpty() || email.isEmpty() || password.isEmpty() || inNim.editText?.text.toString().isEmpty()) {
                Snackbar.make(
                    btnRegisterNow,
                    "Formulir tidak boleh kosong",
                    Snackbar.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val builder = AlertDialog.Builder(this);
            builder.setTitle("Konfirmasi Registrasi")
                .setMessage(
                    "Nama ${nama}\nEmail: ${email}\nNIM: ${nim}"
                )
                .setPositiveButton("OK", DialogInterface.OnClickListener { dialogInterface, i ->
                    run {

                        val jsonBody = JSONObject()
                        jsonBody.put("name", nama)
                        jsonBody.put("password", password)
                        jsonBody.put("email", email)
                        jsonBody.put("username", nim)

                        val registReq = JsonObjectRequest(
                            Request.Method.POST, Constant.EP_REGISTER, jsonBody,
                            {
                                if (autolog) {
                                    val res = Gson().fromJson(it.toString(), LoginResult::class.java)
                                    val authX = AuthX(res.user!!.username!!, res.user!!.email!!, res!!.jwt!!, res!!.user!!.id)

                                    AppDatabase.getInstance(this).authXDao().insertAll(authX)

                                    AuthSingleton.currentUser = authX;

                                    Toast.makeText(this, "Hi ${authX.username}", Toast.LENGTH_SHORT).show()

                                    val intent = Intent(this, ListActivity::class.java);
                                    startActivity(intent)
                                } else {
                                    Toast.makeText(this, "Kamu berhasil mendaftar", Toast.LENGTH_SHORT).show()
                                    val intent = Intent(this, MainActivity::class.java);
                                    startActivity(intent)
                                }
                            },
                            {
                                var errMsg: String = ""
                                errMsg = if (it.networkResponse.statusCode != 400) {
                                    "Ada masalah"
                                } else {
                                    val res = Gson().fromJson(
                                        String(it.networkResponse.data),
                                        ErrorResult::class.java
                                    );
                                    res.error?.message!!
                                }

                                Snackbar.make(
                                    btnRegisterNow,
                                    errMsg,
                                    Snackbar.LENGTH_SHORT
                                ).show();

                            }
                        )
                        registReq.tag = TAG

                        queue.add(registReq)
                    }
                })
                .setNegativeButton("Batal", DialogInterface.OnClickListener { dialogInterface, i ->
                    run {}
                })
                .create()
                .show()
        }
    }
}