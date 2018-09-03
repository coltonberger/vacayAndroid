package com.vacay.vacayandroid;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView eventList;
    private EventListAdapter mEventListAdapter; // google started this crazy m before name thing for my...

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eventList = (RecyclerView) findViewById(R.id.events_list);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        eventList.setLayoutManager(layoutManager);
        mEventListAdapter = new EventListAdapter();
        eventList.setAdapter(mEventListAdapter);


         //* JSONObject  response = new JSONObject(response);
         //JSONarray data = response.getJSONarray("data");

        VolleyRequest.makeGetRequest(this, "https://vaca-backend.herokuapp.com/events", new VolleyRequest.RequestCallback() {
            @Override
            public void onResponse(String response, VolleyError error) {
                if (error != null) {
                    Log.e("ERROR", error + "");
                    toastMessage(error + "");
                } else {
                    //Log.d("RESPONSE", response);
                    //parse the response
                    ArrayList<AppEvent> eventArrayList = new ArrayList<>();
                    // The response is a json
                    //We have to look at what the structure looks like to parse it
                    // The response in this case is a jsonArray, other times it can be object

                    //Look at it carefully, in this case it's an array at the base.

                    try {
                        JSONObject responseObject = new JSONObject(response);
                        if (responseObject.has("data")) {
                            JSONArray dataArray = responseObject.getJSONArray("data");
                            for (int i = 0; i < dataArray.length(); i++) {
                                AppEvent appEvent = new AppEvent();
                                JSONObject eventObject = dataArray.getJSONObject(i);
                                if(eventObject.has("eventName")) {
                                    appEvent.setEventName(eventObject.getString("eventName"));
                                }

                                if(eventObject.has("eventImage")) {
                                    appEvent.setEventImage(eventObject.getString("eventImage"));
                                }

                                if(eventObject.has("eventDescription")) {
                                    appEvent.setEventDescription(eventObject.getString("eventDescription"));
                                }

                                if(eventObject.has("id")) {
                                    appEvent.setEventId(eventObject.getString("id"));
                                }

                                if(eventObject.has("eventPrice")) {
                                    appEvent.setEventPrice(eventObject.getInt("eventPrice"));
                                }

                                if(eventObject.has("eventCity")) {
                                    appEvent.setEventCity(eventObject.getString("eventCity"));
                                }

                                eventArrayList.add(appEvent);
                            }

                            mEventListAdapter.setEvents(eventArrayList);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    //We want to setup the list now
    class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.EventListHolder> {
        private List<AppEvent> events = new ArrayList<>();

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
        public void onBindViewHolder(@NonNull EventListHolder holder, int position) {
            holder.bindTo(events.get(position)); // that's how to bind
        }

        @Override
        public int getItemCount() {
            return events.size();
        }

        class EventListHolder extends RecyclerView.ViewHolder {
            private TextView eventTitle;
            private TextView eventDescription; // not sure about if we have this
            private ImageView eventImage;

            public EventListHolder(View itemView) {
                super(itemView);
                eventTitle = (TextView) itemView.findViewById(R.id.event_title);
                eventImage = (ImageView) itemView.findViewById(R.id.event_image);
            }

            public void bindTo(AppEvent appEvent) {
                eventTitle.setText(appEvent.getEventName());
                Picasso.get().load(appEvent.getEventImage()).error(R.drawable.ic_launcher_background).placeholder(R.drawable.ic_launcher_background).into(eventImage); // crap, I use fresco now guess they changed it, let me see
            }
        }
    }
}
