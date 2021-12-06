package id.ac.umn.hospitalq;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import android.util.Base64;

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

public class UpdateProfile extends AppCompatActivity{
    private static String email, password, doctoremail;
    private String emaildoctor1, firstname, lastname, tanggallahir, alamat, departement, ppicture;
    private ArrayList<DoctorData> mDaftarDocter;

    final Calendar myCalendar = Calendar.getInstance();

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    IMyService iMyService;

    private EditText edt_update_email, edt_update_firstname, edt_update_lastname, edittext_birthday, edt_update_alamat;
    ImageView image_view;
    Button btn_changeppicture, btn_update;

    @Override
    protected void onStop(){
        compositeDisposable.clear();
        super.onStop();
    }

    Button btn_addthisdocterantrian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateprofile);

        Retrofit retrofitClient = RetrofitClient.getInstance();
        iMyService = retrofitClient.create(IMyService.class);

        Intent mainIntent = getIntent();
        email = mainIntent.getStringExtra("email");
        password = mainIntent.getStringExtra("password");

        if(ContextCompat.checkSelfPermission(UpdateProfile.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(UpdateProfile.this,
                    new String[]{
                            Manifest.permission.CAMERA
                    },100);
        }

        getADoctor(email,password, email);
        edt_update_firstname = (EditText) findViewById(R.id.edt_update_firstname);
        edt_update_lastname = (EditText) findViewById(R.id.edt_update_lastname);
        edt_update_email = (EditText) findViewById(R.id.edt_update_email);
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
                new DatePickerDialog(UpdateProfile.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        edt_update_alamat = (EditText) findViewById(R.id.edt_update_alamat);
        image_view = (ImageView) findViewById(R.id.image_view);
        btn_update = (Button) findViewById(R.id.btn_update);
        btn_update.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                updateUser(edt_update_email.getText().toString(),
                        edt_update_firstname.getText().toString(),
                        edt_update_lastname.getText().toString(),
                        edittext_birthday.getText().toString(),
                        edt_update_alamat.getText().toString(),
                        departement,
                        ppicture);
            }
        });
        btn_changeppicture = (Button) findViewById(R.id.btn_changeppicture);
        btn_changeppicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 100);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 100){
            Bitmap captureImage = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            captureImage.compress(Bitmap.CompressFormat.JPEG, 20, stream);
            byte[] bytes = stream.toByteArray();
            ppicture = Base64.encodeToString(bytes, Base64.DEFAULT);
            image_view.setImageBitmap(captureImage);
        }
    }

    private void getADoctor(String email, String password, String doctoremail){
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Email cannot be empty", Toast.LENGTH_LONG).show();
        }if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "password cannot be empty", Toast.LENGTH_LONG).show();
        }
        Call<ArrayList<DoctorData>> call = iMyService.getADoctor(email, password, doctoremail);
        call.enqueue(new Callback<ArrayList<DoctorData>>() {
            @Override
            public void onResponse(Call<ArrayList<DoctorData>> call, Response<ArrayList<DoctorData>> response) {
                if (response.isSuccessful()) {
                    mDaftarDocter = response.body();
                    for (int i = 0; i < mDaftarDocter.size(); i++) {
                        DoctorData mDaftarDocters = mDaftarDocter.get(i);
                        firstname = mDaftarDocters.getFirstname();
                        lastname = mDaftarDocters.getLastname();
                        emaildoctor1 = mDaftarDocters.getEmail();
                        tanggallahir = mDaftarDocters.getBirthdate();
                        alamat = mDaftarDocters.getAlamat();
                        departement = mDaftarDocters.getDepartement();
                        ppicture = mDaftarDocters.getPpicture();

                        edt_update_firstname.setText(firstname, TextView.BufferType.EDITABLE);
                        edt_update_lastname.setText(lastname, TextView.BufferType.EDITABLE);
                        edt_update_email.setText(emaildoctor1, TextView.BufferType.EDITABLE);
                        edittext_birthday.setText(tanggallahir);
                        edt_update_alamat.setText(alamat, TextView.BufferType.EDITABLE);

                        byte[] bytes = Base64.decode(ppicture, Base64.DEFAULT);
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                        image_view.setImageBitmap(bitmap);
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<DoctorData>> call, Throwable t) {
                Toast.makeText(UpdateProfile.this, "Fail to get data", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void updateLabel() {
        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edittext_birthday.setText(sdf.format(myCalendar.getTime()));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void updateUser(String email, String firstname, String lastname, String birthdate, String alamat, String departement, String ppicture){
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
        if(TextUtils.isEmpty(departement)){
            Toast.makeText(this, "birthdate cannot be empty", Toast.LENGTH_LONG).show();
        }
        if(TextUtils.isEmpty(birthdate)){
            Toast.makeText(this, "birthdate cannot be empty", Toast.LENGTH_LONG).show();
        }
        if(TextUtils.isEmpty(ppicture)){
            Toast.makeText(this, "ppicture cannot be empty", Toast.LENGTH_LONG).show();
        }
        compositeDisposable.add(iMyService.updateUser(email, firstname, lastname, birthdate, alamat, departement, ppicture)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String response1) throws Exception{
                        Toast.makeText(UpdateProfile.this, ""+response1, Toast.LENGTH_SHORT).show();
                    }
                }));
    }
}
