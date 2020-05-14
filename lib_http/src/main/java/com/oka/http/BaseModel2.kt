package com.oka.http

import com.google.gson.annotations.SerializedName

/**
 * Created by zengyong on 2020/4/30
 */
data class BaseModel2<T>(

    @SerializedName("success")
    val success : String,
    @SerializedName("data")
    val data : T?,
    @SerializedName("error")
    val error : ErrorModel?

)