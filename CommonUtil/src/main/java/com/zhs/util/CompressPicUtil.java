package com.zhs.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by dell on 2016/12/16.
 * 加强版
 */
public class CompressPicUtil {
    /**
     *
     * 根据bitmap压缩图片质量
     * @param bitmap 未压缩的bitmap
     * @return 压缩后的bitmap
     */
    public static Bitmap cQuality(Bitmap bitmap){
        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
        int beginRate = 100;
        //第一个参数 ：图片格式 ，第二个参数： 图片质量，100为最高，0为最差  ，第三个参数：保存压缩后的数据的流
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bOut);
        while(bOut.size()/1024/1024>100){  //如果压缩后大于100Kb，则提高压缩率，重新压缩
            beginRate -=10;
            bOut.reset();
            bitmap.compress(Bitmap.CompressFormat.JPEG, beginRate, bOut);
        }
        ByteArrayInputStream bInt = new ByteArrayInputStream(bOut.toByteArray());
        Bitmap newBitmap = BitmapFactory.decodeStream(bInt);
        if(newBitmap!=null){
            return newBitmap;
        }else{
            return bitmap;
        }
    }

    public static boolean getCacheImage(String filePath,String cachePath){
        OutputStream out = null;
        BitmapFactory.Options option = new BitmapFactory.Options();
        option.inJustDecodeBounds = true;  //设置为true，只读尺寸信息，不加载像素信息到内存
        Bitmap bitmap = BitmapFactory.decodeFile(filePath, option);  //此时bitmap为空
        option.inJustDecodeBounds = false;
        int bWidth = option.outWidth;
        int bHeight= option.outHeight;
        int toWidth = 400;
        int toHeight = 800;
        int be = 1;  //be = 1代表不缩放
        if(bWidth/toWidth>bHeight/toHeight&&bWidth>toWidth){
            be = (int)bWidth/toWidth;
        }else if(bWidth/toWidth<bHeight/toHeight&&bHeight>toHeight){
            be = (int)bHeight/toHeight;
        }
        option.inSampleSize = be; //设置缩放比例
        bitmap  = BitmapFactory.decodeFile(filePath, option);
        try {
            out = new FileOutputStream(new File(cachePath));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
    }

    public static File scal(Uri fileUri){
        String path = fileUri.getPath();
        File outputFile = new File(path);
        long fileSize = outputFile.length();
        final long fileMaxSize = 200 * 1024;
        if (fileSize >= fileMaxSize) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, options);
            int height = options.outHeight;
            int width = options.outWidth;

            double scale = Math.sqrt((float) fileSize / fileMaxSize);
            options.outHeight = (int) (height / scale);
            options.outWidth = (int) (width / scale);
            options.inSampleSize = (int) (scale + 0.5);
            options.inJustDecodeBounds = false;

            Bitmap bitmap = BitmapFactory.decodeFile(path, options);
            outputFile = new File(PhotoUtil.createImageFile().getPath());
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(outputFile);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, fos);
                fos.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Log.d("", "sss ok " + outputFile.length());
            if (!bitmap.isRecycled()) {
                bitmap.recycle();
            }else{
                File tempFile = outputFile;
                outputFile = new File(PhotoUtil.createImageFile().getPath());
                PhotoUtil.copyFileUsingFileChannels(tempFile, outputFile);
            }

        }
        return outputFile;

    }



    public static class PhotoUtil {
        public static Uri createImageFile(){
            // Create an image file name
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "JPEG_" + timeStamp + "_";
            File storageDir = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES);
            File image = null;
            try {
                image = File.createTempFile(
                        imageFileName,  /* prefix */
                        ".jpg",         /* suffix */
                        storageDir      /* directory */
                );
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            // Save a file: path for use with ACTION_VIEW intents
            return Uri.fromFile(image);
        }
        public static void copyFileUsingFileChannels(File source, File dest){
            FileChannel inputChannel = null;
            FileChannel outputChannel = null;
            try {
                try {
                    inputChannel = new FileInputStream(source).getChannel();
                    outputChannel = new FileOutputStream(dest).getChannel();
                    outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } finally {
                try {
                    if(inputChannel != null)
                        inputChannel.close();
                    if(outputChannel != null)
                        outputChannel.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * Gets the content:// URI  from the given corresponding path to a file
     * 把file转成content开头的uri,有的手机不识别file开头的uri,所以content开头的兼容较好
     * @param context
     * @param imageFile
     * @return content Uri
     */
    public static Uri getImageContentUri(Context context, File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[] { MediaStore.Images.Media._ID },
                MediaStore.Images.Media.DATA + "=? ",
                new String[] { filePath }, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }

    /*压缩系统返回的图片,转换成content://...返回*/
    public static Uri scalUriPicture(Context context , Uri uri , ArrayList<File>... arr) {
        if (uri == null) {
            return null;
        }
        try {
            File file = scal(uri);
            if (file == null) {
                return null;
            }
            Uri result = getImageContentUri(context, file);
            if (arr.length != 0) {
                arr[0].add(file);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

}
