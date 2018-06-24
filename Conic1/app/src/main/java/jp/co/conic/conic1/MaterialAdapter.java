package jp.co.conic.conic1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MaterialAdapter extends BaseAdapter {
    private Context mContext;
    private List<Material> materials = new ArrayList<>();
    private int mLayout;

    public MaterialAdapter(Context mContext, List<Material> materials, int layout) {
        this.mContext = mContext;
        this.materials = materials;
        this.mLayout = layout;
    }

    @Override
    public int getCount() {
        return materials.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(mLayout, null);
        TextView txtName = view.findViewById(R.id.txtMaterialName);
        TextView txtShear = view.findViewById(R.id.txtShear);
        TextView txtClearance = view.findViewById(R.id.txtClearance);

        txtName.setText(materials.get(i).getName());
        txtShear.setText(String.valueOf(materials.get(i).getShear()));
        txtClearance.setText(String.valueOf(materials.get(i).getClearance()));
        return view;
    }
}
