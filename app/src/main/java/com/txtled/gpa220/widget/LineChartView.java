package com.txtled.gpa220.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.txtled.gpa220.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr.Quan on 2020/3/31.
 */
public class LineChartView extends View {
    private float mViewHeight, mViewWidth, needDrawHeight, needDrawWidth;
    private float mBrokenLineLeft, mBrokenLineRight, mBrokenLineTop, mBrokenLineBottom;
    private Paint mTextPaint, mBorderLinePaint, mBrokenLinePaint, mCirclePaint, mFloatPaint;
    private Context mContext;
    private int textColor, lineColor, canvasColor, circleColor;
    private float textWidth, textHeight, circleWidth, circleHeight;
    private float textSize, averageHeight, xWidth, firstLineHeight;
    private Float[] valueText = {42f, 41f, 40f, 39f, 38f, 37f, 36f, 35f, 34f};
    private List<Float> data = new ArrayList<>();
    private Point[] points;
    private GestureDetector gestureDetector;
    private float scrollX;
    private float fullWidth, canScrollWidth;
    private int position;
    private boolean initData;//控制是否滚动到最末端

    public LineChartView(Context context) {
        this(context, null, 0);
    }

    public LineChartView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        gestureDetector = new GestureDetector(context, new MyOnGestureListener());
        TypedArray typedArray = context.getTheme().
                obtainStyledAttributes(attrs, R.styleable.LineChart, defStyleAttr, 0);
        for (int i = 0; i < typedArray.getIndexCount(); i++) {
            switch (typedArray.getIndex(i)) {
                case R.styleable.LineChart_text_color:
                    textColor = typedArray.getColor(typedArray.getIndex(i), Color.BLACK);
                    break;
                case R.styleable.LineChart_line_color:
                    lineColor = typedArray.getColor(typedArray.getIndex(i), Color.BLACK);
                    break;
                case R.styleable.LineChart_circle_color:
                    circleColor = typedArray.getColor(typedArray.getIndex(i), Color.BLACK);
                    break;
                case R.styleable.LineChart_canvas_color:
                    canvasColor = typedArray.getColor(typedArray.getIndex(i), Color.WHITE);
                    break;
                case R.styleable.LineChart_text_size:
                    textSize = typedArray.getDimension(typedArray.getIndex(i), 14);
                    break;
            }
        }
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mViewHeight = getMeasuredHeight();
        mViewWidth = getMeasuredWidth();
        getDrawWidthAndHeight();
        initPaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mBrokenLineLeft = getPaddingLeft();
        mBrokenLineRight = getPaddingRight();
        mBrokenLineBottom = getPaddingBottom();
        mBrokenLineTop = getPaddingTop();
        drawXYAndText(canvas);

        if (data.size() != 0) {
            getPoint(data);
            fullWidth = points[points.length - 1].x + (xWidth / 2 * 3) - scrollX;
            canScrollWidth = fullWidth - mViewWidth;
            if (initData){
                if (data.size() > 7){
                    scrollX = -canScrollWidth;
                    fullWidth = points[points.length - 1].x + (xWidth / 2 * 3) - scrollX;
                    canScrollWidth = fullWidth - mViewWidth;
                    initData = false;
                }
            }
        }
        drawLineCircle(canvas);
        showText(position, canvas);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (changed) {
            mViewWidth = getWidth();
            mViewHeight = getHeight();
        }

        super.onLayout(changed, left, top, right, bottom);
    }

    /**
     * 手势事件
     */
    class MyOnGestureListener implements GestureDetector.OnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) { // 按下事件
            return false;
        }

        // 按下停留时间超过瞬时，并且按下时没有松开或拖动，就会执行此方法
        @Override
        public void onShowPress(MotionEvent motionEvent) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent motionEvent) { // 单击抬起
            float findX = motionEvent.getX();
            float findY = motionEvent.getY();
            for (int i = 0; i < points.length; i++) {
                if (Math.abs(points[i].x - findX) < xWidth && Math.abs(points[i].y - findY) < xWidth) {
                    position = i;
                    invalidate();
                    break;
                }
            }
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (data.size() < 7) {
                return false;
            }
            if (e1.getX() > mBrokenLineLeft && e1.getX() < mViewWidth &&
                    e1.getY() < mViewHeight) {
                //注意：这里的distanceX是e1.getX()-e2.getX()
                scrollX += -distanceX;
                if (scrollX > 0) {
                    scrollX = 0;
                } else if (scrollX < -canScrollWidth) {
                    scrollX = -canScrollWidth;
                }
                invalidate();
            }
            return false;
        }

        @Override
        public void onLongPress(MotionEvent motionEvent) {
        } // 长按事件

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }
    }

    private void showText(int position, Canvas canvas) {
        if (data.size() != 0){
            if (data.get(position) > 37.2f && data.get(position) < 38.0f){
                mFloatPaint.setColor(mContext.getResources().getColor(R.color.orange));
            }else if (data.get(position) >= 38.0f){
                mFloatPaint.setColor(mContext.getResources().getColor(R.color.red));
            }else {
                mFloatPaint.setColor(circleColor);
            }

            mFloatPaint.setTextAlign(Paint.Align.LEFT);
            mFloatPaint.setTextSize(textSize);
            if (data.size() != 0) {
                canvas.drawText(data.get(position) + "℃", points[position].x - (textWidth / 2),
                        points[position].y - xWidth, mFloatPaint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return true;
    }


    private void drawLineCircle(Canvas canvas) {
        if (data.size() != 0) {
            Point[] points = getPoint(data);

            if (points.length >= 2) {
                mTextPaint.setStrokeWidth(4);
                for (int i = 0; i < points.length - 1; i++) {
                    canvas.drawLine(points[i].x, points[i].y, points[i + 1].x, points[i + 1].y, mTextPaint);
                }
            }

            for (int i = 0; i < points.length; i++) {
                Point point = points[i];
                mCirclePaint.setColor(circleColor);
                mCirclePaint.setStyle(Paint.Style.FILL);

                if (i < 10) {
                    canvas.drawText(String.format("%02d", (i + 1)), mBrokenLineLeft + circleWidth +
                            (circleWidth + textWidth) * i + scrollX, mViewHeight - (circleHeight / 2), mTextPaint);
                } else {
                    canvas.drawText(String.valueOf(i), mBrokenLineLeft + circleWidth +
                            (circleWidth + textWidth) * i + scrollX, mViewHeight - (circleHeight / 2), mTextPaint);
                }

                canvas.drawCircle(point.x, point.y, circleWidth / 2, mCirclePaint);

            }
        }

        canvas.drawRect(new RectF(0, 0, mBrokenLineLeft, mViewHeight), mBorderLinePaint);
        mBrokenLineLeft = getPaddingLeft();
        //绘制文字
        canvas.drawText(valueText[0] + "℃", mBrokenLineLeft, firstLineHeight, mTextPaint);
        for (int i = 1; i < valueText.length; i++) {
            float nowadayHeight = averageHeight * (i) + firstLineHeight;
            canvas.drawText(valueText[i] + "℃", mBrokenLineLeft,
                    nowadayHeight + mBrokenLineTop, mTextPaint);
        }
        mBrokenLineLeft += mBrokenLineLeft / 2;
        mBrokenLineLeft += textWidth;
    }

    public void setAllData(List<Float> data) {
        if (data != null) {
            initData = true;
            this.data = data;
            position = data.size() - 1;
            invalidate();
        }
    }

    public void setSingleData(float singleData){
        initData = true;
        data.add(singleData);
        position = data.size() - 1;
        invalidate();
    }

    public Point[] getPoint(List<Float> values) {
        points = new Point[values.size()];
        for (int i = 0; i < values.size(); i++) {
            if (values.size() < 100) {
                //圆的直径取两个文字长度
                Rect numberRect = new Rect();
                mTextPaint.getTextBounds("00", 0, "00".length(), numberRect);
                circleWidth = numberRect.width();
                xWidth = circleWidth;
                circleHeight = numberRect.height();
            } else if (values.size() >= 100) {
                Rect numberRect = new Rect();
                mTextPaint.getTextBounds(i + "", 0, i + "".length(), numberRect);
                circleWidth = numberRect.width();
            }
            float temp = 43f - values.get(i);
            if (temp > 9f){
                temp = 9f;
            }else if (temp < 0.4f){
                temp = 0.2f;
            }
            float y = temp * 10 * (averageHeight / 10);
            float x;
            if (i == 0) {
                x = mBrokenLineLeft + circleWidth + (xWidth / 2) + scrollX;
            } else {
                x = mBrokenLineLeft + circleWidth + ((xWidth + textWidth) * i) + (xWidth / 2) + scrollX;
            }
            points[i] = new Point((int) x, (int) y);
        }
        return points;
    }

    private void drawXYAndText(Canvas canvas) {
        mTextPaint.setColor(textColor);
        mTextPaint.setTextSize(textSize);
        mTextPaint.setTextAlign(Paint.Align.LEFT);

        mBorderLinePaint.setColor(lineColor);
        mBorderLinePaint.setStrokeWidth(2);
        mBrokenLinePaint.setColor(canvasColor);

        //绘制边框分段横线与分段文本
        Rect textRect = new Rect();
        mTextPaint.getTextBounds(valueText[0] + "℃", 0, (valueText[0] + "℃").length(), textRect);
        textWidth = textRect.width();
        textHeight = textRect.height();
        averageHeight = needDrawHeight / (valueText.length + 1);
        firstLineHeight = averageHeight + (textHeight / 2);

        mBrokenLineLeft += mBrokenLineLeft / 2;
        mBrokenLineLeft += textWidth;
        mBrokenLineBottom = averageHeight;

        //绘制矩形
        canvas.drawRect(new RectF(mBrokenLineLeft, 0, mViewWidth,
                mViewHeight - mBrokenLineBottom), mBrokenLinePaint);


        //绘制线
        for (int i = 0; i < valueText.length; i++) {
            float nowadayHeight = averageHeight * (i + 1);
            canvas.drawLine(mBrokenLineLeft, nowadayHeight,
                    mViewWidth - mBrokenLineRight, nowadayHeight, mBorderLinePaint);
        }

        canvas.drawRect(new RectF(mBrokenLineLeft, averageHeight * (valueText.length), mViewWidth,
                mViewHeight - averageHeight + (textHeight / 2)), mTextPaint);
    }

    private void getDrawWidthAndHeight() {
        needDrawHeight = mViewHeight - mBrokenLineTop - mBrokenLineBottom;
        needDrawWidth = mViewWidth - mBrokenLineLeft - mBrokenLineRight;
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {

        /**初始化文本画笔*/

        if (mTextPaint == null) {
            mTextPaint = new Paint();
        }
        initPaint(mTextPaint);

        /**初始化边框线画笔*/
        if (mBorderLinePaint == null) {
            mBorderLinePaint = new Paint();
            mBorderLinePaint.setTextSize(20);
        }
        initPaint(mBorderLinePaint);

        /**初始化折线画笔*/
        if (mBrokenLinePaint == null) {
            mBrokenLinePaint = new Paint();
        }
        initPaint(mBrokenLinePaint);

        //画圆的笔
        if (mCirclePaint == null) {
            mCirclePaint = new Paint();
        }
        initPaint(mCirclePaint);

        if (mFloatPaint == null) {
            mFloatPaint = new Paint();
        }
        initPaint(mFloatPaint);
    }

    /**
     * 初始化画笔默认属性
     */
    private void initPaint(Paint paint) {
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
    }
}
