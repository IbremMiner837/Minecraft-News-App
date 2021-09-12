package com.mcbedrock.minecraftnews;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.android.play.core.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mcbedrock.minecraftnews.API.SetLocaleAPI;
import com.mcbedrock.minecraftnews.adapter.horizontalRecyclerViewAdapter;
import com.mcbedrock.minecraftnews.adapter.verticalRecyclerViewAdapter;
import com.mcbedrock.minecraftnews.fragments.CategoriesFragment;
import com.mcbedrock.minecraftnews.fragments.betaRecyclerViewFragment;
import com.mcbedrock.minecraftnews.fragments.javaRecyclerViewFragment;
import com.mcbedrock.minecraftnews.fragments.realeseRecyclerViewFragment;
import com.mcbedrock.minecraftnews.fragments.snapshotRecyclerViewFragment;
import com.mcbedrock.minecraftnews.jsonParser.minecraftnet_news;
import com.mcbedrock.minecraftnews.models.categoryModel;
import com.mcbedrock.minecraftnews.models.categoryTwoModel;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {

    private static final int RC_APP_UPDATE = 100;

    //Play Core Update
    private AppUpdateManager mAppUpdateManager;

    //Play Core Review
    private ReviewManager mReviewManager;
    private ReviewInfo reviewInfo;
    private Context mContext;

    private Toolbar toolbar;

    //Play Core Update
    private InstallStateUpdatedListener installStateUpdatedListener = new InstallStateUpdatedListener() {
        @Override
        public void onStateUpdate(@NonNull @NotNull InstallState state) {
            if (state.installStatus() == InstallStatus.DOWNLOADED) {
                showCompletedUpdate();
            }
        }
    };

    //Play Core Update
    private void showCompletedUpdate() {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "New App is Ready!",
                Snackbar.LENGTH_SHORT);
        snackbar.setAction("Install", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAppUpdateManager.completeUpdate();
            }
        });
        snackbar.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!isConnected(this)) {
            Intent intent = new Intent(MainActivity.this, NoConnectionActivity.class);
            startActivity(intent);
            finish();
        }
        mContext = getApplicationContext();

        getSupportFragmentManager().beginTransaction().replace(R.id.wrapper, new CategoriesFragment()).commit();

        final SetLocaleAPI setLocaleAPI = new SetLocaleAPI();
        setLocaleAPI.setLocale(this, "ru");

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //Play Core Review
        activateReviewInfo();

        //Play Core Update
        mAppUpdateManager = AppUpdateManagerFactory.create(this);
        mAppUpdateManager.getAppUpdateInfo().addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo result) {
                if (result.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                        && result.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                    try {
                        mAppUpdateManager.startUpdateFlowForResult(result, AppUpdateType.IMMEDIATE, MainActivity.this, RC_APP_UPDATE);
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        //mAppUpdateManager.registerListener(installStateUpdatedListener);

        Thread thread = new Thread() {
            public void run() {
                try {
                    sleep(1000 * 60 * 15);
                    startReviewFlow();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            //case R.id.share_app:
            //code
            //  break;
            case R.id.about_app: {
                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        toolbar.setTitle(R.string.app_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
            return;
        }
    }

    //Play Core Update
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_APP_UPDATE && resultCode != RESULT_OK) {
            Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //Play Core Review
    void activateReviewInfo() {
        mReviewManager = ReviewManagerFactory.create(this);
        Task<ReviewInfo> managerInfoTask = mReviewManager.requestReviewFlow();
        managerInfoTask.addOnCompleteListener((task) -> {
            if (task.isSuccessful()) {
                reviewInfo = task.getResult();
            } else {
                Toast.makeText(this, "Review failed to start", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Play Core Review
    void startReviewFlow() {
        if (reviewInfo != null) {
            Task<Void> flow = mReviewManager.launchReviewFlow(this, reviewInfo);
            flow.addOnCompleteListener(task ->
            {
                Toast.makeText(this, "Rating is Completed", Toast.LENGTH_SHORT);
            });
        }
    }

    private boolean isConnected(Activity activity) {
        ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(mContext.CONNECTIVITY_SERVICE);

        NetworkInfo wifiConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        return (wifiConn != null && wifiConn.isConnected()) || (mobileConn != null && mobileConn.isConnected());
    }

    @Override
    protected void onStop() {
        //Play Core Update
        //if(mAppUpdateManager != null) {
        //    mAppUpdateManager.unregisterListener(installStateUpdatedListener);
        //}

        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        final SetLocaleAPI setLocaleAPI = new SetLocaleAPI();
        setLocaleAPI.setLocale(this, "ru");
    }

    @Override
    protected void onResume() {
        super.onResume();

        final SetLocaleAPI setLocaleAPI = new SetLocaleAPI();
        setLocaleAPI.setLocale(this, "ru");

        //Play Core Update
        mAppUpdateManager.getAppUpdateInfo().addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo result) {
                if (result.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                    try {
                        mAppUpdateManager.startUpdateFlowForResult(result, AppUpdateType.IMMEDIATE, MainActivity.this, RC_APP_UPDATE);
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}