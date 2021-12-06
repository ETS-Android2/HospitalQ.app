package id.ac.umn.hospitalq;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import id.ac.umn.hospitalq.Retrofit.IMyService;
import id.ac.umn.hospitalq.Retrofit.RetrofitClient;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class updateDepartement extends AppCompatActivity {
    EditText edt_update_departement;
    Button btn_update;

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
        setContentView(R.layout.activity_updatedepartement);

        Intent mainIntent = getIntent();
        String email = mainIntent.getStringExtra("email");
        String password = mainIntent.getStringExtra("password");
        String departement = mainIntent.getStringExtra("departement");

        Retrofit retrofitClient = RetrofitClient.getInstance();
        iMyService = retrofitClient.create(IMyService.class);

        edt_update_departement = (EditText) findViewById(R.id.edt_update_departement);
        edt_update_departement.setText(departement, TextView.BufferType.EDITABLE);
        btn_update = (Button) findViewById(R.id.btn_update);
        btn_update.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                updateDepartement(email, password, departement, edt_update_departement.getText().toString());
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void updateDepartement(String email, String password, String departement, String departementtoupdate){
        if(TextUtils.isEmpty(departementtoupdate)){
            Toast.makeText(this, "Departement cannot be empty", Toast.LENGTH_LONG).show();
        }
        compositeDisposable.add(iMyService.updateDepartement(email, password, departement, departementtoupdate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String response1) throws Exception{
                        Toast.makeText(updateDepartement.this, ""+response1, Toast.LENGTH_SHORT).show();
                    }
                }));
    }
}
