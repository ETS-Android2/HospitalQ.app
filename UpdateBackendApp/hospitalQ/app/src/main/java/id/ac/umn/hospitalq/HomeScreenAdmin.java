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

public class HomeScreenAdmin  extends AppCompatActivity {
    Button btn_departement, btn_listdoktoradmin, btn_logout;

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_ROLE_USER = "role";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreenadmin);

        Intent mainIntent = getIntent();
        String email = mainIntent.getStringExtra("email");
        String password = mainIntent.getStringExtra("password");
        Toast.makeText(HomeScreenAdmin.this,"Selamat Datang, " + email, Toast.LENGTH_LONG).show();

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String emailshared = sharedPreferences.getString(KEY_EMAIL, null);
        String password1shared = sharedPreferences.getString(KEY_PASSWORD, null);
        String roleshared = sharedPreferences.getString(KEY_ROLE_USER, null);

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

        btn_logout = (Button) findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                Toast.makeText(HomeScreenAdmin.this,"Logout Success", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }
}
