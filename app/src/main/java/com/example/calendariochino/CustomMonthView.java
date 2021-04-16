package com.example.calendariochino;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.MonthView;

import java.util.List;

public class CustomMonthView extends MonthView {

    private Paint mPointPaint = new Paint();

    private int mRadius;

    private int mPointRadius;

    public CustomMonthView(Context context) {
        super(context);

        mPointPaint.setAntiAlias(true);
        mPointPaint.setStyle(Paint.Style.FILL);
        mSchemePaint.setStyle(Paint.Style.STROKE);
        mSchemePaint.setStrokeWidth(dipToPx(context, 1.2f));
        mSchemePaint.setColor(0xFFFFFFFF);
        mPointRadius = dipToPx(context, 3.6f);
        mPointPaint.setColor(Color.RED);

    }

    @Override
    protected void onPreviewHook() {

        mRadius = Math.min(mItemWidth, mItemHeight) / 5 * 2;

    }

    @Override
    protected boolean onDrawSelected(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme) {

        int cx = x + mItemWidth / 2;
        int cy = y + mItemHeight / 2;

        canvas.drawCircle(cx, cy, mRadius, mSelectedPaint);

        return false;

    }

    @Override
    protected void onDrawScheme(Canvas canvas, Calendar calendar, int x, int y) {

        int cx = x + mItemWidth / 2;
        int cy = y + mItemHeight / 2;

        List<Calendar.Scheme> schemes = calendar.getSchemes();

        canvas.drawCircle(cx, cy, mRadius, mSchemePaint);

        mPointPaint.setColor(schemes.get(0).getShcemeColor());

        int rightTopX = (int) (cx + mRadius * Math.cos(-10 * Math.PI / 180));
        int rightTopY = (int) (cy + mRadius * Math.sin(-10 * Math.PI / 180));

        canvas.drawCircle(rightTopX, rightTopY, mPointRadius, mPointPaint);

        mPointPaint.setColor(schemes.get(1).getShcemeColor());

        int leftTopX = (int) (cx + mRadius * Math.cos(-140 * Math.PI / 180));
        int leftTopY = (int) (cy + mRadius * Math.sin(-140 * Math.PI / 180));

        canvas.drawCircle(leftTopX, leftTopY, mPointRadius, mPointPaint);

        mPointPaint.setColor(schemes.get(2).getShcemeColor());

        int bottomX = (int) (cx + mRadius * Math.cos(100 * Math.PI / 180));
        int bottomY = (int) (cy + mRadius * Math.sin(100 * Math.PI / 180));

        canvas.drawCircle(bottomX, bottomY, mPointRadius, mPointPaint);

    }

    @Override
    protected void onDrawText(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme, boolean isSelected) {

        float baselineY = mTextBaseLine + y;
        int cx = x + mItemWidth / 2;

        if (isSelected) {
            canvas.drawText(String.valueOf(calendar.getDay()),
                    cx,
                    baselineY,
                    mSelectTextPaint);
        } else if (hasScheme) {
            canvas.drawText(String.valueOf(calendar.getDay()),
                    cx,
                    baselineY,
                    calendar.isCurrentDay() ? mCurDayTextPaint :
                            calendar.isCurrentMonth() ? mSchemeTextPaint : mOtherMonthTextPaint);

        } else {
            canvas.drawText(String.valueOf(calendar.getDay()), cx, baselineY,
                    calendar.isCurrentDay() ? mCurDayTextPaint :
                            calendar.isCurrentMonth() ? mCurMonthTextPaint : mOtherMonthTextPaint);
        }
    }

    private static int dipToPx(Context context, float dpValue) {

        final float scale = context.getResources().getDisplayMetrics().density;

        return (int) (dpValue * scale + 0.5f);

    }
}

