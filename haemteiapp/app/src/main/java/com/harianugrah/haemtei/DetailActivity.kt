package com.harianugrah.haemtei

import android.R.string.no
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.harianugrah.haemtei.models.OneOprecResult
import com.squareup.picasso.Picasso


class DetailActivity : AppCompatActivity() {
    val TAG = "DETAIL_ACT";

    lateinit var queue: RequestQueue;

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

        val btnDelete = findViewById<Button>(R.id.btnDelete)

        val intent = getIntent();

        val oprec_id_to_show = intent.getIntExtra(INTENT_OPREC_ID, -1);

        btnDelete.setOnClickListener {

            val deleteReq = object : JsonObjectRequest(
                Request.Method.DELETE, Constant.EP_OPREC_ONE + "/" + oprec_id_to_show, null,
                {
                    val intent = Intent(this, ListActivity::class.java);
                    finish()
                    startActivity(intent)
                    Log.v(TAG, it.toString())
                },
                {
                    Snackbar.make(
                        textTitle,
                        "Gagal menghapus",
                        Snackbar.LENGTH_SHORT
                    ).show();
                    Log.e(TAG, it.message.toString())
                },
            ) {
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    val token = AuthSingleton.currentUser!!.jwtToken;
                    headers["Authorization"] = "Bearer $token"
                    return headers
                }
            }


            AlertDialog.Builder(this)
                .setTitle("Oprec")
                .setMessage("Yakin menghapus oprec??")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes,
                    DialogInterface.OnClickListener { dialog, whichButton ->
                        queue.add(deleteReq)
                    })
                .setNegativeButton(no, null).show()

        }

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

                val dateStart = data?.startDate?.split("T")?.get(0) ?: "-"
                val dateEnd = data?.startDate?.split("T")?.get(0) ?: "-"
                textStart.text = dateStart
                textEnd.text = dateEnd

                if (AuthSingleton.currentUser?.username == data?.owner?.username) {
                    btnDelete.visibility = View.VISIBLE
                }


                if (data?.thumbnail != null) {
                    val imgUrl = Constant.BASE_URL + data?.thumbnail?.formats?.large?.url
                    Log.v("IMG URL", imgUrl)
                    Picasso.get().load(imgUrl)
                        .into(imgPoster);
                }

                if (data?.thumbstr != null) {
                    try {
                        val decodedString: ByteArray = Base64.decode(data?.thumbstr, Base64.DEFAULT)
                        val decodedByte =
                            BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
                        imgPoster.setImageBitmap(decodedByte)
                    } catch (ex: Exception) {
                        Log.e("wwww", ex.toString())
                    }
                }

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
    }

    //
    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, ListActivity::class.java);
        finish();
        startActivity(intent);
    }
}