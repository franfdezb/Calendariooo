package com.example.calendariochino;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;

import java.util.HashMap;
import java.util.Map;

public class Calendario extends MainActivity implements
        CalendarView.OnCalendarSelectListener,
        CalendarView.OnYearChangeListener,
        View.OnClickListener {

    TextView mTextMonthDay;

    TextView mTextEvents;

    EditText mAddEvent;



    TextView mTextYear;

    TextView mTextLunar;

    Map<String, Calendar> map = new HashMap<>();

    TextView mTextCurrentDay;

    CalendarView mCalendarView;

    RelativeLayout mRelativeTool;
    private int mYear;
    CalendarLayout mCalendarLayout;

    private int selectedDay;
    private int selectedYear;
    private int selectedMonth;

    ImageView mAddbtn;





    @SuppressLint("SetTextI18n")
    @Override
    protected void initView() {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.solar_background));
        }
        setContentView(R.layout.activity_calendario);
        mTextMonthDay = findViewById(R.id.tv_month_day);
        mTextYear = findViewById(R.id.tv_year);
        mTextLunar = findViewById(R.id.tv_lunar);
        mRelativeTool = findViewById(R.id.rl_tool);
        mCalendarView = findViewById(R.id.calendarView);
        mTextCurrentDay = findViewById(R.id.tv_current_day);
        mTextEvents = findViewById(R.id.tv_events);
        mAddEvent = findViewById(R.id.txtEvent);
        mAddbtn = findViewById(R.id.add_btn);



        mTextMonthDay.setOnClickListener(v -> {
            if (!mCalendarLayout.isExpand()) {
                mCalendarLayout.expand();
                return;
            }
            mCalendarView.showYearSelectLayout(mYear);
            mTextEvents.setVisibility(View.GONE);
            mTextLunar.setVisibility(View.GONE);
            mTextYear.setVisibility(View.GONE);
            mTextMonthDay.setText(String.valueOf(mYear));
        });
        findViewById(R.id.fl_current).setOnClickListener(v -> mCalendarView.scrollToCurrent());
        mCalendarLayout = findViewById(R.id.calendarLayout);
        mCalendarView.setOnCalendarSelectListener(this);
        mCalendarView.setOnYearChangeListener(this);
        mTextYear.setText(String.valueOf(mCalendarView.getCurYear()));
        mYear = mCalendarView.getCurYear();
        mTextMonthDay.setText(mCalendarView.getCurDay() + " / " + mCalendarView.getCurMonth());
        //mTextLunar.setText("今日");
        mTextCurrentDay.setText(String.valueOf(mCalendarView.getCurDay()));


    }

    @Override
    protected void initData() {
        int year = mCalendarView.getCurYear();
        int month = mCalendarView.getCurMonth();


        map.put(getSchemeCalendar(year, month, 3, "Dentista").toString(),
                getSchemeCalendar(year, month, 3, "Dentista"));
        map.put(getSchemeCalendar(year, month, 6, "Suspenso time").toString(),
                getSchemeCalendar(year, month, 6, "Suspenso time"));
        map.put(getSchemeCalendar(year, month, 9, "Vacuna").toString(),
                getSchemeCalendar(year, month, 9, "Vacuna"));
        mCalendarView.setSchemeDate(map);



    }



    public void addEvent(View v){


        int year = selectedYear;
        int month = selectedMonth;
        int day = selectedDay;

        mAddEvent.setVisibility(View.VISIBLE);
        mTextEvents.setText("");
        map.put((getSchemeCalendar(year, month, day, mAddEvent.getText().toString())).toString(),
                getSchemeCalendar(year, month, day, mAddEvent.getText().toString()));
        mCalendarView.setSchemeDate(map);
        mTextEvents.setVisibility(View.VISIBLE);



    }


    @Override
    public void onClick(View v) {


    }

    private Calendar getSchemeCalendar(int year, int month, int day, String text) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(Color.WHITE);
        calendar.setScheme(text);
        calendar.addScheme(0xFFa8b015, "rightTop");
        calendar.addScheme(0xFF423cb0, "leftTop");
        calendar.addScheme(0xFF643c8c, "bottom");

        return calendar;
    }

    @Override
    public void onCalendarOutOfRange(Calendar calendar) {

    }


    @Override
    public void onCalendarSelect(Calendar calendar, boolean isClick) {
        selectedDay = calendar.getDay();
        selectedYear = calendar.getYear();
        selectedMonth = calendar.getMonth();
        mAddEvent.setVisibility(View.GONE);
        mTextLunar.setVisibility(View.VISIBLE);
        mTextYear.setVisibility(View.VISIBLE);
        mTextEvents.setVisibility(View.VISIBLE);
        mTextEvents.setText(calendar.getDay() + "/" + calendar.getMonth() + ": " + calendar.getScheme());
        mTextMonthDay.setText(calendar.getDay()+ " / " +calendar.getMonth());
        mTextYear.setText(String.valueOf(calendar.getYear()));
        //mTextLunar.setText(calendar.getLunar());
        mYear = calendar.getYear();

        if(calendar.getScheme() == null || calendar.getScheme().isEmpty())
            mTextEvents.setText(calendar.getDay() + "/" + calendar.getMonth() + ": Sin eventos programados");


    }

    @Override
    public void onYearChange(int year) {
        mTextMonthDay.setText(String.valueOf(year));
    }}