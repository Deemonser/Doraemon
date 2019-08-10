package com.deemo.widget.input;

import android.content.Context;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import com.deemo.widget.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * author： deemons
 * date:    2018/6/12
 * desc:    邮箱 提示
 */
public class EmailAdapter extends BaseAdapter implements Filterable {

    private final String[] mEmailArray;

    private String email = "";
    private List<String> mList;
    private MyFilter mFilter;
    private DisplayMetrics displayMetrics;

    public EmailAdapter(Context context) {
        mList = new ArrayList<>();
        mEmailArray = context.getResources().getStringArray(R.array.email);
        displayMetrics = context.getResources().getDisplayMetrics();
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList == null ? null : mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            TextView textView = new TextView(parent.getContext());
            textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    mm2px(60)));
            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    mm2px(28));
            textView.setTextColor(Color.parseColor("#666666"));
            textView.setPadding(mm2px(30), 0, 0, 0);
            textView.setSingleLine();
            convertView = textView;
        }
        TextView txt = (TextView) convertView;
        txt.setText(mList.get(position));
        return txt;
    }

    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new MyFilter();
        }
        return mFilter;
    }


    private int mm2px(int value) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_MM, value, displayMetrics) + 0.5f);
    }

    private class MyFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (mList == null) {
                mList = new ArrayList<String>();
            }
            results.values = email + mList;
            results.count = mList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }

    public void setEmail(String inputEmail) {
        if (inputEmail.contains("@")) {
            int index = inputEmail.indexOf("@");
            this.email = inputEmail.substring(0, index > 0 ? index : 0);
        } else {
            this.email = inputEmail;
        }

        if (this.email == null) {
            this.email = "";
        }

        ArrayList<String> list = new ArrayList<>();
        for (String s : mEmailArray) {
            list.add(String.format(Locale.getDefault(), "%1$s@%2$s.com", this.email, s));
        }

        mList.clear();
        for (String s : list) {
            if (s.contains(inputEmail)) {
                mList.add(s);
            }
        }

        notifyDataSetChanged();
    }

}
