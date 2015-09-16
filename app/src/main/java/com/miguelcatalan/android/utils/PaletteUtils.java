package com.miguelcatalan.android.utils;

import android.support.v7.graphics.Palette;

import static com.miguelcatalan.android.utils.LogUtils.LOGD;

/**
 * @author Miguel Catalan Bañuls
 */
public class PaletteUtils {

    private static final String TAG = LogUtils.makeLogTag("PaletteUtils");

    public static Palette.Swatch getSwatch(Palette palette) {
        Palette.Swatch swatch = null;
        if (palette != null) {
            swatch = palette.getMutedSwatch();
            if (swatch == null) {
                LOGD(TAG, "No mutedSwatch");
                swatch = palette.getVibrantSwatch();
                if (swatch != null) {
                    LOGD(TAG, "No vibrantSwatch");
                }
            }
        } else {
            LOGD(TAG, "Using default palette");
        }
        return swatch;
    }
}