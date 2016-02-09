package org.checkfood.widget;



import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.TextView;

import org.checkfood.R;

public class AdditiveView extends TextView {
    private int danger = -1;
    private Paint paint;
    private RectF rectFSquare;
    private Path pathTriangle;

    public AdditiveView(Context context, AttributeSet attrs, int defStyle) {
	super(context, attrs, defStyle);
	init();
    }

    public AdditiveView(Context context, AttributeSet attrs) {
	this(context, attrs, 0);
    }

    public AdditiveView(Context context) {
	this(context, null);
    }

    @Override
    protected void onDraw(Canvas canvas) {
	if (paint == null) {
	    preparePaint();
	}

	int viewWidth = getMeasuredWidth();
	int viewHeight = getMeasuredHeight();

	rectFSquare.set(0, 0, viewWidth - viewHeight / 2, viewHeight);
	pathTriangle.moveTo(viewWidth - viewHeight / 2, viewHeight);
	pathTriangle.lineTo(viewWidth - viewHeight / 2, 0);
	pathTriangle.lineTo(viewWidth, 0);
	pathTriangle.lineTo(viewWidth - viewHeight / 2, viewHeight);

	canvas.drawRect(rectFSquare, paint);
	canvas.drawPath(pathTriangle, paint);
	setPadding(- viewHeight / 4, 0, 0, 0);

	super.onDraw(canvas);

    }

    public int getDanger() {
	return danger;
    }

    public void setDanger(int danger) {
	this.danger = danger;
	preparePaint();
	invalidate();
    }

    private void init() {
	rectFSquare = new RectF();
	pathTriangle = new Path();
    }

    private void preparePaint() {
	LinearGradient linearGradient = null;
	RectF rectF = new RectF(0, 0, getMeasuredWidth(), getMeasuredHeight());

	switch (danger) {
	case -1:
	    linearGradient = new LinearGradient(rectF.left, rectF.top, rectF.left, rectF.bottom, getResources()
		    .getColor(R.color.color_gradient_additive_gray_start), getResources().getColor(
		    R.color.color_gradient_additive_gray_end), android.graphics.Shader.TileMode.REPEAT);
	    break;

	case 0:
	    linearGradient = new LinearGradient(rectF.left, rectF.top, rectF.left, rectF.bottom, getResources()
		    .getColor(R.color.color_gradient_additive_green_start), getResources().getColor(
		    R.color.color_gradient_additive_green_end), android.graphics.Shader.TileMode.REPEAT);
	    break;

	case 1:
	    linearGradient = new LinearGradient(rectF.left, rectF.top, rectF.left, rectF.bottom, getResources()
		    .getColor(R.color.color_gradient_additive_yellow_start), getResources().getColor(
		    R.color.color_gradient_additive_yellow_end), android.graphics.Shader.TileMode.REPEAT);
	    break;

	case 2:
	    linearGradient = new LinearGradient(rectF.left, rectF.top, rectF.left, rectF.bottom, getResources()
		    .getColor(R.color.color_gradient_additive_orange_start), getResources().getColor(
		    R.color.color_gradient_additive_orange_end), android.graphics.Shader.TileMode.REPEAT);
	    break;

	case 3:
	    linearGradient = new LinearGradient(rectF.left, rectF.top, rectF.left, rectF.bottom, getResources()
		    .getColor(R.color.color_gradient_additive_mandarin_start), getResources().getColor(
		    R.color.color_gradient_additive_mandarin_end), android.graphics.Shader.TileMode.REPEAT);
	    break;

	case 4:
	    linearGradient = new LinearGradient(rectF.left, rectF.top, rectF.left, rectF.bottom, getResources()
		    .getColor(R.color.color_gradient_additive_red_start), getResources().getColor(
		    R.color.color_gradient_additive_red_end), android.graphics.Shader.TileMode.REPEAT);
	    break;

	case 5:
	    linearGradient = new LinearGradient(rectF.left, rectF.top, rectF.left, rectF.bottom, getResources()
		    .getColor(R.color.color_gradient_additive_red_start), getResources().getColor(
		    R.color.color_gradient_additive_red_end), android.graphics.Shader.TileMode.REPEAT);
	    break;
	}
	paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	paint.setShader(linearGradient);
	paint.setDither(true);
    }

}
