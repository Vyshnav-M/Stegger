package com.example.navigation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class EncodeResultActivity extends AppCompatActivity {

    Button btnSave;
    Button btnShr;
    ImageView img2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encode_result);

        if (EncodeActivity.progressDialog.isShowing()) {
            EncodeActivity.progressDialog.dismiss();
        }

        btnSave = findViewById(R.id.btnSave);
        btnShr = findViewById(R.id.sharebtn);
        img2 = findViewById(R.id.img2);

        img2.setImageBitmap(G.enbmap);
        Bitmap imgBitmap = G.enbmap;

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OutputStream fOut;
                long currentTime = System.currentTimeMillis();
                File file = new File(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DOWNLOADS), "stegger-"+currentTime+".png"); // the File to save ,
                try {
                    fOut = new FileOutputStream(file);
                    imgBitmap.compress(Bitmap.CompressFormat.PNG,100, fOut); // saving the Bitmap to a file,,
                    fOut.flush(); // Not really required
                    fOut.close(); // do not forget to close the stream
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(EncodeResultActivity.this, "Image saved to Downloads", Toast.LENGTH_SHORT).show();
            }
        });

        btnShr.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("image/jpeg");

                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, "title");
                values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
                Uri uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);


                OutputStream outstream;
                try {
                    outstream = getContentResolver().openOutputStream(uri);
                    G.enbmap.compress(Bitmap.CompressFormat.PNG, 100, outstream);
                    outstream.close();
                } catch (Exception e) { }

                share.putExtra(Intent.EXTRA_STREAM, uri);
                startActivity(Intent.createChooser(share, "Share Image"));
            }
        });
    }
}