package com.appfabet.Models;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;



import android.os.Environment;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


import com.appfabet.ml.EmnistModel1;
import com.appfabet.ml.Mnist2;
import com.appfabet.ml.MnistModel;
import com.appfabet.ml.Model;
import com.appfabet.ml.ModelRGBA;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.Interpreter;

import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;


public class DrawArea extends View
{
    private final int paintColor = Color.BLACK;
    private Paint drawPaint;
    private Path path = new Path();
    private boolean isToClear = false;
    private Interpreter interpreter;

    public DrawArea(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        setupPaint();
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        if(isToClear)
        {
            isToClear = false;
        }
        else {
            canvas.drawPath(path, drawPaint);
        }

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

    public void clearArea() {
        isToClear = true;
        path = new Path();
        this.invalidate();

    }

    private void setupPaint() {
        drawPaint = new Paint();
        drawPaint.setColor(paintColor);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(50);
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
            EmnistModel1 model = EmnistModel1.newInstance(this.getContext());

            // Creates inputs for reference.
            Bitmap bitmap = this.getBitmapFromView();

            Bitmap scaledBitmap = getResizedBitmap(bitmap, 28, 28);
            //System.out.println(scaledBitmap.getAllocationByteCount());

            exportBitmapToAppFiles(scaledBitmap);

            ByteBuffer byteBuffer = ByteBuffer.allocate(1*28*28*1*4);
            byteBuffer.rewind();
            byteBuffer.order(ByteOrder.nativeOrder());

            if (scaledBitmap != null) {
                System.out.println("copying to buffer");
                scaledBitmap.copyPixelsToBuffer(byteBuffer);
            }


//            int[] intValues = new int[32 * 32];
//            bitmap.getPixels(intValues, 0, scaledBitmap.getWidth(), 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight());

//            for (int i=0; i<(32*32*4); i+=4)
//            {
//                if(byteBuffer.array()[i] == -12)
//                {
//                    byteBuffer.array()[i] = 0;
//                    byteBuffer.array()[i+1] = 0;
//                    byteBuffer.array()[i+2] = 0;
//                    byteBuffer.array()[i+3] = 0;
//                }
//                else {
//                    byteBuffer.array()[i] = -1;
//                    byteBuffer.array()[i+1] = -1;
//                    byteBuffer.array()[i+2] = -1;
//                    byteBuffer.array()[i+3] = -1;
//                }
//            }

            byteBuffer.rewind();
            byteBuffer.order(ByteOrder.nativeOrder());
            for (int i=0; i<(28*28*4); i+=4)
            {
                if(byteBuffer.array()[i] == -12)
                {
                    byteBuffer.putFloat(0.0f);
                }
                else {
                    byteBuffer.putFloat(255.0f);
                }
            }


            //byteBuffer.rewind();

//            for (int i=0; i<(32*32*4); i++)
//            {
//
//                byteBuffer.array()[i] = (byte) (byteBuffer.array()[i] & 0xff);
//
//            }


//            ByteBuffer byteBuffer2 = ByteBuffer.allocate(1*32*32*1);
//            byteBuffer2.rewind();
//            int j = 0;
//
//            for (int i=0; i<(32*32*4); i+=4)
//            {
//                if(byteBuffer.array()[i] == 0)
//                {
//                    byteBuffer2.array()[j] = 0;
//                }
//                else {
//                    byteBuffer2.array()[j] = (byte) 255;
//                }
//                j++;
//            }


            //byteBuffer.rewind();
            //byteBuffer = convertBitmapToByteBuffer(scaledBitmap);

            //convertBitmapToByteBufferYep(scaledBitmap, byteBuffer);


            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 28, 28}, DataType.FLOAT32);
            inputFeature0.loadBuffer(byteBuffer);

            //TensorImage tensorImage = TensorImage.fromBitmap(scaledBitmap);

            // Runs model inference and gets result.
            EmnistModel1.Outputs outputs = model.process(inputFeature0);
            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();


            float num = 0;
            int numerator =0;
            int finalNum =0;
            for(float i: outputFeature0.getFloatArray())
            {

                System.out.println(numerator + " - " + i);


                if(i>num){
                    num = i;
                    finalNum = numerator;
                }
                numerator++;
            }

            System.out.println(finalNum + " - " + num);
            System.out.println(Environment.getExternalStorageDirectory().toString());

            // Releases model resources if no longer used.
            model.close();

        } catch (IOException e) {
            // TODO Handle the exception
        }
    }

    public void validateModel(){

        File model = new File("/home/michal/AndroidStudioProjects/Appfabet/app/src/main/ml");
        interpreter = new Interpreter(model);

        Bitmap bitmap = this.getBitmapFromView();

        Bitmap scaledBitmap = getResizedBitmap(bitmap, 32, 32);

        ByteBuffer byteBuffer = ByteBuffer.allocate(1*32*32*1*4);
        byteBuffer.rewind();

        if (scaledBitmap != null) {
            System.out.println("copying to buffer");
            scaledBitmap.copyPixelsToBuffer(byteBuffer);
        }

        TensorBuffer outputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 32, 32, 4}, DataType.FLOAT32);

        try {
            interpreter.run(byteBuffer, outputFeature0);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
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
        //bm.recycle();
        return resizedBitmap;
    }

    private Bitmap toGrayscale(Bitmap bmpOriginal)
    {
        int width, height;
        height = bmpOriginal.getHeight();
        width = bmpOriginal.getWidth();

        Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmpGrayscale);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bmpOriginal, 0, 0, paint);
        return bmpGrayscale;
    }

    private ByteBuffer convertBitmapToByteBuffer(Bitmap bitmap) {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * 1 * 32 * 32 * 1);
        byteBuffer.order(ByteOrder.nativeOrder());
        int[] intValues = new int[32 * 32];
        bitmap.getPixels(intValues, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
        int pixel = 0;
        for (int i = 0; i < 32; ++i) {
            for (int j = 0; j < 32; ++j) {
                final int val = intValues[pixel++];
                byteBuffer.putFloat((((val >> 16) & 0xFF)-128)/128f);
                byteBuffer.putFloat((((val >> 8) & 0xFF)-128)/128f);
                byteBuffer.putFloat((((val) & 0xFF)-128)/128f);
            }
        }
        return byteBuffer;
    }


    private void convertBitmapToByteBufferYep(Bitmap bitmap, ByteBuffer imgData) {
        if (imgData == null) {
            return;
        }
        imgData.rewind();

        int[] intValues = new int[32 * 32];
        bitmap.getPixels(intValues, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());


        // Convert the image to floating point.
        int pixel = 0;

        for (int i = 0; i < 32; ++i) {
            for (int j = 0; j < 32; ++j) {
                final int val = intValues[pixel++];

                imgData.putFloat(((val>> 16) & 0xFF) / 255.f);
                imgData.putFloat(((val>> 8) & 0xFF) / 255.f);
                imgData.putFloat((val & 0xFF) / 255.f);
            }
        }
    }

    private void exportBitmapToAppFiles(Bitmap bitmap)
    {
        try {
            FileOutputStream fileOutputStream = this.getContext().openFileOutput("bitmap.png", Context.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
