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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

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

                   RequestQueue queue = Volley.newRequestQueue(v.getContext());
                   Log.d("addToSchedule", "clicked id: "+ this.mSaveEvent.getEventId());
                   Log.d("addToSchedule", "clicked name: "+ this.mSaveEvent.getEventName());
                   Log.d("addToSchedule", "clicked city: "+ this.mSaveEvent.getEventCity());
                   Log.d("addToSchedule", "clicked description: "+ this.mSaveEvent.getEventDescription());

                   String url = "https://vaca-backend.herokuapp.com/schedules/"+ this.mSaveEvent.getEventId();
                   StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                           new Response.Listener<String>()
                           {
                               @Override
                               public void onResponse(String response) {
                                   Log.d("Response", response);
                               }
                           },
                           new Response.ErrorListener()
                           {
                               @Override
                               public void onErrorResponse(VolleyError error) {
                                   Toast.makeText(v.getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                               }
                           }
                   ) {
                       @Override
                       protected Map<String, String> getParams()
                       {         // Adding parameters
                           Map<String, String>  params = new HashMap<String, String>();
                           params.put("id", mSaveEvent.getEventId());
                           params.put("eventName", mSaveEvent.getEventName());
                           params.put("eventCity", mSaveEvent.getEventCity());
                           params.put("eventDescription", mSaveEvent.getEventDescription());
                           params.put("schedule_Id", "1");

                           Log.d("params", "getParams: "+ params);
                           return params;
                       }
                   };
                   queue.add(postRequest);
//                   Intent addToScheduleIntent  = new Intent(v.getContext() , SavedSchedule.class);
//                   prepareIntent(addToScheduleIntent);
                   //Log.d("Clicked", "onClick: add to schedule clicked");
                   break;

           }
        }
    }
}