package com.example.yukngaji;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
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
import com.example.yukngaji.ui.Item.itemsurat;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class List_Surat extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ListAdapter mListadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list__surat);
        setTitle("Daftar Surat");
        mRecyclerView=findViewById(R.id.rv__surat);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.GET, "https://al-quran-8d642.firebaseio.com/data.json?print=pretty", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ArrayList data = new ArrayList<itemsurat>();
                try{
                    JSONArray jsonObject=new JSONArray(response);
                    for(int i=0;i<jsonObject.length();i++){
                        String nama=jsonObject.getJSONObject(i).getString("nama");
                        String nomor=jsonObject.getJSONObject(i).getString("nomor");
                        String arti=jsonObject.getJSONObject(i).getString("arti");
                        String type=jsonObject.getJSONObject(i).getString("type");
                        if(getIntent().getStringExtra("kondisi").equals("true")){
                        data.add(new itemsurat(nama,nomor,arti,type));}
                        else {
                            if(i>76){
                                data.add(new itemsurat(nama,nomor,arti,type));
                            }
                        }
                    }
                    mListadapter = new ListAdapter(data,data);
                    mRecyclerView.setAdapter(mListadapter);
//                    mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//                        @Override
//                        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                            super.onScrolled(recyclerView, dx, dy);
//                            int lastCompletelyVisibleItemPosition = 0;
//                            lastCompletelyVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
//                            Toast.makeText(recyclerView.getContext(), "2"+lastCompletelyVisibleItemPosition, Toast.LENGTH_SHORT).show();
//                        }
//                    });
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
    public boolean onCreateOptionsMenu( Menu menu) {
        getMenuInflater().inflate( R.menu.cari_ayat, menu);
        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mListadapter.getFilter().filter(query);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String query) {
                //FILTER AS YOU TYPE
                mListadapter.getFilter().filter(query);
                return true;
            }
        });
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.bookmark) {
            Intent intent=new Intent(List_Surat.this, BookmarkActivity.class);
            startActivity(intent);
        }
        return true;
    }
    public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> implements Filterable
    {
        private ArrayList<itemsurat> dataList;
        private ArrayList<itemsurat> dataListfilter;

        public ListAdapter(ArrayList<itemsurat> data,ArrayList<itemsurat> datafilter)
        {
            this.dataList = data;
            this.dataListfilter=datafilter;
        }

        public class ViewHolder extends RecyclerView.ViewHolder
        {
            TextView nama,nomor,tempatturun,arti;
            public ViewHolder(View itemView)
            {
                super(itemView);
                this.arti=itemView.findViewById(R.id.artisurat);
                this.nomor=itemView.findViewById(R.id.nomorsurat);
                this.tempatturun=itemView.findViewById(R.id.turunayat);
                this.nama = itemView.findViewById(R.id.namaayat);
            }
        }
        @Override
        public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_surat, parent, false);

            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ListAdapter.ViewHolder holder, final int position)
        {
            holder.nama.setText(dataList.get(position).getNama());
            holder.nomor.setText(dataList.get(position).getNomor());
            holder.arti.setText(dataList.get(position).getArti());
            holder.tempatturun.setText(dataList.get(position).getTempatTurun());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(List_Surat.this,List_ayat.class);
                    intent.putExtra("namasurat",dataList.get(position).getNama());
                    intent.putExtra("nomorsurat",dataList.get(position).getNomor());
                    startActivity(intent);
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
                        ArrayList<itemsurat> filteredList = new ArrayList<>();
                        for (itemsurat row : dataListfilter) {

                            // name match condition. this might differ depending on your requirement
                            // here we are looking for name or phone number match
                            if (row.getNama().toLowerCase().contains(charString.toLowerCase())) {
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
                    dataList = (ArrayList<itemsurat>) filterResults.values;
                    notifyDataSetChanged();
                }
            };
        }
    }


}
