package com.scu.drawabletoptextview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

import com.scu.drawabletoptextview.R;

/**
 * TODO: document your custom view class.
 * DrawableTop;
 */
public class TopRefreshTextView1 extends TextView {

    public TopRefreshTextView1(Context context) {
        super(context);
        init();
    }

    public TopRefreshTextView1(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TopRefreshTextView1(Context context, AttributeSet attrs, int defStyle) {
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
    Rect src,dst;Paint paint = new Paint();
    Bitmap topBitmap;
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
        if (mDrawableTop == null){
            mDrawableTop = getCompoundDrawables()[1];
            topBitmap = drawableToBitmap(mDrawableTop);
        }
    }

    protected void MonDraw(Canvas canvas) {
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

    protected void onDraw(Canvas canvas){
        if (mDrawableTop == null) {
            super.onDraw(canvas);
            return;
        }
        int drawablePadding = getCompoundDrawablePadding();
        int drawableWidth = this.mDrawableTop.getIntrinsicWidth();
        int drawableHeight = this.mDrawableTop.getIntrinsicHeight();
        src = new Rect(0,0,drawableWidth,drawableHeight);
        dst = new Rect(drawablePadding,drawablePadding,drawablePadding+drawableWidth,drawablePadding+drawableHeight);
        canvas.save();
        canvas.drawBitmap(topBitmap,src,dst,paint);
        canvas.restore();
        canvas.save();
        Rect textRect = new Rect();
        paint.getTextBounds(getText().toString(),0,getText().length(),textRect);
        float text_x = (getWidth()>>1) - textRect.left-drawablePadding;
        float text_y = getHeight() - drawablePadding - dst.bottom;
        canvas.drawText(getText(),0,getText().length(),text_x,text_y,paint);
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
    static Drawable zoomDrawable(Drawable drawable, int w, int h)
    {
        int width = drawable.getIntrinsicWidth();
        int height= drawable.getIntrinsicHeight();
        Bitmap oldbmp = drawableToBitmap(drawable); // drawable 转换成 bitmap
        Matrix matrix = new Matrix();   // 创建操作图片用的 Matrix 对象
        float scaleWidth = ((float)w / width);   // 计算缩放比例
        float scaleHeight = ((float)h / height);
        matrix.postScale(scaleWidth, scaleHeight);         // 设置缩放比例
        Bitmap newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height, matrix, true);       // 建立新的 bitmap ，其内容是对原 bitmap 的缩放后的图
        return new BitmapDrawable(newbmp);       // 把 bitmap 转换成 drawable 并返回
    }
    static Bitmap drawableToBitmap(Drawable drawable) // drawable 转换成 bitmap
    {
        int width = drawable.getIntrinsicWidth();   // 取 drawable 的长宽
        int height = drawable.getIntrinsicHeight();
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888:Bitmap.Config.RGB_565;         // 取 drawable 的颜色格式
        Bitmap bitmap = Bitmap.createBitmap(width, height, config);     // 建立对应 bitmap
        Canvas canvas = new Canvas(bitmap);         // 建立对应 bitmap 的画布
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);      // 把 drawable 内容画到画布中
        return bitmap;
    }
}
