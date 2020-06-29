package com.example.betterweather.Common;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import androidx.core.content.FileProvider;

import com.example.betterweather.BuildConfig;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;

public class ProcessImage
{
    public Bitmap takeScreenshot(View view) {
        View screenView = view.getRootView();
        screenView.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(screenView.getDrawingCache());
        screenView.setDrawingCacheEnabled(false);

        return bitmap;

    }

    public void storeScreenshot(Context context, Bitmap bitmap, String fileName) {

        final File directory = context.getExternalFilesDir(null);

        if (!directory.exists()) {
            boolean isCreated = directory.mkdirs();
            Log.d("MakingDir", "Created: " + isCreated);
        }

        File captureImage = new File(directory, fileName+".PNG");

        try
        {
            FileOutputStream writeImage = new FileOutputStream(captureImage);
            bitmap.compress(Bitmap.CompressFormat.PNG, 85, writeImage);
            writeImage.flush();
            writeImage.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void pushToInstagram(Context context ,  String filename)
    {
        final File directory = context.getExternalFilesDir(null);

        String type ="image/*";
        String mediaPath = filename +".PNG";
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType(type);
        File media = new File(directory, mediaPath);
        Uri uri= Uri.fromFile(media);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        {
            uri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider",media);

        }

        share.putExtra(Intent.EXTRA_STREAM,uri);
        context.startActivity(Intent.createChooser(share,"share to"));





    }


}
