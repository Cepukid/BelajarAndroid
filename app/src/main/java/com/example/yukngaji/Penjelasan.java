package com.example.yukngaji;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import com.bumptech.glide.Glide;
import com.example.yukngaji.ui.Item.ItemStore;
import com.example.yukngaji.ui.Utils.Tools;
import com.example.yukngaji.ui.Utils.ViewAnimation;

import java.util.Objects;

public class Penjelasan extends AppCompatActivity {
    ImageView fotobarang;
    TextView Harga, Deskripsi, Nama;
    Button pesan;
    private View parent_view;
    private ImageButton bt_toggle_description;
    private View lyt_expand_description;
    private NestedScrollView nested_scroll_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penjelasan);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        } else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        parent_view = findViewById(R.id.parent_view);
        initComponent();
        fotobarang = findViewById(R.id.imagestore);
        Deskripsi = findViewById(R.id.penjelasanstore);
        Nama=findViewById(R.id.namabarang);
        Harga = findViewById(R.id.hargastore);
        pesan = findViewById(R.id.pesanstore);
        pesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWhatsApp("Saya Mau Pesan " + Nama.getText().toString());
            }
        });
        Intent i = getIntent();
        ItemStore itemStore = (ItemStore)i.getSerializableExtra("data");
        Nama.setText(itemStore.getNama());
        Harga.setText("Rp. " + itemStore.getHarga());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Deskripsi.setText(Html.fromHtml(itemStore.getDeskripsi(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            Deskripsi.setText(Html.fromHtml(itemStore.getDeskripsi()));
        }
        Glide.with(this)
                .load(itemStore.getGambar())
                .centerCrop()
                .placeholder(R.drawable.ic_lock_black_24dp)
                .into(fotobarang);
    }

    private void initComponent() {
        // nested scrollview
        nested_scroll_view = findViewById(R.id.nested_scroll_view);

        // section description
        bt_toggle_description = findViewById(R.id.bt_toggle_description);
        lyt_expand_description = findViewById(R.id.lyt_expand_description);
        bt_toggle_description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleSection(view, lyt_expand_description);
            }
        });
        // expand first description
        toggleArrow(bt_toggle_description);
        lyt_expand_description.setVisibility(View.VISIBLE);
    }

    private void toggleSection(View bt, final View lyt) {
        boolean show = toggleArrow(bt);
        if (show) {
            ViewAnimation.expand(lyt, new ViewAnimation.AnimListener() {
                @Override
                public void onFinish() {
                    Tools.nestedScrollTo(nested_scroll_view, lyt);
                }
            });
        } else {
            ViewAnimation.collapse(lyt);
        }
    }

    public boolean toggleArrow(View view) {
        if (view.getRotation() == 0) {
            view.animate().setDuration(200).rotation(180);
            return true;
        } else {
            view.animate().setDuration(200).rotation(0);
            return false;
        }
    }

    private void openWhatsApp(String pesan) {
        String smsNumber = "6289639308568"; // E164 format without '+' sign
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");
        sendIntent.putExtra(Intent.EXTRA_TEXT, pesan);
        sendIntent.putExtra("jid", smsNumber + "@s.whatsapp.net"); //phone number without "+" prefix
        sendIntent.setPackage("com.whatsapp");
        startActivity(sendIntent);
    }
}
