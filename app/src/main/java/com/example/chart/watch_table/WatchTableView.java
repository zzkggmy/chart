package com.example.chart.watch_table;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.chart.LibUtils;

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


    public WatchTableView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        this.dialWidth = LibUtils.dip2px(context,4);
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
        canvas.drawCircle(0, 0, LibUtils.dip2px(mContext, 5), centerPaint);
    }

    private void drawDial(Canvas canvas) {
        canvas.drawCircle(0, 0, radius, dialPaint);
    }

    private void drawScale(Canvas canvas) {
        for (int i = 1; i <= 12; i++) {
            double angle = Math.toRadians(i * 30);
            canvas.drawLine((radius - dialWidth / 2) * (float) (Math.sin(angle)), (radius - dialWidth / 2) * (float) (Math.cos(angle)), (radius - scaleLength - dialWidth / 2) * (float) (Math.sin(angle)), (radius - scaleLength - dialWidth / 2) * (float) (Math.cos(angle)), scalePaint);
        }
    }

    private void drawHour(Canvas canvas) {
        canvas.drawLine(0, 0, 0, radius / 2, hourPaint);
    }

    private void drawMinute(Canvas canvas) {
        canvas.drawLine(0, 0, 0, -radius + LibUtils.dip2px(mContext, 20), minutePaint);
    }

    private void drawSecond(Canvas canvas) {
        canvas.drawLine(0, 0, radius - LibUtils.dip2px(mContext, 10), 0, secondsPaint);
    }

}
