package com.appfabet.Models;

import android.app.Activity;
import android.content.Context;
import android.graphics.Insets;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowMetrics;
import android.widget.GridView;

import androidx.annotation.NonNull;

public class ScreenOptions {


    public int measureCellWidth(Activity activity)
    {

        float scalefactor = activity.getResources().getDisplayMetrics().density * 100;
        int number = getScreenWidth(activity);
        int columns = (int) ((float) number / scalefactor) / 2;
        if (columns == 0 || columns == 1)
        {
            return columns = 2;
        }

        return columns-1;
    }

    private static int getScreenWidth(@NonNull Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowMetrics windowMetrics = activity.getWindowManager().getCurrentWindowMetrics();
            Insets insets = windowMetrics.getWindowInsets()
                    .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars());
            return windowMetrics.getBounds().width() - insets.left - insets.right;
        } else {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            return displayMetrics.widthPixels;
        }
    }

}
