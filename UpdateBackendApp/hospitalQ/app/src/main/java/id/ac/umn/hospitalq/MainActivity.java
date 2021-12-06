package id.ac.umn.hospitalq;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import java.util.regex.Pattern;

import id.ac.umn.hospitalq.Retrofit.IMyService;
import id.ac.umn.hospitalq.Retrofit.RetrofitClient;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    EditText edt_login_email, edt_login_password;
    Button btn_login, btn_toregister;

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    IMyService iMyService;

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_ROLE_USER = "role";

    @Override
    protected void onStop(){
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofitClient = RetrofitClient.getInstance();
        iMyService = retrofitClient.create(IMyService.class);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String email = sharedPreferences.getString(KEY_EMAIL, null);
        String password = sharedPreferences.getString(KEY_PASSWORD, null);
        String role = sharedPreferences.getString(KEY_ROLE_USER, null);

        if (email != null && password != null){
            if(role.equals("\"0\"")){
                Intent intentHomeScreenPasien = new Intent(MainActivity.this, HomeScreenPasien.class);
                intentHomeScreenPasien.putExtra("email", email);
                intentHomeScreenPasien.putExtra("password", password);
                startActivity(intentHomeScreenPasien);
            }else if(role.equals("\"1\"")){
                Intent HomeScreenDokter = new Intent(MainActivity.this, HomeScreenDokter.class);
                HomeScreenDokter.putExtra("email", email);
                HomeScreenDokter.putExtra("password", password);
                startActivity(HomeScreenDokter);
            }else if(role.equals("\"2\"")){
                Intent HomeScreenAdmin = new Intent(MainActivity.this, HomeScreenAdmin.class);
                HomeScreenAdmin.putExtra("email", email);
                HomeScreenAdmin.putExtra("password", password);
                startActivity(HomeScreenAdmin);
            }
        }

        edt_login_email = (EditText) findViewById(R.id.edt_login_email);
        edt_login_password = (EditText) findViewById(R.id.edt_login_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                loginUser(edt_login_email.getText().toString(),
                    edt_login_password.getText().toString());
            }
        });

        btn_toregister = (Button) findViewById(R.id.btn_toregister);
        btn_toregister.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Intent intentRegisterUser = new Intent(MainActivity.this, RegisterUser.class);
                startActivity(intentRegisterUser);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void loginUser(String email, String password){
        if(TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this, "Email cannot be empty", Toast.LENGTH_LONG).show();
        }if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "password cannot be empty", Toast.LENGTH_LONG).show();
        }
        compositeDisposable.add(iMyService.loginUser(email,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String response) throws Exception{
                        if(response.equals("\"0\"")){ //pasien
                            Intent intentHomeScreenPasien = new Intent(MainActivity.this, HomeScreenPasien.class);

                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(KEY_EMAIL, email);
                            editor.putString(KEY_PASSWORD, password);
                            editor.putString(KEY_ROLE_USER, response);
                            editor.apply();

                            intentHomeScreenPasien.putExtra("email", email);
                            intentHomeScreenPasien.putExtra("password", password);
                            startActivity(intentHomeScreenPasien);
                        }else if(response.equals("\"1\"")){ //doktor
                            Intent intentHomeScreenDokter = new Intent(MainActivity.this, HomeScreenDokter.class);

                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(KEY_EMAIL, email);
                            editor.putString(KEY_PASSWORD, password);
                            editor.putString(KEY_ROLE_USER, response);
                            editor.apply();

                            intentHomeScreenDokter.putExtra("email", email);
                            intentHomeScreenDokter.putExtra("password", password);
                            startActivity(intentHomeScreenDokter);
                        }else if(response.equals("\"2\"")){ //admin
                            Intent intentHomeScreenAdmin = new Intent(MainActivity.this, HomeScreenAdmin.class);

                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(KEY_EMAIL, email);
                            editor.putString(KEY_PASSWORD, password);
                            editor.putString(KEY_ROLE_USER, response);
                            editor.apply();

                            intentHomeScreenAdmin.putExtra("email", email);
                            intentHomeScreenAdmin.putExtra("password", password);
                            startActivity(intentHomeScreenAdmin);
                        }
                        else{
                            Toast.makeText(MainActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                        }
                    }
                }));
    }
}