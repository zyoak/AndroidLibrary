package com.oka.http

import com.google.gson.annotations.SerializedName
/**
 *
 * Created by zengyong on 2020/4/30
 */
data class ErrorModel(

    @SerializedName("code")
    var code: Int = 0,
    @SerializedName("message")
    var message: String?

)