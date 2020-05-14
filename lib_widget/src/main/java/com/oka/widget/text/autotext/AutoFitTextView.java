package com.oka.widget.text.autotext;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import androidx.appcompat.widget.AppCompatTextView;
import com.oka.widget.R;
/**
 * https://www.jianshu.com/p/d51df08a061c
 */
public class AutoFitTextView extends AppCompatTextView {

    private float mDefaultTextSize;
    private Paint mTextPaint;
    private float minTextSize;
    private float autoSizeStepGranularity;
    private boolean isAutoSizeOpen = true;
    private OnAutoTextSizeChangeListener listener;
    private int maxLines;

    public AutoFitTextView(Context context) {
        this(context ,null);
    }

    private void initAttr() {
        mTextPaint = new Paint();
        mTextPaint.set(getPaint());
        mDefaultTextSize = getTextSize();
        maxLines = getMaxLines();
    }

    public AutoFitTextView(Context context,  AttributeSet attrs) {
        this(context, attrs , 0);
    }

    public AutoFitTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr();

        TypedArray ta = context.obtainStyledAttributes(attrs , R.styleable.AutoFitTextView);

        minTextSize = ta.getDimension(R.styleable.AutoFitTextView_autoSizeMinTextSize , 14);
        autoSizeStepGranularity = ta.getDimension(R.styleable.AutoFitTextView_autoSizeStepGranularity , 2);

        ta.recycle();

    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        if(isAutoSizeOpen){
            refitText(text.toString(),getWidth());
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if(isAutoSizeOpen){
            refitText(getText().toString(),getWidth());
        }
    }

    public void setAutoSizeOpen(boolean isAutoSizeOpen){
        this.isAutoSizeOpen = isAutoSizeOpen;
    }

    public void setTextSizeWithOutAutoSize(float textSize){
        setAutoSizeOpen(false);
        setTextSize(TypedValue.COMPLEX_UNIT_PX , textSize);
    }

    public void refitText(String text, int textWidth){
//        Log.e("refit", "refit:"+text+"width:"+textWidth);
        if(textWidth > 0){
            int availableTextWidth = textWidth - getPaddingLeft() - getPaddingRight();
            float tsTextSize = mDefaultTextSize;
            mTextPaint.setTextSize(tsTextSize);
            String subStr = text.substring(0 , text.length()/maxLines);
            float length = mTextPaint.measureText(subStr);
            while (length > availableTextWidth) {
                tsTextSize = tsTextSize - autoSizeStepGranularity;
                mTextPaint.setTextSize(tsTextSize);
                length = mTextPaint.measureText(subStr);
                if(tsTextSize < minTextSize){
                    tsTextSize = minTextSize;
                    break;
                }
            }
            Log.e("refit", "refit:"+text+" , textSize:"+ tsTextSize);
            setTextSize(TypedValue.COMPLEX_UNIT_PX,tsTextSize);
            invalidate();
            if(listener != null){
                listener.onTextSizeChanged(tsTextSize);
            }
        }
    }


    public void setOnAutoTextSizeChangeListener(OnAutoTextSizeChangeListener listener){
        this.listener = listener;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }


}