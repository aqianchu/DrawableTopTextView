package com.scu.drawabletoptextview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

import com.scu.drawabletoptextview.R;

/**
 * TODO: document your custom view class.
 * DrawableTop;根据图片大小绘制TextView
 */
public class TopRefreshTextView extends TextView {

    public TopRefreshTextView(Context context) {
        super(context);
        init();
    }

    public TopRefreshTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TopRefreshTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private String mRefreshCount = "10";
    private boolean mShowCount = false;
    private Drawable mCountDrawable;
    private int mCountHeight;
    private int mCountBottomMarginCenter;
    private int mCountMarginCenter;
    private int mCountTextMarginHorizontal;
    private Paint mPaint;
    private Rect mRect;

    private void init() {
        mCountHeight = (int) getContext().getResources().getDimension(R.dimen.home_bottom_menu_new_hint_height);
        mCountBottomMarginCenter = (int) getContext().getResources().getDimension(R.dimen.home_bottom_menu_new_hint_bottom_margin_center);
        mCountMarginCenter = (int) getContext().getResources().getDimension(R.dimen.home_bottom_menu_new_hint_margin_center);
        mCountTextMarginHorizontal = DensityUtils.dip2px(getContext(), 5);
        mRect = new Rect();
        mPaint = new Paint();
        mPaint.setTextSize(getContext().getResources().getDimension(R.dimen.home_bottom_menu_new_hint_text));
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (mShowCount && !"0".equals(mRefreshCount)) {
            mPaint.getTextBounds(mRefreshCount, 0, mRefreshCount.length(), mRect);
            int textWidth = mRect.width() + mCountTextMarginHorizontal * 2;
            textWidth = textWidth < mCountHeight ? mCountHeight : textWidth;
            int bottom = Math.round(getHeight() / 2.f - mCountBottomMarginCenter);
            int top = bottom - mCountHeight;
            int left = Math.round(getWidth() / 2.f + mCountMarginCenter - textWidth / 2.f);
            int right = left + textWidth;
            if (mCountDrawable != null) {
                mCountDrawable.setBounds(left, top, right, bottom);
                mCountDrawable.draw(canvas);
            }
            int y = (int) (mRect.height() / 2.f + top + (bottom - top) / 2.f);
            canvas.drawText(mRefreshCount, left + Math.round((right - left) / 2.f), y, mPaint);
        }
    }

    public void setCountText(String count) {
        if (TextUtils.isEmpty(count) || count.length() > 5) {
            return;
        }
        mRefreshCount = count;
        if (mShowCount && !"0".equals(mRefreshCount)) {
            invalidate();
        }
    }

    public void setCountTextColor(int color) {
        if (mPaint.getColor() != color) {
            mPaint.setColor(color);
            if (mShowCount) {
                invalidate();
            }
        }
    }

    public void setShowCount(boolean show) {
        if (mShowCount != show) {
            mShowCount = show;
            invalidate();
        }
    }

    public void setHitTextSize(int size) {
        if (mPaint.getTextSize() != size) {
            mPaint.setTextSize(size);
            invalidate();
        }
    }

    public void setCountBackgroundDrawable(Drawable drawable) {
        if (mCountDrawable != drawable) {
            mCountDrawable = drawable;
            if (mShowCount) {
                invalidate();
            }
        }
    }

    public void setHitHeight(int height) {
        if (mCountHeight != height) {
            mCountHeight = height;
            invalidate();
        }
    }

    public void setHitMarginCenter(int bottomMarginCenter, int horizontalMarginCenter, int textMarginHorizontal) {
        boolean invalidate = false;
        if (mCountBottomMarginCenter != bottomMarginCenter) {
            mCountBottomMarginCenter = bottomMarginCenter;
            invalidate = true;
        }
        if (mCountMarginCenter != horizontalMarginCenter) {
            mCountMarginCenter = horizontalMarginCenter;
            invalidate = true;
        }
        if (mCountTextMarginHorizontal != textMarginHorizontal) {
            mCountTextMarginHorizontal = textMarginHorizontal;
            invalidate = true;
        }
        if (invalidate) {
            invalidate();
        }
    }
    int textWidth;
    int textHeight;
    Drawable mDrawableTop = null;
    int startDrawableX = 0;
    int startDrawableY= 0;
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initText();
    }

    private void initText() {
        String textStr = super.getText().toString();
        Rect rect = new Rect();
        getPaint().getTextBounds(textStr,0,textStr.length(),rect);
        getPaint().setColor(getTextColors().getDefaultColor());
        getPaint().setTextSize(getTextSize());
        textWidth = rect.width();
        textHeight = rect.height();
        if (mDrawableTop == null) mDrawableTop = getCompoundDrawables()[1];

    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (mDrawableTop == null) {
            super.onDraw(canvas);
            return;
        }
        int drawablePadding = getCompoundDrawablePadding();
        int drawableWidth = this.mDrawableTop.getIntrinsicWidth();
        int drawableHeight = this.mDrawableTop.getIntrinsicHeight();
//        startDrawableX = (getWidth() >> 1) - ((drawablePadding + textWidth + drawableWidth) >> 1);
//        startDrawableY = (getHeight() >> 1) - (drawableHeight >> 1);
        startDrawableX = (getWidth()>>1) - (drawableWidth>>1);
        startDrawableY = (getHeight()>>1) - ((drawableHeight+textHeight+drawablePadding)>>1);
        //画旋转图片
        canvas.save();
        canvas.translate(startDrawableX, startDrawableY);
        this.mDrawableTop.draw(canvas);
        canvas.restore();

        //画文字
//        int boxht = this.getMeasuredHeight() - this.getExtendedPaddingTop() - this.getExtendedPaddingBottom();
//        int textht = getLayout().getHeight();
//        int  voffsetText = boxht - textht >> 1;
//        int boxht = this
        canvas.save();
//        canvas.translate((float) (startDrawableX + drawableWidth + drawablePadding), (float) (getExtendedPaddingTop() + voffsetText));
        canvas.translate((float)(getExtendedPaddingBottom()),(float)(startDrawableY+drawableHeight+drawablePadding));
        getLayout().draw(canvas);
        canvas.restore();
    }

    @Override
    public void invalidateDrawable(Drawable drawable) {
//        super.invalidateDrawable(drawable);
        final Rect dirty = drawable.getBounds();
        int scrollX = 0;
        int scrollY = 0;
        if(drawable == this.mDrawableTop){
            scrollX = startDrawableX;
            scrollY = startDrawableY;
        }
        this.invalidate(dirty.left + scrollX-2, dirty.top + scrollY-2, dirty.right + scrollX+2, dirty.bottom + scrollY+2);
    }

    public Drawable getDrawableTop() {
        return mDrawableTop;
    }

    public void setDrawableTop(Drawable mDrawableTop) {
        this.mDrawableTop = mDrawableTop;
    }
}
