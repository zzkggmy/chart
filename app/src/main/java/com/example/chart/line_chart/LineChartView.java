package com.example.chart.line_chart;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.chart.XAxisFormat;
import com.example.chart.YAxisFormat;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

import static com.example.chart.LibUtils.dip2px;

public class LineChartView extends View {

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
    private Paint linePaint = new Paint();
    private Paint textPaint = new Paint();
    private Paint mkPaint = new Paint();
    private Paint otherLinePaint = new Paint();
    private Rect rect = new Rect();
    private double maxNum = 0;
    private List<Double> datas = new ArrayList<>();
    private List<LineChartBean.OtherDataBean> othersData = new ArrayList<>();
    private double xAxis = 0;
    private double yAxis = 0;
    private double xAxisMaxNum = 0;
    private double yAxisMaxNum = 0;
    private int index = -1;
    private float itemHeight = 0;
    private Paint selectPaint = new Paint();
    private XAxisFormat xAxisFormat;
    private YAxisFormat yAxisFormat;
    private LineChartBean.SelectType selectType;
    private boolean clickEnable = true;
    private float touchX = -100;
    private float touchY = -100;


    public LineChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        xPaint.setAntiAlias(true);
        xPaint.setStyle(Paint.Style.FILL);
        xPaint.setColor(Color.BLACK);
        yPaint.setAntiAlias(true);
        yPaint.setStyle(Paint.Style.FILL);
        yPaint.setColor(Color.BLACK);
        linePaint.setAntiAlias(true);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setColor(Color.RED);
        textPaint.setAntiAlias(true);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(dip2px(mContext, 12));
        selectPaint.setAntiAlias(true);
        mkPaint.setAntiAlias(true);
        mkPaint.setStyle(Paint.Style.STROKE);
        mkPaint.setColor(Color.GRAY);
        otherLinePaint.setAntiAlias(true);
        otherLinePaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
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
        this.endY = dip2px(mContext, 20);
        baseY = height - dip2px(mContext, 30);
        itemHeight = (baseY - endY) / datas.size();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawX(canvas);
        drawY(canvas);
        drawLine(canvas);
    }

    private void drawX(Canvas canvas) {
        float averageX = (float) ((endX - startX) / xAxis);
        double averageNum = xAxisMaxNum / xAxis;
        canvas.drawLine(startX, baseY, endX, baseY, xPaint);
        for (int i = 1; i < xAxis; i++) {
            textPaint.getTextBounds(String.valueOf(i), 0, String.valueOf(i).length(), rect);
            float textWidth = rect.width();
            float textHeight = rect.height();
            canvas.drawLine(startX + averageX * i, baseY, startX + averageX * i, baseY + dip2px(mContext, 5), xPaint);
            canvas.drawText(roundFormat(averageNum * i), startX + averageX * i - textWidth / 2, startY - textHeight / 2, textPaint);
        }
    }

    private void drawY(Canvas canvas) {
        float averageY = (baseY - endY) / (float) yAxis;
        double averageNum = maxNum / (yAxis);
        canvas.drawLine(startX, endY, startX, baseY, yPaint);
        for (int i = 0; i <= yAxis; i++) {
            textPaint.getTextBounds(String.valueOf(i), 0, String.valueOf(i).length(), rect);
            float textWidth = rect.width();
            float textHeight = rect.height();
            canvas.drawLine(startX, baseY - averageY * i, startX - dip2px(mContext, 5), baseY - averageY * i, yPaint);
            canvas.drawText(roundFormat(averageNum * i), textWidth / 2, baseY - averageY * i + textHeight / 2, textPaint);
        }
    }

    public void xAxisFormat(XAxisFormat xAxisFormat) {
        if (this.xAxisFormat == null) {
            this.xAxisFormat = this::roundFormat;
        } else {
            this.xAxisFormat = xAxisFormat;
        }
    }

    public void yAxisFormat(YAxisFormat yAxisFormat) {
        if (this.yAxisFormat == null) {
            this.yAxisFormat = this::roundFormat;
        } else {
            this.yAxisFormat = yAxisFormat;
        }
    }

    private void drawLine(Canvas canvas) {
        linePaint.setStrokeWidth(dip2px(mContext, 2));
        float averageX = (endX - startX) / (datas.size() - 1);
        double averageYNum = maxNum / datas.size();
        Path path = new Path();
        path.rMoveTo(startX, (float) (baseY - datas.get(0) / averageYNum * itemHeight));
        for (int i = 0; i < datas.size(); i++) {
            path.lineTo(startX + i * averageX, (float) (baseY - (datas.get(i) / averageYNum) * itemHeight));
            if (clickEnable) {
                switch (selectType) {
                    case all:
                        textPaint.getTextBounds(roundFormat((baseY - touchY) / (baseY - endY) * maxNum), 0, roundFormat((baseY - touchY) / (baseY - endY) * maxNum).length(), rect);
                        float allTextWidth = rect.width();
                        float allTextHeight = rect.height();
                        canvas.drawText(roundFormat((baseY - touchY) / (baseY - endY) * maxNum), touchX - allTextWidth / 2, endY + allTextHeight / 2 * 3, textPaint);
                        canvas.drawRect(touchX - allTextWidth, endY, touchX + allTextWidth, endY + 2 * allTextHeight, mkPaint);
                        canvas.drawLine(touchX, baseY, touchX, endY + allTextHeight * 2, selectPaint);
                        break;
                    case point:
                        if (index == i) {
                            textPaint.getTextBounds(String.valueOf(datas.get(i)), 0, String.valueOf(datas.get(i)).length(), rect);
                            float pointTextWidth = rect.width();
                            float pointTextHeight = rect.height();
                            canvas.drawRoundRect(startX + i * averageX - pointTextWidth, endY, startX + i * averageX + pointTextWidth, endY + pointTextHeight * 2, dip2px(mContext, 10), dip2px(mContext, 10), mkPaint);
                            canvas.drawText(String.valueOf(datas.get(i)), startX + i * averageX - pointTextWidth / 2, endY + pointTextHeight / 2 * 3, selectPaint);
                            canvas.drawLine(startX + i * averageX, baseY, startX + i * averageX, endY + pointTextHeight * 2, selectPaint);
                        }
                        break;
                }
            }
        }
        canvas.drawPath(path, linePaint);
        drawOthersLine(canvas, averageX, averageYNum);
    }

    private void drawOthersLine(Canvas canvas, float averageX, double averageYNum) {
        for (int i = 0; i < othersData.size(); i++) {
            otherLinePaint.setColor(mContext.getResources().getColor(othersData.get(i).getColor()));
            Path path = new Path();
            path.rMoveTo(startX, (float) (baseY - othersData.get(i).getOthersNumList().get(0) / averageYNum * itemHeight));
            for (int j = 0; j < othersData.get(i).getOthersNumList().size(); j++) {
                path.lineTo(startX + j * averageX, (float) (baseY - (othersData.get(i).getOthersNumList().get(j) / averageYNum) * itemHeight));
            }
            canvas.drawPath(path, otherLinePaint);
        }
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float averageX = (endX - startX) / (datas.size() - 1);
        double averageYNum = maxNum / datas.size();
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_DOWN:
                float x = event.getX();
                float y = event.getY();
                for (int i = 0; i < datas.size(); i++) {
                    switch (selectType) {
                        case point:
                            if (x <= startX + i * averageX + dip2px(mContext, 8) && x >= startX + i * averageX - dip2px(mContext, 8)) {
                                if (y <= (float) (baseY - (datas.get(i) / averageYNum) * itemHeight) + dip2px(mContext, 8) && y >= (float) (baseY - (datas.get(i) / averageYNum) * itemHeight) - dip2px(mContext, 8))
                                    index = i;
                            }
                            break;
                        case all:
                            if (x >= startX && x <= endX) {
                                if (y >= endY && y <= baseY) {
                                    touchX = x;
                                    touchY = y;
                                }
                            }
                            break;
                    }
                }
                invalidate();
                return true;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onTouchEvent(event);
    }

    public void setData(LineChartBean lineChartBean) {
        this.datas.clear();
        this.datas.addAll(lineChartBean.getDatas());
        for (Double value : lineChartBean.getDatas()) {
            if (value > maxNum) maxNum = value;
        }
        this.othersData.clear();
        this.othersData.addAll(lineChartBean.getOtherData());
        for (LineChartBean.OtherDataBean listBean : lineChartBean.getOtherData()) {
            for (Double otherValue : listBean.getOthersNumList()) {
                if (otherValue >= maxNum) maxNum = otherValue;
            }
        }
        maxNum = maxNum + 3;
        this.xAxis = lineChartBean.getxAxis();
        this.yAxis = lineChartBean.getyAxis();
        this.xAxisMaxNum = lineChartBean.getxAxisMaxNum();
        this.yAxisMaxNum = lineChartBean.getyAxisMaxNum();
        linePaint.setColor(mContext.getResources().getColor(lineChartBean.getLineColor()));
        selectPaint.setStrokeWidth(dip2px(mContext, lineChartBean.getSelectPaintWidth()));
        selectPaint.setColor(mContext.getResources().getColor(lineChartBean.getSelectPaintColor()));
        this.selectType = lineChartBean.getSelectType() == null ? LineChartBean.SelectType.point : lineChartBean.getSelectType();
        this.clickEnable = lineChartBean.isClickEnable();
    }

    private String roundFormat(Double value) {
        BigDecimal b = new BigDecimal(value);
        return b.setScale(1, RoundingMode.HALF_UP).toString();
    }
}