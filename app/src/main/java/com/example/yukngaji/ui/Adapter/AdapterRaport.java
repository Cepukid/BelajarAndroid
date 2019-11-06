package com.example.yukngaji.ui.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yukngaji.R;
import com.example.yukngaji.setting.UserPreference;
import com.example.yukngaji.ui.Item.itemraport;

import java.util.ArrayList;

public class AdapterRaport extends RecyclerView.Adapter<AdapterRaport.ViewHolder> {
    Context context;
    UserPreference preference;
    private ArrayList<itemraport> dataList;

    public AdapterRaport(Context context, ArrayList<itemraport> data) {
        this.dataList = data;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_rapot, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.Nama.setText(dataList.get(position).getNamanilairaport());
        holder.Nilai.setText(dataList.get(position).getNilaiRaport());
        preference = new UserPreference(context);
        if (preference.getGuru()) {
            holder.Nilai.setEnabled(true);
        }
        holder.No.setText(dataList.get(position).getNo());

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Nama;
        EditText Nilai;
        TextView No;

        public ViewHolder(View itemView) {
            super(itemView);
            this.Nama = itemView.findViewById(R.id.namanilairaport);
            this.Nilai = itemView.findViewById(R.id.nilairaport);
            this.No = itemView.findViewById(R.id.nomorraport);
        }
    }
}
