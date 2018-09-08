package com.vacay.vacayandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;


//hackhands.com/fawzan

public class EventDetail extends AppCompatActivity {

    TextView eventNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cardview_event_detail);

        eventNameTextView = findViewById(R.id.event_name);

        String eventName = getIntent().getStringExtra("EVENT_NAME");
        Log.d("APP_DEBUG", "The name I got wis : " + eventName);
        eventNameTextView.setText(eventName);


    }

    //private void getIncoming ()
}
