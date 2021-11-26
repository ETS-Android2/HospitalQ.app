package id.ac.umn.hospitalq;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import id.ac.umn.hospitalq.Retrofit.IMyService;
import id.ac.umn.hospitalq.Retrofit.RetrofitClient;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class RegisterDepartement extends AppCompatActivity {

    EditText edt_register_departement;
    Button btn_register;

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
        setContentView(R.layout.activity_registerdepartement);

        Retrofit retrofitClient = RetrofitClient.getInstance();
        iMyService = retrofitClient.create(IMyService.class);

        edt_register_departement = (EditText) findViewById(R.id.edt_register_departement);
        btn_register = (Button) findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                addDepartement(edt_register_departement.getText().toString());
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void addDepartement(String departement){
        if(TextUtils.isEmpty(departement)){
            Toast.makeText(this, "Departement cannot be empty", Toast.LENGTH_LONG).show();
        }
        int userrole = 1;
        compositeDisposable.add(iMyService.addDepartement(departement) //userrole doctor
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String response1) throws Exception{
                        Toast.makeText(RegisterDepartement.this, ""+response1, Toast.LENGTH_SHORT).show();
                    }
                }));
    }
}
