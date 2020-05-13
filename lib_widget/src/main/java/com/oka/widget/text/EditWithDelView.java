package com.oka.widget.text;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import com.oka.widget.R;
import com.oka.widget.text.listener.ActionModeCallback;

/**
 * Created by zengyong on 2018/11/29
 */
public class EditWithDelView extends EditText implements TextWatcher {


    private Drawable mClearDrawable;
    //两位小数金额验证正则
    //private String regex = "^(0(\\.\\d{0,2})?|[1-9](\\d{0,11}|\\d{0,10}\\.?|\\d{0,9}(\\.\\d?)?|\\d{0,8}(\\.\\d{0,2})?))?$";

    public EditWithDelView(Context context) {
        this(context , null);
    }

    public EditWithDelView(Context context, AttributeSet attrs) {
        this(context, attrs , 0);
    }

    public EditWithDelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        clearCopyAndPasteFunc();
        init();
        addTextChangedListener(this);
    }


    private void init(){
        mClearDrawable = getCompoundDrawables()[2];
        if(mClearDrawable == null) mClearDrawable = getResources().getDrawable(R.drawable.ic_edit_del);
        Rect bounds = mClearDrawable.getBounds();
        int drawableSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP , 16 , getResources().getDisplayMetrics());
        mClearDrawable.setBounds(bounds.left , bounds.top , bounds.right + drawableSize , bounds.bottom + drawableSize);
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {}

    @Override
    public void afterTextChanged(Editable editable) {
        String str = editable.toString().trim();
        if(TextUtils.isEmpty(str)){
            setClearIconVisible(false);
        }else{
            setClearIconVisible(true);
        }

//        if(!TextUtils.isEmpty(regex) && str.matches(regex)){
//            if(listener != null)listener.onVerifySuccess(str);
//        }else{
//            if(TextUtils.equals(str , "."))setText("");
//            else if(str.matches("0{1}[1-9]+")) setText(str.substring(1 , str.length()));
//            else setText(str.substring(0 , str.length() - 1));
//        }
    }


    public void setClearIconVisible(boolean isShow){
        Drawable right = isShow ? mClearDrawable : null;
        setCompoundDrawables(getCompoundDrawables()[0] , getCompoundDrawables()[1] , right , getCompoundDrawables()[3]);
    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        super.onSelectionChanged(selStart, selEnd);
        if(selStart == selEnd){
            setSelection(getText().length());
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_UP){
            if(getCompoundDrawables()[2] != null){
                if(event.getX() > (getWidth() - getTotalPaddingRight()) && event.getX() < (getWidth() - getPaddingRight())){ // 点击删除图片位置
                    setText("");
                }
            }
        }
        return super.onTouchEvent(event);
    }



    private void clearCopyAndPasteFunc(){
        setLongClickable(false);  //去掉长按复制粘贴功能，后面设置长按监听事件也不再起作用
        setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });
        setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        setTextIsSelectable(false);
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M)return;
        setCustomSelectionActionModeCallback(new ActionModeCallback());
    }


    @Override
    public boolean onTextContextMenuItem(int id) {
        if(id == android.R.id.paste || id == android.R.id.copy){
            return true;
        }
        return super.onTextContextMenuItem(id);
    }


    @Override
    public boolean isSuggestionsEnabled() {
        return false;
    }

    private OnTextVerifyListener listener;

    public void setOnTextVerifyListener(OnTextVerifyListener listener){
        this.listener = listener;
    }

    public interface OnTextVerifyListener{

        void onVerifySuccess(String s);

        void onEmpty();
    }


}
