package ru.teacherarmy.data.network

import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response

abstract class ApiRequest {
    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): T? {
        val response = call.invoke()
        if (response.isSuccessful) {
            return response.body()
        } else {
            val responseErr = response.errorBody()?.string()
            val message = StringBuilder()
            responseErr?.let {
                try {
                    message.append(JSONObject(it).getString("error"))
                } catch (e: JSONException) {
                }
            }
            throw Exception(message.toString())
        }
    }
}
