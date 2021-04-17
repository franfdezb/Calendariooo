package com.example.calendariochino;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

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

    private mySQLiteDBHandler dbHandler;

    private SQLiteDatabase sqLiteDatabase;

    TextView mTextMonthDay;

    TextView mTextEvents;

    TextView mTextYear;

    TextView mTextLunar;

    TextView mTextCurrentDay;

    TextView mTimeFrom;

    TextView mTimeTill;

    EditText mAddEvent;

    ImageButton mClose;

    ImageButton mOk;

    ImageButton mAddEventBtn;

    ImageButton mDeleteEventBtn;

    CalendarView mCalendarView;

    RelativeLayout mRelativeTool;

    CalendarLayout mCalendarLayout;

    private String desde;

    private String hasta;

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
        mDeleteEventBtn = findViewById(R.id.delete_btn);
        mTimeFrom = findViewById(R.id.txtTime1);
        mTimeTill = findViewById(R.id.txtTime2);

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
        onCalendarSelect(mCalendarView.getSelectedCalendar(), true);

        try{

            dbHandler = new mySQLiteDBHandler(this, "EventsDB", null,1);
            sqLiteDatabase = dbHandler.getWritableDatabase();
            sqLiteDatabase.execSQL("CREATE TABLE EventCalendar(ID INTEGER PRIMARY KEY, Dia TEXT, Mes TEXT, Año TEXT, Evento TEXT, Desde TEXT, Hasta TEXT)");
        }
        catch (Exception e){
            e.printStackTrace();
        }


    }

    @Override
    protected void initData() {




        Cursor  cursor = sqLiteDatabase.rawQuery("select * from EventCalendar",null);


        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String name = cursor.getString(cursor.getColumnIndex("Evento"));
                int day = cursor.getInt(cursor.getColumnIndex("Dia"));
                int month =cursor.getInt(cursor.getColumnIndex("Mes"));
                int year =cursor.getInt(cursor.getColumnIndex("Año"));

                map.put((getSchemeCalendar(year, month, day, name)).toString(),
                        getSchemeCalendar(year, month, day, name));
                cursor.moveToNext();
            }
        }

            mCalendarView.setSchemeDate(map);

        }



    public void closeEvent(View v){

        mOk.setVisibility(View.GONE);
        mClose.setVisibility(View.GONE);
        mAddEventBtn.setVisibility(View.VISIBLE);
        mAddEvent.setText("");
        mAddEvent.setVisibility(View.GONE);
        mDeleteEventBtn.setVisibility(View.VISIBLE);
        onCalendarSelect(mCalendarView.getSelectedCalendar(), true);
        mTimeTill.setVisibility(View.GONE);
        mTimeFrom.setVisibility(View.GONE);
        mTimeTill.setText("");
        mTimeFrom.setText("");

    }

    public void addEvent(View v){

        mDeleteEventBtn.setVisibility(View.GONE);
        mTimeTill.setVisibility(View.VISIBLE);
        mTimeFrom.setVisibility(View.VISIBLE);
        mClose.setVisibility(View.VISIBLE);
        mAddEvent.setVisibility(View.VISIBLE);
        mAddEventBtn.setVisibility(View.GONE);
        mOk.setVisibility(View.VISIBLE);
        mTextEvents.setText("");
        mTextEvents.setVisibility(View.VISIBLE);


    }

    java.util.Calendar c = java.util.Calendar.getInstance();


    public void from(View v) {

        int hour = c.get(java.util.Calendar.HOUR);
        int minute = c.get(java.util.Calendar.HOUR);

        TimePickerDialog timePickerDialog = new TimePickerDialog(Calendario.this,
                (view, hourOfDay, minuteOfDay) -> {

                    String hourString;
                    if (hourOfDay < 10)
                        hourString = "0" + hourOfDay;
                    else
                        hourString = "" +hourOfDay;

                    String minuteSting;
                    if (minute < 10)
                        minuteSting = "0" + minute;
                    else
                        minuteSting = "" +minute;

                    mTimeFrom.setText(hourString + ":" + minuteSting);



                }, hour, minute, false);
        timePickerDialog.show();
    }


    public void till(View v){

        int hour = c.get(java.util.Calendar.HOUR);
        int minute = c.get(java.util.Calendar.HOUR);

        TimePickerDialog timePickerDialog = new TimePickerDialog(Calendario.this,
                (view, hourOfDay, minuteOfDay) -> {

                    String hourString;
                    if (hourOfDay < 10)
                        hourString = "0" + hourOfDay;
                    else
                        hourString = "" +hourOfDay;

                    String minuteSting;
                    if (minute < 10)
                        minuteSting = "0" + minute;
                    else
                        minuteSting = "" +minute;

                    mTimeTill.setText(hourString + ":" + minuteSting);
                }, hour, minute, false);
        timePickerDialog.show();

    }

    public void deleteEvent(View v){

        int year = selectedYear;
        int month = selectedMonth;
        int day = selectedDay;
        String txt;

        txt = mTextEvents.getText().toString();

        mCalendarView.removeSchemeDate(getSchemeCalendar(year, month, day, txt));

        onCalendarSelect(mCalendarView.getSelectedCalendar(), true);

            sqLiteDatabase.rawQuery("DELETE FROM EventCalendar WHERE Dia = " +selectedDay+ " AND  Mes = " +selectedMonth+ " AND Año = "+selectedYear , null);

       }


    public void okEvent(View v){

        int year = selectedYear;
        int month = selectedMonth;
        int day = selectedDay;
        String selectedDate = selectedDay + "/" + selectedMonth + "/" + selectedYear;
        String txt;

        if(mAddEvent.getText().toString().isEmpty())
            txt = "(Sin nombre)";
        else
            txt = mAddEvent.getText().toString();

        map.put((getSchemeCalendar(year, month, day, txt)).toString(),
                getSchemeCalendar(year, month, day, txt));
        mCalendarView.setSchemeDate(map);

        ContentValues contentValues = new ContentValues();
        contentValues.put("Dia", selectedDay);
        contentValues.put("Mes", selectedMonth);
        contentValues.put("Año", selectedYear);
        contentValues.put("Evento", txt);
        contentValues.put("Desde", mTimeFrom.getText().toString());
        contentValues.put("Hasta", mTimeTill.getText().toString());
        sqLiteDatabase.insert("EventCalendar", null, contentValues);

        mOk.setVisibility(View.GONE);
        mClose.setVisibility(View.GONE);
        mAddEvent.setVisibility(View.GONE);
        mAddEventBtn.setVisibility(View.VISIBLE);
        mDeleteEventBtn.setVisibility(View.VISIBLE);
        onCalendarSelect(mCalendarView.getSelectedCalendar(), true);
        mAddEvent.setText("");
        mTimeTill.setVisibility(View.GONE);
        mTimeFrom.setVisibility(View.GONE);
        mTimeTill.setText("");
        mTimeFrom.setText("");

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
        mTimeTill.setVisibility(View.GONE);
        mTimeFrom.setVisibility(View.GONE);
        mTimeTill.setText("");
        mTimeFrom.setText("");
        selectedDay = calendar.getDay();
        selectedYear = calendar.getYear();
        selectedMonth = calendar.getMonth();
        mAddEvent.setVisibility(View.GONE);
        mTextLunar.setVisibility(View.VISIBLE);
        mTextYear.setVisibility(View.VISIBLE);
        mTextEvents.setVisibility(View.VISIBLE);
        mDeleteEventBtn.setVisibility(View.VISIBLE);

        try {

            String selectQuery = "SELECT Desde FROM EventCalendar WHERE Dia = "+ selectedDay +" AND Mes ="+ selectedMonth +" AND Año = "+ selectedYear;

            Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                do {
                    desde = cursor.getString(0);
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();

        }

        try {

            String selectQuery = "SELECT Hasta FROM EventCalendar WHERE Dia = "+ selectedDay +" AND Mes ="+ selectedMonth +" AND Año = "+ selectedYear;

            Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                do {
                    hasta = cursor.getString(0);
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();

        }

        if((desde == null || desde.isEmpty()) && (hasta == null || hasta.isEmpty()))
            mTextEvents.setText(calendar.getScheme() + "\n Todo el día");

        else if((desde == null || desde.isEmpty()) && !hasta.isEmpty())
            mTextEvents.setText(calendar.getScheme() + "\n  ? - " +hasta+"");
        else if(!desde.isEmpty() && (hasta.isEmpty() || hasta == null))
            mTextEvents.setText(calendar.getScheme() + "\n " + desde + " -  ?");
        else
        mTextEvents.setText(calendar.getScheme() + "\n " + desde + " - " +hasta+"");


        mTextMonthDay.setText(calendar.getDay()+ " / " +calendar.getMonth());
        mTextYear.setText(String.valueOf(calendar.getYear()));
        mYear = calendar.getYear();

        if(calendar.getScheme() == null || calendar.getScheme().isEmpty())
            mTextEvents.setText("Sin eventos programados");
    }

    @Override
    public void onYearChange(int year) {

        mTextMonthDay.setText(String.valueOf(year));

    }

}