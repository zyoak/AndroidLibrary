package com.oka.widget.text;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.widget.EditText;

import com.oka.widget.R;
import com.oka.widget.text.listener.ActionModeCallback;

/**
 * 自定义切割手机号 EditText
 * 实现效果：输入手机号，实现按344位置切分。
 */
public class PhoneNumInputEditText extends EditText {

    /**
     * 默认中国手机号码长度
     */
    private int phoneLength = 11;
    /**
     * 定义手机号码分割的位置，A为第一个空格分隔位置，B为第二个，C...依此类推
     * <p>
     * 当前为 国内手机号 187 6543 2100
     */
    private int splitA = 3;
    private int splitB = 4;
    private int splitC = 0;

    /**
     * 当前号码
     */
    private String phone = "";

    private CurrentPhone currentPhoneListener;
    private boolean isShowDelIcon;
    private Drawable mClearDrawable;

    public PhoneNumInputEditText(Context context) {
        this(context, null);
    }

    public PhoneNumInputEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs , R.styleable.PhoneNumInputEditText);
        isShowDelIcon = a.getBoolean(R.styleable.PhoneNumInputEditText_isShowDelIcon , false);
        a.recycle();

        setInputType(InputType.TYPE_CLASS_PHONE);
        initSplitListener();
        disableCopy();
        configDelIcon();
    }

    private void configDelIcon(){
        if(isShowDelIcon){
            mClearDrawable = getCompoundDrawables()[2];
            if(mClearDrawable == null) mClearDrawable = getResources().getDrawable(R.drawable.ic_edit_del);
            Rect bounds = mClearDrawable.getBounds();
            int drawableSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP , 16 , getResources().getDisplayMetrics());
            mClearDrawable.setBounds(bounds.left , bounds.top , bounds.right + drawableSize , bounds.bottom + drawableSize);
        }
    }


    public void setClearIconVisible(boolean isShow){
        Drawable right = isShow ? mClearDrawable : null;
        setCompoundDrawables(getCompoundDrawables()[0] , getCompoundDrawables()[1] , right , getCompoundDrawables()[3]);
    }

    public boolean onTouchEvent(MotionEvent event) {
        if(isShowDelIcon && event.getAction() == MotionEvent.ACTION_UP){
            if(getCompoundDrawables()[2] != null){
                if(event.getX() > (getWidth() - getTotalPaddingRight()) && event.getX() < (getWidth() - getPaddingRight())){ // 点击删除图片位置
                    setText("");
                }
            }
        }
        return super.onTouchEvent(event);
    }


    public void setCurrentPhoneListener(CurrentPhone currentPhoneListener) {
        this.currentPhoneListener = currentPhoneListener;
    }

    /**
     * 设置监听器，实时改变字符间隔
     */
    private void initSplitListener() {
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int before, int count) {
                //这里不要随便写一些代码，因为这里会有一些进程和线程之间的初始化和交互，开始
                //这里写了一些东西，调试发现的，最后就去了Handler和Looper，尤其是使用return就出错了。
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s) || s.length() == 0) return;
                //----------防止手动输入空格越界---------------
                if (s.toString().substring(s.length() - 1).equals(" ")) {
                    setText(s.toString().trim());
                    setSelection(s.toString().trim().length());
                    return;
                }

                String ns = s.toString().replace(" ", "");

                //已经输入设置长度的手机号
                if (ns.length() == phoneLength) {
                    return;
                } else {
                    //这里测试过，当在输入第spliteA+1 = a个数字和第spliteA + spliteB+2 = b个数字时，count瞬间变为a,b
                    //下面的ns.length() + count会大于phoneLength，其实并没有，下面手动改变count数值。
                    count = 1;
                }

                //下面是切割主要逻辑
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < s.length(); i++) {
                    if (splitC == 0) {
                        splitC = -1;
                    }
                    if (splitB == 0) {
                        splitB = -1;
                    }
                    //当前处于不应该切分的空格处，即应删除空格
                    if (i != splitA && i != (splitA + splitB + 1) && i != (splitA + splitB + splitC + 2) && s.charAt(i) == ' ') {
                        continue;
                    } else {
                        //不应该删除的位置，添加上字符，不论该字符是不是空格，这里出现的空格是该循环中添加上的空格，
                        //用户如果手动输入空格，在上面通过trim()已经删掉了
                        sb.append(s.charAt(i));
                        //如果添加至此字符长度等于该分段长度，分段长度：187 6543 2100该手机号分为前4位(187+" ")一段，前9位(187+" "+6543+" ")一段
                        //最后一个非空格字符前插入空格进行分割，所有空格分隔都由此处插入
                        if ((sb.length() == (splitA + 1) || sb.length() == (splitA + splitB + 2) || sb.length() == (splitA + splitB + splitC + 3)) && sb.charAt(sb.length() - 1) != ' ') {
                            sb.insert(sb.length() - 1, ' ');
                        }
                    }
                }
                try {
                    /**
                     * before为1是粘贴替换的情况
                     */
                    if (!sb.toString().equals(s.toString())) {

                        int index = start + 1;

                        if (sb.charAt(start) == ' ') {
                            if (before == 0) {
                                index++;
                            } else {
                                index--;
                            }
                        } else {
                            if (before == 1) {
                                index--;
                            }
                        }
                        setText(sb.toString());
                        setSelection(index);
                    }
                } catch (IndexOutOfBoundsException e) {

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                phone = getText().toString().replace(" ", "");
                if (currentPhoneListener != null) {
                    currentPhoneListener.getCurrentPhone(phone);
                }
                String formatPhone = getText().toString();
                if(!TextUtils.isEmpty(formatPhone)){
                    if(!TextUtils.isEmpty(phone)){
                        if(phone.length() >= 11 && (formatPhone.indexOf(" ") !=3 || formatPhone.lastIndexOf(" ") != 8)){
                            setSpText(phone);
                        }
                    }
                }
                if(isShowDelIcon && TextUtils.isEmpty(s.toString().trim())){
                    setClearIconVisible(false);
                }else if(isShowDelIcon){
                    setClearIconVisible(true);
                }
            }
        });
    }

    //回调结果接口
    public interface CurrentPhone {
        void getCurrentPhone(String phone);
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getPhoneLength() {
        return phoneLength;
    }

    public void setPhoneLength(int phoneLength) {
        this.phoneLength = phoneLength;
    }

    public int getSplitA() {
        return splitA;
    }

    public void setSplitA(int splitA) {
        this.splitA = splitA;
    }

    public int getSplitB() {
        return splitB;
    }

    public void setSplitB(int splitB) {
        this.splitB = splitB;
    }

    public int getSplitC() {
        return splitC;
    }

    public void setSplitC(int splitC) {
        this.splitC = splitC;
    }

    public String getTextWithoutSpace() {
        return phone;
    }

    public void setSpText(String text) {
        StringBuffer content = new StringBuffer(text);
        if (text.length() >= 8) {
            content.insert(3, " ");
            content.insert(8, " ");
        } else if (text.length() > 3) {
            content.insert(3, " ");
        }
        setText(content.toString());
        setSelection(content.length());
    }


    private void disableCopy() {
        setTextIsSelectable(false);
        setLongClickable(false);
        setCustomSelectionActionModeCallback(new ActionModeCallback());
        setSelectAllOnFocus(false);
    }
}
