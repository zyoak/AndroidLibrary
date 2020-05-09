package com.oka.mvp

import com.oka.mvp.interf.IMvpPresenter
import com.oka.mvp.interf.IMvpView
import java.lang.ref.WeakReference
/**
 * Created by zengyong on 2020/5/8
 */
open class BaseMvpPresenter<V : IMvpView> : IMvpPresenter {


    private var mViewRef : WeakReference<IMvpView>? = null


    override fun attachView(view: IMvpView) {
        mViewRef = WeakReference(view)
    }

    override fun detachView() {
        mViewRef?.clear()
    }


    override fun onCreate() {
        
    }

    override fun onResume() {

    }

    override fun onPause() {

    }

    override fun onDestroy() {

    }


}