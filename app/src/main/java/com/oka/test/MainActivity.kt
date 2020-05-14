package com.oka.test

import android.os.Bundle
import com.oka.mvp.BaseMvpActivity

class MainActivity : BaseMvpActivity<MainPresenter>()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun createPresenter(): MainPresenter {
        return MainPresenter()
    }



}
