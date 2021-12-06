package id.ac.umn.hospitalq;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
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
        holder.btnDeleteDepartement.setOnClickListener(new View.OnClickListener() {
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
                        Toast.makeText(mContext, "Departement Deleted", Toast.LENGTH_SHORT).show();
                        deleteDepartement(ListDepartement.getEmail(), ListDepartement.getPassword(),mDaftarDepartements.getDepartement());
                        ((ListDepartement) mContext).refreshlist();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDaftarDepartement.size();
    }
    public class DepartementAdminRecyclerViewAdminHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView tvNamaDepartement;
        private Button btnDeleteDepartement;
        private DepartementData mDaftarDepartements;
        private int mPosisi;
        private DepartementAdminRecyclerViewAdapter mAdapter;
        public DepartementAdminRecyclerViewAdminHolder(@NonNull View itemView, DepartementAdminRecyclerViewAdapter adapter) {
            super(itemView);
            mAdapter = adapter;
            tvNamaDepartement = (TextView) itemView.findViewById(R.id.tvNamaDepartement);
            btnDeleteDepartement = (Button) itemView.findViewById(R.id.btnDeleteDepartement);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v){
            mPosisi = getLayoutPosition();
            mDaftarDepartements = mDaftarDepartement.get(mPosisi);
            Intent updateDepartement = new Intent(mContext, updateDepartement.class);
            updateDepartement.putExtra("email", ListDepartement.getEmail());
            updateDepartement.putExtra("password", ListDepartement.getPassword());
            updateDepartement.putExtra("departement", mDaftarDepartements.getDepartement());
            mContext.startActivity(updateDepartement);
        }
    }

    private void deleteDepartement(String emailuser, String password, String departementtodelete){
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        IMyService iMyService;
        Retrofit retrofitClient = RetrofitClient.getInstance();
        iMyService = retrofitClient.create(IMyService.class);
        compositeDisposable.add(iMyService.deleteDepartement(emailuser,password, departementtodelete)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String response) throws Exception{
                    }
                }));
    }
}
