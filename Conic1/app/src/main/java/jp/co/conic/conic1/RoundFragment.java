package jp.co.conic.conic1;

import android.content.Context;
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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link RoundFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RoundFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private MainActivity mContext;

    private EditText edtA, edtThickness;
    private Spinner spinnerShearAngle;
    private Button btnCalculate;
    private int steel = 0;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RoundFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RoundFragment newInstance(String param1, String param2) {
        RoundFragment fragment = new RoundFragment();
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
        View v = inflater.inflate(R.layout.fragment_round, container, false);
        mapView(v);
        return v;
    }
    private void mapView(View v) {
        edtA = v.findViewById(R.id.edtA);
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
        /*if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }*/
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            mContext = (MainActivity) context;
            Spinner spinnerSteel = mContext.findViewById(
                    R.id.spinner_material);
            int position = spinnerSteel.getSelectedItemPosition();
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View view) {
        String sA = edtA.getText().toString().trim();
        String sthick = edtThickness.getText().toString().trim();
        if (sA.equals("") || sthick.equals("")) {
            Toast.makeText(mContext,
                    "Empty value, enter full value to calculate",
                    Toast.LENGTH_LONG).show();
            return;
        }
        float a = Float.parseFloat(sA);
        float thickness = Float.parseFloat(sthick);
        float cv = a * 3.14f;
        float punchngForce = cv * thickness *400/1000.0f;
        TextView txt = mContext.findViewById(R.id.txtPunching_force);
        txt.setText(String.valueOf(punchngForce));

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
    /*public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }*/
}
