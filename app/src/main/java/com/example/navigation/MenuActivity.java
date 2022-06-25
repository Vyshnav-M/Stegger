package com.example.navigation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
}