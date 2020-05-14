package com.oka.widget.ptr

import `in`.srain.cube.views.ptr.PtrFrameLayout
import `in`.srain.cube.views.ptr.PtrUIHandler
import `in`.srain.cube.views.ptr.indicator.PtrIndicator
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import com.oka.widget.R
import kotlinx.android.synthetic.main.layout_ptr_header.view.*
/**
 * Created by zengyong on 2020/5/13
 */
class PtrHeader @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr : Int = 0)
    : FrameLayout(context , attrs , defStyleAttr) , PtrUIHandler{

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_ptr_header , this)
    }

    private val tag : String = "PtrHeader"
    private val loadingAnimator : Animation = AnimationUtils.loadAnimation(context , R.anim.anim_loading)

    override fun onUIRefreshComplete(frame: PtrFrameLayout) {
        Log.e(tag,"onUIRefreshComplete.......")

    }

    override fun onUIPositionChange(frame: PtrFrameLayout, isUnderTouch: Boolean, status: Byte, ptrIndicator: PtrIndicator) {
        Log.e(tag,"onUIPositionChange.......${status}  , ${ptrIndicator.currentPercent} ")
        if(status.toInt() == 2){
            ivLoading.rotation = Math.min(1f , ptrIndicator.currentPercent) * 360
        }
    }

    override fun onUIRefreshBegin(frame: PtrFrameLayout) {
        Log.e(tag,"onUIRefreshBegin.......")
        ivLoading.startAnimation(loadingAnimator)
    }

    override fun onUIRefreshPrepare(frame: PtrFrameLayout) {
        Log.e(tag,"onUIRefreshPrepare.......")
    }

    override fun onUIReset(frame: PtrFrameLayout) {
        Log.e(tag,"onUIReset.......")
        reset()
    }

    private fun reset(){
        ivLoading.clearAnimation()
        ivLoading.rotation = 0f
    }

}