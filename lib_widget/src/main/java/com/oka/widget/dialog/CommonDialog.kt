package com.oka.widget.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import androidx.core.view.setPadding
import com.oka.widget.R
import com.oka.widget.extensions.dp
import kotlinx.android.synthetic.main.layout_common_dialog.*

/**
 * 通用弹窗dialog
 * Created by zengyong on 2020/5/9
 */
class CommonDialog(context: Context ,private val builder: Builder) : Dialog(context , R.style.transparent_dialog){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.let {
            val displayMetrics : DisplayMetrics = context.resources.displayMetrics
            it.decorView.setPadding(0,0,0,0)
            val dialogParams : WindowManager.LayoutParams = it.attributes
            dialogParams.width = displayMetrics.widthPixels - builder.borderWidth*2
            dialogParams.height = WindowManager.LayoutParams.WRAP_CONTENT
            dialogParams.gravity = Gravity.CENTER
            it.attributes = dialogParams
        }
        setCancelable(builder.cancelable)
        setCanceledOnTouchOutside(builder.canceledOnTouchOutside)
        setContentView(R.layout.layout_common_dialog)
        llContainer.setBackgroundResource(R.drawable.shape_rect_fff_3)

        builder.titleTxt?.let {
            tvTitle.apply {
                text = it
                visibility = View.VISIBLE
                setTextColor(context.resources.getColor(builder.titleTxtColorResId))
            }
        }

        builder.contentTxt?.let {
            tvContent.apply {
                text = it
                visibility = View.VISIBLE
                setTextColor(context.resources.getColor(builder.contentTxtColorResId))
            }
        }

        builder.negativeTxt?.let {
            tvNegative.apply {
                text = it
                visibility = View.VISIBLE
                setTextColor(context.resources.getColor(builder.negativeTxtColorResId))
                setOnClickListener{
                    builder.negativeClickListener?.onClick(this@CommonDialog , DialogInterface.BUTTON_NEGATIVE)
                }
            }
        }

        builder.positiveTxt?.let {
            tvPositive.apply {
                text = it
                visibility = View.VISIBLE
                setTextColor(context.resources.getColor(builder.positiveTxtColorResId))
                setOnClickListener{
                    builder.negativeClickListener?.onClick(this@CommonDialog , DialogInterface.BUTTON_POSITIVE)
                }
            }
        }

        builder.customContentView?.let {
            flContentView.apply {
                addView(it)
                visibility = View.VISIBLE
            }
        }

    }


    class Builder(private val context: Context){

        var borderWidth : Int = 30.dp
        var titleTxt : String? = null
        var titleTxtColorResId : Int = R.color.color_333333
        var contentTxt : String? = null
        var contentTxtColorResId : Int = R.color.color_999999
        var negativeTxt : String? = null
        var negativeTxtColorResId : Int = R.color.color_333333
        var negativeClickListener : DialogInterface.OnClickListener? = null
        var positiveTxt : String? = null
        var positiveTxtColorResId : Int = R.color.color_333333
        var positiveClickListener : DialogInterface.OnClickListener? = null
        var customContentView : View? = null
        var canceledOnTouchOutside : Boolean = true
        var cancelable : Boolean = true

        fun builder() : CommonDialog{
            return CommonDialog(context , this)
        }

    }


}
