package com.example.obtoggle;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.RemoteViews;
import android.widget.ToggleButton;

/**
 * Created by kognsin on 10/15/2016.
 */

@RemoteViews.RemoteView
public class ObToggle extends ToggleButton {

    private static final String TAG = "ObToggle";
    private Drawable mDrawable, mLeftIconBg, mBg;
    private int mHeight = parseToDp(45);
    private int mWidth = parseToDp(100);
    private Rect mRect = new Rect();
    private Paint mTextPaint;

    public ObToggle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, 0);
    }

    public ObToggle(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public ObToggle(Context context) {
        super(context);
        init();
    }

    private void init(){
        init(null, 0);
    }

    private Paint getTextPaint(){
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(getCurrentTextColor());
        mTextPaint.setTextSize(getTextSize());
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setStyle(Paint.Style.FILL);
        return mTextPaint;
    }

    public void setTextColor(int color){
        mTextPaint.setColor(color);
        invalidate();
    }

    private void init(AttributeSet attr, int defStyleAttr){

        setBackgroundDrawable(getDefLeftIconBg());
        TypedArray typedArray = getContext().obtainStyledAttributes(attr, R.styleable.ObToggle, defStyleAttr, 0);
        int count = typedArray.getIndexCount();
        for (int i = 0; i < count; i++) {
            int i1 = typedArray.getIndex(i);
            if (i1 == R.styleable.ObToggle_backgroundDrawable) {
                setBackgroundDrawable(typedArray.getDrawable(i));
            } else if (i1 == R.styleable.ObToggle_leftIconBackgroundDrawable) {
                setLeftIconBackground(typedArray.getDrawable(i));
            } else if (i1 == R.styleable.ObToggle_leftIconDrawable) {
                setLeftIcon(typedArray.getDrawable(i));
            }
        }

        typedArray.recycle();
    }

    @Override
    public void setBackgroundDrawable(Drawable drawable){
        mBg = drawable;
        super.setBackgroundDrawable(mBg);
    }

    private void paddingIcon(){
        setPadding(mHeight, parseToDp(0), parseToDp(0), parseToDp(0));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.getClipBounds(mRect);
        mHeight = mRect.bottom;
        mWidth = mRect.right;
        drawBackground(canvas);
        drawLeftIconBackground(canvas);
        drawLeftIcon(canvas);
        drawText(canvas);
    }

    protected void drawText(Canvas canvas) {
        if (getText() != null) {
            Rect bounds = new Rect();
            Paint textPaint = getTextPaint();
            textPaint.getTextBounds(getText().toString(), 0, getText().length(), bounds);
            canvas.drawText(getText(), 0, getText().length(), (mWidth + mHeight) / 2, (mHeight / 2) - bounds.exactCenterY(), textPaint);
        }
    }

    protected void drawBackground(Canvas canvas) {
        mBg.setBounds(0, 0, mWidth, mHeight);
        mBg.setState(getDrawableState());
        mBg.draw(canvas);
    }

    protected void drawLeftIcon(Canvas canvas) {
        if (mDrawable != null){
            int s = (int) (mHeight * 0.8);
            int xy = (int) (mHeight * 0.2);
            mDrawable.setBounds(xy, xy, s, s);
            mDrawable.setState(getDrawableState());
            mDrawable.draw(canvas);
        }
    }

    protected void drawLeftIconBackground(Canvas canvas) {
        if (mLeftIconBg != null) {
            int h = mHeight;
            mLeftIconBg.setBounds(0, 0, h, h);
            mLeftIconBg.setState(getDrawableState());
            mLeftIconBg.draw(canvas);
        } else {
            int h = mHeight;
            Drawable drawable = getDefLeftIconBg();
            drawable.setBounds(parseToDp(0), parseToDp(0), h, h);
            drawable.setState(getDrawableState());
            drawable.draw(canvas);
        }
        paddingIcon();
    }

    @Override
    public void setHeight(int pixels) {
        super.setHeight(pixels);
        mHeight = parseToDp(pixels);
        invalidate();
    }

    private int parseToDp(int val){
       return (int) getContext().getResources().getDisplayMetrics().scaledDensity * val;
    }

    public void setLeftIcon(Drawable drawable){
        mDrawable = drawable;
        invalidate();
    }

    public void setLeftIconBackground(Drawable drawable){
        mLeftIconBg = drawable;
        invalidate();
    }

    private Drawable getDefaultBg(){
        return ContextCompat.getDrawable(getContext(), R.drawable.def_bg);
    }

    private Drawable getEmptyIcon(){
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(Color.TRANSPARENT);
        return drawable;
    }

    private Drawable getDefLeftIconBg(){
        return ContextCompat.getDrawable(getContext(), R.drawable.def_icon_bg);
    }

}
