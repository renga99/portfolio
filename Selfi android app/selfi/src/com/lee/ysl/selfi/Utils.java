package com.lee.ysl.selfi;



import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.View;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Utils {
    public static Bitmap loadBitmapFromFile(String mBackgroundFilename) {
        BitmapFactory.Options localOptions = new BitmapFactory.Options();
        localOptions.inPurgeable = true;
        localOptions.inInputShareable = true;
        return BitmapFactory.decodeFile(mBackgroundFilename, localOptions);
    }

    public static String saveBitmapToFile(Activity context, Bitmap bitmap) {
        String filePath = "";
        try {
            File file = generateFile(context);
            file.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            filePath = file.getAbsolutePath();
            return filePath;
        } catch (IOException exception) {}
        return filePath;
    }

    private static File generateFile(Activity context) {
        String timestamp = String.valueOf(System.currentTimeMillis());
        String md5 = String.valueOf(DigestUtils.md5(timestamp));
        File localFile = getCacheDir(context, "img_cache");
        if (!localFile.exists())
            localFile.mkdirs();
        return new File(localFile, md5);
    }

    private static File getCacheDir(Context context, String dirName) {
        return new File(context.getCacheDir(), dirName);
    }

    //http://stackoverflow.com/a/9596132/1121509
    public static Bitmap drawViewToBitmap(View view, int color) {
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable =view.getBackground();
        if (bgDrawable!=null)
            bgDrawable.draw(canvas);
        else
            canvas.drawColor(color);
        view.draw(canvas);
        return returnedBitmap;
    }

    public static boolean deleteFile(String filename) {
        return new File(filename).delete();
    }
}

