package id.ac.umn.hospitalq;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

import id.ac.umn.hospitalq.Retrofit.IMyService;
import id.ac.umn.hospitalq.Retrofit.RetrofitClient;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

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
        holder.btnDeleteDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage("Confirm Delete?");
                builder.setCancelable(true);
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(mContext, "Doctor Deleted", Toast.LENGTH_SHORT).show();
                        deleteUser(ListDoktorAdmin.getEmail(), ListDoktorAdmin.getPassword(),mDaftarDocters.getEmail());
                        ((ListDoktorAdmin) mContext).refreshlist();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDaftarDocter.size();
    }
    public class DoctorRecyclerViewAdminHolder extends RecyclerView.ViewHolder{
        private TextView tvNamaDokter, tvDepartementDokter;
        private Button btnDeleteDoctor;
        private DoctorData mDaftarDocters;
        private int mPosisi;
        private DoctorRecyclerViewAdminAdapter mAdapter;
        public DoctorRecyclerViewAdminHolder(@NonNull View itemView, DoctorRecyclerViewAdminAdapter adapter) {
            super(itemView);
            mAdapter = adapter;
            tvNamaDokter = (TextView) itemView.findViewById(R.id.tvNamaDokter);
            tvDepartementDokter = (TextView) itemView.findViewById(R.id.tvDepartementDokter);
            btnDeleteDoctor = (Button) itemView.findViewById(R.id.btnDeleteDoctor);
        }
    }

    private void deleteUser(String emailuser, String password, String emailtodelete){
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        IMyService iMyService;
        Retrofit retrofitClient = RetrofitClient.getInstance();
        iMyService = retrofitClient.create(IMyService.class);
        compositeDisposable.add(iMyService.deleteUser(emailuser,password, emailtodelete)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String response) throws Exception{
                    }
                }));
    }
}
