package com.appfabet.Models;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

import android.util.AttributeSet;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.View;


import androidx.annotation.NonNull;

import com.appfabet.ml.Model;

import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DrawArea extends View
{
    private final int paintColor = Color.BLACK;
    private Paint drawPaint;
    private List<Point> circlePoints;
    private Path path = new Path();

    public DrawArea(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        setupPaint();
        circlePoints = new ArrayList<Point>();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(path, drawPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float pointX = event.getX();
        float pointY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                path.moveTo(pointX, pointY);
                break;
            case MotionEvent.ACTION_MOVE:

                path.lineTo(pointX, pointY);
                break;
            default:
                return false;
        }

        postInvalidate(); // Indicate view should be redrawn
        return true;
    }

    private void setupPaint() {
        drawPaint = new Paint();
        drawPaint.setColor(paintColor);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(5);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
        drawPaint.setStyle(Paint.Style.STROKE);
    }

    public Bitmap getBitmapFromView() {
        Bitmap b = Bitmap.createBitmap( this.getLayoutParams().width, this.getLayoutParams().height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        this.layout(this.getLeft(), this.getTop(), this.getRight(), this.getBottom());
        this.draw(c);
        return b;
    }

    public void checkModel()
    {
        try {
            Model model = Model.newInstance(this.getContext());

            // Creates inputs for reference.
            Bitmap bitmap = this.getBitmapFromView();

            Bitmap scaledBitmap = getResizedBitmap(bitmap, 32, 32);
            //scaledBitmap.setWidth(32);


            System.out.println("testoviron");
            System.out.println(scaledBitmap.getHeight());
            System.out.println(scaledBitmap.getWidth());



            TensorImage image = TensorImage.fromBitmap(scaledBitmap);

            // Runs model inference and gets result.
            Model.Outputs outputs = model.process(image.getTensorBuffer());
            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

            System.out.println(outputFeature0);

            // Releases model resources if no longer used.
            model.close();

        } catch (IOException e) {
            // TODO Handle the exception
        }
    }

    private Bitmap scaleDown(Bitmap realImage, float maxImageSize,
                                   boolean filter) {
        float ratio = Math.min(
                (float) maxImageSize / realImage.getWidth(),
                (float) maxImageSize / realImage.getHeight());
        int width = Math.round((float) ratio * realImage.getWidth());
        int height = Math.round((float) ratio * realImage.getHeight());

        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width,
                height, filter);
        return newBitmap;
    }

    private Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }


}
