package com.android.leaguemanager.customDialogs;

import android.graphics.Bitmap;

import java.io.File;

public interface PhotoListener {

    void checkPermission();

    void getImageFromStorage();

    void openCameraToCaptureImage(File photoFile, String mCurrentPhotoPath);

    void getPhotoAction(boolean isPhotoSelected, Bitmap selectedPhoto, boolean isRemoved);
}
