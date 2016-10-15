package com.example.obtoggle;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
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
    private Drawable mDrawable, mLeftIconBg;
    private int mHeight = parseToDp(45);

    public ObToggle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public ObToggle(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ObToggle(Context context) {
        super(context);
        init();
    }

    private void init(){
        init(null);
    }

    private void init(AttributeSet attr){

        post(new Runnable() {
            @Override
            public void run() {
                mHeight = getHeight();
                if (getBackground() == null){
                    setBackgroundDrawable(getDefaultBg());
                    invalidate();
                }
                invalidate();
            }
        });

        TypedArray typedArray = getContext().obtainStyledAttributes(attr, R.styleable.ObToggle, 0, 0);
        int count = typedArray.getIndexCount();
        for (int i = 0; i < count; i++) {
            int i1 = typedArray.getIndex(i);
            if (i1 == R.styleable.ObToggle_leftIconDrawable) {
                setLeftIcon(typedArray.getDrawable(i));
            } else if (i1 == R.styleable.ObToggle_leftIconBackgroundDrawable) {
                setLeftIconBackground(typedArray.getDrawable(i));
            }
        }

        typedArray.recycle();
    }

    private void paddingIcon(){
        setPadding(mHeight, parseToDp(0), parseToDp(0), parseToDp(0));
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (mLeftIconBg != null){
            mHeight = canvas.getClipBounds().bottom;
            mLeftIconBg.setBounds(parseToDp(0), parseToDp(0), mHeight, mHeight);
            mLeftIconBg.setState(getDrawableState());
            mLeftIconBg.draw(canvas);
            paddingIcon();
        } else {
            mHeight = canvas.getClipBounds().bottom;
            mLeftIconBg = getDefLeftIconBg();
            mLeftIconBg.setBounds(parseToDp(0), parseToDp(0), mHeight, mHeight);
            mLeftIconBg.setState(getDrawableState());
            mLeftIconBg.draw(canvas);
            paddingIcon();
        }
        if (mDrawable != null){
            int s = (int) (mHeight * 0.8);
            int xy = (int) (mHeight * 0.2);
            mDrawable.setBounds(xy, xy, s, s);
            mDrawable.setState(getDrawableState());
            mDrawable.draw(canvas);
        }
        super.onDraw(canvas);

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

    private Drawable getDefLeftIconBg(){
        return ContextCompat.getDrawable(getContext(), R.drawable.def_icon_bg);
    }

}
