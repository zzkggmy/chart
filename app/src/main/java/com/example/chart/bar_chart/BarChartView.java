package com.example.chart.bar_chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.chart.pie_chart.BarChartBean;

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
    private List<BarChartBean.DataBean> datas = new ArrayList<>();
    private Rect rect = new Rect();
    private double maxNum = 0;
    private float barWidth = 0;


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
        textPaint.setTextSize(dip2px(mContext,12));
        barWidth = dip2px(mContext,20);
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
        this.startX = dip2px(mContext,50);
        this.startY = height;
        this.endX = width - dip2px(mContext,15);
        this.endY = dip2px(mContext,10);
        baseY = height - dip2px(mContext,30);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawX(canvas);
        drawY(canvas);
        drawBar(canvas);
    }

    private void drawX(Canvas canvas) {
        float averageX = (endX - startX) / (datas.size() + 1);
        canvas.drawLine(startX, baseY, endX, baseY, xPaint);
        for (int i = 0; i < datas.size(); i++) {
            textPaint.getTextBounds(String.valueOf(i), 0, String.valueOf(i).length(), rect);
            float textWidth = rect.width();
            float textHeight = rect.height();
            canvas.drawLine(startX + averageX * (i + 1), baseY, startX + averageX * (i + 1), baseY + dip2px(mContext,5), xPaint);
            canvas.drawText("" + (i + 1), startX + averageX * (i + 1) - textWidth / 2, startY - textHeight / 2, textPaint);
        }
    }

    private void drawY(Canvas canvas) {
        float averageY = (baseY - endY) / 5;
        double averageNum = maxNum / 5;
        canvas.drawLine(startX, endY, startX, baseY, yPaint);
        for (int i = 0; i <= 5; i++) {
            textPaint.getTextBounds(String.valueOf(i), 0, String.valueOf(i).length(), rect);
            float textWidth = rect.width();
            float textHeight = rect.height();
            canvas.drawText(String.valueOf(averageNum * i), textWidth / 2, baseY - averageY * i + textHeight / 2, textPaint);
            canvas.drawLine(startX, baseY - i * averageY, startX - dip2px(mContext,5), baseY - i * averageY, yPaint);
        }
    }

    private void drawBar(Canvas canvas) {
        float averageX = (endX - startX) / (datas.size() + 1);
        float averageY = (baseY - endY);
        textPaint.setTextSize(dip2px(mContext,10));
        for (int i = 0; i < datas.size(); i++) {
            textPaint.getTextBounds(String.valueOf(datas.get(i).getNum()), 0, String.valueOf(datas.get(i).getNum()).length(), rect);
            float textWidth = rect.width();
            float textHeight = rect.height();
            textPaint.setColor(mContext.getResources().getColor(datas.get(i).getColor()));
            barPaint.setColor(mContext.getResources().getColor(datas.get(i).getColor()));
            canvas.drawRect(startX + averageX * (i + 1) - barWidth / 2, (float) (baseY - (float) (datas.get(i).getNum()) / maxNum * averageY), startX + averageX * (i + 1) + barWidth / 2, baseY, barPaint);
            canvas.drawText(String.valueOf(datas.get(i).getNum()),startX + averageX * (i + 1) - textWidth / 2,(float) (baseY - (float) (datas.get(i).getNum()) / maxNum * averageY) - textHeight / 2,textPaint);
        }
    }

    public void setData(BarChartBean barChartBean) {
        this.datas.clear();
        this.datas.addAll(barChartBean.getDatas());
        for (BarChartBean.DataBean value : barChartBean.getDatas()) {
            if (value.getNum() > maxNum) maxNum = value.getNum();
        }
        maxNum = maxNum + 3;
    }
}
