package com.vacay.vacayandroid;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EventsFragement extends Fragment {
    private EventListAdapter mEventListAdapter;
    private RecyclerView list;
    private String usePrice;
    String filter;
    View view;
    List<AppEvent> appEvents = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_events, container, false);

        initAdapter();

        getArgs();

        getEvents();

        return view;
    }

    //filters for both city and price
    private void getArgs() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            if (bundle.containsKey(Constants.CITY)) filter = bundle.getString(Constants.CITY);
            if (bundle.containsKey(Constants.USE_PRICE))
                usePrice = bundle.getString(Constants.USE_PRICE);
        }
    }

    //filtering by event price adding it to city that was filtered
    public void setFilter(String filter) {
        if(appEvents != null && appEvents.size() > 0) {
            if(filter.equalsIgnoreCase(Constants.CHEAP)) {
                List<AppEvent> events = new ArrayList<>();
                for (AppEvent  event : appEvents) {
                    if(event.getEventPrice() <= 20) {
                        events.add(event);
                    }
                }

                mEventListAdapter.setEvents(events);
            }

            if(filter.equalsIgnoreCase(Constants.MEDIUM)) {
                List<AppEvent> events = new ArrayList<>();
                for (AppEvent  event : appEvents) {
                    if(event.getEventPrice() > 20 && event.getEventPrice() < 40) {
                        events.add(event);
                    }
                }

                mEventListAdapter.setEvents(events);
            }

            if(filter.equalsIgnoreCase(Constants.EXPENSIVE)) {
                List<AppEvent> events = new ArrayList<>();
                for (AppEvent event : appEvents) {
                    if(event.getEventPrice() > 40) {
                        events.add(event);
                    }
                }

                mEventListAdapter.setEvents(events);
            }
        }
    }

    private void initAdapter() {
        mEventListAdapter = new EventListAdapter();
        list = view.findViewById(R.id.events_list);
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        list.setAdapter(mEventListAdapter);
    }

    private void getEvents() {
        VolleyRequest.makeGetRequest(getContext(), "https://vaca-backend.herokuapp.com/events", new VolleyRequest.RequestCallback() {
            @Override
            public void onResponse(String response, VolleyError error) {
                if (error != null) {
                    Log.e("ERROR", error + "");
                } else {
                    //Log.d("RESPONSE", response);
                    //parse the response


                    // The response is a jsonArray

                    //Look at it carefully, in this case it's an array at the base.

                    try {
                        JSONObject responseObject = new JSONObject(response);
                        if (responseObject.has("data")) {
                            JSONArray dataArray = responseObject.getJSONArray("data");
                            for (int i = 0; i < dataArray.length(); i++) {
                                AppEvent appEvent = new AppEvent();
                                JSONObject eventObject = dataArray.getJSONObject(i);
                                if (eventObject.has("eventName")) {
                                    appEvent.setEventName(eventObject.getString("eventName"));
                                }

                                if (eventObject.has("eventImage")) {
                                    appEvent.setEventImage(eventObject.getString("eventImage"));
                                }

                                if (eventObject.has("eventDescription")) {
                                    appEvent.setEventDescription(eventObject.getString("eventDescription"));
                                }

                                if (eventObject.has("id")) {
                                    appEvent.setEventId(eventObject.getString("id"));
                                }

                                if (eventObject.has("eventPrice")) {
                                    appEvent.setEventPrice(eventObject.getInt("eventPrice"));
                                }

                                if (eventObject.has("eventCity")) {
                                    appEvent.setEventCity(eventObject.getString("eventCity"));
                                }

                                if (eventObject.has("eventWebsite")) {
                                    appEvent.setEventWebsite(eventObject.getString("eventWebsite"));
                                }

                                appEvents.add(appEvent);
                            }

                            if (filter != null && !filter.isEmpty()) {
                                ArrayList<AppEvent> events = new ArrayList<>();
                                for (AppEvent event : appEvents) {
                                    if (event.getEventCity().toLowerCase().contains(filter.toLowerCase())) {
                                        events.add(event);
                                    }
                                }

                                appEvents.clear();
                                appEvents.addAll(events);
                                mEventListAdapter.setEvents(appEvents);

                            }  else {
                                mEventListAdapter.setEvents(appEvents);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
