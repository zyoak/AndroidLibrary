package com.oka.widget.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.oka.widget.R
import kotlinx.android.synthetic.main.layout_loading_dialog.*
/**
 * Created by zengyong on 2020/5/11
 */
class LoadingDialog(context: Context) : Dialog(context){

    var loadingAnimate : Animation? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.let {
            val dialogParams : WindowManager.LayoutParams = it.attributes
            dialogParams.width = WindowManager.LayoutParams.WRAP_CONTENT
            dialogParams.height = WindowManager.LayoutParams.WRAP_CONTENT
            dialogParams.gravity = Gravity.CENTER
            it.attributes = dialogParams
            it.setDimAmount(0F)
            it.setBackgroundDrawableResource(android.R.color.transparent)
        }
        setContentView(R.layout.layout_loading_dialog)
        loadingAnimate = AnimationUtils.loadAnimation(context , R.anim.anim_loading)

    }


    override fun onStart() {
        super.onStart()
        ivLoading.startAnimation(loadingAnimate)
    }

    override fun onStop() {
        super.onStop()
        loadingAnimate?.cancel()
    }

}