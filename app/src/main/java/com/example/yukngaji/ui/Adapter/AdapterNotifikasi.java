package com.example.yukngaji.ui.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yukngaji.PenjelasanPromoActivity;
import com.example.yukngaji.R;
import com.example.yukngaji.ui.Item.ItemNotif;

import java.util.ArrayList;

public class AdapterNotifikasi extends RecyclerView.Adapter<AdapterNotifikasi.NotifikasiViewHolder> {
    private ArrayList<ItemNotif> item = new ArrayList<>();
    private Context context;

    public AdapterNotifikasi(Context context, ArrayList<ItemNotif> items) {
        this.item = items;
        this.context = context;
    }
    @NonNull
    @Override
    public NotifikasiViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.itemnotifikasi, viewGroup, false);
        return new NotifikasiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotifikasiViewHolder viewHolder, final int i) {
        viewHolder.judul.setText(item.get(i).getJudul());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            viewHolder.Penjelasan.setText(Html.fromHtml(item.get(i).getDescription(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            viewHolder.Penjelasan.setText(Html.fromHtml(item.get(i).getDescription()));
        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, PenjelasanPromoActivity.class);
                intent.putExtra("judul", item.get(i).getJudul());
                intent.putExtra("penjelasan", item.get(i).getDescription());
                intent.putExtra("gambar", item.get(i).getGambar());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public class NotifikasiViewHolder extends RecyclerView.ViewHolder {
        public TextView judul,Penjelasan;

        public NotifikasiViewHolder(@NonNull View itemView) {
            super(itemView);
            judul = itemView.findViewById(R.id.judulnotif);
            Penjelasan = itemView.findViewById(R.id.notifdescription);
        }

    }
}
