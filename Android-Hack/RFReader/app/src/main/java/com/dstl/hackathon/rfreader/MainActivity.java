package com.dstl.hackathon.rfreader;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.text.Layout;
import android.util.Log;
import android.util.StringBuilderPrinter;
import android.view.MotionEvent;
import android.view.View;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;




public class MainActivity extends AppCompatActivity {


    static String gazLog = "gazlog";

    TextView rfidLabel;
    private NfcAdapter nfcAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(gazLog,"The activity is been created");

        setContentView(R.layout.activity_main);

        rfidLabel = (TextView)findViewById(R.id.welcome);
        rfidLabel.setText("Scan RFID");
        rfidLabel.setTextColor(Color.BLACK);
        rfidLabel.setTextSize(50);
        rfidLabel.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);



        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if(nfcAdapter == null){
            Log.i(gazLog,"The RFID adaptor is NILL");
            finish();
        }else if(!nfcAdapter.isEnabled()) {
            Log.i(gazLog,"The RFID is not enabled on this device");
            finish();
        }
    }



    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        String action = intent.getAction();

        Log.i(gazLog,"The activity has resumed");

        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)) {
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            if(tag == null){
                Log.i(gazLog,"The tag has no value");
                rfidLabel.setText("tag == null");
            }else{

                String tagInfo = "";

                byte[] tagId = tag.getId();

                for(int i=0; i<tagId.length; i++){
                    tagInfo += Integer.toHexString(tagId[i] & 0xFF);
                }

                Log.i(gazLog,tagInfo);

                Boolean isTracked = isUserTracked(tagInfo);

                //Gonna do a check here to see if the RFID chip is already tracked.
                if (isTracked) {
                    launchRFIDWithAKnownUser(tagInfo,"Paula","Baula",false);
                }else{
                    launchRFIDNotYetTracked(tagInfo);
                }


            }
        }else{
            Log.i(gazLog,"No RFID chip event sent");
        }




    }

    boolean isUserTracked(String id) {
        if(id.equals("45d41e53")){
            Log.i(gazLog,"User Tracked");
            return true;
        }else{
            Log.i(gazLog,"User NOT Tracked");
            return false;
        }
    }


    void launchRFIDNotYetTracked(String id) {

        Log.i(gazLog,"The RFID chip is reading but not yet tracked");
        Intent i = new Intent(getApplicationContext(),RFIdScreen.class);
        i.putExtra("RFID",id);
        startActivity(i);

    }

    void launchRFIDWithAKnownUser(String id,String name,String surname,Boolean gender) {
        Log.i(gazLog,"The RFID chip is reading and the user exists in the database");

        Intent i = new Intent(getApplicationContext(),TrackedUserActivity.class);
        i.putExtra("RFID",id);
        i.putExtra("name",name);
        i.putExtra("surname",surname);
        i.putExtra("gender",gender);

        startActivity(i);

    }



}
