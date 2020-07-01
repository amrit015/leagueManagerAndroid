package com.android.leaguemanager.customDialogs;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by amritkhatiwada on 06/28/2020.
 */

// used to change the attributes of the alertdialog/dialogs
public class CustomAlertsProperties {

    public static void setUpCustomDialog(Dialog dialog, Window window) {
        Rect displayRectangle = new Rect();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
        if (dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public static void repositionDialogIntoScreen(Dialog dialog, Display display, View layout) {
        if (dialog.getWindow()!=null) {
            WindowManager.LayoutParams windowParams = dialog.getWindow().getAttributes();
            Point displaySize = new Point();
            display.getSize(displaySize);
            int maxX = displaySize.x;
            int maxY = displaySize.y;
            windowParams.gravity = Gravity.TOP | Gravity.START;
            windowParams.x = 0;   //x position
            windowParams.y = maxY;   //y position
            layout.setMinimumWidth(maxX);
        }
    }

    public static void changeAttributes(Dialog dialog) {
        // set the screen width and height for the dialog after showing the dialog
        WindowManager.LayoutParams lWindowParams = new WindowManager.LayoutParams();
        if (dialog.getWindow()!=null) {
            lWindowParams.copyFrom(dialog.getWindow().getAttributes());
            lWindowParams.width = WindowManager.LayoutParams.MATCH_PARENT; // this is where the dialog will fit into the width of the screen
            lWindowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setAttributes(lWindowParams);
        }
    }

    public static void unRepositionIntoBottom(Dialog dialog, Display display,View layout) {
        if (dialog.getWindow()!=null) {
            WindowManager.LayoutParams windowParams = dialog.getWindow().getAttributes();
            Point displaySize = new Point();
            display.getSize(displaySize);
            int maxX = displaySize.x;
            int maxY = displaySize.y;
            windowParams.gravity = Gravity.TOP | Gravity.START;
            windowParams.x = 0;   //x position
            windowParams.y = maxY;   //y position
            layout.setMinimumHeight(maxY);
        }
    }
}
