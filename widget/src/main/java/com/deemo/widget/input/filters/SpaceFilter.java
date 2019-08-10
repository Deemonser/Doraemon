package com.deemo.widget.input.filters;

import android.text.InputFilter;
import android.text.Spanned;

import java.util.regex.Pattern;

/**
 * author： deemo
 * date:    2019-08-05
 * desc:    屏蔽空格
 */
public class SpaceFilter implements InputFilter {
    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        Pattern p = Pattern.compile("\\s+");
        return p.matcher(source).replaceAll("");
    }
}
