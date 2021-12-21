package com.harianugrah.haemtei

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.harianugrah.haemtei.models.AppDatabase
import com.harianugrah.haemtei.models.OneOprecResult
import com.harianugrah.haemtei.models.OprecsResult
import com.squareup.picasso.Picasso
import java.lang.Exception

class DetailActivity : AppCompatActivity() {
    val TAG = "DETAIL_ACT";

    //    lateinit var detailRoomUser: RoomUser;
    lateinit var queue: RequestQueue;

    //
    companion object {
        val INTENT_OPREC_ID = "ID_OPREC_PASSED_HERE";
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(findViewById(R.id.toolbarDetail))

        queue = Volley.newRequestQueue(this)

        val textTitle = findViewById<TextView>(R.id.textTitle)
        val textDesc = findViewById<TextView>(R.id.textDesc)
        val textStart = findViewById<TextView>(R.id.textStartDate)
        val textEnd = findViewById<TextView>(R.id.textEndDate)
        val textOwner = findViewById<TextView>(R.id.textOfferer)
        val imgPoster = findViewById<ImageView>(R.id.imgPoster)

        val intent = getIntent();

        val oprec_id_to_show = intent.getIntExtra(INTENT_OPREC_ID, -1);

        if (oprec_id_to_show == -1) {
            throw Exception("Not valid user id")
        }

        val detailReq = object : JsonObjectRequest(
            Request.Method.GET, Constant.EP_OPREC_ONE + "/" + oprec_id_to_show, null,
            {
                val res = Gson().fromJson(it.toString(), OneOprecResult::class.java)
                val data = res.data

                textTitle.text = data?.title;
                textDesc.text = data?.description ?: "-"
                textOwner.text = data?.owner?.username

                val dateStart = data?.startDate?.split("T")?.get(0)
                val dateEnd = data?.startDate?.split("T")?.get(0)
                textStart.text = dateStart
                textEnd.text = dateEnd


                val imgUrl = Constant.BASE_URL +data?.thumbnail?.formats?.medium?.url
                Picasso.get().load(imgUrl)
                    .into(imgPoster);
            },
            {

                Snackbar.make(
                    textTitle,
                    "Ada yang salah",
                    Snackbar.LENGTH_SHORT
                ).show();

            },
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                val token = AuthSingleton.currentUser!!.jwtToken;
                headers["Authorization"] = "Bearer $token"
                return headers
            }
        }

        detailReq.tag = TAG

        queue.add(detailReq)
//
//        detailRoomUser = AppDatabase.getInstance(this).userDao().getById(user_id_to_show);
//
//        val imgProfile = findViewById<ImageView>(R.id.imgProfile);
//        val textName = findViewById<TextView>(R.id.textName);
//        val textNick = findViewById<TextView>(R.id.textNick);
//        val textNim = findViewById<TextView>(R.id.textNim);
//        val textSex = findViewById<TextView>(R.id.textSex);
//        val textAkt = findViewById<TextView>(R.id.textAkt);
//        supportActionBar?.title = "Detail " + detailRoomUser.nick;
//
//        textName.text = detailRoomUser.name;
//        textNick.text = detailRoomUser.nick;
//        textNim.text = detailRoomUser.nim;
//        textSex.text = if (detailRoomUser.is_male) "Laki-laki" else "Perempuan";
//        textAkt.text = "20" + detailRoomUser.angkatan.toString();
//
//        val decodedString: ByteArray = Base64.decode(detailRoomUser.avatar_b64, Base64.DEFAULT)
//        val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
//        imgProfile.setImageBitmap(decodedByte)

//        val btnDelete = findViewById<Button>(R.id.btnDelete);
//        val btnEdit = findViewById<Button>(R.id.btnEdit);

//        btnDelete.setOnClickListener {
//            AppDatabase.getInstance(this).userDao().delete(detailRoomUser);
//
//            val intent = Intent(this, ListActivity::class.java);
//            finish();
//            startActivity(intent);
//        }
//
//        btnEdit.setOnClickListener {
//            val intent = Intent(this, EditActivity::class.java);
//            intent.putExtra(EditActivity.INTENT_USER_ID, detailRoomUser.uid);
//
//            finish();
//            startActivity(intent);
//        }

    }
//
//    override fun onBackPressed() {
//        super.onBackPressed()
//        val intent = Intent(this, ListActivity::class.java);
//        finish();
//        startActivity(intent);
//    }
}