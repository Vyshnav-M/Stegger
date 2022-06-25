package com.example.navigation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DecodeResultActivity extends AppCompatActivity {
    TextView secretMsg;
    Button menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decode_result);

        if (DecodeActivity.progressDialog.isShowing()) {
            DecodeActivity.progressDialog.dismiss();
        }

        secretMsg = findViewById(R.id.textView3);
        secretMsg.setText(G.msgDecoded);
        menu = findViewById(R.id.btnMenu);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMenu();
            }
        });
    }
    public void openMenu()
    {
        Intent intent = new Intent(this,MenuActivity.class);
        startActivity(intent);
    }
}