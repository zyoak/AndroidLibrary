package com.oka.mvp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.oka.mvp.interf.IMvpView

/**
 * Created by zengyong on 2020/5/8
 */
abstract class BaseMvpActivity<P : BaseMvpPresenter<*>> : AppCompatActivity() , IMvpView {


    protected val mPresenter : P by lazy { createPresenter() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        mPresenter?.let {
            it.attachView(this)
            it.onCreate()
        }
    }

    abstract fun createPresenter() : P


    override fun onResume() {
        super.onResume()
        mPresenter?.onResume()
    }


    override fun onPause() {
        super.onPause()
        mPresenter?.onPause()
    }


    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.onDestroy()
        mPresenter?.detachView()
    }


    override fun showToast(msg: String, duration: Int) {
        Toast.makeText(this , msg , duration).show()
    }


}