package com.ruby.x.json2.Libraries;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AdapterArrayCustom extends ArrayAdapter {

    Typeface myFont;

    public AdapterArrayCustom(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public TextView getView(int position, View convertView, ViewGroup parent) {
        TextView v = (TextView) super.getView(position, convertView, parent);
        myFont = Typeface.createFromAsset(getContext().getAssets(), "fonts/Lato-Regular.ttf");
        v.setTypeface(myFont);
        return v;
    }

    public TextView getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView v = (TextView) super.getView(position, convertView, parent);
        myFont = Typeface.createFromAsset(getContext().getAssets(), "fonts/Lato-Regular.ttf");
        v.setTypeface(myFont);
        return v;
    }

}
