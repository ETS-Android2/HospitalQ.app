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
import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ListDepartement extends AppCompatActivity {
    private RecyclerView RV;
    private ArrayList<DepartementData> mDaftarDepartement;
    private DepartementAdminRecyclerViewAdapter mAdapter;

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    IMyService iMyService;

    Button btn_registerdepartement;
    @Override
    protected void onStop(){
        compositeDisposable.clear();
        super.onStop();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listdepartement);

        Retrofit retrofitClient = RetrofitClient.getInstance();
        iMyService = retrofitClient.create(IMyService.class);


        Intent mainIntent = getIntent();
        String email = mainIntent.getStringExtra("email");
        String password = mainIntent.getStringExtra("password");

        RV = (RecyclerView) findViewById(R.id.RV);
        mDaftarDepartement = new ArrayList<>();
        getDepartement(email,password);

        btn_registerdepartement = (Button) findViewById(R.id.btn_registerdepartement);
        btn_registerdepartement.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Intent intentRegisterDepartementr = new Intent(ListDepartement.this, RegisterDepartement.class);
                startActivity(intentRegisterDepartementr);
            }
        });

    }

    private void getDepartement(String email, String password){
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Email cannot be empty", Toast.LENGTH_LONG).show();
        }if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "password cannot be empty", Toast.LENGTH_LONG).show();
        }
        Call<ArrayList<DepartementData>> call = iMyService.getDepartement(email, password);
        call.enqueue(new Callback<ArrayList<DepartementData>>() {
            @Override
            public void onResponse(Call<ArrayList<DepartementData>> call, Response<ArrayList<DepartementData>> response) {
                if (response.isSuccessful()) {
                    mDaftarDepartement = response.body();
                    for (int i = 0; i < mDaftarDepartement.size(); i++) {
                        mAdapter = new DepartementAdminRecyclerViewAdapter(ListDepartement.this, mDaftarDepartement);
                        LinearLayoutManager manager = new LinearLayoutManager(ListDepartement.this);
                        RV.setLayoutManager(manager);
                        RV.setAdapter(mAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<DepartementData>> call, Throwable t) {
                Toast.makeText(ListDepartement.this, "Fail to get data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
