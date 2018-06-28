package jp.co.conic.conic1;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private final String DBName = "MaterialDB.sqlite";
    private Spinner spinnerToolingType, spinnerDriveSystem,
            spinnerMaterial, spinnerFigure;
    private Button btnNewMaterial;
    SQLiteDatabase database;
    List<String> materialNames;
    List<Material> materials;
    public static float SHEAR;
    private ImageView imgLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = DatabaseManager.initDatabase(this, DBName);
        /*database = DatabaseManager.initDatabase(this, "MaterialDB.sqlite");
        Cursor cursor = database.rawQuery("SELECT * FROM mATERIAL", null);
        cursor.moveToFirst();
        Toast.makeText(this,
                cursor.getString(0) + " " + cursor.getFloat(1),
                Toast.LENGTH_LONG).show();*/

        mapView();
    }

    @Override
    protected void onStart() {
        database = DatabaseManager.initDatabase(this, DBName);
        displayData();
        super.onStart();
    }

    private void mapView() {
        imgLogo = findViewById(R.id.imgLogoConic);
        imgLogo.setOnClickListener(this);
        btnNewMaterial = findViewById(R.id.btnNewMaterial);
        btnNewMaterial.setOnClickListener(this);
        spinnerToolingType = findViewById(R.id.spinner_tooling_type);
        spinnerDriveSystem = findViewById(R.id.spinner_drive_system);
        spinnerMaterial = findViewById(R.id.spinner_material);
        spinnerFigure = findViewById(R.id.spinner_figure);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(
                this, R.array.array_tooling_type,
                android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.
                select_dialog_singlechoice);
        spinnerToolingType.setAdapter(adapter1);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
                this, R.array.array_drive_system,
                android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.
                select_dialog_singlechoice);
        spinnerDriveSystem.setAdapter(adapter2);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(
                this, R.array.array_material,
                android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.
                select_dialog_singlechoice);
        spinnerMaterial.setAdapter(adapter3);
        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(
                this, R.array.array_figure,
                android.R.layout.simple_spinner_item);
        adapter4.setDropDownViewResource(android.R.layout.
                select_dialog_singlechoice);
        materialNames = new ArrayList<>();
        materials = new ArrayList<>();
        spinnerFigure.setAdapter(adapter4);
        spinnerToolingType.setOnItemSelectedListener(this);
        spinnerToolingType.setSelection(0);
        spinnerDriveSystem.setOnItemSelectedListener(this);
        spinnerDriveSystem.setSelection(0);
        spinnerMaterial.setOnItemSelectedListener(this);
        spinnerMaterial.setSelection(0);
        spinnerFigure.setOnItemSelectedListener(this);
        spinnerFigure.setSelection(0);
    }
    private void displayData() {
        materialNames.clear();
        materials.clear();
        String system = (String) spinnerDriveSystem.
                getSelectedItem();
        database = DatabaseManager.initDatabase(this, DBName);
        materials = DatabaseManager.
                getMaterialData(database, system);
        if (materials == null) {
            materialNames = Arrays.asList(getResources().getStringArray(
                    R.array.array_material));
        }else {
            for (int i = 0; i < materials.size(); i++) {
                materialNames.add(materials.get(i).getName());
            }
        }
        ArrayAdapter adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                materialNames.toArray());
        adapter.setDropDownViewResource(android.R.layout.
                select_dialog_singlechoice);
        spinnerMaterial.setAdapter(adapter);
    }
    public void selectShape(int position) {
        SHEAR = materials.get(spinnerMaterial.getSelectedItemPosition()).getShear();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        switch (position) {
            case 0:
                ft.replace(R.id.fragmentBody,  new RoundFragment());
                ft.commit();
                break;
            case 1:
                ft.replace(R.id.fragmentBody, new RectangleFragment());
                ft.commit();
                break;
            case 2:
                ft.replace(R.id.fragmentBody, new ObroundFragment());
                ft.commit();
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Spinner spinner = (Spinner) adapterView;
        int id = spinner.getId();

        switch (id) {
            case R.id.spinner_tooling_type:
                break;
            case R.id.spinner_drive_system:
                database = DatabaseManager.initDatabase(this, DBName);
                String system = (String) spinnerDriveSystem.
                        getSelectedItem();
                materials.clear();
                materials = DatabaseManager.
                        getMaterialData(database, system);
                displayData();
                break;
            case R.id.spinner_material:
                break;
            case R.id.spinner_figure:
                selectShape(i);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btnNewMaterial:
                Intent intent = new Intent(this,
                        MaterialActivity.class);
                String system = (String) spinnerDriveSystem.getSelectedItem();
                intent.putExtra("DRIVESYSTEM", system);
                startActivity(intent);
                break;
            case R.id.imgLogoConic:
                Intent homepageIntent = new Intent(this,
                        HomePageActivity.class);
                startActivity(homepageIntent);
                break;
        }
    }

}
