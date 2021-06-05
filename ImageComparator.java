package com.company;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageComparator {

    private final BufferedImage imgA, imgB;
    private double whitesA;
    private double whitesB;
    private double blacks;
    private double allPixels;
    private double coverage_error;

    public double getCoverage_error() {
        return coverage_error;
    }

    public double getAllPixels() {
        return allPixels;
    }

    //Black pixels pattern to custom ratio
    public double getBlacks() {
        return blacks;
    }

    public double getWhitesB() {
        return whitesB;
    }

    public double getWhitesA() {
        return whitesA;
    }

    public ImageComparator(String imgA_name, String imgB_name, String _path, double coverage_tolerance) throws IOException {

        File fileA = new File(_path + imgA_name);
        File fileB = new File(_path + imgB_name);

        imgA = ImageIO.read(fileA);
        imgB = ImageIO.read(fileB);

        assert imgA != null;
        assert imgB != null;
        assert imgA.getWidth() == imgB.getWidth();
        assert imgA.getHeight() == imgB.getHeight();

        this.whitesA = calc_whiteA();
        this.whitesB = calc_whiteB();
        this.blacks = calc_blacks();
        this.allPixels = imgA.getHeight() * imgA.getWidth();
        this.coverage_error = calc_coverageError(coverage_tolerance);
    }

//    private long calc_difference()
//    {
//        long diff = 0;
//        for (int y = 0; y < imgB.getHeight(); ++y) {
//            for (int x = 0; x < imgA.getWidth(); ++x) {
//                int rgbA = imgA.getRGB(x, y);
//                int rgbB = imgB.getRGB(x, y);
//
//                int redA = (rgbA >> 16) & 0xff;
//                int greenA = (rgbA >> 8) & 0xff;
//                int blueA = (rgbA) & 0xff;
//                int redB = (rgbB >> 16) & 0xff;
//                int greenB = (rgbB >> 8) & 0xff;
//                int blueB = (rgbB) & 0xff;
//
//                diff += Math.abs(redA - redB);
//                diff += Math.abs(greenA - greenB);
//                diff += Math.abs(blueA - blueB);
//             }
//        }
//        return diff;
//    }

    private double calc_coverageError(double tolerance){
        double diffPattern = 0.0, diffCustom = 0.0;

        //Pattern difference
        diffPattern = getAllPixels() - getWhitesA();

        //Custom difference
        diffCustom = getAllPixels() - getWhitesB();

        double ratio = (Math.abs(diffCustom / diffPattern * 100 - 100));

        if(ratio > tolerance)
            System.out.println("ERROR");

        return ratio;
    }



    public double calc_whiteA(){
        double whites = 0;
        for (int y = 0; y < imgA.getHeight(); ++y) {
            for (int x = 0; x < imgA.getWidth(); ++x) {
                int rgbA = imgA.getRGB(x, y);
                if (rgbA == -1)
                    whites++;
            }
        }
        return whites;
    }

    public double calc_whiteB(){
        double whites = 0;
        for (int y = 0; y < imgB.getHeight(); ++y) {
            for (int x = 0; x < imgB.getWidth(); ++x) {
                int rgbB = imgB.getRGB(x, y);
                if (rgbB == -1)
                    whites++;
            }
        }
        return whites;
    }

    private double calc_blacks()
    {
        double blacksA = 0;
        double blacksBoth = 0;
        for (int y = 0; y < imgB.getHeight(); ++y) {
            for (int x = 0; x < imgA.getWidth(); ++x) {
                int rgbA = imgA.getRGB(x, y);
                int rgbB = imgB.getRGB(x, y);

                if(rgbA != -1)
                    blacksA++;

                if(rgbA != -1 && rgbB != -1)
                    blacksBoth++;
            }
        }
        return blacksBoth / blacksA * 100;
    }


//    private double calc_percentage_diff()
//    {
//        double total_pixels = imgA.getHeight() * imgA.getWidth() * 3;
//        double avg_diff_pixels = calc_difference() / total_pixels;
//
//        return (avg_diff_pixels / 255) * 100;
//    }
}
