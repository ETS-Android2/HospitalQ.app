package id.ac.umn.hospitalq;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import android.util.Base64;

import id.ac.umn.hospitalq.Retrofit.IMyService;
import id.ac.umn.hospitalq.Retrofit.RetrofitClient;
import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DetailDoctor extends AppCompatActivity{
    private static String email, password, doctoremail;
    private String emaildoctor1, namalengkap, tanggallahir, alamat, departement, ppicture;
    private ArrayList<DoctorData> mDaftarDocter;

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    IMyService iMyService;

    private TextView tvNamaDokter, tvEmailDokter, tvTanggalLahirDokter, tvAlamatDokter, tvDepartementtDokter;
    ImageView image_view;

    @Override
    protected void onStop(){
        compositeDisposable.clear();
        super.onStop();
    }

    Button btn_addthisdocterantrian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_doctor);

        Retrofit retrofitClient = RetrofitClient.getInstance();
        iMyService = retrofitClient.create(IMyService.class);

        Intent mainIntent = getIntent();
        email = mainIntent.getStringExtra("email");
        password = mainIntent.getStringExtra("password");
        doctoremail = mainIntent.getStringExtra("doctoremail");

        getADoctor(email,password, doctoremail);
        tvNamaDokter = (TextView) findViewById(R.id.tvNamaDokter);
        tvEmailDokter = (TextView) findViewById(R.id.tvEmailDokter);
        tvTanggalLahirDokter = (TextView) findViewById(R.id.tvTanggalLahirDokter);
        tvAlamatDokter = (TextView) findViewById(R.id.tvAlamatDokter);
        tvDepartementtDokter = (TextView) findViewById(R.id.tvDepartementtDokter);
        image_view = (ImageView) findViewById(R.id.image_view);
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
                        namalengkap = mDaftarDocters.namaLengkap();
                        emaildoctor1 = mDaftarDocters.getEmail();
                        tanggallahir = mDaftarDocters.getBirthdate();
                        alamat = mDaftarDocters.getAlamat();
                        departement = mDaftarDocters.getDepartement();
                        ppicture = mDaftarDocters.getPpicture();

                        tvNamaDokter.setText("Nama : "+namalengkap);
                        tvEmailDokter.setText("Email : "+emaildoctor1);
                        tvTanggalLahirDokter.setText("Tanggal lahir : "+tanggallahir);
                        tvAlamatDokter.setText("Alamat : "+alamat);
                        tvDepartementtDokter.setText("Departement : "+departement);
                        byte[] bytes = Base64.decode(ppicture, Base64.DEFAULT);
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                        image_view.setImageBitmap(bitmap);
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<DoctorData>> call, Throwable t) {
                Toast.makeText(DetailDoctor.this, "Fail to get data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
