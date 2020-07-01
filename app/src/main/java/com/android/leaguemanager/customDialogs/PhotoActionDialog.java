package com.android.leaguemanager.customDialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.leaguemanager.R;
import com.android.leaguemanager.utils.UtilBitmap;

import java.io.File;
import java.io.IOException;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

public class PhotoActionDialog {

    private PhotoListener photoListener;
    private Context context;
    private boolean isPhotoSelected;
    private ImageView imgAddPhoto;
    private Window window;
    private Display display;

    public PhotoActionDialog(boolean isPhotoSelected, ImageView imgAddPhoto, Context context, PhotoListener photoListener, Window window, Display display) {
        this.isPhotoSelected = isPhotoSelected;
        this.imgAddPhoto = imgAddPhoto;
        this.context = context;
        this.photoListener = photoListener;
        this.window = window;
        this.display = display;
    }

    public void photoOperation() {
        if (isPhotoSelected) editRemovePhotoOnSelection();
        else {
            callbackToCheckPermission();
        }
    }

    private void callbackToCheckPermission() {
        photoListener.checkPermission();
    }

    private void editRemovePhotoOnSelection() {
        if (context != null) {
            final Dialog dialog = new Dialog(context);

            // setup the custom dialog with rounded corners
            CustomAlertsProperties.setUpCustomDialog(dialog, window);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert inflater != null;
            @SuppressLint("InflateParams") View layout = inflater.inflate(R.layout.dialog_action_edit, null);

            CardView cvEditPhoto = layout.findViewById(R.id.card_view_edit_photo);
            CardView cvRemovePhoto = layout.findViewById(R.id.card_view_remove_photo);
            TextView tvCancel = layout.findViewById(R.id.tv_cancel);

            // reposition the dialog to be at the bottom
            CustomAlertsProperties.repositionDialogIntoScreen(dialog, display, layout);
            dialog.setContentView(layout);
            dialog.show();
            // fit the dialog into the width of the device
            CustomAlertsProperties.changeAttributes(dialog);

            cvEditPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    callbackToCheckPermission();
                }
            });

            cvRemovePhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    removePhotoOnSelection();
                }
            });

            tvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }
    }

    private void removePhotoOnSelection() {
        int color = R.color.grey_black;
        imgAddPhoto.setImageResource(R.drawable.navigation_profile);
        imgAddPhoto.setColorFilter(ContextCompat.getColor(context, color), android.graphics.PorterDuff.Mode.SRC_IN);
        isPhotoSelected = false;
        photoListener.getPhotoAction(isPhotoSelected, null, true);
    }

    public void afterPermissionGranted() {
        addPhotoAlert();
    }

    public void addPhotoAlert() {
        final Dialog dialog = new Dialog(context);

        // setup the custom dialog with rounded corners
        CustomAlertsProperties.setUpCustomDialog(dialog, window);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        @SuppressLint("InflateParams") View layout = inflater.inflate(R.layout.dialog_action_photo, null);

        CardView cvTakePhoto = layout.findViewById(R.id.card_view_take_photo);
        CardView cvChoosePhoto = layout.findViewById(R.id.card_view_choose_photo);
        TextView tvCancel = layout.findViewById(R.id.tv_cancel);

        // reposition the dialog to be at the bottom
        CustomAlertsProperties.repositionDialogIntoScreen(dialog, display, layout);
        dialog.setContentView(layout);
        dialog.show();
        // fit the dialog into the width of the device
        CustomAlertsProperties.changeAttributes(dialog);

        cvTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                takePhoto();
            }
        });

        cvChoosePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                choosePicture();
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void takePhoto() {
        // Create the File where the photo should go
        try {
            File photoFile = UtilBitmap.createImageFile(context);
            // Save a file: path for use with ACTION_VIEW intents
            String mCurrentPhotoPath = photoFile.getAbsolutePath();
            photoListener.openCameraToCaptureImage(photoFile, mCurrentPhotoPath);
        } catch (IOException ex) {
            // Error occurred while creating the File
            ex.printStackTrace();
        }
    }

    private void choosePicture() {
        photoListener.getImageFromStorage();
    }

    /*
    public void attachPhotoToQuickLogs(String photo, Bitmap bitmap) {
        tvAddPhoto.setText(context.getResources().getString(R.string.attached));
        tvAddPhoto.setTextColor(context.getResources().getColor(R.color.orangeyYellow));
        if (bitmap == null) {
            previewPhotoOnQuickLogs(photo);
        } else {
            previewPhotoOnQuickLogs(bitmap);
        }
        isPhotoSelected = true;
        notePhotoListener.getPhotoAction(isPhotoSelected, bitmap, false);
    }

    private void previewPhotoOnQuickLogs(Bitmap bitmap) {
        Bitmap previewBitmap = BitmapUtil.getResizedBitmap(bitmap, 25);
        GlideAppUtil.loadDefaultIconAsBitmapOnQuicklogs(context, previewBitmap, imgAddPhoto);
    }

    private void previewPhotoOnQuickLogs(String photo) {
        int image = BranchSpecificValues.getDrawableForAddPhoto();
        GlideAppUtil.loadImageViewWithOutProgressBar(context, photo, image, imgAddPhoto);
    }

     */
}
