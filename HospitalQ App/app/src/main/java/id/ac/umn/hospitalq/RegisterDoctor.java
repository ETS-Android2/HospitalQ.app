package id.ac.umn.hospitalq;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import android.util.Base64;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

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

public class RegisterDoctor extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText edt_register_email, edt_register_firstname, edt_register_lastname, edt_register_alamat, edt_register_password, edittext_birthday;
    Button btn_register;
    Spinner spinner_1;
    final Calendar myCalendar = Calendar.getInstance();

    private ArrayList<DepartementData> mDaftarDepartement;
    private List<String> list;
    private ArrayAdapter<String> adapter;
    String departement;

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
        setContentView(R.layout.activity_registerdokter);

        Retrofit retrofitClient = RetrofitClient.getInstance();
        iMyService = retrofitClient.create(IMyService.class);

        Intent mainIntent = getIntent();
        String email = mainIntent.getStringExtra("email");
        String password = mainIntent.getStringExtra("password");
        mDaftarDepartement = new ArrayList<>();
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
                new DatePickerDialog(RegisterDoctor.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        edt_register_alamat = (EditText) findViewById(R.id.edt_register_alamat);
        edt_register_password = (EditText) findViewById(R.id.edt_register_password);

        spinner_1 = (Spinner) findViewById(R.id.spinner1);
        spinner_1.setOnItemSelectedListener(this);
        list = new ArrayList<String>();
        getDepartement(email,password);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_1.setAdapter(adapter);

        btn_register = (Button) findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.default_doctor);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 20, stream);
                byte[] bytes = stream.toByteArray();
                String SImage = Base64.encodeToString(bytes, Base64.DEFAULT);
                regiterUser(edt_register_email.getText().toString(),
                        edt_register_password.getText().toString(),
                        edt_register_firstname.getText().toString(),
                        edt_register_lastname.getText().toString(),
                        edittext_birthday.getText().toString(),
                        edt_register_alamat.getText().toString(),
                        departement,
                        SImage);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void regiterUser(String email, String password, String firstname, String lastname, String birthdate, String alamat, String departement, String ppicture){
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
        int userrole = 1;
        compositeDisposable.add(iMyService.registerUser(email,password,firstname,lastname,birthdate,alamat,departement, userrole, ppicture) //userrole doctor
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String response1) throws Exception{
                        Toast.makeText(RegisterDoctor.this, ""+response1, Toast.LENGTH_SHORT).show();
                    }
                }));
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
                        DepartementData mDaftarDepartements = mDaftarDepartement.get(i);
                        list.add(mDaftarDepartements.getDepartement());
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<DepartementData>> call, Throwable t) {
                Toast.makeText(RegisterDoctor.this, "Fail to get data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
        String item = parent.getItemAtPosition(position).toString();
        departement = item;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    private void updateLabel() {
        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edittext_birthday.setText(sdf.format(myCalendar.getTime()));
    }
}
