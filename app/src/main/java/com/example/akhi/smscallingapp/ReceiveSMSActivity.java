package com.example.akhi.smscallingapp;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ReceiveSMSActivity extends Activity implements AdapterView.OnItemClickListener {
private static ReceiveSMSActivity inst;
ArrayList<String> smsMessagelist=new ArrayList<String>();
ListView smsListview;
ArrayAdapter arrayAdapter;
public static ReceiveSMSActivity instance(){
    return inst;
}

    @Override
    protected void onStart() {
        super.onStart();
        inst=this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_sms);
        smsListview=(ListView)findViewById(R.id.SMSList);
        arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,smsMessagelist);
        smsListview.setAdapter(arrayAdapter);
        smsListview.setOnItemClickListener(this);
        refreshSmsInbox();

    }
   public void refreshSmsInbox(){
       ContentResolver contentResolver=getContentResolver();

       Cursor smsInboxCursor=contentResolver.query(Uri.parse("content://sms/inbox"),null,null,null,null);
       int indexBody=smsInboxCursor.getColumnIndex("body");
       int indexAddress=smsInboxCursor.getColumnIndex("address");
       int timeMillis=smsInboxCursor.getColumnIndex("date");
       Date date=new Date(timeMillis);
       SimpleDateFormat format=new SimpleDateFormat("dd/mm/yy");
       String dateText=format.format(date);
       if (indexBody<0|| !smsInboxCursor.moveToFirst())return;
       arrayAdapter.clear();
       do {
           String str=smsInboxCursor.getString(indexAddress)+"\n"+smsInboxCursor.getString(indexBody)+dateText+"at"+"\n";
           arrayAdapter.add(str);
       }while (smsInboxCursor.moveToNext());

   }
   public void updateList(final String smsMessage){
    arrayAdapter.insert(smsMessage,0);
    arrayAdapter.notifyDataSetChanged();
   }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    try {
        String[] smsMessages=smsMessagelist.get(position).split("\n");
        String address=smsMessages[0];
        String smsMessage="";
        for (int i=1;i<smsMessages.length;i++){
            smsMessage +=smsMessages[i];
        }
        String smsMessageStr = address+"\n";
        smsMessageStr+=smsMessage;
        Toast.makeText(this,smsMessageStr, Toast.LENGTH_SHORT).show();
    } catch ( Exception e) {
         e.printStackTrace();
        }

    }
    public void goToCompose(View view) {
        Intent intent=new Intent(ReceiveSMSActivity.this,SendSMSActivity.class);
        startActivity(intent);
    }
}
