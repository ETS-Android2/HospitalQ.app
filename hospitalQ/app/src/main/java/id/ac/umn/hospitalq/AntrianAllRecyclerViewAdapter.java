package id.ac.umn.hospitalq;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class AntrianAllRecyclerViewAdapter extends RecyclerView.Adapter<AntrianAllRecyclerViewAdapter.AntrianAllRecyclerViewAdminHolder>{
    private ArrayList<AntrianData> mDaftarAntrian;
    private LayoutInflater mInflater;
    private Context mContext;
    public AntrianAllRecyclerViewAdapter(Context context, ArrayList<AntrianData> mDaftarAntrian){
        this.mDaftarAntrian = mDaftarAntrian;
        this.mInflater = LayoutInflater.from(context);
        this.mContext = context;
    }
    @NonNull
    @Override
    public AntrianAllRecyclerViewAdapter.AntrianAllRecyclerViewAdminHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_antrian, parent, false);
        return new AntrianAllRecyclerViewAdapter.AntrianAllRecyclerViewAdminHolder(view, this);
    }

    @Override
    public void onBindViewHolder(AntrianAllRecyclerViewAdapter.AntrianAllRecyclerViewAdminHolder holder, int position) {
        AntrianData mDaftarAntrians = mDaftarAntrian.get(position);
        holder.tvNamaDokter.setText("Nama dokter: "+mDaftarAntrians.getDoktor_name());
        holder.tvcurrentantrian.setText("Antrian Sekarang: "+mDaftarAntrians.getCurrent_antrian());
        holder.tvtotalantrian.setText("Total Antrian: "+mDaftarAntrians.getAntrian());
    }

    @Override
    public int getItemCount() {
        return mDaftarAntrian.size();
    }
    public class AntrianAllRecyclerViewAdminHolder extends RecyclerView.ViewHolder{
        private TextView tvNamaDokter, tvcurrentantrian, tvtotalantrian;
        private AntrianData mDaftarAntrians;
        private int mPosisi;
        private AntrianAllRecyclerViewAdapter mAdapter;
        public AntrianAllRecyclerViewAdminHolder(@NonNull View itemView, AntrianAllRecyclerViewAdapter adapter) {
            super(itemView);
            mAdapter = adapter;
            tvNamaDokter = (TextView) itemView.findViewById(R.id.tvNamaDokter);
            tvcurrentantrian = (TextView) itemView.findViewById(R.id.tvcurrentantrian);
            tvtotalantrian = (TextView) itemView.findViewById(R.id.tvtotalantrian);
        }
    }
}
