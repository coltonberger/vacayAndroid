package com.vacay.vacayandroid;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.EventListHolder> {
    List<AppEvent> events = new ArrayList<>();

    public void setEvents(List<AppEvent> appEvents) {
        this.events = appEvents;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public EventListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_item_event, parent, false);
        return new EventListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventListHolder holder, final int position) {
        holder.bindTo(events.get(position)); // that's how to bind
    }

    @Override
    public int getItemCount() {
        return events.size();
    }


    //packages -> adapters, holders, activities, fragments
    class EventListHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        //for mainPage
        private TextView eventTitle;
        private ImageView eventImage;
        private Button discoverMoreButton;
        private Button addToScheduleButton;
        private AppEvent mSaveEvent;

        public EventListHolder(View itemView) {
            super(itemView);
            eventTitle = itemView.findViewById(R.id.event_title);
            eventImage = itemView.findViewById(R.id.event_image);
            discoverMoreButton = itemView.findViewById(R.id.discover_more);
            discoverMoreButton.setOnClickListener(this);
            addToScheduleButton = itemView.findViewById(R.id.add_to_schedule);
            addToScheduleButton.setOnClickListener(this);
        }

        public void bindTo(AppEvent appEvent) {
            this.mSaveEvent = appEvent;
            eventTitle.setText(appEvent.getEventName());
            Picasso.get().load(appEvent.getEventImage()).error(R.drawable.ic_launcher_background).placeholder(R.drawable.ic_launcher_background).into(eventImage);
            Log.d("SaveEvent", "SaveEvent"+mSaveEvent);
        }

        private void prepareIntent(Intent intent) {
            AppEvent appEvent = events.get(getAdapterPosition());

            intent.putExtra(Constants.EVENT_NAME, appEvent.getEventName());
            intent.putExtra(Constants.EVENT_DESCRIPTION, appEvent.getEventDescription());
            intent.putExtra(Constants.EVENT_CITY, appEvent.getEventCity());
            intent.putExtra(Constants.EVENT_PRICE, appEvent.getEventPrice());
            intent.putExtra(Constants.EVENT_WEBSITE, appEvent.getEventWebsite());
            intent.putExtra(Constants.EVENT_IMAGE, appEvent.getEventImage());
        }

        @Override
        public void onClick(final View v) {
           switch (v.getId()) {
               case R.id.discover_more:
                   //Log.d("Clicked", "onClick: discover clicked");
                   Intent intent  = new Intent(v.getContext() , EventDetail.class);
                   prepareIntent(intent);
                   discoverMoreButton.getContext().startActivity(intent);
                   break;

               case R.id.add_to_schedule:

                   /**
                    *
                    created_at
                    :
                    "2018-09-01T23:20:45.418Z"
                    eventCity
                    :
                    "Los Angeles"
                    eventDescription
                    :
                    "PLEASE JOIN US FOR A LITTLE COFFEE AND A LOT OF AMAZING AUTOMOBILES."
                    eventImage
                    :
                    "http://ajeclassics.co.uk/wp-content/uploads/2016/03/Jag-129.jpg"
                    eventName
                    :
                    "Cars and Coffee"
                    eventPrice
                    :
                    5
                    eventWebsite
                    :
                    "https://www.eventbrite.com/e/cars-and-coffee-la-tickets-34210770335"
                    id
                    :
                    3
                    updated_at
                    :
                    "2018-09-01T23:20:45.418Z"
                    */

                   RequestQueue queue = Volley.newRequestQueue(v.getContext());
//                   Log.d("addToSchedule", "clicked id: "+ this.mSaveEvent.getEventId());
//                   Log.d("addToSchedule", "clicked name: "+ this.mSaveEvent.getEventName());
//                   Log.d("addToSchedule", "clicked city: "+ this.mSaveEvent.getEventCity());
//                   Log.d("addToSchedule", "clicked description: "+ this.mSaveEvent.getEventDescription());

                   String url = "https://vaca-backend.herokuapp.com/schedules/";

                   JSONObject parent = new JSONObject();
                   JSONArray eventsList = new JSONArray();
                   JSONObject event = new JSONObject();
                   try {
                       event.put("id", mSaveEvent.getEventId()+"");
                       event.put("eventWebsite", mSaveEvent.getEventWebsite());
                       event.put("eventPrice", mSaveEvent.getEventPrice() + "");
                       event.put("eventName", mSaveEvent.getEventName());
                       event.put("eventImage", mSaveEvent.getEventImage());
                       event.put("eventDescription", mSaveEvent.getEventDescription());
                       event.put("eventCity", mSaveEvent.getEventCity());

                       eventsList.put(event);

                       parent.put("events", eventsList);
                       parent.put("userId", 1);

                   } catch (JSONException e) {
                       e.printStackTrace();
                   }

                   JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, parent, new Response.Listener<JSONObject>() {
                       @Override
                       public void onResponse(JSONObject response) {
                            Toast.makeText(v.getContext(), "Event added", Toast.LENGTH_LONG).show();
                       }
                   }, new Response.ErrorListener() {
                       @Override
                       public void onErrorResponse(VolleyError error) {
                           Toast.makeText(v.getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                       }
                   });

                    queue.add(request);
                   break;

           }
        }
    }
}