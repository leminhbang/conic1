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
    private EditText edtName,  edtShear;
    private Button btnAdd, btnEdit, btnDelete;
    private ListView lvMaterial;

    private List<String> listMaterialName;
    private List<Float> listShear;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material);

        database = DatabaseManager.initDatabase(this, DBName);

        mapView();
        displayData();
    }

    private void mapView() {
        edtName = findViewById(R.id.edtMaterialName);
        edtShear = findViewById(R.id.edtValue);
        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);
        btnEdit = findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(this);
        btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(this);
        lvMaterial = findViewById(R.id.lvMaterial);
        listMaterialName = DatabaseManager.getMaterialName(database);
        adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                listMaterialName);
        lvMaterial.setAdapter(adapter);
        lvMaterial.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

    }

    private void displayData() {
        listMaterialName = DatabaseManager.getMaterialName(database);
        listShear = DatabaseManager.getShear(database);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        edtName.setText(listMaterialName.get(i));
        edtShear.setText(listShear.get(i) + "");
    }
}
