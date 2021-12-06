package id.ac.umn.hospitalq;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import id.ac.umn.hospitalq.Retrofit.IMyService;
import id.ac.umn.hospitalq.Retrofit.RetrofitClient;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class RegisterUser extends AppCompatActivity {

    EditText edt_register_email, edt_register_firstname, edt_register_lastname, edt_register_alamat, edt_register_password, edittext_birthday;
    Button btn_register;
    final Calendar myCalendar = Calendar.getInstance();

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
        edittext_birthday = (EditText) findViewById(R.id.Birthday);
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };
        edittext_birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(RegisterUser.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        edt_register_alamat = (EditText) findViewById(R.id.edt_register_alamat);
        edt_register_password = (EditText) findViewById(R.id.edt_register_password);
        btn_register = (Button) findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.default_user);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 20, stream);
                byte[] bytes = stream.toByteArray();
                String SImage = Base64.encodeToString(bytes, Base64.DEFAULT);
                regiterUser(edt_register_email.getText().toString(),
                        edt_register_password.getText().toString(),
                        edt_register_firstname.getText().toString(),
                        edt_register_lastname.getText().toString(),
                        edittext_birthday.getText().toString(),
                        edt_register_alamat.getText().toString(),SImage);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void regiterUser(String email, String password, String firstname, String lastname, String birthdate, String alamat, String ppicture){
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
        }if(TextUtils.isEmpty(birthdate)){
            Toast.makeText(this, "Birthdate cannot be empty", Toast.LENGTH_LONG).show();
        }
        compositeDisposable.add(iMyService.registerUser(email,password,firstname,lastname,birthdate,alamat,"pasien", 0, ppicture)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String response1) throws Exception{
                        Toast.makeText(RegisterUser.this, ""+response1, Toast.LENGTH_SHORT).show();
                    }
                }));
    }
    private void updateLabel() {
        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edittext_birthday.setText(sdf.format(myCalendar.getTime()));
    }
}
