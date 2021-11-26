package id.ac.umn.hospitalq;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class HomeScreenPasien extends AppCompatActivity {

    Button btn_doctorlist, btn_antrianuserlist, btn_bookinguser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreenpasien);

        Intent mainIntent = getIntent();
        String email = mainIntent.getStringExtra("email");
        String password = mainIntent.getStringExtra("password");
        Toast.makeText(HomeScreenPasien.this,"Selamat Datang, " + email, Toast.LENGTH_LONG).show();

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
    }

}
