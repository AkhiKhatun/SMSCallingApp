package com.example.akhi.smscallingapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.session.MediaSession;
import android.net.Uri;
import android.os.Build;
import android.se.omapi.Session;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.reflect.Parameter;

public class MainActivity extends AppCompatActivity {

    Button b;

    EditText txt_phoneNumber;
    Button btn_call;
 @Override
    protected void onCreate(Bundle savedInstanceState) {

           super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b = (Button) findViewById(R.id.call);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent call = new Intent();
                    call.setAction(Intent.ACTION_DIAL);
                    call.setData(Uri.parse("tel:01950414012"));
                    startActivity(call);


                }

                ;
            });
            txt_phoneNumber = (EditText) findViewById(R.id.txt_number);
            btn_call = (Button) findViewById(R.id.btn_call);
            btn_call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentCall = new Intent(Intent.ACTION_CALL);
                    String number = txt_phoneNumber.getText().toString();
                    if (number.trim().isEmpty()) {
                        Toast.makeText(MainActivity.this, "Please Enter Your Number", Toast.LENGTH_SHORT).show();
                    } else {
                        intentCall.setData(Uri.parse("tel:" + number));

                    }
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(MainActivity.this, "Please Grant Permission", Toast.LENGTH_SHORT).show();
                        requestionPermission();

                    } else {
                        startActivity(intentCall);

                    }

                }
            });
        }

        private void requestionPermission () {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
        }


        public void goToInbox (View v){
            Intent intent = new Intent(MainActivity.this, ReceiveSMSActivity.class);
            startActivity(intent);
        }

        public void goToCompose (View v){
            Intent intent = new Intent(MainActivity.this, SendSMSActivity.class);
            startActivity(intent);
        }
    }

