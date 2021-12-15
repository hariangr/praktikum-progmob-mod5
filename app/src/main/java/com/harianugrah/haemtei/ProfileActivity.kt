package com.harianugrah.haemtei

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class   ProfileActivity : AppCompatActivity() {
    private val TAG_LIFECYCLE = "LIFEMSG";

    lateinit var imgProfile: ImageView;
    override fun onStart() {
        super.onStart()
        Log.v(TAG_LIFECYCLE, "Activity Started")
        Toast.makeText(this, "Activity Started", Toast.LENGTH_SHORT).show()
    }

    override fun onPause() {
        super.onPause()
        Log.v(TAG_LIFECYCLE, "Activity Paused")
        Toast.makeText(this, "Activity Paused", Toast.LENGTH_SHORT).show()
    }

    override fun onStop() {
        super.onStop()
        Log.v(TAG_LIFECYCLE, "Activity Stopped")
        Toast.makeText(this, "Activity Stopped", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.v(TAG_LIFECYCLE, "Activity Destroyed")
        Toast.makeText(this, "Activity Destroyed", Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        imgProfile = findViewById(R.id.imgProfile);

        val textProfil = findViewById<TextView>(R.id.textProfil);
        val textName = findViewById<TextView>(R.id.textName);
        val textNick = findViewById<TextView>(R.id.textNick);
        val textNim = findViewById<TextView>(R.id.textNim);
        val textSex = findViewById<TextView>(R.id.textSex);
        val textAkt = findViewById<TextView>(R.id.textAkt);

        textProfil.text = "Profil " + intent.getStringExtra("nick");
        textName.text = intent.getStringExtra("name");
        textNick.text = intent.getStringExtra("nick");
        textNim.text = intent.getStringExtra("nim");
        textSex.text = intent.getStringExtra("radio");
        textAkt.text = "20" + intent.getIntExtra("seek", 19).toString();

        imgProfile.setImageURI(intent.getParcelableExtra("img"));


        val btnToList = findViewById<Button>(R.id.btnToList);
        btnToList.setOnClickListener {
            val intent = Intent(this, ListActivity::class.java)
            startActivity(intent)
        }
    }
}