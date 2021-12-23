package com.harianugrah.haemtei

import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import com.harianugrah.haemtei.models.ErrorResult
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class CreateActivity : AppCompatActivity() {
    val IMAGE_PICK_ID = 101;
    val TAG = "CREATE_ACT";

    lateinit var inputImage: ImageView;
    private var imageUri: Uri? = null;
    lateinit var queue: RequestQueue;

    var calStart = Calendar.getInstance()
    var calStartSet = false;
    var calEnd = Calendar.getInstance()
    var calEndSet = false;

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
        val tglStartLbl = findViewById<TextView>(R.id.tglStart)
        val tglEndLbl = findViewById<TextView>(R.id.tglEnd)

// Start Date
        val dateStartListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                calStart.set(Calendar.YEAR, year)
                calStart.set(Calendar.MONTH, monthOfYear)
                calStart.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                calStartSet = true;

                val myFormat = "dd MMM yyy" // mention the format you need
                val sdf = SimpleDateFormat(myFormat, Locale.US)
                tglStartLbl!!.text = sdf.format(calStart.getTime())
            }
        tglStartLbl.setOnClickListener {
            DatePickerDialog(
                this,
                dateStartListener,
                // set DatePickerDialog to point to today's date when it loads up
                calStart.get(Calendar.YEAR),
                calStart.get(Calendar.MONTH),
                calStart.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
// End Date
        val dateEndListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                calEnd.set(Calendar.YEAR, year)
                calEnd.set(Calendar.MONTH, monthOfYear)
                calEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                calEndSet = true

                val myFormat = "dd MMM yyyy" // mention the format you need
                val sdf = SimpleDateFormat(myFormat, Locale.US)
                tglEndLbl!!.text = sdf.format(calEnd.getTime())
                Log.v(TAG, calEnd.time.toGMTString())
            }
        tglEndLbl.setOnClickListener {
            DatePickerDialog(
                this,
                dateEndListener,
                // set DatePickerDialog to point to today's date when it loads up
                calEnd.get(Calendar.YEAR),
                calEnd.get(Calendar.MONTH),
                calEnd.get(Calendar.DAY_OF_MONTH)
            ).show()
        }


        val btnCreate = findViewById<Button>(R.id.btnCrt)
        btnCreate.setOnClickListener {
            val base64img = imageUri?.let { it1 -> Helpers.EncodeImage(this, it1) }.orEmpty()

            val jsonBody = JSONObject()
            jsonBody.put("title", crtTitle.editText!!.text)
            jsonBody.put("description", crtDesc.editText!!.text)
            jsonBody.put("thumbstr", base64img)
            jsonBody.put("owner", AuthSingleton.currentUser!!.model_id)
            if (calStartSet) {
                jsonBody.put("start_date", datetimeToIso(calStart))
            }
            if (calEndSet) {
                jsonBody.put("end_date", datetimeToIso(calEnd))
            }

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

    fun datetimeToIso(s: Calendar) : String {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        return sdf.format(s.getTime())
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_ID) {
            imageUri = data?.data
            inputImage.setImageURI(imageUri)
        }
    }
}