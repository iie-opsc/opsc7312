package za.ac.iie.opsc.geoweather;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.View;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;

public class ProcessImageUtil {

    private ProcessImageUtil() {}

    /**
     * Convert a view to a Bitmap.
     * @param view The view to convert.
     * @return The converted Bitmap.
     */
    public static Bitmap takeScreenshot(View view) {
        View screenView = view.getRootView();
        screenView.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(screenView.getDrawingCache());
        screenView.setDrawingCacheEnabled(false);
        return bitmap;
    }

    /**
     * Write a screenshot bitmap to a folder.
     * @param context The activity that this is called from.
     * @param bitmap The bitmap to write.
     * @param fileName The name of the file to create.
     */
    public static void storeScreenshot(Context context, Bitmap bitmap, String fileName) {
        // Get the application's folder - no permissions required to write there!
        final File directory = context.getExternalFilesDir(null);
        if (!directory.exists()) {
            boolean isCreated = directory.mkdirs();
            Log.d("MakingDir", "Created: " + isCreated);
        }
        File captureImage = new File(directory, fileName+".PNG");
        try {
            FileOutputStream writeImage = new FileOutputStream(captureImage);
            bitmap.compress(Bitmap.CompressFormat.PNG, 85, writeImage);
            writeImage.flush();
            writeImage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Share the saved image using an intent.
     * @param context The context where the call is made from.
     * @param filename The name of the file to share.
     */
    public static void pushToInstagram(Context context, String filename) {
        final File directory = context.getExternalFilesDir(null);
        String type ="image/*";
        String mediaPath = filename +".PNG";
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType(type);
        File media = new File(directory, mediaPath);
        Uri uri = Uri.fromFile(media);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(context,
                    BuildConfig.APPLICATION_ID + ".provider", media);
        }
        share.putExtra(Intent.EXTRA_STREAM, uri);
        context.startActivity(Intent.createChooser(share,"share to"));
    }
}
