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

public class ListAntrianAllUser extends AppCompatActivity {
    private RecyclerView RV;
    private ArrayList<AntrianData> mDaftarAntrian;
    private AntrianAllRecyclerViewAdapter mAdapter;

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    IMyService iMyService;

    @Override
    protected void onStop(){
        compositeDisposable.clear();
        super.onStop();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listantrianalluser);

        Retrofit retrofitClient = RetrofitClient.getInstance();
        iMyService = retrofitClient.create(IMyService.class);


        Intent mainIntent = getIntent();
        String email = mainIntent.getStringExtra("email");
        String password = mainIntent.getStringExtra("password");

        RV = (RecyclerView) findViewById(R.id.RV);
        mDaftarAntrian = new ArrayList<>();
        getAllAntrian(email,password);

    }

    private void getAllAntrian(String email, String password){
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Email cannot be empty", Toast.LENGTH_LONG).show();
        }if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "password cannot be empty", Toast.LENGTH_LONG).show();
        }
        Call<ArrayList<AntrianData>> call = iMyService.getAllAntrian(email, password);
        call.enqueue(new Callback<ArrayList<AntrianData>>() {
            @Override
            public void onResponse(Call<ArrayList<AntrianData>> call, Response<ArrayList<AntrianData>> response) {
                if (response.isSuccessful()) {
                    mDaftarAntrian = response.body();
                    for (int i = 0; i < mDaftarAntrian.size(); i++) {
                        mAdapter = new AntrianAllRecyclerViewAdapter(ListAntrianAllUser.this, mDaftarAntrian);
                        LinearLayoutManager manager = new LinearLayoutManager(ListAntrianAllUser.this);
                        RV.setLayoutManager(manager);
                        RV.setAdapter(mAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<AntrianData>> call, Throwable t) {
                Toast.makeText(ListAntrianAllUser.this, "Fail to get data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
