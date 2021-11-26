package id.ac.umn.hospitalq;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

import id.ac.umn.hospitalq.Retrofit.IMyService;
import id.ac.umn.hospitalq.Retrofit.RetrofitClient;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class DoctorRecyclerViewAdaptrer extends RecyclerView.Adapter<DoctorRecyclerViewAdaptrer.DoctorRecyclerViewHolder> {
    private ArrayList<DoctorData> mDaftarDocter;
    private LayoutInflater mInflater;
    private Context mContext;
    public DoctorRecyclerViewAdaptrer(Context context, ArrayList<DoctorData> mDaftarDocter){
        this.mDaftarDocter = mDaftarDocter;
        this.mInflater = LayoutInflater.from(context);
        this.mContext = context;
    }
    @NonNull
    @Override
    public DoctorRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_dokter, parent, false);
        return new DoctorRecyclerViewHolder(view, this);
    }
    @Override
    public void onBindViewHolder(@NonNull DoctorRecyclerViewHolder holder, @SuppressLint("RecyclerView") int position) {
        DoctorData mDaftarDocters = mDaftarDocter.get(position);
        holder.tvNamaDokter.setText(mDaftarDocters.namaLengkap());
        holder.tvDepartementDokter.setText(mDaftarDocters.getDepartement());
        holder.btn_addthisdocterantrian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addantrian(ListDoktorUser.getEmail(), mDaftarDocters.namaLengkap(), mDaftarDocters.getEmail());
            }
        });
    }
    @Override
    public int getItemCount() {
        return mDaftarDocter.size();
    }
    public class DoctorRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView tvNamaDokter, tvDepartementDokter;
        private Button btn_addthisdocterantrian;
        private DoctorData mDaftarDocters;
        private int mPosisi;
        private DoctorRecyclerViewAdaptrer mAdapter;
        public DoctorRecyclerViewHolder(@NonNull View itemView, DoctorRecyclerViewAdaptrer adapter) {
            super(itemView);
            mAdapter = adapter;
            tvNamaDokter = (TextView) itemView.findViewById(R.id.tvNamaDokter);
            tvDepartementDokter = (TextView) itemView.findViewById(R.id.tvDepartementDokter);
            btn_addthisdocterantrian = (Button) itemView.findViewById(R.id.btn_addthisdocterantrian);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v){
            mPosisi = getLayoutPosition();
            mDaftarDocters = mDaftarDocter.get(mPosisi);
            Intent detailIntent = new Intent(mContext, DoctorData.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("dataDokter", (Serializable) mDaftarDocters);
            detailIntent.putExtras(bundle);
            String namalengkap = tvNamaDokter.getText().toString();
            detailIntent.putExtra("namalengkap", namalengkap);
            mContext.startActivity(detailIntent);
        }
    }

    private void addantrian(String emailuser, String firstnamedoktor, String emaildoktor){
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        IMyService iMyService;
        Retrofit retrofitClient = RetrofitClient.getInstance();
        iMyService = retrofitClient.create(IMyService.class);
        compositeDisposable.add(iMyService.addAntrian(emailuser,firstnamedoktor, emaildoktor)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String response) throws Exception{
                    }
                }));
    }
}
