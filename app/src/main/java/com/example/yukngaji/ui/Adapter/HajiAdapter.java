package com.example.yukngaji.ui.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yukngaji.PenjelasanHaji;
import com.example.yukngaji.R;
import com.example.yukngaji.ui.Item.ItemHaji;

import java.util.ArrayList;

public class HajiAdapter extends RecyclerView.Adapter<HajiAdapter.ViewHolder>{
    private ArrayList<ItemHaji> dataList;
    Context context;
    public HajiAdapter(Context context,ArrayList<ItemHaji> data)
    {
        this.dataList = data;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_listhaji, viewGroup, false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.Nama.setText(dataList.get(position).getNama());
        holder.Penjelasan.setText(dataList.get(position).getPenjelasan());
        holder.Selengkapnya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, PenjelasanHaji.class);
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
        TextView Nama,Penjelasan,Selengkapnya;
        ImageView Gambar;
        public ViewHolder(View itemView)
        {
            super(itemView);
            this.Nama=itemView.findViewById(R.id.NamaHaji);
            this.Penjelasan=itemView.findViewById(R.id.penjelasan_haji);
            this.Gambar=itemView.findViewById(R.id.img_thumbnailhaji);
            this.Selengkapnya=itemView.findViewById(R.id.selengkapnya_haji);
        }
    }
}
