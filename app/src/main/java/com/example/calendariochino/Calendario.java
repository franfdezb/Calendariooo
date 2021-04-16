package com.example.calendariochino;

import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
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

    Map<String, Calendar> map = new HashMap<>();

    TextView mTextMonthDay;

    TextView mTextEvents;

    TextView mTextYear;

    TextView mTextLunar;

    TextView mTextCurrentDay;

    EditText mAddEvent;

    ImageButton mClose;

    ImageButton mOk;

    ImageButton mAddEventBtn;

    CalendarView mCalendarView;

    RelativeLayout mRelativeTool;

    CalendarLayout mCalendarLayout;

    private int mYear;

    private int selectedDay;

    private int selectedYear;

    private int selectedMonth;


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
        mClose = findViewById(R.id.close_btn);
        mOk = findViewById(R.id.ok_btn);
        mAddEventBtn = findViewById(R.id.add_btn);

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



    public void closeEvent(View v){

        mOk.setVisibility(View.GONE);
        mClose.setVisibility(View.GONE);
        mAddEventBtn.setVisibility(View.VISIBLE);
        mAddEvent.setText("");
        mAddEvent.setVisibility(View.GONE);

    }

    public void addEvent(View v){

        mClose.setVisibility(View.VISIBLE);
        mAddEvent.setVisibility(View.VISIBLE);
        mAddEventBtn.setVisibility(View.GONE);
        mOk.setVisibility(View.VISIBLE);
        mTextEvents.setText("");
        mTextEvents.setVisibility(View.VISIBLE);

    }


    public void okEvent(View v){

        int year = selectedYear;
        int month = selectedMonth;
        int day = selectedDay;
        String txt;

        if(mAddEvent.getText().toString().isEmpty())
            txt = "(Sin nombre)";
        else
            txt = mAddEvent.getText().toString();

        map.put((getSchemeCalendar(year, month, day, txt)).toString(),
                getSchemeCalendar(year, month, day, txt));
        mCalendarView.setSchemeDate(map);

        mOk.setVisibility(View.GONE);
        mClose.setVisibility(View.GONE);
        mAddEvent.setVisibility(View.GONE);
        mAddEventBtn.setVisibility(View.VISIBLE);
        mAddEvent.setText("");

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

        mOk.setVisibility(View.GONE);
        mClose.setVisibility(View.GONE);
        mAddEventBtn.setVisibility(View.VISIBLE);
        mAddEvent.setText("");

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
        mYear = calendar.getYear();

        if(calendar.getScheme() == null || calendar.getScheme().isEmpty())
            mTextEvents.setText(calendar.getDay() + "/" + calendar.getMonth() + ": Sin eventos programados");
    }

    @Override
    public void onYearChange(int year) {

        mTextMonthDay.setText(String.valueOf(year));

    }

}