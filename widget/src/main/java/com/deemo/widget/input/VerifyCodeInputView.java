package com.deemo.widget.input;

import android.content.ClipboardManager;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import com.deemo.widget.R;

import java.util.ArrayList;
import java.util.regex.Pattern;

import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * author： deemons
 * date:    2018/6/11
 * desc:    验证码的 View，4位数
 */
public class VerifyCodeInputView extends LinearLayout {


    protected ArrayList<EditText> mEditTexts = new ArrayList<>();

    private FullListener mFullListener;

    //验证码粘贴，仅支持4位数字
    private Pattern mPattern = Pattern.compile("[0-9]{2,}");

    public VerifyCodeInputView(Context context) {
        super(context);
        init(context, null);
    }

    public VerifyCodeInputView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public VerifyCodeInputView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        initView();

        initAttrs(context, attrs);

        initListener();

    }

    protected void initView() {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.widget_layout_verify_code, this, true);

        mEditTexts.add((EditText) rootView.findViewById(R.id.widget_input_code_1));
        mEditTexts.add((EditText) rootView.findViewById(R.id.widget_input_code_2));
        mEditTexts.add((EditText) rootView.findViewById(R.id.widget_input_code_3));
        mEditTexts.add((EditText) rootView.findViewById(R.id.widget_input_code_4));

    }

    private void initAttrs(Context context, AttributeSet attrs) {

    }

    private void initListener() {

        //获取焦点时，清空当前输入框内容
        OnFocusChangeListener focusChangeListener = new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus && v instanceof EditText) {
                    EditText editText = (EditText) v;
                    editText.getText().clear();
                }
            }
        };

        for (EditText editText : mEditTexts) {
            editText.setOnFocusChangeListener(focusChangeListener);
        }

        //点击删除按键时，上一个EditText 获取焦点
        for (int i = 1; i < mEditTexts.size(); i++) {
            mEditTexts.get(i).setOnKeyListener(new DelKeyListener(mEditTexts.get(i - 1)));
        }


        //输入一个字符时，下一个 EditText 获取焦点 ，因监听普通按键无效，因此直接监听内容
        NextFocusWatcher.onChangeListener onChangeListener = new NextFocusWatcher.onChangeListener() {
            @Override
            public void onChange(CharSequence s) {
                autoInput(s);
            }
        };
        for (int i = 0; i < mEditTexts.size(); i++) {
            final boolean isLast = i == mEditTexts.size() - 1;
            mEditTexts.get(i).addTextChangedListener(new NextFocusWatcher(isLast ? null : mEditTexts.get(i + 1),
                    i == 0 ? null : onChangeListener) {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    super.onTextChanged(s, start, before, count);
                    if (isLast) checkFill(before, count);
                }
            });

        }


        //粘贴验证码的监听
        PasteEditText.PasteListener pasteListener = new PasteEditText.PasteListener() {
            @Override
            public void onPaste(String s) {
                autoInput(s);
            }
        };

        ((PasteEditText) mEditTexts.get(0)).setPasteListener(pasteListener);

    }


    /**
     * 校验是否填满
     */
    private void checkFill(int before, int count) {
//        if (before == 0 && count == 1) {
        if (mFullListener != null) {
            StringBuilder builder = new StringBuilder();

            for (EditText editText : mEditTexts) {
                builder.append(editText.getText().toString());
            }
            //短信验证码长度为4 时，填充满
            mFullListener.onFull(builder.length() == mEditTexts.size(), builder.toString());
        }
//        }
    }


    /**
     * 校验输入，如果符合，则自动输入
     */
    public void autoInput(@NonNull CharSequence s) {
        if (checkPaste(s, mPattern)) {
            int limit = Math.min(s.length(), mEditTexts.size());
            for (int i = 0; i < limit; i++) {
                mEditTexts.get(i).setText(String.valueOf(s.charAt(i)));
            }

            EditText text = mEditTexts.get(limit - 1);
            text.setSelection(text.getText().length());
        }
    }

    private static boolean checkPaste(CharSequence s, Pattern pattern) {
        return !TextUtils.isEmpty(s) && pattern.matcher(s).matches();
    }


    /**
     * 对 删除按键的监听，以此来改变焦点
     */
    private static class DelKeyListener implements OnKeyListener {
        EditText mEditText;

        public DelKeyListener(EditText editText) {
            mEditText = editText;
        }

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_DEL
                    && event.getAction() == KeyEvent.ACTION_DOWN) {
                if (mEditText != null) mEditText.requestFocus();
            }
            return false;
        }
    }


    /**
     * 对内容的监听，以此跳转到下一个焦点
     */
    private static class NextFocusWatcher implements TextWatcher {
        EditText mEditText;
        onChangeListener mListener;

        public NextFocusWatcher(EditText editText, onChangeListener listener) {
            mEditText = editText;
            mListener = listener;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (mListener != null) mListener.onChange(s);

            if (before == 0 && count == 1) {
                if (mEditText != null) mEditText.requestFocus();
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }


        interface onChangeListener {
            void onChange(CharSequence s);
        }

    }

    /**
     * 监听粘贴事件的 EditText
     */
    public static class PasteEditText extends AppCompatEditText {

        private PasteListener mListener;

        public PasteEditText(Context context) {
            super(context);
        }

        public PasteEditText(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public PasteEditText(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        @Override
        public boolean onTextContextMenuItem(int id) {
            switch (id) {
                case android.R.id.cut:
                    break;
                case android.R.id.copy:
                    break;
                case android.R.id.paste:
                    if (mListener != null) mListener.onPaste(getClipboard().toString());
                    break;
            }
            return super.onTextContextMenuItem(id);
        }

        public void setPasteListener(PasteListener listener) {
            mListener = listener;
        }


        private CharSequence getClipboard() {

            final ClipboardManager manager = (ClipboardManager) getContext().getSystemService(CLIPBOARD_SERVICE);

            if (manager.hasPrimaryClip() && manager.getPrimaryClip().getItemCount() > 0) {
                return manager.getPrimaryClip().getItemAt(0).getText();
            }
            return "";
        }


        interface PasteListener {
            void onPaste(String contain);
        }
    }


    /**
     * 全部输入完成时，回调
     */
    public interface FullListener {
        void onFull(boolean isFull, String code);
    }

//    ============== 对外公布的方法

    /**
     * 清除所有输入
     */
    public void cleanAll() {
        for (EditText editText : mEditTexts) {
            editText.getText().clear();
        }

        mEditTexts.get(0).requestFocus();
    }

    /**
     * 设置 全部输入完成时的监听
     *
     * @param listener {@link FullListener}
     */
    public void setFillListener(FullListener listener) {
        mFullListener = listener;
    }


}
