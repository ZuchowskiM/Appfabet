package com.appfabet.Models;

import android.util.Log;

import java.nio.ByteBuffer;

public class ImageComparatorBMP {

    private final ByteBuffer bbA;
    private final ByteBuffer bbB;

    private final double blacksA;
    private final double blacksB;
    private final double blacksRatio;
    private double blacks_ratio_minimum;
    private final boolean blacks_overfit;

    public ImageComparatorBMP(ByteBuffer pattern, ByteBuffer toCompare, double blacks_tolerance) {
        this.bbA = pattern;
        this.bbB = toCompare;

        assert this.bbA != null;
        assert this.bbB != null;
        assert this.bbA.capacity() == this.bbB.capacity();

        this.blacks_ratio_minimum = 0.0;

        this.blacksA = calc_blackA();
        this.blacksB = calc_blackB();
        this.blacksRatio = calc_blacks();
        this.blacks_overfit = blacks_overfit(blacks_tolerance);
    }

    public void setBlacks_ratio_minimum(double blacks_ratio_minimum) {
        this.blacks_ratio_minimum = blacks_ratio_minimum;
    }

    private double calc_blackA(){
        double blacks = 0;
        for (int y = 3; y < bbA.capacity(); y+=4)
            if (bbA.get(y) != 0)
                blacks++;
        return blacks;
    }

    private double calc_blackB(){
        double blacks = 0;
        for (int y = 3; y < bbB.capacity(); y+=4)
            if (bbB.get(y) != 0)
                blacks++;
        return blacks;
    }

    private double calc_blacks()
    {
        double blacksBoth = 0;
        for (int y = 3; y < bbA.capacity(); y+=4) {

            if(bbA.get(y) != 0 && bbB.get(y) != 0)
                blacksBoth++;
        }
        System.out.println("blacksA: " + this.blacksA + " blacksB: " + this.blacksB +  "  blacksBoth: " + blacksBoth);
        return blacksBoth / this.blacksA * 100;
    }

    private boolean blacks_overfit(double tolerance) {
        Log.d("fit", String.valueOf(this.blacksA * tolerance));
        return this.blacksA * tolerance > this.blacksB;
    }

    public boolean isGood() {
        if (blacksRatio > blacks_ratio_minimum)
            return blacks_overfit;

        return false;
    }
}
