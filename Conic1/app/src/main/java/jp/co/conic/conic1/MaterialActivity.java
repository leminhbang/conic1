package jp.co.conic.conic1;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

public class MaterialActivity extends AppCompatActivity implements View.OnClickListener,  AdapterView.OnItemClickListener {
    private String DBName = "MaterialDB.sqlite";
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
        materials = DatabaseManager.getMaterialData(database, driveSystemName);
        /*listMaterialName = DatabaseManager.getMaterialName(database);
        adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                listMaterialName);*/

        adapter1 = new MaterialAdapter(this,
                materials, R.layout.material_row);
        lvMaterial.setAdapter(adapter1);
        lvMaterial.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

    }

    private void displayData() {
        //listMaterialName = DatabaseManager.getMaterialName(database);
        //listShear = DatabaseManager.getShear(database);
        //adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //listClearance = DatabaseManager.getClearance(database, i,)
        edtName.setText(materials.get(i).getName());
        edtShear.setText(materials.get(i).getShear() + "");
        edtClearance.setText(materials.get(i).getClearance() + "");
    }
}
