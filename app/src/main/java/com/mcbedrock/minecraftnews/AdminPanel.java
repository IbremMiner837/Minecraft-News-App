package com.mcbedrock.minecraftnews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mcbedrock.minecraftnews.databinding.ActivityAdminPanelBinding;

import java.lang.reflect.Array;

public class AdminPanel extends AppCompatActivity {

    ActivityAdminPanelBinding binding;

    private String name;
    private EditText version_input_adminpan, changelog_img_link_adminpan, changelog_link_adminpan;
    private Button push_changelog_btn;
    private String PostID;

    private int changelogType = 0;

    FirebaseDatabase realeseChangelogFD, betaChangelogFD, snapshotChangelogFD;
    DatabaseReference realeseChangelogDR, betaChangelogDR, snapshotChangelogDR;

    changelogModel changelogModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminPanelBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.versionInputAdminpan.setKeyListener(DigitsKeyListener.getInstance("0123456789.WAabc"));

        AutoCompleteTextView set_changelog_type = (AutoCompleteTextView) findViewById(R.id.select_changelog);
        name = "";
        version_input_adminpan = findViewById(R.id.version_input_adminpan);
        changelog_img_link_adminpan = findViewById(R.id.changelog_img_link_adminpan);
        changelog_link_adminpan = findViewById(R.id.changelog_link_adminpan);

        realeseChangelogFD = FirebaseDatabase.getInstance();
        betaChangelogFD = FirebaseDatabase.getInstance();
        snapshotChangelogFD = FirebaseDatabase.getInstance();

        realeseChangelogDR = realeseChangelogFD.getReference("realese_changelogs");
        betaChangelogDR = betaChangelogFD.getReference("beta_changelogs");
        snapshotChangelogDR = betaChangelogFD.getReference("snapshot_changelogs");

        push_changelog_btn = findViewById(R.id.push_changelog_btn);
        changelogModel = new changelogModel();

        //publish changelog
        push_changelog_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String changelog_type = name;
                String img_link = changelog_img_link_adminpan.getText().toString();
                String changelog_link = changelog_link_adminpan.getText().toString();
                String version = version_input_adminpan.getText().toString();

                if (TextUtils.isEmpty(version) && TextUtils.isEmpty(img_link) && TextUtils.isEmpty(changelog_link)) {
                    Toast.makeText(AdminPanel.this, R.string.empty_fields, Toast.LENGTH_SHORT).show();
                } else {
                    if (changelogType == 0) {
                        addRealeseChangelog(img_link, changelog_link, changelog_type, version);
                    } else if (changelogType == 2) {
                        addBetaChangelog(img_link, changelog_link, changelog_type, version);
                    } else if (changelogType == 3) {
                        addSnapshotChangelog(img_link, changelog_link, changelog_type, version);
                    }
                }
            }
        });

        //AutoCompleteText
        String[] option = getResources().getStringArray(R.array.changelog_type);

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.option_item, option);
        set_changelog_type.setText(arrayAdapter.getItem(0).toString(), false);
        set_changelog_type.setAdapter(arrayAdapter);

        //AutoCompleteText Item Click
        set_changelog_type.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: {
                        name = "Bedrock";
                        changelogType = 0;
                        //binding.versionInputAdminpan.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                        break;
                    }
                    case 1: {
                        name = "Java Edition";
                        changelogType = 1;
                        //binding.versionInputAdminpan.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                        break;
                    }
                    case 2: {
                        name = "Beta";
                        changelogType = 2;
                        //binding.versionInputAdminpan.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                        break;
                    }
                    case 3: {
                        name = "Snapshot";
                        changelogType = 3;
                        //version_input_adminpan.setInputType(InputType.TYPE_CLASS_TEXT);
                        break;
                    }
                }
            }
        });
    }

    //generate alpha-numeric-string
    /*private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "abcdefghijklmnorstuvwxyz" + "0123456789";
    public static String randomAlphaNumeric(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 10) {
            int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }*/

    //Add Realese Changelog to Firebase Database
    private void addRealeseChangelog(String img, String link, String name, String version) {
        changelogModel.setImg(img);
        changelogModel.setLink(link);
        changelogModel.setName(name);
        changelogModel.setVersion(version);
        realeseChangelogDR.child(version.replace(".", "-")).setValue(changelogModel);

        realeseChangelogDR.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Toast.makeText(AdminPanel.this, R.string.changelog_published, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AdminPanel.this, "Fail to add data " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Add Beta Changelog to Firebase Database
    private void addBetaChangelog(String img, String link, String name, String version) {
        changelogModel.setImg(img);
        changelogModel.setLink(link);
        changelogModel.setName(name);
        changelogModel.setVersion(version);
        betaChangelogDR.child("version").setValue(changelogModel);

        betaChangelogDR.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Toast.makeText(AdminPanel.this, R.string.changelog_published, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AdminPanel.this, "Fail to add data " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Add Snapshot Changelog to Firebase Database
    private void addSnapshotChangelog(String img, String link, String name, String version) {
        changelogModel.setImg(img);
        changelogModel.setLink(link);
        changelogModel.setName(name);
        changelogModel.setVersion(version);
        snapshotChangelogDR.child("version").setValue(changelogModel);

        snapshotChangelogDR.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Toast.makeText(AdminPanel.this, R.string.changelog_published, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AdminPanel.this, "Fail to add data " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}