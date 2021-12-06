package id.ac.umn.hospitalq;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class HomeScreenDokter  extends AppCompatActivity {
    Button btn_antrian, btn_profile, btn_antrianlistdokteruser, btn_logout;

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_ROLE_USER = "role";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreendokter);

        Intent mainIntent = getIntent();
        String email = mainIntent.getStringExtra("email");
        String password = mainIntent.getStringExtra("password");
        Toast.makeText(HomeScreenDokter.this,"Selamat Datang, " + email, Toast.LENGTH_LONG).show();

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String emailshared = sharedPreferences.getString(KEY_EMAIL, null);
        String password1shared = sharedPreferences.getString(KEY_PASSWORD, null);
        String roleshared = sharedPreferences.getString(KEY_ROLE_USER, null);

        btn_antrian = (Button) findViewById(R.id.btn_antrianlistdokter);
        btn_antrian.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Intent intentBookingDoktor = new Intent(HomeScreenDokter.this, BookingDoktor.class);
                intentBookingDoktor.putExtra("email", email);
                intentBookingDoktor.putExtra("password", password);
                startActivity(intentBookingDoktor);
            }
        });

        btn_antrianlistdokteruser = (Button) findViewById(R.id.btn_antrianlistdokteruser);
        btn_antrianlistdokteruser.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Intent ListBookingDokter = new Intent(HomeScreenDokter.this, ListBookingDokter.class);
                ListBookingDokter.putExtra("email", email);
                ListBookingDokter.putExtra("password", password);
                startActivity(ListBookingDokter);
            }
        });

        btn_profile = (Button) findViewById(R.id.btn_changeprofile);
        btn_profile.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Intent UpdateProfile = new Intent(HomeScreenDokter.this, UpdateProfile.class);
                UpdateProfile.putExtra("email", email);
                UpdateProfile.putExtra("password", password);
                startActivity(UpdateProfile);
            }
        });

        btn_logout = (Button) findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                Toast.makeText(HomeScreenDokter.this,"Logout Success", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }
}
