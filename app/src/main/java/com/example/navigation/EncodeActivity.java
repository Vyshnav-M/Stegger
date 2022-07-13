package com.example.navigation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


public class EncodeActivity extends AppCompatActivity {

    Intent intent;
    Button btnEn,btnPick,btnCam;
    EditText secMsg,pass;
    ImageView img;
    SwitchCompat switchPass;
    Uri imageUri;
    boolean isCheck;
    ContentValues values;
    public static ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encode);

        btnEn =  findViewById(R.id.btnEncode);
        btnCam = findViewById(R.id.btnCam);
        btnPick = findViewById(R.id.btnPickImg);
        img = findViewById(R.id.img);
        secMsg = findViewById(R.id.edtTxtSec);
        switchPass = findViewById(R.id.switch1);
        pass = findViewById(R.id.EdtPass);

        switchPass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    pass.setVisibility(View.VISIBLE);
                    isCheck = true;
                } else {
                    pass.setVisibility(View.INVISIBLE);
                    isCheck = false;
                }
            }
        });

        btnCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, "New Picture");
                values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
                imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, 0);
            }
        });

        btnPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
            }
        });

        btnEn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String secretMsg = secMsg.getText().toString();
                boolean enc = true;
                if(img.getTag().toString().equals("default")) {
                    Toast.makeText(EncodeActivity.this, "Please select an image", Toast.LENGTH_SHORT).show();
                    enc = false;
                }
                else if(secretMsg.equals("")) {
                    enc = false;
                    secMsg.setError("Enter a valid message");
                }
                else if(isCheck) {
                    String passWord = pass.getText().toString();
                    if(TextUtils.isEmpty(passWord) || pass.length() != 8)
                    {
                        pass.setError("You must have 8 characters in your password");
                        enc = false;
                    }
                    else{
                        G.encodePassword = passWord;
                    }
                }
                if(enc) {
                    progressDialog = new ProgressDialog(EncodeActivity.this);
                    progressDialog.setMessage("Please wait"); // Setting Message
                    progressDialog.setTitle("Encoding"); // Setting Title
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                    progressDialog.show(); // Display Progress Dialog
                    progressDialog.setCancelable(false);
                    G.secMsg = secretMsg;
                    G.encode();
                    encodeResultActivity();
                }
            }
        });
    }

    public void encodeResultActivity(){
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                intent = new Intent(EncodeActivity.this,EncodeResultActivity.class);
                startActivity(intent);
            }
        },2500);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        if(resultCode == RESULT_OK && requestCode == 1) {
            Uri selectedImage = imageReturnedIntent.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                bitmap = Bitmap.createScaledBitmap(bitmap, 450, 600, true);
                img.setImageBitmap(bitmap);
                G.bmap = bitmap;
                img.setTag("updated");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(resultCode == RESULT_OK && requestCode == 0) {
            try {
                Bitmap thumbnail = MediaStore.Images.Media.getBitmap(
                        getContentResolver(), imageUri);
                thumbnail = Bitmap.createScaledBitmap(thumbnail, 450, 600, true);
                img.setImageBitmap(thumbnail);
                G.bmap = thumbnail;
                img.setTag("updated");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        G.imageDrawable = img.getDrawable();
        img.setImageDrawable(G.imageDrawable);
    }
}