package com.android.leaguemanager.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.android.leaguemanager.di.GlideApp;

public class UtilGlideApp {

    public static void loadBasicImageViewAsBitmap(Context context, Bitmap bitmap, ImageView imgUserPicture){
        GlideApp.with(context)
                .asBitmap()
                .load(bitmap)
                .circleCrop()
                .into(imgUserPicture);
    }
}
