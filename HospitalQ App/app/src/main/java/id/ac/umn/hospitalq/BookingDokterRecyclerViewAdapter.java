package id.ac.umn.hospitalq;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import id.ac.umn.hospitalq.Retrofit.IMyService;
import id.ac.umn.hospitalq.Retrofit.RetrofitClient;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class BookingDokterRecyclerViewAdapter extends RecyclerView.Adapter<BookingDokterRecyclerViewAdapter.BookingDokterRecyclerViewAdminHolder>{
    private ArrayList<BookingData> mDaftarBooking;
    private LayoutInflater mInflater;
    private Context mContext;
    public BookingDokterRecyclerViewAdapter(Context context, ArrayList<BookingData> mDaftarBooking){
        this.mDaftarBooking = mDaftarBooking;
        this.mInflater = LayoutInflater.from(context);
        this.mContext = context;
    }

    @NonNull
    @Override
    public BookingDokterRecyclerViewAdapter.BookingDokterRecyclerViewAdminHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_booking, parent, false);
        return new BookingDokterRecyclerViewAdapter.BookingDokterRecyclerViewAdminHolder(view, this);
    }

    @Override
    public void onBindViewHolder(BookingDokterRecyclerViewAdapter.BookingDokterRecyclerViewAdminHolder holder, int position) {
        BookingData mDaftarBookings = mDaftarBooking.get(position);
        holder.tvNamaDokter.setText("Email Pasien: "+mDaftarBookings.getUser_name());
        holder.btnDeleteAntrian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage("Confirm Delete?");
                builder.setCancelable(true);
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(mContext, "Antrian Deleted", Toast.LENGTH_SHORT).show();
                        deleteAntrian(mDaftarBookings.getUser_name(), mDaftarBookings.getDoktor_name(), mDaftarBookings.getDoctor_email());
                        ((ListBookingDokter) mContext).refreshlist();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDaftarBooking.size();
    }
    public class BookingDokterRecyclerViewAdminHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView tvNamaDokter;
        private Button btnDeleteAntrian;
        private BookingData mDaftarBookings;
        private int mPosisi;
        private BookingDokterRecyclerViewAdapter mAdapter;
        public BookingDokterRecyclerViewAdminHolder(@NonNull View itemView, BookingDokterRecyclerViewAdapter adapter) {
            super(itemView);
            mAdapter = adapter;
            tvNamaDokter = (TextView) itemView.findViewById(R.id.tvNamaDokter);
            btnDeleteAntrian = (Button) itemView.findViewById(R.id.btnDeleteAntrian);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v){
            mPosisi = getLayoutPosition();
            mDaftarBookings = mDaftarBooking.get(mPosisi);
            Intent DetailDoctor = new Intent(mContext, DetailDoctor.class);
            String email = ListBookingDokter.getEmail();
            String password = ListBookingDokter.getPassword();
            String doctoremail = mDaftarBookings.getUser_name();
            DetailDoctor.putExtra("email", email);
            DetailDoctor.putExtra("password", password);
            DetailDoctor.putExtra("doctoremail", doctoremail);
            mContext.startActivity(DetailDoctor);
        }
    }

    private void deleteAntrian(String user_name, String doktor_name, String doktor_email){
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        IMyService iMyService;
        Retrofit retrofitClient = RetrofitClient.getInstance();
        iMyService = retrofitClient.create(IMyService.class);
        compositeDisposable.add(iMyService.deleteAntrian(user_name,doktor_name, doktor_email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String response) throws Exception{
                    }
                }));
    }
}
