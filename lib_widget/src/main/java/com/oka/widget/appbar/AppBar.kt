package com.oka.widget.appbar

import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.fengfd.base.appbar.IAppbar
import com.google.android.material.appbar.AppBarLayout
import com.oka.widget.R
import com.oka.widget.extensions.dp
/**
 * Created by zengyong on 2020/5/6
 */
class AppBar constructor(private val activity : AppCompatActivity,
                         override var titleTxt: String? = null,
                         override var titleTxtSize: Float = 18F,
                         override var titleTextColorResId: Int = R.color.color_333333,
                         override var isShowTitleCenter: Boolean = true,
                         override var rightTxt: String? = null,
                         override var rightTxtColorResId: Int = R.color.color_333333,
                         override var rightImgResId: Int? = null,
                         override var isShowBackIcon: Boolean = true,
                         override var leftImgRes: Int? = R.drawable.selector_toolbar_back,
                         override var leftTxt: String? = null,
                         override var leftTxtColorResId: Int = R.color.color_333333,
                         override var dividerColorResId: Int = R.color.color_dddddd,
                         override var isShowDivider: Boolean = false,
                         override var leftClickListener: View.OnClickListener? = null,
                         override var rightClickListener: View.OnClickListener? = null,
                         override var toolBarBgColorResId: Int = android.R.color.white
) : IAppbar {


    private lateinit var appbarLayout : AppBarLayout
    private var flBarContainer : FrameLayout? = null
    private var toolBar : Toolbar? = null
    private var divider : View? = null
    private var leftTxtView : TextView? = null
    private var titleTxtView : TextView? = null
    private var rightTxtView : TextView? = null
    private var rightImgView : ImageView? = null


    override fun attachToActivity() {
        appbarLayout = LayoutInflater.from(activity).inflate(R.layout.layout_toolbar , null) as AppBarLayout
        flBarContainer = appbarLayout.findViewById(R.id.flBarContainer)
        toolBar = appbarLayout.findViewById(R.id.toolBar)
        divider = appbarLayout.findViewById(R.id.divider)
        val decorView : ViewGroup = activity.window.decorView as ViewGroup
        val contentView : ViewGroup = decorView.findViewById<ViewGroup>(android.R.id.content).getChildAt(0) as ViewGroup
        val contentParentView : ViewGroup = contentView.parent as ViewGroup
        val newContentView : LinearLayout = LinearLayout(activity)
        newContentView.orientation = LinearLayout.VERTICAL
        newContentView.addView(appbarLayout , LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
        contentParentView.removeView(contentView)
        newContentView.addView(contentView , LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
        contentParentView.addView(newContentView)
        activity.setSupportActionBar(toolBar)

        initToolbarLayout()
    }


    private fun initToolbarLayout(){
        setDivider(isShowDivider)
        setNavigationIcon(isShowBackIcon)
        setToolbarBackground(toolBarBgColorResId)
        setLeftTxt(leftTxt , leftTxtColorResId , leftClickListener)
        setTitle(titleTxt)
        rightTxt?.let { setRightTxt(rightTxt)}
        rightImgResId?.let { setRightImg(rightImgResId!!) }
    }


    override fun setTitle(titleTxt : String? , titleColor : Int , showTitleCenter : Boolean , titleSize : Float){
        if(titleTxtView == null){
            titleTxtView = TextView(activity)
            val lp = Toolbar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            if(isShowTitleCenter){
                lp.gravity = Gravity.CENTER
            }else{
                lp.gravity = Gravity.LEFT or Gravity.CENTER_VERTICAL
            }
            toolBar?.addView(titleTxtView ,lp)
        }
        if(titleTxtView!!.parent == null){
            toolBar?.addView(titleTxtView)
        }
        titleTxtView?.let {
            it.text = titleTxt
            it.setTextSize(TypedValue.COMPLEX_UNIT_DIP , titleSize)
            it.setTextColor(activity.resources.getColor(titleColor))
        }
    }


    override fun setToolbarBackground(barBgColorId: Int){
        flBarContainer?.let { it.setBackgroundResource(barBgColorId) }
        //添加沉浸式
        if(activity.resources.getColor(barBgColorId) != activity.resources.getColor(android.R.color.white)){
            StatusBarUtil.clearMode(activity)
            StatusBarUtil.setColor(activity , activity.resources.getColor(barBgColorId) , 0)
        }
    }


    override fun setRightTxt(rightTxt : String?, txtColorResId : Int , clickListener : View.OnClickListener?){
        if(rightTxtView == null){
            rightTxtView = TextView(activity)
            val lp = Toolbar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            lp.gravity = Gravity.CENTER_VERTICAL or Gravity.RIGHT
            lp.rightMargin = 12.dp
            toolBar?.addView(rightTxtView , lp)
        }
        if(rightTxtView!!.parent == null){
            toolBar?.addView(rightTxtView)
        }
        rightTxtView!!.let {
            it.text = rightTxt
            it.setTextSize(TypedValue.COMPLEX_UNIT_DIP , 16F)
            it.setTextColor(activity.resources.getColor(txtColorResId))
            clickListener?.let { rightTxtView?.setOnClickListener(it) }
        }
    }


    override fun setRightImg(rightImgResId : Int, clickListener : View.OnClickListener?){
        if(rightImgView == null){
            rightImgView = ImageView(activity)
            val lp = Toolbar.LayoutParams(24.dp , 24.dp)
            lp.gravity = Gravity.CENTER_VERTICAL or Gravity.RIGHT
            lp.rightMargin = 12.dp
            toolBar?.addView(rightImgView , lp)
        }
        if(rightImgView!!.parent == null){
            toolBar?.addView(rightImgView)
        }
        rightImgView!!.let {
            it.scaleType = ImageView.ScaleType.CENTER_CROP
            it.setImageResource(rightImgResId)
            clickListener?.let { rightImgView?.setOnClickListener(it) }
        }
    }


    override fun setDivider(isShowDivider : Boolean, dividerColorResId : Int){
        divider?.apply {
            visibility = if(isShowDivider) View.VISIBLE else View.GONE
            setBackgroundResource(dividerColorResId)
        }
    }


    override fun setLeftTxt(leftTxt : String?, txtColorResId : Int , clickListener : View.OnClickListener?){
        if(leftTxtView == null){
            leftTxtView = TextView(activity)
            val lp = Toolbar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            lp.gravity = Gravity.CENTER_VERTICAL or Gravity.LEFT
            lp.leftMargin = 12.dp
            toolBar?.addView(leftTxtView , lp)
        }
        if(leftTxtView!!.parent == null){
            toolBar?.addView(leftTxtView)
        }
        leftTxtView!!.let {
            it.text = leftTxt
            it.setTextSize(TypedValue.COMPLEX_UNIT_DIP , 16F)
            it.setTextColor(activity.resources.getColor(txtColorResId))
            clickListener?.let { rightTxtView?.setOnClickListener(it) }
        }
    }

    override fun setNavigationIcon(showBackIcon : Boolean, navigationIcon : Int? , clickListener : View.OnClickListener?){
        activity.supportActionBar?.let {
            it.setDisplayShowTitleEnabled(false)
        }
        if(isShowBackIcon){
            toolBar?.setNavigationOnClickListener{
                activity?.finish()
            }
            clickListener?.let {
                toolBar?.setNavigationOnClickListener(it)
            }
            navigationIcon?.let {
                toolBar?.setNavigationIcon(it)
             }
        }
    }



}