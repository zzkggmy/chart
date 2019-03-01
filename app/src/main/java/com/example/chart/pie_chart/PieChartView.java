package com.example.chart.pie_chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.PI;

public class PieChartView extends View {
    private Context mContext;
    private float left = 0f;
    private float right = 0f;
    private float top = 0f;
    private float bottom = 0f;

    private float width = 0f;
    private float height = 0f;
    private Paint paint = new Paint();
    private Paint withinPaint = new Paint();
    private Paint textPaint = new Paint();
    private Paint circlePaint = new Paint();
    private Paint dividePaint = new Paint();

    private float allNum = 0F;
    private List<ViewData> datas = new ArrayList<>();
    private boolean select = false;
    private float radius = 0;
    private List<PieBaseData> pieList = new ArrayList<>();
    private float bigRadius = 0;
    private float circleRadius = 0;
    private float divideNum = 3;

    public PieChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        withinPaint.setAntiAlias(true);
        withinPaint.setStyle(Paint.Style.FILL);
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.BLACK);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(20f);
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(Color.WHITE);
        circlePaint.setStyle(Paint.Style.FILL);
        dividePaint.setAntiAlias(true);
        dividePaint.setColor(Color.TRANSPARENT);
        dividePaint.setStyle(Paint.Style.FILL);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        this.left = left;
        this.right = right;
        this.bottom = bottom;
        this.top = top;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.width = w;
        this.height = h;
        bigRadius = (Math.min(w, h)) / 2;
        radius = bigRadius - 30;
        circleRadius = radius / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawPieChart(canvas);
    }

    private void drawPieChart(Canvas canvas) {
        canvas.translate(width / 2, height / 2);
        RectF oval = new RectF(-radius, -radius, radius, radius);
        RectF oval1 = new RectF(-bigRadius, -bigRadius, bigRadius, bigRadius);
        RectF oval2 = new RectF(-circleRadius - 25, -circleRadius - 25, circleRadius + 25, circleRadius + 25);
        float startAngle = 0;
        float sweepAngle;
        float textAngle;
        for (int i = 0; i < datas.size(); i++) {
            withinPaint.setColor(mContext.getResources().getColor(datas.get(i).getColor()));
            paint.setColor(mContext.getResources().getColor(datas.get(i).getColor()));
            canvas.drawArc(oval, startAngle, divideNum, true, dividePaint);
            startAngle += divideNum;
            sweepAngle = (float) (datas.get(i).getPercent() / allNum * (360 - datas.size() * divideNum));
            PieBaseData pieBaseData = new PieBaseData();
            pieBaseData.setStartAngle(startAngle);
            pieBaseData.setSweepAngle(sweepAngle);
            pieList.add(pieBaseData);
            paint.setAlpha(225);
            if (select)
                canvas.drawArc(oval1, startAngle, sweepAngle, true, paint);
            else
                canvas.drawArc(oval, startAngle, sweepAngle, true, paint);
            withinPaint.setAlpha(10);
            canvas.drawArc(oval2, startAngle, sweepAngle, true, withinPaint);
//            //文字角度
//            textAngle = startAngle + sweepAngle / 2;
//            float x = (float) (radius * Math.cos(textAngle * PI / 180));    //计算文字位置坐标
//            float y = (float) (radius * Math.sin(textAngle * PI / 180));
////            textAngle = startAngle + sweepAngle / 2;
////            getTextPoint(textAngle, canvas, "这是第" + (i + 1) + "条数据");
//            canvas.drawText("这是第" + (i + 1) + "条数据",x / 2,y,textPaint);
            startAngle += sweepAngle;
        }
        canvas.drawCircle(0, 0, circleRadius, circlePaint);
    }

    public void setResource(List<ViewData> data) {
        this.datas.clear();
        this.datas.addAll(data);
        for (ViewData bean : data) {
            allNum += bean.getPercent();
        }
    }


    public void getTextPoint(float degree, Canvas canvas, String text) {
        float dx;
        float dy;
//        if (degree >= 0 && degree <= 90) {
//            dx = (float) (radius * 2.3 / 3 * Math.cos(radius * PI / 180 * degree));//注意Math.sin(x)中x为弧度值，并非数学中的角度，所以需要将角度转换为弧度
//            dy = (float) (radius * 2.7 / 3 * Math.sin(radius * PI / 180 * degree));
//        } else if (degree > 90 && degree <= 180) {
//            dx = (float) -(radius * 2.3 / 3 * Math.cos(radius * PI / 180 * (180f - degree)));
//            dy = (float) (radius * 2.7 / 3 * Math.sin(radius * PI / 180 * (180f - degree)));
//        } else if (degree > 180 && degree <= 270) {
//            dx = (float) -(radius * 2.3 / 3 * Math.cos(radius * PI / 180 * (270f - degree)));
//            dy = (float) -(radius * 2.7 / 3 * Math.sin(radius * PI / 180 * (270f - degree)));
//        } else {
//            dx = (float) (radius * 2.3 / 3 * Math.cos(radius * PI / 180 * (360f - degree)));
//            dy = (float) -(radius * 2.7 / 3 * Math.sin(radius * PI / 180 * (360f - degree)));
//        }
        dx = (float) (radius * 2.3 / 3 * Math.cos(radius * PI / 180 * degree));
        dy = (float) (radius * 2.7 / 3 * Math.sin(radius * PI / 180 * degree));
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(30f);
        canvas.drawText(text, dx, dy, textPaint);
//            switch ((int) degree / 90) {
//                //第一象限内
//                case 0:
//                    p.x = (radius * (float) Math.cos(Math.toRadians(degree)));
//                    p.y = (radius * (float) Math.sin(Math.toRadians(degree)));
//                    break;
//                //第二象限内
//                case 1:
//                    p.x = (radius * (float) Math.sin(Math.toRadians(degree - 90)));
//                    p.y = (radius * (float) Math.cos(Math.toRadians(degree - 90)));
//                    break;
//                //第三象限内
//                case 2:
//                    p.x = (radius * (float) Math.cos(Math.toRadians(degree - 180)));
//                    p.y = (radius * (float) Math.sin(Math.toRadians(degree - 180)));
//                    break;
//                //第四象限内
//                case 3:
//                    p.x = radius * (float) Math.sin(Math.toRadians(degree - 270));
//                    p.y = radius * (float) Math.cos(Math.toRadians(degree - 270));
//                    break;
//            }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        if (x >= 0 && y >= 0) {
            select = !select;
            Toast.makeText(mContext, "第一象限", Toast.LENGTH_SHORT).show();
            invalidate();
        }
        return super.onTouchEvent(event);

    }
}
