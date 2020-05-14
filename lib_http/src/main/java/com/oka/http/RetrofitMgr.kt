package com.oka.http

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
/**
 * Created by zengyong on 2020/4/30
 */
internal object RetrofitMgr{


    private val READ_TIMEOUT : Long = 30000
    private val CONNECT_TIMEOUT : Long = 30000
    private val WRITE_TIMEOUT : Long = 30000

    private val retrofitContainer : MutableMap<String , Retrofit> = mutableMapOf()


    public fun getRetrofit(baseUrl : String) : Retrofit{
        var retrofit = retrofitContainer[baseUrl]
        if(retrofit == null){
            retrofit = createRetrofit(baseUrl)
            retrofitContainer[baseUrl] = retrofit
        }
        return retrofit
    }


    private fun createRetrofit(baseUrl : String) : Retrofit{
        return Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(createOkHttpClient())
                .build()
    }


    private fun createOkHttpClient() : OkHttpClient{
        return OkHttpClient.Builder().readTimeout(READ_TIMEOUT ,TimeUnit.SECONDS)
                .connectTimeout(CONNECT_TIMEOUT , TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT , TimeUnit.SECONDS)
                .addInterceptor(HttpLoggingInterceptor())
                .build()
    }

}