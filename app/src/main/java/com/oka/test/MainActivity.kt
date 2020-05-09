package com.oka.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.oka.widget.dialog.CommonDialog
import com.oka.widget.extensions.dp
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn.setOnClickListener {
            CommonDialog.Builder(this).apply {
                titleTxt = "温馨提示"
                contentTxt = "根据新规要求，您的投资经历低于2年，不符合合格投资者的条件。"
                negativeTxt = "退出评测"
                positiveTxt = "重新选择"
                borderWidth = 53.dp
                positiveTxtColorResId = R.color.color_ff6200
            }.builder().show()
        }
    }


}
