package com.example.chart.watch_table;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.chart.LibUtils;

import java.lang.ref.WeakReference;

public class WatchTableView extends View {
    private Context mContext;
    private float startX;
    private float endX;
    private float startY;
    private float endY;
    private float baseX;
    private float baseY;
    private float cx;
    private float cy;
    private float radius;
    private float scaleLength = 0;
    private float dialWidth;
    private Paint dialPaint = new Paint();
    private Paint scalePaint = new Paint();
    private Paint circlePaint = new Paint();
    private float scaleWidth;
    private Paint centerPaint = new Paint();
    private Paint hourPaint = new Paint();
    private Paint minutePaint = new Paint();
    private Paint secondsPaint = new Paint();
    private Paint textPaint = new Paint();
    private Rect rect = new Rect();
    private float centerRadius;
    private float secondAngle = 0;
    private float minuteAngle = 0;
    private float hourAngle = 0;
    private MyHandler myHandler = new MyHandler(this);


    public WatchTableView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        this.dialWidth = LibUtils.dip2px(context, 4);
        dialPaint.setAntiAlias(true);
        dialPaint.setStyle(Paint.Style.STROKE);
        dialPaint.setStrokeWidth(dialWidth);
        dialPaint.setColor(Color.BLACK);
        scalePaint.setAntiAlias(true);
        scalePaint.setStyle(Paint.Style.FILL);
        scalePaint.setStrokeWidth(LibUtils.dip2px(context, 2));
        scalePaint.setColor(Color.BLACK);
        circlePaint.setAntiAlias(true);
        circlePaint.setStyle(Paint.Style.FILL);
        circlePaint.setColor(Color.WHITE);
        centerPaint.setAntiAlias(true);
        centerPaint.setColor(Color.RED);
        centerPaint.setStrokeWidth(LibUtils.dip2px(context, 5));
        centerPaint.setStyle(Paint.Style.STROKE);
        hourPaint.setAntiAlias(true);
        hourPaint.setColor(Color.RED);
        hourPaint.setStyle(Paint.Style.FILL);
        hourPaint.setStrokeWidth(LibUtils.dip2px(context, 5));
        minutePaint.setAntiAlias(true);
        minutePaint.setColor(Color.GREEN);
        minutePaint.setStyle(Paint.Style.FILL);
        minutePaint.setStrokeWidth(LibUtils.dip2px(context, 3));
        secondsPaint.setAntiAlias(true);
        secondsPaint.setColor(Color.BLACK);
        secondsPaint.setStyle(Paint.Style.FILL);
        secondsPaint.setStrokeWidth(LibUtils.dip2px(context, 1));
        this.scaleWidth = LibUtils.dip2px(context, 1);
        textPaint.setAntiAlias(true);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(LibUtils.dip2px(context, 12));
        this.centerRadius = LibUtils.dip2px(context, 7);
        myHandler.sendEmptyMessageDelayed(1024, 1000);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.startX = 0;
        this.endY = 0;
        this.baseX = LibUtils.dip2px(mContext, 5);
        this.startY = h;
        this.baseY = h - LibUtils.dip2px(mContext, 5);
        this.endX = w - LibUtils.dip2px(mContext, 5);
        this.cx = w / 2;
        this.cy = h / 2;
        this.radius = Math.min(cx, cy) - LibUtils.dip2px(mContext, 5);
        this.scaleLength = LibUtils.dip2px(mContext, 10);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(cx, cy);
        drawDial(canvas);
        drawScale(canvas);
        drawHour(canvas);
        drawMinute(canvas);
        drawSecond(canvas);
        canvas.drawCircle(0, 0, centerRadius, centerPaint);
    }

    private void drawDial(Canvas canvas) {
        canvas.drawCircle(0, 0, radius, dialPaint);
    }

    private void drawScale(Canvas canvas) {
        for (int i = 1; i <= 12; i++) {
            textPaint.getTextBounds(String.valueOf(i), 0, String.valueOf(i).length(), rect);
            float textWidth = rect.width();
            float textHeight = rect.height();
            double angle = Math.toRadians(i * 30);
            canvas.drawLine(-(radius - dialWidth / 2) * (float) (Math.sin(angle)), -(radius - dialWidth / 2) * (float) (Math.cos(angle)), -(radius - scaleLength - dialWidth / 2) * (float) (Math.sin(angle)), -(radius - scaleLength - dialWidth / 2) * (float) (Math.cos(angle)), scalePaint);
            float textX = ((radius - scaleLength - dialWidth) * (float) (Math.sin(angle)));
            float textY = ((radius - scaleLength - dialWidth) * (float) (Math.cos(angle)));
            canvas.drawText("" + (i), textX, textY, textPaint);
        }
    }

    private void drawHour(Canvas canvas) {
        double angle = Math.toRadians(hourAngle);
        canvas.drawLine(0, 0, (float) (Math.sin(angle) * (radius / 2)), (float) (Math.cos(angle) * (radius / 2)), hourPaint);
    }

    private void drawMinute(Canvas canvas) {
        double angle = Math.toRadians(minuteAngle);
        canvas.drawLine(0, 0, (float) (Math.sin(angle) * (LibUtils.dip2px(mContext, 40) - radius)), (float) (Math.cos(angle) * (LibUtils.dip2px(mContext, 40) - radius)), minutePaint);
    }

    private void drawSecond(Canvas canvas) {
        double angle = Math.toRadians(secondAngle);
        canvas.drawLine(0, 0, (float) (Math.sin(angle) * (radius - LibUtils.dip2px(mContext, 30))), (float) (Math.cos(angle) * (radius - LibUtils.dip2px(mContext, 30))), secondsPaint);
    }

    private class MyHandler extends Handler {
        WeakReference<WatchTableView> weakReference;

        MyHandler(WatchTableView watchTableView) {
            this.weakReference = new WeakReference<>(watchTableView);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1024:
                    secondAngle -= 6;
                    invalidate();
                    if (secondAngle % -360 == 0) {
                        secondAngle = 0;
                        myHandler.sendEmptyMessage(1025);
                    }
                    myHandler.sendEmptyMessageDelayed(1024, 1000);
                    break;
                case 1025:
                    minuteAngle -= 6;
                    if (minuteAngle % -360 == 0)
                        myHandler.sendEmptyMessage(1026);
                    invalidate();
                    break;
                case 1026:
                    hourAngle -= 30;
                    invalidate();
                    break;
            }
        }
    }
}
