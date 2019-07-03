package com.example.yukngaji;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.yukngaji.db.Bookmarkhelper;
import com.example.yukngaji.ui.Item.itemayat;
import com.example.yukngaji.ui.Item.itembokmark;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class List_ayat extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ListAdapter mListadapter;
    private Bookmarkhelper bookmarkhelper;
    String no,message ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_ayat);
        final Intent intent = getIntent();
        message= intent.getStringExtra("namasurat");
        no= intent.getStringExtra("nomorsurat");
        setTitle(message);
        bookmarkhelper=Bookmarkhelper.getInstance(getApplicationContext());
        mRecyclerView=findViewById(R.id.rv__ayat);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.GET, "https://al-quran-8d642.firebaseio.com/surat/"+no+".json?print=pretty", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ArrayList data = new ArrayList<itemayat>();
                try{
                    JSONArray jsonObject=new JSONArray(response);
                    for(int i=0;i<jsonObject.length();i++){
                        String ayat=jsonObject.getJSONObject(i).getString("ar");
                        String nomor=jsonObject.getJSONObject(i).getString("nomor");
                        String arti=jsonObject.getJSONObject(i).getString("id");

                        data.add(new itemayat(ayat,nomor,arti));
                    }
                    mListadapter = new ListAdapter(data,data);
                    mRecyclerView.setAdapter(mListadapter);
                    if (intent.hasExtra("nomorayat")) {
                        mRecyclerView.getLayoutManager().scrollToPosition(intent.getIntExtra("nomorayat",0));
                    } else {
                    }
                }catch (JSONException e){e.printStackTrace();}
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }
    @Override
    public void onBackPressed()
    {
        itembokmark itembokmarks=new itembokmark();
        itembokmarks.setIdsurat(no);
        itembokmarks.setNamasurat(message);
        itembokmarks.setArti(message);
        LinearLayoutManager layoutManager = ((LinearLayoutManager)mRecyclerView.getLayoutManager());
        int lastCompletelyVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
        int ItemPosition=lastCompletelyVisibleItemPosition+1;
        itembokmarks.setIdayat(String.valueOf(ItemPosition));
        if(ItemPosition!=0){
            showAlertDialog(no,itembokmarks);
        }
    }
    private void showAlertDialog(final String nomorayat, final itembokmark Itembokmarks) {
        String dialogTitle, dialogMessage;
            dialogTitle = "Simpan";
            dialogMessage = "Apakah anda ingin Menyimpan?";

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle(dialogTitle);
        alertDialogBuilder
                .setMessage(dialogMessage)
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                            bookmarkhelper.insertNote(Itembokmarks);
                            finish();
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }
    public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> implements Filterable
    {
        private ArrayList<itemayat> dataList;
        private ArrayList<itemayat> dataListfilter;

        public ListAdapter(ArrayList<itemayat> data,ArrayList<itemayat> datafilter)
        {
            this.dataList = data;
            this.dataListfilter=datafilter;
        }

        public class ViewHolder extends RecyclerView.ViewHolder
        {
            TextView ayat,nomor,arti;
            public ViewHolder(View itemView)
            {
                super(itemView);
                this.arti=itemView.findViewById(R.id.arti_ayat);
                this.nomor=itemView.findViewById(R.id.nomorayat);
                this.ayat = itemView.findViewById(R.id.Kataayat);
            }
        }

        @NonNull
        @Override
        public ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.itemayat, viewGroup, false);
            ViewHolder viewHolder=new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.ayat.setText(dataList.get(position).getAyat());
            holder.nomor.setText(dataList.get(position).getNomorayat());
            holder.arti.setText(dataList.get(position).getArti());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
        @Override
        public int getItemCount()
        {
            return dataList.size();
        }
        @Override
        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    String charString = charSequence.toString();
                    if (charString.isEmpty()) {
                        dataList = dataListfilter;
                    } else {
                        ArrayList<itemayat> filteredList = new ArrayList<>();
                        for (itemayat row : dataListfilter) {

                            // name match condition. this might differ depending on your requirement
                            // here we are looking for name or phone number match
                            if (row.getNomorayat().toLowerCase().contains(charString.toLowerCase())) {
                                filteredList.add(row);
                            }
                        }

                        dataList = filteredList;
                    }

                    FilterResults filterResults = new FilterResults();
                    filterResults.values = dataList;
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    dataList = (ArrayList<itemayat>) filterResults.values;
                    notifyDataSetChanged();
                }
            };
        }
    }
}
