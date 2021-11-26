package id.ac.umn.hospitalq;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import id.ac.umn.hospitalq.Retrofit.IMyService;
import id.ac.umn.hospitalq.Retrofit.RetrofitClient;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ListDoktorAdmin extends AppCompatActivity {
    private RecyclerView RV;
    private ArrayList<DoctorData> mDaftarDocter;
    private DoctorRecyclerViewAdminAdapter mAdapter;

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    IMyService iMyService;

    @Override
    protected void onStop(){
        compositeDisposable.clear();
        super.onStop();
    }

    Button btn_registerdokter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listdokteradmin);

        Retrofit retrofitClient = RetrofitClient.getInstance();
        iMyService = retrofitClient.create(IMyService.class);


        Intent mainIntent = getIntent();
        String email = mainIntent.getStringExtra("email");
        String password = mainIntent.getStringExtra("password");

        RV = (RecyclerView) findViewById(R.id.RV);
        mDaftarDocter = new ArrayList<>();
        getAllDoctors(email,password);

        btn_registerdokter = (Button) findViewById(R.id.btn_registerdokter);
        btn_registerdokter.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Intent intentRegisterUser = new Intent(ListDoktorAdmin.this, RegisterDoctor.class);
                startActivity(intentRegisterUser);
            }
        });
    }

    private void getAllDoctors(String email, String password){
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Email cannot be empty", Toast.LENGTH_LONG).show();
        }if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "password cannot be empty", Toast.LENGTH_LONG).show();
        }
        Call<ArrayList<DoctorData>> call = iMyService.getAllDoctors(email, password);
        call.enqueue(new Callback<ArrayList<DoctorData>>() {
            @Override
            public void onResponse(Call<ArrayList<DoctorData>> call, Response<ArrayList<DoctorData>> response) {
                if (response.isSuccessful()) {
                    mDaftarDocter = response.body();
                    for (int i = 0; i < mDaftarDocter.size(); i++) {
                        mAdapter = new DoctorRecyclerViewAdminAdapter(ListDoktorAdmin.this, mDaftarDocter);
                        LinearLayoutManager manager = new LinearLayoutManager(ListDoktorAdmin.this);
                        RV.setLayoutManager(manager);
                        RV.setAdapter(mAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<DoctorData>> call, Throwable t) {
                Toast.makeText(ListDoktorAdmin.this, "Fail to get data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
