package com.oka.widget.text;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.widget.AppCompatEditText;
import com.oka.widget.R;
import java.util.ArrayList;
import java.util.List;
/**
 * 验证码输入框
 */
public class CaptchaInputView extends AppCompatEditText {

    private int borderColor;
    private float borderWidth;
    private float borderRadius;

    private int passwordLength = 4;
    private int passwordColor;
    private float passwordWidth;
    private float passwordRadius;

    private Paint passwordPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint blackBorderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);


    private final int defaultContMargin = 5;
    private final int defaultSplitLineWidth = 3;

    private TextChangeListener mTextChangeListener;
    private float mDefaultInputViewSize, mDefaultInputViewPadding, mDefaultInputTextSize, mDefaultInputViewHeight;
    private float mCursorWidth;
    private int mCursorHeight;
    private float mDefalutMargin = 10;
    private boolean mPwdVisiable = true;
    private String mInputText;
    private List<RectF> rectList;
    private Context mContext;
    private int mSelectIndex = 0;
    private Handler mCursorHandler;
    private CursorRunnable mCursorRunnable;
    static final int CURSOR_DELAY_TIME = 500;
    public static final int MODE_DIVIDER = 1;

    public static final int AUTO = 2;
    public static final int ACCURATE = 1;

    private int fitMode = ACCURATE;
    private int mode = 0;

    public CaptchaInputView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        final Resources res = getResources();
        final int defaultBorderColor = res.getColor(R.color.color_dddddd);
        final float defaultBorderWidth = res.getDimension(R.dimen.default_ev_border_width);
        final float defaultBorderRadius = res.getDimension(R.dimen.default_ev_border_radius);
        final int defaultPasswordLength = res.getInteger(R.integer.default_ev_password_length);
        final int defaultPasswordColor = res.getColor(R.color.color_1b2e43);
        final float defaultPasswordWidth = res.getDimension(R.dimen.default_ev_password_width);
        final float defaultPasswordRadius = res.getDimension(R.dimen.default_ev_password_radius);
        final float defaultInputViewSize = res.getDimension(R.dimen.default_input_text_view_size);
        final float defaultInputViewPadding = res.getDimension(R.dimen.default_input_text_view_padding);
        final float defaultInputTextSize = res.getDimension(R.dimen.default_input_text_size);
        final float defaultInputViewHeight = res.getDimension(R.dimen.default_input_text_view_height_size);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.PasswordInputView, 0, 0);
        try {
            borderColor = a.getColor(R.styleable.PasswordInputView_captchaBorderColor, defaultBorderColor);
            borderWidth = a.getDimension(R.styleable.PasswordInputView_captchaBorderWidth, defaultBorderWidth);
            borderRadius = a.getDimension(R.styleable.PasswordInputView_captchaBorderRadius, defaultBorderRadius);
            passwordLength = a.getInt(R.styleable.PasswordInputView_captchaLength, defaultPasswordLength);
            passwordColor = a.getColor(R.styleable.PasswordInputView_captchaColor, defaultPasswordColor);
            passwordWidth = a.getDimension(R.styleable.PasswordInputView_captchaWidth, defaultPasswordWidth);
            passwordRadius = a.getDimension(R.styleable.PasswordInputView_captchaRadius, defaultPasswordRadius);
            mDefaultInputViewSize = a.getDimension(R.styleable.PasswordInputView_captchaViewSize, defaultInputViewSize);
            mDefaultInputViewHeight = a.getDimension(R.styleable.PasswordInputView_captchaViewHeight, defaultInputViewHeight);
            mDefaultInputViewPadding = a.getDimension(R.styleable.PasswordInputView_captchaViewPadding, defaultInputViewPadding);
            mDefaultInputTextSize = a.getDimension(R.styleable.PasswordInputView_captchaTextSize, defaultInputTextSize);
        } finally {
            a.recycle();
        }

        mCursorWidth = mContext.getResources().getDimension(R.dimen.captcha_cursor_width);
        mCursorHeight = (int) mContext.getResources().getDimension(R.dimen.captcha_cursor_height);

        borderPaint.setStrokeWidth(borderWidth);
        borderPaint.setColor(borderColor);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setAntiAlias(true);

        blackBorderPaint.setStrokeWidth(borderWidth);
        blackBorderPaint.setColor(getResources().getColor(R.color.color_c7c7cc));
        blackBorderPaint.setStyle(Paint.Style.STROKE);
        blackBorderPaint.setAntiAlias(true);


        linePaint.setColor(getResources().getColor(R.color.color_ff6200));
        linePaint.setStrokeWidth(mCursorWidth);        //绘制直线


        passwordPaint.setColor(passwordColor);
        passwordPaint.setTextSize(mDefaultInputTextSize);
        rectList = new ArrayList<>();

        mCursorHandler = new Handler();
        mCursorRunnable = new CursorRunnable();
        mCursorHandler.post(mCursorRunnable);


        setCursorVisible(false);
        setTextIsSelectable(false);
        setLongClickable(false);
        //最大字数
        setFilters(new InputFilter[]{new InputFilter.LengthFilter(20){}});
        setCustomSelectionActionModeCallback(new ActionMode.Callback(){
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }
        });
        setSelectAllOnFocus(false);
    }

    @Override
    public boolean isSuggestionsEnabled() {
        return false;
    }

    public void setPwdLength(int length) {
        this.passwordLength = length;
        postInvalidate();
    }


    public int getPwdLength() {
        return this.passwordLength;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (fitMode == ACCURATE) {
            int width = passwordLength * (int) mDefaultInputViewSize + (int) mDefaultInputViewPadding * (passwordLength - 1) + (int) mDefalutMargin * 2;
            int height = (int) mDefaultInputViewHeight + (int) mDefalutMargin * 2;
            setMeasuredDimension(width, height);
        } else {
            int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
            int parentHeight = (int) mDefaultInputViewHeight + (int) mDefalutMargin * 2;
            int childWidth = (int) ((parentWidth - ((passwordLength - 1) * mDefaultInputViewPadding) - mDefalutMargin * 2) / passwordLength);
            mDefaultInputViewSize = childWidth;
            setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), parentHeight);
        }
    }

    private class CursorRunnable implements Runnable {
        private boolean mCancelled = false;
        private boolean mCursorVisible = false;

        @Override
        public void run() {
            if (mCancelled) {
                return;
            }
            postInvalidate();
            postDelayed(this, CURSOR_DELAY_TIME);
        }

        void cancel() {
            if (!mCancelled) {
                mCursorHandler.removeCallbacks(this);
                mCancelled = true;
            }
        }

        void uncancel() {
            mCancelled = false;
        }

        public boolean getCursorVisiable() {
            return mCursorVisible = !mCursorVisible;
        }
    }


    public void stopCursor() {
        if (mCursorRunnable != null && mCursorHandler != null) {
            mCursorRunnable.cancel();
        }
    }

    public void setBackModel(int mode) {
        this.mode = mode;
    }

    public void setAutoModel(int model) {
        fitMode = model;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //边框
        int left = (int) mDefalutMargin;
        int top = (int) mDefalutMargin;
        for (int i = 0; i < passwordLength; i++) {
            if (mode == MODE_DIVIDER) {
                if (i == mSelectIndex) {
                    blackBorderPaint.setColor(getResources().getColor(R.color.color_000000));
                } else {
                    blackBorderPaint.setColor(getResources().getColor(R.color.color_c7c7cc));
                }
            } else {
                if (i == mSelectIndex) {
                    borderPaint.setColor(getResources().getColor(R.color.color_333333));
                } else {
                    borderPaint.setColor(borderColor);
                }
            }
            if (mode == MODE_DIVIDER) {
                canvas.drawLine(0 + left, mDefaultInputViewHeight + top, mDefaultInputViewSize + left, mDefaultInputViewHeight + top, blackBorderPaint);
            } else {
                RectF rectF = new RectF(0 + left, 0 + top, mDefaultInputViewSize + left, mDefaultInputViewHeight + top);
                float radius = mContext.getResources().getDimension(R.dimen.captcha_rect_radius);
                canvas.drawRoundRect(rectF, radius, radius, borderPaint);
            }
            left += mDefaultInputViewPadding + mDefaultInputViewSize;
        }

        //内容,密码可见
        int textLeft = (int) mDefalutMargin + (int) mDefaultInputViewSize / 2;
        if (mPwdVisiable) {
            for (int i = 0; i < mInputText.length(); i++) {
                String text = mInputText.substring(i, i + 1);
                int textWidth = !TextUtils.isEmpty(text) ? getTextWidth(passwordPaint, text) / 2 : 0;
                canvas.drawText(text, textLeft - textWidth, mDefaultInputViewHeight / 2 + mDefaultInputTextSize / 2, passwordPaint);
                textLeft += mDefaultInputViewPadding + mDefaultInputViewSize;
            }
        } else {
            for (int i = 0; i < mInputText.length(); i++) {
                String text = mInputText.substring(i, i + 1);
                int textWidth = !TextUtils.isEmpty(text) ? getTextWidth(passwordPaint, "*") / 2 : 0;
                canvas.drawText("*", textLeft - textWidth, mDefaultInputViewHeight / 2 + mDefaultInputTextSize / 2 + 5, passwordPaint);
                textLeft += mDefaultInputViewPadding + mDefaultInputViewSize;
            }
        }

        //光标
        if (mSelectIndex < passwordLength && mCursorRunnable.getCursorVisiable()) {

            if (mode == MODE_DIVIDER) {
                linePaint.setColor(getResources().getColor(R.color.color_1c5c9a));
            } else {
                linePaint.setColor(getResources().getColor(R.color.color_ff6200));
            }
            int cursorLeft = (int) mDefalutMargin;
            int cursorTop = (int) mDefalutMargin + ((int) mDefaultInputViewHeight - mCursorHeight) / 2;
            int startX = cursorLeft + (int) mDefaultInputViewSize / 2 + mSelectIndex * (int) mDefaultInputViewSize + mSelectIndex * (int) mDefaultInputViewPadding;
            int stopX = startX;
            int startY = cursorTop;
            int stopY = startY + mCursorHeight;
            canvas.drawLine(startX, startY, stopX, stopY, linePaint);
        }

    }

    public int getTextWidth(Paint paint, String str) {
        int iRet = 0;
        if (str != null && str.length() > 0) {
            int len = str.length();
            float[] widths = new float[len];
            paint.getTextWidths(str, widths);
            for (int j = 0; j < len; j++) {
                iRet += (int) Math.ceil(widths[j]);
            }
        }
        return iRet;
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        mInputText = text.toString();
        if (mInputText.length() > 0) {
            mSelectIndex = mInputText.length();
        } else {
            mSelectIndex = 0;
        }
        if (mTextChangeListener != null) {
            mTextChangeListener.onTextChange(mInputText);
        }
        if (mTextChangeListener != null && mInputText.length() >= passwordLength) {
            mTextChangeListener.inputComplete(mInputText);
        }
        postInvalidate();
    }

    public void setPwdVisiable(boolean pwdVisiable) {
        this.mPwdVisiable = pwdVisiable;
    }

    public int getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(int borderColor) {
        this.borderColor = borderColor;
        borderPaint.setColor(borderColor);
        invalidate();
    }

    public float getBorderWidth() {
        return borderWidth;
    }

    public void setBorderWidth(float borderWidth) {
        this.borderWidth = borderWidth;
        borderPaint.setStrokeWidth(borderWidth);
        invalidate();
    }

    public float getBorderRadius() {
        return borderRadius;
    }

    public void setBorderRadius(float borderRadius) {
        this.borderRadius = borderRadius;
        invalidate();
    }

    public int getPasswordColor() {
        return passwordColor;
    }

    public void setPasswordColor(int passwordColor) {
        this.passwordColor = passwordColor;
        passwordPaint.setColor(passwordColor);
        invalidate();
    }

    public float getPasswordWidth() {
        return passwordWidth;
    }

    public void setPasswordWidth(float passwordWidth) {
        this.passwordWidth = passwordWidth;
        passwordPaint.setStrokeWidth(passwordWidth);
        invalidate();
    }

    public float getPasswordRadius() {
        return passwordRadius;
    }

    public void setPasswordRadius(float passwordRadius) {
        this.passwordRadius = passwordRadius;
        invalidate();
    }

    public void setTextChangeListener(TextChangeListener mTextChangeListener) {
        this.mTextChangeListener = mTextChangeListener;
    }

    public interface TextChangeListener {
        void inputComplete(String text);

        void onTextChange(String text);
    }
}
