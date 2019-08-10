package com.deemo.widget.input.filters;

import android.text.InputFilter;
import android.text.Spanned;

import java.util.regex.Pattern;

/**
 * author： deemo
 * date:    2019-08-05
 * desc:    IFCCode （英文、数字）
 */
public class IFCCodeFilter implements InputFilter {
    /**
     * @param source 输入的文字
     * @param start  输入-0，删除-0
     * @param end    输入-文字的长度，删除-0
     * @param dest   原先显示的内容
     * @param dstart 输入-原光标位置，删除-光标删除结束位置
     * @param dend   输入-原光标位置，删除-光标删除开始位置
     * @return
     */
    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        Pattern p = Pattern.compile("[^a-zA-Z0-9]");
        return p.matcher(source).replaceAll("");
    }
}
