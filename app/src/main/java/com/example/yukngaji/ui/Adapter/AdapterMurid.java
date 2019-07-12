package com.example.yukngaji.ui.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yukngaji.ChatActivity;
import com.example.yukngaji.ListChatMurid;
import com.example.yukngaji.ListMurid;
import com.example.yukngaji.R;
import com.example.yukngaji.ui.Item.itemmurid;

import java.util.ArrayList;
import java.util.List;

public class AdapterMurid extends RecyclerView.Adapter<AdapterMurid.ViewHolder> {
    private List<itemmurid> items = new ArrayList<>();

    private Context context;

    public AdapterMurid(Context context, List<itemmurid> items) {
        this.items = items;
        this.context = context;
    }
    @NonNull
    @Override
    public AdapterMurid.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_murid, viewGroup, false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMurid.ViewHolder viewHolder, final int i) {
        viewHolder.name.setText(items.get(i).getName());
        if ( this.context instanceof ListChatMurid) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, ChatActivity.class);
                    intent.putExtra("uid", items.get(i).getUid());
                    intent.putExtra("nama", items.get(i).getName());
                    context.startActivity(intent);
                }
            });
        }else if(this.context instanceof ListMurid){
            Intent intent=new Intent(context, ChatActivity.class);
            intent.putExtra("uid", items.get(i).getUid());
            intent.putExtra("nama", items.get(i).getName());
            context.startActivity(intent);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ViewHolder(@NonNull View v) {
            super(v);
            name =  v.findViewById(R.id.name);
        }
    }
}
