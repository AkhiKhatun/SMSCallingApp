package com.example.akhi.smscallingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SendSMSActivity extends AppCompatActivity {
    Button sendSmsBtn;
    EditText toPhoneNumber;
    EditText smsMessageET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_sms);
        sendSmsBtn=(Button)findViewById(R.id.btnSendSMS);
        toPhoneNumber=(EditText)findViewById(R.id.editTextSMS);
        smsMessageET=(EditText)findViewById(R.id.editTextSMS);
        sendSmsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    private void sendSms(){
        String toPhone=toPhoneNumber.getText().toString();
        String smsMessage=smsMessageET.getText().toString();
        try {
            SmsManager smsManager=SmsManager.getDefault();
            smsManager.sendTextMessage(toPhone,null,smsMessage,null,null);
            Toast.makeText(this,"SMS Sent", Toast.LENGTH_SHORT).show();

        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void goToInbox(View view) {
        Intent intent=new Intent(SendSMSActivity.this,ReceiveSMSActivity.class);
        startActivity(intent);
    }

}
