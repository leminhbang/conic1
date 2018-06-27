package jp.co.conic.conic1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import static jp.co.conic.conic1.MainActivity.SHEAR;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link RectangleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RectangleFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private MainActivity mContext;

    private Spinner spinnerShearAngle;
    private EditText edtA, edtB, edtR, edtThickness;
    private Button btnCalculate;
    private int steel = 0;

    private final String DBName = "MaterialDB.sqlite";
    private String tooling_type, drive_system, material;
    private float shear, dimension_a, dimension_b, angle_r, thickness;
    private int figure;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RectangleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RectangleFragment newInstance(String param1, String param2) {
        RectangleFragment fragment = new RectangleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_rectangle, container, false);
        mapView(v);
        return v;
    }

    private void mapView(View v) {
        edtA = v.findViewById(R.id.edtA);
        edtR = v.findViewById(R.id.edtR);
        edtB = v.findViewById(R.id.edtB);
        edtThickness = v.findViewById(R.id.edtThickness);
        spinnerShearAngle = v.findViewById(R.id.spinnerShearAngle);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                mContext, R.array.array_shear_angle,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.
                select_dialog_singlechoice);
        spinnerShearAngle.setAdapter(adapter);
        btnCalculate = v.findViewById(R.id.btnCalculate);
        btnCalculate.setOnClickListener(this);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            mContext = (MainActivity) context;
            setValue();
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
    private void setValue() {
        Spinner spinnerToolingType = mContext.findViewById(
                R.id.spinner_tooling_type);
        Spinner spinnerDriveSystem = mContext.findViewById(
                R.id.spinner_drive_system);
        Spinner spinnerMaterial = mContext.findViewById(
                R.id.spinner_material);
        Spinner spinnerFigure = mContext.findViewById(
                R.id.spinner_figure);
        tooling_type = (String) spinnerToolingType.getSelectedItem();
        drive_system = (String) spinnerDriveSystem.getSelectedItem();
        material = (String) spinnerMaterial.getSelectedItem();
        figure = spinnerFigure.getSelectedItemPosition();
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btnCalculate:
                setValue();
                String sA = edtA.getText().toString().trim();
                String eB = edtB.getText().toString().trim();
                String eR = edtR.getText().toString().trim();
                String sthick = edtThickness.getText().toString().trim();
                if (sA.equals("") || sthick.equals("") || eB.equals("") ||
                        eR.equals("")) {
                    Toast.makeText(mContext,
                            "Empty value, enter full value to calculate",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                float a = Float.parseFloat(sA);
                float b = Float.parseFloat(eB);
                float r = Float.parseFloat(eR);
                float thickness = Float.parseFloat(sthick);
                shear = SHEAR;
                CalculationHelper cal = new CalculationHelper(tooling_type,
                        drive_system, material, shear, figure, a, b,
                        r, thickness);
                SQLiteDatabase db = DatabaseManager.initDatabase(mContext, DBName);
                float clearance = cal.getClearance(db);
                float punch_force = cal.getPunchingForce();
                float diameter = cal.getDiameter();
                String station = cal.getStation();

                TextView txtClearance = mContext.findViewById(R.id.txtClearance);
                txtClearance.setText(String.valueOf(clearance));
                TextView txtPunchForce = mContext.findViewById(R.id.txtPunching_force);
                txtPunchForce.setText(String.valueOf(punch_force));
                TextView txtDiameter = mContext.findViewById(R.id.txtDiameter);
                txtDiameter.setText(String.valueOf(diameter));
                TextView txtStation = mContext.findViewById(R.id.txtStation);
                txtStation.setText(station);
                break;
        }
    }

    /*
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
}
