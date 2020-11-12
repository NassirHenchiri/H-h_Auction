package com.Exodia.H_and_N;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.net.Uri;
public class Ajoutvente extends AppCompatActivity {
    private ImageButton imageBn;
    private EditText editItemName;
    private EditText editDesc;
    private EditText editBaseBid;
    private EditText editStartTime;

    private EditText editEndTime;
    private Uri mImageUri;
    private Button submitBn;
    private static final int GALLERY_REQUEST = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajoutvente);
        imageBn = findViewById(R.id.image_bn);
        editItemName = findViewById(R.id.edit_item_name);
        editDesc = findViewById(R.id.edit_description);
        editBaseBid = findViewById(R.id.edit_base_bid);
        editStartTime = findViewById(R.id.edit_start_time);
        editEndTime = findViewById(R.id.edit_end_time);
        submitBn = findViewById(R.id.submit_bn);


        imageBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_REQUEST);
            }
        });

        submitBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                publishPost();
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {
            mImageUri = data.getData();
            imageBn.setImageURI(mImageUri);
        }
    }

    private void publishPost() {
        final String itemName = editItemName.getText().toString().trim();
        final String itemDesc = editDesc.getText().toString().trim();
        final String baseBid = editBaseBid.getText().toString().trim();
        final String startTime = editStartTime.getText().toString().trim();
        final String endTime = editEndTime.getText().toString().trim();
        if(!itemName.isEmpty() && !itemDesc.isEmpty() && !baseBid.isEmpty()
                && !startTime.isEmpty() && !endTime.isEmpty()&& mImageUri != null) {
            Toast.makeText(getApplicationContext(), "Published Successfully!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Error... Please Try Again.", Toast.LENGTH_SHORT).show();
        }

    }
}