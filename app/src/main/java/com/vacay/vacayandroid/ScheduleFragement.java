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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ScheduleFragement extends Fragment {
    View view;
    final static String URL = "https://vaca-backend.herokuapp.com/schedules/1";
    private SavedEventsAdapter mSavedEventsAdapter;
    private RecyclerView mRecyclerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_schedule, container, false);
        initAdater(view);
        getSavedEvents();

        return view;
    }

    private void initAdater (View view) {
        mSavedEventsAdapter = new SavedEventsAdapter();
        mRecyclerView = view.findViewById(R.id.events_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mSavedEventsAdapter);
    }

    private void getSavedEvents() {

        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                List<SavedEvent> savedEvents = new ArrayList<>();
                try {
                    JSONArray arrayResponse = new JSONArray(response);
                    for (int i = 0; i < arrayResponse.length(); i++) {
                        SavedEvent savedEvent = new SavedEvent();
                        JSONObject data = arrayResponse.getJSONObject(i);
                        if (data.has("eventName")) {
                            savedEvent.setEventName(data.getString("eventName"));
                        }

                        if (data.has("eventDescription")) {
                            savedEvent.setEventDescription(data.getString("eventDescription"));
                        }

                        if (data.has("eventCity")) {
                            savedEvent.setEventCity(data.getString("eventCity"));
                        }

                        if (data.has("schedule_id")) {
                            savedEvent.setScheduleId(data.getInt("schedule_id"));
                        }

                        savedEvents.add(savedEvent);
                    }

                    mSavedEventsAdapter.setEvents(savedEvents);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);
    }
}
