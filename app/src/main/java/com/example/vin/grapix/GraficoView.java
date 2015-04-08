package com.example.vin.grapix;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.CYAN;
import static android.graphics.Color.RED;
import static android.graphics.Color.argb;
import static android.graphics.Color.red;


/**
 */
public class GraficoView extends SurfaceView implements SurfaceHolder.Callback {
    private String mExampleString = "example"; // TODO: use a default from R.string...
    private int mExampleColor = RED; // TODO: use a default from R.color...
    private float mExampleDimension = 50; // TODO: use a default from R.dimen...
    private Drawable mExampleDrawable;

    private TextPaint mTextPaint = new TextPaint(RED);
    private float mTextWidth=200;
    private float mTextHeight=200;
    private float mTextSize=40;
    private TextView mStatusText;


    int paddingLeft = getPaddingLeft();
    int paddingTop = getPaddingTop();
    int paddingRight = getPaddingRight();
    int paddingBottom = getPaddingBottom();

    int contentWidth = getWidth();
    int contentHeight = getHeight();

    private int mCanvasHeight = 1;
    private int mCanvasWidth = 1;

    private SurfaceHolder mSurfaceHolder;
    private Context mContext;
    private Handler mHandler;
    private Bitmap mBackgroundImage= Bitmap.createBitmap(100 ,100,Bitmap.Config.ARGB_8888);


    GraficoThread thread;
    boolean threadRunning;
    private boolean mRun = true;
    private final Object mRunLock = new Object();
    List<Integer> valores=new ArrayList<Integer>();
    BlockChart pie=new BlockChart();

    class GraficoThread extends Thread {
        Path path = new Path();
        Paint paint = new Paint(BLACK);
        public GraficoThread(SurfaceHolder surfaceHolder, Context context,
                           Handler handler) {
            // get handles to some important objects
            mSurfaceHolder = surfaceHolder;
            mHandler = handler;
            mContext = context;
            valores.add(10);
            valores.add(20);
            valores.add(30);
            Resources res = context.getResources();
        }
        private void doDraw(Canvas canvas) {
            canvas.drawBitmap(mBackgroundImage, 0, 0, null);
            canvas.drawColor(Color.WHITE);
            Rect canvasRect = canvas.getClipBounds();

            pie.draw(canvas,valores);


            mTextPaint.setTextSize(mTextSize);
            mTextPaint.setColor(RED);
            canvas.drawText(mExampleString, 20,  mTextSize+mTextHeight,  mTextPaint);
            mTextHeight--;

            //canvasRect.
            // Draw the example drawable on top of the text.
            if (mExampleDrawable != null) {
                mExampleDrawable.setBounds(paddingLeft, paddingTop,
                        paddingLeft + contentWidth, paddingTop + contentHeight);
                mExampleDrawable.draw(canvas);
            }




        }
        public void setRunning(boolean b) {
            threadRunning =b;
            mRun=b;
        }
        public void doStart(){



        }
        @Override
        public void run() {
            while (mRun) {
                Canvas c = null;
                try {
                    c = mSurfaceHolder.lockCanvas(null);
                    synchronized (mSurfaceHolder) {
                        // Critical section. Do not allow mRun to be set false until
                        // we are sure all canvas draw operations are complete.
                        //
                        // If mRun has been toggled false, inhibit canvas operations.
                        synchronized (mRunLock) {
                            if (mRun) doDraw(c);
                        }
                    }
                } finally {
                    // do this in a finally so that if an exception is thrown
                    // during the above, we don't leave the Surface in an
                    // inconsistent state
                    if (c != null) {
                        mSurfaceHolder.unlockCanvasAndPost(c);
                    }
                }
            }
        }

        public void setSurfaceSize(int width, int height) {
            // synchronized to make sure these all change atomically
            synchronized (mSurfaceHolder) {
                mCanvasWidth = width;
                mCanvasHeight = height;

                // don't forget to resize the background image
                mBackgroundImage = Bitmap.createScaledBitmap(
                        mBackgroundImage, width, height, true);
            }
        }

    }



    public GraficoView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // register our interest in hearing about changes to our surface
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);

        // create thread only; it's started in surfaceCreated()
        thread = new GraficoThread(holder, context, new Handler() {
            @Override
            public void handleMessage(Message m) {
                mStatusText.setVisibility(View.VISIBLE);
                mStatusText.setText(m.getData().getString("text"));
            }
        });

        setFocusable(true); // make sure we get key events
       // init(attrs, 0);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.GraficoView, defStyle, 0);

        mExampleString = a.getString(
                R.styleable.GraficoView_exampleString);
        mExampleColor = a.getColor(
                R.styleable.GraficoView_exampleColor,
                mExampleColor);
        // Use getDimensionPixelSize or getDimensionPixelOffset when dealing with
        // values that should fall on pixel boundaries.
        mExampleDimension = a.getDimension(
                R.styleable.GraficoView_exampleDimension,
                mExampleDimension);

        if (a.hasValue(R.styleable.GraficoView_exampleDrawable)) {
            mExampleDrawable = a.getDrawable(
                    R.styleable.GraficoView_exampleDrawable);
            mExampleDrawable.setCallback(this);
        }

        a.recycle();

        // Set up a default TextPaint object
        mTextPaint = new TextPaint();
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.LEFT);

        // Update TextPaint and text measurements from attributes
        invalidateTextPaintAndMeasurements();
    }

    private void invalidateTextPaintAndMeasurements() {
        mTextPaint.setTextSize(mExampleDimension);
        mTextPaint.setColor(mExampleColor);
        mTextWidth = mTextPaint.measureText(mExampleString);

        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        mTextHeight = fontMetrics.bottom;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.restore();
        canvas.drawColor(Color.BLUE);
        // Draw the text.
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(RED);
        canvas.drawText(mExampleString, 20,  30,  mTextPaint);

        // Draw the example drawable on top of the text.
        if (mExampleDrawable != null) {
            mExampleDrawable.setBounds(paddingLeft, paddingTop,
                    paddingLeft + contentWidth, paddingTop + contentHeight);
            mExampleDrawable.draw(canvas);
        }
    }

    /**
     * Gets the example string attribute value.
     *
     * @return The example string attribute value.
     */
    public String getExampleString() {
        return mExampleString;
    }

    /**
     * Sets the view's example string attribute value. In the example view, this string
     * is the text to draw.
     *
     * @param exampleString The example string attribute value to use.
     */
    public void setExampleString(String exampleString) {
        mExampleString = exampleString;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example color attribute value.
     *
     * @return The example color attribute value.
     */
    public int getExampleColor() {
        return mExampleColor;
    }

    /**
     * Sets the view's example color attribute value. In the example view, this color
     * is the font color.
     *
     * @param exampleColor The example color attribute value to use.
     */
    public void setExampleColor(int exampleColor) {
        mExampleColor = exampleColor;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example dimension attribute value.
     *
     * @return The example dimension attribute value.
     */
    public float getExampleDimension() {
        return mExampleDimension;
    }

    /**
     * Sets the view's example dimension attribute value. In the example view, this dimension
     * is the font size.
     *
     * @param exampleDimension The example dimension attribute value to use.
     */
    public void setExampleDimension(float exampleDimension) {
        mExampleDimension = exampleDimension;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example drawable attribute value.
     *
     * @return The example drawable attribute value.
     */
    public Drawable getExampleDrawable() {
        return mExampleDrawable;
    }

    /**
     * Sets the view's example drawable attribute value. In the example view, this drawable is
     * drawn above the text.
     *
     * @param exampleDrawable The example drawable attribute value to use.
     */
    public void setExampleDrawable(Drawable exampleDrawable) {
        mExampleDrawable = exampleDrawable;
    }
        public void surfaceCreated(SurfaceHolder holder) {
            // start the thread here so that we don't busy-wait in run()
            // waiting for the surface to be created
            thread.setRunning(true);
          thread.start();
    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        thread.setSurfaceSize(width, height);
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        // we have to tell thread to shut down & wait for it to finish, or else
        // it might touch the Surface after we return and explode
        boolean retry = true;
        thread.setRunning(false);
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }


}
