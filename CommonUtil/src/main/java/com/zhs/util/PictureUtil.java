package com.zhs.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by qiaosen on 16/6/6.
 */
public class PictureUtil {

    /**
     * 图片服务器地址，相对路径需要拼接
     */
    public final static String BASE_IGM = "http://image.zhihuishu.com/";

    /**
     * 图片尺寸 large 大；middle 中；small 小
     */
    public enum PicSize {
        /** 对应s1,s2,s3。其中s3最小 */
        LARGE, MIDDLE, SMALL
    }

    public static String getPicUrl(String picUrl, PicSize picSize) {
        if (picUrl != null) {
            int x = picUrl.lastIndexOf(".");
            if (x != -1) {
                String prefix = picUrl.substring(0, x);
                String subfix = picUrl.substring(x + 1, picUrl.length());
                if (prefix.endsWith("_s1") || prefix.endsWith("_s2") || prefix.endsWith("_s3")) {
                    prefix = prefix.substring(0, prefix.length() - 3);
                }
                if (picSize == PicSize.LARGE) {
                    picUrl = prefix + "_s1." + subfix;
                } else if (picSize == PicSize.SMALL) {
                    picUrl = prefix + "_s3." + subfix;
                } else {
                    picUrl = prefix + "_s2." + subfix;
                }
                // 如果是相对地址，拼接成绝对地址
                if (!picUrl.startsWith("http://")) {
                    picUrl = BASE_IGM + picUrl;
                }
            }
        }else {
            picUrl="";
        }
        return picUrl;
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
            outputFile = new File(CompressPicUtil.PhotoUtil.createImageFile().getPath());
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
                outputFile = new File(CompressPicUtil.PhotoUtil.createImageFile().getPath());
                CompressPicUtil.PhotoUtil.copyFileUsingFileChannels(tempFile, outputFile);
            }

        }
        return outputFile;

    }
}
