package com.vacay.vacayandroid;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.List;

public class SavedEventsAdapter extends RecyclerView.Adapter<SavedEventsAdapter.SavedEventListHolder>{
    List<SavedEvent> savedEvents = new ArrayList<>();


    public void setEvents(List<SavedEvent> appEvents) {
        this.savedEvents = appEvents;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SavedEventListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_saved_schedule, parent, false);
        return new SavedEventListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedEventListHolder holder, final int position) {
        holder.bindTo(savedEvents.get(position)); // that's how to bind
    }

    @Override
    public int getItemCount() {
        return savedEvents.size();
    }

    public void removeItemAt(int position) {
        savedEvents.remove(position);
        notifyDataSetChanged();
    }

    //packages -> adapters, holders, activities, fragments
    class SavedEventListHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        //for mainPage
        private ImageView eventImage;
        private TextView eventName;
        private TextView eventDescription;
        private ImageView delete;
        private SavedEvent mSavedEvent;

        public SavedEventListHolder(View itemView)  {
            super(itemView);
            eventName = itemView.findViewById(R.id.event_name);
            eventImage = itemView.findViewById(R.id.event_image);
            delete = itemView.findViewById(R.id.delete_event);
            eventDescription= itemView.findViewById(R.id.event_description);
            delete.setOnClickListener(this);
        }

        public void bindTo(SavedEvent appEvent) {
            this.mSavedEvent = appEvent;
            eventName.setText(appEvent.getEventName());
            eventDescription.setText(appEvent.getEventDescription());
            Picasso.get().load(appEvent.getEventImage()).error(R.drawable.ic_launcher_background).placeholder(R.drawable.ic_launcher_background).into(eventImage);
            Log.d("SavedEvent", "SavedEvent"+mSavedEvent);
        }


        @Override
        public void onClick(final View v) {
            //Log.d("View v", "onClick: "+ v.getId());
            switch (v.getId()) {
                case R.id.delete_event:
                    //Instantiate the RequestQueue
                    //Log.d("eventId", "clicked view"+ this.mSavedEvent.getEventId());
                    RequestQueue queue = Volley.newRequestQueue(v.getContext());
                    String url = "https://vaca-backend.herokuapp.com/savedEvents/" + this.mSavedEvent.getEventId() + "/" + mSavedEvent.getScheduleId();
                    //Log.d("URL", url);
                    StringRequest deleteRequest = new StringRequest(Request.Method.DELETE, url,
                            new Response.Listener<String>()
                            {
                                @Override
                                public void onResponse(String response) {
                                    // response
                                    removeItemAt(getAdapterPosition());
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
                    );
                    queue.add(deleteRequest);
                    break;
            }
        }
    }
}
