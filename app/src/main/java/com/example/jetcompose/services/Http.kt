package com.example.jetcompose.services

import android.os.Handler
import android.os.Looper
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.IOException

object Http {
    private val client = OkHttpClient()

    private val baseUrl = "http://172.31.150.23:3000"
    private val mainHandler = Handler(Looper.getMainLooper())
    private val gson = GsonBuilder()
        .setLenient()          // 宽松模式，兼容非标准 JSON
        .create()
    private val JSON_MEDIA_TYPE = "application/json; charset=utf-8".toMediaType()
    fun get(
        url: String,
        callback: (Boolean, String) -> Unit
    ) {
        val newUrl = baseUrl + url
        val res = Request.Builder().url(newUrl).get().build()

        client.newCall(res).enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                mainHandler.post {
                    callback(false, e.message ?: "请求失败")
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                mainHandler.post {
                    if (response.isSuccessful && body != null) {
                        callback(true, body)
                    } else {
                        callback(false, "请求失败${response.code}")
                    }
                }
            }
        })
    }

    fun post(
        url: String,
        payLoad: Any,
        callback: (Boolean, String) -> Unit
    ) {
        val newUrl = baseUrl + url
        val json = gson.toJson(payLoad)
        val body = json.toRequestBody(JSON_MEDIA_TYPE)

        val request = Request.Builder()
            .url(newUrl)
            .post(body)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback(false, e.message ?: "请求失败")
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                if (response.isSuccessful && body != null) {
                    callback(true, body)
                } else {
                    callback(false, "请求失败 ${response.code}")
                }
            }
        })
    }
    // 快速解析成对象
    inline fun <reified T> parseJson(json: String): T? {
        return try {
            // 内联函数内创建 Gson，无需访问外部私有成员
            val gson = GsonBuilder()
                .setLenient()
                .create()
            println("待解析JSON：$json")
            val type = object : TypeToken<T>() {}.type
            val result = gson.fromJson<T>(json, type)
            println("解析结果：$result")
            result
        } catch (e: Exception) {
            e.printStackTrace()
            println("解析失败：${e.message}")
            null
        }
    }
}