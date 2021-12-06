package id.ac.umn.hospitalq;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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

public class ListDoktorUser extends AppCompatActivity {
    private static String email, password;
    private RecyclerView RV;
    private ArrayList<DoctorData> mDaftarDocter;
    private DoctorRecyclerViewAdaptrer mAdapter;

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    IMyService iMyService;

    @Override
    protected void onStop(){
        compositeDisposable.clear();
        super.onStop();
    }

    Button btn_addthisdocterantrian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listdokter);

        Retrofit retrofitClient = RetrofitClient.getInstance();
        iMyService = retrofitClient.create(IMyService.class);

        Intent mainIntent = getIntent();
        email = mainIntent.getStringExtra("email");
        password = mainIntent.getStringExtra("password");

        RV = (RecyclerView) findViewById(R.id.RV);
        mDaftarDocter = new ArrayList<>();
        getAllDoctors(email,password);

        btn_addthisdocterantrian = (Button) findViewById(R.id.btn_addthisdocterantrian);
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
                        mAdapter = new DoctorRecyclerViewAdaptrer(ListDoktorUser.this, mDaftarDocter);
                        LinearLayoutManager manager = new LinearLayoutManager(ListDoktorUser.this);
                        RV.setLayoutManager(manager);
                        RV.setAdapter(mAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<DoctorData>> call, Throwable t) {
                Toast.makeText(ListDoktorUser.this, "Fail to get data", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public static String getEmail(){
        return email;
    }
    public static String getPassword(){
        return password;
    }
    public void onResume() {
        super.onResume();
        getAllDoctors(email,password);
    }
    public void refreshlist(){
        finish();
        startActivity(getIntent());
    }
}
