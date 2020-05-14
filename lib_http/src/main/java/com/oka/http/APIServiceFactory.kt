package com.oka.http

/**
 * Created by zengyong on 2020/4/30
 */
object APIServiceFactory {

    public fun <T> createAPIService(baseUrl : String , clazz : Class<T>) : T{
        return RetrofitMgr.getRetrofit(baseUrl).create(clazz)
    }

}