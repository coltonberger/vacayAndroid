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
            eventTitle.setText(appEvent.getEventName());
            Picasso.get().load(appEvent.getEventImage()).error(R.drawable.ic_launcher_background).placeholder(R.drawable.ic_launcher_background).into(eventImage);
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

        public void addEvent() {
            //POST REQUEST WITH VOLLEY
            //RequestQueue queue = Volley.newRequestQueue();
            String url = "https://vaca-backend.herokuapp.com/schedules/1";
            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response) {
                            // response
                            Log.d("Response", response);
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // error
                            //Log.d("Error.Response", response);
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams()
                {         // Adding parameters
                    Map<String, String>  params = new HashMap<String, String>();
                    params.put("id", "value1");
                    params.put("eventName", "value2");
                    params.put("eventCity", "value3");
                    params.put("eventDescription", "value4");

                    return params;
                }
            };
            //queue.add(postRequest);
        }

        @Override
        public void onClick(View v) {
           switch (v.getId()) {
               case R.id.discover_more:
                   //Log.d("Clicked", "onClick: discover clicked");
                   Intent intent  = new Intent(v.getContext() , EventDetail.class);
                   prepareIntent(intent);
                   discoverMoreButton.getContext().startActivity(intent);
                   break;

               case R.id.add_to_schedule:
                   addEvent();

//                   Intent addToScheduleIntent  = new Intent(v.getContext() , SavedSchedule.class);
//                   prepareIntent(addToScheduleIntent);
                   Log.d("Clicked", "onClick: add to schedule clicked");
                   break;

           }
        }
    }
}