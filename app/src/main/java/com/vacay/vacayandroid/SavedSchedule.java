package com.vacay.vacayandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class SavedSchedule extends AppCompatActivity {
    //declare variables
    TextView eventNameTextView;
    TextView eventDescriptionTextView;
    TextView eventCityTextView;
    TextView eventPriceIntView;
    TextView eventWebsiteTextView;
    ImageView eventImage;
    TextView eventId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_schedule);

        initiUI();

        bindData(getData());

    }

    private AppEvent getData() {
        AppEvent appEvent = new AppEvent();
        Intent intent = getIntent();

        if(intent.hasExtra(Constants.EVENT_PRICE)) {
            appEvent.setEventPrice(intent.getIntExtra(Constants.EVENT_PRICE, -1));
        }

        if(intent.hasExtra(Constants.EVENT_DESCRIPTION)) {
            appEvent.setEventDescription(intent.getStringExtra(Constants.EVENT_DESCRIPTION));
        }

        if(intent.hasExtra(Constants.EVENT_CITY)) {
            appEvent.setEventCity(intent.getStringExtra(Constants.EVENT_CITY));
        }

        if(intent.hasExtra(Constants.EVENT_WEBSITE)) {
            appEvent.setEventWebsite(intent.getStringExtra(Constants.EVENT_WEBSITE));
        }

        if(intent.hasExtra(Constants.EVENT_NAME)) {
            appEvent.setEventName(intent.getStringExtra(Constants.EVENT_NAME));
        }

        if(intent.hasExtra(Constants.EVENT_IMAGE)) {
            appEvent.setEventImage(intent.getStringExtra(Constants.EVENT_IMAGE));
        }

//        if(intent.hasExtra(Constants.EVENT_ID)) {
//            appEvent.setEventImage(intent.getStringExtra(Constants.EVENT_ID));
//        }

        //Log.d("AppEvent", "getData: "+appEvent);
        return appEvent;
    }

    private void bindData(AppEvent appEvent) {
//        if(appEvent.getEventId() != null) {
//            eventNameTextView.setText(appEvent.getEventId());
//        }

        if(appEvent.getEventName() != null) {
            eventNameTextView.setText(appEvent.getEventName());
        }

        if(appEvent.getEventPrice() != -1) {
            eventPriceIntView.setText(String.valueOf(appEvent.getEventPrice()));
        }

        if(appEvent.getEventDescription() != null) {
            eventDescriptionTextView.setText(appEvent.getEventDescription());
        }

        if(appEvent.getEventCity() != null) {
            eventCityTextView.setText(appEvent.getEventCity());
        }

        if(appEvent.getEventWebsite() != null) {
            eventWebsiteTextView.setText(appEvent.getEventWebsite());
        }

        if(appEvent.getEventImage() != null) {
            Picasso.get().load(appEvent.getEventImage()).error(R.drawable.ic_launcher_background).into(eventImage);
        }
    }

    private void initiUI() {
        eventNameTextView = findViewById(R.id.event_name);
        eventDescriptionTextView = findViewById(R.id.event_description);
        eventCityTextView = findViewById(R.id.event_city);
        eventPriceIntView = findViewById(R.id.event_price);
        eventWebsiteTextView = findViewById(R.id.event_website);
        eventImage = findViewById(R.id.event_image);
    }
}
