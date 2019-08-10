package com.deemo.widget.input.filters;

import android.text.InputFilter;
import android.text.Spanned;

import java.util.regex.Pattern;

public class PhoneNumberFilter implements InputFilter {

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend){
        Pattern p = Pattern.compile("^[7-9][0-9]{9}$");
        return p.matcher(source).replaceAll("");
    }

}
