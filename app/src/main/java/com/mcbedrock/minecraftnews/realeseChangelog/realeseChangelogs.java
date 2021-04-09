package com.mcbedrock.minecraftnews.realeseChangelog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.mcbedrock.minecraftnews.R;
import com.mcbedrock.minecraftnews.SettingsActivity;
import com.mcbedrock.minecraftnews.ViewPagerAdapter;
import com.mcbedrock.minecraftnews.betaChangelog.betaRecyclerViewFragment;
import com.mcbedrock.minecraftnews.snapshotChangelog.snapshotRecyclerViewFragment;
import com.mcbedrock.minecraftnews.splashScreen;

import java.util.HashMap;

public class realeseChangelogs extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private FirebaseRemoteConfig mFirebaseRemoteConfig;

    //AdLoader adLoader;

    //private DatabaseReference mDatabase;


    public realeseChangelogs() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realese_changelogs);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //mDatabase = FirebaseDatabase.getInstance().getReference("app_update_link");

        //MobileAds.initialize(this, getResources().getString(R.string.admob_app_id));

        //TabLayout
        tabLayout = (TabLayout) findViewById(R.id.tabLayout_id);
        viewPager = (ViewPager) findViewById(R.id.viewPager_id);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.AddFragment(new realeseRecyclerViewFragment(), getString(R.string.release_changelog));
        adapter.AddFragment(new betaRecyclerViewFragment(), getString(R.string.beta_changelog));
        adapter.AddFragment(new snapshotRecyclerViewFragment(), getString(R.string.snapshot_changelog));

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

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
            Intent intent=new Intent(realeseChangelogs.this, SettingsActivity.class);
            startActivity(intent);
            finish();
        }

        if(id == R.id.action_about) {
            Toast toast = Toast.makeText(this,R.string.function_not_available, Toast.LENGTH_LONG);
            toast.show();
        }

        /*if(id == R.id.action_sort){
            //display alert dialog to choose sorting
            //showSortDialog();
            return true;
        }*/
        return super.onOptionsItemSelected(item);
    }
}