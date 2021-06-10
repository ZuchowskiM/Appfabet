package com.appfabet.Models;

import java.nio.ByteBuffer;

public class ImageComparatorBMP {

    private final ByteBuffer bbA;
    private final ByteBuffer bbB;

    private final double whitesA;
    private final double whitesB;
    private final double blacksRatio;
    private final int allPixels;
    private final boolean coverage_error;
    private double blacks_ratio_minimum;

    public ImageComparatorBMP(ByteBuffer pattern, ByteBuffer toCompare, double coverage_tolerance) {
        this.bbA = pattern;
        this.bbB = toCompare;

        assert this.bbA != null;
        assert this.bbB != null;
        assert this.bbA.capacity() == this.bbB.capacity();

        this.blacks_ratio_minimum = 0.0;

        this.whitesA = calc_whiteA();
        this.whitesB = calc_whiteB();
        this.blacksRatio = calc_blacks();
        this.allPixels = bbA.capacity();
        this.coverage_error = calc_coverageError(coverage_tolerance);
    }

    public void setBlacks_ratio_minimum(double blacks_ratio_minimum) {
        this.blacks_ratio_minimum = blacks_ratio_minimum;
    }

    private double calc_whiteA(){
        double whites = 0;
        for (int y = 0; y < bbA.capacity(); ++y)
            if (bbA.get(y) == -1)
                whites++;
        return whites;
    }

    private double calc_whiteB(){
        double whites = 0;
        for (int y = 0; y < bbB.capacity(); ++y)
            if (bbB.get(y) == -1)
                whites++;
        return whites;
    }

    private double calc_blacks()
    {
        double blacksA = 0;
        double blacksBoth = 0;
        for (int y = 0; y < bbA.capacity(); ++y) {

            if(bbA.get(y) != -1)
                blacksA++;

            if(bbA.get(y) != -1 && bbB.get(y) != -1)
                blacksBoth++;
        }
        return blacksBoth / blacksA * 100;
    }

    private boolean calc_coverageError(double tolerance){
        double diffPattern = 0.0, diffCustom = 0.0;

        //Pattern difference
        diffPattern = this.allPixels - this.whitesA;

        //Custom difference
        diffCustom = this.allPixels - this.whitesB;

        double ratio = (Math.abs(diffCustom / diffPattern * 100 - 100));

        return !(ratio > tolerance);
    }

    public boolean isGood() {
        if (blacksRatio > blacks_ratio_minimum)
            return coverage_error;

        return false;
    }
}
