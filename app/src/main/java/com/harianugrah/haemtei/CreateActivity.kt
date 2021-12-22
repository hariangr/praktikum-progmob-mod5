package com.harianugrah.haemtei

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import com.harianugrah.haemtei.models.AuthX
import com.harianugrah.haemtei.models.ErrorResult
import com.harianugrah.haemtei.models.OprecsResult
import org.json.JSONObject

class CreateActivity : AppCompatActivity() {
    val IMAGE_PICK_ID = 101;
    val TAG = "CREATE_ACT";

    lateinit var inputImage: ImageView;
    private var imageUri: Uri? = null;
    lateinit var queue: RequestQueue;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        setSupportActionBar(findViewById(R.id.toolbarCrt))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        inputImage = findViewById(R.id.imgPrev)
        queue = Volley.newRequestQueue(this)

        val btnImage = findViewById<Button>(R.id.btnImage);
        btnImage.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            startActivityForResult(gallery, IMAGE_PICK_ID);
        }

        val crtTitle = findViewById<TextInputLayout>(R.id.crtTitle)
        val crtDesc = findViewById<TextInputLayout>(R.id.crtDesc)

        val btnCreate = findViewById<Button>(R.id.btnCrt)
        btnCreate.setOnClickListener {
            val base64img = imageUri?.let { it1 -> Helpers.EncodeImage(this, it1) }.orEmpty()

            val jsonBody = JSONObject()
            jsonBody.put("title", crtTitle.editText!!.text)
            jsonBody.put("description", crtDesc.editText!!.text)
            jsonBody.put("thumbstr", base64img)
            jsonBody.put("owner", AuthSingleton.currentUser!!.model_id)

            val jsonBodyData = JSONObject()
            jsonBodyData.put("data", jsonBody)

            Log.v("mn", jsonBodyData.toString())

            val createReq = object : JsonObjectRequest(

                Request.Method.POST, Constant.EP_FIND_OPREC, jsonBodyData,
                {
                    val intent = Intent(this, ListActivity::class.java);
                    finish();
                    startActivity(intent);
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
                        btnCreate,
                        errMsg,
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

            createReq.tag = TAG

            queue.add(createReq)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_ID) {
            imageUri = data?.data
            inputImage.setImageURI(imageUri)
        }
    }
}