package com.oka.mvp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fengfd.base.appbar.IAppbar
import com.oka.mvp.interf.IMvpView
import com.oka.widget.dialog.LoadingDialog
/**
 *
 * Created by zengyong on 2020/5/8
 */
abstract class BaseMvpActivity<V : IMvpView ,out P : BaseMvpPresenter<V>> : AppCompatActivity() , IMvpView {


    protected val mPresenter : P by lazy { createPresenter() }
    protected val appbar : IAppbar? by lazy { createAppBar() }
    protected val loadingDialog : LoadingDialog by lazy { createLoadingDialog() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        //添加appbar
        appbar?.attachToActivity()

        mPresenter?.let {
            it.attachView(this as V)
            it.onCreate()
        }
    }


    abstract fun createPresenter() : P


    abstract fun createAppBar() :  IAppbar?


    protected fun createLoadingDialog() : LoadingDialog{
        return LoadingDialog(this)
    }

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


    override fun showLoadingDialog() {
        loadingDialog.show()
    }


    override fun hideLoadingDialog() {
        loadingDialog.dismiss()
    }


}