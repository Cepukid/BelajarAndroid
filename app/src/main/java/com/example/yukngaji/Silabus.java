package com.example.yukngaji;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.yukngaji.FragmentSilabus.Kelas1;
import com.example.yukngaji.FragmentSilabus.Kelas2;
import com.example.yukngaji.FragmentSilabus.Kelas3;
import com.example.yukngaji.FragmentSilabus.KelasAlQuran;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class Silabus extends AppCompatActivity {
    private TabLayout tabLayout;
    public ViewPager viewPager;
    Button daftar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_silabus);
        viewPager = findViewById(R.id.viewsilabus);
        daftar=findViewById(R.id.daftarsekarang);
        setupViewPager(viewPager);
        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Silabus.this,YukNgajiMurid.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Kelas1(), "Kelas 1");
        adapter.addFragment(new Kelas2(), "Kelas 2");
        adapter.addFragment(new Kelas3(), "Kelas 3");
        adapter.addFragment(new KelasAlQuran(), "Al- Quran");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
