package com.arise.network.screens.profile

import android.content.Context
import android.net.Uri
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.InputStream

fun uploadImageToImgur(
    context: Context,
    imageUri: Uri,
    clientId: String
): String {
    val inputStream: InputStream? = context.contentResolver.openInputStream(imageUri)
    val bytes = inputStream?.readBytes() ?: throw Exception("Unable to read image")
    val base64Image = android.util.Base64.encodeToString(bytes, android.util.Base64.DEFAULT)

    val client = OkHttpClient()
    val requestBody = FormBody.Builder()
        .add("image", base64Image)
        .build()

    val request = Request.Builder()
        .url("https://api.imgur.com/3/image")
        .addHeader("Authorization", "823497d151fa415")
        .post(requestBody)
        .build()

    val response = client.newCall(request).execute()
    if (!response.isSuccessful) throw Exception("Imgur upload failed: ${response.message}")

    val jsonResponse = JSONObject(response.body?.string() ?: "")
    return jsonResponse.getJSONObject("data").getString("link")
}
