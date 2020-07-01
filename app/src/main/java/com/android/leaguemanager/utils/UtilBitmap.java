package com.android.leaguemanager.utils;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Base64;

import com.android.leaguemanager.BuildConfig;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.core.content.FileProvider;
import androidx.exifinterface.media.ExifInterface;

public class UtilBitmap {

    public static File createImageFile(Context context) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(
                imageFileName,  /* prefix */
                ".png",         /* suffix */
                storageDir      /* directory */
        );
    }

    private static Bitmap performBitmapOperations(String mCurrentPhotoPath) {
        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        bmOptions.inSampleSize = calculateInSampleSize(bmOptions, 1024, 768);
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        bitmap = rotateImageIfRequired(bitmap, mCurrentPhotoPath);
        return bitmap;
    }

    private static Bitmap rotateImageIfRequired(Bitmap bitmap, String mCurrentPhotoPath) {
        ExifInterface ei = null;
        Bitmap rotatedBitmap = null;
        try {
            ei = new ExifInterface(mCurrentPhotoPath);
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED);

            switch (orientation) {

                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotatedBitmap = rotateImage(bitmap, 90);
                    break;

                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotatedBitmap = rotateImage(bitmap, 180);
                    break;

                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotatedBitmap = rotateImage(bitmap, 270);
                    break;

                case ExifInterface.ORIENTATION_NORMAL:
                default:
                    rotatedBitmap = bitmap;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return rotatedBitmap;
    }

    private static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        Bitmap resized = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
        if (resized != source) {
            source.recycle();
        }
        return resized;
    }

    private static Bitmap performCompression(Bitmap realImage) {
        int imgWidth = realImage.getWidth();
        int imgHeight = realImage.getHeight();
        float maxHeight = 1024.0f;
        float maxWidth = 768.0f;
        float imgRatio = imgWidth / imgHeight;
        float maxRatio = maxWidth / maxHeight;


        //      width and height values are set maintaining the aspect ratio of the image
        if (imgHeight > maxHeight || imgWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / imgHeight;
                imgWidth = (int) (imgRatio * imgWidth);
                imgHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / imgWidth;
                imgHeight = (int) (imgRatio * imgHeight);
                imgWidth = (int) maxWidth;
            } else {
                imgHeight = (int) maxHeight;
                imgWidth = (int) maxWidth;
            }
        }
        return Bitmap.createScaledBitmap(realImage, imgWidth,
                imgHeight, true);
    }

    private static void createFileAndShare(Context context, File mPath, Bitmap bitmap, boolean incText, String text, int quality, String header) {
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(mPath);
            bitmap.compress(Bitmap.CompressFormat.PNG, quality, fOut);
            fOut.flush();
            fOut.close();

            final Intent shareIntent = new Intent(Intent.ACTION_SEND);
            Uri imageUri = FileProvider.getUriForFile(
                    context,
                    BuildConfig.APPLICATION_ID + ".provider",
                    mPath);
            shareIntent.setType("image/*");
            if (incText) {
                shareIntent.putExtra(Intent.EXTRA_TEXT, text);
            }
            shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            context.startActivity(Intent.createChooser(shareIntent, header));
        } catch (Throwable tr) {
            AppLogs.d("BitmapUtil", "Couldn't save screenshot");
        }
    }

    // converts the bitmap into base64 format
    public static String convertToBase64(Bitmap bitmap, boolean remove) {
        if (bitmap != null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 75, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            try {
                byteArrayOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return Base64.encodeToString(byteArray, Base64.DEFAULT);
        } else {
            if (remove) return "";
            else return null;
        }
    }

    public static Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    // samplesize that is to be used for bitmap size reduction
    // from the Android documentation - method to calculate a sample size value that is a power of two based on a target width and height
    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap doNecessaryActionsForSelectedImage(Context context, Uri selectedImage) {
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(selectedImage));
            bitmap = performCompression(bitmap);
            // get the pathname of the file to perform the photo rotation (if required)
            String pathname = getRealPathFromURI(context, selectedImage);
            return rotateImageIfRequired(bitmap, pathname);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap doNecessaryActionsForCapturedImage(String mCurrentPhotoPath) {
        Bitmap bitmap = performBitmapOperations(mCurrentPhotoPath);
        return performCompression(bitmap);
    }

    // get the pathname of the image selected from the gallery
    private static String getRealPathFromURI(Context context, Uri contentURI) {
        String filePath = "";
        try {
            if (contentURI != null) {
                if (DocumentsContract.isDocumentUri(context, contentURI)) {
                    String wholeID = DocumentsContract.getDocumentId(contentURI);

                    // Split at colon, use second item in the array
                    if (wholeID.length() > 0) {
                        String[] splitId = wholeID.split(":");
                        if (splitId.length > 1) {
                            String id = splitId[1];
                            filePath = getDataColumn(context, id);
                        }
                    }
                } else {
                    // google photos
                    InputStream inputStream = context.getContentResolver().openInputStream(contentURI);
                    File photoFile = createTemporalFileFrom(context, inputStream);

                    filePath = photoFile.getPath();
                }
            }
            return filePath;
        } catch (
                IllegalArgumentException | IOException e) {
            return filePath;
        }
    }

    private static File createTemporalFileFrom(Context context, InputStream inputStream) throws IOException {
        File targetFile = null;

        if (inputStream != null) {
            int read;
            byte[] buffer = new byte[8 * 1024];

            targetFile = createTemporalFile(context);
            OutputStream outputStream = new FileOutputStream(targetFile);

            while ((read = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
            }
            outputStream.flush();

            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return targetFile;
    }

    private static File createTemporalFile(Context context) {
        return new File(context.getExternalCacheDir(), "tempFile.jpg");
    }

    private static String getDataColumn(Context context, String id) {
        String filePath = "";
        String[] column = {MediaStore.Images.Media.DATA};

        // where id is equal to
        String sel = MediaStore.Images.Media._ID + "=?";

        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                column, sel, new String[]{id}, null);

        if (cursor != null) {
            int columnIndex = cursor.getColumnIndex(column[0]);

            if (cursor.moveToFirst()) {
                filePath = cursor.getString(columnIndex);
            }
            cursor.close();
        }
        return filePath;
    }
}
