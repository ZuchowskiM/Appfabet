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

import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


import com.appfabet.ml.ConvEmnistEnBig;
import com.appfabet.ml.ConvEmnistEnSmall;
import com.appfabet.ml.ConvMnist;
import com.appfabet.ml.ConvPolcharsSmall;


import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

///MATI NIE DOTYKAJ DRAW AREA///////
///A W XML ZAPYTAC PRZED WPROWADZANIEM ZMIAN DO DRAWING///
public class DrawArea extends View
{
    private final int paintColor = Color.BLACK;
    private Paint drawPaint;
    private Path path = new Path();
    private boolean isToClear = false;
    private int modelSize = 28;
    private float percentage;
    private LevelType currentLevelType;


    public DrawArea(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        setupPaint();

    }

    public void setCurrentLevelType(LevelType currentLevelType) {
        this.currentLevelType = currentLevelType;

        System.out.println(currentLevelType);
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

        System.out.println(this.getMeasuredWidth() + " " + this.getMeasuredHeight());
        //Bitmap b = Bitmap.createBitmap( this.getLayoutParams().width, this.getLayoutParams().height, Bitmap.Config.ARGB_8888);
        Bitmap b = Bitmap.createBitmap( this.getMeasuredWidth(), this.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas c = new Canvas(b);
        this.layout(this.getLeft(), this.getTop(), this.getRight(), this.getBottom());
        this.draw(c);
        return b;
    }

    ///MATI NIE DOTYKAJ DRAW AREA///////
    ///A W XML ZAPYTAC PRZED WPROWADZANIEM ZMIAN DO DRAWING///
    public String checkModel()
    {
        try {

            if(currentLevelType == LevelType.BIG_LETTERS) {

                modelSize = 28;

                ConvEmnistEnBig model = ConvEmnistEnBig.newInstance(this.getContext());
                TensorBuffer inputFeature0 = makeNumberModelCalculations();

                // Runs model inference and gets result.
                ConvEmnistEnBig.Outputs outputs = model.process(inputFeature0);
                TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

                int finalIndex = printTensorOutput(outputFeature0);

                //DEBUG
                //System.out.println(Environment.getExternalStorageDirectory().toString());

                OutputInterpreter outputInterpreter = new OutputInterpreter(getContext());

                //outputInterpreter.createArrayFromJson();


                model.close();

                return outputInterpreter.getResultFromBigDictionary(finalIndex);
            }
            else if(currentLevelType == LevelType.SMALL_LETTERS){

                modelSize = 28;

                ConvEmnistEnSmall model = ConvEmnistEnSmall.newInstance(this.getContext());
                TensorBuffer inputFeature0 = makeNumberModelCalculations();

                // Runs model inference and gets result.
                ConvEmnistEnSmall.Outputs outputs = model.process(inputFeature0);
                TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

                int finalIndex = printTensorOutput(outputFeature0);

                //DEBUG
                //System.out.println(Environment.getExternalStorageDirectory().toString());

                OutputInterpreter outputInterpreter = new OutputInterpreter(getContext());

                //outputInterpreter.createArrayFromJson();


                model.close();

                return outputInterpreter.getResultFromSmallDictionary(finalIndex);

            }
            else {

                modelSize = 28;

                ConvMnist model = ConvMnist.newInstance(this.getContext());
                TensorBuffer inputFeature0 = makeNumberModelCalculations();

                // Runs model inference and gets result.
                ConvMnist.Outputs outputs = model.process(inputFeature0);
                TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

                int finalIndex = printTensorOutput(outputFeature0);

                //DEBUG
                //System.out.println(Environment.getExternalStorageDirectory().toString());

                OutputInterpreter outputInterpreter = new OutputInterpreter(getContext());

                //outputInterpreter.createArrayFromJson();


                model.close();

                return outputInterpreter.getResultFromNumberDictionary(finalIndex);
            }


        } catch (IOException e) {
            // TODO Handle the exception
            return "error in checkModel()";
        }
    }

    private TensorBuffer makeNumberModelCalculations(){
        // Creates inputs for reference.
        Bitmap bitmap = this.getBitmapFromView();

        Bitmap scaledBitmap = getResizedBitmap(bitmap, modelSize, modelSize);

        //DEBUG
        //System.out.println(scaledBitmap.getAllocationByteCount());
        //exportBitmapToAppFiles(scaledBitmap);

        ByteBuffer byteBuffer = ByteBuffer.allocate(1*modelSize*modelSize*1*4);
        byteBuffer.rewind();
        byteBuffer.order(ByteOrder.nativeOrder());

        if (scaledBitmap != null) {
            System.out.println("copying to buffer");
            scaledBitmap.copyPixelsToBuffer(byteBuffer);
        }

        byteBuffer.rewind();
        byteBuffer.order(ByteOrder.nativeOrder());
        for (int i=0; i<(modelSize*modelSize*4); i+=4)
        {
            if(byteBuffer.array()[i+3] != 0)
            {
                //for emnist model 1.0
                //for emnistPL 0.0
                byteBuffer.putFloat(1.0f);
            }
            else {
                //for emnist model 0.0
                //for emnistPL 1.0
                byteBuffer.putFloat(0.0f);
            }
        }

        //DEBUG
        //printByteBufferAs2DArray(byteBuffer);

        TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, modelSize, modelSize}, DataType.FLOAT32);
        inputFeature0.loadBuffer(byteBuffer);

        return inputFeature0;
    }

    private int printTensorOutput(TensorBuffer tensorBuffer){

        float num = 0;
        int numerator =0;
        int finalNum =0;
        for(float i: tensorBuffer.getFloatArray())
        {
            System.out.println(numerator + " - " + i);

            if(i>num){
                num = i;
                finalNum = numerator;
                percentage = i;
            }
            numerator++;
        }

        System.out.println(finalNum + " - " + num);
        return finalNum;
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

    //unused
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

    //DEBUG FUN
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


    //DEBUG
    private void printByteBufferAs2DArray(ByteBuffer byteBuffer){

        int k=0;
        float toPrint;
        byteBuffer.rewind();

        for(int i=0; i< modelSize; i++)
        {
            for(int j=0; j<modelSize; j++){

                toPrint = byteBuffer.getFloat();
                System.out.print(toPrint + " ");
                k++;
            }
            System.out.println();
        }
    }


    public float getPercentage() {
        return percentage;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }
}
