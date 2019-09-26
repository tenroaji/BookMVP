package com.lagecong.books.utils

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by Andi Tenroaji Ahmad on 9/26/2019.
 */

object RetrofitUtils {

    @JvmStatic
    fun <T> createService(baseUrl : String, service : Class<T>) : T{
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(service)
    }

    @JvmStatic
    fun <T> createService(baseUrl : String,service : Class<T>,timeOut : Long) : T {
        if(timeOut > 10000L){
            val httpClient = OkHttpClient.Builder()
            httpClient.callTimeout(timeOut, TimeUnit.MILLISECONDS)
            httpClient.readTimeout(timeOut, TimeUnit.MILLISECONDS)
            httpClient.writeTimeout(timeOut, TimeUnit.MILLISECONDS)
            httpClient.connectTimeout(timeOut, TimeUnit.MILLISECONDS)
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(service)
        }
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(service)
    }

    @JvmStatic
    fun <T> createService(baseUrl: String, service : Class<T>, timeOut: Long, level : HttpLoggingInterceptor.Level) : T {
        val httpClient = OkHttpClient.Builder()
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = level
        httpClient.addInterceptor(interceptor)
        if(timeOut > 10000L) {
            httpClient.callTimeout(timeOut, TimeUnit.MILLISECONDS)
            httpClient.readTimeout(timeOut, TimeUnit.MILLISECONDS)
            httpClient.writeTimeout(timeOut, TimeUnit.MILLISECONDS)
            httpClient.connectTimeout(timeOut, TimeUnit.MILLISECONDS)
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(service)
        }

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(httpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(service)
    }
}