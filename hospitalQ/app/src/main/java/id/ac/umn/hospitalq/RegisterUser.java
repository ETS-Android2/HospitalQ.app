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
import android.widget.Toast;

import id.ac.umn.hospitalq.Retrofit.IMyService;
import id.ac.umn.hospitalq.Retrofit.RetrofitClient;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class RegisterUser extends AppCompatActivity {

    EditText edt_register_email, edt_register_firstname, edt_register_lastname, edt_register_hari
            , edt_register_bulan, edt_register_tahun, edt_register_alamat, edt_register_password;
    Button btn_register;

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
        setContentView(R.layout.activity_register);

        Retrofit retrofitClient = RetrofitClient.getInstance();
        iMyService = retrofitClient.create(IMyService.class);

        edt_register_email = (EditText) findViewById(R.id.edt_register_email);
        edt_register_firstname = (EditText) findViewById(R.id.edt_register_firstname);
        edt_register_lastname = (EditText) findViewById(R.id.edt_register_lastname);
        edt_register_hari = (EditText) findViewById(R.id.edt_register_hari);
        edt_register_bulan = (EditText) findViewById(R.id.edt_register_bulan);
        edt_register_tahun = (EditText) findViewById(R.id.edt_register_tahun);
        edt_register_alamat = (EditText) findViewById(R.id.edt_register_alamat);
        edt_register_password = (EditText) findViewById(R.id.edt_register_password);
        btn_register = (Button) findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                String hari = edt_register_hari.getText().toString();
                String bulan = edt_register_bulan.getText().toString();
                String tahun = edt_register_tahun.getText().toString();
                int hariint=Integer.parseInt(hari);
                int bulanint=Integer.parseInt(bulan);
                int tahunint=Integer.parseInt(tahun);
                regiterUser(edt_register_email.getText().toString(),
                        edt_register_password.getText().toString(),
                        edt_register_firstname.getText().toString(),
                        edt_register_lastname.getText().toString(),
                        hariint, bulanint, tahunint,
                        edt_register_alamat.getText().toString());
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void regiterUser(String email, String password, String firstname, String lastname, int hari,
                             int bulan, int tahun, String alamat){
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Email cannot be empty", Toast.LENGTH_LONG).show();
        }if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "password cannot be empty", Toast.LENGTH_LONG).show();
        }if(TextUtils.isEmpty(firstname)){
            Toast.makeText(this, "firstname cannot be empty", Toast.LENGTH_LONG).show();
        }if(TextUtils.isEmpty(lastname)){
            Toast.makeText(this, "lastname cannot be empty", Toast.LENGTH_LONG).show();
        }if(TextUtils.isEmpty(alamat)){
            Toast.makeText(this, "alamat cannot be empty", Toast.LENGTH_LONG).show();
        }
        compositeDisposable.add(iMyService.registerUser(email,password,firstname,lastname,hari,bulan,tahun,alamat,"pasien", 0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String response1) throws Exception{
                        Toast.makeText(RegisterUser.this, ""+response1, Toast.LENGTH_SHORT).show();
                    }
                }));
    }
}
