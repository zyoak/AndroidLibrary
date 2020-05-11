package com.oka.test

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.oka.widget.dialog.CommonDialog
import com.oka.widget.extensions.dp
import kotlinx.android.synthetic.main.activity_main.*
import java.util.zip.Inflater

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn.setOnClickListener {
            CommonDialog.Builder(this).apply {
//                titleTxt = "温馨提示"
//                contentTxt = "根据新规要求，您的投资经历低于2年，不符合合格投资者的条件。"
                customContentView = LayoutInflater.from(this@MainActivity).inflate(R.layout.customer_dialog , null)
                negativeTxt = "退出评测"
                positiveTxt = "重新选择"
                borderWidth = 53.dp
                positiveTxtColorResId = R.color.color_ff6200
                negativeClickListener = DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() }
                positiveClickListener = DialogInterface.OnClickListener { dialog, _ -> dialog.dismiss() }

            }.builder().show()
        }

    }


}
