package com.example.yukngaji;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.yukngaji.ui.Utils.AlarmReceiver;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Objects;

public class JadwalSholat extends AppCompatActivity {
    TextView fajr, dhuhr, asr, maghrib, isha, tanggal;
    SwitchCompat switchfajr, switchdhuhr, switchasr, switchmaghrib, switchisha;
    ProgressDialog dialog;
    AlarmReceiver alarmReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal_sholat);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        } else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setTitle("Jadwal Sholat");
        alarmReceiver = new AlarmReceiver();
        dialog = ProgressDialog.show(this, "", "", true);
        fajr = findViewById(R.id.subuh);
        dhuhr = findViewById(R.id.dzuhur);
        asr = findViewById(R.id.ashar);
        maghrib = findViewById(R.id.maghrib);
        isha = findViewById(R.id.isya);
        tanggal = findViewById(R.id.tanggalsholat);
        switchfajr = findViewById(R.id.switchshubuh);
        switchdhuhr = findViewById(R.id.switchdzuhur);
        switchasr = findViewById(R.id.switchashar);
        switchmaghrib = findViewById(R.id.switchmagrib);
        switchisha = findViewById(R.id.switchisya);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://muslimsalat.com/surabaya/daily.json", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    tanggal.setText(jsonObject.getJSONArray("items").getJSONObject(0).getString("date_for"));
                    fajr.setText(converjam(jsonObject.getJSONArray("items").getJSONObject(0).getString("fajr")));
                    dhuhr.setText(converjam(jsonObject.getJSONArray("items").getJSONObject(0).getString("dhuhr")));
                    asr.setText(converjam(jsonObject.getJSONArray("items").getJSONObject(0).getString("asr")));
                    maghrib.setText(converjam(jsonObject.getJSONArray("items").getJSONObject(0).getString("maghrib")));
                    isha.setText(converjam(jsonObject.getJSONArray("items").getJSONObject(0).getString("isha")));
                    dialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
        switchfajr.setChecked(alarmReceiver.isAlarmSet(JadwalSholat.this, AlarmReceiver.ID_SHUBUH));
        switchdhuhr.setChecked(alarmReceiver.isAlarmSet(JadwalSholat.this, AlarmReceiver.ID_DHUHUR));
        switchasr.setChecked(alarmReceiver.isAlarmSet(JadwalSholat.this, AlarmReceiver.ID_ASHAR));
        switchmaghrib.setChecked(alarmReceiver.isAlarmSet(JadwalSholat.this, AlarmReceiver.ID_MAGRIB));
        switchisha.setChecked(alarmReceiver.isAlarmSet(JadwalSholat.this, AlarmReceiver.ID_ISYA));
        switchfajr.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    alarmReceiver.setSubuh(JadwalSholat.this, AlarmReceiver.ID_SHUBUH, fajr.getText().toString(), "Subuh");
                } else {
                    alarmReceiver.cancelAlarm(JadwalSholat.this, AlarmReceiver.ID_SHUBUH);
                }
            }
        });
        switchdhuhr.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    alarmReceiver.setSubuh(JadwalSholat.this, AlarmReceiver.ID_DHUHUR, dhuhr.getText().toString(), "Dhuhur");
                } else {
                    alarmReceiver.cancelAlarm(JadwalSholat.this, AlarmReceiver.ID_DHUHUR);
                }
            }
        });
        switchasr.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    alarmReceiver.setSubuh(JadwalSholat.this, AlarmReceiver.ID_ASHAR, asr.getText().toString(), "Ashar");
                } else {
                    alarmReceiver.cancelAlarm(JadwalSholat.this, AlarmReceiver.ID_ASHAR);
                }
            }
        });
        switchmaghrib.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    alarmReceiver.setSubuh(JadwalSholat.this, AlarmReceiver.ID_MAGRIB, maghrib.getText().toString(), "Maghrib");
                } else {
                    alarmReceiver.cancelAlarm(JadwalSholat.this, AlarmReceiver.ID_MAGRIB);
                }
            }
        });
        switchisha.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    alarmReceiver.setSubuh(JadwalSholat.this, AlarmReceiver.ID_ISYA, isha.getText().toString(), "Isya");
                } else {
                    alarmReceiver.cancelAlarm(JadwalSholat.this, AlarmReceiver.ID_ISYA);
                }
            }
        });
    }

    public String converjam(String time) {

        SimpleDateFormat date12Format = new SimpleDateFormat("hh:mm a");

        SimpleDateFormat date24Format = new SimpleDateFormat("HH:mm");
        String hasil = "";
        try {
            hasil = date24Format.format(date12Format.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return hasil;
    }
}
