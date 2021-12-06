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

public class ListBooking  extends AppCompatActivity {
    private static String email;
    private static String password;
    private RecyclerView RV;
    private ArrayList<BookingData> mDaftaBooking;
    private BookingRecyclerViewAdapter mAdapter;
    Button btnDeleteAntrian;

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
        setContentView(R.layout.activity_listbooking);

        Retrofit retrofitClient = RetrofitClient.getInstance();
        iMyService = retrofitClient.create(IMyService.class);


        Intent mainIntent = getIntent();
        email = mainIntent.getStringExtra("email");
        password = mainIntent.getStringExtra("password");

        RV = (RecyclerView) findViewById(R.id.RV);
        mDaftaBooking = new ArrayList<>();
        getBooking(email,password);
        btnDeleteAntrian = (Button) findViewById(R.id.btnDeleteAntrian);

    }

    private void getBooking(String email, String password){
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Email cannot be empty", Toast.LENGTH_LONG).show();
        }if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "password cannot be empty", Toast.LENGTH_LONG).show();
        }
        Call<ArrayList<BookingData>> call = iMyService.getBooking(email, password);
        call.enqueue(new Callback<ArrayList<BookingData>>() {
            @Override
            public void onResponse(Call<ArrayList<BookingData>> call, Response<ArrayList<BookingData>> response) {
                if (response.isSuccessful()) {
                    mDaftaBooking = response.body();
                    for (int i = 0; i < mDaftaBooking.size(); i++) {
                        mAdapter = new BookingRecyclerViewAdapter(ListBooking.this, mDaftaBooking);
                        LinearLayoutManager manager = new LinearLayoutManager(ListBooking.this);
                        RV.setLayoutManager(manager);
                        RV.setAdapter(mAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<BookingData>> call, Throwable t) {
                Toast.makeText(ListBooking.this, "Fail to get data", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public static String getEmail(){
        return email;
    }    public static String getPassword(){
        return password;
    }
    @Override
    public void onResume() {
        super.onResume();
        getBooking(email,password);
    }
    public void refreshlist(){
        finish();
        startActivity(getIntent());
    }
}
