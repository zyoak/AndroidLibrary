package com.oka.mvp.interf

/**
 * Created by zengyong on 2020/5/8
 */
interface IMvpPresenter{


    fun attachView(view : IMvpView)

    fun detachView()

    fun onCreate()

    fun onResume()

    fun onPause()

    fun onDestroy()

}