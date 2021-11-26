package id.ac.umn.hospitalq;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class BookingRecyclerViewAdapter extends RecyclerView.Adapter<BookingRecyclerViewAdapter.BookingRecyclerViewAdminHolder>{
    private ArrayList<BookingData> mDaftarBooking;
    private LayoutInflater mInflater;
    private Context mContext;
    public BookingRecyclerViewAdapter(Context context, ArrayList<BookingData> mDaftarBooking){
        this.mDaftarBooking = mDaftarBooking;
        this.mInflater = LayoutInflater.from(context);
        this.mContext = context;
    }
    @NonNull
    @Override
    public BookingRecyclerViewAdapter.BookingRecyclerViewAdminHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_booking, parent, false);
        return new BookingRecyclerViewAdapter.BookingRecyclerViewAdminHolder(view, this);
    }

    @Override
    public void onBindViewHolder(BookingRecyclerViewAdapter.BookingRecyclerViewAdminHolder holder, int position) {
        BookingData mDaftarBookings = mDaftarBooking.get(position);
        holder.tvNamaDokter.setText("Nama dokter: "+mDaftarBookings.getDoktor_name());
    }

    @Override
    public int getItemCount() {
        return mDaftarBooking.size();
    }
    public class BookingRecyclerViewAdminHolder extends RecyclerView.ViewHolder{
        private TextView tvNamaDokter;
        private AntrianData mDaftarBookings;
        private int mPosisi;
        private BookingRecyclerViewAdapter mAdapter;
        public BookingRecyclerViewAdminHolder(@NonNull View itemView, BookingRecyclerViewAdapter adapter) {
            super(itemView);
            mAdapter = adapter;
            tvNamaDokter = (TextView) itemView.findViewById(R.id.tvNamaDokter);
        }
    }
}
