package com.harianugrah.ngampus

import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.harianugrah.ngampus.models.AppDatabase
import com.harianugrah.ngampus.models.User

class MainActivity : AppCompatActivity() {
    val IMAGE_PICK_ID = 101;

    lateinit var inputImage: ImageView;
    private var imageUri: Uri? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val inputName = findViewById<TextInputLayout>(R.id.inputName);
        val inputNick = findViewById<TextInputLayout>(R.id.inputNick);
        val inputNim = findViewById<TextInputLayout>(R.id.inputNim);
        val inputRadio = findViewById<RadioGroup>(R.id.radioGroup);
        val inputSeek = findViewById<SeekBar>(R.id.inputSeek);
        val inputCheck = findViewById<CheckBox>(R.id.checkBox);

        inputImage = findViewById<ImageView>(R.id.imgPrev)

        val btnImage = findViewById<Button>(R.id.btnImage);
        btnImage.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            startActivityForResult(gallery, IMAGE_PICK_ID);
        }

        val btnSkip = findViewById<TextView>(R.id.btnSkip);
        btnSkip.setOnClickListener {
            val intent = Intent(this, ListActivity::class.java)
            startActivity(intent)
        }

        val btnCampus = findViewById<Button>(R.id.btnCampus);
        btnCampus.setOnClickListener {
            val nameVal = inputName.editText!!.text.toString();
            val nickVal = inputNick.editText!!.text.toString();
            val nimVal = inputNim.editText!!.text.toString();
            val radioVal =
                findViewById<RadioButton>(inputRadio.checkedRadioButtonId).text.toString();
            val isMaleVal =
                findViewById<RadioButton>(inputRadio.checkedRadioButtonId).id == R.id.radioMale;
            val seekVal = inputSeek.progress;
            val checkVal = inputCheck.isChecked;

            if (nameVal.isEmpty()) {
                Toast.makeText(this, "Nama tidak boleh kosong", Toast.LENGTH_SHORT).show();
                return@setOnClickListener
            }
            if (nickVal.isEmpty()) {
                Toast.makeText(this, "Nick tidak boleh kosong", Toast.LENGTH_SHORT).show();
                return@setOnClickListener
            }
            if (nimVal.isEmpty()) {
                Toast.makeText(this, "Nim tidak boleh kosong", Toast.LENGTH_SHORT).show();
                return@setOnClickListener
            }
            if (nimVal.length != 10) {
                Toast.makeText(this, "Nim harus 10 karakter", Toast.LENGTH_SHORT).show();
                return@setOnClickListener
            }
            if (imageUri == null) {
                Toast.makeText(
                    this,
                    "Silahkan tambahkan gambar untuk memudahkan orang mengenalimu",
                    Toast.LENGTH_SHORT
                ).show();
                return@setOnClickListener
            }

            if (!checkVal) {
                Snackbar.make(
                    btnCampus,
                    "Kamu harus menyetujui syarat pengguna untuk menggunakan aplikasi",
                    Snackbar.LENGTH_SHORT
                ).show();
            } else {


                val builder = AlertDialog.Builder(this);
                builder.setTitle("Konfirmasi profil")
                    .setMessage(
                        "Nama ${nameVal}\nNick: ${nickVal}\nNIM: ${nimVal}\nJenis Kelamin: $radioVal"
                    )
                    .setPositiveButton("OK", DialogInterface.OnClickListener { dialogInterface, i ->
                        run {
                            val intent = Intent(this, ProfileActivity::class.java).apply {
                                putExtra("name", nameVal);
                                putExtra("nick", nickVal);
                                putExtra("nim", nimVal);
                                putExtra("radio", radioVal);
                                putExtra("seek", seekVal);
                                putExtra("img", imageUri);
                            }

                            val base64img = imageUri?.let { it1 -> Helpers.EncodeImage(this, it1) }.orEmpty()
                            val newUser = User(nameVal, nickVal, nimVal, isMaleVal, base64img, seekVal)
                            AppDatabase.getInstance(this).userDao().insertAll(newUser)
                            startActivity(intent);
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_ID) {
            imageUri = data?.data
            inputImage.setImageURI(imageUri)
        }
    }
}