package com.ebediening.Utilites;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.ebediening.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

//import android.icu.util.GregorianCalendar;


public class CommonUtilFunctions {
    public static String count_item = "";
    public static String count_cart = "";
    public static String eng_pun_hin = "";
    public static ArrayList<String> arary_id = new ArrayList<>();
    public static Bitmap bm;
    public static Bitmap bitmap_util;
    public static int rotationAngle = 0;
    public static Matrix matrix;
    public static boolean vaccinate_schedule = false;


    public static void previewCapturedImage(File media_file, Matrix matrix,
                                            int rotationAngle, ImageView img) throws IOException {
        try {

            // bimatp factory
            BitmapFactory.Options options = new BitmapFactory.Options();

            // downsizing image as it throws OutOfMemory Exception for larger
            // images
            options.inSampleSize = 3;

            final Bitmap bitmap = BitmapFactory.decodeFile(
                    media_file.getAbsolutePath(), options);

            ExifInterface ei = new ExifInterface(media_file.getAbsolutePath());

            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotationAngle = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotationAngle = 180;
                    break;

                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotationAngle = 270;
                    break;
            }
            matrix.setRotate(rotationAngle);
            // to make image in portrait mode.
            Bitmap cambitmap = Bitmap.createBitmap(bitmap, 0, 0,
                    options.outWidth, options.outHeight, matrix, true);

            img.setImageBitmap(cambitmap);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * get current time stamp
     **/
//    public static String get_CurrentTimeStamp(boolean space) {
//        SimpleDateFormat dateFormat = new SimpleDateFormat(
//                "dd MM yyyy HH mm ss", Locale.getDefault());
//        Date date = new Date();
//        String dt = dateFormat.format(date);
//        String day = dt.split(" ")[0];
//        String month = dt.split(" ")[1];
//        String year = dt.split(" ")[2];
//        int hrs = Integer.parseInt(dt.split(" ")[3]);
//        int mins = Integer.parseInt(dt.split(" ")[4]);
//        String my_time = get_AmPm_withTime(hrs, mins);
//        String hours = my_time.split(" ")[0];
//        String minutes = my_time.split(" ")[1];
//        String meridian = my_time.split(" ")[2];
//        if (space) {
//            return (day + "/" + month + "/" + year + " " + hours + ":"
//                    + minutes + " " + meridian);
//        } else {
//            return (day + "/" + month + "/" + year + "_" + hours + ":"
//                    + minutes + " " + meridian);
//        }
//
//    }
    public static void DownloadImageFromPath(String path) {
        InputStream in = null;
        Bitmap bmp = null;
        File file_path_img_name = null;
        int responseCode = -1;
        try {
            File direct = new File(Environment.getExternalStorageDirectory() + "/ChimePath");
            if (!direct.exists()) {
                File wallpaperDirectory = new File(Environment.getExternalStorageDirectory() + "/ChimePath/");
                wallpaperDirectory.mkdirs();
            }

            file_path_img_name = new File(new File(Environment.getExternalStorageDirectory() + "/ChimePath/"), System.currentTimeMillis() + ".jpg");
            if (file_path_img_name.exists()) {
                file_path_img_name.delete();
            }
            URL url = new URL(path);//"http://192.xx.xx.xx/mypath/img1.jpg
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoInput(true);
            con.connect();
            responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                //download
                in = con.getInputStream();
                bmp = BitmapFactory.decodeStream(in);

                in.close();
            }

        } catch (Exception ex) {
            Log.e("Exception", ex.toString());
        }
    }


    public static String onCaptureImageResult(Context con, Intent data, boolean bool, Uri imageUri, boolean b, String fileName, CircleImageView img_edit_profile) {
        String bitmap_name = "";
        matrix = new Matrix();
        try {
            bm = MediaStore.Images.Media.getBitmap(
                    con.getContentResolver(), imageUri);

//            ExifInterface ei = new ExifInterface(imageUri.getPath());
//
//            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
//                    ExifInterface.ORIENTATION_NORMAL);
//
//            switch (orientation) {
//                case ExifInterface.ORIENTATION_ROTATE_90:
//                    rotationAngle = 90;
//                    break;
//                case ExifInterface.ORIENTATION_ROTATE_180:
//                    rotationAngle = 180;
//                    break;
//
//                case ExifInterface.ORIENTATION_ROTATE_270:
//                    rotationAngle = 270;
//                    break;
//            }
//            if (getDeviceName().contains("Samsung")) {
//                rotationAngle = 90;
//                matrix.setRotate(rotationAngle);
//            } else {
//                matrix.setRotate(rotationAngle);
//            }

//            if (bool == false) {
//                imageView.setImageBitmap(Bitmap.createScaledBitmap(bm,
//                        120, 160, true));
//            } else {

            bm = Bitmap.createScaledBitmap(bm, 200, 200, true);
//            bm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);

//            imageView.setImageBitmap(bm);
//            }
            img_edit_profile.setImageBitmap(bm);

        } catch (IOException e) {
            e.printStackTrace();
        }
        createDirectoryAndSaveFile(bm, fileName);
        bitmap_name = Environment.getExternalStorageDirectory() + "/ebediening/" + fileName.toString();
        return bitmap_name;
    }

    public static String onCaptureImageResult_imageview(Context con, Intent data, boolean bool, Uri imageUri,
                                                        boolean b, String fileName, ImageView img_edit_profile) {
        String bitmap_name = "";
        matrix = new Matrix();
        try {
            bm = MediaStore.Images.Media.getBitmap(
                    con.getContentResolver(), imageUri);

//            ExifInterface ei = new ExifInterface(imageUri.getPath());
//
//            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
//                    ExifInterface.ORIENTATION_NORMAL);
//
//            switch (orientation) {
//                case ExifInterface.ORIENTATION_ROTATE_90:
//                    rotationAngle = 90;
//                    break;
//                case ExifInterface.ORIENTATION_ROTATE_180:
//                    rotationAngle = 180;
//                    break;
//
//                case ExifInterface.ORIENTATION_ROTATE_270:
//                    rotationAngle = 270;
//                    break;
//            }
//            if (getDeviceName().contains("Samsung")) {
//                rotationAngle = 90;
//                matrix.setRotate(rotationAngle);
//            } else {
//                matrix.setRotate(rotationAngle);
//            }

//            if (bool == false) {
//                imageView.setImageBitmap(Bitmap.createScaledBitmap(bm,
//                        120, 160, true));
//            } else {

            bm = Bitmap.createScaledBitmap(bm, 600, 450, true);
//            bm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);

//            imageView.setImageBitmap(bm);
//            }
            img_edit_profile.setImageBitmap(bm);
//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
//                Bitmap blurredBitmap = RSBlurProcessor.blur(con, bm);
//                image_bg.setImageBitmap(blurredBitmap);
//            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        createDirectoryAndSaveFile(bm, fileName);
        bitmap_name = Environment.getExternalStorageDirectory() + "/ebediening/" + fileName.toString();
        return bitmap_name;
    }


    public static String getRealPathFromURI(Context con, Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = con.getContentResolver().query(contentUri, proj, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public static String onSelectFromGalleryResult(Context con, Intent data, boolean bool, CircleImageView img_edit_profile) {
        String bitmap_name = "";
        Bitmap selectedImage = null;
//
        if (data != null && !data.equals("")) {
            Bundle bundle = data.getExtras();
            Uri selectImage = data.getData();
            InputStream imageStream = null;
            try {
                imageStream = con.getContentResolver().openInputStream(selectImage);
                selectedImage = BitmapFactory.decodeStream(imageStream);
                selectedImage = getResizedBitmap(selectedImage, 300);
                img_edit_profile.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            String fileName = System.currentTimeMillis() + ".png";
            createDirectoryAndSaveFile(selectedImage, fileName);
            bitmap_name = Environment.getExternalStorageDirectory() + "/ebediening/" + fileName.toString();

//            Bitmap selectedBitmap = bundle.getParcelable("data");
//            Uri selectedImage = getImageUri(con, selectedBitmap);

//            String[] filePathColumn = {MediaStore.Images.Media.DATA};
//            Cursor cursor = con.getContentResolver().query(selectedImage,
//                    filePathColumn, null, null, null);
//            cursor.moveToFirst();
//            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//            String picturePath = cursor.getString(columnIndex);
//            File f = new File(picturePath);
//            cursor.close();
//            bm = BitmapFactory.decodeFile(picturePath);

//            bitmap_name = picturePath;

//        if (bool == false) {
//            imageView.setImageBitmap(Bitmap.createScaledBitmap(bm,
//                    bm.getWidth(),bm.getHeight(), true));
//        } else {

//        }
        }
        return bitmap_name;
    }

    public static String onSelectFromGalleryResult_imageview(Context con, Intent data, boolean bool,
                                                             ImageView img_edit_profile) {
        String bitmap_name = "";
        Bitmap selectedImage = null;

//
        if (data != null && !data.equals("")) {
            Bundle bundle = data.getExtras();
            Uri selectImage = data.getData();
            InputStream imageStream = null;
            try {
                imageStream = con.getContentResolver().openInputStream(selectImage);
                selectedImage = BitmapFactory.decodeStream(imageStream);
                selectedImage = getResizedBitmap(selectedImage, 450);

//                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
//                    Bitmap blurredBitmap = RSBlurProcessor.blur(con, selectedImage);
//                    user_image_bg.setImageBitmap(blurredBitmap);
//                }


                img_edit_profile.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            String fileName = System.currentTimeMillis() + ".png";
            createDirectoryAndSaveFile(selectedImage, fileName);
            bitmap_name = Environment.getExternalStorageDirectory() + "/ebediening/" + fileName.toString();

//            Bitmap selectedBitmap = bundle.getParcelable("data");
//            Uri selectedImage = getImageUri(con, selectedBitmap);

//            String[] filePathColumn = {MediaStore.Images.Media.DATA};
//            Cursor cursor = con.getContentResolver().query(selectedImage,
//                    filePathColumn, null, null, null);
//            cursor.moveToFirst();
//            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//            String picturePath = cursor.getString(columnIndex);
//            File f = new File(picturePath);
//            cursor.close();
//            bm = BitmapFactory.decodeFile(picturePath);

//            bitmap_name = picturePath;

//        if (bool == false) {
//            imageView.setImageBitmap(Bitmap.createScaledBitmap(bm,
//                    bm.getWidth(),bm.getHeight(), true));
//        } else {

//        }
        }
        return bitmap_name;
    }

    public static String onSelectFromGalleryResult_imageview_bitmap(Context con, Bitmap data, boolean bool,
                                                                    ImageView img_edit_profile) {
        String bitmap_name = "";
        Bitmap selectedImage = null;

//
        if (data != null && !data.equals("")) {
//            Bundle bundle = data.getExtras();
//            Uri selectImage = data.getData();
//            InputStream imageStream = null;
            //                imageStream = con.getContentResolver().openInputStream(selectImage);
//                selectedImage = BitmapFactory.decodeStream(imageStream);
//                selectedImage = getResizedBitmap(selectedImage, 450);
//
////                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
////                    Bitmap blurredBitmap = RSBlurProcessor.blur(con, selectedImage);
////                    user_image_bg.setImageBitmap(blurredBitmap);
////                }


            img_edit_profile.setImageBitmap(data);
            String fileName = System.currentTimeMillis() + ".png";
            createDirectoryAndSaveFile(selectedImage, fileName);
            bitmap_name = Environment.getExternalStorageDirectory() + "/ebediening/" + fileName.toString();

//            Bitmap selectedBitmap = bundle.getParcelable("data");
//            Uri selectedImage = getImageUri(con, selectedBitmap);

//            String[] filePathColumn = {MediaStore.Images.Media.DATA};
//            Cursor cursor = con.getContentResolver().query(selectedImage,
//                    filePathColumn, null, null, null);
//            cursor.moveToFirst();
//            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//            String picturePath = cursor.getString(columnIndex);
//            File f = new File(picturePath);
//            cursor.close();
//            bm = BitmapFactory.decodeFile(picturePath);

//            bitmap_name = picturePath;

//        if (bool == false) {
//            imageView.setImageBitmap(Bitmap.createScaledBitmap(bm,
//                    bm.getWidth(),bm.getHeight(), true));
//        } else {

//        }
        }
        return bitmap_name;
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


    public static Bitmap StringToBitMap(String image) {
        try {
            byte[] encodeByte = Base64.decode(image, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    public static String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }


    public static Bitmap ShrinkBitmap(String file, int width, int height) {

        BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
        bmpFactoryOptions.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(file, bmpFactoryOptions);

        int heightRatio = (int) Math.ceil(bmpFactoryOptions.outHeight / (float) height);
        int widthRatio = (int) Math.ceil(bmpFactoryOptions.outWidth / (float) width);

        if (heightRatio > 1 || widthRatio > 1) {
            if (heightRatio > widthRatio) {
                bmpFactoryOptions.inSampleSize = heightRatio;
            } else {
                bmpFactoryOptions.inSampleSize = widthRatio;
            }
        }

        bmpFactoryOptions.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(file, bmpFactoryOptions);
        return bitmap;
    }


    public static Bitmap decodeSampledBitmapFromFile(String path, int reqWidth, int reqHeight) { // BEST QUALITY MATCH
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize, Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        int inSampleSize = 1;

        if (height > reqHeight) {
            inSampleSize = Math.round((float) height / (float) reqHeight);
        }
        int expectedWidth = width / inSampleSize;

        if (expectedWidth > reqWidth) {
            //if(Math.round((float)width / (float)reqWidth) > inSampleSize) // If bigger SampSize..
            inSampleSize = Math.round((float) width / (float) reqWidth);
        }

        options.inSampleSize = inSampleSize;

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(path, options);
    }

    public static void createDirectoryAndSaveFile(Bitmap imageToSave, String fileName) {

        File direct = new File(Environment.getExternalStorageDirectory() + "/ebediening/");

        if (!direct.exists()) {
            File wallpaperDirectory = new File(Environment.getExternalStorageDirectory() + "/ebediening/");
            wallpaperDirectory.mkdirs();
        }

        File file = new File(new File(Environment.getExternalStorageDirectory() + "/ebediening/"), fileName);
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            imageToSave.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public static void Enable_GPS_Setting(final Context con) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(con);
        alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Goto Settings Page To Enable GPS",
                        new OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                con.startActivity(callGPSSettingIntent);
                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancel",
                new OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }


    public static String capture_image_rotated(Context con, Intent data, ImageView imageView, boolean bool, Uri imageUri) {
        String image_path = "";

        File direct = new File(Environment.getExternalStorageDirectory() + "/ebediening");
        if (!direct.exists()) {
            File wallpaperDirectory = new File(Environment.getExternalStorageDirectory() + "/ebediening/");
            wallpaperDirectory.mkdirs();
        }
        String output_file_name = Environment.getExternalStorageDirectory() + "/ebediening/" + System.currentTimeMillis() + ".jpeg";
        File pictureFile = new File(output_file_name);
        if (pictureFile.exists()) {
            pictureFile.delete();
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            Bitmap realImage = MediaStore.Images.Media.getBitmap(
                    con.getContentResolver(), imageUri);
//            Bitmap realImage = BitmapFactory.decodeByteArray(data, 0, data.length);

            ExifInterface exif = new ExifInterface(pictureFile.toString());

            Log.d("EXIF value", exif.getAttribute(ExifInterface.TAG_ORIENTATION));
            if (exif.getAttribute(ExifInterface.TAG_ORIENTATION).equalsIgnoreCase("6")) {
                realImage = rotate(realImage, 90);
            } else if (exif.getAttribute(ExifInterface.TAG_ORIENTATION).equalsIgnoreCase("8")) {
                realImage = rotate(realImage, 270);
            } else if (exif.getAttribute(ExifInterface.TAG_ORIENTATION).equalsIgnoreCase("3")) {
                realImage = rotate(realImage, 180);
            } else if (exif.getAttribute(ExifInterface.TAG_ORIENTATION).equalsIgnoreCase("0")) {
                realImage = rotate(realImage, 90);
            }

            boolean bo = realImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);

            fos.close();
            imageView.setImageBitmap(realImage);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image_path;
    }

    public static Bitmap rotate(Bitmap bitmap, int degree) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        Matrix mtx = new Matrix();
        //       mtx.postRotate(degree);
        mtx.setRotate(degree);

        return Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, true);
    }


//                boolean isGranted = isPermissionsGranted(getActivity(), new String[]{PermissionUtils.Manifest_WRITE_EXTERNAL_STORAGE, PermissionUtils.Manifest_CAMERA});
//                if(isGranted){
//                        show_dialog();
//                        }else{
//                        askCompactPermissions(new String[]{PermissionUtils.Manifest_CAMERA,PermissionUtils.Manifest_WRITE_EXTERNAL_STORAGE,PermissionUtils.Manifest_READ_EXTERNAL_STORAGE},new PermissionResult(){
//@Override
//public void permissionGranted(){
//        //permission granted
//        //replace with your action
//        show_dialog();
//        }
//
//@Override
//public void permissionDenied(){
//        //permission denied
//        //replace with your action
//        }
//
//@Override
//public void permissionForeverDenied(){
//        // user has check 'never ask again'
//        // you need to open setting manually
//        //  Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//        //  Uri uri = Uri.fromParts("package", getPackageName(), null);
//        //   intent.setData(uri);
//        //  startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
//        }
//        });
//        }


//    public void show_dialog(Context context) {
//        final Dialog dialog_img = new Dialog(context);
//        dialog_img.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog_img.setContentView(R.layout.dialog_for_menuimage);
//        dialog_img.findViewById(R.id.btnChoosePath)
//                .setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog_img.dismiss();
//                        // activeGallery();
//
//                    }
//                });
//        dialog_img.findViewById(R.id.btnTakePhoto)
//                .setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog_img.dismiss();
//                        //   activeTakePhoto();
//                    }
//                });
//
//        // show dialog on screen
//        dialog_img.show();
//    }


    public static void call(Context context, String no) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + no));
        context.startActivity(intent);
    }


    /**
     * Date Picker
     **/
    public static void DatePickerDialog_DOB(final Context context, final EditText edittext) {
        // TODO Auto-generated method stub

        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        // Launch Date Picker Dialog
        DatePickerDialog dpd = new DatePickerDialog(context, android.R.style.Theme_DeviceDefault_Dialog,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // TODO Auto-generated method stub
                        c.set(year, monthOfYear, dayOfMonth);
                        if (c.after(Calendar.getInstance())) {
                            DatePickerDialog_DOB(context, edittext);
//                            showShortToast(context, "Please Select Valid Date");
                        } else {
                            String date_of_birth = (monthOfYear + 1) + "-" + dayOfMonth + "-"
                                    + year;
                            edittext.setText(date_of_birth);
                        }
                    }
                }, mYear, mMonth, mDay);
        dpd.getDatePicker().setMaxDate(System.currentTimeMillis());
        dpd.getDatePicker().setCalendarViewShown(false);
        dpd.show();

    }

    public static void DatePickerDialog(Context context, final TextView edittext) {
        // TODO Auto-generated method stub

        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        // Launch Date Picker Dialog
        DatePickerDialog dpd = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // TODO Auto-generated method stub
                        String date_of_birth = year + "-" + (monthOfYear + 1) + "-" +
                                +dayOfMonth;
                        edittext.setText(date_of_birth);
                    }
                }, mYear, mMonth, mDay);
        dpd.getDatePicker().setMaxDate(System.currentTimeMillis());
        dpd.getDatePicker().setCalendarViewShown(false);
        dpd.show();

    }

    public static String DatePickerDialog_yy_mm_dd(Context context, final EditText edittext) {
        // TODO Auto-generated method stub
        final String[] date = {""};
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        // Launch Date Picker Dialog
        DatePickerDialog dpd = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // TODO Auto-generated method stub
                        String date_of_birth = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                        date[0] = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        Calendar temp = Calendar.getInstance();
                        edittext.setText(date_of_birth + " " + padding_str(temp.get(Calendar.HOUR))+":"+padding_str(temp.get(Calendar.MINUTE))+":"+padding_str(temp.get(Calendar.SECOND)));
                    }
                }, mYear, mMonth, mDay);
        dpd.getDatePicker().setMinDate(System.currentTimeMillis());
        dpd.getDatePicker().setCalendarViewShown(false);
        dpd.show();
        return date[0];
    }

    /**
     * Time Picker
     **/
    public static String timepickerdialog(final Context context, final EditText textView) {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;

        mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
//
                Calendar temp = Calendar.getInstance();
                temp.set(Calendar.HOUR_OF_DAY, selectedHour);
                temp.set(Calendar.MINUTE, selectedMinute);
                Calendar cal = Calendar.getInstance();

                if (temp.getTime().before(cal.getTime())) {
                    Toast.makeText(context, "Please Select Valid Time", Toast.LENGTH_SHORT).show();
                    textView.setText("");
                } else {
                    boolean isPM = (selectedHour >= 12);
                    textView.setText(String.format("%02d:%02d %s", (selectedHour == 12 || selectedHour == 0) ? 12 : selectedHour % 12, selectedMinute, isPM ? "PM" : "AM"));
//                    if (b == true) {
////                    linear_mode.setVisibility(View.VISIBLE);
//                        Frag_Select_FourMode.update_data(context);
//                    } else {
////                    linear_mode.setVisibility(View.GONE);
//                    }
                }

            }
        }, hour, minute, false);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");

        mTimePicker.show();
        return "selected";
    }

    public static String timepickerdialog_to(final Context context, final TextView textView, final LinearLayout linear_mode, final boolean b, final String s) {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        final SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm");
        final SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm a");
        final SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd hh:mm:ss 'GMT'Z yyyy");
        TimePickerDialog mTimePicker;

        mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                Calendar temp = Calendar.getInstance();
                temp.set(Calendar.HOUR_OF_DAY, selectedHour);
                temp.set(Calendar.MINUTE, selectedMinute);
                Log.e("time", temp.getTime().toString());
                Calendar cal = Calendar.getInstance();
                Calendar cal1 = Calendar.getInstance();
                //     long current=cal1.getTimeInMillis();
                //     long selected = 0;
                try {

                    String time = displayFormat.format(parseFormat.parse(s));
                    String[] time_array = time.split(":");
                    cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time_array[0]));
                    cal.set(Calendar.MINUTE, Integer.parseInt(time_array[1]) + 1);
//                     selected=cal1.getTimeInMillis();
//                    Log.e("date_current", String.valueOf(cal1.getTimeInMillis()));
//                    Log.e("date_dateFormat", String.valueOf(cal.getTimeInMillis()));

                } catch (ParseException e) {
                    e.printStackTrace();
                }

//                if(current==selected) {
                if (temp.getTime().before(cal.getTime())) {
                    Toast.makeText(context, "Please Select Valid Time", Toast.LENGTH_SHORT).show();
                    textView.setText("");
                } else {
                    boolean isPM = (selectedHour >= 12);
                    textView.setText(String.format("%02d:%02d %s", (selectedHour == 12 || selectedHour == 0) ? 12 : selectedHour % 12, selectedMinute, isPM ? "PM" : "AM"));
//                    if (b == true) {
//                        Frag_Select_FourMode.update_data(context);
//                    } else {
////                    linear_mode.setVisibility(View.GONE);
//                    }
                }
//                }else{
//                    Toast.makeText(context, "Please Select Valid date", Toast.LENGTH_SHORT).show();
//                }

            }
        }, hour, minute, false);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");

        mTimePicker.show();
        return "selected";
    }

    public static String padding_str(int c) {
        if (c >= 10) {
            return String.valueOf(c);
        } else {
            return ("0" + String.valueOf(c));
        }
    }
}


//    private void activeTakePhoto() {
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
//            fileName = System.currentTimeMillis() + ".png";
//            File dir = new File(Environment.getExternalStorageDirectory() + "/");
//            File output = new File(dir, fileName);
//            if (Build.VERSION.SDK_INT >= 24) {
//                mCapturedImageURI = FileProvider.getUriForFile(getActivity(), getActivity().getApplicationContext().getPackageName() + ".provider", output);
//            } else {
//                mCapturedImageURI = Uri.fromFile(output);
//            }
//            takePictureIntent
//                    .putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
//            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//        }
//    }
//
//    private void activeGallery() {
//        Intent intent = new Intent(Intent.ACTION_PICK,
//                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        startActivityForResult(intent, RESULT_LOAD_IMAGE);
//    }


//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        switch (requestCode) {
//            //camera
//            case REQUEST_IMAGE_CAPTURE:
//                if (resultCode != 0) {
//                    image_camera = CommonUtilFunctions.onCaptureImageResult(getActivity(), data, true, mCapturedImageURI, true, fileName);
//                    upload_menu(image_camera);
////                    add_image(image_camera);
//                }
//
//                break;
//            //gallery
//            case RESULT_LOAD_IMAGE:
//                if (data != null && !data.equals("")) {
//                    image_gallery = CommonUtilFunctions.onSelectFromGalleryResult(getActivity(), data, false);
//                    upload_menu(image_gallery);
////                    add_image(image_gallery);
//                }
//
//
//                break;
//
//        }
//    }
