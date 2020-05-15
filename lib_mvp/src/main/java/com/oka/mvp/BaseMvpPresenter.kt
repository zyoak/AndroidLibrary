package com.oka.mvp

import com.oka.mvp.interf.IMvpPresenter
import com.oka.mvp.interf.IMvpView
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.lang.ref.WeakReference
/**
 * Created by zengyong on 2020/5/8
 */
open class BaseMvpPresenter<V : IMvpView> : IMvpPresenter<V> {


    private var mViewRef : WeakReference<V>? = null
    private val mSubscriptions : CompositeDisposable? = null

    override fun attachView(view: V) {
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

    override fun getView(): V? {
        return mViewRef?.get()
    }


}