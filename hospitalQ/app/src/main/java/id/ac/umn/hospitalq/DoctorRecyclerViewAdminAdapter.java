package id.ac.umn.hospitalq;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

public class DoctorRecyclerViewAdminAdapter extends RecyclerView.Adapter<DoctorRecyclerViewAdminAdapter.DoctorRecyclerViewAdminHolder>{
    private ArrayList<DoctorData> mDaftarDocter;
    private LayoutInflater mInflater;
    private Context mContext;
    public DoctorRecyclerViewAdminAdapter(Context context, ArrayList<DoctorData> mDaftarDocter){
        this.mDaftarDocter = mDaftarDocter;
        this.mInflater = LayoutInflater.from(context);
        this.mContext = context;
    }
    @NonNull
    @Override
    public DoctorRecyclerViewAdminHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_dokter_admin, parent, false);
        return new DoctorRecyclerViewAdminHolder(view, this);
    }

    @Override
    public void onBindViewHolder(DoctorRecyclerViewAdminHolder holder, int position) {
        DoctorData mDaftarDocters = mDaftarDocter.get(position);
        holder.tvNamaDokter.setText(mDaftarDocters.namaLengkap());
        holder.tvDepartementDokter.setText(mDaftarDocters.getDepartement());
    }

    @Override
    public int getItemCount() {
        return mDaftarDocter.size();
    }
    public class DoctorRecyclerViewAdminHolder extends RecyclerView.ViewHolder{
        private TextView tvNamaDokter, tvDepartementDokter;
        private DoctorData mDaftarDocters;
        private int mPosisi;
        private DoctorRecyclerViewAdminAdapter mAdapter;
        public DoctorRecyclerViewAdminHolder(@NonNull View itemView, DoctorRecyclerViewAdminAdapter adapter) {
            super(itemView);
            mAdapter = adapter;
            tvNamaDokter = (TextView) itemView.findViewById(R.id.tvNamaDokter);
            tvDepartementDokter = (TextView) itemView.findViewById(R.id.tvDepartementDokter);
        }
    }
}
