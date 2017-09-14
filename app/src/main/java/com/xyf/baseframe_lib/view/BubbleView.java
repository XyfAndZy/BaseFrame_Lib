package com.xyf.baseframe_lib.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.xyf.baseframe_lib.utlis.DisplayUtil;

/**
 * Description: <br/>
 * Author:XYF<br/>
 * Date:2017/8/15 11:19
 */
/*
   实现思路:
   1.手指按下的时候,绘制出两个圆(固定圆和拖拽圆)
       固定圆的圆心位置固定,但是半径可发生变化
       拖拽圆的圆心可变,半径固定
   2.手指拖动的时候,不断更新拖拽圆的位置(不断的绘制),
       同时改变固定圆的圆心大小(两个圆越近,固定圆半径越大;两圆越远,固定圆的半径越小;
       两圆距离超过一定值时,固定圆消失不见
*/

public class BubbleView extends View {
    private static final String TAG = "BubbleView";
    private Paint mPaint;
    //拖拽圆半径
    private float mDragRadius = 9;
    // 固定圆最大半径(初始半径)/半径的最小值
    private float mFixationRadiusMax = 12;
    private float mFixationRadiusMin = 3;
    // 两个实心圆--根据点的坐标来绘制圆;
    private PointF mFixationPoint;
    private PointF mDragPoint;
    private int mFixationRadius;

    public BubbleView(Context context) {
        this(context, null);
    }

    public BubbleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BubbleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint(context);
    }

    private void initPaint(Context context) {
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mDragRadius = DisplayUtil.dip2px(context, mDragRadius);
        mFixationRadiusMax = DisplayUtil.dip2px(context, mFixationRadiusMax);
        mFixationRadiusMin = DisplayUtil.dip2px(context, mFixationRadiusMin);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //手指按下的时候，在当前位置绘制两个圆
                float downX = event.getX();
                float downY = event.getY();
                initPoint(downX, downY);
                break;
            case MotionEvent.ACTION_MOVE:
                //移动的时候，更新位置
                float moveX = event.getX();
                float moveY = event.getY();
                updateDragPointLocation(moveX, moveY);
                break;
        }
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mFixationPoint == null || mDragPoint == null)
            return;
        // 画两个圆: 固定圆有一个初始化大小,而且随着两圆距离的增大而变小,小到一定程度就不见了(不画了)
        // 拖拽圆 半径不变,位置跟随手指移动
        canvas.drawCircle(mDragPoint.x, mDragPoint.y, mDragRadius, mPaint);
        //绘制贝塞尔曲线，如果两圆拖拽到一定距离，固定圆消失的同时不再绘制贝塞尔曲线
        Path besaierPath = getBesaierPath();
        if (besaierPath != null) {
            // 固定圆半径可变 当拖拽在一定距离时才去绘制,超过一定距离就不在绘制
            canvas.drawCircle(mFixationPoint.x, mFixationPoint.y, mFixationRadius, mPaint);
            canvas.drawPath(besaierPath, mPaint);
        }
    }

    private void initPoint(float downX, float downY) {
        mFixationPoint = new PointF(downX, downY);
        mDragPoint = new PointF(downX, downY);
    }

    private void updateDragPointLocation(float moveX, float moveY) {
        mDragPoint.x = moveX;
        mDragPoint.y = moveY;
    }

    private Path getBesaierPath() {
        double distance = Math.sqrt((mDragPoint.x - mFixationPoint.x) * (mDragPoint.x - mFixationPoint.x)
                - (mDragPoint.y - mFixationPoint.y) * (mDragPoint.y - mFixationPoint.y));
        //随着拖拽的距离变化，逐渐改变固定圆的半径
        mFixationRadius = (int) (mFixationRadiusMax - distance / 14);
        if (mFixationRadius < mFixationRadiusMin)
            // 超过一定距离  贝塞尔曲线和固定圆都不要绘制了
            return null;
        Path besaierPath = new Path();
        //求角度
        double angleA = Math.atan((mDragPoint.y - mFixationPoint.y) / (mDragPoint.x - mFixationPoint.x));
        float P0x = (float) (mFixationPoint.x + mFixationRadius * Math.sin(angleA));
        float P0y = (float) (mFixationPoint.y - mFixationRadius * Math.cos(angleA));

        float P3x = (float) (mFixationPoint.x - mFixationRadius * Math.sin(angleA));
        float P3y = (float) (mFixationPoint.y + mFixationRadius * Math.cos(angleA));

        float P1x = (float) (mDragPoint.x + mDragRadius * Math.sin(angleA));
        float P1y = (float) (mDragPoint.y - mDragRadius * Math.cos(angleA));

        float P2x = (float) (mDragPoint.x - mDragRadius * Math.sin(angleA));
        float P2y = (float) (mDragPoint.y + mDragRadius * Math.cos(angleA));
        //拼接贝塞尔曲线,移动到我们的起始点，否则默认（0，0）开始
        besaierPath.moveTo(P0x, P0y);
        //求控制点坐标，取两圆圆心为控制点
        PointF controlPoint = new PointF((mDragPoint.x + mFixationPoint.x) / 2, (mDragPoint.y + mFixationPoint.y) / 2);
        besaierPath.quadTo(controlPoint.x, controlPoint.y, P1x, P1y);
        besaierPath.lineTo(P2x, P2y);
        besaierPath.quadTo(controlPoint.x, controlPoint.y, P3x, P3y);
        besaierPath.close();
        return besaierPath;
    }

    public static void setOnViewListener(View view){

    }


}
