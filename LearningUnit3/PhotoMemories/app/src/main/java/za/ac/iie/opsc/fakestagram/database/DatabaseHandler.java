package za.ac.iie.opsc.fakestagram.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.Nullable;
import java.io.ByteArrayOutputStream;


import za.ac.iie.opsc.fakestagram.model.ImageModel;

public class DatabaseHandler extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "images.db";
    public static final int DATABASE_VERSION = 1;
    private ByteArrayOutputStream convertBitmapToByteArray;
    private byte [] imageInBytes;

    private static String createTableQuery =
            "create table imageStore(imageName TEXT, imageBitmap BLOB)";

    public DatabaseHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(createTableQuery);
            Toast.makeText(context, "Table created", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    /**
     * Add an image to the database.
     * @param imageModel The image to store.
     */
    public void storeImageLocal(ImageModel imageModel) {
        try {
            SQLiteDatabase imageDatabase = this.getWritableDatabase();
            Bitmap imageToStore = imageModel.getImageBitmap();
            convertBitmapToByteArray = new ByteArrayOutputStream();
            imageToStore.compress(Bitmap.CompressFormat.JPEG,
                    100, convertBitmapToByteArray);
            imageInBytes = convertBitmapToByteArray.toByteArray();
            ContentValues contentValues = new ContentValues();
            contentValues.put("imageName", imageModel.getImageName());
            contentValues.put("imageBitmap", imageInBytes);

            long checkIfQueryRuns =  imageDatabase.insert("imageStore",
                    null,contentValues);

            if (checkIfQueryRuns!=-1) {
                Toast.makeText(context, "Image saved", Toast.LENGTH_SHORT).show();
                imageDatabase.close();
            } else {
                Toast.makeText(context, "Unable to save Image", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e) {
            Log.i("SAVE TO DB ", "storeImageLocal: " +e.getMessage());
        }
    }
}
