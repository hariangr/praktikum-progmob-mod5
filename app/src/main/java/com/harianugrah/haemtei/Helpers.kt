package com.harianugrah.haemtei

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import android.webkit.MimeTypeMap
import java.io.ByteArrayOutputStream
import java.io.File


class Helpers {
    companion object {
        public fun EncodeImage(context: Context, uri: Uri): String? {
            val imageStream = context.getContentResolver().openInputStream(uri);
            val bm = BitmapFactory.decodeStream(imageStream);

            val baos = ByteArrayOutputStream()
            bm.compress(Bitmap.CompressFormat.JPEG, 50, baos)
            val b: ByteArray = baos.toByteArray()

            var base64 = Base64.encodeToString(b, Base64.DEFAULT);
//            base64 = "data:image/" + getMimeType(context, uri) + ";base64," + base64;
            return base64
        }

        private fun getMimeType(context: Context, uri: Uri): String? {
            val extension: String? = if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
                //If scheme is a content
                val mime = MimeTypeMap.getSingleton()
                mime.getExtensionFromMimeType(context.getContentResolver().getType(uri))
            } else {
                //If scheme is a File
                //This will replace white spaces with %20 and also other special characters. This will avoid returning null values on file name with spaces and special characters.
                MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(File(uri.getPath())).toString())
            }
            return extension
        }


    }
}