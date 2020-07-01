package com.android.leaguemanager.homePage.homePageFragments;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.leaguemanager.BuildConfig;
import com.android.leaguemanager.R;
import com.android.leaguemanager.customDialogs.PhotoActionDialog;
import com.android.leaguemanager.customDialogs.PhotoListener;
import com.android.leaguemanager.di.LeagueApp;
import com.android.leaguemanager.utils.AppKeys;
import com.android.leaguemanager.utils.AppLogs;
import com.android.leaguemanager.utils.PermissionCheck;
import com.android.leaguemanager.utils.SharedPreferenceHelper;
import com.android.leaguemanager.utils.UtilBitmap;
import com.android.leaguemanager.utils.UtilGlideApp;

import java.io.ByteArrayOutputStream;
import java.io.File;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import static android.app.Activity.RESULT_CANCELED;

public class UserProfileFragment extends Fragment implements View.OnClickListener, PhotoListener {

    ImageView imgProfile;
    TextView tvUsername;
    RelativeLayout layoutProfilePhoto;
    private boolean isPhotoSelected = false;
    PhotoActionDialog photoActionDialog;

    private boolean isPermissionPhoto = false;
    private PermissionCheck permissionCheck;
    private String[] mPermission = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
    private int CAPTURE_IMAGE = 99;
    private String mCurrentPhotoPath;
    private int PICK_IMAGE = 100;
    private Bitmap bitmap = null;
    private String TAG = "UserProfileFragment";

    @Inject
    SharedPreferenceHelper sharedPreferenceHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user_profile, container, false);
        if (getActivity() != null) {
            ((LeagueApp) getActivity().getApplication()).getApplicationComponent().inject(this);
        }
        TextView tvToolbarTitle = rootView.findViewById(R.id.toolbar_title);
        imgProfile = rootView.findViewById(R.id.img_profile);
        tvUsername = rootView.findViewById(R.id.tv_profile_name);
        layoutProfilePhoto = rootView.findViewById(R.id.layout_profile_image);
        tvToolbarTitle.setText(getResources().getString(R.string.profile));
        loadUsername();
        layoutProfilePhoto.setOnClickListener(this);
        return rootView;
    }

    private void loadUsername() {
        if (sharedPreferenceHelper.getUsername().length() > 0) {
            tvUsername.setText(sharedPreferenceHelper.getUsername());
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_profile_image:
                openPhotoDialog();
                break;
            default:
                break;
        }
    }

    private void openPhotoDialog() {
        Window window = getActivity().getWindow();
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        photoActionDialog = new PhotoActionDialog(isPhotoSelected, imgProfile, getActivity(), this, window, display);
        photoActionDialog.photoOperation();

        // TODO: fix the photos open from Photos
    }

    @Override
    public void checkPermission() {
        checkForPermissionAndAddPhoto();
    }

    private void checkForPermissionAndAddPhoto() {
        permissionCheck = new PermissionCheck();
        boolean isGranted = permissionCheck.getPermissionStatus(getActivity(), mPermission);
        if (!isGranted) {
            ActivityCompat.requestPermissions(getActivity(),
                    mPermission, AppKeys.KEY_APP_PERMISSION_STORAGE_CAMERA_PHOTO);
        } else {
            isPermissionPhoto = true;
            openPhotoActionPopUp();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        isPermissionPhoto = permissionCheck.getPermissionGranted(requestCode, grantResults, AppKeys.KEY_APP_PERMISSION_STORAGE_CAMERA_PHOTO);

        // get the results if denied and take action accordingly
        if (requestCode == AppKeys.KEY_APP_PERMISSION_STORAGE_CAMERA_PHOTO) {
            if (isPermissionPhoto) openPhotoActionPopUp();
            else
                Toast.makeText(getActivity(), getResources().getString(R.string.enable_permission), Toast.LENGTH_LONG).show();
        }
    }

    private void openPhotoActionPopUp() {
        if (photoActionDialog != null)
            photoActionDialog.afterPermissionGranted();
    }

    @Override
    public void getImageFromStorage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, getResources().getString(R.string.select_photo)), PICK_IMAGE);
    }

    @Override
    public void openCameraToCaptureImage(File photoFile, String mCurrentPhotoPath) {
        if (photoFile != null) {
            this.mCurrentPhotoPath = mCurrentPhotoPath;
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            Uri outputFileUri = FileProvider.getUriForFile(getActivity(), BuildConfig.APPLICATION_ID + ".provider", photoFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            intent.putExtra("android.intent.extras.CAMERA_FACING", 1);
            startActivityForResult(intent, CAPTURE_IMAGE);
        } else {
            AppLogs.i(TAG, "null photoFile");
        }
    }

    @Override
    public void getPhotoAction(boolean isPhotoSelected, Bitmap selectedPhoto, boolean isRemoved) {
        this.isPhotoSelected = isPhotoSelected;
        this.bitmap = selectedPhoto;
    }

    // onActivity result is overridden here to implement the method inside the fragment, also to access the google fit after receiving the permission
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode != RESULT_CANCELED) {
            if (data != null && isPermissionPhoto) {
                Uri selectedImage = data.getData();
                bitmap = UtilBitmap.doNecessaryActionsForSelectedImage(getActivity(), selectedImage);
                loadIntoGlide(bitmap);
            }
        } else if (requestCode == CAPTURE_IMAGE && resultCode != RESULT_CANCELED) {
            if (data != null && isPermissionPhoto) {
                bitmap = UtilBitmap.doNecessaryActionsForCapturedImage(mCurrentPhotoPath);
                loadIntoGlide(bitmap);
            }
        }
    }

    // load the new user profile picture
    private void loadIntoGlide(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
        if (getActivity() != null)
            UtilGlideApp.loadBasicImageViewAsBitmap(getActivity(), bitmap, imgProfile);
    }

}
