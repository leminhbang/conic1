package jp.co.conic.conic1;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MaterialActivity extends AppCompatActivity implements View.OnClickListener,  AdapterView.OnItemClickListener {
    private final String DBName = "MaterialDB.sqlite";
    SQLiteDatabase database;
    private EditText edtName,  edtShear, edtClearance;
    private Button btnAdd, btnEdit, btnDelete;
    private ListView lvMaterial;

    private List<String> listMaterialName;
    private List<Float> listShear;
    private List<Float> listClearance;
    private ArrayAdapter<String> adapter;

    private String driveSystemName;
    private List<Material> materials;
    private MaterialAdapter adapter1;

    private int POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material);

        database = DatabaseManager.initDatabase(this, DBName);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            driveSystemName = bundle.getString("DRIVESYSTEM");
        }

        mapView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayData();
    }

    private void mapView() {
        edtName = findViewById(R.id.edtMaterialName);
        edtShear = findViewById(R.id.edtShear);
        edtClearance = findViewById(R.id.edtClearance);
        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);
        btnEdit = findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(this);
        btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(this);
        lvMaterial = findViewById(R.id.lvMaterial);
        materials = new ArrayList<>();

        lvMaterial.setAdapter(adapter1);
        lvMaterial.setOnItemClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btnAdd:
                //insert();
                showAddDialog();
                break;
            case R.id.btnEdit:
                update(POSITION);
                break;
            case R.id.btnDelete:
                delete(POSITION);
                break;
        }
    }

    private void showAddDialog() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_add_new_material);
        dialog.setCanceledOnTouchOutside(false);

        dialog.show();
    }

    private void insert() {
        if (!checkValueValidate()) {
            return;
        }
        String name = edtName.getText().toString().trim();
        String shear = edtShear.getText().toString().trim();
        String clearance = edtClearance.getText().toString().trim();

        boolean isExist = DatabaseManager.checkIsMaterialExist(
                database, name);
        if (isExist) {
            Toast.makeText(this,
                    "Material is exist",
                    Toast.LENGTH_LONG).show();
            return;
        }

        //insert value to material table
        ContentValues values = new ContentValues();
        float shearVal = Float.parseFloat(shear);
        values.put("name", name);
        values.put("shear", shearVal);

        //insert value to clearance table
        //get last material id, the new material id is
        //increase by 1
        ContentValues values_2 = new ContentValues();
        int id_material = materials.get(materials.size() - 1).getId() + 1;
        int id_drive_system = DatabaseManager.getDriveSystemID(
                database, driveSystemName);
        float clearanceVal = Float.parseFloat(clearance);
        values_2.put("id_material", id_material);
        values_2.put("id_drive_system", id_drive_system);
        values_2.put("value", clearanceVal);;

        if (id_material == -1 || id_drive_system == -1) {
            Toast.makeText(this,
                    "Add new Material failed",
                    Toast.LENGTH_LONG).show();
            return;
        }
        database.insert("Material", null, values);
        database.insert("Clearance", null, values_2);
        Toast.makeText(this,
                "Add new material finished",
                Toast.LENGTH_LONG).show();
        //update UI
        displayData();
    }

    private void update(int position) {
        if (position == -1) {
            Toast.makeText(this,
                    "Can not update to empty material",
                    Toast.LENGTH_LONG).show();
            return;
        }
        if (!checkValueValidate()) {
            return;
        }
        String name = edtName.getText().toString().trim();
        String shear = edtShear.getText().toString().trim();
        String clearance = edtClearance.getText().toString().trim();

        boolean isExist = DatabaseManager.checkIsMaterialExist(
                database, name);
        if (!isExist) {
            Toast.makeText(this,
                    "Material is not exist to update",
                    Toast.LENGTH_LONG).show();
            return;
        }

        //material exist, update value
        //update value to Material table
        ContentValues values = new ContentValues();
        int id_material = materials.get(position).getId();
        float shearVal = Float.parseFloat(shear.trim());
        values.put("name", name);
        values.put("shear", shearVal);

        //update value to Clearance table
        ContentValues values_2 = new ContentValues();
        int id_clearance = materials.get(position).getId_clearance();
        float clearaneVal = Float.parseFloat(clearance.trim());
        values_2.put("value", clearaneVal);
        //update value
        database.update("Material", values,
                "id = ?", new String[] {id_material + ""});
        database.update("Clearance", values_2,
                "id = ?", new String[] {id_clearance + ""});
        Toast.makeText(this,
                "Update finished", Toast.LENGTH_LONG).show();

        //update UI
        displayData();
    }

    public void delete(int position) {
        //confirm to delete
        final int id_material = materials.get(position).getId();
        if (id_material == 1 || id_material == 2 ||
                id_material == 3) {
            Toast.makeText(this,
                    "Can not delete default material",
                    Toast.LENGTH_LONG).show();
            return;
        }

        String name = materials.get(position).getName();
        float shear = materials.get(position).getShear();
        float clearance= materials.get(position).getClearance();
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete");
        builder.setMessage("Do you want to delete " + name +
            " material, shear = " + shear + " (mm), clearance = " +
            clearance + " (mm)?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                database.delete("Material",
                        "id = ?", new String[] {id_material + ""});
                displayData();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog dialog =builder.create();
        dialog.show();
    }

    private boolean checkValueValidate() {
        String s1 = edtName.getText().toString().trim();
        String s2 = edtShear.getText().toString().trim();
        String s3 = edtClearance.getText().toString().trim();
        if (s1.equals("") || s2.equals("") || s3.equals("")) {
            Toast.makeText(this,
                    "Empty value, Please enter full fields to continue",
                    Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
    private void displayData() {
        materials.clear();
        materials = DatabaseManager.getMaterialData(database,
                driveSystemName);
        adapter1 = new MaterialAdapter(this,
                materials, R.layout.material_row);
        adapter1.notifyDataSetChanged();
        lvMaterial.setAdapter(adapter1);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //default material couldn't update
        int id_material = materials.get(i).getId();
        if (id_material == 1 || id_material == 2 ||
                id_material == 3) {
            edtName.setEnabled(false);
        } else {
            edtName.setEnabled(true);
        }
        //listClearance = DatabaseManager.getClearance(database, i,)
        edtName.setText(materials.get(i).getName());
        edtShear.setText(String.valueOf(materials.get(i).getShear()));
        edtClearance.setText(String.valueOf(materials.get(i).getClearance()));

        //enable button edit va delete
        btnEdit.setEnabled(true);
        btnEdit.setVisibility(View.VISIBLE);
        btnDelete.setEnabled(true);
        btnDelete.setVisibility(View.VISIBLE);

        //get item position selected
        POSITION = i;
    }
}
