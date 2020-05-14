package com.oka.mvp.interf

/**
 * Created by zengyong on 2020/5/8
 */
interface IMvpPresenter<V : IMvpView>{

    fun getView() : V?

    fun attachView(view : V)

    fun detachView()

    fun onCreate()

    fun onResume()

    fun onPause()

    fun onDestroy()

}