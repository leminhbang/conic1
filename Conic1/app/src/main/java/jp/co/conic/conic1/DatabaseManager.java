package jp.co.conic.conic1;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {

    //https://github.com/vanpho93/ManageEmployee
    public static SQLiteDatabase initDatabase(Activity activity, String databaseName){
        try {
            String outFileName = activity.getApplicationInfo().dataDir + "/databases/" + databaseName;
            File f = new File(outFileName);
            if(!f.exists()) {
                InputStream e = activity.getAssets().open(databaseName);
                File folder = new File(activity.getApplicationInfo().dataDir + "/databases/");
                if (!folder.exists()) {
                    folder.mkdir();
                }
                FileOutputStream myOutput = new FileOutputStream(outFileName);
                byte[] buffer = new byte[1024];

                int length;
                while ((length = e.read(buffer)) > 0) {
                    myOutput.write(buffer, 0, length);
                }

                myOutput.flush();
                myOutput.close();
                e.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return activity.openOrCreateDatabase(databaseName, Context.MODE_PRIVATE, null);
    }

    public static List<String> getMaterialName(SQLiteDatabase db) {
        List<String> names = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM Material", null);
        if (!cursor.moveToFirst()) {
            return null;
        }
        for (int i = 0; i < cursor.getColumnCount(); i++) {
            names.add(cursor.getString(1));
            cursor.moveToNext();
        }
        return  names;
    }

    public static List<Float> getShear(SQLiteDatabase db) {
        List<Float> shears = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM Material", null);
        if (!cursor.moveToFirst()) {
            return null;
        }
        for (int i = 0; i < cursor.getColumnCount(); i++) {
            shears.add(cursor.getFloat(2));
            cursor.moveToNext();
        }
        return shears;
    }

    public static float getClearanceCoefficient(SQLiteDatabase db, String drive_system, String material) {
        String query = "select * from Clearance " +
                "join Material on Clearance.id_material = Material.id " +
                "join DriveSystem on Clearance.id_drive_system = DriveSystem.id " +
                "where Material.name like \"" + material + "\" and DriveSystem.name like \"" + drive_system + "\"";
        Cursor cursor = db.rawQuery(query, null);
        if (!cursor.moveToFirst()) {
            return -1;
        }
        else {
            float result = cursor.getFloat(cursor.getColumnIndex("value"));
            cursor.close();
            return result;
        }
    }
}
