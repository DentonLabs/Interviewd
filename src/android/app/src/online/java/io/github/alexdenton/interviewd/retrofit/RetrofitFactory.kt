package io.github.alexdenton.interviewd.retrofit

import android.content.Context
import io.github.alexdenton.interviewd.api.InterviewdApi
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by ryan on 8/17/17.
 */
class RetrofitFactory(val context: Context) {

    private val normal: String = "Not yet implemented" // TODO: Replace this with the server's domain name once it goes public
    private val local = "http://192.168.86.26:9005"

    fun create(mode: Mode): InterviewdApiRetrofit = when (mode) {
        Mode.Normal -> createRetrofitClient(normal)
        Mode.Local -> createRetrofitClient(local)
    }

    private fun createRetrofitClient(url: String) = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(InterviewdApiRetrofit::class.java)

    enum class Mode {
        Normal, Local
    }

}