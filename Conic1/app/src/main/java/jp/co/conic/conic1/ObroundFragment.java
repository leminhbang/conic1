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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ObroundFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ObroundFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ObroundFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private MainActivity mContext;
    private EditText edtA, edtB, edtR, edtThickness;
    private Spinner spinnerShearAngle;
    private Button btnCalculate;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ObroundFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ObroundFragment newInstance(String param1, String param2) {
        ObroundFragment fragment = new ObroundFragment();
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
        View v = inflater.inflate(R.layout.fragment_obround, container, false);
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
                simple_list_item_single_choice);
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
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
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
