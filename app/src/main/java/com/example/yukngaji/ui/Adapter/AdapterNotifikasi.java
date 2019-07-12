package com.example.yukngaji.ui.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yukngaji.PenjelasanPromoActivity;
import com.example.yukngaji.R;
import com.example.yukngaji.ui.Item.ItemNotif;

import java.util.ArrayList;
import java.util.List;

public class AdapterNotifikasi extends RecyclerView.Adapter<AdapterNotifikasi.ViewHolder>{
    private List<ItemNotif> items = new ArrayList<>();
    private Context context;

    public AdapterNotifikasi(Context context, List<ItemNotif> items) {
        this.items = items;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.itemnotifikasi, viewGroup, false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.judul.setText(items.get(i).getJudul());
        viewHolder.Penjelasan.setText(items.get(i).getDescription());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, PenjelasanPromoActivity.class);
                intent.putExtra("judul",items.get(i).getJudul() );
                intent.putExtra("penjelasan",items.get(i).getDescription());
                intent.putExtra("gambar",items.get(i).getGambar());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView judul,Penjelasan;
        public ViewHolder(@NonNull View v) {
            super(v);
            judul =  v.findViewById(R.id.judulnotif);
            Penjelasan=v.findViewById(R.id.notifdescription);
        }
    }
}
