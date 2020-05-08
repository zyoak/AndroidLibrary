package com.fengfd.base.appbar

import android.view.View
/**
 * Created by zengyong on 2020/5/6
 */
interface IAppbar {

    var titleTxt : String?
    var titleTxtSize : Float
    var titleTextColorResId : Int
    var isShowTitleCenter : Boolean

    var rightTxt : String?
    var rightTxtColorResId : Int
    var rightImgResId : Int?

    var leftImgRes : Int?
    var leftTxt : String?
    var leftTxtColorResId : Int

    var dividerColorResId : Int
    var isShowDivider : Boolean
    var isShowBackIcon : Boolean

    var toolBarBgColorResId : Int
    var leftClickListener : View.OnClickListener?
    var rightClickListener : View.OnClickListener?


    fun attachToActivity()

    fun setLeftTxt(leftTxt : String?, txtColorResId : Int = leftTxtColorResId, clickListener : View.OnClickListener? = leftClickListener)

    fun setTitle(title : String? , titleColor : Int = titleTextColorResId ,showTitleCenter : Boolean = isShowTitleCenter, titleSize : Float = titleTxtSize)

    fun setRightTxt(rightTxt : String?, txtColorResId : Int = rightTxtColorResId, clickListener : View.OnClickListener? = rightClickListener)

    fun setRightImg(rightImgResId : Int ,  clickListener : View.OnClickListener? = rightClickListener)

    fun setDivider(isShowDivider : Boolean , dividerColor : Int = dividerColorResId)

    fun setNavigationIcon(showBackIcon : Boolean , navigationIcon : Int? = leftImgRes , clickListener : View.OnClickListener? = leftClickListener)

    fun setToolbarBackground(barBgColor : Int = toolBarBgColorResId)


}