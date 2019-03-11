package com.example.chart.bar_chart;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import static com.example.chart.LibUtils.dip2px;

public class BarChartView extends View {
    private Context mContext;

    private float width = 0;
    private float height = 0;
    private float left = 0;
    private float right = 0;
    private float top = 0;
    private float bottom;
    private float startX = 0;
    private float startY = 0;
    private float endX = 0;
    private float endY = 0;
    private float baseY = 0;
    private Paint xPaint = new Paint();
    private Paint yPaint = new Paint();
    private Paint barPaint = new Paint();
    private Paint textPaint = new Paint();
    private Paint axisTextPaint = new Paint();
    private Paint selectPaint = new Paint();
    private List<BarChartBean.DataBean> datas = new ArrayList<>();
    private Rect rect = new Rect();
    private double maxNum = 0;
    private float barWidth;
    private int yScale = 5;
    private int index = -1;
    private BarChartBean.SelectType selectType;
    private float averageX = 0;
    private boolean clickEnable = true;
    private int widthSize;
    private int heightSize;


    public BarChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        xPaint.setAntiAlias(true);
        xPaint.setStyle(Paint.Style.FILL);
        xPaint.setColor(Color.BLACK);
        yPaint.setAntiAlias(true);
        yPaint.setStyle(Paint.Style.FILL);
        yPaint.setColor(Color.BLACK);
        barPaint.setAntiAlias(true);
        barPaint.setStyle(Paint.Style.FILL);
        barPaint.setColor(Color.BLACK);
        textPaint.setAntiAlias(true);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(dip2px(mContext, 12));
        selectPaint.setColor(Color.BLACK);
        selectPaint.setAntiAlias(true);
        selectPaint.setStyle(Paint.Style.STROKE);
        selectPaint.setStrokeWidth(dip2px(context, 2));
        axisTextPaint.setAntiAlias(true);
        axisTextPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        widthSize = MeasureSpec.getSize(widthMeasureSpec);
        heightSize = MeasureSpec.getSize(heightMeasureSpec);
        Log.d("onMeasure", "wid   " + widthSize + "hei   " + heightSize);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.d("onLayout", "changed " + changed + "left   " + left + "top   " + top + "right   " + right + "bottom   " + bottom);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.width = w;
        this.height = h;
        this.right = w;
        this.bottom = h;
        this.startX = dip2px(mContext, 50);
        this.startY = height;
        this.endX = width - dip2px(mContext, 15);
        this.endY = dip2px(mContext, 10);
        baseY = startY - dip2px(mContext, 30);
        averageX = (endX - startX) / (datas.size() + 1);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float paddingLeft = getPaddingLeft();
        float paddingRight = getPaddingRight();
        float paddingTop = getPaddingTop();
        float paddingBottom = getPaddingBottom();
        float realWidth = getWidth() - getPaddingLeft() - getPaddingRight();
        float realHeight = getHeight() - getPaddingTop() - getPaddingBottom();
//        this.startX = startX + getPaddingLeft();
//        this.endX = endX - getPaddingRight();
//        this.startY = startY - getPaddingBottom();
//        this.endY = endY + getPaddingTop();
//        this.baseY = startY - dip2px(mContext, 30);
        drawX(canvas);
        drawY(canvas);
        drawBar(canvas);
    }

    private void drawX(Canvas canvas) {
        canvas.drawLine(startX, baseY, endX, baseY, xPaint);
        for (int i = 0; i < datas.size(); i++) {
            textPaint.getTextBounds(String.valueOf(i), 0, String.valueOf(i).length(), rect);
            float textWidth = rect.width();
            float textHeight = rect.height();
            canvas.drawLine(startX + averageX * (i + 1), baseY, startX + averageX * (i + 1), baseY + dip2px(mContext, 5), xPaint);
            canvas.drawText("" + (i + 1), startX + averageX * (i + 1) - textWidth / 2, startY - textHeight / 2, axisTextPaint);
        }
    }

    private void drawY(Canvas canvas) {
        float averageY = (baseY - endY) / yScale;
        double averageNum = maxNum / yScale;
        canvas.drawLine(startX, endY, startX, baseY, yPaint);
        for (int i = 0; i <= yScale; i++) {
            textPaint.getTextBounds(String.valueOf(i), 0, String.valueOf(i).length(), rect);
            float textWidth = rect.width();
            float textHeight = rect.height();
            canvas.drawText(String.valueOf(averageNum * i), textWidth / 2, baseY - averageY * i + textHeight / 2, axisTextPaint);
            canvas.drawLine(startX, baseY - i * averageY, startX - dip2px(mContext, 5), baseY - i * averageY, yPaint);
        }
    }

    private void drawBar(Canvas canvas) {
        float averageY = (baseY - endY);
        textPaint.setTextSize(dip2px(mContext, 10));
        for (int i = 0; i < datas.size(); i++) {
            textPaint.getTextBounds(String.valueOf(datas.get(i).getNum()), 0, String.valueOf(datas.get(i).getNum()).length(), rect);
            float textWidth = rect.width();
            float textHeight = rect.height();
            textPaint.setColor(mContext.getResources().getColor(datas.get(i).getColor()));
            barPaint.setColor(mContext.getResources().getColor(datas.get(i).getColor()));
            if (index == i) {
                if (clickEnable) {
                    switch (selectType) {
                        case zoom:
                            canvas.drawRect(startX + averageX * (i + 1) - barWidth - dip2px(mContext, 3), (float) (baseY - (float) (datas.get(i).getNum()) / maxNum * averageY) - dip2px(mContext, 3), startX + averageX * (i + 1) + barWidth + dip2px(mContext, 3), baseY, barPaint);
                            break;
                        case round:
                            Path path = new Path();
                            path.rMoveTo(startX + averageX * (i + 1) - barWidth, baseY);
                            path.lineTo(startX + averageX * (i + 1) - barWidth, (float) (baseY - (float) (datas.get(i).getNum()) / maxNum * averageY));
                            path.lineTo(startX + averageX * (i + 1) + barWidth, (float) (baseY - (float) (datas.get(i).getNum()) / maxNum * averageY));
                            path.lineTo(startX + averageX * (i + 1) + barWidth, baseY);
                            canvas.drawPath(path, selectPaint);
                            canvas.drawRect(startX + averageX * (i + 1) - barWidth, (float) (baseY - (float) (datas.get(i).getNum()) / maxNum * averageY), startX + averageX * (i + 1) + barWidth, baseY, barPaint);
                            break;
                    }
                    canvas.drawText(String.valueOf(datas.get(i).getNum()), startX + averageX * (i + 1) - textWidth / 2, (float) (baseY - (float) (datas.get(i).getNum()) / maxNum * averageY) - textHeight / 2 - dip2px(mContext, 3) - dip2px(mContext, 3), textPaint);
                }

            } else {
                canvas.drawRect(startX + averageX * (i + 1) - barWidth, (float) (baseY - (float) (datas.get(i).getNum()) / maxNum * averageY), startX + averageX * (i + 1) + barWidth, baseY, barPaint);
                canvas.drawText(String.valueOf(datas.get(i).getNum()), startX + averageX * (i + 1) - textWidth / 2, (float) (baseY - (float) (datas.get(i).getNum()) / maxNum * averageY) - textHeight / 2, textPaint);
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float averageX = (endX - startX) / (datas.size() + 1);
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_DOWN:
                float x = event.getX();
                float y = event.getY();
                for (int i = 0; i < datas.size(); i++) {
                    if (x >= startX + averageX * (i + 1) - barWidth && x <= startX + averageX * (i + 1) + barWidth && y <= baseY && y >= (float) (baseY - (float) (datas.get(i).getNum()) / maxNum * (baseY - endY))) {
                        index = i;
                    }
                }
                invalidate();
                return true;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onTouchEvent(event);
    }

    public void setData(BarChartBean barChartBean) {
        this.datas.clear();
        this.datas.addAll(barChartBean.getDatas());
        for (BarChartBean.DataBean value : barChartBean.getDatas()) {
            if (value.getNum() > this.maxNum) this.maxNum = value.getNum();
        }
        this.maxNum = this.maxNum + 3;
        this.yScale = barChartBean.getyScale();
        this.barWidth = dip2px(mContext, barChartBean.getBarWidth()) / 2;
        this.selectType = barChartBean.getSelectType();
        this.axisTextPaint.setColor(barChartBean.getAxisTextColor() == Color.BLACK ? Color.BLACK : mContext.getResources().getColor(barChartBean.getAxisTextColor()));
        this.axisTextPaint.setTextSize(dip2px(mContext, barChartBean.getAxisTextSize()));
        this.clickEnable = barChartBean.isClickEnable();
        this.selectType = barChartBean.getSelectType() == null ? BarChartBean.SelectType.zoom : barChartBean.getSelectType();
    }
}
