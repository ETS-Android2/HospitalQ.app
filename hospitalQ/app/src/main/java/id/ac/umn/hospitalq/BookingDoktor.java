package id.ac.umn.hospitalq;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import id.ac.umn.hospitalq.Retrofit.IMyService;
import id.ac.umn.hospitalq.Retrofit.RetrofitClient;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class BookingDoktor extends AppCompatActivity {

    private TextView jumlahsekarang, jumlahantrian;
    Button btn_next, btn_zero;

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    IMyService iMyService;

    @Override
    protected void onStop(){
        compositeDisposable.clear();
        super.onStop();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dokterbooking);

        Retrofit retrofitClient = RetrofitClient.getInstance();
        iMyService = retrofitClient.create(IMyService.class);

        Intent mainIntent = getIntent();
        String email = mainIntent.getStringExtra("email");
        String password = mainIntent.getStringExtra("password");

        jumlahsekarang = (TextView) findViewById(R.id.jumlahsekarang);
        jumlahantrian = (TextView) findViewById(R.id.jumlahantrian);
        btn_next = (Button) findViewById(R.id.btn_next);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                incrementAntrian(email, password);
            }
        });

        btn_zero = (Button) findViewById(R.id.btn_zero);
        btn_zero.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                zeroAntrian(email, password);
            }
        });
        getCurrentDoctorantrian(email, password);
        getDoctorantrian(email, password);
    }

    private void getCurrentDoctorantrian(String email, String password){
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Email cannot be empty", Toast.LENGTH_LONG).show();
        }if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "password cannot be empty", Toast.LENGTH_LONG).show();
        }
        compositeDisposable.add(iMyService.getCurrentDoctorantrian(email,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String response) throws Exception{
                        jumlahsekarang.setText(response);
                    }
                }));
    }
    private void getDoctorantrian(String email, String password){
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Email cannot be empty", Toast.LENGTH_LONG).show();
        }if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "password cannot be empty", Toast.LENGTH_LONG).show();
        }
        compositeDisposable.add(iMyService.getDoctorantrian(email,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String response) throws Exception{
                        jumlahantrian.setText(response);
                    }
                }));
    }
    private void incrementAntrian(String email, String password){
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Email cannot be empty", Toast.LENGTH_LONG).show();
        }if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "password cannot be empty", Toast.LENGTH_LONG).show();
        }
        compositeDisposable.add(iMyService.incrementAntrian(email,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String response) throws Exception{
                        Toast.makeText(BookingDoktor.this, ""+response, Toast.LENGTH_SHORT).show();
                        getCurrentDoctorantrian(email, password);
                        getDoctorantrian(email, password);
                    }
                }));
    }
    private void zeroAntrian(String email, String password){
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Email cannot be empty", Toast.LENGTH_LONG).show();
        }if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "password cannot be empty", Toast.LENGTH_LONG).show();
        }
        compositeDisposable.add(iMyService.zeroAntrian(email,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String response) throws Exception{
                        Toast.makeText(BookingDoktor.this, ""+response, Toast.LENGTH_SHORT).show();
                        getCurrentDoctorantrian(email, password);
                        getDoctorantrian(email, password);
                    }
                }));
    }
}
