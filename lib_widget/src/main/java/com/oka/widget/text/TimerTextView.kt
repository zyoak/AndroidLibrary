package com.oka.widget.text

import android.content.Context
import android.content.res.TypedArray
import android.os.CountDownTimer
import android.text.TextUtils
import android.text.format.DateUtils
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.widget.AppCompatTextView
import com.oka.widget.R
/**
 * 倒计时控件
 */
class TimerTextView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : AppCompatTextView(context, attrs, defStyleAttr) {

    private var timer : CountDownTimer? = null
    private var timerListener : TimerListener? = null
    private var countDownTxt : String?
    private var defaultTxt : String?
    private var timeSecond : Int

    init {
        val a : TypedArray = context.obtainStyledAttributes(attrs , R.styleable.TimerTextView)
        timeSecond = a.getInt(R.styleable.TimerTextView_timeSecond, 60)
        defaultTxt = a.getString(R.styleable.TimerTextView_defaultText)
        countDownTxt = a.getString(R.styleable.TimerTextView_countDownText)
        a.recycle()
        defaultTxt = defaultTxt ?: resources.getString(R.string.resend)
        countDownTxt = countDownTxt ?: resources.getString(R.string.countTimeTxt)

        text = defaultTxt
    }

    private fun initTimer(time: Int) {
        timer = object : CountDownTimer(time * DateUtils.SECOND_IN_MILLIS, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                text = (millisUntilFinished / 1000).toString() + countDownTxt
            }

            override fun onFinish() {
                isEnabled = true
                text = defaultTxt
                timerListener?.onFinish()
            }
        }
    }


    fun startTimer(timerListener: TimerListener? = null) {
        if(isEnabled){
            timer ?: initTimer(timeSecond)
            isEnabled = false
            this.timerListener = timerListener
            timer?.start()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        cancel()
    }


    private fun cancel() {
        timer?.cancel()
    }


    interface TimerListener {
        fun onFinish()
    }

}
