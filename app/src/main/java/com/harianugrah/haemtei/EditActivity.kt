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
//    val IMAGE_PICK_ID = 101;
//
//    lateinit var inputImage: ImageView;
//    private var imageUri: Uri? = null;
//
//    companion object {
//        const val INTENT_USER_ID = "ID_USER_PASSED_HERE";
//    }
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_edit)
//        setSupportActionBar(findViewById(R.id.editToolbar))
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true);
//        supportActionBar?.setDisplayShowHomeEnabled(true);
//
//        val intent = getIntent();
//
//        val user_id_to_show = intent.getIntExtra(EditActivity.INTENT_USER_ID, -1);
//
//        if (user_id_to_show == -1) {
//            throw Exception("Not valid user id")
//        }
//
//        detailRoomUser = AppDatabase.getInstance(this).userDao().getById(user_id_to_show);
//
//
//        val inputName = findViewById<TextInputLayout>(R.id.inputIdentifier);
//        val inputNick = findViewById<TextInputLayout>(R.id.inputPassword);
//        val inputNim = findViewById<TextInputLayout>(R.id.inputNim);
//        val inputSeek = findViewById<SeekBar>(R.id.inputSeek);
//        val inputRadio = findViewById<RadioGroup>(R.id.radioGroup);
//        val inputCheckMale = findViewById<RadioButton>(R.id.radioMale);
//        val inputCheckFemale = findViewById<RadioButton>(R.id.radioFemale);
//        val textAngkatan = findViewById<TextView>(R.id.textAngkatan)
//
//        inputImage = findViewById<ImageView>(R.id.imgPrev)
//
//        inputName.editText!!.setText(detailRoomUser.name);
//        inputNick.editText!!.setText(detailRoomUser.nick);
//        inputNim.editText!!.setText(detailRoomUser.nim);
//        if (detailRoomUser.is_male) {
//            inputCheckMale.isChecked = true;
//        } else {
//            inputCheckFemale.isChecked = true;
//        }
//        inputSeek.setProgress(detailRoomUser.angkatan, true);
//        textAngkatan.text = "Angkatan (" + detailRoomUser.angkatan.toString() + ")"
//        inputSeek.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
//            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
//                detailRoomUser.angkatan = p1;
//                textAngkatan.text = "Angkatan (" + detailRoomUser.angkatan.toString() + ")"
//            }
//
//            override fun onStartTrackingTouch(p0: SeekBar?) {
//            }
//
//            override fun onStopTrackingTouch(p0: SeekBar?) {
//            }
//        });
//
//        val decodedString: ByteArray = Base64.decode(detailRoomUser.avatar_b64, Base64.DEFAULT)
//        val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
//        inputImage.setImageBitmap(decodedByte)
//
//        val btnCancel = findViewById<TextView>(R.id.btnRegister);
//        btnCancel.setOnClickListener {
//            onBackPressed();
//        }
//
//        val btnSave = findViewById<Button>(R.id.btnSaveEdit)
//        btnSave.setOnClickListener {
//            val nameVal = inputName.editText!!.text.toString();
//            val nickVal = inputNick.editText!!.text.toString();
//            val nimVal = inputNim.editText!!.text.toString();
//            val radioVal =
//                findViewById<RadioButton>(inputRadio.checkedRadioButtonId).text.toString();
//            val isMaleVal =
//                findViewById<RadioButton>(inputRadio.checkedRadioButtonId).id == R.id.radioMale;
//            val seekVal = inputSeek.progress;
//
//            if (nameVal.isEmpty()) {
//                Toast.makeText(this, "Nama tidak boleh kosong", Toast.LENGTH_SHORT).show();
//                return@setOnClickListener
//            }
//            if (nickVal.isEmpty()) {
//                Toast.makeText(this, "Nick tidak boleh kosong", Toast.LENGTH_SHORT).show();
//                return@setOnClickListener
//            }
//            if (nimVal.isEmpty()) {
//                Toast.makeText(this, "Nim tidak boleh kosong", Toast.LENGTH_SHORT).show();
//                return@setOnClickListener
//            }
//            if (nimVal.length != 10) {
//                Toast.makeText(this, "Nim harus 10 karakter", Toast.LENGTH_SHORT).show();
//                return@setOnClickListener
//            }
//            if (imageUri != null) {
//                val base64img = imageUri?.let { it1 -> Helpers.EncodeImage(this, it1) }.orEmpty()
//                detailRoomUser.avatar_b64 = base64img
//            }
//
//            detailRoomUser.name = nameVal
//            detailRoomUser.nick = nickVal
//            detailRoomUser.nim = nimVal
//            detailRoomUser.is_male = isMaleVal
//            detailRoomUser.angkatan = seekVal
//            AppDatabase.getInstance(this).userDao().update(detailRoomUser);
//
//            onBackPressed()
//        }
//
//    }
//
//    override fun onBackPressed() {
//        super.onBackPressed()
//        val intent = Intent(this, DetailActivity::class.java);
//        intent.putExtra(DetailActivity.INTENT_USER_ID, detailRoomUser.uid);
//        finish();
//        startActivity(intent);
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when (item.itemId) {
//            android.R.id.home -> {
//                val intent = Intent(this, DetailActivity::class.java);
//                intent.putExtra(DetailActivity.INTENT_USER_ID, detailRoomUser.uid);
//                finish();
//                startActivity(intent);
//                true
//            }
//            else -> super.onOptionsItemSelected(item)
//        }
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_ID) {
//            imageUri = data?.data
//            inputImage.setImageURI(imageUri)
//        }
//    }
}