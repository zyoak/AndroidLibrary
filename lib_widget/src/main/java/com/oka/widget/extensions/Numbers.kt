package com.oka.widget.extensions

import android.content.res.Resources
/**
 * 对数字做了尺寸转换扩展
 * Created by zengyong on 2019/4/2
 */
val Float.dp: Float                 // [xxhdpi](360 -> 1080)
    get() = android.util.TypedValue.applyDimension(
            android.util.TypedValue.COMPLEX_UNIT_DIP, this, Resources.getSystem().displayMetrics)


val Int.dp: Int
    get() = android.util.TypedValue.applyDimension(
            android.util.TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), Resources.getSystem().displayMetrics).toInt()


val Float.sp: Float                 // [xxhdpi](360 -> 1080)
    get() = android.util.TypedValue.applyDimension(
            android.util.TypedValue.COMPLEX_UNIT_SP, this, Resources.getSystem().displayMetrics)


val Int.sp: Int
    get() = android.util.TypedValue.applyDimension(
            android.util.TypedValue.COMPLEX_UNIT_SP, this.toFloat(), Resources.getSystem().displayMetrics).toInt()
