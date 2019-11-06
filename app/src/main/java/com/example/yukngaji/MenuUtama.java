package com.example.yukngaji;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.balysv.materialripple.MaterialRippleLayout;
import com.example.yukngaji.setting.UserPreference;
import com.example.yukngaji.ui.Item.Image;
import com.example.yukngaji.ui.Item.ItemNotif;
import com.example.yukngaji.ui.Utils.Tools;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.ArrayList;
import java.util.List;


public class MenuUtama extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseAuth mAuth;
    TextView nama, status;
    UserPreference preference;
    LinearLayout Yukngaji,Raport,AlQuran,SuratPendek,JadwalSholat,store,haji,Chat;
    private ViewPager viewPager;
    private LinearLayout layout_dots;
    private AdapterImageSlider adapterImageSlider;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_utama);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mAuth = FirebaseAuth.getInstance();
        store=findViewById(R.id.Store);
        AlQuran=findViewById(R.id.Alquran);
        SuratPendek=findViewById(R.id.SuratPendek);
        haji=findViewById(R.id.Haji);
        Chat=findViewById(R.id.Chat);
        Yukngaji=findViewById(R.id.YukNgaji);
        Raport=findViewById(R.id.Rapot);
        JadwalSholat = findViewById(R.id.jadwaadzan);
        preference=new UserPreference(this);
        preference.setFirtsrun(false);
        FirebaseUser user = mAuth.getCurrentUser();
        user.getIdToken(false).addOnSuccessListener(new OnSuccessListener<GetTokenResult>() {
            @Override
            public void onSuccess(GetTokenResult result) {
                Object isGuru = result.getClaims().get("Guru");
                Object isdaftar = result.getClaims().get("uidguru");
                if(isGuru!=null){
                    preference.setGuru(true);
                }
                if (isdaftar != null) {
                    preference.setCekBayar(true);
                    preference.setTunggu(true);
                    preference.setUidGuru(isdaftar.toString());
                }
            }
        });
        initComponent();
        AdView adBanner;
        AdRequest adRequest;
        adBanner = findViewById(R.id.adView);

        adRequest = new AdRequest.Builder().addTestDevice("B698A7FF859996A519A36D901F34D23C").build();

        adBanner.loadAd(adRequest);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        nama=headerView.findViewById(R.id.namaprofil);
        status = headerView.findViewById(R.id.statusprofil);
        nama.setText(preference.getNama());
        if (preference.getGuru()) {
            status.setText("GURU");
        } else {
            status.setText("MURID");
        }
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
//                        if (!task.isSuccessful()) {
//                            return;
//                        }
//                        String token = task.getResult().getToken();
                    }
                });
        Chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(preference.getGuru()){
                    Intent intent=new Intent(MenuUtama.this,ListChatMurid.class);
                    startActivity(intent);
                }else {
                    if(preference.getCekBayar()){
                        Intent intent=new Intent(MenuUtama.this,ChatActivity.class);
                        intent.putExtra("uid",preference.getUidGuru() );
                        intent.putExtra("nama",preference.getNamaGuru());
                        startActivity(intent);
                    }else {
                        Toast.makeText(MenuUtama.this,"Maaf Anda Belum Mendaftar", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MenuUtama.this,StoreActivity.class);
                startActivity(intent);
            }
        });
        haji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MenuUtama.this,HajiActivity.class);
                startActivity(intent);
            }
        });
        AlQuran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MenuUtama.this,List_Surat.class);
                intent.putExtra("kondisi","true");
                startActivity(intent);
            }
        });
        SuratPendek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MenuUtama.this,List_Surat.class);
                intent.putExtra("kondisi","false");
                startActivity(intent);
            }
        });
        Yukngaji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserPreference preference=new UserPreference(MenuUtama.this);
                if (preference.getGuru()) {
                    Intent intent = new Intent(MenuUtama.this, ListMurid.class);
                    startActivity(intent);
                } else {
                    if (preference.getCekDaftar() & preference.getTunggu()) {
                        if (preference.getCekBayar()) {
                            Intent intent = new Intent(MenuUtama.this, ProfilGuru.class);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(MenuUtama.this, TungguKonfirmasi.class);
                            startActivity(intent);
                        }
                    } else if (preference.getCekDaftar()) {
                        Intent intent = new Intent(MenuUtama.this, Pembayaran.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(MenuUtama.this, Silabus.class);
                        startActivity(intent);
                    }
                }
            }
        });
        Raport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (preference.getGuru()) {
                    Intent intent = new Intent(MenuUtama.this, ListRaportMurid.class);
                    startActivity(intent);
                } else {
                    if (preference.getCekBayar()) {
                        Intent intent = new Intent(MenuUtama.this, RaportActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(MenuUtama.this, "Maaf Anda Belum Mendaftar", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        JadwalSholat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuUtama.this, JadwalSholat.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_utama, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_notifications) {
            Intent intent=new Intent(MenuUtama.this,NotifikasiActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profil) {
            Intent intent=new Intent(MenuUtama.this,ProfilActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_faq) {
            Intent intent=new Intent(MenuUtama.this,FAQActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_hubungikami) {
            Intent intent = new Intent(MenuUtama.this, HubungiKamiActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_tentang) {
            Intent intent=new Intent(MenuUtama.this,TentangKamiActivity.class);
            startActivity(intent);
        } else if (id==R.id.nav_logout){
            mAuth.signOut();
            preference.setDaftar(false);
            preference.setTunggu(false);
            preference.setCekBayar(false);
            preference.setGuru(false);
            Intent intent=new Intent(MenuUtama.this,LoginRegister.class);
            startActivity(intent);
            finish();
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void initComponent() {
        layout_dots =findViewById(R.id.layout_dots);
        viewPager =  findViewById(R.id.pager);
        DatabaseReference reference;
        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Promo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<Image> items = new ArrayList<>();
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    ItemNotif murid = noteDataSnapshot.getValue(ItemNotif.class);
                    Image obj = new Image();
                    obj.image = murid.getGambar();
                    items.add(obj);
                }
                adapterImageSlider = new AdapterImageSlider(MenuUtama.this, new ArrayList<Image>());
                adapterImageSlider.setItems(items);
                viewPager.setAdapter(adapterImageSlider);
                viewPager.setCurrentItem(0);
                addBottomDots(layout_dots, adapterImageSlider.getCount(), 0);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        // displaying selected image first
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int pos, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int pos) {
                addBottomDots(layout_dots, adapterImageSlider.getCount(), pos);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
    private void addBottomDots(LinearLayout layout_dots, int size, int current) {
        ImageView[] dots = new ImageView[size];

        layout_dots.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new ImageView(this);
            int width_height = 15;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(width_height, width_height));
            params.setMargins(10, 10, 10, 10);
            dots[i].setLayoutParams(params);
            dots[i].setImageResource(R.drawable.shape_circle);
            dots[i].setColorFilter(ContextCompat.getColor(this, R.color.overlay_dark_10), PorterDuff.Mode.SRC_ATOP);
            layout_dots.addView(dots[i]);
        }

        if (dots.length > 0) {
            dots[current].setColorFilter(ContextCompat.getColor(this, R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);
        }
    }
    private static class AdapterImageSlider extends PagerAdapter {

        private Activity act;
        private List<Image> items;

        private OnItemClickListener onItemClickListener;

        private interface OnItemClickListener {
            void onItemClick(View view, Image obj);
        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }

        // constructor
        private AdapterImageSlider(Activity activity, List<Image> items) {
            this.act = activity;
            this.items = items;
        }

        @Override
        public int getCount() {
            return this.items.size();
        }

        public Image getItem(int pos) {
            return items.get(pos);
        }

        public void setItems(List<Image> items) {
            this.items = items;
            notifyDataSetChanged();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ( object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            final Image o = items.get(position);
            LayoutInflater inflater = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(R.layout.item_slider_image, container, false);

            ImageView image =  v.findViewById(R.id.image);
            MaterialRippleLayout lyt_parent =  v.findViewById(R.id.lyt_parent);
            Tools.displayImageOriginal(act, image, o.image);
            lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(v, o);
                    }
                }
            });

            ( container).addView(v);

            return v;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ( container).removeView((RelativeLayout) object);

        }

    }
}
