package jp.co.conic.conic1;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ClearanceCalculator {

    private String drive_system;
    private String material;
    private float thickness;

    public ClearanceCalculator(String drive_system,
                               String material,
                               float thickness) {
        this.drive_system = drive_system;
        this.material = material;
        this.thickness = thickness;
    }

    public float getClearanceCoefficient(SQLiteDatabase database) {
        String query = "select * from Clearance " +
                "join Material on Clearance.id_material = Material.id " +
                "join DriveSystem on Clearance.id_drive_system = DriveSystem.id " +
                "where Material.name like \"" + this.material + "\" and DriveSystem.name like \"" + this.drive_system + "\"";
        Cursor cursor = database.rawQuery(query, null);
        if (!cursor.moveToFirst()) {
            return -1;
        }
        else {
            float clearance = cursor.getFloat(cursor.getColumnIndex("value"));
            cursor.close();
            return clearance;
        }
    }

    public float getClearance(SQLiteDatabase database) {
        return this.thickness * this.getClearanceCoefficient(database);
    }
}
