package com.example.chart.line_chart;

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
import android.widget.Toast;

import java.util.ArrayList;
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
    private Rect rect = new Rect();
    private double maxNum = 0;
    private List<LineChartBean.DataBean> datas = new ArrayList<>();
    private double xAxis = 0;
    private double yAxis = 0;
    private double xAxisMaxNum = 0;
    private double yAxisMaxNum = 0;
    private int index = -1;
    private float itemHeight = 0;
    private Paint selectPaint = new Paint();

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
            canvas.drawText("" + (averageNum * i), startX + averageX * i - textWidth / 2, startY - textHeight / 2, textPaint);
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
            canvas.drawText(String.valueOf(averageNum * i), textWidth / 2, baseY - averageY * i + textHeight / 2, textPaint);
        }
    }

    private void drawLine(Canvas canvas) {
        linePaint.setStrokeWidth(dip2px(mContext, 2));
        float averageX = (endX - startX) / (datas.size() - 1);
        double averageYNum = maxNum / datas.size();
        Path path = new Path();
        path.rMoveTo(startX, (float) (baseY - datas.get(0).getNum() / averageYNum * itemHeight));
        for (int i = 0; i < datas.size(); i++) {
            path.lineTo(startX + i * averageX, (float) (baseY - (datas.get(i).getNum() / averageYNum) * itemHeight));
            if (index == i) {
                canvas.drawLine(startX + i * averageX, baseY, startX + i * averageX, endY, selectPaint);
            }
        }
        canvas.drawPath(path, linePaint);
    }


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
                    if (x <= startX + i * averageX + dip2px(mContext, 8) && x >= startX + i * averageX - dip2px(mContext, 8)) {
                        if (y <= (float) (baseY - (datas.get(i).getNum() / averageYNum) * itemHeight) + dip2px(mContext, 8) && y >= (float) (baseY - (datas.get(i).getNum() / averageYNum) * itemHeight) - dip2px(mContext, 8))
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

    public void setData(LineChartBean lineChartBean) {
        this.datas.clear();
        this.datas.addAll(lineChartBean.getDatas());
        for (LineChartBean.DataBean value : lineChartBean.getDatas()) {
            if (value.getNum() > maxNum) maxNum = value.getNum();
        }
        maxNum = maxNum + 3;
        this.xAxis = lineChartBean.getxAxis();
        this.yAxis = lineChartBean.getyAxis();
        this.xAxisMaxNum = lineChartBean.getxAxisMaxNum();
        this.yAxisMaxNum = lineChartBean.getyAxisMaxNum();
        selectPaint.setStrokeWidth(dip2px(mContext,lineChartBean.getSelectPaintWidth()));
        selectPaint.setColor(lineChartBean.getSelectPaintColor());
    }

}
