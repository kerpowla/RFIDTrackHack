package com.dstl.hackathon.rfreader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.ImageView;



public class TrackedUserActivity extends AppCompatActivity {

    TextView rfid;
    TextView name;
    TextView surname;
    TextView gender;
    ImageView image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracked_user);

        rfid = (TextView)findViewById(R.id.rifd);
        name = (TextView)findViewById(R.id.name);
        surname = (TextView)findViewById(R.id.surname);
        gender = (TextView)findViewById(R.id.gender);
        image = (ImageView)findViewById(R.id.image);
    }

    @Override
    protected void onResume() {
        super.onResume();



        Bundle extras = getIntent().getExtras();
        if (extras != null){

            rfid.setText(extras.getString("RFID"));
            name.setText(extras.getString("name"));
            surname.setText(extras.getString("surname"));

            if(extras.getBoolean("gender")) {
                gender.setText("Male");
            }else{
                gender.setText("Female");
            }
        }

            image.setImageResource(R.drawable.paula);

    }

}
