package id.ac.umn.hospitalq;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class HomeScreenAdmin  extends AppCompatActivity {
    Button btn_departement, btn_listdoktoradmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreenadmin);

        Intent mainIntent = getIntent();
        String email = mainIntent.getStringExtra("email");
        String password = mainIntent.getStringExtra("password");
        Toast.makeText(HomeScreenAdmin.this,"Selamat Datang, " + email, Toast.LENGTH_LONG).show();

        btn_departement = (Button) findViewById(R.id.btn_departementlist);
        btn_departement.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Intent intentListDepartement = new Intent(HomeScreenAdmin.this, ListDepartement.class);
                intentListDepartement.putExtra("email", email);
                intentListDepartement.putExtra("password", password);
                startActivity(intentListDepartement);
            }
        });

        btn_listdoktoradmin = (Button) findViewById(R.id.btn_doktorlistadmin);
        btn_listdoktoradmin.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Intent intentListDoktorAdmin = new Intent(HomeScreenAdmin.this, ListDoktorAdmin.class);
                intentListDoktorAdmin.putExtra("email", email);
                intentListDoktorAdmin.putExtra("password", password);
                startActivity(intentListDoktorAdmin);
            }
        });
    }
}
