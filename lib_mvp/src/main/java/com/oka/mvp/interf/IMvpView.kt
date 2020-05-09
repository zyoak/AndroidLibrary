package com.oka.mvp.interf

import android.widget.Toast
/**
 * Created by zengyong on 2020/5/8
 */
interface IMvpView{


    fun showToast(msg : String , duration : Int = Toast.LENGTH_SHORT)



}