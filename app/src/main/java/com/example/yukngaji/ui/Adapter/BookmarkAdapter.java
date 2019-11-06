package com.example.yukngaji.ui.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yukngaji.List_ayat;
import com.example.yukngaji.R;
import com.example.yukngaji.db.Bookmarkhelper;
import com.example.yukngaji.ui.Item.itembokmark;

import java.util.ArrayList;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.BookmarkViewHolder>{
    private ArrayList<itembokmark> listbokmark = new ArrayList<>();
    private Activity activity;
    public BookmarkAdapter(Activity activity) {
        this.activity = activity;
    }
    public ArrayList<itembokmark> getListBookmark() {
        return listbokmark;
    }
    public void setlistbokmark(ArrayList<itembokmark> listbokmark) {
        if (listbokmark.size() > 0) {
            this.listbokmark.clear();
        }
        this.listbokmark.addAll(listbokmark);
        notifyDataSetChanged();
    }
    public void addItem(itembokmark itembokmarks) {
        this.listbokmark.add(itembokmarks);
        notifyItemInserted(listbokmark.size() - 1);
    }
    public void updateItem(int position, itembokmark itembokmarks) {
        this.listbokmark.set(position, itembokmarks);
        notifyItemChanged(position, itembokmarks);
    }
    public void removeItem(int position) {
        this.listbokmark.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,listbokmark.size());
    }
    @NonNull
    @Override
    public BookmarkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bookmark, parent, false);
        return new BookmarkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final BookmarkViewHolder holder, final int position) {
        holder.nama.setText(listbokmark.get(position).getNamasurat());
        holder.nomorayat.setText(listbokmark.get(position).getIdayat());
        holder.nomorsurat.setText(listbokmark.get(position).getIdsurat());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog(listbokmark.get(position).getIdayat(),position);


            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(activity.getApplicationContext(), List_ayat.class);
                intent.putExtra("namasurat",listbokmark.get(position).getNamasurat());
                intent.putExtra("nomorsurat",listbokmark.get(position).getIdsurat());
                intent.putExtra("nomorayat",Integer.parseInt(listbokmark.get(position).getIdayat()));
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listbokmark.size();
    }
    private void showAlertDialog(final String nomorayat, final int position) {
        String dialogTitle, dialogMessage;
        dialogTitle = "Hapus";
        dialogMessage = "Apakah anda ingin Menghapus?";
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        alertDialogBuilder.setTitle(dialogTitle);
        alertDialogBuilder
                .setMessage(dialogMessage)
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        final Bookmarkhelper bookmarkhelper=Bookmarkhelper.getInstance(activity.getApplicationContext());
                        bookmarkhelper.open();
                        bookmarkhelper.deleteNote(listbokmark.get(position).getIdsurat());
                        removeItem(position);
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public class BookmarkViewHolder extends RecyclerView.ViewHolder {
        TextView nama,nomorsurat,nomorayat;
        ImageView delete;
        public BookmarkViewHolder(@NonNull View itemView) {
            super(itemView);
            this.nomorsurat=itemView.findViewById(R.id.nomorsurat);
            this.nomorayat=itemView.findViewById(R.id.noayat);
            this.nama = itemView.findViewById(R.id.namaayat);
            this.delete= itemView.findViewById(R.id.deletebookmark);
        }
    }

}
