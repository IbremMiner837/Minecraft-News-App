package com.mcbedrock.minecraftnews.bedrockRealeseChangelog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.mcbedrock.minecraftnews.AboutActivity;
import com.mcbedrock.minecraftnews.minecraftBedrockDownload.MinecraftDownloadActivity;
import com.mcbedrock.minecraftnews.R;
import com.mcbedrock.minecraftnews.ViewPagerAdapter;
import com.mcbedrock.minecraftnews.desingSettingsActivity;

public class realeseChangelogs extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 pager2;
    ViewPagerAdapter viewPagerAdapter;

    private int theme = 0;

    public realeseChangelogs() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realese_changelogs);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //ActionBar actionBar = getSupportActionBar();
        //actionBar.setLogo(R.drawable.creeper_512);
        //actionBar.setDisplayUseLogoEnabled(true);
        //actionBar.setDisplayShowHomeEnabled(true);

        LoadPrefs();

        if (theme == 0) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        } else if (theme == 1) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else if (theme == 2) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);

        bottomNavigationView.setSelectedItemId(R.id.navigation_changelogs);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_minecraft_download:
                        startActivity(new Intent(getApplicationContext(), MinecraftDownloadActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.navigation_changelogs:
                        return true;

                    case R.id.navigation_about:
                        startActivity(new Intent(getApplicationContext(), AboutActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }

                return false;
            }
        });

        //TabLayout + ViewPager2 Start
        tabLayout = findViewById(R.id.tabLayout_id);
        pager2 = findViewById(R.id.viewPager_id);

        FragmentManager fm = getSupportFragmentManager();
        viewPagerAdapter = new ViewPagerAdapter(fm,getLifecycle());
        pager2.setAdapter(viewPagerAdapter);

        tabLayout.addTab(tabLayout.newTab().setText(R.string.bedrock_release_changelog));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.java_realese_changelog));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.beta_changelog));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.snapshot_changelog));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        pager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });
        //TabLayout + ViewPager2 End

        getSupportFragmentManager().beginTransaction().replace(R.id.wrapper, new realeseRecyclerViewFragment()).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        //handle other actionbar item clicks here

        if(id == R.id.action_check_update) {
            Toast toast = Toast.makeText(this,R.string.function_not_available, Toast.LENGTH_LONG);
            toast.show();        }

        if(id == R.id.action_settings) {
            Intent intent=new Intent(realeseChangelogs.this, desingSettingsActivity.class);
            startActivity(intent);
            finish();
        }

        if(id == R.id.action_about) {
            Intent intent=new Intent(realeseChangelogs.this, AboutActivity.class);
            startActivity(intent);
        }

        /*if(id == R.id.action_sort){
            //display alert dialog to choose sorting
            //showSortDialog();
            return true;
        }*/
        return super.onOptionsItemSelected(item);
    }

    private void LoadPrefs() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        theme = sharedPreferences.getInt("theme", 1);
    }
}