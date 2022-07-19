package com.example.navigation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Button btnEnc = findViewById(R.id.btnEnc);
        Button btnDec = findViewById(R.id.btnDec);
        btnEnc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEncodeActivity();
            }
        });
        btnDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDecodeActivity();
            }
        });
    }
    public void openEncodeActivity(){
        intent = new Intent(this,EncodeActivity.class);
        startActivity(intent);
    }
    public void openDecodeActivity() {
        intent = new Intent(this, DecodeActivity.class);
        startActivity(intent);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.stegger_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.howtouse:
                openGuideActivity();
                break;
            case R.id.recent:
                openRecentActivity();
                break;
            case R.id.about:
                openAboutActivity();
        }
        return super.onOptionsItemSelected(item);
    }
    public void openGuideActivity() {
        intent = new Intent(this, GuideActivity.class);
        startActivity(intent);
    }
    public void openRecentActivity() {
        intent = new Intent(this, RecentProjectsActivity.class);
        startActivity(intent);
    }
    public void openAboutActivity() {
        intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }
}