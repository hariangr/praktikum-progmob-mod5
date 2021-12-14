package com.harianugrah.ngampus

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.harianugrah.ngampus.models.AppDatabase
import com.harianugrah.ngampus.models.User
import java.lang.Exception

class DetailActivity : AppCompatActivity() {
    lateinit var detailUser: User;

    companion object {
        val INTENT_USER_ID = "ID_USER_PASSED_HERE";
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val intent = getIntent();

        val user_id_to_show = intent.getIntExtra(INTENT_USER_ID, -1);

        if (user_id_to_show == -1) {
            throw Exception("Not valid user id")
        }

        detailUser = AppDatabase.getInstance(this).userDao().getById(user_id_to_show);

        val imgProfile = findViewById<ImageView>(R.id.imgProfile);
        val textName = findViewById<TextView>(R.id.textName);
        val textNick = findViewById<TextView>(R.id.textNick);
        val textNim = findViewById<TextView>(R.id.textNim);
        val textSex = findViewById<TextView>(R.id.textSex);
        val textAkt = findViewById<TextView>(R.id.textAkt);

        textName.text = detailUser.name;
        textNick.text = detailUser.nick;
        textNim.text = detailUser.nim;
        textSex.text = if (detailUser.is_male) "Laki-laki" else "Perempuan";
        textAkt.text = "20" + intent.getIntExtra("seek", 19).toString();

        val decodedString: ByteArray = Base64.decode(detailUser.avatar_b64, Base64.DEFAULT)
        val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
        imgProfile.setImageBitmap(decodedByte)

        val btnDelete = findViewById<Button>(R.id.btnDelete);
        val btnEdit = findViewById<Button>(R.id.btnEdit);

        btnDelete.setOnClickListener {
            AppDatabase.getInstance(this).userDao().delete(detailUser);

            val intent = Intent(this, ListActivity::class.java);
            finish();
            startActivity(intent);
        }
    }
}