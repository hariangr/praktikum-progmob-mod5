package com.harianugrah.haemtei

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        setSupportActionBar(findViewById(R.id.registerToolbar))

        supportActionBar?.title = "Register"

        val btnToLogin = findViewById<TextView>(R.id.btnToLogin)
        btnToLogin.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            finish()
            startActivity(intent)
        }

        val inName = findViewById<TextInputLayout>(R.id.inName)
        val inPassword = findViewById<TextInputLayout>(R.id.inPassword)
        val inAkt = findViewById<SeekBar>(R.id.inAkt)
        val inNim = findViewById<TextInputLayout>(R.id.inNim)

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


        val btnRegisterNow = findViewById<Button>(R.id.btnRegisterNow)
        btnRegisterNow.setOnClickListener {
            if (!checkAgree.isChecked) {
                Snackbar.make(
                    btnRegisterNow,
                    "You have to agree with user agreement",
                    Snackbar.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val autolog = radAutolog.isChecked

        }
    }
}