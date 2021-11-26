package id.ac.umn.hospitalq;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class HomeScreenDokter  extends AppCompatActivity {
    Button btn_antrian, btn_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreendokter);

        Intent mainIntent = getIntent();
        String email = mainIntent.getStringExtra("email");
        String password = mainIntent.getStringExtra("password");
        Toast.makeText(HomeScreenDokter.this,"Selamat Datang, " + email, Toast.LENGTH_LONG).show();

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

        btn_profile = (Button) findViewById(R.id.btn_changeprofile);
        btn_profile.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Intent intentBookingDoktor = new Intent(HomeScreenDokter.this, BookingDoktor.class);
                intentBookingDoktor.putExtra("email", email);
                intentBookingDoktor.putExtra("password", password);
                startActivity(intentBookingDoktor);
            }
        });
    }
}
