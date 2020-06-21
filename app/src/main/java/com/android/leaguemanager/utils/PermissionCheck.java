package com.android.leaguemanager.utils;

import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

// permission check class
public class PermissionCheck {

    public boolean getPermissionStatus(Context context, String[] mPermission) {
        return checkForPermission(context, mPermission);
    }

    private boolean checkForPermission(Context context, String[] mPermission) {
        boolean check = false;
        // api 23+ runtime app permission
        try {
            for (String s : mPermission) {
                check = ActivityCompat.checkSelfPermission(context, s)
                        == PackageManager.PERMISSION_GRANTED;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return check;
    }

    // checking if the permission is granted
    public boolean getPermissionGranted(int requestCode, int[] grantResults, int specifiedCode) {
        boolean isPermission = false;
        if (requestCode == specifiedCode) {

            for (int grantResult : grantResults) {
                isPermission = grantResult == PackageManager.PERMISSION_GRANTED;
            }
        }
        return isPermission;
    }

    // checking if the permission is denied
    public boolean getPermissionDenied(int requestCode, int[] grantResults, int specifiedCode) {
        boolean isAction = true;
        if (requestCode == specifiedCode) {
            for (int grantResult : grantResults) {
                if (grantResult == PackageManager.PERMISSION_DENIED) {
                    isAction = false;
                    break;
                }
            }
        }
        return isAction;
    }
}