package id.ac.umn.hospitalq;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class DepartementAdminRecyclerViewAdapter extends RecyclerView.Adapter<DepartementAdminRecyclerViewAdapter.DepartementAdminRecyclerViewAdminHolder>{
    private ArrayList<DepartementData> mDaftarDepartement;
    private LayoutInflater mInflater;
    private Context mContext;
    public DepartementAdminRecyclerViewAdapter(Context context, ArrayList<DepartementData> mDaftarDepartement){
        this.mDaftarDepartement = mDaftarDepartement;
        this.mInflater = LayoutInflater.from(context);
        this.mContext = context;
    }
    @NonNull
    @Override
    public DepartementAdminRecyclerViewAdapter.DepartementAdminRecyclerViewAdminHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_departement, parent, false);
        return new DepartementAdminRecyclerViewAdapter.DepartementAdminRecyclerViewAdminHolder(view, this);
    }

    @Override
    public void onBindViewHolder(DepartementAdminRecyclerViewAdapter.DepartementAdminRecyclerViewAdminHolder holder, int position) {
        DepartementData mDaftarDepartements = mDaftarDepartement.get(position);
        holder.tvNamaDepartement.setText("Nama Departement: "+mDaftarDepartements.getDepartement());
    }

    @Override
    public int getItemCount() {
        return mDaftarDepartement.size();
    }
    public class DepartementAdminRecyclerViewAdminHolder extends RecyclerView.ViewHolder{
        private TextView tvNamaDepartement;
        private DepartementData mDaftarDepartements;
        private int mPosisi;
        private DepartementAdminRecyclerViewAdapter mAdapter;
        public DepartementAdminRecyclerViewAdminHolder(@NonNull View itemView, DepartementAdminRecyclerViewAdapter adapter) {
            super(itemView);
            mAdapter = adapter;
            tvNamaDepartement = (TextView) itemView.findViewById(R.id.tvNamaDepartement);
        }
    }
}
