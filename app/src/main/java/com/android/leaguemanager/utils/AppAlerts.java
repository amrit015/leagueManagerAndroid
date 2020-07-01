package com.android.leaguemanager.utils;

import android.content.Context;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import androidx.constraintlayout.widget.ConstraintLayout;

public class AppAlerts {

    public static void showToast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showSnackBar(ConstraintLayout layout, String message){
        Snackbar.make(layout, message, Snackbar.LENGTH_SHORT).show();
    }
}
