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
    public static List<Material> getMaterialData(SQLiteDatabase db, String driveSystem) {
        List<Material> data = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM Material", null);

        if (!cursor.moveToFirst()) {
            return null;
        }
        int count = cursor.getCount();
        int idSystem = getDriveSystemID(db, driveSystem);
        for (int i = 0; i < count; i++) {
            int id = cursor.getInt(0);
            Cursor c = db.rawQuery("SELECT * FROM Clearance WHERE id_material = " +
                    id + " and id_drive_system = " + idSystem, null);
            c.moveToFirst();
            data.add(new Material(cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getFloat(2),
                    c.getFloat(2)));
            cursor.moveToNext();
        }
        return data;
    }
    public static int getDriveSystemID(SQLiteDatabase db, String driveSystem) {
        int id = -1;
        String s = "SELECT * FROM DriveSystem where name like '" +
                driveSystem + "'";
        Cursor cursor = db.rawQuery("SELECT * FROM DriveSystem WHERE TRIM(name) = '" +
                driveSystem.trim() + "'", null);
        if (!cursor.moveToFirst()) {
            return id;
        }
        id = cursor.getInt(0);
        return  id;
    }
    public static List<String> getMaterialName(SQLiteDatabase db) {
        List<String> names = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM Material", null);
        if (!cursor.moveToFirst()) {
            return null;
        }
        for (int i = 0; i < cursor.getCount(); i++) {
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
        for (int i = 0; i < cursor.getCount(); i++) {
            shears.add(cursor.getFloat(2));
            cursor.moveToNext();
        }
        return shears;
    }
    public static List<Float> getClearance(SQLiteDatabase db, int materialID,
                                           String driveSystem) {
        int driveSystemID = 0;
        List<Float> clearnces = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM Clearance where id_material = " +
                materialID + " and id_drive_system = " + driveSystemID,
                null);
        if (!cursor.moveToFirst()) {
            return null;
        }
        for (int i = 0; i < cursor.getCount(); i++) {
            clearnces.add(cursor.getFloat(2));
            cursor.moveToNext();
        }
        return clearnces;
    }

}
