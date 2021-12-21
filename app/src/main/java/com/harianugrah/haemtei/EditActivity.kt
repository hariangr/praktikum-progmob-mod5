package com.harianugrah.haemtei

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import com.harianugrah.haemtei.models.AppDatabase

class EditActivity : AppCompatActivity() {
    companion object {
        const val INTENT_AUTHX = "ID_AUTHX_HERE";
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        setSupportActionBar(findViewById(R.id.editToolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true);

        val intent = getIntent();

        val authx_id = intent.getIntExtra(EditActivity.INTENT_AUTHX, -1);

        if (authx_id == -1) {
            throw Exception("Not valid authx id")
        }

        val authx = AppDatabase.getInstance(this).authXDao().getById(authx_id);

        val iUsername = findViewById<TextInputLayout>(R.id.editUsername);
        val iEmail = findViewById<TextInputLayout>(R.id.editEmail);

        iUsername.editText?.setText(authx.username)
        iEmail.editText?.setText(authx.email)

        val btnCancel = findViewById<TextView>(R.id.btnToLogin);
        btnCancel.setOnClickListener {
            onBackPressed();
        }

        val btnSave = findViewById<Button>(R.id.btnRegRegist)
        btnSave.setOnClickListener {
            val email = iEmail.editText!!.text.toString();

            authx.email = email
            AppDatabase.getInstance(this).authXDao().update(authx);

            onBackPressed()
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java);
        finish();
        startActivity(intent);
    }
}