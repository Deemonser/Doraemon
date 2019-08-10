package com.deemo.widget.input;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import com.deemo.widget.R;

/**
 * author： deemons
 * date:    2018/6/11
 * desc:    验证码的 View，4位数
 */
public class PinCodeInputView extends VerifyCodeInputView {


    public PinCodeInputView(Context context) {
        super(context);
    }

    public PinCodeInputView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PinCodeInputView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    protected void initView() {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.widget_layout_pin_code, this, true);

        mEditTexts.add((EditText) rootView.findViewById(R.id.widget_input_code_1));
        mEditTexts.add((EditText) rootView.findViewById(R.id.widget_input_code_2));
        mEditTexts.add((EditText) rootView.findViewById(R.id.widget_input_code_3));
        mEditTexts.add((EditText) rootView.findViewById(R.id.widget_input_code_4));
        mEditTexts.add((EditText) rootView.findViewById(R.id.widget_input_code_5));
        mEditTexts.add((EditText) rootView.findViewById(R.id.widget_input_code_6));

    }

}
