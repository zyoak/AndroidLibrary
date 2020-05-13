package com.oka.widget.ptr

import `in`.srain.cube.views.ptr.PtrDefaultHandler
import `in`.srain.cube.views.ptr.PtrFrameLayout
import android.content.Context
import android.util.AttributeSet
import android.view.View
/**
 * Created by zengyong on 2020/5/13
 */
class PtrView @JvmOverloads constructor(context: Context , attrs: AttributeSet? = null, defStyleAttr : Int = 0)
    : PtrFrameLayout(context , attrs , defStyleAttr){

    var onRefreshListener : OnRefreshListener? = null
    var canDoRefreshListener : CanDoRefreshListener? = null

    init {
        val ptrHeader : PtrHeader = PtrHeader(context)
        headerView = ptrHeader
        addPtrUIHandler(ptrHeader)

        setPtrHandler(object : PtrDefaultHandler(){
            override fun onRefreshBegin(frame: PtrFrameLayout) {
                onRefreshListener?.onRefresh(this@PtrView)
            }

            override fun checkCanDoRefresh(frame: PtrFrameLayout, content: View, header: View): Boolean {
                return canDoRefreshListener?.checkCanDoRefresh(this@PtrView , content , header) ?: super.checkCanDoRefresh(frame , content , header)
            }

        })
    }

    interface OnRefreshListener{
        fun onRefresh(ptrView : PtrView)
    }

    interface CanDoRefreshListener{
        fun checkCanDoRefresh(frame: PtrView, content: View, header: View) : Boolean
    }

}




