package org.checkfood.widget;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.TextView;

import org.checkfood.R;

public class TextViewWithShadow extends TextView {
    private Paint paint;
    private LinearGradient linearGradient;

    public TextViewWithShadow(Context context, AttributeSet attrs, int defStyle) {
	super(context, attrs, defStyle);
	init();
    }

    public TextViewWithShadow(Context context, AttributeSet attrs) {
	this(context, attrs, 0);
    }

    public TextViewWithShadow(Context context) {
	this(context, null);
    }

    @Override
    protected void onDraw(Canvas canvas) {
	super.onDraw(canvas);
	if (isTextToLarge()) {
	    drawShadow(canvas);
	}
    }

    private void init() {
	paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    private void drawShadow(Canvas canvas) {
	int viewWidth = getMeasuredWidth();
	int viewHeight = getMeasuredHeight();
	Rect rect = new Rect(viewWidth - viewWidth/7, 0, viewWidth, viewHeight);
	linearGradient = new LinearGradient(rect.right, rect.top, rect.left, rect.top, getResources().getColor(
		R.color.color_gradient_textview_with_shadow_start), getResources().getColor(
		R.color.color_gradient_textview_with_shadow_end), android.graphics.Shader.TileMode.CLAMP);
	paint.setShader(linearGradient);
	canvas.drawRect(rect, paint);
    }

    private boolean isTextToLarge() {
	int textLenght = getText().toString().length();
	int maxLength = getPaint().breakText(getText().toString(), true, getWidth(), null);
	return textLenght > maxLength;
    }

}
