package com.example.navigation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class DecodeActivity extends AppCompatActivity {

    Intent intent;
    Button btnSelectSteg;
    Button btnDecodeSteg;
    ImageView stegImg;
    public static ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decode);

        btnSelectSteg = findViewById(R.id.btnSelectSteg);
        btnDecodeSteg = findViewById(R.id.btnDecodeSteg);
        stegImg = findViewById(R.id.stegImg);

        btnSelectSteg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
            }
        });

        btnDecodeSteg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(stegImg.getTag().toString().equals("default"))
                    Toast.makeText(DecodeActivity.this, "Please select an image", Toast.LENGTH_SHORT).show();
                else {
                    progressDialog = new ProgressDialog(DecodeActivity.this);
                    progressDialog.setMessage("Please wait"); // Setting Message
                    progressDialog.setTitle("Decoding"); // Setting Title
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                    progressDialog.show(); // Display Progress Dialog
                    progressDialog.setCancelable(false);
                    G.msgDecoded = "";
                    G.decode();
                    openDecodeResultActivity();
                }
            }
        });
    }

    public void openDecodeResultActivity(){
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                intent = new Intent(DecodeActivity.this,DecodeResultActivity.class);
                startActivity(intent);
            }
        },2500);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        if (resultCode == RESULT_OK && requestCode == 1) {
            Uri selectedImage = imageReturnedIntent.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                bitmap = Bitmap.createScaledBitmap(bitmap, 450, 600, true);
                stegImg.setImageBitmap(bitmap);
                G.debmap = bitmap;
                stegImg.setTag("updated");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}