package org.checkfood.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import org.checkfood.R;

import java.util.ArrayList;

public class DangerView extends View {
    private int danger =1;
    private static final int DEFAULT_PADDING_WIDTH = 0;
    private static final int DEFAULT_PADDING_HEIGHT = 0;
    private static final int RECTANGLE_COUNT = 5;
    private static final int DEFAULT_WIDTH = 100;
    private static final int WIDTH_FACTOR = 12;
    private static final float HEIGHT_FACTOR = 0.95f;

    private Paint paint;
    private ArrayList<RectF> arrayListRectFs;
    private ArrayList<LinearGradient> arrayListLinearGradients;

    public DangerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public DangerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DangerView(Context context) {
        this(context, null);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int viewWidth = getMeasuredWidth();
        int viewHeight = getMeasuredHeight();

        float rectWidth = (float) ((viewWidth - DEFAULT_PADDING_WIDTH * 2) / (RECTANGLE_COUNT + 1));
        float rectHeight = (float) (viewHeight - DEFAULT_PADDING_HEIGHT * 2);

        generateRectFs(rectWidth, rectHeight);
        generateLinearGradients();

        for (int i = 0; i < arrayListRectFs.size(); i++) {
            paint.reset();
            paint.setAntiAlias(true);
            paint.setShader(arrayListLinearGradients.get(i));
            canvas.drawRect(arrayListRectFs.get(i), paint);
            paint.reset();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int measuredWidth = calculateWidth(widthMeasureSpec);
        int measuredHeight = calculateHeight(heightMeasureSpec, widthMeasureSpec);

        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    public int getDanger() {
        return danger;
    }

    public void setDanger(int danger) {
        this.danger = danger;
        invalidate();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        arrayListRectFs = new ArrayList<RectF>();
        arrayListLinearGradients = new ArrayList<LinearGradient>();
    }

    private void generateRectFs(float rectWidth, float rectHeight) {
        arrayListRectFs.clear();
        for (int i = 0; i < RECTANGLE_COUNT; i++) {
            arrayListRectFs.add(new RectF(DEFAULT_PADDING_WIDTH + rectWidth * i + (rectWidth * i) / 4, DEFAULT_PADDING_HEIGHT,
                    DEFAULT_PADDING_WIDTH + rectWidth + rectWidth * i + (rectWidth * i) / 4, rectHeight + DEFAULT_PADDING_HEIGHT));
        }
    }

    private void generateLinearGradients() {
        arrayListLinearGradients.clear();
        LinearGradient linearGradient;
        RectF rectFFirst;
        RectF rectFSecond;
        RectF rectFThird;
        RectF rectFFourth;
        RectF rectFFifth;

        switch (danger) {
            case -1:
                for (int i = 0; i < arrayListRectFs.size(); i++) {
                    RectF rectF = arrayListRectFs.get(i);
                    linearGradient = new LinearGradient(rectF.left, rectF.bottom, rectF.right, rectF.top, getResources()
                            .getColor(R.color.color_gradient_danger_bar_gray_start), getResources().getColor(
                            R.color.color_gradient_danger_bar_gray_end), Shader.TileMode.REPEAT);
                    arrayListLinearGradients.add(linearGradient);

                }
                break;

            case 0:
                for (int i = 0; i < arrayListRectFs.size(); i++) {
                    RectF rectF = arrayListRectFs.get(i);
                    linearGradient = new LinearGradient(rectF.left, rectF.bottom, rectF.right, rectF.top, getResources()
                            .getColor(R.color.color_gradient_danger_bar_gray_start), getResources().getColor(
                            R.color.color_gradient_danger_bar_gray_end), Shader.TileMode.REPEAT);
                    arrayListLinearGradients.add(linearGradient);

                }
                break;

            case 1:
                rectFFirst = arrayListRectFs.get(0);
                linearGradient = new LinearGradient(rectFFirst.left, rectFFirst.bottom, rectFFirst.right, rectFFirst.top,
                        getResources().getColor(R.color.color_gradient_danger_bar_yellow_start), getResources().getColor(
                        R.color.color_gradient_danger_bar_yellow_end), Shader.TileMode.REPEAT);
                arrayListLinearGradients.add(linearGradient);

                for (int i = 1; i < arrayListRectFs.size(); i++) {
                    RectF rectF = arrayListRectFs.get(i);
                    linearGradient = new LinearGradient(rectF.left, rectF.bottom, rectF.right, rectF.top, getResources()
                            .getColor(R.color.color_gradient_danger_bar_gray_start), getResources().getColor(
                            R.color.color_gradient_danger_bar_gray_end), Shader.TileMode.REPEAT);
                    arrayListLinearGradients.add(linearGradient);

                }

                break;
            case 2:
                rectFFirst = arrayListRectFs.get(0);
                linearGradient = new LinearGradient(rectFFirst.left, rectFFirst.bottom, rectFFirst.right, rectFFirst.top,
                        getResources().getColor(R.color.color_gradient_danger_bar_yellow_start), getResources().getColor(
                        R.color.color_gradient_danger_bar_yellow_end), Shader.TileMode.REPEAT);
                arrayListLinearGradients.add(linearGradient);

                rectFSecond = arrayListRectFs.get(1);
                linearGradient = new LinearGradient(rectFSecond.left, rectFSecond.bottom, rectFSecond.right,
                        rectFSecond.top, getResources().getColor(R.color.color_gradient_danger_bar_orange_start),
                        getResources().getColor(R.color.color_gradient_danger_bar_orange_end),
                        Shader.TileMode.REPEAT);
                arrayListLinearGradients.add(linearGradient);

                for (int i = 2; i < arrayListRectFs.size(); i++) {
                    RectF rectF = arrayListRectFs.get(i);
                    linearGradient = new LinearGradient(rectF.left, rectF.bottom, rectF.right, rectF.top, getResources()
                            .getColor(R.color.color_gradient_danger_bar_gray_start), getResources().getColor(
                            R.color.color_gradient_danger_bar_gray_end), Shader.TileMode.REPEAT);
                    arrayListLinearGradients.add(linearGradient);

                }

                break;
            case 3:
                rectFFirst = arrayListRectFs.get(0);
                linearGradient = new LinearGradient(rectFFirst.left, rectFFirst.bottom, rectFFirst.right, rectFFirst.top,
                        getResources().getColor(R.color.color_gradient_danger_bar_yellow_start), getResources().getColor(
                        R.color.color_gradient_danger_bar_yellow_end), Shader.TileMode.REPEAT);
                arrayListLinearGradients.add(linearGradient);

                rectFSecond = arrayListRectFs.get(1);
                linearGradient = new LinearGradient(rectFSecond.left, rectFSecond.bottom, rectFSecond.right,
                        rectFSecond.top, getResources().getColor(R.color.color_gradient_danger_bar_orange_start),
                        getResources().getColor(R.color.color_gradient_danger_bar_orange_end),
                        Shader.TileMode.REPEAT);
                arrayListLinearGradients.add(linearGradient);

                rectFThird = arrayListRectFs.get(2);
                linearGradient = new LinearGradient(rectFThird.left, rectFThird.bottom, rectFThird.right, rectFThird.top,
                        getResources().getColor(R.color.color_gradient_danger_bar_mandarin_start), getResources().getColor(
                        R.color.color_gradient_danger_bar_mandarin_end), Shader.TileMode.REPEAT);
                arrayListLinearGradients.add(linearGradient);

                for (int i = 3; i < arrayListRectFs.size(); i++) {
                    RectF rectF = arrayListRectFs.get(i);
                    linearGradient = new LinearGradient(rectF.left, rectF.bottom, rectF.right, rectF.top, getResources()
                            .getColor(R.color.color_gradient_danger_bar_gray_start), getResources().getColor(
                            R.color.color_gradient_danger_bar_gray_end), Shader.TileMode.REPEAT);
                    arrayListLinearGradients.add(linearGradient);

                }
                break;

            case 4:
                rectFFirst = arrayListRectFs.get(0);
                linearGradient = new LinearGradient(rectFFirst.left, rectFFirst.bottom, rectFFirst.right, rectFFirst.top,
                        getResources().getColor(R.color.color_gradient_danger_bar_yellow_start), getResources().getColor(
                        R.color.color_gradient_danger_bar_yellow_end), Shader.TileMode.REPEAT);
                arrayListLinearGradients.add(linearGradient);

                rectFSecond = arrayListRectFs.get(1);
                linearGradient = new LinearGradient(rectFSecond.left, rectFSecond.bottom, rectFSecond.right,
                        rectFSecond.top, getResources().getColor(R.color.color_gradient_danger_bar_orange_start),
                        getResources().getColor(R.color.color_gradient_danger_bar_orange_end),
                        Shader.TileMode.REPEAT);
                arrayListLinearGradients.add(linearGradient);

                rectFThird = arrayListRectFs.get(2);
                linearGradient = new LinearGradient(rectFThird.left, rectFThird.bottom, rectFThird.right, rectFThird.top,
                        getResources().getColor(R.color.color_gradient_danger_bar_mandarin_start), getResources().getColor(
                        R.color.color_gradient_danger_bar_mandarin_end), Shader.TileMode.REPEAT);
                arrayListLinearGradients.add(linearGradient);

                rectFFourth = arrayListRectFs.get(3);
                linearGradient = new LinearGradient(rectFFourth.left, rectFFourth.bottom, rectFFourth.right,
                        rectFFourth.top, getResources().getColor(R.color.color_gradient_danger_bar_red_start),
                        getResources().getColor(R.color.color_gradient_danger_bar_red_end),
                        Shader.TileMode.REPEAT);
                arrayListLinearGradients.add(linearGradient);

                for (int i = 4; i < arrayListRectFs.size(); i++) {
                    RectF rectF = arrayListRectFs.get(i);
                    linearGradient = new LinearGradient(rectF.left, rectF.bottom, rectF.right, rectF.top, getResources()
                            .getColor(R.color.color_gradient_danger_bar_gray_start), getResources().getColor(
                            R.color.color_gradient_danger_bar_gray_end), Shader.TileMode.REPEAT);
                    arrayListLinearGradients.add(linearGradient);

                }
                break;

            case 5:
                rectFFirst = arrayListRectFs.get(0);
                linearGradient = new LinearGradient(rectFFirst.left, rectFFirst.bottom, rectFFirst.right, rectFFirst.top,
                        getResources().getColor(R.color.color_gradient_danger_bar_yellow_start), getResources().getColor(
                        R.color.color_gradient_danger_bar_yellow_end), Shader.TileMode.REPEAT);
                arrayListLinearGradients.add(linearGradient);

                rectFSecond = arrayListRectFs.get(1);
                linearGradient = new LinearGradient(rectFSecond.left, rectFSecond.bottom, rectFSecond.right,
                        rectFSecond.top, getResources().getColor(R.color.color_gradient_danger_bar_orange_start),
                        getResources().getColor(R.color.color_gradient_danger_bar_orange_end),
                        Shader.TileMode.REPEAT);
                arrayListLinearGradients.add(linearGradient);

                rectFThird = arrayListRectFs.get(2);
                linearGradient = new LinearGradient(rectFThird.left, rectFThird.bottom, rectFThird.right, rectFThird.top,
                        getResources().getColor(R.color.color_gradient_danger_bar_mandarin_start), getResources().getColor(
                        R.color.color_gradient_danger_bar_mandarin_end), Shader.TileMode.REPEAT);
                arrayListLinearGradients.add(linearGradient);

                rectFFourth = arrayListRectFs.get(3);
                linearGradient = new LinearGradient(rectFFourth.left, rectFFourth.bottom, rectFFourth.right,
                        rectFFourth.top, getResources().getColor(R.color.color_gradient_danger_bar_red_start),
                        getResources().getColor(R.color.color_gradient_danger_bar_red_end),
                        Shader.TileMode.REPEAT);
                arrayListLinearGradients.add(linearGradient);

                rectFFifth = arrayListRectFs.get(4);
                linearGradient = new LinearGradient(rectFFifth.left, rectFFifth.bottom, rectFFifth.right, rectFFifth.top,
                        getResources().getColor(R.color.color_gradient_danger_bar_red_start), getResources().getColor(
                        R.color.color_gradient_danger_bar_red_end), Shader.TileMode.REPEAT);
                arrayListLinearGradients.add(linearGradient);
                break;
        }
    }

    private int calculateHeight(int heightMeasureSpec, int widthMeasureSpec) {
        int specMode = MeasureSpec.getMode(heightMeasureSpec);
        int specSize = MeasureSpec.getSize(heightMeasureSpec);

        int specSizeWidth = MeasureSpec.getSize(widthMeasureSpec);

        int defValue = specSizeWidth/WIDTH_FACTOR + DEFAULT_PADDING_HEIGHT * 2;

        if (specMode == MeasureSpec.EXACTLY) {
            defValue = specSize;
        }
        return (int)(defValue * HEIGHT_FACTOR);
    }

    private int calculateWidth(int widthMeasureSpec) {
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);

        int defValue = DEFAULT_WIDTH;

        if (specMode == MeasureSpec.EXACTLY) {
            defValue = specSize;
        }
        return defValue;
    }
}
