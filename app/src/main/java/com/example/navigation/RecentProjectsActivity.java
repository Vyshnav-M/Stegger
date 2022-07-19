package com.example.navigation;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

public class RecentProjectsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<Image> arrayList = new ArrayList<>();
    private final ActivityResultLauncher<String> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(),
            result -> {
                if (result) {
                    getImages();
                }
            });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_projects);
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(RecentProjectsActivity.this));
        recyclerView.setHasFixedSize(true);

        getImages();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getImages();
    }

    private void getImages(){
        arrayList.clear();
        File[] files = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).listFiles();
        if (files != null) {
            for (File file1 : files) {
                if (file1.getName().contains("stegger")) {
                    Date lastModDate = new Date(file1.lastModified());
                    String date =  lastModDate.toString().substring(0,20);
                    arrayList.add(new Image(file1.getName(), file1.getPath(), date));
                }
            }
        }
        ImageAdapter adapter = new ImageAdapter(RecentProjectsActivity.this, arrayList);
        recyclerView.setAdapter(adapter);
    }
}