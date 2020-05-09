package com.oka.widget.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.oka.widget.R

/**
 * Created by zengyong on 2020/5/9
 */
class CommonDialog(context: Context) : Dialog(context , R.style.transparent_dialog){



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_common_dialog)


    }




}