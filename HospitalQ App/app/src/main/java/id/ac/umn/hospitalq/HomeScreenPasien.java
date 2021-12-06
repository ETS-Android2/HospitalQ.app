package id.ac.umn.hospitalq;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class HomeScreenPasien extends AppCompatActivity {

    Button btn_doctorlist, btn_antrianuserlist, btn_bookinguser, btn_changeprofile, btn_logout;

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_ROLE_USER = "role";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreenpasien);

        Intent mainIntent = getIntent();
        String email = mainIntent.getStringExtra("email");
        String password = mainIntent.getStringExtra("password");
        Toast.makeText(HomeScreenPasien.this,"Selamat Datang, " + email, Toast.LENGTH_LONG).show();

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String emailshared = sharedPreferences.getString(KEY_EMAIL, null);
        String password1shared = sharedPreferences.getString(KEY_PASSWORD, null);
        String roleshared = sharedPreferences.getString(KEY_ROLE_USER, null);

        btn_doctorlist = (Button) findViewById(R.id.btn_doktorlistuser);
        btn_doctorlist.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Intent intentListDoktorUser = new Intent(HomeScreenPasien.this, ListDoktorUser.class);
                intentListDoktorUser.putExtra("email", email);
                intentListDoktorUser.putExtra("password", password);
                startActivity(intentListDoktorUser);
            }
        });

        btn_antrianuserlist = (Button) findViewById(R.id.btn_antrianlistuser);
        btn_antrianuserlist.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Intent intentListAntrianAllUser = new Intent(HomeScreenPasien.this, ListAntrianAllUser.class);
                intentListAntrianAllUser.putExtra("email", email);
                intentListAntrianAllUser.putExtra("password", password);
                startActivity(intentListAntrianAllUser);
            }
        });

        btn_changeprofile = (Button) findViewById(R.id.btn_changeprofile);
        btn_changeprofile.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Intent UpdateProfile = new Intent(HomeScreenPasien.this, UpdateProfile.class);
                UpdateProfile.putExtra("email", email);
                UpdateProfile.putExtra("password", password);
                startActivity(UpdateProfile);
            }
        });
        btn_bookinguser = (Button) findViewById(R.id.btn_bookinguser);
        btn_bookinguser.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Intent intentListBooking = new Intent(HomeScreenPasien.this, ListBooking.class);
                intentListBooking.putExtra("email", email);
                intentListBooking.putExtra("password", password);
                startActivity(intentListBooking);
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
                Toast.makeText(HomeScreenPasien.this,"Logout Success", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }

}
