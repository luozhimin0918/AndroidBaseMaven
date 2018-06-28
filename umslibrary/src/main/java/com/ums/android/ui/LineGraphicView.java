package com.ums.android.ui;

import java.math.BigDecimal;
import java.util.ArrayList;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import com.ums.android.R;
import com.ums.android.util.Log;

public class LineGraphicView extends View {
    /**
     * 公共部分
     */
    private static final int CIRCLE_SIZE = 10;

    private static enum Linestyle {
        Line, Curve
    }

    private Context mContext;
    private Paint mPaint;
    private Resources res;
    private DisplayMetrics dm;

    /**
     * data
     */
    private Linestyle mStyle = Linestyle.Curve;

    private int canvasHeight;
    private int canvasWidth;
    private int bheight = 0;
    private int blwidh;
    private boolean isMeasure = true;
    /**
     * Y轴最大值
     */
    private double maxValue;
    /**
     * Y轴最小值
     */
    private double minValue;
    /**
     * Y轴间距值
     */
    private double averageValue;
    private int spacingHeight;
    private int marginTop = 100;
    private int marginRight = 33;
    private int yMarginRight = 5;
//    private int marginBottom = 100;

    /**
     * 曲线上总点数
     */
    private Point[] mPoints;
    /**
     * 纵坐标值
     */
    private ArrayList<Double> yRawData = new ArrayList<Double>();
    /**
     * 横坐标值
     */
    private ArrayList<String> xRawDatas;
    private ArrayList<Integer> xList = new ArrayList<Integer>();// 记录每个x的值


    public LineGraphicView(Context context) {
        this(context, null);
    }

    public LineGraphicView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initView();
    }

    private void initView() {
        this.res = mContext.getResources();
        this.mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (isMeasure) {
            this.canvasHeight = getHeight();
            this.canvasWidth = getWidth();
            if (bheight == 0)
                bheight = (int) (canvasHeight - marginTop*2);
            blwidh = dip2px(marginRight);
            isMeasure = false;
        }
    }

    public void setData(ArrayList<Double> yRawData, ArrayList<String> xRawData, double maxValue, double minValue,double averageValue) {
        this.maxValue = maxValue;
        this.minValue = minValue;
        this.averageValue = averageValue;
        this.mPoints = new Point[yRawData.size()];
        this.xRawDatas = xRawData;
        this.yRawData = yRawData;
        this.spacingHeight = (int)((maxValue-minValue) / averageValue);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setColor(res.getColor(R.color.ums_gray_normal));
        mPaint.setStrokeWidth(dip2px(1f));
        drawAllXLine(canvas);
        // 画直线（纵向）
        drawAllYLine(canvas);
        // 点的操作设置
        mPoints = getPoints();

        mPaint.setColor(res.getColor(R.color.ums_text_orange));
        mPaint.setStrokeWidth(dip2px(2.5f));
        mPaint.setStyle(Style.STROKE);
        if (mStyle == Linestyle.Curve) {
            drawScrollLine(canvas);
        } else {
            drawLine(canvas);
        }

        mPaint.setStyle(Style.FILL);
        for (int i = 0; i < mPoints.length; i++) {
            canvas.drawCircle(mPoints[i].x, mPoints[i].y, CIRCLE_SIZE / 2, mPaint);
        }
    }

    /**
     * 画所有横向表格，包括X轴
     */
    private void drawAllXLine(Canvas canvas) {
        if(spacingHeight >0){
            for (int i = 0; i < spacingHeight + 1; i++) {
                canvas.drawLine(blwidh+marginRight, bheight - (bheight / spacingHeight) * i + marginTop, (canvasWidth - blwidh)+marginRight,
                        bheight - (bheight / spacingHeight) * i + marginTop, mPaint);// Y坐标
                if(i == 0)continue;
                Log.i("LineView","AAA origin: " + (minValue + averageValue * i));
                BigDecimal bg = new BigDecimal((minValue + averageValue * i));
                double f1 = bg.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
                String str = String.valueOf(f1);
                Log.i("LineView","AAA str: " +str);
                drawText(str, yMarginRight, bheight - (bheight / spacingHeight) * i + marginTop + spacingHeight / 2,
                        canvas);
            }
        }
    }

    /**
     * 画所有纵向表格，包括Y轴
     */
    private void drawAllYLine(Canvas canvas) {
        for (int i = 0; i < yRawData.size(); i++) {
            xList.add(blwidh + (canvasWidth - blwidh) / yRawData.size() * i);
            canvas.drawLine(blwidh + (canvasWidth - blwidh) / yRawData.size() * i+marginRight, (marginTop-20), blwidh
                    + (canvasWidth - blwidh) / yRawData.size() * i+marginRight, bheight + marginTop, mPaint);
            drawText(xRawDatas.get(i), blwidh + (canvasWidth - blwidh) / yRawData.size() * i+marginRight, bheight + (int) (marginTop * 1.5),
                    canvas);// X坐标
        }
    }

    private void drawScrollLine(Canvas canvas) {
        Point startp = new Point();
        Point endp = new Point();
        for (int i = 0; i < mPoints.length - 1; i++) {
            startp = mPoints[i];
            endp = mPoints[i + 1];
            int wt = (startp.x + endp.x) / 2;
            Point p3 = new Point();
            Point p4 = new Point();
            p3.y = startp.y;
            p3.x = wt;
            p4.y = endp.y;
            p4.x = wt;

            Path path = new Path();
            path.moveTo(startp.x, startp.y);
            path.cubicTo(p3.x, p3.y, p4.x, p4.y, endp.x, endp.y);
            canvas.drawPath(path, mPaint);
        }
    }

    private void drawLine(Canvas canvas) {
        Point startp = new Point();
        Point endp = new Point();
        for (int i = 0; i < mPoints.length - 1; i++) {
            startp = mPoints[i];
            endp = mPoints[i + 1];
            canvas.drawLine(startp.x, startp.y, endp.x, endp.y, mPaint);
        }
    }

    private void drawText(String text, int x, int y, Canvas canvas) {
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setTextSize(dip2px(12));
        p.setColor(res.getColor(R.color.ums_gray_normal));
        p.setTextAlign(Paint.Align.LEFT);
        canvas.drawText(text, x, y, p);
    }

    private Point[] getPoints() {
        Point[] points = new Point[yRawData.size()];
        for (int i = 0; i < yRawData.size(); i++) {
            int ph = bheight - (int) (bheight * ((yRawData.get(i)-minValue) / (maxValue-minValue)));
            points[i] = new Point(xList.get(i)+marginRight, ph + marginTop);
        }
        return points;
    }



    public void setTotalvalue(int maxValue) {
        this.maxValue = maxValue;
    }

    public void setPjvalue(int averageValue) {
        this.averageValue = averageValue;
    }

    public void setMargint(int marginTop) {
        this.marginTop = marginTop;
    }

//    public void setMarginb(int marginBottom) {
//        this.marginBottom = marginBottom;
//    }

    public void setMstyle(Linestyle mStyle) {
        this.mStyle = mStyle;
    }

    public void setBheight(int bheight) {
        this.bheight = bheight;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    private int dip2px(float dpValue) {
        return (int) (dpValue * dm.density + 0.5f);
    }

}
