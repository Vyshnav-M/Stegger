package com.example.navigation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DecodeResultActivity extends AppCompatActivity {

    public static boolean passwordFound;
    TextView secretMsg;
    Button menu;

    Button btnGet;
    EditText pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decode_result);

        if (DecodeActivity.progressDialog.isShowing()) {
            DecodeActivity.progressDialog.dismiss();
        }

        secretMsg = findViewById(R.id.textView3);
        menu = findViewById(R.id.btnMenu);

        btnGet = findViewById(R.id.btnGetMsg);
        pass = findViewById(R.id.PassDecode);

        if(passwordFound)
        {
            pass.setVisibility(View.VISIBLE);
            btnGet.setVisibility(View.VISIBLE);
        }
        else
        {
            secretMsg.setText(G.msgDecoded);
        }

        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pass.getText().toString().equals(G.decodePassword)){
                    secretMsg.setText(G.msgDecoded);
                }
                else{
                    pass.setError("Password doesn't match");
                }
            }
        });

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