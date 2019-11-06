package com.example.yukngaji.ui.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.yukngaji.Penjelasan;
import com.example.yukngaji.R;
import com.example.yukngaji.ui.Item.ItemStore;

import java.util.ArrayList;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.ViewHolder>{
    private ArrayList<ItemStore> dataList;
    Context context;
    public StoreAdapter(Context context,ArrayList<ItemStore> data)
    {
        this.dataList = data;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_liststore, viewGroup, false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.Nama.setText(dataList.get(position).getNama());
        holder.Harga.setText(dataList.get(position).getHarga());
        Glide.with(context)
                .load(dataList.get(position).getGambar())
                .centerCrop()
                .placeholder(R.drawable.ic_lock_black_24dp)
                .into(holder.Gambar);
        holder.Selengkapnya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, Penjelasan.class);
                intent.putExtra("data", dataList.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView Nama,Harga,Selengkapnya;
        ImageView Gambar;
        public ViewHolder(View itemView)
        {
            super(itemView);
            this.Nama=itemView.findViewById(R.id.NamaBarang);
            this.Harga=itemView.findViewById(R.id.harga);
            this.Gambar=itemView.findViewById(R.id.img_thumbnail);
            this.Selengkapnya=itemView.findViewById(R.id.storeselengkapnya);
        }
    }
}
