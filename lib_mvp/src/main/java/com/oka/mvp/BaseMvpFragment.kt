package com.oka.mvp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.oka.mvp.interf.IMvpView
/**
 * Created by zengyong on 2020/5/14
 */
abstract class BaseMvpFragment<V : IMvpView , out P : BaseMvpPresenter<V>>() : Fragment() , IMvpView{


    protected val mPresenter : P by lazy { createPresenter() }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPresenter?.let {
            it.attachView(this as V)
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
        Toast.makeText(context , msg , duration).show()
    }

    override fun showLoadingDialog() {

    }

    override fun hideLoadingDialog() {

    }


}