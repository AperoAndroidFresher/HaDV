package com.example.dovietha_bt

import android.content.Context
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

const val INTERNAL_STORAGE_DIR = "song"
fun saveFileToInternalStorage(context: Context, fileName:String,content: ByteArray){
    
    val directory = File(context.filesDir, INTERNAL_STORAGE_DIR)
    if(!directory.exists()){
        directory.mkdirs()
    }
    val file =File(directory,fileName)
    val fileOutputStream= FileOutputStream(file)
    fileOutputStream.write(content)
    fileOutputStream.close()
}

fun downloadMusicWithEnqueue(
    context: Context,
    url: String,
    fileName: String,
    onComplete: (Boolean, String) -> Unit
) {
    val client = OkHttpClient()
    val request = Request.Builder().url(url).build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            e.printStackTrace()
            onComplete(false, "Download failed: ${e.message}")
        }

        override fun onResponse(call: Call, response: Response) {
            if (!response.isSuccessful) {
                onComplete(false, "Download failed: ${response.message}")
                return
            }

            val bytes = response.body?.bytes()
            if (bytes == null) {
                onComplete(false, "Empty content")
                return
            }

            try {
                val file = File(context.filesDir, fileName)
                val fos = FileOutputStream(file)
                fos.write(bytes)
                fos.close()

                onComplete(true, file.absolutePath)
            } catch (e: Exception) {
                e.printStackTrace()
                onComplete(false, "Saving failed: ${e.message}")
            }
        }
    })
}
