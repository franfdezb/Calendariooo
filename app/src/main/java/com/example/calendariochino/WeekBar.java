package com.example.calendariochino;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.TextView;

public class WeekBar extends com.haibin.calendarview.WeekBar {

    public WeekBar(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.solar_week_bar, this, true);
        setBackgroundColor(context.getResources().getColor(R.color.dark_red));
    }

    @Override
    protected void onWeekStartChange(int weekStart) {

        for (int i = 0; i < getChildCount(); i++) {
            ((TextView) getChildAt(i)).setText(getWeekString(i, weekStart));
        }

    }

    private String getWeekString(int index, int weekStart) {

        String[] weeks = getContext().getResources().getStringArray(R.array.spanish_week_string_array);

        if (weekStart == 1) {
            return weeks[index];
        }
        if (weekStart == 2) {
            return weeks[index == 6 ? 0 : index + 1];
        }

        return weeks[index == 0 ? 6 : index - 1];
    }

}
