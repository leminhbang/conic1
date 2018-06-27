package jp.co.conic.conic1;

import android.database.sqlite.SQLiteDatabase;

public class ClearanceCalculator {

    private String drive_system;
    private String material;
    private float thickness;

    private String DATABASE_NAME = "MaterialDB.sqlite";
    SQLiteDatabase database;

    public ClearanceCalculator(String drive_system,
                               String material,
                               float thickness) {
        this.drive_system = drive_system;
        this.material = material;
        this.thickness = thickness;
    }

    public float getClearance(String drive_system, String material, float thickness) {
        return -1;
    }
}
